package com.lexmark;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lexmark.google.auth.PasswordHasher;
/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		PasswordHasher ph = new PasswordHasher();
		QueryWithContext qwc = new QueryWithContext();
		RequestDispatcher view = null;
		try{
			User user = qwc.getUserFromUsername(username);
			if (user != null){
				if(ph.checkPassword(password, user.getPassword())){
					//Update user's last login time
					qwc.updateUsersLastLogin(user.getId());
					request.getSession().setAttribute("loggedInUser", user);
					view = request.getRequestDispatcher("dashboard.jsp");
					view.forward(request, response);
				} else {
					request.setAttribute("passwordnotmatched", "The supplied password is incorrect");
					view = request.getRequestDispatcher("Login.jsp");
					view.forward(request, response);
				}
			} else {
				request.setAttribute("userdoesnotexists", "The Username does not exists");
				view = request.getRequestDispatcher("Login.jsp");
				view.forward(request, response);
			}
        }  catch (SQLException e) {
        	request.setAttribute("DBError", e.getMessage());
        	view = request.getRequestDispatcher("ErrorPage.jsp");
        	view.forward(request, response);
        } catch (Exception e) {
        	request.setAttribute("Error", "Fatal Error occurred. Please contact system admin");
        	view = request.getRequestDispatcher("ErrorPage.jsp");
        	view.forward(request, response);
        }
	}
}
