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

	protected void processRequest(HttpServletRequest request, HttpServletResponse response, String senderID)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
	
		//senderId = DatabaseUtil.getUserId(request.getSession().getAttribute("email")); //get senderId from email
		//request.getSession().setAttribute("senderId", senderId);
		//String temp = (String)request.getSession().getAttribute("email");
		senderId =  DatabaseUtil.getUserId(senderID);
		//request.setAttribute("senderID", 1);
		 
		//request.getSession().setAttribute("senderId", senderId);
		PrintWriter out = response.getWriter();

		try {
			// ArrayList<String> list = chatDB.getAvailableStudents("a", out);
			Student st2 = DatabaseUtil.getStudentFromId(senderId);
			Instructor in2 = null;
			ArrayList<Class> classes = null;
			boolean isInstructor = false;
			if (st2 != null) {
				classes = DatabaseUtil.getClassesFromStudent(st2);
			} else {
				isInstructor = true;
				in2 = DatabaseUtil.getInstructorFromId(senderId);
				classes = DatabaseUtil.getClassesFromInstructor(in2);
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
		} 

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String temp = (String)request.getSession().getAttribute("email");
		processRequest(request, response, temp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String temp = (String)request.getSession().getAttribute("email");
		processRequest(request, response, temp);
	}
	
	HttpSession session;
}
