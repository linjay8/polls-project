import java.sql.*;
import java.util.ArrayList;
import java.io.*;

public class chatDB {
	static String db = "jdbc:mysql://localhost:3306/FinalProject?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=America/Los_Angeles";
	static String user = "root";
	static String pwd = "root";

	// Add new message table to database
	// must get senderId, recipientId, message from the front end
	// do error checking before adding table
	public static void addNewMessage(String message, String senderId, String recipientId) {
		String chatId = senderId + "_" + recipientId;
		String driver = "com.mysql.jdbc.Driver"; 
		String sql = "INSERT INTO chat (id, sender_id, recipient_id, chat_id, message, time) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection conn = DriverManager.getConnection(db, user, pwd);
				PreparedStatement ps = conn.prepareStatement(sql);) {
			ps.setString(1, null);
			ps.setString(2, senderId);
			ps.setString(3, recipientId);
			ps.setString(4, chatId);
			ps.setString(5, message);
			ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void viewMessages(PrintWriter out, String senderId, String recipientId) {
		String chatId1 = senderId + "_" + recipientId;
		String chatId2 = recipientId + "_" + senderId;
		String senderName;
		String driver = "com.mysql.jdbc.Driver";
		String sql = "SELECT * FROM chat WHERE chat_id = ? OR chat_id = ?";

		try (Connection conn = DriverManager.getConnection(db, user, pwd); PreparedStatement ps = conn.prepareStatement(sql);) {
			ps.setString(1, chatId1);
			ps.setString(2, chatId2);
			ResultSet rs = ps.executeQuery();
			
			//get the name of student here
			while (rs.next()) {
				senderName = chatDB.getNameFromId(rs.getString(2));
				String messages = rs.getString(6) + ": " + senderName + " >> " + rs.getString(5);

				out.println(messages);
			}

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//show students in class that you are currently enrolled in
	//show professors of your classes
	//those are the people you are allowed to message
	public static boolean verifyChat(String senderId, String recipientId, PrintWriter out) {
		ArrayList<String> availableR = getAvailableStudents(senderId, out);
		ArrayList<String> availableS = getAvailableStudents(recipientId, out);
		if(availableR == null || availableS == null) {
			return false;
		}
		if (availableR.contains(recipientId) && availableS.contains(senderId)) {
			return true;
		}
		return false;
	}
	
	public static ArrayList<String> getAvailableStudents(String senderId, PrintWriter out) {
		Student s1 = DatabaseUtil.getStudent(senderId);
		Instructor i1 = null;
		ArrayList<Class> classes = new ArrayList<Class>();
		ArrayList<String> recipients = new ArrayList<String>();
		boolean isStudent = true;
		if(s1 == null) {
			isStudent = false;
			i1 = DatabaseUtil.getInstructor(senderId);
			if(i1 == null) {
				return null;
			}
		}
		if (isStudent) {
			classes = DatabaseUtil.getClassesFromStudent(s1);
		} else {
			classes = DatabaseUtil.getClassesFromInstructor(i1);
		}
		
		
		//print out messages here to separate classes?
		for (int i=0; i < classes.size(); i++) {
			if (!classes.get(i).getInstructor().getUserId().equals(senderId)) {
				recipients.add(classes.get(i).getInstructor().getUserId());
			}
			ArrayList<Student> students = DatabaseUtil.getStudentsFromClass(classes.get(i).getClassCode());
			for (int j=0; j < students.size(); j++) {
				if (!students.get(j).getUserId().equals(senderId)) {
					recipients.add(students.get(j).getUserId());
				}
			}
		}
		return recipients;
	}
	
	public static String getNameFromId(String senderId) {
		String name = "";
		Student s1 = DatabaseUtil.getStudent(senderId);
		Instructor i1 = null;
		if(s1 == null) {
			i1 = DatabaseUtil.getInstructor(senderId);
			name = i1.getFullName();
		} else {
			name = s1.getFullName();
		}
		return name;
	}
	

}
