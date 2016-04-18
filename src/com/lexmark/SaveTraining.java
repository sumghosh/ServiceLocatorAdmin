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
 * Servlet implementation class SaveTraining
 */
@WebServlet("/SaveTraining")
public class SaveTraining extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveTraining() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Training training = new Training();
		if (request.getParameter("technician_id") != null && request.getParameter("technician_id") != "")
			training.setTechnicianId(Integer.parseInt(request.getParameter("technician_id")));
		if (request.getParameter("product_id") != null && request.getParameter("product_id") != "")
			training.setProductId(Integer.parseInt(request.getParameter("product_id")));
		training.setType(request.getParameter("cert_type"));
    	QueryWithContext qwc = new QueryWithContext();
    	RequestDispatcher view = null;
    	try {
			qwc.saveTraining(training);
        }  catch (SQLException e) {
        	request.setAttribute("DBError", e.getMessage());
        	view = request.getRequestDispatcher("ErrorPage.jsp");
        	view.forward(request, response);
        } catch (Exception e) {
        	request.setAttribute("Error", "Fatal Error occurred. Please contact system admin");
        	view = request.getRequestDispatcher("ErrorPage.jsp");
        	view.forward(request, response);
        }
    	view = request.getRequestDispatcher("TrainingDetails");
        view.forward(request, response);
	}

}
