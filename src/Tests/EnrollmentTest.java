package Tests;

import models.Class;
import models.DatabaseUtil;
import models.Instructor;
import models.Student;

public class EnrollmentTest {
	public static void main(String[] args) 
	{
		DatabaseUtil.addNewUser("1 1", "1", 2);
		Instructor i = DatabaseUtil.getInstructor("1");
		System.out.println(i.getFullName().equals("1 1"));
		
		DatabaseUtil.addNewUser("2 2", "2", 2);
		Instructor i2 = DatabaseUtil.getInstructor("2");
		System.out.println(i2.getFullName().equals("2 2"));
		
		DatabaseUtil.addNewUser("3 3", "3", 2);
		Instructor i3 = DatabaseUtil.getInstructor("3");
		System.out.println(i3.getFullName().equals("3 3"));
		
		i.createNewClass("1");
		Class c = i.getClasses().get(0);
		System.out.println(c.getClassCode());
		
		DatabaseUtil.addNewUser("4 4", "4", 1);
		Student s = DatabaseUtil.getStudent("4");
		s.enrollClass(c.getClassCode());


		DatabaseUtil.addNewUser("5 5", "5", 1);
		Student s2 = DatabaseUtil.getStudent("5");
		s2.enrollClass(c.getClassCode());
		
		DatabaseUtil.addNewUser("6 6", "6", 1);
		Student s3 = DatabaseUtil.getStudent("6");
		s3.enrollClass(c.getClassCode());
		
		DatabaseUtil.addNewUser("7 7", "7", 1);
		Student s4 = DatabaseUtil.getStudent("7");
		s4.enrollClass(c.getClassCode());
		

	}
}
