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
 * Servlet implementation class UpdateTraining
 */
@WebServlet("/UpdateTraining")
public class UpdateTraining extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateTraining() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = null;
        try {
			Training training = new Training();
			if(request.getParameter("trainingid")!=null && request.getParameter("trainingid")!= ""){
				training.setId(Integer.parseInt(request.getParameter("trainingid")));
	    	}
			if (request.getParameter("technician_id") != null && request.getParameter("technician_id") != ""){
				training.setTechnicianId(Integer.parseInt(request.getParameter("technician_id")));
			}
			if (request.getParameter("product_id") != null && request.getParameter("product_id") != ""){
				training.setProductId(Integer.parseInt(request.getParameter("product_id")));
			}
			training.setType(request.getParameter("cert_type"));
	        QueryWithContext qwc = new QueryWithContext();
			qwc.updateTraining(training);
			String rediredtURL = "";
	        if(request.getParameter("button_type") != null && request.getParameter("button_type").equalsIgnoreCase("save"))
	        	rediredtURL = "TrainingDetails";
	        else if(request.getParameter("button_type") != null && request.getParameter("button_type").equalsIgnoreCase("saveadd"))
	        	rediredtURL = "addTraining";
	        else if(request.getParameter("button_type") != null && request.getParameter("button_type").equalsIgnoreCase("saveedit"))
	        	rediredtURL = "EditTraining?trainingid=" + Integer.parseInt(request.getParameter("trainingid"));
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
        //view = request.getRequestDispatcher("TrainingDetails");
        //view.forward(request, response);
	}

}

