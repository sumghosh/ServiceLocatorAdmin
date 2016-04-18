package com.lexmark;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class TomcatServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        //PrintWriter out = response.getWriter();
        //String c = request.getParameter("type");
        // If the button that we have in our jsp page made a POST
        // then the servlet is activated and does whatever we programmed
        // it to do.
        if (request.getParameter("jdbc_query") != null) {
            try {
                // Use this class if you have created the context.xml file.
            	QueryWithContext qwcontext = new QueryWithContext();
            	qwcontext.displayCompanies(1, 1000);
                
                // Use this one without creating/using the context.xml file.
                //QueryWithoutContext.queryCompanies(out);
            }  catch (SQLException e) {
            	request.setAttribute("DBError", e.getMessage());
            	//view = request.getRequestDispatcher("ErrorPage.jsp");
            	//view.forward(request, response);
            } catch (Exception e) {
            	request.setAttribute("Error", "Fatal Error occurred. Please contact system admin");
            	//view = request.getRequestDispatcher("ErrorPage.jsp");
            	//view.forward(request, response);
            }
        }
    }
    
    public void goGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	response.setContentType("text/html");
    	PrintWriter out = response.getWriter();
    	try{
    		QueryWithoutContext.displayCompanies(out);
    	} catch(NamingException e){
    		e.printStackTrace();
    	}
    }
}