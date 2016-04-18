package com.lexmark;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class AddTechnician extends HttpServlet{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		QueryWithContext qwc = new QueryWithContext();
		RequestDispatcher view = null;
		try {
			List<Product> prods = qwc.displayProducts(1, 10000); //Big number is used to fetch all the products
			request.setAttribute("products", prods);
		} catch (Exception e) {
        	request.setAttribute("Error", "Fatal Error occurred. Please contact system admin");
        	view = request.getRequestDispatcher("ErrorPage.jsp");
        	view.forward(request, response);
		}
		view = request.getRequestDispatcher("AddTechnician.jsp");
		view.forward(request, response);
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		QueryWithContext qwc = new QueryWithContext();
		RequestDispatcher view = null;
		try {
			List<Product> prods = qwc.displayProducts(1, 10000); //Big number is used to fetch all the products
			request.setAttribute("products", prods);
		} catch (Exception e) {
        	request.setAttribute("Error", "Fatal Error occurred. Please contact system admin");
        	view = request.getRequestDispatcher("ErrorPage.jsp");
        	view.forward(request, response);
		}
		view = request.getRequestDispatcher("AddTechnician.jsp");
		view.forward(request, response);
    }
}