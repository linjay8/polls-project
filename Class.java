import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class Class {
	
	private Instructor instructor;
	private ArrayList<Student> students;
	private OfficeHours oh;
	private ZoneId timezone;
	private String name;
	private String classCode;
	
	public Class (Instructor instructor, String name, String classCode)
	{
		this.instructor = instructor;
		this.students = new ArrayList<Student>();
		this.oh = null;
		this.timezone = instructor.getTimezone();
		this.name = name;
		this.classCode = classCode;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void addStudent(Student s)
	{
		students.add(s);
	}
	
	public String getClassCode()
	{
		return classCode;
	}
	
	public Instructor getInstructor()
	{
		return instructor;
	}

	public OfficeHours getOH()
	{
		return oh;
	}
	
	public ZoneId getTimezone()
	{
		return timezone;
	}
	
	public void startOH(int meetingLimit, double timeSlot, String link,
			ZoneId timezone, String startTime, String endTime)
	{
		oh = new OfficeHours (meetingLimit, timeSlot, link, timezone, startTime, endTime);
		oh.initSemaphore();
		System.out.println(ZonedDateTime.now(timezone) + "OH OPEN CORRECT");
		Runnable r = oh;
		Thread t = new Thread(r);
		t.start();
	}
	
	public void stopOH()
	{
		oh = null;
		System.out.println(ZonedDateTime.now(timezone) + " Instructor has ended OH");
	}
}
