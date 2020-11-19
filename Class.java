import java.util.ArrayList;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Class 
{
//    private ArrayList<Poll> pollList;
    private String classCode;
    private String className;
    private Instructor instructor;
    private ArrayList<Student> students;
//    private OfficeHours oh;
	private ZoneId timezone;

    public Class(Instructor instructor, String name, String classCode)
    {
//        pollList = new ArrayList<Poll>();
        this.instructor = instructor;
//		this.oh = null;
//		this.timezone = instructor.getTimezone();
		this.className = name;
		this.classCode = classCode;
    }

//    public ArrayList<Poll> getPollList() 
//    {
//        return pollList;
//    }
//    
    public String getClassCode() 
    {
    	return classCode;
    }
    
    public String getClassName()
    {
    	return className;
    }
    
    public Instructor getInstructor()
    {
    	return instructor;
    }
    
    public ArrayList<Student> getStudents()
    {
    	return students;
    }
    
    public void addStudent(Student s)
    {
    	students.add(s);
    }
    
//    public void addPoll(Poll p)
//    {
//    	pollList.add(p);
//    }
//    
//    public OfficeHours getOH()
//	{
//		return oh;
//	}
	
	public ZoneId getTimezone()
	{
		return timezone;
	}
	
//	public void startOH(int meetingLimit, double timeSlot, String link,
//			ZoneId timezone, String startTime, String endTime)
//	{
//		oh = new OfficeHours (meetingLimit, timeSlot, link, timezone, startTime, endTime);
//		oh.initSemaphore();
//		System.out.println(ZonedDateTime.now(timezone) + "OH OPEN CORRECT");
//		Runnable r = oh;
//		Thread t = new Thread(r);
//		t.start();
//	}
//	
//	public void stopOH()
//	{
//		oh = null;
//		System.out.println(ZonedDateTime.now(timezone) + " Instructor has ended OH");
//	}
//    
    
}