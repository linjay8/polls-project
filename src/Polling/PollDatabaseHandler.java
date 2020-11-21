package Polling;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
//		String jdbcURL = "jdbc:mysql://localhost:3306/Polling";
//		String dbUser = "root";
//		String dbPassword = "root";
		
		Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
		String sql = "INSERT INTO Poll (id, count, isPublic, question, classCode) VALUES (?, ?, ?, ?, ?);";	
		// , instructorID, responseListID
		PreparedStatement PS = connection.prepareStatement(sql);
		PS.setInt(1, poll.getPollID());
		PS.setInt(2, 0);
		PS.setBoolean(3, poll.isPublic());
		PS.setString(4,  poll.getQuestion());
		
		System.out.println("TEST " + poll.getPollID() + " "  
					+ poll.isPublic() + " " + poll.getQuestion()
				);

		
		if (poll.getClassCode().equals(""))
			PS.setString(5, null);
		else
			PS.setString(5, poll.getClassCode());
			
		this.saveResponses(connection);

		// Add 5 and 6?
		PS.executeUpdate();
		connection.close();
	}
	
	
	// Functions to get poll information by PollId
	public ArrayList<String> getPollResults(int pollId) throws SQLException{
		ArrayList<String> output = new ArrayList<String>();
		
//		String jdbcURL = "jdbc:mysql://localhost:3306/Polling";
//		String dbUser = "root";
//		String dbPassword = "root";
		
		Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
		String sql = "Select response from Response "
				+ "Inner join Poll on Poll.id = Response.questionID "
				+ "where Poll.id=" + pollId + ";";	
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
		
//		String jdbcURL = "jdbc:mysql://localhost:3306/Polling";
//		String dbUser = "root";
//		String dbPassword = "root";
	
		Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
		String sql = "Select count from Response "
				+ "Inner join Poll on Poll.id = Response.questionID "
				+ "where Poll.id=" + pollId + ";";	
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
//		String jdbcURL = "jdbc:mysql://localhost:3306/Polling";
//		String dbUser = "root";
//		String dbPassword = "root";
		
		Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
		String sql = "Select question from Poll Where id=" + pollId + ";";	
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
			
//			String jdbcURL = "jdbc:mysql://localhost:3306/Polling";
//			String dbUser = "root";
//			String dbPassword = "root";
			
			Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
			String sql = "SELECT id FROM Poll Where isPublic=true;";
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
		
//		String jdbcURL = "jdbc:mysql://localhost:3306/Polling";
//		String dbUser = "root";
//		String dbPassword = "root";
		
		Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
		String sql = "Select id from Poll where Poll.classCode=" + classId + ";";	
		PreparedStatement PS = connection.prepareStatement(sql);
		ResultSet rs = PS.executeQuery();
		
		
		while (rs.next()) {
            output.add(rs.getInt(1));
        }
		
		connection.close();
		
		return output;
		
	}
	
	
}