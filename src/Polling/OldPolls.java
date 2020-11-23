package Polling;
import models.DatabaseUtil;
import models.Student;
import models.Class;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/OldPolls")
public class OldPolls extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		// Variables to help print results
		String email = (String)request.getSession().getAttribute("email"); 
		Student s = DatabaseUtil.getStudent(email);
		ArrayList<Class> classCodes = DatabaseUtil.getClassesFromStudent(s);
		
		
		
		PollDatabaseHandler dbHandler = new PollDatabaseHandler();
		try{
			String title = "Using GET Method to Read Form Data";
			out.println("<html>");
			out.println("<head><title>" + title + "</title></head>");
			out.println("<body>");
			out.println("<h4>Previous polls</h4>");
			
			
			// For each poll in each class, print answer choice with count
			// Only print if student has not already answered poll
			int listCount= 0;
			for (Class c : classCodes) {
				String classCode = c.getClassCode();
				for (int pollId : dbHandler.getPollIdByClass(classCode)) {
					ArrayList<String> studentList = dbHandler.getStudentList(pollId);
					
					// If student is NOT in studentList for pollId 
					if (alreadyAnswered(studentList, email)) {
						ArrayList<String> resultList = dbHandler.getPollResults(pollId);
						ArrayList<Integer> countList = dbHandler.getPollResultCount(pollId);
						
						String className = DatabaseUtil.getClass(classCode).getClassName();
						
						out.print("<div>");
						out.println("<b>" + className + "</b>");
						out.print("  <li><b>Question</b>: " 
								+ dbHandler.getQuestion(pollId) +  "\n" );
						 for (int i = 0 ; i < resultList.size(); i++) {
					            out.print(" <li>" + resultList.get(i)  + ": " + countList.get(i)  +" \n");
					     }
						out.print( "</ul>");
						out.print( "<br></br>");
		
						out.print("</div> <br><br>");
					
						listCount++;
					};
				}
			}
			
	
			
//			
			
			
			// Might want to use a separate form instead of a button?
			if (listCount == 0) {
				out.println("<h2>You haven't answered any polls</h2>");
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
