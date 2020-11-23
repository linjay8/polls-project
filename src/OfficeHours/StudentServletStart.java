package OfficeHours;
import models.*;

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
 * Servlet implementation class StudentServlet
 */
@WebServlet("/StudentServletStart")
public class StudentServletStart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentServletStart() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nextPage = "/StudentResults.jsp";
	
		String classString = request.getParameter("class");
		String emailString = (String)request.getSession().getAttribute("email");
		Student s = DatabaseUtil.getStudent(emailString);
		models.Class c = DatabaseUtil.getClass(classString);
		
		if (request.getParameter("joinOHButton") != null) {
			if (c == null)
			{
				request.setAttribute("errorMessageClassDNE", "Class does not exist");
				nextPage = "/StudentStart.jsp";
			}
			if (!DatabaseUtil.alreadyEnrolled(classString, s.getUserId()))
			{
				request.setAttribute("errorMessageStudentClass", "Student is not registered for this class");
				nextPage = "/StudentStart.jsp";
			}
			if (!s.joinOH(c))
			{
				request.setAttribute("errorMessageStart", "Unable to join office hours");
				nextPage = "/StudentStart.jsp";
			}
			
			try {
				Thread.sleep(5000);
			}catch(InterruptedException e) {
				
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
