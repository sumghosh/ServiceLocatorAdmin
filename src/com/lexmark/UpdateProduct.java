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
 * Servlet implementation class UpdateProduct
 */
@WebServlet("/UpdateProduct")
public class UpdateProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateProduct() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = null;
        try {
			Product prod = new Product();
			if(request.getParameter("prodid")!=null){
				prod.setId(Integer.parseInt(request.getParameter("prodid")));
	    	}
			prod.setName(request.getParameter("name"));
	        prod.setSlug(request.getParameter("slug"));
	        int categories = 0;
	        if(request.getParameter("category_1")!=null){
	        	categories += (Integer.parseInt(request.getParameter("category_1")));
	        }
	        if(request.getParameter("category_2")!=null){
	        	categories += (Integer.parseInt(request.getParameter("category_2")));
	        }
	        if(request.getParameter("category_4")!=null){
	        	categories += (Integer.parseInt(request.getParameter("category_4")));
	        }
	        if(request.getParameter("category_8")!=null){
	        	categories += (Integer.parseInt(request.getParameter("category_8")));
	        }
	        if(request.getParameter("category_16")!=null){
	        	categories += (Integer.parseInt(request.getParameter("category_16")));
	        }
	        if(request.getParameter("category_32")!=null){
	        	categories += (Integer.parseInt(request.getParameter("category_32")));
	        }
	        prod.setCategory(categories);
	        if(request.getParameter("active")!=null){
	        	if(request.getParameter("active").equals("on"))
	        		prod.setActive(1);
	        	else
	        		prod.setActive(0);
	        }
	        QueryWithContext qwc = new QueryWithContext();
			qwc.updateProduct(prod);
			String rediredtURL = "";
	        if(request.getParameter("button_type") != null && request.getParameter("button_type").equalsIgnoreCase("save"))
	        	rediredtURL = "ProductDetails";
	        else if(request.getParameter("button_type") != null && request.getParameter("button_type").equalsIgnoreCase("saveadd"))
	        	rediredtURL = "AddProduct";
	        else if(request.getParameter("button_type") != null && request.getParameter("button_type").equalsIgnoreCase("saveedit"))
	        	rediredtURL = "EditProduct?prodid=" + Integer.parseInt(request.getParameter("prodid"));
        	response.sendRedirect(rediredtURL);
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
