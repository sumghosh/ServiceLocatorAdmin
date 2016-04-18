package com.lexmark;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class UpdateTechnician extends HttpServlet{
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	RequestDispatcher view = null;
    	try {
        	Technician tech = new Technician();
        	Address addr = new Address();
        	if(request.getParameter("techid")!=null){
        		tech.setId(Integer.parseInt(request.getParameter("techid")));
        	}
            tech.setUserId(request.getParameter("user_id"));
            tech.setFirstName(request.getParameter("first_name"));
            tech.setLastName(request.getParameter("last_name"));
            tech.setPhone(request.getParameter("phone"));
            tech.setEmail(request.getParameter("email"));
            addr.setAddressLine1(request.getParameter("address1"));
            addr.setAddressLine2(request.getParameter("address2"));
            addr.setCity(request.getParameter("city"));
            if(request.getParameter("region")!=null)
            {
               addr.setRegion(request.getParameter("region"));
            }
            addr.setPostalCode(request.getParameter("postalcode"));
            if(request.getParameter("country")!=null)
            {
               addr.setCountry(request.getParameter("country"));
            }
            addr.setAddressHash(request.getParameter("address_hash"));
            addr.setLocationType(request.getParameter("location_type"));
            if(request.getParameter("lat") != null){
            	addr.setLatitude(Double.parseDouble(request.getParameter("lat")));
            }
            if(request.getParameter("lng") != null){
            	addr.setLongitude(Double.parseDouble(request.getParameter("lng")));
            }
            /*if(request.getParameter("geocoded_on") != null && request.getParameter("geocoded_on") != ""){
            	DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm a", Locale.ENGLISH);
            	Date date = format.parse(request.getParameter("geocoded_on"));
            	Timestamp timestamp = new Timestamp(date.getTime());
            	addr.setGeocodedOn(new java.sql.Timestamp(timestamp.getTime()));
            }*/
            tech.setTechAddress(addr);
        	QueryWithContext qwc = new QueryWithContext();
            qwc.updateTechnician(tech);
            // Update related products 
            String[] prodArray = request.getParameterValues("product_id");
            String[] certArray = request.getParameterValues("certification_type");
            List<Training> trainings = new ArrayList<Training>();
            for (int i=0; i<prodArray.length; i++){
            	if(prodArray[i] != null && prodArray[i] != ""){
            		Training training = new Training();
	            	training.setTechnicianId(tech.getId());
	            	training.setProductId(Integer.parseInt(prodArray[i]));
	            	training.setType(certArray[i]);
	            	java.sql.Timestamp now = new java.sql.Timestamp(new java.util.Date().getTime());
	            	training.setLastModified(now);
	            	trainings.add(training);
            	}
            }
            qwc.updateTrainingByTech(trainings, tech.getId());
            
            // Update related companies
            String[] arr = request.getParameterValues("company_id");
            long comp_id = 0;
            List<Long> compArray = new ArrayList<Long>();
            for (int i=0; i< arr.length; i++){
            	//System.out.println(arr[i]);
            	if(arr[i] != null && arr[i] != "")
            		comp_id = Long.parseLong(arr[i]);
            		compArray.add(comp_id);
            	//if(tech_id > 0 && comp.getId() > 0)
            		
            }
            qwc.updateEmploymentByTech(compArray, tech.getId());
            String rediredtURL = "";
            if(request.getParameter("button_type") != null && request.getParameter("button_type").equalsIgnoreCase("save"))
            	rediredtURL = "TechnicianDetails";
            else if(request.getParameter("button_type") != null && request.getParameter("button_type").equalsIgnoreCase("saveadd"))
            	rediredtURL = "addTechnician";
            else if(request.getParameter("button_type") != null && request.getParameter("button_type").equalsIgnoreCase("saveedit"))
            	rediredtURL = "EditTechnician?techid=" + Integer.parseInt(request.getParameter("techid"));
            response.sendRedirect(rediredtURL);
        } catch (Exception e) {
        	request.setAttribute("Error", "Fatal Error occurred. Please contact system admin");
        	view = request.getRequestDispatcher("ErrorPage.jsp");
        	view.forward(request, response);
        }
    }
}

