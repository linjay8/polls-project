package OfficeHours;
import models.*;


import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class InstructorServlet
 */
@WebServlet("/InstructorServlet")
public class InstructorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InstructorServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nextPage = "/InstructorResults.jsp";
		
		String classString = request.getParameter("class");
		String meetingLimitString = request.getParameter("meetinglimit");
		String timeslotString = request.getParameter("timeslot");
		String linkString = request.getParameter("link");
		String startTimeString = request.getParameter("starttime");
		String endTimeString = request.getParameter("endtime");
		String email = request.getParameter("email");
		
		
		if (classString == null || classString.isEmpty())
		{
			request.setAttribute("errorMessageClass", "Please fill in the class");
			nextPage = "/InstructorStart.jsp";
		}
		
		if (linkString == null || linkString.isEmpty())
		{
			request.setAttribute("errorMessageLink", "Please provide the meeting link");
			nextPage = "/InstructorStart.jsp";
		}
		
		if (request.getParameter("startOHButton") != null) {
			Instructor i = DatabaseUtil.getInstructor(email);
			models.Class c = DatabaseUtil.getClass(classString);
			i.startOfficeHours(c, Integer.parseInt(meetingLimitString), Double.parseDouble(timeslotString), 
					linkString, ZoneId.systemDefault(), startTimeString, endTimeString);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextPage);
			dispatcher.forward(request, response);
		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
