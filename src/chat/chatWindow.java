package chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "chatWindow", urlPatterns = { "/chatWindow" })
public class chatWindow extends HttpServlet {

	String username, tempName;
	String senderName, recipientName;
	public int senderId;
	public int recipientIdFinal;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		try (PrintWriter out = response.getWriter()) {

			String message = request.getParameter("txtMsg"); // Extract Message
			// session = request.getSession();
			// session.setAttribute("recipientId", request.getParameter("recipient")); //
			// Set Attribute
			int recipientId;
			senderId = 2;
			if (request.getParameter("recipient") != null) {
				recipientId = Integer.parseInt(request.getParameter("recipient"));
				recipientIdFinal = recipientId;
			}
			if (!chatDB.verifyChat(senderId, recipientIdFinal, out)) {
				out.println("<!DOCTYPE html>");
				out.println("<html>");
				out.println("<head>");
				out.println("<title>Servlet </title>");
				out.println("</head>");
				out.println("<body>");
				out.println("<h1>Cannot send message to this user. </h1>");
				out.println("</body>");
				out.println("</html>");
			} else {
				// String recipientId = request.getAttribute("recipientId").toString();
				// String recipientId = session.getAttribute("recipientId").toString();

				senderName = chatDB.getNameFromId(senderId);
				recipientName = chatDB.getNameFromId(recipientIdFinal);
				// senderId = session.getAttribute("userId").toString();

				// Display Chat Room
				out.println(
						"<html>  <head> <body bgcolor=\"#FFFFFF\"> <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"> <title>Chat Room</title>  </head>");
				out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"> <center>");
				out.println("<h2>Chatting with: ");
				out.println(recipientName);
				out.println("</h2><hr>");
				out.println("  <body>");
				out.println("      <form name=\"chatWindow\" action=\"chatWindow\">");
				out.println(
						"Message: <input type=\"text\" name=\"txtMsg\" value=\"\" /><input type=\"submit\" value=\"Send\" name=\"cmdSend\"/>");
				out.println("<br><br> <a href=\"chatWindow\">Refresh Chat Room</a>");
				out.println("<br>  <br>");
				out.println("Messages in Chat Box:");
				out.println("<br>");
				out.println("<textarea  readonly=\"readonly\"   name=\"txtMessage\" rows=\"20\" cols=\"60\">");

				// If enetered message != null then insert into database
				if (request.getParameter("txtMsg") != null) {
					chatDB.addNewMessage(message, senderId, recipientIdFinal);
				}
				// Retrieve all messages from database
				try {
					chatDB.viewMessages(out, senderId, recipientIdFinal);
				} finally {
					out.println("</textarea>");
					out.println("<hr>");
					out.println("</form>");
					out.println("</body>");
					out.println("</html>");
					// RequestDispatcher dispatcher =
					// getServletContext().getRequestDispatcher("/chatWindow");
					// dispatcher.forward(request, response);
					// request.getRequestDispatcher("/chatWindow").include(request, response);
				}

			}
		}

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Session
		session = request.getSession();
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
