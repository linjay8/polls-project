package Polling;


import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/PollList")
public class PollList extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		// Variables to help print results
		String userId = request.getParameter("userId");
		int classCode = Integer.parseInt(request.getParameter("classCode"));
		PollDatabaseHandler dbHandler = new PollDatabaseHandler();
		try{
			String title = "Using GET Method to Read Form Data";
			out.println("<html>");
			out.println("<head><title>" + title + "</title></head>");
			out.println("<body>");
			out.println("<h4>Results in java</h4>");
			
			
			// For each poll in class, print answer choice with count
			// Must verify that student has not answered poll
			for (int pollId : dbHandler.getPollIdByClass(classCode)) {
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
				out.print("<input type = \"hidden\" name = \"classCode\" value = " + classCode+ ">");
				out.print("<input type = \"hidden\" name = \"userId\" value = " + userId+ ">");
				
				out.print("<input type = \"submit\" value = \"Answer this poll\" /> ");
				out.print("</form>");
				out.print("</div> <br><br>");
			}
			
	       
			
			// Might want to use a separate form instead of a button?
			out.print("<br><br>");
			out.print("<a href=\"StudentHome.html\">Home</a>");
	
			out.println("</body>");
			out.println("</html>");
		}
		catch(SQLException e){
			e.printStackTrace();
		}

	}
}
