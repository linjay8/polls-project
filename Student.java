import java.util.ArrayList;
import java.time.*;
import java.util.*;


public class Student extends User 
{
    private ArrayList<Class> classes;
    private Class inClass; // the class page they are currently on


    public Student(String name_, String email_, int userId_)
    {
        super(name_,  email_,  1,  userId_);
        if(!DatabaseUtil.userExists(userId_))
		{
			DatabaseUtil.addNewUser(this);
		}
        this.classes = DatabaseUtil.getClassesFromStudent(this);   
        inClass = null;
    }

    public ArrayList<Class> getClasses() 
    {
        return classes;
    }

//    public ArrayList<Poll> getPrivatePolls(String classCode) 
//    {
//        for(Class c : classes)
//        {
//        	if(c.getClassCode() == classCode)
//        	{
//        		return c.getPollList();
//        	}
//        }
//        return null;
//    }
    
    public void enrollClass(String classCode)
    {
    	if(DatabaseUtil.classCodeExists(classCode) && !DatabaseUtil.alreadyEnrolled(classCode, userId))
    	{
	    	DatabaseUtil.addStudentToClass(classCode, userId);
	    	Class c = DatabaseUtil.getClass(classCode);
	    	classes.add(c);
    	}
    }
    
    // enter a class page
    public Boolean enterClass (Class c)
    {
    	if (classes.contains(c))
    	{
    		inClass = c;
        	return true;
    	}
    	return false;
    }
    
//    public Boolean joinOH (Class c)
//    {
//    	if (classes.contains(c) && c.getOH() != null)
//    	{
//    		// check time constraint
//    		if (ZonedDateTime.now(c.getTimezone()).isBefore(c.getOH().getStartTime())
//    				|| ZonedDateTime.now(c.getTimezone()).isAfter(c.getOH().getEndTime()))
//    		{
//    			return false;
//    		}
//    		inClass.getOH().addStudentToWaiting(this);
//			
//    		return true;
//    	}
//    	return false;
//    }
//    
//    public void startingTurn() {
//		System.out.println (ZonedDateTime.now(inClass.getTimezone()) + " Student " + getFullName() + " is starting turn in meeting.");
//
//	}
//
//	public void finishingTurn() {
//		System.out.println (ZonedDateTime.now(inClass.getTimezone()) + " Student " + getFullName() + " is ending turn in meeting.");
//	}
//    
//    public void leaveMeeting(Class c)
//    {
//    	c.getOH().removeStudentFromMeeting(this);
//    }
//    
//    public void leaveWaitingList(Class c)
//    {
//    	c.getOH().removeStudentFromWaiting(this);
//    }
//    
//	@Override
//	public void run()
//	{
//		try
//		{
//			startingTurn();
//			Thread.sleep((long)(inClass.getOH().getTimeSlot() * 60000));
//		}
//		catch (InterruptedException ie) {}
//		finally
//		{
//			// finish delivery
//			finishingTurn();
//			inClass.getOH().removeStudentFromMeeting(this);
//		}
//	}
}