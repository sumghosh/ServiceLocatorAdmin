package com.lexmark;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SaveTechnician extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            if(request.getParameter("geocoded_on") != null && request.getParameter("geocoded_on") != ""){
            	DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm a", Locale.ENGLISH);
            	Date date = format.parse(request.getParameter("geocoded_on"));
            	Timestamp timestamp = new Timestamp(date.getTime());
            	addr.setGeocodedOn(new java.sql.Timestamp(timestamp.getTime()));
            }
            tech.setTechAddress(addr);
        	QueryWithContext qwc = new QueryWithContext();
            int tech_id = qwc.saveTechnician(tech);
            
            //Save related products or training info
            String[] prodArray = request.getParameterValues("product_id");
            String[] certArray = request.getParameterValues("certification_type");
            Training training = new Training();
            for (int i=0; i<prodArray.length; i++){
            	training.setTechnicianId(tech_id);
            	training.setProductId(Integer.parseInt(prodArray[i]));
            	training.setType(certArray[i]);
            	java.sql.Timestamp now = new java.sql.Timestamp(new java.util.Date().getTime());
            	training.setLastModified(now);
            	qwc.saveTraining(training);
            }

            // Save employment
            String[] arr = request.getParameterValues("company_id");
            long comp_id = 0;
            for (int i=0; i< arr.length; i++){
            	//System.out.println(arr[i]);
            	if(arr[i] != null && arr[i] != "")
            		comp_id = Long.parseLong(arr[i]);
            	if(tech_id > 0 && comp_id > 0)
            		System.out.println("nothing");
            		//qwc.saveEmployment(tech_id, comp_id);
            }
            RequestDispatcher view = null;
            if(request.getParameter("type_of_add") != null && request.getParameter("type_of_add").equalsIgnoreCase("save"))
            	view = request.getRequestDispatcher("/TechnicianDetails");
            else if(request.getParameter("type_of_add") != null && request.getParameter("type_of_add").equalsIgnoreCase("saveadd"))
            	view = request.getRequestDispatcher("/addTechnician");
            else if(request.getParameter("type_of_add") != null && request.getParameter("type_of_add").equalsIgnoreCase("saveedit"))
            	view = request.getRequestDispatcher("/EditTechnician?techid=" + tech_id);
            if(view != null)
            	view.forward(request, response);
            else
            	Logger.getLogger("View is null in SaveTechnician");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

