package chat;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import models.Class;
import models.DatabaseUtil;
import models.Instructor;
import models.Student;
/*
 * THIS ONLY WORKS ON COMPLETELY EMPTY DATABASE
 */

@TestMethodOrder(OrderAnnotation.class)
class JUnitChat {

	 @Test //display available chats to start
	 @Order(1)    
	 public void startChat() {
		   DatabaseUtil.addNewUser("Vincente Mai", "email_1_2", 1);
		   DatabaseUtil.addNewUser("Chuck Norris", "email_1_3", 1);
		   DatabaseUtil.addNewUser("Bruce Lee", "email_1_4", 2);
		   DatabaseUtil.addNewUser("Morgan Freeman", "email_1_5", 2);
		   Student s1 = DatabaseUtil.getStudent("email_1_2");
		   Student s2 = DatabaseUtil.getStudent("email_1_3");
		   Instructor i1 = DatabaseUtil.getInstructor("email_1_4");
		   Instructor i2 = DatabaseUtil.getInstructor("email_1_5");
		   
		   int s1ID = s1.getUserId();
		   int s2ID = s2.getUserId();
		   int i1ID = i1.getUserId();
		   int i2ID = i2.getUserId();
		   
		   i1.createNewClass("CSCI201LL");
		   i2.createNewClass("CSCI104LL");
		   
		   Class c = i1.getClasses().get(0);
		   Class c2 = i2.getClasses().get(0);
		   s1.enrollClass(c.getClassCode());
		   s1.enrollClass(c2.getClassCode());
		   s2.enrollClass(c2.getClassCode());
		   
		   ArrayList<Integer> list1 = new ArrayList<Integer>(Arrays.asList(i1ID, i2ID, s2ID));
		   ArrayList<Integer> list2 = new ArrayList<Integer>(Arrays.asList(i2ID, s1ID));
		   ArrayList<Integer> list3 = new ArrayList<Integer>(Arrays.asList(s1ID));
		   ArrayList<Integer> list4 = new ArrayList<Integer>(Arrays.asList(s1ID, s2ID));
		   ArrayList<Integer> list5 = chatDB.getAvailableStudents(s1ID);
		   ArrayList<Integer> list6 = chatDB.getAvailableStudents(s2ID);
		   ArrayList<Integer> list7 = chatDB.getAvailableStudents(i1ID);
		   ArrayList<Integer> list8 = chatDB.getAvailableStudents(i2ID);
		    
		   assertEquals(true, list1.equals(list5)); 
		   assertEquals(true, list2.equals(list6)); 
		   assertEquals(true, list3.equals(list7)); 
		   assertEquals(true, list4.equals(list8)); 
	   }
	   
	   @Test //checks if messages are sent correctly
	   @Order(4)  
	   public void sendMessage() {
		   //stress test
		   for (int i = 1; i < 100; i++) {
			   for (int j = 2; j < 5; j++) {
				   chatDB.addNewMessage("test" + 1 +"_" + j, 1, j);
				   assertEquals("test" + 1 +"_" + j, chatDB.getMessageFromDB("test" + 1 +"_" + j, 1, j));
			   }
		   }
	   }
	   
	   //checks if students/instructors are authorized to send messages to one another
	   @Test
	   @Order(3)  
	   public void validateUsers() {
		   int s1ID = DatabaseUtil.getStudent("email_1_2").getUserId();
		   int s2ID = DatabaseUtil.getStudent("email_1_3").getUserId();
		   int i1ID = DatabaseUtil.getInstructor("email_1_4").getUserId();
		   int i2ID = DatabaseUtil.getInstructor("email_1_5").getUserId();
		   
		   assertEquals(true, chatDB.verifyChat(s1ID, i1ID)); //student is in the class
		   assertEquals(true, chatDB.verifyChat(i1ID, s1ID)); //instructor can message student
		   assertEquals(false, chatDB.verifyChat(s2ID, i1ID)); //student not in the class
		   assertEquals(false, chatDB.verifyChat(i1ID, i2ID)); //instructors not in the same class
		   assertEquals(true, chatDB.verifyChat(s1ID, s2ID)); //both students are in different classes
		   assertEquals(true, chatDB.verifyChat(i2ID, s2ID)); //instructor cannot message this student
		   assertEquals(false, chatDB.verifyChat(9999, 8888)); //nonexistant users
		   assertEquals(false, chatDB.verifyChat(s1ID, 9999)); //1 real 1 fake
	   }
	   
	   //checks if messages are displayed in the correct order
	   @Test
	   @Order(2)  
	   public void displayMessages() {
		   DatabaseUtil.addNewUser("student 12", "email12", 1);
		   DatabaseUtil.addNewUser("instructor 12", "email13", 2);
		   Student s1 = DatabaseUtil.getStudent("email12");
		   Instructor i1 = DatabaseUtil.getInstructor("email13");
		   i1.createNewClass("CSCI270LL");
		   Class c = i1.getClasses().get(0);
		   s1.enrollClass(c.getClassCode());
		   int s1ID = s1.getUserId();
		   int i1ID = i1.getUserId();
		   ArrayList<String> list = new ArrayList<String>();
		   for (int i = 1; i < 5; i++) {
			   chatDB.addNewMessage("test" + i, s1ID, i1ID);
			   list.add("test"+i);
		   }
		   assertEquals(true, list.equals(chatDB.allMessages(s1ID, i1ID)));
	   }
}
