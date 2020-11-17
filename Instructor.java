import java.util.*;
import java.time.*;

public class Instructor extends User {
    ArrayList<Class> classes;
    ZoneId timezone;


    public Instructor(String name_, String email_, int userId_){
        super( name_,  email_,  2,  userId_);
        this.timezone = ZoneId.systemDefault();
    }

    public ArrayList<Class> getClasses() {
        return classes;
    }
    
    public ZoneId getTimezone()
    {
    	return timezone;
    }

    public Boolean startOfficeHours(Class c, int meetingLimit, double timeSlot, String link,
			ZoneId timezone, String startTime, String endTime)
    {
    	if (c.getInstructor() == this)
    	{    		
    		System.out.println(ZonedDateTime.now(timezone) + " Instructor " + getFullName() + " is starting OH");
        	c.startOH(meetingLimit, timeSlot, link, timezone, startTime, endTime);
        	return true;
    	}
    	return false;
    }
    
    public void endOfficeHours(Class c)
    {
    	if (c.getOH() != null)
    	{
    		c.stopOH();
    	}
    }

}