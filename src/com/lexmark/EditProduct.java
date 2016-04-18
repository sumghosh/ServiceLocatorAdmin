package com.lexmark;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class EditProduct extends HttpServlet{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long prod_id = 0;
        RequestDispatcher view = null;
    	if(request.getParameter("prodid")!=null){
    		prod_id = Long.parseLong(request.getParameter("prodid"));
    	}
		QueryWithContext qwc = new QueryWithContext();
		Product prod = null;;
		try {
			prod = qwc.editProduct(prod_id);
        }  catch (SQLException e) {
        	request.setAttribute("DBError", e.getMessage());
        	view = request.getRequestDispatcher("ErrorPage.jsp");
        	view.forward(request, response);
        } catch (Exception e) {
        	request.setAttribute("Error", "Fatal Error occurred. Please contact system admin");
        	view = request.getRequestDispatcher("ErrorPage.jsp");
        	view.forward(request, response);
        }
		request.setAttribute("product", prod);
		view = request.getRequestDispatcher("EditProduct.jsp");
		view.forward(request, response);
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long prod_id = 0;
        RequestDispatcher view = null;
    	if(request.getParameter("prodid")!=null){
    		prod_id = Long.parseLong(request.getParameter("prodid"));
    	}
		QueryWithContext qwc = new QueryWithContext();
		Product prod = null;
		try {
			prod = qwc.editProduct(prod_id);
        }  catch (SQLException e) {
        	request.setAttribute("DBError", e.getMessage());
        	view = request.getRequestDispatcher("ErrorPage.jsp");
        	view.forward(request, response);
        } catch (Exception e) {
        	request.setAttribute("Error", "Fatal Error occurred. Please contact system admin");
        	view = request.getRequestDispatcher("ErrorPage.jsp");
        	view.forward(request, response);
        }
		request.setAttribute("product", prod);
		view = request.getRequestDispatcher("EditProduct.jsp");
		view.forward(request, response);
    }

}
