package Tests;

import models.Class;
import models.DatabaseUtil;
import models.Instructor;
import models.Student;

public class EnrollmentTest {
	public static void main(String[] args) 
	{
		System.out.println("User Creation");
		DatabaseUtil.addNewUser("1 1", "1", 2);
		Instructor i = DatabaseUtil.getInstructor("1");
		System.out.println("Should be true: " + i.getFullName().equals("1 1"));
		
		DatabaseUtil.addNewUser("2 2", "2", 2);
		Instructor i2 = DatabaseUtil.getInstructor("2");
		System.out.println("Should be true: " + i2.getFullName().equals("2 2"));
		
		DatabaseUtil.addNewUser("3 3", "3", 2);
		Instructor i3 = DatabaseUtil.getInstructor("3");
		System.out.println("Should be true: " + i3.getFullName().equals("3 3"));
		
		System.out.println("Congrats! No errors. \n");
		
		System.out.println("Class Creation: Class codes should be random alphanumeric codes of length 6");
		System.out.println("Single Instructor Class Creation");
		i.createNewClass("1");
		Class c = i.getClasses().get(0);
		System.out.println(c.getClassCode());
		
		System.out.println("Multiple Instructor Class Creation");
		i2.createNewClass("2");
		Class c2 = i2.getClasses().get(0);
		System.out.println(c2.getClassCode());
		
		i3.createNewClass("3");
		Class c3 = i3.getClasses().get(0);
		System.out.println(c3.getClassCode());
		
		System.out.println("Congrats! No errors. \n");
		
		System.out.println("Enrollment");
		
		System.out.println("Single Student Enrollment");
		DatabaseUtil.addNewUser("4 4", "4", 1);
		Student s = DatabaseUtil.getStudent("4");
		s.enrollClass(c.getClassCode());


		System.out.println("Multiple Student Enrollment");
		DatabaseUtil.addNewUser("5 5", "5", 1);
		Student s2 = DatabaseUtil.getStudent("5");
		s2.enrollClass(c.getClassCode());
		
		DatabaseUtil.addNewUser("6 6", "6", 1);
		Student s3 = DatabaseUtil.getStudent("6");
		s3.enrollClass(c.getClassCode());
		
		DatabaseUtil.addNewUser("7 7", "7", 1);
		Student s4 = DatabaseUtil.getStudent("7");
		s4.enrollClass(c.getClassCode());
		System.out.println("Congrats! No errors. \n");
		

	}
}
