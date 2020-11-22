package Polling;


import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;



@WebServlet("/PollSubmission")
public class PollSubmission extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String title = "Using GET Method to Read Form Data";
		out.println("<html>");
		out.println("<head><title>" + title + "</title></head>");
		out.println("<body>");
		out.println("<h4>Results in java</h4>");

	
		
		int pId = Integer.parseInt(request.getParameter("pollId"));
		String email = (String)request.getSession().getAttribute("email"); 
		String nextPage ;
		if (email != null) {
			nextPage = "StudentHome.jsp";
		}
		else {
			nextPage = "GuestHome";
		}
		
		
		PollDatabaseHandler dbHandler = new PollDatabaseHandler();
		
		// Check if public or private poll
	
		if (request.getParameter("classCode") == null) {
			try{
				for (int pollId : dbHandler.getPollIdList()) {
					if (pId == pollId) {
						ArrayList<String> resultList = dbHandler.getPollResults(pollId);
						out.print("<div>");
						out.print("\n" + "  <li><b>Question</b>: " 
								+ dbHandler.getQuestion(pollId) + "\n" );
						
						 out.println("<form name=\"myForm\" action =\"VerifySubmission\" method = \"GET\" >");
						 for (int i = 0 ; i < resultList.size(); i++) {
							 String answerChoice = resultList.get(i);
							 out.println(answerChoice + " <input type=\"checkbox\" name=\"answer"+ (i+1) + "\" value="+ answerChoice +"/>");
							 out.println("<br></br>");				
					     }
						out.print( "</ul>");
						
						out.print("<input type = \"hidden\" name = \"pollId\" id = \"pollId\" value = " + pollId + ">");
						out.println("<input type = \"submit\" value = \"Submit\" />  ");
							 
							 
						out.println("</form>");
						out.print("</div> <br><br>");
					}
				}
			}catch(SQLException e){e.printStackTrace();}
		}
		else {
			String classCode = request.getParameter("classCode");

			try{
				// For each poll in class, print answer choice with count
				// Must verify that student has not answered this poll yet
				for (int pollId : dbHandler.getPollIdByClass(classCode)) {
					if (pId == pollId) {
						ArrayList<String> resultList = dbHandler.getPollResults(pollId);
			
						out.print("<div>");
						
						out.print("\n" + "  <li><b>Question</b>: " 
								+ dbHandler.getQuestion(pollId) + "\n" );
						
						 out.println("<form name=\"myForm\" action =\"VerifySubmission\" method = \"GET\" >");
						 for (int i = 0 ; i < resultList.size(); i++) {
							 String answerChoice = resultList.get(i);
							 out.println(answerChoice + " <input type=\"checkbox\" name=\"answer"+ (i+1) + "\" value="+ answerChoice +"/>");
							 out.println("<br></br>");				
					     }
						out.print( "</ul>");
						
						out.print("<input type = \"hidden\" name = \"pollId\" id = \"pollId\" value = " + pollId + ">");
						out.print("<input type = \"hidden\" name = \"classCode\" value = " + classCode+ ">");
						out.println("<input type = \"submit\" value = \"Submit\" />  ");
							 
							 
						out.println("</form>");
						out.print("</div> <br><br>");
					}
				}
			}catch(SQLException e){e.printStackTrace();}
			
			
		}
		
		
		// Might want to use a separate form instead of a button?
		out.print("<br><br>");
		out.print("<a href=" + nextPage  +">Home</a>");

		out.println("</body>");
		out.println("</html>");
	}
}
