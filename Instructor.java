import java.util.ArrayList;
import java.util.Random;
import java.time.*;

public class Instructor extends User
{
	private ArrayList<Class> classes;
//	private ArrayList<Poll> privatePolls;
	private ZoneId timezone;

	public Instructor(String name_, String email_, int userId_)
	{
		super( name_,  email_,  2,  userId_);
		if(!DatabaseUtil.userExists(userId_))
		{
			DatabaseUtil.addNewUser(this);
		}
		this.timezone = ZoneId.systemDefault();
		classes = DatabaseUtil.getClassesFromInstructor(this);
	}

	public ArrayList<Class> getClasses() 
	{
		return classes;
	}
	public String generateClassCode()
	{
		// replace this with apache
		int leftLimit = 48; // numeral '0'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 6;
	    Random random = new Random();
	 
	    String classCode = random.ints(leftLimit, rightLimit + 1)
	      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();
	    
	    return classCode;
	}
	
	public Class createNewClass(String className)
	{
		String classCode;
		do
		{
			classCode = generateClassCode();
		} while(DatabaseUtil.classCodeExists(classCode));
		
		Class c = new Class(this, className, classCode);
		classes.add(c);
		DatabaseUtil.addNewClass(c, userId);
		return c;
	}

//	public ArrayList<Poll> getPrivatePolls() {
//		return privatePolls;
//	}
//
//	/** Called when "create poll" button is pressed **/
//	public void createPublicPoll()
//	{
//		// Collect information from website
//		// Prompt instructor for how many questions they want to create
//		// loop to create response objects
//		// need 2 to 4 of strings for options
//		Poll p = new Poll();
//		addPublicPoll(p);
//	}
//
//	public void createPrivatePoll()
//	{
//		// Collect information from website
//		Poll p = new Poll();
//		privatePolls.add(p);
//	}
//
//
//	public void setPrivatePolls()
//	{
//		privatePolls.clear();
//		for(Class c : classes){
//			for (Poll p : c.getPollList()){
//				privatePolls.add(p);
//			}
//		}
//	}
//	public ArrayList<Poll> getPrivatePolls()
//	{
//		return privatePolls;
//	}
//
//	public ZoneId getTimezone()
//    {
//    	return timezone;
//    }
//
//    public Boolean startOfficeHours(Class c, int meetingLimit, double timeSlot, String link,
//			ZoneId timezone, String startTime, String endTime)
//    {
//    	if (c.getInstructor() == this)
//    	{    		
//    		System.out.println(ZonedDateTime.now(timezone) + " Instructor " + getFullName() + " is starting OH");
//        	c.startOH(meetingLimit, timeSlot, link, timezone, startTime, endTime);
//        	return true;
//    	}
//    	return false;
//    }
//    
//    public void endOfficeHours(Class c)
//    {
//    	if (c.getOH() != null)
//    	{
//    		c.stopOH();
//    	}
//    }

}