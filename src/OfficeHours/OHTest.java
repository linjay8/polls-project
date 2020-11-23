package OfficeHours;
import java.util.*;
import java.time.*;
import models.*;
import models.Class;

public class OHTest {
	
	/* NOTE: Make sure to rerun the database if you are running this Testing file again!!!
	 * 
	 * - Some tests will close OH immediately based on return value
	 * - If you do not run ClientThread.java when the code directs you to, after a small period,
	 * 	 your spot will be given to the next student.
	 */
	
	// try make an instructor create OH for one of their classes.
	// will return true, meaning the OH has been created.
	public static Boolean instructorCreateOH(Instructor i, Class c)
	{
		System.out.println("TEST: InstructorCreateOH");
		if (i.startOfficeHours(c, 2, 2, "zoom.com", ZoneId.systemDefault(), 1))
		{
			try {
				Thread.sleep(15000);
			} catch (InterruptedException e) {}
			i.endOfficeHours(c);
			return true;
		}
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {}
		i.endOfficeHours(c);
		return false;
	}
	
	// try make an instructor create OH for a class that is not theirs.
	// will return false, meaning the OH could not be created.
	public static Boolean instructorCreateOHWrongClass(Instructor i, Class c)
	{
		System.out.println("TEST: InstructorCreateOHWrongClass");
		if (i.startOfficeHours(c, 2, 2, "zoom.com", ZoneId.systemDefault(), 5))
		{
			try {
				Thread.sleep(15000);
			} catch (InterruptedException e) {}
			i.endOfficeHours(c);
			return true;
		}
		return false;
	}
	
	// try to add a student to OH before the instructor has created it.
	// will return false, meaning student is unable to join OH.
	public static Boolean addStudentBeforeStarted(Instructor i, Class c, Student s)
	{
		System.out.println("TEST: AddStudentBeforeStarted");
		if (!s.joinOH(c))
		{
			return false;
		}
		
		return true;
	}

	// try to add a student to OH after OH has ended.
	// will return false, meaning student is unable to join OH.
	public static Boolean addStudentAfterEnded(Instructor i, Class c, Student s)
	{
		System.out.println("TEST: AddStudentAfterEnded");
		i.startOfficeHours(c, 4, 0.5, "zoom.com", ZoneId.systemDefault(), 1);
		try
		{
			Thread.sleep(70000);
		}
		catch (InterruptedException ie) {}
		
		if (!s.joinOH(c))
		{
			i.endOfficeHours(c);
			return false;
		}
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {}
		i.endOfficeHours(c);
		return true;
	}
	
	// try to add a student to OH at a valid time.
	// will return true, meaning student has joined OH.
	// printed statements will show the 1st student joins the waiting room and immediately
	// transfers to meeting room
	public static Boolean addStudentToOH(Instructor i, Class c, Student s)
	{
		System.out.println("TEST: AddStudentToOH");
		i.startOfficeHours(c, 4, 0.5, "zoom.com", ZoneId.systemDefault(), 2);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {}
		if (!s.joinOH(c))
		{
			i.endOfficeHours(c);
			return false;
		}
		try {
			Thread.sleep(59000);
		} catch (InterruptedException e) {}
		return true;
	}
	
	// add a 3rd student to OH with a meeting limit of 2, meaning student will have to wait
	// will return true, meaning student can join OH
	// printed statements will show the 3rd student waiting until the 1st student leaves the meeting
	public static Boolean addStudentToFullOH(Instructor i, Class c, Student s1, Student s2, Student s3)
	{
		System.out.println("TEST: AddStudentToFullOH");
		
		i.startOfficeHours(c, 2, 0.5, "zoom.com", ZoneId.systemDefault(), 2);
		try
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException ie) {}
		
		if (!s1.joinOH(c))
		{
			i.endOfficeHours(c);
			return false;
		}
		try
		{
			Thread.sleep(5000);
		}
		catch (InterruptedException ie) {}
		if (!s2.joinOH(c))
		{
			i.endOfficeHours(c);
			return false;
		}
		try
		{
			Thread.sleep(5000);
		}
		catch (InterruptedException ie) {}
		if (!s3.joinOH(c))
		{
			i.endOfficeHours(c);
			return false;
		}
		try
		{
			Thread.sleep(65000);
		}
		catch(InterruptedException ie) {}
		return true;
	}
	
	// add multiple students to OH at the same time.
	// all students will join the waiting list, some will transfer to the meeting until meeting
	// limit is reached, and the rest will transfer over once their turns are over.
	// will return true, meaning all students joined OH.
	public static Boolean addMultipleStudentsOH(Instructor i, Class c, Student s1, Student s2, Student s3)
	{
		System.out.println("TEST: AddMultipleStudentsOH");
		
		i.startOfficeHours(c, 2, 0.5, "zoom.com", ZoneId.systemDefault(), 2);
		try
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException ie) {}
		if (!s1.joinOH(c))
		{
			i.endOfficeHours(c);
			return false;
		}
		if (!s2.joinOH(c))
		{
			i.endOfficeHours(c);
			return false;
		}
		if (!s3.joinOH(c))
		{
			i.endOfficeHours(c);
			return false;
		}
		try
		{
			Thread.sleep(65000);
		}
		catch(InterruptedException ie) {}
		return true;
	}
	
	public static void main (String [] args)
	{
		
		DatabaseUtil.addNewUser("Instructor 1", "itest@usc.edu", 1);
		Instructor i = DatabaseUtil.getInstructor("itest@usc.edu");
		
		DatabaseUtil.addNewUser("Instructor 2", "itest2@usc.edu", 1);
		Instructor i2 = DatabaseUtil.getInstructor("itest2@usc.edu");
	
		Class c1 = new Class(i, "Class1", "ABC123");
		DatabaseUtil.addNewClass(c1, i.getUserId());
		
		Class c2 = new Class(i2, "ClassA", "999AAA");
		DatabaseUtil.addNewClass(c2, i2.getUserId());
		
		DatabaseUtil.addNewClass(c1, i.getUserId());
		DatabaseUtil.addNewClass(c2, i2.getUserId());
		
		c1 = DatabaseUtil.getClass("ABC123");
		c2 = DatabaseUtil.getClass("999AAA");
		
		DatabaseUtil.addNewUser("Student 1", "s1@usc.edu", 2);
		Student s1 = DatabaseUtil.getStudent("s1@usc.edu");
		s1.enrollClass ("ABC123");
		
		DatabaseUtil.addNewUser("Student 2", "s2@usc.edu", 2);
		Student s2 = DatabaseUtil.getStudent("s2@usc.edu");
		s2.enrollClass ("ABC123");
		
		DatabaseUtil.addNewUser("Student 3", "s3@usc.edu", 2);
		Student s3 = DatabaseUtil.getStudent("s3@usc.edu");
		s3.enrollClass ("ABC123");
		
		DatabaseUtil.addNewUser("Student 4", "s4@usc.edu", 2);
		Student s4 = DatabaseUtil.getStudent("s4@usc.edu");
		s4.enrollClass ("ABC123");
		
		DatabaseUtil.addNewUser("Student 5", "s5@usc.edu", 2);
		Student s5 = DatabaseUtil.getStudent("s5@usc.edu");
		s5.enrollClass ("ABC123");
		
		DatabaseUtil.addNewUser("Student 6", "s6@usc.edu", 2);
		Student s6 = DatabaseUtil.getStudent("s6@usc.edu");
		s6.enrollClass ("ABC123");
		
		DatabaseUtil.addNewUser("Student 7", "s7@usc.edu", 2);
		Student s7 = DatabaseUtil.getStudent("s7@usc.edu");
		s7.enrollClass ("ABC123");

		
		if (instructorCreateOH(i, c1))
		{
			System.out.println("Correct: returns true; instructor can create OH for their class.");
		}
		else
		{
			System.out.println("Incorrect: returns false; instructor should be able to create OH for their class.");
		}
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {}
		
		if (!instructorCreateOHWrongClass(i, c2))
		{
			System.out.println("Correct: returns false; instructor cannot create OH for a class that is not their own.");
		}
		else
		{
			System.out.println("Incorrect: returns true; instructor should not be able to create OH for different class.");
		}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {}
		
		if (!addStudentBeforeStarted(i, c1, s1))
		{
			System.out.println("Correct: returns false; student cannot join OH before open.");
		}
		else
		{
			System.out.println("Incorrect: returns true; student should not be able to join OH before open.");
		}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {}
		
		if (!addStudentAfterEnded(i, c1, s1))
		{
			System.out.println("Correct: returns false; student cannot join OH after closed.");
		}
		else
		{
			System.out.println("Incorrect: returns true; student should not be able to join OH after closed.");
		}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {}
		
		if (addStudentToOH(i, c1, s1))
		{
			System.out.println("Correct: returns true; student can join OH within the constraints.");
		}
		else
		{
			System.out.println("Incorrect: returns false; student should be able to join OH within the constraints.");
		}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {}
		
		if (addStudentToFullOH(i, c1, s2, s3, s4))
		{
			System.out.println("Correct: returns true; student can join waiting list if meeting is full.");
		}
		else
		{
			System.out.println("Incorrect: returns false; student should be able to join OH waiting list if meeting is full.");
		}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {}
		
		if (addMultipleStudentsOH(i, c1, s5, s6, s7))
		{
			System.out.println("Correct: returns true; multiple students joining at the same time within constraints will all be added to OH.");
		}
		else
		{
			System.out.println("Incorrect: returns false; multiple students joining at the same time within constraints should all be added to either waitlist or meeting.");
		}	
		
	}
}
