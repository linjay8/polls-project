package Polling;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.DatabaseUtil;
import models.Poll;
import models.Response;

public class PollDatabaseHandler {
	Poll poll;
	int classCode;
	
	String jdbcURL = "jdbc:mysql://localhost:3306/FinalProject";
	String dbUser = "root";
	String dbPassword = "root";
	
	
	public PollDatabaseHandler(Poll poll) {
		this.poll = poll;
	}
	
	public PollDatabaseHandler() {
	
	}
	
	public void saveResponses(Connection connection) throws SQLException{
		// Helper called from savePoll()
		for (Response r : poll.getOptions()) {
			String sql = "INSERT INTO Response (responseID, response, numVotes, questionID) VALUES (?, ?, ?, ?);";	

			PreparedStatement PS = connection.prepareStatement(sql);
			PS.setInt(1, r.getResponseID());
			PS.setString(2, r.getText());
			PS.setInt(3, 0);
			PS.setInt(4, poll.getPollID());
			
			// Add 5 and 6?
			PS.executeUpdate();
		}
	}
	
	public void savePoll() throws SQLException {
	
		Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
		String sql = "INSERT INTO Poll (questionID, count, isPublic, question, classCode, instructorId) VALUES (?, ?, ?, ?, ?, ?);";	
		// , instructorID, responseListID
		PreparedStatement PS = connection.prepareStatement(sql);
		PS.setInt(1, poll.getPollID());
		PS.setInt(2, 0);
		PS.setBoolean(3, poll.isPublic());
		PS.setString(4,  poll.getQuestion());

		if (poll.getClassCode().equals(""))
			PS.setString(5, null);
		else
			PS.setString(5, poll.getClassCode());
		
		PS.setString(6, poll.getEmail());
			
		this.saveResponses(connection);

		// Add 5 and 6?
		PS.executeUpdate();
		connection.close();
	}
	
	
	// Functions to get poll information by PollId
	public ArrayList<String> getPollResults(int pollId) throws SQLException{
		ArrayList<String> output = new ArrayList<String>();

		
		Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
		String sql = "Select response from Response "
				+ "Inner join Poll on Poll.questionID = Response.questionID "
				+ "where Poll.questionID=" + pollId + ";";	
		PreparedStatement PS = connection.prepareStatement(sql);
		ResultSet rs = PS.executeQuery();
		
		while (rs.next()) {
            output.add(rs.getString(1));
        }
		

		connection.close();
		
		return output;
	}
	
	public ArrayList<Integer> getPollResultCount(int pollId) throws SQLException{
		ArrayList<Integer> output = new ArrayList<Integer>();
		

		Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
		String sql = "Select count from Response "
				+ "Inner join Poll on Poll.questionID = Response.questionID "
				+ "where Poll.questionID=" + pollId + ";";	
		PreparedStatement PS = connection.prepareStatement(sql);
		ResultSet rs = PS.executeQuery();
		
		while (rs.next()) {
            output.add(rs.getInt(1));
        }

		//this.saveResponse(connection);
		// PS.executeUpdate();
		connection.close();
		
		return output;

	}
	
	public String getQuestion(int pollId) throws SQLException {		

		Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
		String sql = "Select question from Poll Where questionID=" + pollId + ";";	
		PreparedStatement PS = connection.prepareStatement(sql);
		ResultSet rs = PS.executeQuery();
		rs.next();
		String output = rs.getString(1);
		
		connection.close();
		
		return output;
	}
	
	
	// Functions to get all Poll ID's
		public ArrayList<Integer> getPollIdList() throws SQLException{
			ArrayList<Integer> output = new ArrayList<Integer>();

			
			Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
			String sql = "SELECT questionID FROM Poll Where isPublic=true;";
			PreparedStatement PS = connection.prepareStatement(sql);
			ResultSet rs = PS.executeQuery();
			
			while (rs.next()) {
	            output.add(rs.getInt(1));
	        }

			connection.close();
			
			return output;
		}
		
		
		
	
	// Functions to get poll information by classId
	public ArrayList<Integer> getPollIdByClass(int classId) throws SQLException {
		ArrayList<Integer> output = new ArrayList<Integer>();
		
		
		Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
		String sql = "Select questionID from Poll where Poll.classCode=" + classId + ";";	
		PreparedStatement PS = connection.prepareStatement(sql);
		ResultSet rs = PS.executeQuery();
		
		
		while (rs.next()) {
            output.add(rs.getInt(1));
        }
		
		connection.close();
		
		return output;
		
	}
	
	
	
	public void addVote(String email, int pollId, int responseId) throws SQLException{
		
		Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
		String sql = "INSERT INTO UserResponse (studentID, questionID, responseID) VALUES (?,?,?); ";
		
		
		PreparedStatement PS = connection.prepareStatement(sql);
		
		PS.setInt(1,DatabaseUtil.getUserId(email));
		PS.setInt(2, pollId);
		PS.setInt(3, responseId);
		PS.executeUpdate();
		
		String sql2 = "SELECT numVotes FROM Response WHERE responseID = " + responseId + ";";	
		PreparedStatement PS2 = connection.prepareStatement(sql2);
		ResultSet rs2 = PS2.executeQuery();
		
		rs2.next();
		int count = rs2.getInt(1);
		count++;
		
		String sql3 = "UPDATE Response SET numVotes=" + count
				+ " WHERE responseID = " + responseId + ";";	
		PreparedStatement PS3 = connection.prepareStatement(sql3);
//		ResultSet rs3 = PS3.executeQuery();
		PS3.executeUpdate();
		
		connection.close();
		

	}
	
	
	public int getResponseID(String _response, int pollId) throws SQLException {
		System.out.println("TEST response=" + _response);
		System.out.println("TEST pollId=" + pollId);
		Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
		String sql = "SELECT responseID FROM Response WHERE response='" + _response + "' AND questionID= " + pollId + ";";	
		PreparedStatement PS = connection.prepareStatement(sql);
		ResultSet rs = PS.executeQuery();
		
		rs.next();
		int output = rs.getInt(1);
		
		connection.close();
		
		return output;
	}
	
	public ArrayList<Integer> getStudentList(int pollId) throws SQLException{
		ArrayList<Integer> output = new ArrayList<Integer>();

		Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
		String sql = "SELECT studentID FROM UserResponse WHERE questionID=" + pollId + ";";
		PreparedStatement PS = connection.prepareStatement(sql);
		ResultSet rs = PS.executeQuery();
		
		while (rs.next()) {
            output.add(rs.getInt(1));
        }

		connection.close();
		
		return output;
		
	}
	
	
	
	
}