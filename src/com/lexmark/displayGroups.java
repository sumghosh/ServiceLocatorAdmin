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
public class displayGroups extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
                // Use this class if you have created the context.xml file.
        	int page = 1;
        	if(request.getParameter("page") != null && request.getParameter("page") != "")
                page = Integer.parseInt(request.getParameter("page"));
                QueryWithContext qwc = new QueryWithContext();
                List<Group> result = qwc.displayGroups((page - 1)*Constants.RECORDS_PER_PAGE, Constants.RECORDS_PER_PAGE);
                request.setAttribute("groups", result);
                request.setAttribute("nosTotal", result.size());
                int noOfRecords = qwc.getNoOfRecords();
                int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / Constants.RECORDS_PER_PAGE);
                request.setAttribute("noOfPages", noOfPages);
                request.setAttribute("currentPage", page);
                RequestDispatcher view = request.getRequestDispatcher("GroupDetails.jsp");
                view.forward(request, response);
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String query_param = request.getParameter("q");
    	QueryWithContext qwc = new QueryWithContext();
    	try{
            //Get the total count
        	int page = 1;
        	if(request.getParameter("page") != null && request.getParameter("page") != "")
                page = Integer.parseInt(request.getParameter("page"));
            List<Group> totalResult = qwc.displayGroups((page - 1)*Constants.RECORDS_PER_PAGE, Constants.RECORDS_PER_PAGE);
            request.setAttribute("groups", totalResult);
            request.setAttribute("nosTotal", totalResult.size());
            int noOfRecords = qwc.getNoOfRecords();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / Constants.RECORDS_PER_PAGE);
            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", page);
            if (request.getParameterMap().containsKey("q")){
            	if (request.getParameter("q") != ""){
		            List<Group> searchResult = qwc.searchGroups(query_param, (page - 1)*Constants.RECORDS_PER_PAGE, Constants.RECORDS_PER_PAGE);
		            request.setAttribute("groups", searchResult);
		            request.setAttribute("nosReturned", searchResult.size());
		            noOfRecords = qwc.getNoOfRecords();
		            noOfPages = (int) Math.ceil(noOfRecords * 1.0 / Constants.RECORDS_PER_PAGE);
		            request.setAttribute("noOfPages", noOfPages);
		            request.setAttribute("currentPage", page);
	            }
            }
    		RequestDispatcher view = request.getRequestDispatcher("GroupDetails.jsp");
            view.forward(request, response);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}
