package chat;

import java.sql.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import models.Credentials;
import models.DatabaseUtil;
import models.Instructor;
import models.Student;
import models.Class;

import java.io.*;

public class chatDB {
	static String db = "jdbc:mysql://localhost:3306/FinalProject?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=America/Los_Angeles";
	static String user = Credentials.user;
	static String pwd = Credentials.pwd;

	// Add new message table to database
	// must get senderId, recipientId, message from the front end
	// do error checking before adding table
	public static void addNewMessage(String message, int senderId, int recipientId) {
		String chatId = senderId + "_" + recipientId;
		String driver = "com.mysql.jdbc.Driver";
		String sql = "INSERT INTO chat (sender_id, recipient_id, chat_id, message, time) "
				+ "VALUES (?, ?, ?, ?, ?)";
		try (Connection conn = DriverManager.getConnection(db, user, pwd);
				PreparedStatement ps = conn.prepareStatement(sql);) {
			//ps.setString(1, null);
			ps.setInt(1, senderId);
			ps.setInt(2, recipientId);
			ps.setString(3, chatId);
			ps.setString(4, message);
			ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void viewMessages(PrintWriter out, int senderId, int recipientId) {
		String chatId1 = senderId + "_" + recipientId;
		String chatId2 = recipientId + "_" + senderId;
		String senderName;
		String driver = "com.mysql.jdbc.Driver";
		String sql = "SELECT * FROM chat WHERE chat_id = ? OR chat_id = ?";

		try (Connection conn = DriverManager.getConnection(db, user, pwd);
				PreparedStatement ps = conn.prepareStatement(sql);) {
			ps.setString(1, chatId1);
			ps.setString(2, chatId2);
			ResultSet rs = ps.executeQuery();

			// get the name of student here
			while (rs.next()) {
				senderName = chatDB.getNameFromId(rs.getInt(2));
				String messages = rs.getString(6) + ": " + senderName + " >> " + rs.getString(5);
				out.println(messages);
			}
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// show students in class that you are currently enrolled in
	// show professors of your classes
	// those are the people you are allowed to message
	public static boolean verifyChat(int senderId, int recipientId) {
		ArrayList<Integer> availableR = getAvailableStudents(senderId);
		ArrayList<Integer> availableS = getAvailableStudents(recipientId);
		if (availableR == null || availableS == null) {
			return false;
		}
		if (availableR.contains(recipientId) && availableS.contains(senderId)) {
			return true;
		}
		return false;
	}

	public static ArrayList<Integer> getAvailableStudents(int senderId) {
		Student s1 = DatabaseUtil.getStudentFromId(senderId);
		Instructor i1 = null;
		ArrayList<Class> classes = new ArrayList<Class>();
		ArrayList<Integer> recipients = new ArrayList<Integer>();
		boolean isStudent = true;
		if (s1 == null) {
			isStudent = false;
			i1 = DatabaseUtil.getInstructorFromId(senderId);
			if (i1 == null) {
				return null;
			}
		}
		if (isStudent) {
			classes = DatabaseUtil.getClassesFromStudent(s1);
		} else {
			classes = DatabaseUtil.getClassesFromInstructor(i1);
		}

		// print out messages here to separate classes?
		for (int i = 0; i < classes.size(); i++) {
			if (classes.get(i).getInstructor().getUserId() !=  senderId) {
				recipients.add(classes.get(i).getInstructor().getUserId());
			}
			ArrayList<Student> students = DatabaseUtil.getStudentsFromClass(classes.get(i).getClassCode());
			for (int j = 0; j < students.size(); j++) {
				if (students.get(j).getUserId() != (senderId)) {
					recipients.add(students.get(j).getUserId());
				}
			}
		}
		return recipients;
	}

	public static String getNameFromId(int senderId) {
		String name = "";
		Student s1 = DatabaseUtil.getStudentFromId(senderId);
		Instructor i1 = null;
		if (s1 == null) {
			i1 = DatabaseUtil.getInstructorFromId(senderId);
			name = i1.getFullName();
		} else {
			name = s1.getFullName();
		}
		return name;
	}
	public static String getMessageFromDB(String message, int senderId, int recipientId) {
		String chatId1 = senderId + "_" + recipientId;
		String chatId2 = recipientId + "_" + senderId;
		String returnThis = null;
		String driver = "com.mysql.jdbc.Driver";
		String sql = "SELECT * FROM chat WHERE message = ? AND (chat_id = ? OR chat_id = ?)";

		try (Connection conn = DriverManager.getConnection(db, user, pwd);
				PreparedStatement ps = conn.prepareStatement(sql);) {
			ps.setString(1, message);
			ps.setString(2, chatId1);
			ps.setString(3, chatId2);
			ResultSet rs = ps.executeQuery();

			// get the name of student here
			while (rs.next()) {
				returnThis = rs.getString(5);
				
			}
			conn.close();
			return returnThis;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnThis;
	}
	
	public static void loadChat(PrintWriter out, int senderId, int recipientId) {
		out.println("<textarea  readonly=\"readonly\"   name=\"txtMessage\" rows=\"100\" cols=\"100\">");
		try {
			chatDB.viewMessages(out, senderId, recipientId);
		} finally {
			out.println("</textarea>");
			out.println("<hr>");
			out.println("</form>");
			out.println("</body>");
			out.println("</html>");
		}
	}
	public static ArrayList<String> allMessages(int senderId, int recipientId) {
		String chatId1 = senderId + "_" + recipientId;
		String chatId2 = recipientId + "_" + senderId;
		ArrayList<String> list = new ArrayList<String>();
		String driver = "com.mysql.jdbc.Driver";
		String sql = "SELECT * FROM chat WHERE chat_id = ? OR chat_id = ?";

		try (Connection conn = DriverManager.getConnection(db, user, pwd);
				PreparedStatement ps = conn.prepareStatement(sql);) {
			ps.setString(1, chatId1);
			ps.setString(2, chatId2);
			ResultSet rs = ps.executeQuery();

			// get the name of student here
			while (rs.next()) {
				list.add(rs.getString(5));
			}
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}

