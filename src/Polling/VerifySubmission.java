package Polling;


import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;



@WebServlet("/VerifySubmission")
public class VerifySubmission extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String email = (String)request.getSession().getAttribute("email"); 
		
		// Get Variables from previous page
		int classCode = Integer.parseInt(request.getParameter("classCode"));
		int pollId = Integer.parseInt(request.getParameter("pollId"));
		String userId = request.getParameter("userId");
		
		String answer1 = request.getParameter("answer1");
		String answer2 = request.getParameter("answer2");
		String answer3 = request.getParameter("answer3");
		String answer4 = request.getParameter("answer4");
		
		
		// Send hidden variables to next page if needed
		out.print("<input type = \"hidden\" name = \"pollId\" id = \"pollId\" value = " + pollId + ">");
		out.print("<input type = \"hidden\" name = \"classCode\" value = " + classCode+ ">");
	//	out.print("<input type = \"hidden\" name = \"userId\" value = " + userId+ ">");
	
		String text = "";

		// Count number of responses
		int count = 0;
		if (answer1 != null) {
			count++;
			text = answer1;
			
		}
		if (answer2 != null) {
			count++;
			text = answer2;
		}
		
		if (answer3 != null) {
			count++;
			text = answer3;
		}
		if (answer4 != null) {
			count++;
			text = answer4;
		}
		
		// Check only one response
		if ((count > 1) || (count == 0)) {
			out.println("<h4>Please give only one response</h4>");
			request.getRequestDispatcher("/PollSubmission").include(request, response);
		}
		
		else {	
			// Call addVote()
			// Add student to studentList
			// Increment count in database
			
			if ((text != null) && (text.length() > 0)) {
				text = text.substring(0, text.length()-1);
			}
			
			
			PollDatabaseHandler dbHandler = new PollDatabaseHandler();
			try {
				int responseId = dbHandler.getResponseID(text, pollId);
				dbHandler.addVote(email, pollId, responseId);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}

			
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Thank you. Your response has been recorded. ');");
			out.println("location='StudentHome.html';");
			out.println("</script>");
		//	request.getRequestDispatcher("/StudentHome.html").include(request, response);
		}
		
		
	}
}
