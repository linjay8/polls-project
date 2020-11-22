package Authentication;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.DatabaseUtil;

/**
 * Servlet implementation class DemoServlet
 */
@WebServlet("/AuthenticationServlet")
public class AuthenticationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthenticationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String nextPage = "/signedin.jsp";

		String id_token = request.getParameter("id_token");
		// response.getWriter().append("Served at: ").append(request.getContextPath()).append(" \nid_token = ").append(id_token);
		response.getWriter().append(" id_token = ").append(id_token);
		String accountlevel = request.getParameter("accountlevel");
		response.getWriter().append("\naccount level = ").append(accountlevel);
		String firstname = request.getParameter("firstname");
		response.getWriter().append("\nfirst name = ").append(firstname);
		String lastname = request.getParameter("lastname");
		response.getWriter().append("\nlast name = ").append(lastname);
		String email = request.getParameter("email");
		response.getWriter().append("\nemail = ").append(email);
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextPage);
		dispatcher.forward(request, response);
		
		
		request.getSession().setAttribute("email", email);
		// Check duplicates
		if(!DatabaseUtil.userExists(email)) {
			DatabaseUtil.addNewUser(firstname + " " + lastname, email, Integer.valueOf(accountlevel));
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
