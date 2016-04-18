package com.lexmark;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DeleteXMLFeed
 */
@WebServlet("/DeleteXMLFeed")
public class DeleteXMLFeed extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteXMLFeed() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long feed_id = 0;
		if(request.getParameter("feedid") != null && request.getParameter("feedid") != ""){
			feed_id = Long.parseLong(request.getParameter("feedid"));
		}
		QueryWithContext qwc = new QueryWithContext();
		qwc.deleteXMLFeed(feed_id);
		RequestDispatcher view = request.getRequestDispatcher("/XMLFeedDetails");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long feed_id = 0;
		if(request.getParameter("feedid") != null && request.getParameter("feedid") != ""){
			feed_id = Long.parseLong(request.getParameter("feedid"));
		}
		QueryWithContext qwc = new QueryWithContext();
		qwc.deleteXMLFeed(feed_id);
		RequestDispatcher view = request.getRequestDispatcher("/XMLFeedDetails");
		view.forward(request, response);
	}
}
