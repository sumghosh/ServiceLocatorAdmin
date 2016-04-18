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
public class EditTechnician extends HttpServlet{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long tech_id = 0;
        RequestDispatcher view = null;
        try {
        	if(request.getParameter("techid") != null)
        	tech_id = Long.parseLong(request.getParameter("techid"));
        	QueryWithContext qwc = new QueryWithContext();
			Technician tech = qwc.editTechnician(tech_id);
			request.setAttribute("technician", tech);
			List<Long> comps = qwc.editEmploymentByTech(tech_id);
			request.setAttribute("companies", comps);
		
			List<Product> prods = qwc.displayProducts(1, 10000); //given high value (10000) to fetch all products
			request.setAttribute("products", prods);
			List<Training> trainings = qwc.getTrainingByTechId(tech_id);
			request.setAttribute("trainings", trainings);
        }  catch (SQLException e) {
        	request.setAttribute("DBError", e.getMessage());
        	view = request.getRequestDispatcher("ErrorPage.jsp");
        	view.forward(request, response);
        } catch (Exception e) {
        	request.setAttribute("Error", "Fatal Error occurred. Please contact system admin");
        	view = request.getRequestDispatcher("ErrorPage.jsp");
        	view.forward(request, response);
		}
		view = request.getRequestDispatcher("EditTechnician.jsp");
		view.forward(request, response);
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	long tech_id = 0;
        RequestDispatcher view = null;
        try {
        	if(request.getParameter("techid") != null)
    		tech_id = Long.parseLong(request.getParameter("techid"));
        	QueryWithContext qwc = new QueryWithContext();
			Technician tech = qwc.editTechnician(tech_id);
			request.setAttribute("technician", tech);
			List<Long> comps = qwc.editEmploymentByTech(tech_id);
			request.setAttribute("companies", comps);
		
			List<Product> prods = qwc.displayProducts(1, 10000); //given high value (10000) to fetch all products
			request.setAttribute("products", prods);
			List<Training> trainings = qwc.getTrainingByTechId(tech_id);
			request.setAttribute("trainings", trainings);
        }  catch (SQLException e) {
        	request.setAttribute("DBError", e.getMessage());
        	view = request.getRequestDispatcher("ErrorPage.jsp");
        	view.forward(request, response);
        } catch (Exception e) {
        	request.setAttribute("Error", "Fatal Error occurred. Please contact system admin");
        	view = request.getRequestDispatcher("ErrorPage.jsp");
        	view.forward(request, response);
		}
		view = request.getRequestDispatcher("EditTechnician.jsp");
		view.forward(request, response);
    }

}