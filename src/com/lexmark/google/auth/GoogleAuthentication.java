package com.lexmark.google.auth;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import com.lexmark.QueryWithContext;
import com.lexmark.User;

/**
 * Servlet implementation class GoogleAuthentication
 */
@WebServlet("/GoogleAuthentication")
public class GoogleAuthentication extends HttpServlet {
	private static final long serialVersionUID = 1L;
	GoogleAuthHelper helper = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoogleAuthentication() {
        //super();
        helper = new GoogleAuthHelper();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//final GoogleAuthHelper helper = new GoogleAuthHelper();
		
		HttpSession session = request.getSession();
		if (request.getParameter("code") == null || request.getParameter("state") == null) {
			helper.buildLoginUrl();
			session.setAttribute("state", helper.getStateToken());
			
			//doPost(request, response);
			//RequestDispatcher view = request.getRequestDispatcher("GoogleAuthentication");
			//view.forward(request, response);
		} else if (request.getParameter("code") != null && request.getParameter("state") != null
				&& request.getParameter("state").equals(request.getSession().getAttribute("state"))) {
			session.removeAttribute("state");
			String userJSON = helper.getUserInfoJson(request.getParameter("code"));
			JSONObject jObject;
			String firstName = "";
			String email = "";
			User user = null;
			try {
				jObject = new JSONObject(userJSON);
				firstName = jObject.getString("given_name");
				email = jObject.getString("email");
				QueryWithContext qwc = new QueryWithContext();
				RequestDispatcher view = null;
				if (!email.equals("")){
					try {
						user = qwc.getUserFromEmail(email);
			        }  catch (SQLException e) {
			        	request.setAttribute("DBError", e.getMessage());
			        	view = request.getRequestDispatcher("ErrorPage.jsp");
			        	view.forward(request, response);
			        } catch (Exception e) {
			        	request.setAttribute("Error", "Fatal Error occurred. Please contact system admin");
			        	view = request.getRequestDispatcher("ErrorPage.jsp");
			        	view.forward(request, response);
			        }
					if (user != null){
						session.setAttribute("GoogleloggedInUser", firstName);
						view = request.getRequestDispatcher("dashboard.jsp");
						view.forward(request, response);
					} else {
						//redirect to error page. User is successfully logged into Google but doesn't have access to the SL Admin
					}
				} else {
					Logger.getLogger("Email is empty");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final GoogleAuthHelper helper = new GoogleAuthHelper();
		HttpSession session = request.getSession();
		if (request.getParameter("code") == null || request.getParameter("state") == null) {
			helper.buildLoginUrl();
			session.setAttribute("state", helper.getStateToken());
			//doGet(request, response);
			//RequestDispatcher view = request.getRequestDispatcher("GoogleAuthentication");
			//view.forward(request, response);
		} else if (request.getParameter("code") != null && request.getParameter("state") != null
				&& request.getParameter("state").equals(request.getSession().getAttribute("state"))) {
			session.removeAttribute("state");
			String userJSON = helper.getUserInfoJson(request.getParameter("code"));
			JSONObject jObject;
			String firstName = "";
			String email = "";
			User user = null;
			try {
				jObject = new JSONObject(userJSON);
				firstName = jObject.getString("given_name");
				email = jObject.getString("email");
				QueryWithContext qwc = new QueryWithContext();
				RequestDispatcher view = null;
				if (!email.equals("")){
					try {
						user = qwc.getUserFromEmail(email);
			        }  catch (SQLException e) {
			        	request.setAttribute("DBError", e.getMessage());
			        	view = request.getRequestDispatcher("ErrorPage.jsp");
			        	view.forward(request, response);
			        } catch (Exception e) {
			        	request.setAttribute("Error", "Fatal Error occurred. Please contact system admin");
			        	view = request.getRequestDispatcher("ErrorPage.jsp");
			        	view.forward(request, response);
			        }
					if (user != null){
						session.setAttribute("GoogleloggedInUser", firstName);
						view = request.getRequestDispatcher("dashboard.jsp");
						view.forward(request, response);
					}
				} else {
					Logger.getLogger("Email is empty");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

}
