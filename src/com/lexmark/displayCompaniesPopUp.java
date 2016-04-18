package com.lexmark;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lexmark.QueryWithContext;

@SuppressWarnings("serial")
public class displayCompaniesPopUp extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	RequestDispatcher view = null;
    	try {
                // Use this class if you have created the context.xml file.
        	int page = 1;
        	if(request.getParameter("page") != null && request.getParameter("page") != "")
                page = Integer.parseInt(request.getParameter("page"));
            QueryWithContext qwc = new QueryWithContext();
            List<Company> result = qwc.displayCompanies((page - 1)*Constants.RECORDS_PER_PAGE, Constants.RECORDS_PER_PAGE);
            request.setAttribute("companies", result);
            request.setAttribute("nosTotal", result.size());
            int noOfRecords = qwc.getNoOfRecords();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / Constants.RECORDS_PER_PAGE);
            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", page);
            view = request.getRequestDispatcher("CompanyDetailsPopUp.jsp");
            view.forward(request, response);
        }  catch (SQLException e) {
        	request.setAttribute("DBError", "Fatal Error occurred. Please contact system admin");
        	view = request.getRequestDispatcher("ErrorPage.jsp");
        	view.forward(request, response);
        } catch (Exception e) {
        	request.setAttribute("Error", "Fatal Error occurred. Please contact system admin");
        	view = request.getRequestDispatcher("ErrorPage.jsp");
        	view.forward(request, response);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String query_param = request.getParameter("q");
    	QueryWithContext qwc = new QueryWithContext();
    	RequestDispatcher view = null;
    	try{
            //Get the total count
        	int page = 1;
        	if(request.getParameter("page") != null && request.getParameter("page") != "")
                page = Integer.parseInt(request.getParameter("page"));
            List<Company> totalResult = qwc.displayCompanies((page - 1)*Constants.RECORDS_PER_PAGE, Constants.RECORDS_PER_PAGE);
            request.setAttribute("companies", totalResult);
            request.setAttribute("nosTotal", totalResult.size());
            int noOfRecords = qwc.getNoOfRecords();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / Constants.RECORDS_PER_PAGE);
            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", page);
            if (!request.getParameterMap().containsKey("q")){
            	view = request.getRequestDispatcher("CompanyDetailsPopUp.jsp");
	            view.forward(request, response);
            } else {
	            List<Company> searchResult = qwc.searchCompanies(query_param, (page - 1)*Constants.RECORDS_PER_PAGE, Constants.RECORDS_PER_PAGE);
	            request.setAttribute("companies", searchResult);
	            request.setAttribute("nosReturned", searchResult.size());
	            noOfRecords = qwc.getNoOfRecords();
	            noOfPages = (int) Math.ceil(noOfRecords * 1.0 / Constants.RECORDS_PER_PAGE);
	            request.setAttribute("noOfPages", noOfPages);
	            request.setAttribute("currentPage", page);
		    	if (!request.getParameterMap().containsKey("o")) {
			            request.setAttribute("searchtext", query_param);
			            view = request.getRequestDispatcher("CompanyDetailsPopUp.jsp");
			            view.forward(request, response);
		    	} else {
		    	 	String query = "";
		    		int sort_column = Integer.parseInt(request.getParameter("o"));
		    		if (sort_column == 1)
		    			query = Constants.NAME;
		    		else if (sort_column == 2)
		    			query = Constants.LOCATION_ID;
		    		else if (sort_column == 3)
		    			query = Constants.PHONE;
		    		else if (sort_column == 5)
		    			query = Constants.COUNTRY;
		            List<Company> result = qwc.sortCompanies(query_param, query, (page - 1)*Constants.RECORDS_PER_PAGE, Constants.RECORDS_PER_PAGE);
		            request.setAttribute("companies", result);
		            request.setAttribute("searchtext", query_param);
		            noOfRecords = qwc.getNoOfRecords();
		            noOfPages = (int) Math.ceil(noOfRecords * 1.0 / Constants.RECORDS_PER_PAGE);
		            request.setAttribute("noOfPages", noOfPages);
		            request.setAttribute("currentPage", page);
		            view = request.getRequestDispatcher("CompanyDetailsPopUp.jsp");
		            view.forward(request, response);
		    	}
            }
        }  catch (SQLException e) {
        	request.setAttribute("DBError", "Fatal Error occurred. Please contact system admin");
        	view = request.getRequestDispatcher("ErrorPage.jsp");
        	view.forward(request, response);
        } catch (Exception e) {
        	request.setAttribute("Error", "Fatal Error occurred. Please contact system admin");
        	view = request.getRequestDispatcher("ErrorPage.jsp");
        	view.forward(request, response);
        }
    }
}
