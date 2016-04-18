package com.lexmark;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class EditCompany extends HttpServlet{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	long comp_id =0;
    	RequestDispatcher view = null;
    	if (request.getParameter("compid") != null && request.getParameter("compid") != ""){
    		comp_id = Long.parseLong(request.getParameter("compid"));
    	}
    	try {
			QueryWithContext qwc = new QueryWithContext();
			Company comp = qwc.editCompany(comp_id);
			request.setAttribute("company", comp);
			List<Long> techs = qwc.editEmploymentByComp(comp_id);
			request.setAttribute("technicians", techs);
			view = request.getRequestDispatcher("EditCompany.jsp");
			view.forward(request, response);
        } catch (SQLException e) {
        	request.setAttribute("DBError", "Fatal Error occurred. Please contact system admin");
        	view = request.getRequestDispatcher("ErrorPage.jsp");
        	view.forward(request, response);
        } catch (Exception e) {
        	request.setAttribute("Error", "Fatal Error occurred. Please contact system admin");
        	view = request.getRequestDispatcher("ErrorPage.jsp");
        	view.forward(request, response);
        }
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	long comp_id =0;
    	RequestDispatcher view = null;
    	if (request.getParameter("compid") != null && request.getParameter("compid") != ""){
    		comp_id = Long.parseLong(request.getParameter("compid"));
    	}
    	try {
			QueryWithContext qwc = new QueryWithContext();
			Company comp = qwc.editCompany(comp_id);
			request.setAttribute("company", comp);
			List<Long> techs = qwc.editEmploymentByComp(comp_id);
			request.setAttribute("technicians", techs);
			view = request.getRequestDispatcher("EditCompany.jsp");
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

}
