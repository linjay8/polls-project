import java.util.ArrayList;

public class Student extends User 
{
    private ArrayList<Class> classes;
    private String fname;
    private String lname;

    public Student(String name_, String email_, int userId_)
    {
        super( name_,  email_,  1,  userId_);
    }

    public ArrayList<Class> getClasses() 
    {
        return classes;
    }

    public ArrayList<Poll> getPrivatePolls(String classCode) 
    {
        for(Class c : classes)
        {
        	if(c.getClassCode() == classCode)
        	{
        		return c.getPollList();
        	}
        }
        return null;
    }
    
    public void enrollClass(String classCode)
    {
    	// database searching
    	// add class
    }
}