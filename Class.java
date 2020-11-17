import java.util.ArrayList;

public class Class 
{
    private ArrayList<Poll> pollList;
    private String classCode;
    private String className;
    private Instructor instructor;
    private ArrayList<Student> students;
    private ArrayList<OfficeHours> officeHours;

    public Class()
    {
        pollList = new ArrayList<Poll>();
    }

    public ArrayList<Poll> getPollList() 
    {
        return pollList;
    }
    
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
    
    public ArrayList<OfficeHours> getOfficeHours()
    {
    	return officeHours;
    }
    
    public void addStudent(Student s)
    {
    	students.add(s);
    }
    
    public void addPoll(Poll p)
    {
    	pollList.add(p);
    }
    
    public void addOfficeHours(OfficeHours o)
    {
    	officeHours.add(o);
    }
    
    public void removeOfficeHours(OfficeHours o)
    {
    	officeHours.remove(o);
    }
    
    
}