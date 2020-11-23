package Tests;

import java.sql.SQLException;
import java.util.ArrayList;

import Polling.PollDatabaseHandler;
import models.*;


public class PollTest {
	public static void main(String[] args) 
	{
		System.out.println("User Creation");
		//String name, String email, int accountLevel
		DatabaseUtil.addNewUser("1 1", "1", 2);
		Instructor i = DatabaseUtil.getInstructor("1");
		System.out.println("Should be true: " + i.getFullName().equals("1 1"));
		
		DatabaseUtil.addNewUser("2 2", "2", 2);
		Instructor i2 = DatabaseUtil.getInstructor("2");
		System.out.println("Should be true: " + i2.getFullName().equals("2 2"));
		
		
		System.out.println("Congrats! No errors. \n");
		
		System.out.println("Class Creation: Class codes should be random alphanumeric codes of length 6");
		System.out.println("Single Instructor Class Creation");
		i.createNewClass("1");
		models.Class c = i.getClasses().get(0);
		System.out.println(c.getClassCode());
		
		System.out.println("Multiple Instructor Class Creation");
		i2.createNewClass("2");
		models.Class c2 = i2.getClasses().get(0);
		System.out.println(c2.getClassCode());
		
		
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
		
		//-------------newly added part-------------
		//PollCreation Tests
		System.out.println("PollCreation & GetPoll Tests");
		//Creating some responses and needed input parameters
		ArrayList<Response> responseList = new ArrayList<Response>();
		Response yes = new Response("yes");
		Response no = new Response("no");
		responseList.add(yes);
		responseList.add(no);
		ArrayList<Student> studentList = new ArrayList<Student>();
		//Create poll
		Poll mPoll1 = new Poll(1, "Instructor@usc.edu", c.getClassCode(), false, "Are you happy?", 
			responseList, studentList);
		PollDatabaseHandler handler1 = new PollDatabaseHandler(mPoll1);
		try {
			handler1.savePoll();
		}catch(SQLException e) {
			System.out.println("Handler1 could not save poll");
		}
		

		//Getting polls 
		Student s4 = DatabaseUtil.getStudent("1");
		ArrayList<models.Class> classCodes = DatabaseUtil.getClassesFromStudent(s4);
		PollDatabaseHandler dbHandler = new PollDatabaseHandler();
		try {
			for(models.Class class_: classCodes){
				String classCode = class_.getClassCode();
				for (int pollId : dbHandler.getPollIdByClass(classCode)) {
					ArrayList<String> resultList = dbHandler.getPollResults(pollId);
					if(!resultList.get(0).equals("yes")){
						System.out.println("First answer choice does not match");
						System.out.println("Got: " + resultList.get(0));
					}
					if(!resultList.get(1).equals("no")){
						System.out.println("Second answer choice does not match");
						System.out.println("Got: " + resultList.get(1));
					}
					
				}
		}
		}catch(SQLException e) {
			System.out.println("Handler1 could not save poll");
		}
		System.out.println("PollCreation & GetPoll Tests is successful!");

		//-------------newly added part-------------
		

	}
}