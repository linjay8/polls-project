package OfficeHours;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class StudentServletResults
 */
@WebServlet("/StudentServletResults")
public class StudentServletResults extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentServletResults() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nextPage = "/StudentStart.jsp";
	
		//String idTokenString = request.getParameter("id_token");
		
		//Student s = DatabaseUtil.getStudent(idTokenString);
		//Class c = s.getInClass();
		
		if (request.getParameter("leaveOHButton") != null) {
		//	s.joinOH(c);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextPage);
			dispatcher.forward(request, response);
		}
		
//		response.setContentType("text/html");
//		PrintWriter out = response.getWriter();
//		if (s.getInWaitingStatus())
//		{
//			int placeInLine = c.getOH().getMeetingSize() + c.getOH().getWaitingSize();
//			out.println("<h3>You are waiting to join the meeting...</h3>");
//			out.println ("<h4>When you first joined, your place in line was:  + <%= placeInLine> </h4>");
//			request.getRequestDispatcher("/StudentResults.jsp").include(request, response);
//		}
//		else if (s.getInMeetingStatus())
//		{
//			out.println("<h3>You are now invited to join the meeting...</h3>");
//			request.getRequestDispatcher("StudentResults.jsp").include(request, response);
//		}
//		else if (!s.getInWaitingStatus() && !s.getInMeetingStatus())
//		{
//			out.println("<h3>Turn in meeting over... exiting...</h3>");
//			request.getRequestDispatcher("StudentStart.jsp").forward(request, response);
//		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
