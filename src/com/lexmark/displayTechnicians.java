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
public class displayTechnicians extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	RequestDispatcher view = null;
    	try {
                // Use this class if you have created the context.xml file.
        	int page = 1;
        	if(request.getParameter("page") != null && request.getParameter("page") != "")
        		page = Integer.parseInt(request.getParameter("page"));
            QueryWithContext qwc = new QueryWithContext();
            List<Technician> result = qwc.displayTechnicians((page - 1)*Constants.RECORDS_PER_PAGE, Constants.RECORDS_PER_PAGE);
            request.setAttribute("technicians", result);
            request.setAttribute("nosTotal", result.size());
            int noOfRecords = qwc.getNoOfRecords();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / Constants.RECORDS_PER_PAGE);
            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", page);
            view = request.getRequestDispatcher("TechnicianDetails.jsp");
            view.forward(request, response);
        }  catch (SQLException e) {
        	request.setAttribute("DBError", e.getMessage());
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
    		int page = 1;
        	if(request.getParameter("page") != null && request.getParameter("page") != "")
                page = Integer.parseInt(request.getParameter("page"));
            //Get the total count
            List<Technician> totalResult = qwc.displayTechnicians((page - 1)*Constants.RECORDS_PER_PAGE, Constants.RECORDS_PER_PAGE);
            request.setAttribute("technicians", totalResult);
            request.setAttribute("nosTotal", totalResult.size());
            int noOfRecords = qwc.getNoOfRecords();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / Constants.RECORDS_PER_PAGE);
            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", page);
            if (request.getParameterMap().containsKey("q")){
            	if (request.getParameter("q") == ""){
            		if (request.getParameterMap().containsKey("o")){
                		String query = "";
                		int sort_column = Integer.parseInt(request.getParameter("o"));
                		if (sort_column == 1)
                			query = Constants.USERID;
    		    		else if (sort_column == 3)
    		    			query = Constants.PHONE;
    		    		else if (sort_column == 4)
    		    			query = Constants.EMAIL;
    		    		else if (sort_column == 6)
    		    			query = Constants.COUNTRY;
    		    		else if (sort_column == 7)
    		    			query = Constants.LASTMODIFIED;
    	    	
                		List<Technician> result = qwc.sortTechnicians(query_param, query, (page - 1)*Constants.RECORDS_PER_PAGE, Constants.RECORDS_PER_PAGE);
                		request.setAttribute("technicians", result);
                		request.setAttribute("searchtext", query_param);
    		            noOfRecords = qwc.getNoOfRecords();
    		            noOfPages = (int) Math.ceil(noOfRecords * 1.0 / Constants.RECORDS_PER_PAGE);
    		            request.setAttribute("noOfPages", noOfPages);
    		            request.setAttribute("currentPage", page);
            		}
	            } else {
	            	if (!request.getParameterMap().containsKey("o")){
	                	List<Technician> searchResult = qwc.searchTechnicians(query_param, (page - 1)*Constants.RECORDS_PER_PAGE, Constants.RECORDS_PER_PAGE);
	                	request.setAttribute("technicians", searchResult);
	                	request.setAttribute("nosReturned", searchResult.size());
	    	            noOfRecords = qwc.getNoOfRecords();
	    	            noOfPages = (int) Math.ceil(noOfRecords * 1.0 / Constants.RECORDS_PER_PAGE);
	    	            request.setAttribute("noOfPages", noOfPages);
	    	            request.setAttribute("currentPage", page);
	            	} else {
	            		String query = "";
	            		int sort_column = Integer.parseInt(request.getParameter("o"));
	            		if (sort_column == 1)
	            			query = Constants.USERID;
			    		else if (sort_column == 3)
			    			query = Constants.PHONE;
			    		else if (sort_column == 4)
			    			query = Constants.EMAIL;
			    		else if (sort_column == 6)
			    			query = Constants.COUNTRY;
			    		else if (sort_column == 7)
			    			query = Constants.LASTMODIFIED;
		    	
	            		List<Technician> result = qwc.sortTechnicians(query_param, query, (page - 1)*Constants.RECORDS_PER_PAGE, Constants.RECORDS_PER_PAGE);
	            		request.setAttribute("technicians", result);
	            		request.setAttribute("searchtext", query_param);
			            noOfRecords = qwc.getNoOfRecords();
			            noOfPages = (int) Math.ceil(noOfRecords * 1.0 / Constants.RECORDS_PER_PAGE);
			            request.setAttribute("noOfPages", noOfPages);
			            request.setAttribute("currentPage", page);
	            	}
	            }
            }
        	view = request.getRequestDispatcher("TechnicianDetails.jsp");
            view.forward(request, response);
        }  catch (SQLException e) {
        	request.setAttribute("DBError", e.getMessage());
        	view = request.getRequestDispatcher("ErrorPage.jsp");
        	view.forward(request, response);
        } catch (Exception e) {
        	request.setAttribute("Error", "Fatal Error occurred. Please contact system admin");
        	view = request.getRequestDispatcher("ErrorPage.jsp");
        	view.forward(request, response);
        }
    }
}
