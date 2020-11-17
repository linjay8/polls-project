import java.time.*;
import java.util.*;
import java.util.concurrent.*;

public class OfficeHours implements Runnable {
	
	private ConcurrentLinkedQueue<Student> inMeeting;
	private Vector<Student> waitingList;
	private Queue<Student> waitToMeet;
	private int meetingLimit;
	private int meetingSize;
	private int waitingSize;
	private double timeSlot;
	private String link;
	private ZoneId timezone;
	private ZonedDateTime startTime;
	private ZonedDateTime endTime;
	
	private transient Semaphore semaphore;
	
	public OfficeHours (int meetingLimit, double timeSlot, String link, ZoneId timezone,
			String startTime, String endTime)
	{
		this.meetingLimit = meetingLimit;
		this.meetingSize = 0;
		this.waitingSize = 0;
		this.timeSlot = timeSlot;
		this.link = link;

		this.inMeeting = new ConcurrentLinkedQueue<Student>();
		this.waitingList = new Vector<Student>();
		this.waitToMeet = new LinkedList<Student>();
		
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
	
	public void addStudentToWaiting(Student student)
	{
		System.out.println ("Student " + student.getFullName() + " place in line: " + (meetingSize + waitingSize));
		waitingList.add(student);
		waitingSize++;
		System.out.println (ZonedDateTime.now(timezone) + " Student " + student.getFullName() + " is waiting");
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
		sendLink(student);
		
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
	
	public String sendLink (Student student)
	{
		System.out.println ("Zoom link");
		return link;
	}
	
	public void initSemaphore()
	{
		semaphore = new Semaphore(meetingLimit);
	}

	@Override
	public void run() {
		System.out.println("STARTING OH RUN");
		while (ohOpen())
		{
			//System.out.println("OH OPEN");
			if (!waitingList.isEmpty())
			{
				if (meetingSize < meetingLimit)
				{
					// remove student from waitlist
					Student s = waitingList.remove(0);
					waitingSize--;
					// add student to meeting and send link
					addStudentToMeeting(s);
				}
			}
		}
		System.out.println("OH NOT OPEN");
	}
	
}
