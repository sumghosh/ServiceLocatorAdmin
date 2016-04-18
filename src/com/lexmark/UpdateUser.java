package com.lexmark;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UpdateUser
 */
@WebServlet("/UpdateUser")
public class UpdateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = new User();
		if(request.getParameter("userid")!=null && request.getParameter("userid")!= ""){
			user.setId(Integer.parseInt(request.getParameter("userid")));
    	}
		user.setUsername(request.getParameter("username"));
		user.setFirstName(request.getParameter("firstname"));
		user.setLastName(request.getParameter("lastname"));
		user.setEmail(request.getParameter("email"));
		if(request.getParameter("isActive")!=null){
        	if(request.getParameter("isActive").equals("on"))
        		user.setIsActive((short) 1);
        	else
        		user.setIsActive((short) 0);
        }
		if(request.getParameter("isStaff")!=null){
        	if(request.getParameter("isStaff").equals("on"))
        		user.setIsStaff((short) 1);
        	else
        		user.setIsStaff((short) 0);
        }
		if(request.getParameter("isSuperuser")!=null){
        	if(request.getParameter("isSuperuser").equals("on"))
        		user.setIsSuperuser((short) 1);
        	else
        		user.setIsSuperuser((short) 0);
        }
		//final String NEW_FORMAT = "yyyy-MM-dd HH:mm:ss";
		//DateFormat formatter = new SimpleDateFormat(NEW_FORMAT);
//		Timestamp timestamp = null;
//		try{
//			
//		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//		    Date lastLogin = dateFormat.parse(request.getParameter("last_login"));
//		    timestamp = new java.sql.Timestamp(lastLogin.getTime());
//		} catch(Exception e){//this generic but you can control another types of exception
//			e.printStackTrace();
//		}
		
		//Timestamp t = Your timestamp;
		//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    //String s = df.format(timestamp);
		//String newDateString = formatter.format(request.getParameter("last_login"));
		//System.out.println(Timestamp.valueOf(s));
		if (request.getParameter("last_login") != null && request.getParameter("last_login") != ""){
			user.setLastLogin(Timestamp.valueOf(request.getParameter("last_login")));
			//user.setLastLogin(timestamp);
		}
		if (request.getParameter("date_joined") != null && request.getParameter("date_joined") != "")
			user.setDateJoined(Timestamp.valueOf(request.getParameter("date_joined")));
        QueryWithContext qwc = new QueryWithContext();
        RequestDispatcher view = null;
        try {
			qwc.updateUser(user);
			String rediredtURL = "";
	        if(request.getParameter("button_type") != null && request.getParameter("button_type").equalsIgnoreCase("save"))
	        	rediredtURL = "UserDetails";
	        else if(request.getParameter("button_type") != null && request.getParameter("button_type").equalsIgnoreCase("saveadd"))
	        	rediredtURL = "addUser";
	        else if(request.getParameter("button_type") != null && request.getParameter("button_type").equalsIgnoreCase("saveedit"))
	        	rediredtURL = "EditUser?userid=" + Integer.parseInt(request.getParameter("userid"));
        	response.sendRedirect(rediredtURL);
        }  catch (SQLException e) {
        	request.setAttribute("DBError", e.getMessage());
        	view = request.getRequestDispatcher("ErrorPage.jsp");
        	view.forward(request, response);
        } catch (Exception e) {
        	request.setAttribute("Error", "Fatal Error occurred. Please contact system admin");
        	view = request.getRequestDispatcher("ErrorPage.jsp");
        	view.forward(request, response);
        }
        //view = request.getRequestDispatcher("UserDetails");
        //view.forward(request, response);
	}

}

