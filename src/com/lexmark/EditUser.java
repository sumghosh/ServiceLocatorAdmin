package com.lexmark;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class EditUser extends HttpServlet{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int user_id = 0;
    	if(request.getParameter("userid")!= null && request.getParameter("userid")!= ""){
    		user_id = Integer.parseInt(request.getParameter("userid"));
    	}
		QueryWithContext qwc = new QueryWithContext();
		RequestDispatcher view = null;
		try {
			User user = qwc.editUser(user_id);
			request.setAttribute("user", user);
		
			List<Group> groupList = qwc.getAllGroups();
			List<UserPermissionDummy> userPermDummyList = qwc.getAllUserPermission();
			List<Group> selectedGroups = qwc.getGroupsByUser(user_id);
			request.setAttribute("selectedGroups", selectedGroups);
			groupList.removeAll(selectedGroups);
			request.setAttribute("groups", groupList);
			List<UserPermissionDummy> selectedUserPermDummyList = qwc.getUserPermissionByUser(user_id);
			request.setAttribute("selectedUserPermissions", selectedUserPermDummyList);
			boolean b = userPermDummyList.removeAll(selectedUserPermDummyList);
			//System.out.println(selectedUserPermDummyList.size());
			request.setAttribute("userPermissions", userPermDummyList);
			//System.out.println(b);
        }  catch (SQLException e) {
        	request.setAttribute("DBError", e.getMessage());
        	view = request.getRequestDispatcher("ErrorPage.jsp");
        	view.forward(request, response);
        } catch (Exception e) {
        	request.setAttribute("Error", "Fatal Error occurred. Please contact system admin");
        	view = request.getRequestDispatcher("ErrorPage.jsp");
        	view.forward(request, response);
        }
		view = request.getRequestDispatcher("EditUser.jsp");
		view.forward(request, response);
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int user_id = 0;
    	if(request.getParameter("userid")!= null && request.getParameter("userid")!= ""){
    		user_id = Integer.parseInt(request.getParameter("userid"));
    	}
		QueryWithContext qwc = new QueryWithContext();
		RequestDispatcher view = null;
		try {
			User user = qwc.editUser(user_id);
			request.setAttribute("user", user);
		
			List<Group> groupList = qwc.getAllGroups();
			List<UserPermissionDummy> userPermDummyList = qwc.getAllUserPermission();
			List<Group> selectedGroups = qwc.getGroupsByUser(user_id);
			request.setAttribute("selectedGroups", selectedGroups);
			groupList.removeAll(selectedGroups);
			request.setAttribute("groups", groupList);
			List<UserPermissionDummy> selectedUserPermDummyList = qwc.getUserPermissionByUser(user_id);
			request.setAttribute("selectedUserPermissions", selectedUserPermDummyList);
			userPermDummyList.removeAll(selectedUserPermDummyList);
			request.setAttribute("userPermissions", userPermDummyList);
        }  catch (SQLException e) {
        	request.setAttribute("DBError", e.getMessage());
        	view = request.getRequestDispatcher("ErrorPage.jsp");
        	view.forward(request, response);
        } catch (Exception e) {
        	request.setAttribute("Error", "Fatal Error occurred. Please contact system admin");
        	view = request.getRequestDispatcher("ErrorPage.jsp");
        	view.forward(request, response);
        }
		view = request.getRequestDispatcher("EditUser.jsp");
		view.forward(request, response);
    }

}
