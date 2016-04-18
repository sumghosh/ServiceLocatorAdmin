package com.lexmark;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UpdateXMLFeed
 */
@WebServlet("/UpdateXMLFeed")
public class UpdateXMLFeed extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateXMLFeed() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		XMLFeed feed = new XMLFeed();
		if(request.getParameter("feedid")!=null && request.getParameter("feedid")!= ""){
			feed.setId(Long.parseLong(request.getParameter("feedid")));
    	}
		feed.setXml_file(request.getParameter("xml_file_name"));
        QueryWithContext qwc = new QueryWithContext();
        qwc.updateXMLFeed(feed);
        RequestDispatcher view = request.getRequestDispatcher("/XMLFeedDetails");
        view.forward(request, response);
	}

}

