package Enrollment;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/CreateClass")
public class CreateClass extends HttpServlet {

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
      if (cn.contentEquals("")) 
      {
    	  out.println("<h4>Class name is missing!</h4>"); 
    	  request.getRequestDispatcher("/CreateClass.html").include(request, response);
	  }
      else
      {
    	  request.getRequestDispatcher("ClassCreated").forward(request, response);  
      }
   }

   public void destroy() {
      // do nothing.
   }
}

