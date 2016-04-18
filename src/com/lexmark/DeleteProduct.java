package com.lexmark;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DeleteProduct
 */
@WebServlet("/DeleteProduct")
public class DeleteProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteProduct() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // Use this class if you have created the context.xml file.
			long prod_id = 0;
			RequestDispatcher view = null;
			if(request.getParameter("prodid") != null){
				prod_id = Long.parseLong(request.getParameter("prodid"));
			}
			QueryWithContext qwc = new QueryWithContext();
			try {
				qwc.deleteProduct(prod_id);
	        }  catch (SQLException e) {
	        	request.setAttribute("DBError", e.getMessage());
	        	view = request.getRequestDispatcher("ErrorPage.jsp");
	        	view.forward(request, response);
	        } catch (Exception e) {
	        	request.setAttribute("Error", "Fatal Error occurred. Please contact system admin");
	        	view = request.getRequestDispatcher("ErrorPage.jsp");
	        	view.forward(request, response);
	        }
			view = request.getRequestDispatcher("/ProductDetails");
			view.forward(request, response);
    }
}
