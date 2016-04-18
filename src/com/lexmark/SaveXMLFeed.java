package com.lexmark;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SaveXMLFeed
 */
@WebServlet("/SaveXMLFeed")
public class SaveXMLFeed extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveXMLFeed() {
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
		XMLFeed feed = new XMLFeed();
		if (request.getParameter("xml_file_name") != null && request.getParameter("xml_file_name") != "")
			feed.setXml_file(request.getParameter("xml_file_name"));
		feed.setLog("new feed");
    	QueryWithContext qwc = new QueryWithContext();
    	qwc.saveXMLFeed(feed);
    	RequestDispatcher view = request.getRequestDispatcher("/XMLFeedDetails");
        view.forward(request, response);
	}

}
