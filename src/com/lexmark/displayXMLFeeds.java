package com.lexmark;

import java.io.IOException;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lexmark.QueryWithContext;

@SuppressWarnings("serial")
public class displayXMLFeeds extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
        	int page = 1;
        	if(request.getParameter("page") != null && request.getParameter("page") != "")
                page = Integer.parseInt(request.getParameter("page"));
            // Use this class if you have created the context.xml file.
            QueryWithContext qwc = new QueryWithContext();
            List<XMLFeed> result = qwc.displayXMLFeeds((page - 1)*Constants.RECORDS_PER_PAGE, Constants.RECORDS_PER_PAGE);
            request.setAttribute("feeds", result);
            //request.setAttribute("nosTotal", result.size());
            int noOfRecords = qwc.getNoOfRecords();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / Constants.RECORDS_PER_PAGE);
            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", page);
            RequestDispatcher view = request.getRequestDispatcher("XMLFeedDetails.jsp");
            view.forward(request, response);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//String query_param = request.getParameter("q");
    	QueryWithContext qwc = new QueryWithContext();
    	try{
        	int page = 1;
        	if(request.getParameter("page") != null && request.getParameter("page") != "")
                page = Integer.parseInt(request.getParameter("page"));
            //Get the total count
            List<XMLFeed> totalResult = qwc.displayXMLFeeds((page - 1)*Constants.RECORDS_PER_PAGE, Constants.RECORDS_PER_PAGE);
            request.setAttribute("feeds", totalResult);
//            request.setAttribute("nosTotal", totalResult.size());
//            if (!request.getParameterMap().containsKey("q")){
//            	RequestDispatcher view = request.getRequestDispatcher("XMLFeedDetails.jsp");
//	            view.forward(request, response);
//            } else {
//            	List<Product> searchResult = qwc.searchProducts(query_param);
//            	request.setAttribute("feeds", searchResult);
//            	request.setAttribute("nosReturned", searchResult.size());
		    	if (request.getParameterMap().containsKey("o")) {
//			            request.setAttribute("searchtext", query_param);
//			            RequestDispatcher view = request.getRequestDispatcher("XMLFeedDetails.jsp");
//			            view.forward(request, response);
//		    	} else {
		    	 	String query = "";
		    		int sort_column = Integer.parseInt(request.getParameter("o"));
		    		if (sort_column == 1)
		    			query = Constants.CREATED_ON;
		    		else if (sort_column == 2)
		    			query = Constants.PROCESSED_ON;
		    		else if (sort_column == 3)
		    			query = Constants.STATUS;
		    		else if (sort_column == 4)
		    			query = Constants.XML_FILE;
		            List<XMLFeed> result = qwc.sortXMLFeeds(query, (page - 1)*Constants.RECORDS_PER_PAGE, Constants.RECORDS_PER_PAGE);
		            request.setAttribute("feeds", result);
		            //.setAttribute("searchtext", query_param);
		    	}
	            int noOfRecords = qwc.getNoOfRecords();
	            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / Constants.RECORDS_PER_PAGE);
	            request.setAttribute("noOfPages", noOfPages);
	            request.setAttribute("currentPage", page);
	            RequestDispatcher view = request.getRequestDispatcher("XMLFeedDetails.jsp");
	            view.forward(request, response);
            //}
    	} catch (NamingException e) {
            e.printStackTrace();
        }
    }   
}
