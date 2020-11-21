package Polling;

import java.io.*;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.http.*;

import models.*;


import java.util.ArrayList;
import java.util.Random;

import javax.servlet.annotation.WebServlet;


@WebServlet("/PollCreation")
public class PollCreation extends HttpServlet
{
	Instructor instructor;

	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		// Initialize variables
		String answer1 = "";
		String answer2 = "";
		String answer3 = "";
		String answer4 = "";
		
		String classCode = request.getParameter("classCode");
		String privateCheckbox = request.getParameter("private");
		String publicCheckbox = request.getParameter("public");
		
		// If checkbox value was null, convert to ""
		if (privateCheckbox == null)
			privateCheckbox = "";
		if (publicCheckbox == null)
			publicCheckbox = "";
		
		String question = request.getParameter("question");
		answer1 = request.getParameter("answer1");
		answer2 = request.getParameter("answer2");
		answer3 = request.getParameter("answer3");
		answer4 = request.getParameter("answer4");
		
		ArrayList<String> answers = new ArrayList<String>();	
		
		// Handle Edge Cases Here:
		// Check that at least two answers were given

		if (!answer1.contentEquals(""))
			answers.add(answer1);
		if (!answer2.contentEquals(""))
			answers.add(answer2);
		if (!answer3.contentEquals(""))
			answers.add(answer3);
		if (!answer4.contentEquals(""))
			answers.add(answer4);
		
		

		//Check that Class code exists
		
		if (answers.size() < 2) {
			out.println("<h4>Please give at least two possible answers</h4>"); 
			request.getRequestDispatcher("/CheckBox.html").include(request, response);
		}
		
		
		
		// Check that exactly one box was selected
		else if ((privateCheckbox.contentEquals("") && publicCheckbox.contentEquals(""))
				|| (privateCheckbox.contentEquals("Private") && publicCheckbox.contentEquals("Public")) ) {
			out.println("<h4>Please select one poll availability option</h4>"); 
			request.getRequestDispatcher("/CheckBox.html").include(request, response);
		}
		
		// If poll is private and class code was not given, then print error
		else if(publicCheckbox.contentEquals("Private") && classCode.contentEquals("")) {
			out.println("<h4>Please provide a class code for private poll</h4>"); 
			request.getRequestDispatcher("/CheckBox.html").include(request, response);
		}
		
		// If poll is public and class code was given, print error
		else if(privateCheckbox.contentEquals("Public") && !classCode.contentEquals("")) {
			out.println("<h4>Public poll does not require class code</h4>"); 
			request.getRequestDispatcher("/CheckBox.html").include(request, response);
		}
		
		// If question was not given
		else if(question == null) {
			out.println("<h4>Please provide a question for your students</h4>"); 
			request.getRequestDispatcher("/CheckBox.html").include(request, response);
		}
		
		// If poll successfully created, add to database and display submission page
		else {
//			 Initialize poll variables
//			 Poll ID
			Random rand = new Random();
			int pollId = rand.nextInt(1000000000);
			
			// Visibility
			boolean isPublic;
			if (privateCheckbox.equals("")) 
				isPublic = true;
			else 
				isPublic = false;
			
			// Not sure why we need a student list, but it's in the constructor
			ArrayList<Student> studentList = new ArrayList<Student>();
			
			// ResponseList
			ArrayList<Response> responseList = new ArrayList<Response>();
			for (String s : answers) {
				Response r = new Response(s);
				responseList.add(r);	
			}
			
			Poll poll = new Poll(pollId, instructor, classCode,  isPublic, question, responseList, studentList);
	        PollDatabaseHandler handler = new PollDatabaseHandler(poll);
	        try {
	        	 handler.savePoll();
	        }
	        catch (SQLException e) {
	        	e.printStackTrace();
	        }
	       
	        
			request.getRequestDispatcher("Submitted").forward(request, response);
		}
	}
}
