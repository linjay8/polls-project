package chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.DatabaseUtil;

@WebServlet(name = "chatWindow", urlPatterns = { "/chatWindow" })
public class chatWindow extends HttpServlet {

	public int senderId;
	boolean verified = false;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response, String senderID, String recipientID)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		try (PrintWriter out = response.getWriter()) {
			
			
			String message = request.getParameter("txtMsg"); // Extract Message
		
			senderId = DatabaseUtil.getUserId(senderID);
			int recipientIdFinal = Integer.parseInt(recipientID);
			
			if (!chatDB.verifyChat(senderId, recipientIdFinal)) {
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
				String recipientName = chatDB.getNameFromId(recipientIdFinal);
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
						"Enter Message: <input type=\"text\" name=\"txtMsg\" value=\"\" /><input type=\"submit\" value=\"Send\" name=\"cmdSend\"/>");
				out.println("<br><br> <a href=\"chatWindow\">Refresh Chat Room</a>");
				out.println("<br>  <br>");
				out.println("Messages in Chat Box:");
				out.println("<br>");

				// If entered message != null then insert into database
				if (request.getParameter("txtMsg") != null) {
					if (message != "")
						chatDB.addNewMessage(message, senderId, recipientIdFinal);
				} 
				
				chatDB.loadChat(out, senderId, recipientIdFinal);
			
			}
		}

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String send = (String) request.getSession().getAttribute("email");
		String recipient;
		
		if (request.getParameter("recipient") != null) {
			request.getSession().setAttribute("recipient", request.getParameter("recipient"));
			recipient = (String)request.getParameter("recipient");
		} else {
			recipient = request.getSession().getAttribute("recipient").toString();
		}	
	    
	    processRequest(request, response, send, recipient);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String send = (String)request.getSession().getAttribute("email");
		String recieve = (String)request.getSession().getAttribute("recipient");
		processRequest(request, response, send, recieve);
	}

	HttpSession session;
}
