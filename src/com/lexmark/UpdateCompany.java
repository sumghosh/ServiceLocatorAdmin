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
public class UpdateCompany extends HttpServlet{
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	RequestDispatcher view = null;
    	try {
        	Company comp = new Company();
        	Address addr = new Address();
        	if(request.getParameter("compid")!=null){
        		comp.setId(Integer.parseInt(request.getParameter("compid")));
        	}
            comp.setCompName(request.getParameter("name"));
            comp.setCompLocId(request.getParameter("complocid"));
            comp.setCompPhone(request.getParameter("phone"));
            comp.setCompEmail(request.getParameter("email"));
            comp.setCompUrl(request.getParameter("url"));
            addr.setAddressLine1(request.getParameter("addressline1"));
            addr.setAddressLine2(request.getParameter("addressline2"));
            addr.setCity(request.getParameter("city"));
            if(request.getParameter("region")!=null){
               addr.setRegion(request.getParameter("region"));
            }
            addr.setPostalCode(request.getParameter("postalcode"));
            if(request.getParameter("country")!=null){
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
            comp.setCompAddress(addr);
        	QueryWithContext qwc = new QueryWithContext();
            qwc.updateCompany(comp);
            String[] arr = request.getParameterValues("technician_id");
            long tech_id = 0;
            List<Long> techArray = new ArrayList<Long>();
            for (int i=0; i< arr.length; i++){
            	//System.out.println(arr[i]);
            	if(arr[i] != null && arr[i] != ""){
            		tech_id = Long.parseLong(arr[i]);
            		techArray.add(tech_id);
            	}
            	//if(tech_id > 0 && comp.getId() > 0)
            		
            }
            qwc.updateEmploymentByComp(techArray, comp.getId());
            String rediredtURL = "";
            if(request.getParameter("button_type") != null && request.getParameter("button_type").equalsIgnoreCase("save"))
            	rediredtURL = "CompanyDetails";
            else if(request.getParameter("button_type") != null && request.getParameter("button_type").equalsIgnoreCase("saveadd"))
            	rediredtURL = "addCompany";
            else if(request.getParameter("button_type") != null && request.getParameter("button_type").equalsIgnoreCase("saveedit"))
            	rediredtURL = "EditCompany?compid=" + Integer.parseInt(request.getParameter("compid"));
            response.sendRedirect(rediredtURL);
        } catch (Exception e) {
        	request.setAttribute("Error", "Fatal Error occurred. Please contact system admin");
        	view = request.getRequestDispatcher("ErrorPage.jsp");
        	view.forward(request, response);
        }
    }

}
