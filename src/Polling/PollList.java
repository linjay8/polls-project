package Polling;
import models.Class;
import models.DatabaseUtil;
import models.Student;

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
//		String classCode = request.getParameter("classCode");
		String email = (String)request.getSession().getAttribute("email"); 
//		String className = DatabaseUtil.getClass(classCode).getClassName();
		Student s = DatabaseUtil.getStudent(email);
		ArrayList<Class> classCodes = DatabaseUtil.getClassesFromStudent(s);
		
		PollDatabaseHandler dbHandler = new PollDatabaseHandler();
		try{
			String title = "Using GET Method to Read Form Data";
			out.println("<html>");
			out.println("<head><title>" + title + "</title></head>");
			out.println("<body>");
			out.println("<h4>Available Polls</h4>");
			
			
			// For each poll in class, print answer choice with count
			// Only print if student has not yet answered poll
			int listCount= 0;
			
			for (Class c : classCodes) {
				String classCode = c.getClassCode();
				for (int pollId : dbHandler.getPollIdByClass(classCode)) {
					ArrayList<String> studentList = dbHandler.getStudentList(pollId);
					
					// If student is NOT in studentList for pollId 
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
						out.print("<input type = \"hidden\" name = \"classCode\" value = " + classCode+ ">");
						out.print("<input type = \"hidden\" name = \"email\" value = " + email+ ">");
						
						
						out.print("<input type = \"submit\" value = \"Answer this poll\" /> ");
						out.print("</form>");
						out.print("</div> <br><br>");
					
						listCount++;
					};
				}
			}
			
	       
			
			// Might want to use a separate form instead of a button?
			if (listCount == 0) {
				out.println("<h2>Currently no available polls</h2>");
			}
			out.print("<br><br>");
			out.print("<a href=\"StudentHome.jsp\">Home</a>");
	
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
