package OfficeHours;

import models.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.time.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OfficeHours implements Runnable {
	
	private ConcurrentLinkedQueue<Student> inMeeting;
	private Vector<Student> waitingList;
	
	private Instructor i;
	private int meetingLimit;
	private int meetingSize;
	private int waitingSize;
	private double timeSlot;
	private String link;
	private ZoneId timezone;
	private ZonedDateTime startTime;
	private ZonedDateTime endTime;
	private String classCode;
	
	private ServerSocket ss;
	private PrintWriter pw;
	
	private transient Semaphore semaphore;
		
	public OfficeHours (Instructor i, int meetingLimit, double timeSlot, String link, ZoneId timezone,
			ZonedDateTime startTime, ZonedDateTime endTime, String classCode)
	{
		this.i = i;
		this.meetingLimit = meetingLimit;
		this.meetingSize = 0;
		this.waitingSize = 0;
		this.timeSlot = timeSlot;
		this.link = link;

		this.inMeeting = new ConcurrentLinkedQueue<Student>();
		this.waitingList = new Vector<Student>();
		
		this.timezone = timezone;
		this.startTime = startTime;
		this.endTime = endTime;
		this.classCode = classCode;
		
		try {
			this.ss = new ServerSocket(6789);
			// time for student to accept meeting invite
			ss.setSoTimeout(30000);
			this.pw = null;
		} catch (IOException e) {}
		System.out.println(startTime + " Instructor " + i.getFullName() + " is starting OH");
	}
	
	public ArrayList<Student> getStudentsInMeeting()
	{
		ArrayList<Student> studentsReturn = new ArrayList<Student>();
		Student[] studentsInMeeting = (Student[]) inMeeting.toArray();
		for (Student s : studentsInMeeting)
		{
			studentsReturn.add(s);
		}
		return studentsReturn;
	}
	
	public ArrayList<Student> getWaitingStudents()
	{
		ArrayList<Student> studentsReturn = new ArrayList<Student>();
		for (Student s : waitingList)
		{
			studentsReturn.add(s);
		}
		return studentsReturn;
	}
	
	public ZoneId getTimezone()
	{
		return timezone;
	}
	
	public String getClassCode()
	{
		return classCode;
	}
	
	public int getMeetingLimit()
	{
		return meetingLimit;
	}
	
	public int getMeetingSize()
	{
		return meetingSize;
	}
	
	public int getWaitingSize()
	{
		return waitingSize;
	}
	
	public double getTimeSlot()
	{
		return timeSlot;
	}
	
	public ZonedDateTime getStartTime()
	{
		return startTime;
	}
	
	public ZonedDateTime getEndTime()
	{
		return endTime;
	}
	
	public String getLink()
	{
		return link;
	}
	
	public Boolean ohOpen()
	{
		if (ZonedDateTime.now(timezone).isBefore(startTime) 
				|| ZonedDateTime.now(timezone).isAfter(endTime))
		{
			return false;
		}
		return true;
	}
	
	public Boolean addStudentToWaiting(Student student)
	{
		student.setInWaitingStatus(true);
		waitingList.add(student);
		waitingSize++;
		System.out.println (ZonedDateTime.now(timezone) + " Student " + student.getFullName() + " is in waiting list");
		return true;
	}
	
	public Student removeStudentFromWaiting (Student student)
	{
		waitingList.remove(student);
		waitingSize--;
		System.out.println (ZonedDateTime.now(timezone) + " Student " + student.getFullName() + " is exiting from waiting list");
		return student;
	}
	
	public void addStudentToMeeting(Student student)
	{
		try
		{
			semaphore.acquire();
		}
		catch (InterruptedException e) {}
		
		System.out.println (ZonedDateTime.now(timezone) + " Inviting student " + student.getFullName() + " from waiting list to meeting");
		
				
		try {
			System.out.println("Run ClientThread.Java now to simulate clicking a button to accept meeting invite to " + student.getFullName());
			
			Socket s = ss.accept();
			pw = new PrintWriter(s.getOutputStream(), true);
			System.out.println(ZonedDateTime.now(timezone) + " Sending to " + student.getFullName() + ": " + link);
			pw.println(link);
			pw.flush();
			pw.close();
			s.close();
			inMeeting.add(student);
			meetingSize++;
			
			student.start();
		}
		catch (SocketTimeoutException st) 
		{
			semaphore.release();
			System.out.println("Meeting invite expired.");
		}
		catch (IOException e2) {}
		
	}
	
	public void removeStudentFromMeeting(Student student)
	{
		inMeeting.remove(student);
		meetingSize--;
		semaphore.release();
		System.out.println (ZonedDateTime.now(timezone) + " Student " + student.getFullName() + " is leaving meeting");
	}
	
	public void initSemaphore()
	{
		semaphore = new Semaphore(meetingLimit);
	}

	@Override
	public void run() {
		System.out.println(ZonedDateTime.now(timezone) + " OFFICE HOURS ARE OPEN");
		while (ohOpen())
		{
			while (!waitingList.isEmpty())
			{
				if (meetingSize < meetingLimit)
				{
					Student s = waitingList.remove(0);
					waitingSize--;
					s.setInWaitingStatus(false);
					addStudentToMeeting(s);
				}
			}
		}
		System.out.println(ZonedDateTime.now(timezone) + " OFFICE HOURS ARE CLOSED");
	}
	
}
