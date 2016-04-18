package com.lexmark;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DeleteGroup
 */
@WebServlet("/DeleteGroup")
public class DeleteGroup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteGroup() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long group_id = 0;
		if(request.getParameter("groupid") != null && request.getParameter("groupid") != ""){
			group_id = Long.parseLong(request.getParameter("groupid"));
		}
		QueryWithContext qwc = new QueryWithContext();
		qwc.deleteGroup(group_id);
		RequestDispatcher view = request.getRequestDispatcher("/GroupDetails");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long group_id = 0;
		if(request.getParameter("groupid") != null && request.getParameter("groupid") != ""){
			group_id = Long.parseLong(request.getParameter("groupid"));
		}
		QueryWithContext qwc = new QueryWithContext();
		qwc.deleteGroup(group_id);
		RequestDispatcher view = request.getRequestDispatcher("/GroupDetails");
		view.forward(request, response);
	}

}
