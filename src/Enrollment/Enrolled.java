package Enrollment;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import models.*;
import models.Class;

import javax.servlet.annotation.WebServlet;

@WebServlet("/Enrolled")
public class Enrolled extends HttpServlet {


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
      
      System.out.println("EMAIL " +(String)request.getSession().getAttribute("email") );
      Student s = DatabaseUtil.getStudent((String)request.getSession().getAttribute("email"));
    
      s.enrollClass(cc);
      Class c = DatabaseUtil.getClass(cc);
      out.println(s.getFullName() + ", welcome to " + c.getClassName());
   }

   public void destroy() {
      // do nothing.
   }
}

