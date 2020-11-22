package Enrollment;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import models.DatabaseUtil;

import javax.servlet.annotation.WebServlet;

@WebServlet("/Enroll")
public class Enroll extends HttpServlet {

   public void init() throws ServletException {
      // Do required initialization
   }

   public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

      // Set response content type
      response.setContentType("text/html");

      // Actual logic goes here.
      PrintWriter out = response.getWriter();
      String cc = request.getParameter("class_code");
      if (cc.contentEquals("")) 
      {
    	  out.println("<h4>Class code is missing!</h4>"); 
    	  request.getRequestDispatcher("/Enroll.html").include(request, response);
	  }
      else if (!DatabaseUtil.classCodeExists(cc))
      {
    	  out.println("<h4>Class does not exist!</h4>"); 
    	  request.getRequestDispatcher("/Enroll.html").include(request, response);
      }
      else
      {
    	  request.getRequestDispatcher("Enrolled").forward(request, response);  
      }
   }

   public void destroy() {
      // do nothing.
   }
}

