package OfficeHours;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
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
	
	private transient Semaphore semaphore;
	
	private LinkRoom lr;
	
	public OfficeHours (Instructor i, int meetingLimit, double timeSlot, String link, ZoneId timezone,
			String startTime, String endTime)
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
		// break up input by colons and trim extra whitespace
		// string input format = year:month:dayOfMonth:hour:minute
		String[] startVals = startTime.split(":");
		this.startTime = ZonedDateTime.of(Integer.parseInt(startVals[0]), Integer.parseInt(startVals[1]),
				Integer.parseInt(startVals[2]), Integer.parseInt(startVals[3]), Integer.parseInt(startVals[4]),
				0, 0, timezone);
		String[] endVals = endTime.split(":");
		this.endTime = ZonedDateTime.of(Integer.parseInt(endVals[0]), Integer.parseInt(endVals[1]),
				Integer.parseInt(endVals[2]), Integer.parseInt(endVals[3]), Integer.parseInt(endVals[4]),
				0, 0, timezone);
		lr = new LinkRoom(6789, link);
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
		System.out.println (ZonedDateTime.now(timezone) + " Student " + student.getFullName() + " is waiting");
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
		inMeeting.add(student);
		meetingSize++;
		
		System.out.println ("Transferring student " + student.getFullName() + " from waitlist to meeting");
		
		ClientThread ct = new ClientThread(student, "localhost", 6789);
		ct.start();
		
		// student has 1 min to join and then their time slot starts
		try {
			Thread.sleep(60,000);
		} catch (InterruptedException e1) {}
		
		student.start();
		
		try
		{
			semaphore.acquire();
		}
		catch (InterruptedException e) {}
	}
	
	public void removeStudentFromMeeting(Student student)
	{
		inMeeting.remove(student);
		meetingSize--;
		semaphore.release();
		System.out.println (ZonedDateTime.now(timezone) + ": Student " + student.getFullName() + " is leaving meeting");
	}
	
	public void initSemaphore()
	{
		semaphore = new Semaphore(meetingLimit);
	}

	@Override
	public void run() {
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
		// close LinkRoom
		lr = null;
	}
	
}
