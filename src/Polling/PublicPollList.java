package Polling;
import models.DatabaseUtil;
import models.Student;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/PublicPollList")
public class PublicPollList extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		// Variables to help print results
//		String studentId = request.getParameter("studentId");
		PollDatabaseHandler dbHandler = new PollDatabaseHandler();
		try{			
			String title = "Public Polls";
			out.println("<html>");
			out.println("<head><title>" + title + "</title></head>");
			out.println("<body>");

			
			// Determine next page
			String email = (String)request.getSession().getAttribute("email"); 
			String nextPage;
			
			int accLevel = DatabaseUtil.getAccountLevel(email);
			System.out.println(email + " " + accLevel);
			
			
			if (accLevel == 2) {
				nextPage = "InstructorHome.jsp";
			}
			else if (accLevel == 1) {
				nextPage = "StudentHome.jsp";
			}
			else {
				out.println("<h1> Welcome, Guest.</h1>");
				nextPage = "GuestHome";
				
			}
			
			out.println("<h2>Available Public Polls</h2>");
			
			
			// For each poll in class, print answer choice with count
			// Must verify that student has not answered poll
			
			for (int pollId : dbHandler.getPublicPollIdList()) {
				ArrayList<String> studentList = dbHandler.getStudentList(pollId);
				
				if (!alreadyAnswered(studentList, email)) {
					ArrayList<String> resultList = dbHandler.getPollResults(pollId);
					
					out.print("<div>");
					out.print("<form name=" + pollId + " action=\"PollSubmission\" method=GET>" );
					
					out.print("\n" + "  <li><b>Question</b>: " 
							+ dbHandler.getQuestion(pollId) +  "\n" );
					 for (int i = 0 ; i < resultList.size(); i++) {
				            out.print(" <li>" + resultList.get(i) +" \n");
				     }
					out.print( "</ul>");
					out.print( "<br></br>");
	
					// Pass hidden variables to next page
					out.print("<input type = \"hidden\" name = \"pollId\" id = \"pollId\" value = " + pollId + ">");
					
					out.print("<input type = \"submit\" value = \"Answer this poll\" /> ");
					out.print("</form>");
					out.print("</div> <br><br>");
				}
			}
			
	       
			
			// Might want to use a separate form instead of a button?
			out.print("<br><br>");
			out.print("<a href=" + nextPage  +">Home</a>");
	
			out.println("</body>");
			out.println("</html>");
		}
		catch(SQLException e){
			e.printStackTrace();
		}

	}
	
	
	private boolean alreadyAnswered(ArrayList<String> studentList, String student) {
		for (String s : studentList) {
			if (student.compareTo(s) == 0)
				return true;
		}
		return false;
	}
}
