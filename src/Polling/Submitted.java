package Polling;


import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/Submitted")
public class Submitted extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		// Variables to help print results
		String visibility ;
		if (request.getParameter("private") == null) 
			visibility = "public";
		else
			visibility = "private";
		
		String[] answers = new String[4];
		answers[0] = request.getParameter("answer1");
		answers[1] = request.getParameter("answer2");
		answers[2] = request.getParameter("answer3");
		answers[3] = request.getParameter("answer4");
		int index = 1;
		
		String title = "Using GET Method to Read Form Data";
		out.println("<html>");
		out.println("<head><title>" + title + "</title></head>");
		out.println("<body>");
		out.println("<h4>Your poll was created</h4>");
		
		out.print("<ul>\n" + "  <li><b>Poll is "
				+ visibility + "</b> \n" );
		
		out.print("\n" + "  <li><b>Question</b>: " 
				+ request.getParameter("question") + "\n" );
		
		// Walk through and print answers
		for (int i = 0; i < answers.length; i++) {
			if (!answers[i].equals("")) {
				out.print(" <li><b>Answer "+ index++ + "</b>: "
						+ answers[i] + "\n");
			}
		}
		out.print( "</ul>\n");
		
		// Might want to use a separate form instead of a button?
		out.print("<a href=\"InstructorHome.html\">OK</a>");
		
		
		out.println("</body>");
		out.println("</html>");
	}
}
