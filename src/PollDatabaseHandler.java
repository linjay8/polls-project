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
	
	public PollDatabaseHandler(Poll poll) {
		this.poll = poll;
	}
	
	public void saveResponses(Connection connection) throws SQLException{
		// Helper called from savePoll()
		for (Response r : poll.getOptions()) {
			String sql = "INSERT INTO Responses (responseID, response, numVotes, questionID) VALUES (?, ?, ?, ?);";	

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
		String jdbcURL = "jdbc:mysql://localhost:3306/userinformation";
		String dbUser = "root";
		String dbPassword = "root";
		
		Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
		String sql = "INSERT INTO Poll (id, count, isPublic, question, instructorID, responseListID) VALUES (?, ?, ?, ?, ?, ?);";	
		
		PreparedStatement PS = connection.prepareStatement(sql);
		PS.setInt(1, poll.getPollID());
		PS.setInt(2, 0);
		PS.setBoolean(3, poll.isPublic());
		PS.setString(4,  poll.getQuestion());
		this.saveResponses(connection);

		// Add 5 and 6?
		PS.executeUpdate();
		connection.close();
	}
	
//	public boolean verifyUser() throws SQLException {
//		String jdbcURL = "jdbc:mysql://localhost:3306/userinformation";
//		String dbUser = "root";
//		String dbPassword = "root";
//		
//		//Class.forName("com.mysql.jdbc.Driver");
//		Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
//		String sql = "SELECT * FROM users WHERE username = ? and password = ?";
//			
//		PreparedStatement PS = connection.prepareStatement(sql);
////		PS.setString(1, username);
////		PS.setString(2, password);
//			
//		ResultSet result = PS.executeQuery();
//		if (result.next()) {
//			return true;
//		}
//		return false;
//			
//		//connection.close();
//	}
	
}