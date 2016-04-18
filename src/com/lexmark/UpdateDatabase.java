package com.lexmark;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UpdateDatabase {
	public String loginfo = "";
    public static Connection getConnection() throws NamingException {
        
    	Context context = null;
        DataSource datasource = null;
        Connection connect = null;
        try {
            // Get the context and create a connection
            context = new InitialContext();
            datasource = (DataSource) context.lookup("java:/comp/env/jdbc/dealerlocator"); //dev
            //datasource = (DataSource) context.lookup("java:/comp/env/jdbc/dlocator"); //QA
            connect = datasource.getConnection();
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        return connect;
    }
    
    public void saveEmployment(long comp_id, long tech_id){
       	PreparedStatement stmt = null;
    	Connection con = null;
        ResultSet resultSet = null;
        String selectQuery = "SELECT * from service_locator_employment WHERE technician_id = ? and company_id = ?";
    	try{
            con = getConnection();
 			con.setAutoCommit(false);
 			stmt = con.prepareStatement(selectQuery);
 			stmt.setLong(1, tech_id);
 			stmt.setLong(2, comp_id);
 			resultSet = stmt.executeQuery();
 			if (!resultSet.next()){
 				String insertQuery = "INSERT INTO service_locator_employment(technician_id, "
 						+ "company_id, last_modified) VALUES(?, ?, ?)";
 				stmt = con.prepareStatement(insertQuery);
 				stmt.setLong(1, tech_id);
 				stmt.setLong(2, comp_id);
 				Date now = new java.sql.Date(Calendar.getInstance().getTime().getTime()); 
 				stmt.setDate(3, now);
	 			stmt.execute();
	            con.commit();
	            
	            //Log ingo into xmlfeed table
	            loginfo = loginfo + "New employment created: ";
				
 			}
        	resultSet.close();
 			stmt.close();
         	con.close();      		
    	} catch (NamingException e) {
    		e.printStackTrace();
    	} catch(SQLException se){
    		se.printStackTrace();
    	} finally {
  	    	//	
    	}
    }
    public long saveCompany(Company comp){
    	PreparedStatement stmt = null;
    	Connection con = null;
        ResultSet resultSet = null;
        String query = "INSERT INTO service_locator_company(address1, address2, city, "
       		 + "region, postal, country, address_hash, location_type, lat, lng, "
       		 + "geocoded_on, location_id, name, phone, email, url) VALUES("
       		 +  "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        String selectQuery = "SELECT * from service_locator_company WHERE location_id = ?";
        Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        String addrhash = "";
        double lat = 0;
        double lng = 0;
        String loc_type = "";
        long comp_id = 0;
        try {
            con = getConnection();
 			con.setAutoCommit(false);

 			stmt = con.prepareStatement(selectQuery);
 			stmt.setString(1, comp.getCompLocId());
 			
 			// Execute the query and get the result set.
            resultSet = stmt.executeQuery();
 			if(!(resultSet.next())){
 				loginfo = loginfo + "Found new company location id: " + comp.getCompLocId();
	 			stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	 			Map<String, String> addrmap = new HashMap<String, String>();
	 			/*addrmap = geocode(comp.getCompAddress());
	 			if(addrmap!=null){
	 				addrhash = addrmap.get("addrHash");
	 				lat = Double.parseDouble(addrmap.get("lat"));
	 				lng = Double.parseDouble(addrmap.get("lng"));
	 				loc_type = addrmap.get("location_type");
	 			}*/
	 			
	 			stmt.setString(1, comp.getCompAddress().getAddressLine1());
	 			stmt.setString(2, comp.getCompAddress().getAddressLine2());
	 			stmt.setString(3, comp.getCompAddress().getCity());
	 			stmt.setString(4, comp.getCompAddress().getRegion());
	 			stmt.setString(5, comp.getCompAddress().getPostalCode());
	 			stmt.setString(6, comp.getCompAddress().getCountry());
	 			//geocoding elements
//	 			stmt.setString(7,  addrhash);
//	 			stmt.setString(8, loc_type);
//	 			stmt.setDouble(9, lat);
//	 			stmt.setDouble(10, lng);
	 			stmt.setString(7,  "");
	 			stmt.setString(8, "");
	 			stmt.setDouble(9, 0.00);
	 			stmt.setDouble(10, 0.00);
	 			stmt.setDate(11, date);
	 			//end geocoding elements
	 			stmt.setString(12, comp.getCompLocId());
	 			stmt.setString(13, comp.getCompName());
	 			stmt.setString(14, comp.getCompPhone());
	 			stmt.setString(15, comp.getCompEmail());
	 			stmt.setString(16, "");
	 			
	 			System.out.println("query-->"+query);
	 			
	 			//stmt.addBatch();
	 			//stmt.executeBatch();
	 			stmt.executeUpdate();
	 			ResultSet keys = stmt.getGeneratedKeys();    
	 			keys.next();  
	 			comp_id = keys.getInt(1);
	            con.commit();
	        	keys.close();	            
	            //Log ingo into xmlfeed table
	            loginfo = loginfo + "Company info saved: " + comp.getCompName();
 			} else {
 				//Company exists, return the id
 				comp_id = resultSet.getLong("id");
 			}
            // Close the connection and release the resources used.
        	resultSet.close();
            stmt.close();
         	con.close();
 			
 			//geocode(comp.getCompAddress());
    	} catch (NamingException e) {
			 e.printStackTrace();
		}catch (SQLException e) { 
    		 e.printStackTrace();
        }
        return comp_id;
    }
    
    public long saveTechnician(Technician tech){
    	PreparedStatement stmt = null;
        Connection con = null;
        ResultSet resultSet = null;
        String query = "INSERT INTO service_locator_technician(address1, address2, city, "
       		 + "region, postal, country, address_hash, location_type, lat, lng, geocoded_on, "
       		 + "user_id, first_name, last_name, phone, email, last_modified) VALUES("
       		 +  "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String selectQuery = "SELECT * from service_locator_technician where user_id = ?";
        String addrhash = "";
        double lat = 0;
        double lng = 0;
        String loc_type = "";
        long tech_id = 0;
        try{
            con = getConnection();
 			con.setAutoCommit(false);
 			stmt = con.prepareStatement(selectQuery);
 			stmt.setString(1, tech.getUserId());
 			
 			// Execute the query and get the result set.
            resultSet = stmt.executeQuery();
 			if(!resultSet.next()){
 				loginfo = loginfo + "Found new technician id: " + tech.getUserId();
 	 			stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	 			Map<String, String> addrmap = new HashMap<String, String>();
	 			Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
	 			/*addrmap = geocode(tech.getTechAddress());
	 			if(addrmap!=null){
	 				addrhash = addrmap.get("addrHash");
	 				lat = Double.parseDouble(addrmap.get("lat"));
	 				lng = Double.parseDouble(addrmap.get("lng"));
	 				loc_type = addrmap.get("location_type");
	 			}*/
 	        	
 	 			stmt.setString(1, tech.getTechAddress().getAddressLine1());
 	 			stmt.setString(2, tech.getTechAddress().getAddressLine2());
 	 			stmt.setString(3, tech.getTechAddress().getCity());
 	 			stmt.setString(4, tech.getTechAddress().getRegion());
 	 			stmt.setString(5, tech.getTechAddress().getPostalCode());
 	 			stmt.setString(6, tech.getTechAddress().getCountry());
 	 			//geocoding elements
/* 	 			stmt.setString(7, addrhash);
 	 			stmt.setString(8, loc_type);
 	 			stmt.setDouble(9, lat);
 	 			stmt.setDouble(10, lng);*/
 	 			stmt.setString(7, "");
 	 			stmt.setString(8, "");
 	 			stmt.setDouble(9, 0.00);
 	 			stmt.setDouble(10, 0.00);
 	 			stmt.setDate(11, date);
 	 			//end geocoding elements
 	 			stmt.setString(12, tech.getUserId());
 	 			stmt.setString(13, tech.getFirstName());
 	 			stmt.setString(14, tech.getLastName());
 	 			stmt.setString(15, ""); //default phone number is empty string
 	 			stmt.setString(16, ""); //default email is empty
 	 			stmt.setDate(17, date);
	 			stmt.executeUpdate();
	 			ResultSet keys = stmt.getGeneratedKeys();    
	 			keys.next();  
	 			tech_id = keys.getInt(1);
 	 			con.commit();
 	       	 	keys.close();
 	            loginfo = loginfo = "Technician info saved: " + tech.getFirstName() + " " + tech.getLastName(); 
 			} else {
 				tech_id = resultSet.getLong("id");
 			}
            // Close the connection and release the resources used.
       	 	resultSet.close();
 			stmt.close();
        	con.close();
        } catch (SQLException se) {
        	se.printStackTrace();
        } catch (NamingException e) {
			e.printStackTrace();
		}
        return tech_id;
    }
    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
        	urlString = urlString.replaceAll(" ", "%20");
            //String encodedURL = URLEncoder.encode(urlString, "UTF-8");
            //urlString = "https://maps.googleapis.com/maps/api/geocode/json?address=PO%20Box%20550727,Dallas,TX,USA,75355-0727&key=AIzaSyB4fY5sxTBmIV_RQ44BnLCro1oPNuto6MY";
        	URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            //reader = new BufferedReader(new InputStreamReader(((HttpURLConnection) (new URL(urlString)).openConnection()).getInputStream(), Charset.forName("UTF-8")));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read); 

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }
    public Map<String, String> geocode(Address addr){
    	final String apikey = "AIzaSyB4fY5sxTBmIV_RQ44BnLCro1oPNuto6MY"; //obtained from google developer's console
    	String googleURL = "https://maps.googleapis.com/maps/api/geocode/json";
    	boolean geocode = false;
    	Map<String, String> addrmap = new HashMap<String, String>();
    	if (addr != null){
    		if (addr.getAddressLine1() != null && !addr.getAddressLine1().isEmpty() ){
    			geocode = true;
    		}
    	    else if (addr.getPostalCode() != null && !addr.getPostalCode().isEmpty() ) {
    	    	geocode = true;
    	    }
    		//geocode
    		String geocodeURL = googleURL + "?address=" + addr.getAddressLine1() + ","+ addr.getCity() 
    				+ "," + addr.getRegion() + "," + addr.getCountry() + "," + addr.getPostalCode() 
    				+ "&key=" + apikey;
    		System.out.println("geocodeURL-->"+geocodeURL);
    		if(geocode){
	    		try {
	    		    JSONObject json;
	    		    String addrhash = "";
					json = new JSONObject(readUrl(geocodeURL));
					JSONArray results = (JSONArray) json.get("results");
					for (int i=0; i<results.length(); i++){
						JSONObject json1 = results.getJSONObject(i);
						JSONObject geometry = json1.getJSONObject("geometry");
						JSONObject location = geometry.getJSONObject("location");
						String loc_type = geometry.getString("location_type");
						String lat = location.getString("lat");
						String lng = location.getString("lng");
						addrhash = json1.getString("place_id");
						System.out.println("Address Hash: " + addrhash);
						System.out.println("Latitude: " + lat);
						System.out.println("Longitude: " + lng);
						System.out.println("Location Type: " + loc_type);
						addrmap.put("addrHash", addrhash);
						addrmap.put("lat", lat);
						addrmap.put("lng", lng);
						addrmap.put("location_type", loc_type);
						
					}
	    		} catch (JSONException e) {
	    		    e.printStackTrace();
	    		} catch (Exception e) {
					e.printStackTrace();
				}
    		}    		
    	}
		return addrmap;
    }
    public long saveProduct(Product prod){
    	String slug = makeSlug(prod.getName());
       	PreparedStatement stmt = null;
    	Connection con = null;
        ResultSet resultSet = null;
        String selectQuery = "SELECT * FROM service_locator_product WHERE slug = ?";
        String insertQuery = "INSERT INTO service_locator_product (name, slug, categories, active) "
        		+ "VALUES (?, ?, ?, ?)";
        long prod_id = 0;
        try{
            con = getConnection();
 			con.setAutoCommit(false);
 			stmt = con.prepareStatement(selectQuery);
 			stmt.setString(1, slug);
 			// Execute the query and get the result set.
            resultSet = stmt.executeQuery();
 			if(!resultSet.next()){
 	 			stmt = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
 				stmt.setString(1, prod.getName());
 				stmt.setString(2, slug);
 				stmt.setInt(3, guessCategories(slug));
 				stmt.setBoolean(4, true);
	 			stmt.executeUpdate();
	 			ResultSet keys = stmt.getGeneratedKeys();    
	 			keys.next();  
	 			prod_id = keys.getInt(1);
 	            con.commit();
 	       	 	keys.close();
 	            loginfo = loginfo = "New product created: " + prod.getName();
 			} else {
 				prod_id = resultSet.getLong("id");
 			}
	            // Close the connection and release the resources used.
       	 	resultSet.close();
            stmt.close();
        	con.close();
        	
        } catch(NamingException e) {
        	e.printStackTrace();
        } catch(SQLException se){
        	se.printStackTrace();
        }
        return prod_id;
    }
    private String makeSlug(String prodName){
    	prodName.replaceFirst("Lexmark ", "");
    	prodName.replaceAll(" ", "-").toLowerCase();
    	return prodName;
    }
    private int guessCategories(String slug){
    	int COLOR_LASER = 1;
    	int	MONO_LASER = 2;
    	int	MFP = 4;
    	int	DOT_MATRIX = 8;
    	int	INKJET = 16;
    	int	OTHER = 32;
    	int categories = 0;
    	if (slug.startsWith("cx"))
    		categories = COLOR_LASER | MFP;
    	else if (slug.startsWith("c"))
    		categories = COLOR_LASER;
    	else if (slug.startsWith("mx"))
    		categories = MONO_LASER | MFP;
    	else if (slug.startsWith("m"))
    		categories = MONO_LASER;
    	else if (slug.startsWith("x"))
    		categories = MFP;
    	else if (slug.startsWith("xm"))
    		categories = MFP;
    	else if (slug.startsWith("w"))
    		categories = MONO_LASER;
    	return categories;
    }
    public void saveTraining(long techId, long prodId){
       	PreparedStatement stmt = null;
    	Connection con = null;
        ResultSet resultSet = null;
        String selectQuery = "SELECT * FROM service_locator_training WHERE technician_id = ? AND product_id = ?";
        try{
            con = getConnection();
 			con.setAutoCommit(false);
 			stmt = con.prepareStatement(selectQuery);
 			stmt.setLong(1, techId);
 			stmt.setLong(2, prodId);
            resultSet = stmt.executeQuery();
 			if(!resultSet.next()){
 				String insertQuery = "INSERT INTO service_locator_training (technician_id, product_id,"
 						+ " type, last_modified) VALUES (?, ?, ?, ?)";
 	 			stmt = con.prepareStatement(insertQuery);
 	 			stmt.setLong(1, techId);
 	 			stmt.setLong(2, prodId);
 	 			stmt.setString(3, "o");
 	 			Date now = new java.sql.Date(Calendar.getInstance().getTime().getTime());
 	 			stmt.setDate(4, now);
 	 			stmt.execute();
 	            con.commit();
 	            // Close the connection and release the resources used.
 	            resultSet.close();
 	            stmt.close();
            	con.close();
 	            loginfo = loginfo = "New training created: ";
 			}		
        } catch(NamingException e) {
        	e.printStackTrace();
        } catch(SQLException se){
        	se.printStackTrace();
        }
    }
    public long saveXMLFeed(String fileName){
       	PreparedStatement stmt = null;
       	//Statement statement = null;
    	Connection con = null;
    	ResultSet resultSet = null;
    	long feed_id = 0;
    	String selectQuery = "SELECT MAX(id) FROM service_locator_xmlfeed";
    	String insertQuery = "INSERT INTO service_locator_xmlfeed(created_on, status, xml_file, log)"
    			+ " VALUES (?, ?, ?, ?)";
		try {
            con = getConnection();
			con.setAutoCommit(false);
			stmt = con.prepareStatement(selectQuery);
			//statement = con.prepareStatement();
 			resultSet = stmt.executeQuery(selectQuery);
 			if(resultSet!=null && resultSet.next()){
 				//feed_id = resultSet.getLong(0) + 1;
 				//System.out.println("Feed_id-->" + feed_id);
 	 			//ResultSet generatedKeys = stmt.getGeneratedKeys();
 	 			feed_id = resultSet.getLong(1) + 1;
 	 			//System.out.println("Feed_id-->" + feed_id);
	 			stmt = con.prepareStatement(insertQuery);
				Timestamp now = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
				stmt.setTimestamp(1, now);
				stmt.setString(2, Constants.FEED_STATUS_NEW);
				stmt.setString(3, fileName + "-" + feed_id);
				stmt.setString(4, loginfo);
				//System.out.println("insert Query-->" + insertQuery);
	 			stmt.execute();
	            con.commit();
	            // Close the connection and release the resources used.
	       	 	resultSet.close();
	            stmt.close();
	        	con.close();
 			}
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return feed_id;
    }
}
