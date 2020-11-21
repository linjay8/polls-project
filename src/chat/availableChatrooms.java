package chat;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.DatabaseUtil;
import models.Instructor;
import models.Student;
import models.Class;

@WebServlet(name = "availableChatrooms", urlPatterns = { "/availableChatrooms" })
public class availableChatrooms extends HttpServlet {

	public String recipient;
	public int senderId;
	public String recipientId;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		// recipient = request.getParameter("recipient").toString(); // Extract username
		// senderId = session.getAttribute("userId").toString();
		//need to get senderId from requestdispatcher? or a button that sends value to this servlet (URl query get)
		senderId = 1;
		PrintWriter out = response.getWriter();

		try {
			Student s2 = DatabaseUtil.getStudent(senderId);
			Instructor i2 = null;
			ArrayList<Class> classes = null;
			boolean isInstructor = false;
			
			if (s2 != null) {
				classes = DatabaseUtil.getClassesFromStudent(s2);
			} else {
				isInstructor = true;
				i2 = DatabaseUtil.getInstructor(senderId);
				classes = DatabaseUtil.getClassesFromInstructor(i2);
			}

			// display available chatrooms
			out.println(
					"<html>  <head> <body bgcolor=\"#FFFFFF\"> <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"> <title>Chat Rooms: </title>  </head>");
			out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
			out.println("<body>");
			out.println("<form name=\"chatWindow\" action=\"chatWindow\">");
			out.println("<h1>Send Message To: </h1>");
			for (int i = 0; i < classes.size(); i++) {
				ArrayList<Student> studentList = DatabaseUtil.getStudentsFromClass(classes.get(i).getClassCode());
				out.println("<h2>Class: " + classes.get(i).getClassName() + "</h2>");
				if (!isInstructor) {
					out.println("<button name=\"recipient\" type=\"submit\" value=\""
							+ classes.get(i).getInstructor().getUserId() + "\">"
							+ classes.get(i).getInstructor().getFullName() + "</button> <br>");
				}
				for (int j = 0; j < studentList.size(); j++) {
					if (studentList.get(j).getUserId() != (senderId)) {
						out.println(
								"<button name=\"recipient\" type=\"submit\" value=\"" + studentList.get(j).getUserId()
										+ "\">" + studentList.get(j).getFullName() + "</button> <br>");
					}
				}

			}
			out.println("</form>");
			out.println("</body>");
			out.println("</html>");

			// session = request.getSession();
			// session.setAttribute("recipientId", r);
			// session.setAttribute("recipientId", request.getParameter("recipient")); //
			// Set Attribute

		} catch (Exception e) {
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet </title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Sevlet failed</h1>");
			out.println("</body>");
			out.println("</html>");
			System.out.println(e);
		} finally {
			// request.setAttribute("recipientId",
			// request.getParameter("recipient").toString());
			// RequestDispatcher dispatcher =
			// getServletContext().getRequestDispatcher("/chatWindow");
			// dispatcher.forward(request, response);
			// request.getRequestDispatcher("/chatWindow").include(request, response);
		}

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

	HttpSession session;
}
