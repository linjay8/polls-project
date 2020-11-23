package OfficeHours;
import java.util.*;
import java.time.*;
import models.*;
import models.Class;

public class OHTest {
	
	/* NOTES: 
	 * 
	 * MAKE SURE TO RUN EACH TEST ONE AT A TIME - MULTITHREADING FEATURES GET MESSED UP
	 * 	IF YOU TRY TO RUN MULTIPLE DUE TO THE TIMING OF WHEN ONE ENDS AND THE NEXT STARTS
	 * 	(just comment out the other tests while you run one)
	 * 
	 * If you repeat a test, make sure you clear the database.
	 *
	 * If you do not run ClientThread.java when the code directs you to, after a small period,
	 * 	your spot will be given to the next student.
	 * 
	 * Notice that the time difference between Office Hours opening and closing is equivalent
	 * 	to the length of the meeting. Notice that the time difference between a student startng
	 * 	and ending their turn is equivalent to the timeslot inputted by the instructor.
	 * 
	 * Instructor.startOfficeHours(Class, meetinglimit, timeslot, link, starttime, minuteslong)
	 * 
	 */
	
	// try make an instructor create OH for one of their classes.
	// will return true, meaning the OH has been created.
	public static Boolean instructorCreateOH()
	{
		System.out.println("TEST: InstructorCreateOH");
		
		DatabaseUtil.addNewUser("Instructor 1", "itest@usc.edu", 1);
		Instructor i = DatabaseUtil.getInstructor("itest@usc.edu");
		
		Class c1 = new Class(i, "Class1", "ABC123");
		DatabaseUtil.addNewClass(c1, i.getUserId());
		c1 = DatabaseUtil.getClass("ABC123");
		
		if (i.startOfficeHours(c1, 2, 2, "zoom.com", ZoneId.systemDefault(), 1))
		{
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {}
			return true;
		}
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {}
		return false;
	}
	
	// try make an instructor create OH for a class that is not theirs.
	// will return false, meaning the OH could not be created.
	public static Boolean instructorCreateOHWrongClass()
	{
		System.out.println("TEST: InstructorCreateOHWrongClass");
		
		DatabaseUtil.addNewUser("Instructor 2", "itest2@usc.edu", 1);
		Instructor i2 = DatabaseUtil.getInstructor("itest2@usc.edu");
		
		DatabaseUtil.addNewUser("Instructor Other", "itestother@usc.edu", 1);
		Instructor iOther = DatabaseUtil.getInstructor("itestother@usc.edu");
		
		Class wrong = new Class(iOther, "ClassWrong", "XXXXXX");
		DatabaseUtil.addNewClass(wrong, iOther.getUserId());
		wrong = DatabaseUtil.getClass("XXXXXX");
		
		if (i2.startOfficeHours(wrong, 2, 2, "zoom.com", ZoneId.systemDefault(), 1))
		{
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {}
			return true;
		}
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {}
		return false;
	}
	
	// try to add a student to OH before the instructor has created it.
	// will return false, meaning student is unable to join OH.
	public static Boolean addStudentBeforeStarted()
	{
		System.out.println("TEST: AddStudentBeforeStarted");
		
		DatabaseUtil.addNewUser("Instructor 3", "itest3@usc.edu", 1);
		Instructor i3 = DatabaseUtil.getInstructor("itest3@usc.edu");
		
		Class c3 = new Class(i3, "ClassB", "888BBB");
		DatabaseUtil.addNewClass(c3, i3.getUserId());
		c3 = DatabaseUtil.getClass("888BBB");
		
		DatabaseUtil.addNewUser("Student 3", "s3@usc.edu", 2);
		Student s3 = DatabaseUtil.getStudent("s3@usc.edu");
		s3.enrollClass ("888BBB");
		
		if (!s3.joinOH(c3))
		{
			return false;
		}
		return true;
	}

	// try to add a student to OH after OH has ended.
	// will return false, meaning student is unable to join OH.
	public static Boolean addStudentAfterEnded()
	{
		System.out.println("TEST: AddStudentAfterEnded");
		
		DatabaseUtil.addNewUser("Instructor 4", "itest4@usc.edu", 1);
		Instructor i4 = DatabaseUtil.getInstructor("itest4@usc.edu");
		
		Class c4 = new Class(i4, "ClassC", "777CCC");
		DatabaseUtil.addNewClass(c4, i4.getUserId());
		c4 = DatabaseUtil.getClass("777CCC");
		
		DatabaseUtil.addNewUser("Student 4", "s4@usc.edu", 2);
		Student s4 = DatabaseUtil.getStudent("s4@usc.edu");
		s4.enrollClass ("777CCC");
		
		
		i4.startOfficeHours(c4, 4, 0.5, "zoom.com", ZoneId.systemDefault(), 1);
		try
		{
			Thread.sleep(70000);
		}
		catch (InterruptedException ie) {}
		
		if (!s4.joinOH(c4))
		{
			return false;
		}
		return true;
	}
	
	// try to add a student to OH of a class they are not enrolled in
	// will return false, meaning the student is unable to join OH
	public static Boolean addStudentWrongClass()
	{
		System.out.println("TEST: AddStudentWrongClass");
		
		DatabaseUtil.addNewUser("Instructor 5", "itest5@usc.edu", 1);
		Instructor i5 = DatabaseUtil.getInstructor("itest5@usc.edu");
		
		Class c5 = new Class(i5, "ClassD", "666DDD");
		DatabaseUtil.addNewClass(c5, i5.getUserId());
		c5 = DatabaseUtil.getClass("666DDD");
		
		Class other = new Class(i5, "Other", "OOOOOO");
		DatabaseUtil.addNewClass(other, i5.getUserId());
		other = DatabaseUtil.getClass("OOOOOO");
		
		DatabaseUtil.addNewUser("Student 5", "s5@usc.edu", 2);
		Student s5 = DatabaseUtil.getStudent("s5@usc.edu");
		s5.enrollClass ("666DDD");
		
		i5.startOfficeHours(other, 4, 0.5, "zoom.com", ZoneId.systemDefault(), 1);
		try
		{
			Thread.sleep(70000);
		}
		catch (InterruptedException ie) {}
		
		if (!s5.joinOH(other))
		{
			return false;
		}
		return true;
	}
	
	// try to add a student to OH at a valid time.
	// will return true, meaning student has joined OH.
	// printed statements will show the 1st student joins the waiting room and immediately
	// transfers to meeting room
	public static Boolean addStudentToOH()
	{
		System.out.println("TEST: AddStudentToOH");
		
		DatabaseUtil.addNewUser("Instructor 6", "itest6@usc.edu", 1);
		Instructor i6 = DatabaseUtil.getInstructor("itest6@usc.edu");
		
		Class c6 = new Class(i6, "ClassD", "555EEE");
		DatabaseUtil.addNewClass(c6, i6.getUserId());
		c6 = DatabaseUtil.getClass("555EEE");
		
		DatabaseUtil.addNewUser("Student 6", "s6@usc.edu", 2);
		Student s6 = DatabaseUtil.getStudent("s6@usc.edu");
		s6.enrollClass ("555EEE");
		
		i6.startOfficeHours(c6, 4, 0.5, "zoom.com", ZoneId.systemDefault(), 2);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {}
		if (!s6.joinOH(c6))
		{
			return false;
		}
		try {
			Thread.sleep(120000);
		} catch (InterruptedException e) {}
		return true;
	}
	
	// add a 3rd student to OH with a meeting limit of 2, meaning student will have to wait
	// will return true, meaning student can join OH
	// printed statements will show the 3rd student waiting until the 1st student leaves the meeting
	public static Boolean addStudentToFullOH()
	{
		System.out.println("TEST: AddStudentToFullOH");
		
		DatabaseUtil.addNewUser("Instructor 7", "itest7@usc.edu", 1);
		Instructor i7 = DatabaseUtil.getInstructor("itest7@usc.edu");
		
		Class c7 = new Class(i7, "ClassD", "444FFF");
		DatabaseUtil.addNewClass(c7, i7.getUserId());
		c7 = DatabaseUtil.getClass("444FFF");
		
		DatabaseUtil.addNewUser("Student 7", "s7@usc.edu", 2);
		Student s7 = DatabaseUtil.getStudent("s7@usc.edu");
		s7.enrollClass ("444FFF");
		
		DatabaseUtil.addNewUser("Student 8", "s8@usc.edu", 2);
		Student s8 = DatabaseUtil.getStudent("s8@usc.edu");
		s8.enrollClass ("444FFF");
		
		DatabaseUtil.addNewUser("Student 9", "s9@usc.edu", 2);
		Student s9 = DatabaseUtil.getStudent("s9@usc.edu");
		s9.enrollClass ("444FFF");
		
		i7.startOfficeHours(c7, 2, 0.5, "zoom.com", ZoneId.systemDefault(), 2);
		try
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException ie) {}
		
		if (!s7.joinOH(c7))
		{
			return false;
		}
		try
		{
			Thread.sleep(5000);
		}
		catch (InterruptedException ie) {}
		if (!s8.joinOH(c7))
		{
			return false;
		}
		try
		{
			Thread.sleep(5000);
		}
		catch (InterruptedException ie) {}
		if (!s9.joinOH(c7))
		{
			return false;
		}
		try
		{
			Thread.sleep(120000);
		}
		catch(InterruptedException ie) {}
		return true;
	}
	
	// add multiple students to OH at the same time.
	// all students will join the waiting list, some will transfer to the meeting until meeting
	// limit is reached, and the rest will transfer over once their turns are over.
	// will return true, meaning all students joined OH.
	public static Boolean addMultipleStudentsOH()
	{
		System.out.println("TEST: AddMultipleStudentsOH");
		
		DatabaseUtil.addNewUser("Instructor 8", "itest8@usc.edu", 1);
		Instructor i8 = DatabaseUtil.getInstructor("itest8@usc.edu");
		
		Class c8 = new Class(i8, "ClassD", "555GGG");
		DatabaseUtil.addNewClass(c8, i8.getUserId());
		c8 = DatabaseUtil.getClass("555GGG");
		
		DatabaseUtil.addNewUser("Student 10", "s10@usc.edu", 2);
		Student s10 = DatabaseUtil.getStudent("s10@usc.edu");
		s10.enrollClass ("555GGG");
		
		DatabaseUtil.addNewUser("Student 11", "s11@usc.edu", 2);
		Student s11 = DatabaseUtil.getStudent("s11@usc.edu");
		s11.enrollClass ("555GGG");
		
		DatabaseUtil.addNewUser("Student 12", "s12@usc.edu", 2);
		Student s12 = DatabaseUtil.getStudent("s12@usc.edu");
		s12.enrollClass ("555GGG");
		
		i8.startOfficeHours(c8, 2, 0.5, "zoom.com", ZoneId.systemDefault(), 2);
		try
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException ie) {}
		if (!s10.joinOH(c8))
		{
			return false;
		}
		if (!s11.joinOH(c8))
		{
			return false;
		}
		if (!s12.joinOH(c8))
		{
			return false;
		}
		try
		{
			Thread.sleep(120000);
		}
		catch(InterruptedException ie) {}
		return true;
	}
	
	public static Boolean stressTestOHJoins()
	{
		System.out.println("TEST: StressTestOHJoins");
		
		DatabaseUtil.addNewUser("Instructor 10", "itest10@usc.edu", 1);
		Instructor i10 = DatabaseUtil.getInstructor("itest10@usc.edu");
		
		Class c10 = new Class(i10, "Class10", "101010");
		DatabaseUtil.addNewClass(c10, i10.getUserId());
		c10 = DatabaseUtil.getClass("101010");
		
		DatabaseUtil.addNewUser("Student 15", "s15@usc.edu", 2);
		Student s15 = DatabaseUtil.getStudent("s15@usc.edu");
		s15.enrollClass ("101010");
		
		DatabaseUtil.addNewUser("Student 16", "s16@usc.edu", 2);
		Student s16 = DatabaseUtil.getStudent("s16@usc.edu");
		s16.enrollClass ("101010");
		
		DatabaseUtil.addNewUser("Student 17", "s17@usc.edu", 2);
		Student s17 = DatabaseUtil.getStudent("s17@usc.edu");
		s17.enrollClass ("101010");
		
		DatabaseUtil.addNewUser("Student 18", "s18@usc.edu", 2);
		Student s18 = DatabaseUtil.getStudent("s18@usc.edu");
		s18.enrollClass ("101010");
		
		DatabaseUtil.addNewUser("Student 19", "s19@usc.edu", 2);
		Student s19 = DatabaseUtil.getStudent("s19@usc.edu");
		s19.enrollClass ("101010");
		
		DatabaseUtil.addNewUser("Student 20", "s20@usc.edu", 2);
		Student s20 = DatabaseUtil.getStudent("s20@usc.edu");
		s20.enrollClass ("101010");
		
		i10.startOfficeHours(c10, 2, 0.5, "zoom.com", ZoneId.systemDefault(), 5);
		try
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException ie) {}
		
		if (!s15.joinOH(c10))
		{
			return false;
		}
		
		try
		{
			Thread.sleep(15000);
		}
		catch (InterruptedException ie) {}
		
		if (!s16.joinOH(c10))
		{
			return false;
		}
		if (!s17.joinOH(c10))
		{
			return false;
		}
		
		try
		{
			Thread.sleep(20000);
		}
		catch (InterruptedException ie) {}
		
		if (!s18.joinOH(c10))
		{
			return false;
		}
		
		try
		{
			Thread.sleep(7000);
		}
		catch (InterruptedException ie) {}
		
		if (!s19.joinOH(c10))
		{
			return false;
		}
		
		try
		{
			Thread.sleep(15000);
		}
		catch (InterruptedException ie) {}
		
		if (!s20.joinOH(c10))
		{
			return false;
		}
		try
		{
			Thread.sleep(250000);
		}
		catch(InterruptedException ie) {}
		return true;
	}
	
	public static void main (String [] args)
	{
		// TEST 1
		if (instructorCreateOH())
		{
			System.out.println("Correct: returns true; instructor can create OH for their class.");
		}
		else
		{
			System.out.println("Incorrect: returns false; instructor should be able to create OH for their class.");
		}
		
		// TEST 2
		if (instructorCreateOHWrongClass())
		{
			System.out.println("Incorrect: returns true; instructor should not be able to create OH for different class.");
		}
		else
		{
			System.out.println("Correct: returns false; instructor cannot create OH for a class that is not their own.");
		}
		
		// TEST 3
		if (addStudentBeforeStarted())
		{
			System.out.println("Incorrect: returns true; student should not be able to join OH before open.");
		}
		else
		{
			System.out.println("Correct: returns false; student cannot join OH before open.");
		}
		
		// TEST 4
		if (addStudentAfterEnded())
		{
			System.out.println("Incorrect: returns true; student should not be able to join OH after closed.");
		}
		else
		{
			System.out.println("Correct: returns false; student cannot join OH after closed.");
		}
		
		// TEST 5
		if (addStudentWrongClass()) 
		{
			System.out.println("Incorrect: returns true; student should not be able to join OH if they are not enrolled in class.");
		}
		else
		{
			System.out.println("Correct: returns false; student cannot join OH if they are not enrolled in class.");
		}
		
		// TEST 6
		if (addStudentToOH())
		{
			System.out.println("Correct: returns true; student can join OH within the constraints.");
		}
		else
		{
			System.out.println("Incorrect: returns false; student should be able to join OH within the constraints.");
		}
		
		// TEST 7
		if (addStudentToFullOH())
		{
			System.out.println("Correct: returns true; student can join waiting list if meeting is full.");
		}
		else
		{
			System.out.println("Incorrect: returns false; student should be able to join OH waiting list if meeting is full.");
		}
		
		// TEST 8
		if (addMultipleStudentsOH())
		{
			System.out.println("Correct: returns true; multiple students joining at the same time within constraints will all be added to OH.");
		}
		else
		{
			System.out.println("Incorrect: returns false; multiple students joining at the same time within constraints should all be added to either waitlist or meeting.");
		}
		
		// TEST 9
		if (stressTestOHJoins())
		{
			System.out.println("Correct: returns true; students joining within constraints will all be added to OH.");
		}
		else
		{
			System.out.println("Incorrect: returns false; students joining within constraints should all be added to either waitlist or meeting.");
		}
		
	}
}
