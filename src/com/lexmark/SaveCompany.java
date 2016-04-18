package com.lexmark;

import java.io.IOException;
import java.sql.SQLException;
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

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class SaveCompany extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = null;
		try {
            // Use this class if you have created the context.xml file.
        	Company comp = new Company();
        	Address addr = new Address();
            comp.setCompName(request.getParameter("name"));
            comp.setCompLocId(request.getParameter("complocid"));
            comp.setCompPhone(request.getParameter("phone"));
            comp.setCompEmail(request.getParameter("email"));
            comp.setCompUrl(request.getParameter("url"));
            addr.setAddressLine1(request.getParameter("addressline1"));
            addr.setAddressLine2(request.getParameter("addressline2"));
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
            	//SimpleDateFormat print = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            	//System.out.println(print.format(timestamp));
            	//Date date1 = print.parse(date.toString());
            	addr.setGeocodedOn(new java.sql.Timestamp(timestamp.getTime()));
            }
            comp.setCompAddress(addr);
            QueryWithContext qwc = new QueryWithContext();
            
            int comp_id = 0;
            try{
            	comp_id = qwc.saveCompany(comp);
            } catch (MySQLIntegrityConstraintViolationException e){
            	request.setAttribute("Error1001", "Duplicate company location id entered");
            	view = request.getRequestDispatcher("addCompany");
            	view.forward(request, response);
            } catch (SQLException se){
            	request.setAttribute("DBError", "Fatal Error occurred. Please contact system admin");
            	view = request.getRequestDispatcher("ErrorPage.jsp");
            	view.forward(request, response);
            }
            String[] arr = request.getParameterValues("technician_id");
            long tech_id = 0;
            if( arr != null ){
	            for (int i=0; i< arr.length; i++){
	            	//System.out.println(arr[i]);
	            	if(arr[i] != null && arr[i] != "")
	            		tech_id = Long.parseLong(arr[i]);
	            	if(tech_id > 0 && comp_id > 0)
	            		qwc.saveEmployment(tech_id, comp_id);
	            }
            }
            
            if(request.getParameter("type_of_add") != null && request.getParameter("type_of_add").equalsIgnoreCase("save"))
            	view = request.getRequestDispatcher("/CompanyDetails");
            else if(request.getParameter("type_of_add") != null && request.getParameter("type_of_add").equalsIgnoreCase("saveadd"))
            	view = request.getRequestDispatcher("/addCompany");
            else if(request.getParameter("type_of_add") != null && request.getParameter("type_of_add").equalsIgnoreCase("saveedit"))
            	view = request.getRequestDispatcher("/EditCompany?compid=" + comp_id);
            if (view != null)
            	view.forward(request, response);
            else
            	//error
            	Logger.getLogger("View is null in SaveCompany");
        } catch (Exception e) {
        	request.setAttribute("Error", "Fatal Error occurred. Please contact system admin");
        	view = request.getRequestDispatcher("ErrorPage.jsp");
        	view.forward(request, response);
        }
    }
}
