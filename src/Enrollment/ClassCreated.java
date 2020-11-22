package Enrollment;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import models.*;
import models.Class;

import javax.servlet.annotation.WebServlet;

@WebServlet("/ClassCreated")
public class ClassCreated extends HttpServlet {


   public void init() throws ServletException {
      // Do required initialization
   }

   public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

      // Set response content type
      response.setContentType("text/html");

      // Actual logic goes here.
      PrintWriter out = response.getWriter();
      String cn = request.getParameter("class_name");
      Instructor i = DatabaseUtil.getInstructor((String)request.getSession().getAttribute("email"));
      Class c = i.createNewClass(cn);
      String classCode = c.getClassCode();
      out.println("Class created! The class code is " + classCode);
   }

   public void destroy() {
      // do nothing.
   }
}

