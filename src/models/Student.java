package models;
import OfficeHours.*;

import java.util.ArrayList;
import java.time.*;
import java.util.*;


public class Student extends User 
{
    private ArrayList<Class> classes;
    private Boolean inMeeting;
    private Boolean inWaiting;
    private Class ohClass;
    private String ohLink;

    public Student(String name_, String email_, int userId_)
    {
        super(name_,  email_,  1,  userId_);
        this.classes = DatabaseUtil.getClassesFromStudent(this);   
        inMeeting = false;
        inWaiting = false;
        ohClass = null;
        ohLink = "";
    }

    public ArrayList<Class> getClasses() 
    {
        return classes;
    }
    
    public Class getOHClass()
    {
    	return ohClass;
    }
    
    public Boolean getInMeetingStatus()
    {
    	return inMeeting;
    }
    
    public Boolean getInWaitingStatus()
    {
    	return inWaiting;
    }
    
    public void setInWaitingStatus(Boolean newWaiting)
    {
    	inWaiting = newWaiting;
    }
    
    public void getClientLink (String link)
    {
    	ohLink = link;
    }
    
    public String getOHLink()
    {
    	return ohLink;
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
//    public Boolean enterClass (Class c)
//    {
//    	if (classes.contains(c))
//    	{
//    		ohClass = c;
//        	return true;
//    	}
//    	return false;
//    }
    
	public Boolean joinOH (Class c)
    {
    	if (classes.contains(c) && c.getOH() != null && ohClass == null)
    	{
    		// check time constraint
    		if (ZonedDateTime.now(c.getTimezone()).isBefore(c.getOH().getStartTime())
    				|| ZonedDateTime.now(c.getTimezone()).isAfter(c.getOH().getEndTime()))
    		{
    			return false;
    		}
    		ohClass = c;
    		c.getOH().addStudentToWaiting(this);
    		return true;
    	}
    	return false;
    }
    
    public void startingTurn() {
		System.out.println (ZonedDateTime.now(ohClass.getTimezone()) + " Student " + getFullName() + " is starting turn in meeting.");

	}

	public void finishingTurn() {
		System.out.println (ZonedDateTime.now(ohClass.getTimezone()) + " Student " + getFullName() + " is ending turn in meeting.");
	}
    
    public void leaveWaitingList(Class c)
    {
    	c.getOH().removeStudentFromWaiting(this);
    }
    
	@Override
	public void run()
	{
		inWaiting = false;
		inMeeting = true;
		try
		{
			startingTurn();
			Thread.sleep((long)(ohClass.getOH().getTimeSlot() * 60000));
		}
		catch (InterruptedException ie) {}
		finally
		{
			// finish delivery
			finishingTurn();
			ohClass.getOH().removeStudentFromMeeting(this);
			ohClass = null;
		}
		inMeeting = false;
	}
}