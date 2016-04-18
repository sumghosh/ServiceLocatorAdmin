package com.lexmark;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class EditXMLFeed extends HttpServlet{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	long feed_id = 0;
    	if(request.getParameter("feedid")!= null && request.getParameter("feedid")!= ""){
    		feed_id = Long.parseLong(request.getParameter("feedid"));
    	}
		QueryWithContext qwc = new QueryWithContext();
		XMLFeed feed = qwc.editXMLFeed(feed_id);
		request.setAttribute("feed", feed);
		RequestDispatcher view = request.getRequestDispatcher("EditXMLFeed.jsp");
		view.forward(request, response);
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	long feed_id = 0;
    	if(request.getParameter("feedid")!= null && request.getParameter("feedid")!= ""){
    		feed_id = Long.parseLong(request.getParameter("feedid"));
    	}
		QueryWithContext qwc = new QueryWithContext();
		XMLFeed feed = qwc.editXMLFeed(feed_id);
		request.setAttribute("feed", feed);
		RequestDispatcher view = request.getRequestDispatcher("EditXMLFeed.jsp");
		view.forward(request, response);
    }

}
