package com.lexmark;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class DeleteTechnician extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	long tech_id = 0;
    	RequestDispatcher view = null;
        // Use this class if you have created the context.xml file.
		if(request.getParameter("techid")!=null){
			tech_id = Long.parseLong(request.getParameter("techid"));
		}
		QueryWithContext qwc = new QueryWithContext();
		try {
			qwc.deleteTechnician(tech_id);
        }  catch (SQLException e) {
        	request.setAttribute("DBError", e.getMessage());
        	view = request.getRequestDispatcher("ErrorPage.jsp");
        	view.forward(request, response);
        } catch (Exception e) {
        	request.setAttribute("Error", "Fatal Error occurred. Please contact system admin");
        	view = request.getRequestDispatcher("ErrorPage.jsp");
        	view.forward(request, response);
        }
		view = request.getRequestDispatcher("/TechnicianDetails");
		view.forward(request, response);
    }
}