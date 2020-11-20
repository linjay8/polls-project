

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/asdf")
public class PollDisplay extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
//		// Get Question and responses from database
//		String classCode = request.getParameter("classCode");
//		// get class code and then database query
//		
//		response.setContentType("text/html");
//		PrintWriter out = response.getWriter();
//		
//		String title = "Using GET Method to Read Form Data";
//		out.println("<html>");
//		out.println("<head><title>" + title + "</title></head>");
//		out.println("<body>");
//		out.println("<h4>Your poll was created</h4>");
//		
//		
//		
//		out.print("<a href=\"InstructorHome.html\">OK</a>");
//		
//		
//		out.println("</body>");
//		out.println("</html>");
	}
}
