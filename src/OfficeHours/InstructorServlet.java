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
		String minLongString = request.getParameter("minlong");
		String emailString = (String)request.getSession().getAttribute("email");
		
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
		
		if (meetingLimitString == null || meetingLimitString.isEmpty())
		{
			meetingLimitString = "5";
		}
		if (timeslotString == null || timeslotString.isEmpty())
		{
			timeslotString = "15";
		}
		if (minLongString == null || timeslotString.isEmpty())
		{
			minLongString = "60";
		}
		
		if (request.getParameter("startOHButton") != null) {
			Instructor i = DatabaseUtil.getInstructor(emailString);
			models.Class c = DatabaseUtil.getClass(classString);
			if (c == null)
			{
				request.setAttribute("errorMessageClassDNE", "Instructor is not registered for this class");
				nextPage = "/InstructorStart.jsp";
			}
			if (!DatabaseUtil.getClassesFromInstructor(i).contains(c))
			{
				request.setAttribute("errorMessageInstructorClass", "Instructor is not registered for this class");
				nextPage = "/InstructorStart.jsp";
			}
			
			
			if(!i.startOfficeHours(c, Integer.parseInt(meetingLimitString), Double.parseDouble(timeslotString), 
					linkString, ZoneId.systemDefault(), Integer.parseInt(minLongString))) 
			{
				request.setAttribute("errorMessageStart", "Unable to start office hours");
				nextPage = "/InstructorStart.jsp";
			}
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
