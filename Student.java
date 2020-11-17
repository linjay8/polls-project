import java.time.*;
import java.util.*;

public class Student extends User implements Runnable {
    private ArrayList<Class> classes;
	private Class inClass;
	
    public Student(String name_, String email_, int userId_){
        super( name_,  email_,  1,  userId_);
        this.classes = new ArrayList<Class>();   
        inClass = null;
    }

    public ArrayList<Class> getClasses() {
        return classes;
    }
    
    public void enroll (Class c, String classCode)
    {
    	if (classCode.equals(c.getClassCode()))
    	{
    		classes.add(c);
    		c.addStudent(this);
    		inClass = c;
    	}
    }

    public Boolean enterClass (Class c)
    {
    	if (classes.contains(c))
    	{
    		inClass = c;
        	return true;
    	}
    	return false;
    }
    
    public Boolean joinOH (Class c)
    {
    	if (classes.contains(c) && c.getOH() != null)
    	{
    		// check time constraint
    		if (ZonedDateTime.now(c.getTimezone()).isBefore(c.getOH().getStartTime())
    				|| ZonedDateTime.now(c.getTimezone()).isAfter(c.getOH().getEndTime()))
    		{
    			return false;
    		}
			// create and start student thread
			Runnable r = this;
			Thread t = new Thread(r);
			t.start();
			
    		return true;
    	}
    	return false;
    }
    
    public void startingTurn() {
		System.out.println (ZonedDateTime.now(inClass.getTimezone()) + " Student " + getFullName() + " is starting turn in meeting.");
	}

	public void finishingTurn() {
		System.out.println (ZonedDateTime.now(inClass.getTimezone()) + " Student " + getFullName() + " is ending turn in meeting.");
	}
    
    public void leaveMeeting(Class c)
    {
    	c.getOH().removeStudentFromMeeting(this);
    }
    
    public void leaveWaitingList(Class c)
    {
    	c.getOH().removeStudentFromWaiting(this);
    }
    
	@Override
	public void run()
	{
		OfficeHours oh = inClass.getOH();
		oh.addStudentToWaiting(this);
		
//		try {
//			Thread.sleep((long)waitTime * 60000);
//		} catch (InterruptedException e) {}
		
		try
		{
			startingTurn();
			Thread.sleep((long)(oh.getTimeSlot() * 60000));
		}
		catch (InterruptedException ie) {}
		finally
		{
			// finish delivery
			finishingTurn();
			oh.removeStudentFromMeeting(this);
		}
	}
}