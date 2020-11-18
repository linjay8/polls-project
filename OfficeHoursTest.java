import java.util.*;
import java.time.*;

public class OfficeHoursTest {
	
	public static void main (String [] args)
	{
		Instructor i = new Instructor("Instructor1", "itest@usc.edu", 0);
		Class c1 = new Class(i, "Class1", "ABC123");
		Class c2 = new Class(i, "Class2", "ZYX987");

		Student s1 = new Student("Student1", "s1@usc.edu", 1);
		s1.enroll (c1, "ABC123");
		s1.enterClass(c1);
		
		Student s2 = new Student("Student2", "s2@usc.edu", 2);
		s2.enroll (c1, "ABC123");
		s2.enterClass(c1);
		
		Student s3 = new Student("Student3", "s3@usc.edu", 3);
		s3.enroll (c1, "ABC123");
		s3.enterClass(c1);

		Student s4 = new Student("Student4", "s4@usc.edu", 4);
		s4.enroll (c1, "ABC123");
		s4.enterClass(c1);

		Student s5 = new Student("Student5", "s5@usc.edu", 5);
		s5.enroll (c1, "ABC123");
		s5.enterClass(c1);

		Student s6 = new Student("Student6", "s6@usc.edu", 6);
		s6.enroll (c1, "ABC123");
		s6.enterClass(c1);

		Student s7 = new Student("Student7", "s7@usc.edu", 7);
		s7.enroll (c1, "ABC123");
		s7.enterClass(c1);

		Student s8 = new Student("Student8", "s8@usc.edu", 8);
		s8.enroll (c1, "ABC123");
		s8.enterClass(c1);
		
		Student s9NoAdd = new Student("Student9 NoAdd", "s9@usc.edu", 9);
		s9NoAdd.enroll (c2, "ZYX987");
		s9NoAdd.enterClass(c2);

		
		if (c1.getOH() == null)
		{
			System.out.println("NO OH OPEN CORRECT");
		}
		else
		{
			System.out.println("OH OPEN INCORRECT");

		}

		i.startOfficeHours(c1, 4, 1, "zoom.com", ZoneId.systemDefault(),
				"2020:11:17:00:00", "2020:11:18:23:00");
		
		try
		{
			Thread.sleep(3000);
		}
		catch (InterruptedException ie) {}
		
		s1.joinOH(c1);
		s2.joinOH(c1);
		try
		{
			Thread.sleep(15000);
		}
		catch (InterruptedException ie) {}
		
		s3.joinOH(c1);
		s4.joinOH(c1);
		s9NoAdd.joinOH(c1);
		s5.joinOH(c1);
		s6.joinOH(c1);
		try
		{
			Thread.sleep(15000);
		}
		catch (InterruptedException ie) {}
		s7.joinOH(c1);
		
		
		if (c1.getOH() == null)
		{
			System.out.println("NO OH OPEN INCORRECT");
		}
		
	}
}
