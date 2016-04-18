package com.lexmark;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class EditTraining extends HttpServlet{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long training_id = 0;
    	if (request.getParameter("trainingid") != null && request.getParameter("trainingid") != ""){
        		training_id = Long.parseLong(request.getParameter("trainingid"));
        }
		QueryWithContext qwc = new QueryWithContext();
		Training training = null;
		RequestDispatcher view = null;
		try {
			training = qwc.editTraining(training_id);
        }  catch (SQLException e) {
        	request.setAttribute("DBError", e.getMessage());
        	view = request.getRequestDispatcher("ErrorPage.jsp");
        	view.forward(request, response);
        } catch (Exception e) {
        	request.setAttribute("Error", "Fatal Error occurred. Please contact system admin");
        	view = request.getRequestDispatcher("ErrorPage.jsp");
        	view.forward(request, response);
        }
		request.setAttribute("training", training);
		view = request.getRequestDispatcher("EditTraining.jsp");
		view.forward(request, response);
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	long training_id = 0;
    	if (request.getParameter("trainingid") != null && request.getParameter("trainingid") != ""){
        		training_id = Long.parseLong(request.getParameter("trainingid"));
    	}
		QueryWithContext qwc = new QueryWithContext();
		Training training = null;
		RequestDispatcher view = null;
		try {
			training = qwc.editTraining(training_id);
        }  catch (SQLException e) {
        	request.setAttribute("DBError", e.getMessage());
        	view = request.getRequestDispatcher("ErrorPage.jsp");
        	view.forward(request, response);
        } catch (Exception e) {
        	request.setAttribute("Error", "Fatal Error occurred. Please contact system admin");
        	view = request.getRequestDispatcher("ErrorPage.jsp");
        	view.forward(request, response);
        }
		request.setAttribute("training", training);
		view = request.getRequestDispatcher("EditTraining.jsp");
		view.forward(request, response);
    }

}
