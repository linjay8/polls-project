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
@WebServlet("/InstructorServletResults")
public class InstructorServletResults extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InstructorServletResults() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nextPage = "/InstructorStart.jsp";
	
		//String idTokenString = request.getParameter("id_token");
		//String classString = request.getParameter("class");
		
		//Instructor i = DatabaseUtil.getInstructor(idTokenString);
		//Class c = DatabaseUtil.getClass(classString);
		
		if (request.getParameter("endOHButton") != null) {
		//	i.endOH(c);
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
