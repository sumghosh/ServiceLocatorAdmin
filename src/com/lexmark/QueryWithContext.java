package com.lexmark;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.lexmark.Company;
import com.lexmark.google.auth.PasswordHasher;

public class QueryWithContext {
	private int noOfRecords;
    public static Connection getConnection() throws NamingException, SQLException {
        
    	Context context = null;
        DataSource datasource = null;
        Connection connect = null;
        //Statement statement = null;

        try {
            // Get the context and create a connection
            context = new InitialContext();
            //datasource = (DataSource) context.lookup("java:/comp/env/jdbc/dlocator"); //for QA
            datasource = (DataSource) context.lookup("java:/comp/env/jdbc/dealerlocator");
            connect = datasource.getConnection();
        } catch (SQLException e) {
        	throw new SQLException("Error when connecting database");
        }
        return connect;
    }
	
	public List<Company> displayCompanies(int offset, int noOfRecords) throws NamingException, SQLException {
        Statement statement = null;
        List<Company> list= new ArrayList<Company>();
        ResultSet resultSet = null;
        Connection con = null;
    	con = getConnection();
        // Create the statement to be used to get the results.
        statement = con.createStatement();
        String query = "SELECT SQL_CALC_FOUND_ROWS * FROM service_locator_company order by id desc "
        		+ "limit " + offset + ", " + noOfRecords;

        // Execute the query and get the result set.
        resultSet = statement.executeQuery(query);
        //out.println("<strong>Printing result using context file...</strong><br>");
        while (resultSet.next()) {
            Company company = new Company();
            Address compAddress = new Address();
            company.setId(resultSet.getInt("id"));
        	company.setCompName(resultSet.getString("name"));
        	company.setCompLocId(resultSet.getString("location_id"));
        	company.setCompPhone(resultSet.getString("phone"));
        	compAddress.setAddressLine1(resultSet.getString("address1"));
        	compAddress.setAddressLine2(resultSet.getString("address2"));
        	compAddress.setCity(resultSet.getString("city"));
        	compAddress.setRegion(resultSet.getString("region"));
        	compAddress.setCountry(resultSet.getString("country"));
        	company.setCompAddress(compAddress); 
        	list.add(company);
        }
     // Close the connection and release the resources used.
        resultSet.close();
        resultSet = statement.executeQuery("SELECT FOUND_ROWS()");
        if(resultSet.next())
            this.noOfRecords = resultSet.getInt(1);
        
        resultSet.close();
  	  	con.close();
  	  	statement.close();

        return list;
    }
	
	public int getNoOfRecords() {
        return noOfRecords;
    }
	
	public List<Company> searchCompanies(String q, int offset, int noOfRecords) throws NamingException, SQLException {
        Statement statement = null;
        List<Company> list= new ArrayList<Company>();
        ResultSet resultSet = null;
        Connection con = null;
    	con = getConnection();
        // Create the statement to be used to get the results.
        statement = con.createStatement();
        String query = "(SELECT SQL_CALC_FOUND_ROWS * FROM service_locator_company "
        		+ "where name like '%" + q + "%' "
        		+ "limit " + offset + ", " + noOfRecords + ") order by id desc";

        // Execute the query and get the result set.
        resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            Company company = new Company();
            Address compAddress = new Address();
            company.setId(resultSet.getInt("id"));
        	company.setCompName(resultSet.getString("name"));
        	company.setCompLocId(resultSet.getString("location_id"));
        	company.setCompPhone(resultSet.getString("phone"));
        	compAddress.setAddressLine1(resultSet.getString("address1"));
        	compAddress.setAddressLine2(resultSet.getString("address2"));
        	compAddress.setCity(resultSet.getString("city"));
        	compAddress.setRegion(resultSet.getString("region"));
        	compAddress.setCountry(resultSet.getString("country"));
        	company.setCompAddress(compAddress); 
        	list.add(company);
        }
        resultSet.close();
        resultSet = statement.executeQuery("SELECT FOUND_ROWS()");
        if(resultSet.next())
            this.noOfRecords = resultSet.getInt(1);

        resultSet.close();
        // Close the connection and release the resources used.
        con.close();
  	  	statement.close();

        return list;
    }
	
	public List<Company> sortCompanies(String q, String o, int offset, int noOfRecords) throws NamingException, SQLException {
        Statement statement = null;
        List<Company> list= new ArrayList<Company>();
        ResultSet resultSet = null;
        Connection con = null;
    	con = getConnection();
        // Create the statement to be used to get the results.
        statement = con.createStatement();
        String query = "(SELECT * FROM service_locator_company where name like '%" 
        				+ q + "%' limit " + offset + ", " + noOfRecords + ") order by " + o ;

        // Execute the query and get the result set.
        resultSet = statement.executeQuery(query);
        //out.println("<strong>Printing result using context file...</strong><br>");
        while (resultSet.next()) {
            Company company = new Company();
            Address compAddress = new Address();
            company.setId(resultSet.getInt("id"));
        	company.setCompName(resultSet.getString("name"));
        	company.setCompLocId(resultSet.getString("location_id"));
        	company.setCompPhone(resultSet.getString("phone"));
        	compAddress.setAddressLine1(resultSet.getString("address1"));
        	compAddress.setAddressLine2(resultSet.getString("address2"));
        	compAddress.setCity(resultSet.getString("city"));
        	compAddress.setRegion(resultSet.getString("region"));
        	compAddress.setCountry(resultSet.getString("country"));
        	company.setCompAddress(compAddress); 
        	list.add(company);
        }
        resultSet.close();
        resultSet = statement.executeQuery("SELECT FOUND_ROWS()");
        if(resultSet.next())
            this.noOfRecords = resultSet.getInt(1);
     // Close the connection and release the resources used.
        resultSet.close();
  	  	con.close();
  	  	statement.close();

        return list;
    }
	
	public int saveCompany(Company comp) throws NamingException, SQLException {
    	PreparedStatement stmt = null;
    	Connection con = null;
        con = getConnection();
		con.setAutoCommit(false);
		String insertQuery = "INSERT INTO service_locator_company(name, location_id, "
							+ "phone, email, url, address1, address2, city, "
							+ "region, postal, country, address_hash, location_type, "
							+ "lat, lng, geocoded_on) "
							+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		stmt = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, comp.getCompName());
		stmt.setString(2, comp.getCompLocId());
		stmt.setString(3, comp.getCompPhone());
		stmt.setString(4, comp.getCompEmail());
		stmt.setString(5, comp.getCompUrl()); 
		stmt.setString(6, comp.getCompAddress().getAddressLine1());
		stmt.setString(7, comp.getCompAddress().getAddressLine2());
		stmt.setString(8, comp.getCompAddress().getCity());
		stmt.setString(9, comp.getCompAddress().getRegion());
		stmt.setString(10, comp.getCompAddress().getPostalCode());
		stmt.setString(11, comp.getCompAddress().getCountry());
		stmt.setString(12,  comp.getCompAddress().getAddressHash());
		stmt.setString(13,  comp.getCompAddress().getLocationType());
		stmt.setDouble(14, comp.getCompAddress().getLatitude());
		stmt.setDouble(15, comp.getCompAddress().getLongitude());
		stmt.setTimestamp(16, comp.getCompAddress().getGeocodedOn());
		
		int affectedRows = stmt.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating user failed, no rows affected.");
        }
        ResultSet generatedKeys = stmt.getGeneratedKeys();
        if (generatedKeys.next()) {
            comp.setId(generatedKeys.getInt(1));
        }
        else {
            throw new SQLException("Creating user failed, no ID obtained.");
        }
        con.commit();
		stmt.close();
		con.close();
        return comp.getId();
	}

	public void updateCompany(Company comp) throws NamingException, SQLException {
    	PreparedStatement stmt = null;
    	Connection con = null;
        con = getConnection();
		con.setAutoCommit(false);
		String updateQuery = "UPDATE service_locator_company SET name = ?, location_id = ?, "
							+ "phone = ?, email = ?, url = ?, address1 = ?, address2 = ?, city = ?, "
							+ "region = ?, postal = ?, country = ?, address_hash = ?, location_type = ?, "
							+ "lat = ?, lng = ? "
							+ "WHERE id = ?";
		stmt = con.prepareStatement(updateQuery);
		stmt.setString(1, comp.getCompName());
		stmt.setString(2, comp.getCompLocId());
		stmt.setString(3, comp.getCompPhone());
		stmt.setString(4, comp.getCompEmail());
		stmt.setString(5, comp.getCompUrl());
		stmt.setString(6, comp.getCompAddress().getAddressLine1());
		stmt.setString(7, comp.getCompAddress().getAddressLine2());
		stmt.setString(8, comp.getCompAddress().getCity());
		stmt.setString(9, comp.getCompAddress().getRegion());
		stmt.setString(10, comp.getCompAddress().getPostalCode());
		stmt.setString(11, comp.getCompAddress().getCountry());
		stmt.setString(12,  comp.getCompAddress().getAddressHash());
		stmt.setString(13,  comp.getCompAddress().getLocationType());
		stmt.setDouble(14, comp.getCompAddress().getLatitude());
		stmt.setDouble(15, comp.getCompAddress().getLongitude());
		//stmt.setTimestamp(16, comp.getCompAddress().getGeocodedOn());
		stmt.setInt(16, comp.getId());
		stmt.execute();
		con.commit();
		stmt.close();
		con.close();
	}

	public Company editCompany(long comp_id) throws NamingException, SQLException {
    	PreparedStatement stmt = null;
    	Connection con = null;
    	ResultSet resultSet = null;
    	Company company = null;
        con = getConnection();
		con.setAutoCommit(false);
        String editQuery = "SELECT * FROM service_locator_company where id = ?";
		stmt = con.prepareStatement(editQuery);
		stmt.setLong(1, comp_id);
		
		// execute the prepared statement
		resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            company = new Company();
            Address compAddress = new Address();
            company.setId(resultSet.getInt("id"));
        	company.setCompName(resultSet.getString("name"));
        	company.setCompLocId(resultSet.getString("location_id"));
        	company.setCompPhone(resultSet.getString("phone"));
        	company.setCompEmail(resultSet.getString("email"));
        	company.setCompUrl(resultSet.getString("url"));
        	compAddress.setAddressLine1(resultSet.getString("address1"));
        	compAddress.setAddressLine2(resultSet.getString("address2"));
        	compAddress.setCity(resultSet.getString("city"));
        	compAddress.setRegion(resultSet.getString("region"));
        	compAddress.setPostalCode(resultSet.getString("postal"));
        	compAddress.setCountry(resultSet.getString("country"));
        	compAddress.setAddressHash(resultSet.getString("address_hash"));
        	compAddress.setLocationType(resultSet.getString("location_type"));
        	compAddress.setLatitude(resultSet.getDouble("lat"));
        	compAddress.setLongitude(resultSet.getDouble("lng"));
        	if(resultSet.getString("geocoded_on") != null){
        		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        		try {
					Date date = format.parse(resultSet.getString("geocoded_on"));
					compAddress.setGeocodedOn(new java.sql.Timestamp(date.getTime()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
        	}
        	company.setCompAddress(compAddress); 
        }
        resultSet.close();
        stmt.close();
        con.close();

        return company;
    }

    public void deleteCompany(long comp_id) throws NamingException, SQLException {
    	PreparedStatement stmt = null;
    	Connection con = null;
        con = getConnection();
		con.setAutoCommit(false);
		String delEmploymentQuery = "DELETE FROM service_locator_employment where company_id = ?";
		stmt = con.prepareStatement(delEmploymentQuery);
		stmt.setLong(1, comp_id);
		// execute the prepared statement
	    stmt.execute();
        String delQuery = "DELETE FROM service_locator_company where id = ?";
		stmt = con.prepareStatement(delQuery);
		stmt.setLong(1, comp_id);
		// execute the prepared statement
	    stmt.execute();
	    con.commit();   
	    con.close();
    }
	
	public List<Product> displayProducts(int offset, int noOfRecords) throws NamingException, SQLException {
        Statement statement = null;
        List<Product> list= new ArrayList<Product>();
        ResultSet resultSet = null;
        Connection con = null;
        try {
        	con = getConnection();
            // Create the statement to be used to get the results.
            statement = con.createStatement();
            String query = "SELECT SQL_CALC_FOUND_ROWS * FROM service_locator_product order by id desc "
            		+ "limit " + offset + ", " + noOfRecords;

            // Execute the query and get the result set.
            resultSet = statement.executeQuery(query);
            //out.println("<strong>Printing result using context file...</strong><br>");
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setSlug(resultSet.getString("slug"));
                //int category = resultSet.getInt("categories");
                product.setCategory(resultSet.getInt("categories"));
                product.setActive(Integer.parseInt(resultSet.getString("active")));
            	list.add(product);
            }
            // Close the connection and release the resources used.
            resultSet.close();
            resultSet = statement.executeQuery("SELECT FOUND_ROWS()");
            if(resultSet.next())
                this.noOfRecords = resultSet.getInt(1);
            
            resultSet.close();
      	  	con.close();
      	  	statement.close();

        } catch (SQLException e) {
        	throw new SQLException("Exception occurred in displayProducts during database operation.");
        } 
        return list;
    }

	public List<Product> searchProducts(String q, int offset, int noOfRecords) throws NamingException, SQLException {
        Statement statement = null;
        List<Product> list= new ArrayList<Product>();
        ResultSet resultSet = null;
        Connection con = null;
        try {
        	con = getConnection();
            // Create the statement to be used to get the results.
            statement = con.createStatement();
            String query = "(SELECT SQL_CALC_FOUND_ROWS * FROM service_locator_product where name like '%" + q + "%' "
            		+ "limit " + offset + ", " + noOfRecords + ") order by id desc";

            // Execute the query and get the result set.
            resultSet = statement.executeQuery(query);
            //out.println("<strong>Printing result using context file...</strong><br>");
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setSlug(resultSet.getString("slug"));
                product.setCategory(Integer.parseInt(resultSet.getString("categories")));
                product.setActive(Integer.parseInt(resultSet.getString("active")));
            	list.add(product);
            }
         // Close the connection and release the resources used.
            resultSet.close();
            resultSet = statement.executeQuery("SELECT FOUND_ROWS()");
            if(resultSet.next())
                this.noOfRecords = resultSet.getInt(1);
            
            resultSet.close();
      	  	con.close();
      	  	statement.close();

        } catch (SQLException e) {
        	throw new SQLException("Error in searchProducts during database operation");
        } 
        return list;
    }

	public List<Product> sortProducts(String q, String o, int offset, int noOfRecords) throws NamingException, SQLException {
        Statement statement = null;
        List<Product> list= new ArrayList<Product>();
        ResultSet resultSet = null;
        Connection con = null;
        try {
        	con = getConnection();
            // Create the statement to be used to get the results.
            statement = con.createStatement();
            String query = "(SELECT SQL_CALC_FOUND_ROWS * FROM service_locator_product where name like '%" + q + "%' "
            		+ "limit " + offset + ", " + noOfRecords + ") order by " + o;

            // Execute the query and get the result set.
            resultSet = statement.executeQuery(query);
            //out.println("<strong>Printing result using context file...</strong><br>");
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setSlug(resultSet.getString("slug"));
                product.setCategory(Integer.parseInt(resultSet.getString("categories")));
                product.setActive(Integer.parseInt(resultSet.getString("active")));
            	list.add(product);
            }
         // Close the connection and release the resources used.
            resultSet.close();
            resultSet = statement.executeQuery("SELECT FOUND_ROWS()");
            if(resultSet.next())
                this.noOfRecords = resultSet.getInt(1);
            
            resultSet.close();
      	  	con.close();
      	  	statement.close();

        } catch (SQLException e) {
        	throw new SQLException("Error in sortProducts during database operation");
        } 
        return list;
    }

    public void deleteProduct(long prod_id) throws NamingException, SQLException {
    	PreparedStatement stmt = null;
    	Connection con = null;
        con = getConnection();
		con.setAutoCommit(false);
		
		//First delete from service_locator_training
		String delQuery = "DELETE FROM service_locator_training where product_id = ?";
		stmt = con.prepareStatement(delQuery);
		stmt.setLong(1, prod_id);
		stmt.execute();
	    //con.commit();
	    
        delQuery = "DELETE FROM service_locator_product where id = ?";
		stmt = con.prepareStatement(delQuery);
		stmt.setLong(1, prod_id);
		
		// execute the prepared statement
	    stmt.execute();
	    con.commit();
	    stmt.close();
	    con.close();
    }

    public void saveProduct(Product prod) throws NamingException, SQLException {
    	PreparedStatement stmt = null;
    	Connection con = null;
        con = getConnection();
		con.setAutoCommit(false);
		String insertQuery = "INSERT INTO service_locator_product(name, slug, "
							+ "categories, active) "
							+ "VALUES(?, ?, ?, ?)";
		stmt = con.prepareStatement(insertQuery);
		stmt.setString(1, prod.getName());
		stmt.setString(2, prod.getSlug());
		stmt.setInt(3, prod.getCategory());
		stmt.setInt(4, prod.getActive());
		stmt.execute();
		con.commit();
		stmt.close();
		con.close();
    }
    
    public Product editProduct(long prod_id) throws NamingException, SQLException {
    	PreparedStatement stmt = null;
    	Connection con = null;
    	ResultSet resultSet = null;
    	Product product = null;
        con = getConnection();
		con.setAutoCommit(false);
        String editQuery = "SELECT * FROM service_locator_product where id = ?";
		stmt = con.prepareStatement(editQuery);
		stmt.setLong(1, prod_id);
		
		// execute the prepared statement
		resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            product = new Product();
            product.setId(resultSet.getInt("id"));
        	product.setName(resultSet.getString("name"));
        	product.setSlug(resultSet.getString("slug"));
        	if (resultSet.getString("categories")!=null){
        		product.setCategory(resultSet.getInt("categories"));
        	}
        	if (resultSet.getString("active")!=null){
        		product.setActive(resultSet.getInt("active"));
        	}
        }
        resultSet.close();
        stmt.close();
        con.close(); 
    	return product;
    }

	public void updateProduct(Product prod) throws NamingException, SQLException {
    	PreparedStatement stmt = null;
    	Connection con = null;
        con = getConnection();
		con.setAutoCommit(false);
		String updateQuery = "UPDATE service_locator_product SET name = ?, slug = ?, "
							+ "categories = ?, active = ? "
							+ "WHERE id = ?";
		stmt = con.prepareStatement(updateQuery);
		stmt.setString(1, prod.getName());
		stmt.setString(2, prod.getSlug());
		stmt.setInt(3, prod.getCategory());
		stmt.setInt(4, prod.getActive());
		stmt.setInt(5, prod.getId());
		stmt.execute();
		con.commit();
		stmt.close();
		con.close();
	}

	public List<Technician> displayTechnicians(int offset, int noOfRecords) throws NamingException, SQLException {
        Statement statement = null;
        List<Technician> list= new ArrayList<Technician>();
        ResultSet resultSet = null;
        Connection con = null;
        try {
        	con = getConnection();
            // Create the statement to be used to get the results.
            statement = con.createStatement();
            String query = "SELECT SQL_CALC_FOUND_ROWS * FROM service_locator_technician order by id desc "
            			+ "limit " + offset + ", " + noOfRecords;
            // Execute the query and get the result set.
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Technician tech = new Technician();
                Address techAddress = new Address();
                tech.setId(resultSet.getInt("id"));
                tech.setUserId(resultSet.getString("user_id"));
                tech.setFirstName(resultSet.getString("first_name"));
                tech.setLastName(resultSet.getString("last_name"));
                tech.setEmail(resultSet.getString("email"));
                tech.setPhone(resultSet.getString("phone"));
                techAddress.setAddressLine1(resultSet.getString("address1"));
                techAddress.setAddressLine2(resultSet.getString("address2"));
                techAddress.setCity(resultSet.getString("city"));
                techAddress.setRegion(resultSet.getString("region"));
                techAddress.setCountry(resultSet.getString("country"));
                tech.setLastModified(resultSet.getString("last_modified"));
                tech.setTechAddress(techAddress); 
            	list.add(tech);
            }
         // Close the connection and release the resources used.
            resultSet.close();
            resultSet = statement.executeQuery("SELECT FOUND_ROWS()");
            if(resultSet.next())
                this.noOfRecords = resultSet.getInt(1);
            
            resultSet.close();
      	  	con.close();
      	  	statement.close();

        } catch (SQLException e) {
        	throw new SQLException("Error in displayTechnician while database operation");
        } 
        return list;
    }
	
	public List<Technician> searchTechnicians(String q, int offset, int noOfRecords) throws NamingException, SQLException {
        Statement statement = null;
        List<Technician> list= new ArrayList<Technician>();
        ResultSet resultSet = null;
        Connection con = null;
        try {
        	con = getConnection();
            // Create the statement to be used to get the results.
            statement = con.createStatement();
            String query = "(SELECT SQL_CALC_FOUND_ROWS * FROM service_locator_technician where first_name like "
            		+ "'%" + q + "%' OR last_name like '%" + q + "%' "
            				+ "limit " + offset + ", " + noOfRecords + ") order by id desc";

            // Execute the query and get the result set.
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Technician tech = new Technician();
                Address techAddress = new Address();
                tech.setId(resultSet.getInt("id"));
                tech.setUserId(resultSet.getString("user_id"));
                tech.setFirstName(resultSet.getString("first_name"));
                tech.setLastName(resultSet.getString("last_name"));
                tech.setEmail(resultSet.getString("email"));
                tech.setPhone(resultSet.getString("phone"));
                techAddress.setAddressLine1(resultSet.getString("address1"));
                techAddress.setAddressLine2(resultSet.getString("address2"));
                techAddress.setCity(resultSet.getString("city"));
                techAddress.setRegion(resultSet.getString("region"));
                techAddress.setCountry(resultSet.getString("country"));
                tech.setLastModified(resultSet.getString("last_modified"));
                tech.setTechAddress(techAddress); 
            	list.add(tech);
            }
         // Close the connection and release the resources used.
            resultSet.close();
            resultSet = statement.executeQuery("SELECT FOUND_ROWS()");
            if(resultSet.next())
                this.noOfRecords = resultSet.getInt(1);
            
            resultSet.close();
      	  	con.close();
      	  	statement.close();

        } catch (SQLException e) {
        	throw new SQLException("Error in searchTechnicians while database operation");
        } 
        return list;
    }
	
	public List<Technician> sortTechnicians(String q, String o, int offset, int noOfRecords) throws NamingException, SQLException {
        Statement statement = null;
        List<Technician> list= new ArrayList<Technician>();
        ResultSet resultSet = null;
        Connection con = null;
        try {
        	con = getConnection();
            // Create the statement to be used to get the results.
            statement = con.createStatement();
            String query = "(SELECT SQL_CALC_FOUND_ROWS * FROM service_locator_technician where first_name like '%" + q + "%' "
            		+ "OR last_name like '%" + q + "%' limit " + offset + ", " + noOfRecords + ") order by " + o;

            // Execute the query and get the result set.
            resultSet = statement.executeQuery(query);
            //out.println("<strong>Printing result using context file...</strong><br>");
            while (resultSet.next()) {
                Technician tech = new Technician();
                Address techAddress = new Address();
                tech.setId(resultSet.getInt("id"));
                tech.setUserId(resultSet.getString("user_id"));
                tech.setFirstName(resultSet.getString("first_name"));
                tech.setLastName(resultSet.getString("last_name"));
                tech.setEmail(resultSet.getString("email"));
                tech.setPhone(resultSet.getString("phone"));
                techAddress.setAddressLine1(resultSet.getString("address1"));
                techAddress.setAddressLine2(resultSet.getString("address2"));
                techAddress.setCity(resultSet.getString("city"));
                techAddress.setRegion(resultSet.getString("region"));
                techAddress.setCountry(resultSet.getString("country"));
                tech.setLastModified(resultSet.getString("last_modified"));
                tech.setTechAddress(techAddress); 
            	list.add(tech);
            }
         // Close the connection and release the resources used.
            resultSet.close();
            resultSet = statement.executeQuery("SELECT FOUND_ROWS()");
            if(resultSet.next())
                this.noOfRecords = resultSet.getInt(1);
            
            resultSet.close();
      	  	con.close();
      	  	statement.close();

        } catch (SQLException e) {
        	throw new SQLException("Error in sortTechnicians while database operation");
        } 
        return list;
    }

	public int saveTechnician(Technician tech) throws NamingException, SQLException {
    	PreparedStatement stmt = null;
    	Connection con = null;
        try {
            con = getConnection();
 			con.setAutoCommit(false);
 			String insertQuery = "INSERT INTO service_locator_technician(user_id, first_name, last_name, "
 								+ "phone, email, last_modified, address1, address2, city, "
 								+ "region, postal, country, address_hash, location_type, "
 								+ "lat, lng, geocoded_on) "
 								+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
 			stmt = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
 			stmt.setString(1, tech.getUserId());
 			stmt.setString(2, tech.getFirstName());
 			stmt.setString(3, tech.getLastName());
 			stmt.setString(4, tech.getPhone());
 			stmt.setString(5, tech.getEmail());
 			java.sql.Timestamp now = new java.sql.Timestamp(new java.util.Date().getTime());
 			stmt.setTimestamp(6, now);
 			stmt.setString(7, tech.getTechAddress().getAddressLine1());
 			stmt.setString(8, tech.getTechAddress().getAddressLine2());
 			stmt.setString(9, tech.getTechAddress().getCity());
 			stmt.setString(10, tech.getTechAddress().getRegion());
 			stmt.setString(11, tech.getTechAddress().getPostalCode());
 			stmt.setString(12, tech.getTechAddress().getCountry());
 			if (tech.getTechAddress().getAddressHash() != null){
 				stmt.setString(13,  tech.getTechAddress().getAddressHash());
 			} else {
 				stmt.setString(13, "");
 			}
 			if ( tech.getTechAddress().getLocationType() != null ){
 				stmt.setString(14,  tech.getTechAddress().getLocationType());
 			} else {
 				stmt.setString(14, "");
 			}
 			stmt.setDouble(15, tech.getTechAddress().getLatitude());
 			stmt.setDouble(16, tech.getTechAddress().getLongitude());
        	stmt.setTimestamp(17, tech.getTechAddress().getGeocodedOn());

 			int affectedRows = stmt.executeUpdate();

 	        if (affectedRows == 0) {
 	            throw new SQLException("Creating technician failed, no rows affected.");
 	        }
 	        ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                tech.setId(generatedKeys.getInt(1));
            }
            else {
                throw new SQLException("Creating technician failed, no ID obtained.");
            }
            con.commit();
 			stmt.close();
 			con.close();
 			//stmt.execute();
		} catch (SQLException e) { 
			throw new SQLException("Error in saveTechnician while database operation");
		}
        return tech.getId();
	}

	public void updateTechnician(Technician tech) throws NamingException, SQLException {
    	PreparedStatement stmt = null;
    	Connection con = null;
        try {
            con = getConnection();
 			con.setAutoCommit(false);
 			String updateQuery = "UPDATE service_locator_technician SET user_id = ?, first_name = ?, last_name = ?, "
 								+ "phone = ?, email = ?, last_modified = ?, address1 = ?, address2 = ?, city = ?, "
 								+ "region = ?, postal = ?, country = ?, address_hash = ?, location_type = ?, "
 								+ "lat = ?, lng = ? "
 								+ "WHERE id = ?";
 			stmt = con.prepareStatement(updateQuery);
 			stmt.setString(1, tech.getUserId());
 			stmt.setString(2, tech.getFirstName());
 			stmt.setString(3, tech.getLastName());
 			stmt.setString(4, tech.getPhone());
 			stmt.setString(5, tech.getEmail());
 			java.sql.Timestamp now = new java.sql.Timestamp(new java.util.Date().getTime());
 			stmt.setTimestamp(6, now);
 			stmt.setString(7, tech.getTechAddress().getAddressLine1());
 			stmt.setString(8, tech.getTechAddress().getAddressLine2());
 			stmt.setString(9, tech.getTechAddress().getCity());
 			stmt.setString(10, tech.getTechAddress().getRegion());
 			stmt.setString(11, tech.getTechAddress().getPostalCode());
 			stmt.setString(12, tech.getTechAddress().getCountry());
 			if (tech.getTechAddress().getAddressHash() != null){
 				stmt.setString(13,  tech.getTechAddress().getAddressHash());
 			} else {
 				stmt.setString(13, "");
 			}
 			if ( tech.getTechAddress().getLocationType() != null ){
 				stmt.setString(14,  tech.getTechAddress().getLocationType());
 			} else {
 				stmt.setString(14, "");
 			}
 			stmt.setDouble(15, tech.getTechAddress().getLatitude());
 			stmt.setDouble(16, tech.getTechAddress().getLongitude());
 			//stmt.setTimestamp(17, tech.getTechAddress().getGeocodedOn());
 			stmt.setInt(17, tech.getId());
 			stmt.execute();
 			con.commit();
 			stmt.close();
 			con.close();
		} catch (SQLException e) { 
			throw new SQLException("Error in updateTechnician while database operation");
      }
		
	}

	public Technician editTechnician(long tech_id) throws NamingException, SQLException {
    	PreparedStatement stmt = null;
    	Connection con = null;
    	ResultSet resultSet = null;
    	Technician tech = null;
        try {
            con = getConnection();
 			con.setAutoCommit(false);
 	        String editQuery = "SELECT * FROM service_locator_technician where id = ?";
 			stmt = con.prepareStatement(editQuery);
 			stmt.setLong(1, tech_id);
 			
 			// execute the prepared statement
 			resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                tech = new Technician();
                Address techAddress = new Address();
                tech.setId(resultSet.getInt("id"));
                tech.setUserId(resultSet.getString("user_id"));
                tech.setFirstName(resultSet.getString("first_name"));
                tech.setLastName(resultSet.getString("last_name"));
                tech.setPhone(resultSet.getString("phone"));
                tech.setEmail(resultSet.getString("email"));
                techAddress.setAddressLine1(resultSet.getString("address1"));
                techAddress.setAddressLine2(resultSet.getString("address2"));
                techAddress.setCity(resultSet.getString("city"));
                techAddress.setRegion(resultSet.getString("region"));
                techAddress.setPostalCode(resultSet.getString("postal"));
                techAddress.setCountry(resultSet.getString("country"));
                techAddress.setAddressHash(resultSet.getString("address_hash"));
                techAddress.setLocationType(resultSet.getString("location_type"));
                techAddress.setLatitude(resultSet.getDouble("lat"));
                techAddress.setLongitude(resultSet.getDouble("lng"));
                if(resultSet.getString("geocoded_on") != null){
                	//System.out.println("Geocoded On:::::"+ resultSet.getString("geocoded_on"));
	        		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.ENGLISH);
	        		try {
	        			//String datestring = format.for
						Date date = format.parse(resultSet.getString("geocoded_on"));
						//techAddress.setGeocodedOn(new java.sql.Date(date.getTime()));
						//System.out.println("Date--------->"+date);
						
						techAddress.setGeocodedOn(new java.sql.Timestamp(date.getTime()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
            	}
            	tech.setTechAddress(techAddress); 
            }
            resultSet.close();
            stmt.close();
            con.close();
            
		} catch (SQLException e) { 
			throw new SQLException("Error in editTechnician while database operation");
        }
    	return tech;
    }

    public void deleteTechnician(long tech_id) throws NamingException, SQLException {
    	PreparedStatement stmt = null;
    	Connection con = null;
        try {
            con = getConnection();
 			con.setAutoCommit(false);

 			String delQueryEmployment = "DELETE FROM service_locator_employment where technician_id = ?";
 			stmt = con.prepareStatement(delQueryEmployment);
 			stmt.setLong(1, tech_id);
 			stmt.execute();

 			String delQueryTraining = "DELETE FROM service_locator_training where technician_id = ?";
 			stmt = con.prepareStatement(delQueryTraining);
 			stmt.setLong(1, tech_id);
 			stmt.execute();
 		    
 	        String delQuery = "DELETE FROM service_locator_technician where id = ?";
 			stmt = con.prepareStatement(delQuery);
 			stmt.setLong(1, tech_id);
 			// execute the prepared statement
 		    stmt.execute();

 		    con.commit();   
 		    con.close();
 			
		} catch (SQLException e) { 
			throw new SQLException("Error in deleteTechnician while database operation");
        }
    }
    
    public List<TrainingDummy> displayTrainings(int offset, int noOfRecords) throws NamingException, SQLException {
        Statement statement = null;
        List<TrainingDummy> list= new ArrayList<TrainingDummy>();
        ResultSet resultSet = null;
        Connection con = null;
        try {
        	con = getConnection();
            // Create the statement to be used to get the results.
            statement = con.createStatement();
            String query = "SELECT SQL_CALC_FOUND_ROWS * FROM service_locator_training order by id desc "
            		+ "limit " + offset + ", " + noOfRecords;

            // Execute the query and get the result set.
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                TrainingDummy trainingd = new TrainingDummy();
                trainingd.setId(resultSet.getInt("id"));
                trainingd.setTech(editTechnician(resultSet.getInt("technician_id")));
                trainingd.setProd(editProduct(resultSet.getInt("product_id")));
            	list.add(trainingd);
            }
            // Close the connection and release the resources used.
            resultSet.close();
            resultSet = statement.executeQuery("SELECT FOUND_ROWS()");
            if(resultSet.next())
                this.noOfRecords = resultSet.getInt(1);
            resultSet.close();
      	  	con.close();
      	  	statement.close();

        } catch (SQLException e) {
        	throw new SQLException("Error in displayTrainings while database operation");
        } 
        return list;
    }
    
	public List<TrainingDummy> searchTrainings(String q, int offset, int noOfRecords) throws NamingException, SQLException {
        Statement statement = null;
        List<TrainingDummy> list= new ArrayList<TrainingDummy>();
        ResultSet resultSet = null;
        Connection con = null;
        try {
        	con = getConnection();
            // Create the statement to be used to get the results.
            statement = con.createStatement();
            String query = "SELECT SQL_CALC_FOUND_ROWS * FROM service_locator_training trng, service_locator_technician tech "
            		+ "where trng.technician_id = tech.id "
            		+ "AND (tech.first_name like '%" + q + "%' OR tech.last_name like '%" + q + "%') "
            				+ "order by trng.id desc limit " + offset + ", " + noOfRecords;

            // Execute the query and get the result set.
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                TrainingDummy trainingd = new TrainingDummy();
                trainingd.setId(resultSet.getInt("id"));
                trainingd.setTech(editTechnician(resultSet.getInt("technician_id")));
                trainingd.setProd(editProduct(resultSet.getInt("product_id")));
            	list.add(trainingd);
            }
         // Close the connection and release the resources used.
            resultSet.close();
            resultSet = statement.executeQuery("SELECT FOUND_ROWS()");
            if(resultSet.next())
                this.noOfRecords = resultSet.getInt(1);
            resultSet.close();
      	  	con.close();
      	  	statement.close();

        } catch (SQLException e) {
        	throw new SQLException("Error in searchTrainings while database operation");
        } 
        return list;
    }

	public List<TrainingDummy> sortTrainings(String q, String o, int offset, int noOfRecords) throws NamingException, SQLException {
        Statement statement = null;
        List<TrainingDummy> list= new ArrayList<TrainingDummy>();
        ResultSet resultSet = null;
        Connection con = null;
        String query = "";
        try {
        	con = getConnection();
            // Create the statement to be used to get the results.
            statement = con.createStatement();
            if (o.equalsIgnoreCase(Constants.TECH_FULL_NAME))
            	query = "SELECT SQL_CALC_FOUND_ROWS * FROM service_locator_training trng, service_locator_technician tech "
            		+ "WHERE trng.technician_id = tech.id "
            		+ "AND (tech.first_name like '%" + q + "%' OR tech.last_name like '%" + q + "%') "
            				+ "order by tech.first_name, tech.last_name limit " + offset + ", " + noOfRecords;
            else if (o.equalsIgnoreCase(Constants.PRODUCT_NAME))
            	query = "SELECT SQL_CALC_FOUND_ROWS * FROM service_locator_training trng, service_locator_product prod "
            			+ "WHERE trng.product_id = prod.id order by " + o
                		+ " limit " + offset + ", " + noOfRecords;

            // Execute the query and get the result set.
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                TrainingDummy trainingd = new TrainingDummy();
                trainingd.setId(resultSet.getInt("id"));
                trainingd.setTech(editTechnician(resultSet.getInt("technician_id")));
                trainingd.setProd(editProduct(resultSet.getInt("product_id")));
            	list.add(trainingd);
            }
            // Close the connection and release the resources used.
            resultSet.close();
            resultSet = statement.executeQuery("SELECT FOUND_ROWS()");
            if(resultSet.next())
                this.noOfRecords = resultSet.getInt(1);
            
            resultSet.close();
     	  	con.close();
      	  	statement.close();

        } catch (SQLException e) {
        	throw new SQLException("Error in sortTrainings while database operation");
        } 
        return list;
    }

    public void saveTraining(Training training) throws NamingException, SQLException {
    	PreparedStatement stmt = null;
    	Connection con = null;
        try {
            con = getConnection();
 			con.setAutoCommit(false);
 			String insertQuery = "INSERT INTO service_locator_training(technician_id, product_id, "
 								+ "type, last_modified) "
 								+ "VALUES(?, ?, ?, ?)";
 			stmt = con.prepareStatement(insertQuery);
 			stmt.setInt(1, training.getTechnicianId());
 			stmt.setInt(2, training.getProductId());
 			stmt.setString(3, training.getType());
 			java.sql.Timestamp now = new java.sql.Timestamp(new java.util.Date().getTime());
 			stmt.setTimestamp(4, now);
 			stmt.execute();
 			con.commit();
 			stmt.close();
 			con.close();
        } catch (SQLException se){
        	throw new SQLException("Error in saveTraining while database operation");
        }
    }

    public Training editTraining(long training_id) throws NamingException, SQLException {
    	PreparedStatement stmt = null;
    	Connection con = null;
    	ResultSet resultSet = null;
    	Training training = null;
        try {
            con = getConnection();
 			con.setAutoCommit(false);
 	        String editQuery = "SELECT * FROM service_locator_training where id = ?";
 			stmt = con.prepareStatement(editQuery);
 			stmt.setLong(1, training_id);
 			
 			// execute the prepared statement
 			resultSet = stmt.executeQuery();
            while (resultSet.next()) {
            	training = new Training();
                training.setId(resultSet.getInt("id"));
                training.setTechnicianId(resultSet.getInt("technician_id"));
                training.setProductId(resultSet.getInt("product_id"));
                training.setType(resultSet.getString("type"));
            }
            resultSet.close();
            stmt.close();
            con.close();
            
		} catch (SQLException e) { 
			throw new SQLException("Error in editTraining while database operation");
        }
    	return training;
    }

    public List<Training> getTrainingByTechId(long tech_id) throws NamingException, SQLException {
    	PreparedStatement stmt = null;
    	Connection con = null;
    	ResultSet resultSet = null;
    	Training training = null;
    	List<Training> list = new ArrayList<Training>();
        try {
            con = getConnection();
 			con.setAutoCommit(false);
 	        String editQuery = "SELECT * FROM service_locator_training where technician_id = ?";
 			stmt = con.prepareStatement(editQuery);
 			stmt.setLong(1, tech_id);
 			
 			// execute the prepared statement
 			resultSet = stmt.executeQuery();
            while (resultSet.next()) {
            	training = new Training();
                training.setId(resultSet.getInt("id"));
                training.setTechnicianId(resultSet.getInt("technician_id"));
                training.setProductId(resultSet.getInt("product_id"));
                training.setType(resultSet.getString("type"));
                list.add(training);
            }
            resultSet.close();
            stmt.close();
            con.close();
            
		} catch (SQLException e) { 
			throw new SQLException("Error in getTrainingByTechId while database operation");
        }
    	return list;
    }

    public void updateTraining(Training training) throws NamingException, SQLException {
    	PreparedStatement stmt = null;
    	Connection con = null;
        try {
            con = getConnection();
 			con.setAutoCommit(false);
 			String updateQuery = "UPDATE service_locator_training SET technician_id = ?, product_id = ?, "
 								+ "type = ?, last_modified = ? "
 								+ "WHERE id = ?";
 			stmt = con.prepareStatement(updateQuery);
 			stmt.setInt(1, training.getTechnicianId());
 			stmt.setInt(2, training.getProductId());
 			stmt.setString(3, training.getType());
 			java.sql.Timestamp now = new java.sql.Timestamp(new java.util.Date().getTime());
 			stmt.setTimestamp(4, now);
 			stmt.setInt(5, training.getId());
 			stmt.execute();
 			con.commit();
 			stmt.close();
 			con.close();
		} catch (SQLException e) { 
			throw new SQLException("Error in updateTraining while database operation");
       }
	}

    public void deleteTraining(long training_id) throws NamingException, SQLException {
    	PreparedStatement stmt = null;
    	Connection con = null;
        try {
            con = getConnection();
 			con.setAutoCommit(false);

 	        String delQuery = "DELETE FROM service_locator_training where id = ?";
 			stmt = con.prepareStatement(delQuery);
 			stmt.setLong(1, training_id);
 			
 			// execute the prepared statement
 		    stmt.execute();
 		    con.commit();   
 		    con.close();
 			
		} catch (SQLException e) { 
			throw new SQLException("Error in deleteTraining while database operation");
        }
    }

    public void saveEmployment(long tech_id, long comp_id) throws NamingException, SQLException {
    	PreparedStatement stmt = null;
    	Connection con = null;
        try {
            con = getConnection();
 			con.setAutoCommit(false);

 	        String insertQuery = "INSERT INTO service_locator_employment (technician_id, "
 	        		+ "company_id, last_modified) VALUES(?, ?, ?)";
 			stmt = con.prepareStatement(insertQuery);
 			stmt.setLong(1, tech_id);
 			stmt.setLong(2, comp_id);
 			java.sql.Timestamp now = new java.sql.Timestamp(new java.util.Date().getTime());
 			stmt.setTimestamp(3, now);
 			// execute the prepared statement
 		    stmt.execute();
 		    con.commit();   
 		    con.close();
 			
		} catch (SQLException e) { 
			throw new SQLException("Error in saveEmployment while database operation");
        }
    }

    public List<Long> editEmploymentByComp(long comp_id) throws NamingException, SQLException {
    	PreparedStatement stmt = null;
    	ResultSet resultSet = null;
    	List<Long> tech_ids = new ArrayList<Long>();
    	Connection con = null;
        try {
            con = getConnection();
 			con.setAutoCommit(false);

 	        String selectQuery = "SELECT technician_id FROM service_locator_employment WHERE company_id = ?";
 			stmt = con.prepareStatement(selectQuery);
 			stmt.setLong(1, comp_id);
 			resultSet = stmt.executeQuery();
 			while (resultSet.next()) {
 				tech_ids.add(resultSet.getLong("technician_id"));
 			}
 			resultSet.close();
 			stmt.close();
 			con.close();
		} catch (SQLException e) { 
			throw new SQLException("Error in editEmploymentByComp while database operation");
        }
        return tech_ids;
    }
   
    public List<Long> editEmploymentByTech(long tech_id) throws NamingException, SQLException {
    	PreparedStatement stmt = null;
    	ResultSet resultSet = null;
    	List<Long> comp_ids = new ArrayList<Long>();
    	Connection con = null;
        try {
            con = getConnection();
 			con.setAutoCommit(false);

 	        String selectQuery = "SELECT company_id FROM service_locator_employment WHERE technician_id = ?";
 			stmt = con.prepareStatement(selectQuery);
 			stmt.setLong(1, tech_id);
 			resultSet = stmt.executeQuery();
 			while (resultSet.next()) {
 				comp_ids.add(resultSet.getLong("company_id"));
 			}
 			resultSet.close();
 			stmt.close();
 			con.close();
		} catch (SQLException e) { 
			throw new SQLException("Error in editEmploymentByTech while database operation");
        }
        return comp_ids;
    }

    public void updateEmploymentByComp(List<Long> techArray, long comp_id) throws NamingException, SQLException {
    	PreparedStatement stmt = null;
    	Connection con = null;
    	ResultSet resultSet = null;
    	List<Long> currTechs = new ArrayList<Long>();
        try {
            con = getConnection();
 			con.setAutoCommit(false);
 			String selectQuery = "SELECT technician_id FROM service_locator_employment WHERE company_id = ?";
 			stmt = con.prepareStatement(selectQuery); 
 			stmt.setLong(1, comp_id);
 			resultSet = stmt.executeQuery();
            while (resultSet.next()) {
            	currTechs.add(resultSet.getLong("technician_id"));
            }
 			for (long tech_id : techArray){
 				if(!currTechs.contains(tech_id)){
 					//insert the record
 					saveEmployment(tech_id, comp_id);
 				}
 			}
 			
 			for (long tech_id : currTechs){
 				if(!techArray.contains(tech_id)){
 					//Delete the record
 					deleteEmployment(tech_id, comp_id);
 				}
 			}
		} catch (SQLException e) { 
			throw new SQLException("Error in updateEmploymentByComp while database operation");
        }
    }

    public void updateTrainingByTech(List<Training> trainingArray, int tech_id) throws NamingException, SQLException {
    	PreparedStatement stmt = null;
    	Connection con = null;
    	ResultSet resultSet = null;
    	List<Training> currTrainings = new ArrayList<Training>();
    	List<Integer> currProdIds = new ArrayList<Integer>();
    	List<Integer> trngProdIds = new ArrayList<Integer>();
        try {
            con = getConnection();
 			con.setAutoCommit(false);
 			String selectQuery = "SELECT * FROM service_locator_training WHERE technician_id = ?";
 			stmt = con.prepareStatement(selectQuery); 
 			stmt.setLong(1, tech_id);
 			resultSet = stmt.executeQuery();
 			Training trng = new Training();
 			String cert_type = "";
            while (resultSet.next()) {
            	trng.setId(resultSet.getInt("id"));
            	trng.setProductId(resultSet.getInt("product_id"));
            	trng.setTechnicianId(tech_id);
            	trng.setType(resultSet.getString("type"));
            	currTrainings.add(trng);
            	currProdIds.add(resultSet.getInt("product_id"));
            }
            for (Training submittedTrng : trainingArray){
            	trngProdIds.add(submittedTrng.getProductId());
            	//trngProdIds.add(
            }
            //List<Long> prodIds = trainingArray.
 			for (Integer prod_id : trngProdIds){
 				if(!currProdIds.contains(prod_id)){
 					//insert the record
 					Training training = new Training();
 					for (Training anothertrng : trainingArray){
 						if(anothertrng.getProductId() == prod_id)
 							cert_type = anothertrng.getType();
 					}
 					training.setProductId(prod_id);
 					training.setTechnicianId(tech_id);
 					training.setType(cert_type);
 					saveTraining(training);
 				}
 			}
 			
 			for (Integer prodId : currProdIds){
 				if(!trngProdIds.contains(prodId)){
 					//Delete the record
 					int trng_id = getTrainingIdByProdId(prodId);
 					deleteTraining(trng_id);
 				}
 			}
		} catch (SQLException e) { 
			throw new SQLException("Error in updateTrainingByTech while database operation");
        }
    }
    
    public int getTrainingIdByProdId(int prod_id) throws NamingException, SQLException {
    	PreparedStatement stmt = null;
    	Connection con = null;
    	ResultSet resultSet = null;
    	int training_id = 0;
        try {
            con = getConnection();
 			con.setAutoCommit(false);
 	        String editQuery = "SELECT id FROM service_locator_training where product_id = ?";
 			stmt = con.prepareStatement(editQuery);
 			stmt.setLong(1, prod_id);
 			
 			// execute the prepared statement
 			resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                training_id = resultSet.getInt("id");
            }
            resultSet.close();
            stmt.close();
            con.close();
            
		} catch (SQLException e) { 
			throw new SQLException("Error in getTrainingIdByProdId while database operation");
        }
    	return training_id;
    }

    public void updateEmploymentByTech(List<Long> compArray, long tech_id) throws NamingException, SQLException {
    	PreparedStatement stmt = null;
    	Connection con = null;
    	ResultSet resultSet = null;
    	List<Long> currComps = new ArrayList<Long>();
        try {
            con = getConnection();
 			con.setAutoCommit(false);
 			String selectQuery = "SELECT company_id FROM service_locator_employment WHERE technician_id = ?";
 			stmt = con.prepareStatement(selectQuery); 
 			stmt.setLong(1, tech_id);
 			resultSet = stmt.executeQuery();
            while (resultSet.next()) {
            	currComps.add(resultSet.getLong("company_id"));
            }
 			for (long comp_id : compArray){
 				if(!currComps.contains(comp_id)){
 					//insert the record
 					saveEmployment(tech_id, comp_id);
 				}
 			}
 			
 			for (long comp_id : currComps){
 				if(!compArray.contains(comp_id)){
 					//Delete the record
 					deleteEmployment(tech_id, comp_id);
 				}
 			}
		} catch (SQLException e) { 
			throw new SQLException("Error in updateEmploymentByTech while database operation");
        }
    }

    public void deleteEmployment(long tech_id, long comp_id) throws NamingException, SQLException {
    	PreparedStatement stmt = null;
    	Connection con = null;
        try {
            con = getConnection();
 			con.setAutoCommit(false);

 	        String delQuery = "DELETE FROM service_locator_employment WHERE technician_id = ? AND company_id = ?";
 			stmt = con.prepareStatement(delQuery);
 			stmt.setLong(1, tech_id);
 			stmt.setLong(2, comp_id);
 			// execute the prepared statement
 		    stmt.execute();
 		    con.commit();   
 		    con.close();
 			
		} catch (SQLException e) { 
			throw new SQLException("Error in deleteEmployment while database operation");
        }
    }

	public List<User> displayUsers(int offset, int noOfRecords) throws NamingException, SQLException {
        Statement statement = null;
        List<User> list= new ArrayList<User>();
        ResultSet resultSet = null;
        Connection con = null;
        try {
        	con = getConnection();
            // Create the statement to be used to get the results.
            statement = con.createStatement();
            String query = "SELECT SQL_CALC_FOUND_ROWS * FROM auth_user order by id desc "
            		+ "limit " + offset + ", " + noOfRecords;

            // Execute the query and get the result set.
            resultSet = statement.executeQuery(query);
            //out.println("<strong>Printing result using context file...</strong><br>");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
            	user.setUsername(resultSet.getString("username"));
            	user.setEmail(resultSet.getString("email"));
            	user.setFirstName(resultSet.getString("first_name"));
            	user.setLastName(resultSet.getString("last_name"));
            	user.setIsStaff(resultSet.getShort("is_staff"));
            	list.add(user);
            }
            // Close the connection and release the resources used.
            resultSet.close();
            resultSet = statement.executeQuery("SELECT FOUND_ROWS()");
            if(resultSet.next())
                this.noOfRecords = resultSet.getInt(1);
            resultSet.close();
      	  	con.close();
      	  	statement.close();

        } catch (SQLException e) {
        	throw new SQLException("Error in displayUsers while database operation");
        } 
        return list;
    }
	
	public List<User> searchUsers(String q, int offset, int noOfRecords) throws NamingException, SQLException {
        Statement statement = null;
        List<User> list= new ArrayList<User>();
        ResultSet resultSet = null;
        Connection con = null;
        try {
        	con = getConnection();
            // Create the statement to be used to get the results.
            statement = con.createStatement();
            String query = "(SELECT SQL_CALC_FOUND_ROWS * FROM auth_user where username like '%" + q + "%' "
            		+ "OR first_name like '%" + q + "%' OR last_name like '%" + q + "%' "
            		+ "limit " + offset + ", " + noOfRecords +  ") order by id desc";

            // Execute the query and get the result set.
            resultSet = statement.executeQuery(query);
            //out.println("<strong>Printing result using context file...</strong><br>");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
            	user.setUsername(resultSet.getString("username"));
            	user.setEmail(resultSet.getString("email"));
            	user.setFirstName(resultSet.getString("first_name"));
            	user.setLastName(resultSet.getString("last_name"));
            	user.setIsStaff(resultSet.getShort("is_staff"));
            	list.add(user);
            }
         // Close the connection and release the resources used.
            resultSet.close();
            
            query = "SELECT SQL_CALC_FOUND_ROWS * FROM auth_user where username like '%" + q + "%' "
            		+ "OR first_name like '%" + q + "%' OR last_name like '%" + q + "%' "
            		+ "order by id desc limit " + offset + ", " + noOfRecords;
            resultSet = statement.executeQuery(query);
            resultSet.close();
            
            resultSet = statement.executeQuery("SELECT FOUND_ROWS()");
            if(resultSet.next())
                this.noOfRecords = resultSet.getInt(1);
            resultSet.close();
      	  	con.close();
      	  	statement.close();

        } catch (SQLException e) {
        	throw new SQLException("Error in searchUsers while database operation");
        } 
        return list;
    }
	
	public List<User> sortUsers(String q, String o, int offset, int noOfRecords) throws NamingException, SQLException {
        Statement statement = null;
        List<User> list= new ArrayList<User>();
        ResultSet resultSet = null;
        Connection con = null;
        try {
        	con = getConnection();
            // Create the statement to be used to get the results.
            statement = con.createStatement();
            String query = "(SELECT SQL_CALC_FOUND_ROWS * FROM auth_user where "
            		+ "username like '%" + q + "%' OR first_name like '%" + q + "%' OR last_name like '%" + q + "%'"
            		 + " limit " + offset + ", " + noOfRecords + ") order by " + o;

            // Execute the query and get the result set.
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
            	user.setUsername(resultSet.getString("username"));
            	user.setEmail(resultSet.getString("email"));
            	user.setFirstName(resultSet.getString("first_name"));
            	user.setLastName(resultSet.getString("last_name"));
            	user.setIsStaff(resultSet.getShort("is_staff"));
            	list.add(user);
            }
         // Close the connection and release the resources used.
            resultSet.close();
            query = "SELECT SQL_CALC_FOUND_ROWS * FROM auth_user where "
            		+ "username like '%" + q + "%' OR first_name like '%" + q + "%' OR last_name like '%" + q + "%'"
            		 + " order by " + o + " limit " + offset + ", " + noOfRecords;
            //System.out.println(query);
            resultSet = statement.executeQuery(query);
            resultSet.close();
            
            resultSet = statement.executeQuery("SELECT FOUND_ROWS()");
            if(resultSet.next())
                this.noOfRecords = resultSet.getInt(1);
            resultSet.close();
      	  	con.close();
      	  	statement.close();

        } catch (SQLException e) {
        	throw new SQLException("Error in sortUsers while database operation");
        } 
        return list;
    }

	public int saveUser(User user) throws NamingException, SQLException {
    	PreparedStatement stmt = null;
    	Connection con = null;
        try {
            con = getConnection();
 			con.setAutoCommit(false);
 			String insertQuery = "INSERT INTO auth_user(username, first_name, last_name, email, "
 								+ "password, is_staff, is_active, is_superuser, last_login, "
 								+ "date_joined) "
 								+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
 			stmt = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
 			stmt.setString(1, user.getUsername());
 			stmt.setString(2, ""); // initially First name is empty string 
 			stmt.setString(3, ""); // initially Last name is empty string
 			stmt.setString(4, ""); // initially email is empty string 
 			PasswordHasher ph = new PasswordHasher();
 			String hashedPassword = ph.encode(user.getPassword(), PasswordHasher.getNextSalt().toString());
 			stmt.setString(5, hashedPassword);
 			stmt.setShort(6, (short)0); //Initially isStaff is false
 			stmt.setShort(7, (short)1); //Initially isActive is true
 			stmt.setShort(8, (short)0); //Initially isSuperuser is false
 			java.sql.Timestamp now = new java.sql.Timestamp(new java.util.Date().getTime());
 			stmt.setTimestamp(9, now); //Initially Last login is now
 			stmt.setTimestamp(10, now); //Initially Date Joined is now
 			
 			int affectedRows = stmt.executeUpdate();

 	        if (affectedRows == 0) {
 	            throw new SQLException("Creating user failed, no rows affected.");
 	        }
 	        ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            }
            else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
            con.commit();
 			stmt.close();
 			con.close();
 			//stmt.execute();
		} catch (SQLException e) { 
			throw new SQLException("Error in saveUser while database operation");
		}
        return user.getId();
	}

	public void updateUser(User user) throws NamingException, SQLException {
    	PreparedStatement stmt = null;
    	Connection con = null;
        try {
            con = getConnection();
 			con.setAutoCommit(false);
 			String updateQuery = "UPDATE auth_user SET username = ?, first_name = ?, "
 								+ "last_name = ?, email = ?, is_active = ?, is_staff = ?, "
 								+ "is_superuser = ?, date_joined = ?, last_login = ? "
 								+ "WHERE id = ?";
 			stmt = con.prepareStatement(updateQuery);
 			stmt.setString(1, user.getUsername());
 			stmt.setString(2, user.getFirstName());
 			stmt.setString(3, user.getLastName());
 			stmt.setString(4, user.getEmail());
 			stmt.setShort(5, user.getIsActive());
 			stmt.setShort(6, user.getIsStaff());
 			stmt.setShort(7, user.getIsSuperuser());
 			stmt.setTimestamp(8, user.getDateJoined());
 			stmt.setTimestamp(9, user.getLastLogin());
 			stmt.setInt(10, user.getId());
 			stmt.execute();
 			con.commit();
 			stmt.close();
 			con.close();
        } catch (SQLException e) { 
        	throw new SQLException("Error in updateUser while database operation");
        }
	}

	public void updatePassword(User user) throws NamingException, SQLException {
    	PreparedStatement stmt = null;
    	Connection con = null;
        try {
            con = getConnection();
 			con.setAutoCommit(false);
 			String updateQuery = "UPDATE auth_user SET password = ?"
 								+ "WHERE id = ?";
 			stmt = con.prepareStatement(updateQuery);
 			PasswordHasher ph = new PasswordHasher();
 			String hashedPassword = ph.encode(user.getPassword(), PasswordHasher.getNextSalt().toString());
 			stmt.setString(1, hashedPassword);
 			stmt.setInt(2, user.getId());
 			stmt.execute();
 			con.commit();
 			stmt.close();
 			con.close();
        } catch (SQLException e) { 
        	throw new SQLException("Error in updatePassword while database operation");
       }
	}

	public User editUser(long user_id) throws NamingException, SQLException {
    	PreparedStatement stmt = null;
    	Connection con = null;
    	ResultSet resultSet = null;
    	User user = null;
        try {
            con = getConnection();
 			con.setAutoCommit(false);
 	        String editQuery = "SELECT * FROM auth_user where id = ?";
 			stmt = con.prepareStatement(editQuery);
 			stmt.setLong(1, user_id);
 			
 			// execute the prepared statement
 			resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
            	user.setUsername(resultSet.getString("username"));
            	//user.setPassword(resultSet.getString("password"));
            	user.setFirstName(resultSet.getString("first_name"));
            	user.setLastName(resultSet.getString("last_name"));
            	user.setEmail(resultSet.getString("email"));
            	user.setIsActive(resultSet.getShort("is_active"));
            	user.setIsStaff(resultSet.getShort("is_staff"));
            	user.setIsSuperuser(resultSet.getShort("is_superuser"));
            	user.setDateJoined(resultSet.getTimestamp("date_joined"));
            	user.setLastLogin(resultSet.getTimestamp("last_login"));
            }
            resultSet.close();
            stmt.close();
            con.close();
            
		} catch (SQLException e) { 
			throw new SQLException("Error in editUser while database operation");
        }
    	return user;
    }

    public void deleteUser(long user_id) throws NamingException, SQLException {
    	PreparedStatement stmt = null;
    	Connection con = null;
        try {
            con = getConnection();
 			con.setAutoCommit(false);

 	        String delQuery = "DELETE FROM auth_user where id = ?";
 			stmt = con.prepareStatement(delQuery);
 			stmt.setLong(1, user_id);
 			// execute the prepared statement
 		    stmt.execute();
 		    con.commit();   
 		    con.close();
 			
		} catch (SQLException e) { 
			throw new SQLException("Error in deleteUser while database operation");
        }
    }

	public User getUserFromUsername(String username) throws NamingException, SQLException {
    	PreparedStatement stmt = null;
    	Connection con = null;
    	ResultSet resultSet = null;
    	User user = null;
        try {
            con = getConnection();
 			con.setAutoCommit(false);
 	        String editQuery = "SELECT * FROM auth_user where username = ?";
 			stmt = con.prepareStatement(editQuery);
 			stmt.setString(1, username);
 			
 			// execute the prepared statement
 			resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
            	user.setUsername(resultSet.getString("username"));
            	user.setPassword(resultSet.getString("password"));
            	user.setFirstName(resultSet.getString("first_name"));
            	user.setLastName(resultSet.getString("last_name"));
            	user.setEmail(resultSet.getString("email"));
            	user.setIsActive(resultSet.getShort("is_active"));
            	user.setIsActive(resultSet.getShort("is_staff"));
            	user.setIsSuperuser(resultSet.getShort("is_superuser"));
            	user.setDateJoined(resultSet.getTimestamp("date_joined"));
            	user.setLastLogin(resultSet.getTimestamp("last_login"));
            }
            resultSet.close();
            stmt.close();
            con.close();
            
		} catch (SQLException e) { 
			throw new SQLException("Error in getUserFromUsername while database operation");
        }
    	return user;
    }

	public User getUserFromEmail(String email) throws NamingException, SQLException {
    	PreparedStatement stmt = null;
    	Connection con = null;
    	ResultSet resultSet = null;
    	User user = null;
        try {
            con = getConnection();
 			con.setAutoCommit(false);
 	        String editQuery = "SELECT * FROM auth_user where email = ?";
 			stmt = con.prepareStatement(editQuery);
 			stmt.setString(1, email);
 			
 			// execute the prepared statement
 			resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
            	user.setUsername(resultSet.getString("username"));
            	user.setPassword(resultSet.getString("password"));
            	user.setFirstName(resultSet.getString("first_name"));
            	user.setLastName(resultSet.getString("last_name"));
            	user.setEmail(resultSet.getString("email"));
            	user.setIsActive(resultSet.getShort("is_active"));
            	user.setIsActive(resultSet.getShort("is_staff"));
            	user.setIsSuperuser(resultSet.getShort("is_superuser"));
            	user.setDateJoined(resultSet.getTimestamp("date_joined"));
            	user.setLastLogin(resultSet.getTimestamp("last_login"));
            }
            resultSet.close();
            stmt.close();
            con.close();
            
		} catch (SQLException e) { 
			throw new SQLException("Error in getUserFromEmail while database operation");
        }
    	return user;
    }
	
	public void updateUsersLastLogin(int user_id) throws NamingException, SQLException {
    	PreparedStatement stmt = null;
    	Connection con = null;
        try {
            con = getConnection();
 			con.setAutoCommit(false);
 			String updateQuery = "UPDATE auth_user SET last_login = ? "
 								+ "WHERE id = ?";
 			stmt = con.prepareStatement(updateQuery);
			java.sql.Timestamp now = new java.sql.Timestamp(new java.util.Date().getTime());
 			stmt.setTimestamp(1, now);
 			stmt.setInt(2, user_id);
 			stmt.execute();
 			con.commit();
 			stmt.close();
 			con.close();
		} catch (SQLException e) { 
			throw new SQLException("Error in updateUsersLastLogin while database operation");
       }
		
	}
	
	public List<Group> displayGroups(int offset, int noOfRecords) throws NamingException {
        Statement statement = null;
        List<Group> list= new ArrayList<Group>();
        ResultSet resultSet = null;
        Connection con = null;
        try {
        	con = getConnection();
            // Create the statement to be used to get the results.
            statement = con.createStatement();
            String query = "SELECT SQL_CALC_FOUND_ROWS * FROM auth_group order by id desc "
            		+ "limit " + offset + ", " + noOfRecords;

            // Execute the query and get the result set.
            resultSet = statement.executeQuery(query);
            //out.println("<strong>Printing result using context file...</strong><br>");
            while (resultSet.next()) {
                Group group = new Group();
                group.setId(resultSet.getInt("id"));
                group.setName(resultSet.getString("name"));
            	list.add(group);
            }
            // Close the connection and release the resources used.
            resultSet.close();
            resultSet = statement.executeQuery("SELECT FOUND_ROWS()");
            if(resultSet.next())
                this.noOfRecords = resultSet.getInt(1);
            resultSet.close();
      	  	con.close();
      	  	statement.close();

        } catch (SQLException e) {
        	e.printStackTrace();
        } 
        return list;
    }

	public List<Group> getAllGroups() throws NamingException {
        Statement statement = null;
        List<Group> list= new ArrayList<Group>();
        ResultSet resultSet = null;
        Connection con = null;
        try {
        	con = getConnection();
            // Create the statement to be used to get the results.
            statement = con.createStatement();
            String query = "SELECT * FROM auth_group order by id desc";

            // Execute the query and get the result set.
            resultSet = statement.executeQuery(query);
            //out.println("<strong>Printing result using context file...</strong><br>");
            while (resultSet.next()) {
                Group group = new Group();
                group.setId(resultSet.getInt("id"));
                group.setName(resultSet.getString("name"));
            	list.add(group);
            }
            // Close the connection and release the resources used.
            resultSet.close();
      	  	con.close();
      	  	statement.close();

        } catch (SQLException e) {
        	e.printStackTrace();
        } 
        return list;
    }

	public List<Group> getGroupsByUser(int user_id) throws NamingException {
        Statement statement = null;
        List<Group> list= new ArrayList<Group>();
        ResultSet resultSet = null;
        Connection con = null;
        try {
        	con = getConnection();
            // Create the statement to be used to get the results.
            statement = con.createStatement();
            String query = "SELECT ag.id, ag.name FROM auth_user_groups aug, auth_group ag "
            		+ "where aug.group_id = ag.id AND aug.user_id = " + user_id;

            // Execute the query and get the result set.
            resultSet = statement.executeQuery(query);
            //out.println("<strong>Printing result using context file...</strong><br>");
            while (resultSet.next()) {
                Group group = new Group();
                group.setId(resultSet.getInt("id"));
                group.setName(resultSet.getString("name"));
            	list.add(group);
            }
            // Close the connection and release the resources used.
            resultSet.close();
      	  	con.close();
      	  	statement.close();

        } catch (SQLException e) {
        	e.printStackTrace();
        } 
        return list;
    }

	public List<Group> searchGroups(String q, int offset, int noOfRecords) throws NamingException {
        Statement statement = null;
        List<Group> list= new ArrayList<Group>();
        ResultSet resultSet = null;
        Connection con = null;
        try {
        	con = getConnection();
            // Create the statement to be used to get the results.
            statement = con.createStatement();
            String query = "(SELECT SQL_CALC_FOUND_ROWS * FROM auth_group where name like '%" + q + "%' "
            		+ "limit " + offset + ", " + noOfRecords +  ") order by id desc";

            // Execute the query and get the result set.
            resultSet = statement.executeQuery(query);
            //out.println("<strong>Printing result using context file...</strong><br>");
            while (resultSet.next()) {
                Group group = new Group();
                group.setId(resultSet.getInt("id"));
                group.setName(resultSet.getString("name"));
            	list.add(group);
            }
         // Close the connection and release the resources used.
            resultSet.close();
            
            query = "SELECT SQL_CALC_FOUND_ROWS * FROM auth_group where name like '%" + q + "%' "
            		+ "order by id desc limit " + offset + ", " + noOfRecords;
            resultSet = statement.executeQuery(query);
            resultSet.close();
            
            resultSet = statement.executeQuery("SELECT FOUND_ROWS()");
            if(resultSet.next())
                this.noOfRecords = resultSet.getInt(1);
            resultSet.close();
      	  	con.close();
      	  	statement.close();

        } catch (SQLException e) {
        	e.printStackTrace();
        } 
        return list;
    }
	

	public int saveGroup(Group group, List<Permission> permList){
    	PreparedStatement stmt = null;
    	Connection con = null;
        try {
            con = getConnection();
 			con.setAutoCommit(false);
 			String insertQuery = "INSERT INTO auth_group(name) "
 								+ "VALUES(?)";
 			stmt = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
 			stmt.setString(1, group.getName());
 			
 			int affectedRows = stmt.executeUpdate();

 	        if (affectedRows == 0) {
 	            throw new SQLException("Creating group failed, no rows affected.");
 	        }
 	        ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                group.setId(generatedKeys.getInt(1));
            }
            else {
                throw new SQLException("Creating group failed, no ID obtained.");
            }
            // Add group permissions
            for (Permission perm: permList){
	            insertQuery = "INSERT INTO auth_group_permission(group_id, permission_id) "
							+ "VALUES(?, ?)";
	 			stmt = con.prepareStatement(insertQuery);
	 			stmt.setInt(1, group.getId());
	 			stmt.setInt(2, perm.getId());
	 			stmt.execute();
            }
            con.commit();
 			stmt.close();
 			con.close();
 			//stmt.execute();
        } catch (NamingException e) {
			 e.printStackTrace();
		} catch (SQLException e) { 
   		 	e.printStackTrace();
		}
        return group.getId();
	}

	public Group editGroup(long group_id){
    	PreparedStatement stmt = null;
    	Connection con = null;
    	ResultSet resultSet = null;
    	Group group = null;
        try {
            con = getConnection();
 			con.setAutoCommit(false);
 	        String editQuery = "SELECT * FROM auth_group where id = ?";
 			stmt = con.prepareStatement(editQuery);
 			stmt.setLong(1, group_id);
 			
 			// execute the prepared statement
 			resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                group = new Group();
                group.setId(resultSet.getInt("id"));
            	group.setName(resultSet.getString("name"));
            }
            resultSet.close();
            stmt.close();
            con.close();
            
    	} catch (NamingException e) {
			 e.printStackTrace();
		} catch (SQLException e) { 
    		 e.printStackTrace();
        }
    	return group;
    }

	public List<GroupPermission> editGroupPermission(long group_id){
    	PreparedStatement stmt = null;
    	Connection con = null;
    	ResultSet resultSet = null;
    	GroupPermission groupPerm = null;
    	List<GroupPermission> groupPermList = new ArrayList<GroupPermission>();
        try {
            con = getConnection();
 			con.setAutoCommit(false);
 	        String editQuery = "SELECT * FROM auth_group_permissions where group_id = ?";
 			stmt = con.prepareStatement(editQuery);
 			stmt.setLong(1, group_id);
 			
 			// execute the prepared statement
 			resultSet = stmt.executeQuery();
            while (resultSet.next()) {
            	groupPerm = new GroupPermission();
            	groupPerm.setId(resultSet.getInt("id"));
            	groupPerm.setGroupId(resultSet.getInt("group_id"));
            	groupPerm.setPermissionId(resultSet.getInt("permission_id"));
            	groupPermList.add(groupPerm);
            }
            resultSet.close();
            stmt.close();
            con.close();
            
    	} catch (NamingException e) {
			 e.printStackTrace();
		} catch (SQLException e) { 
    		 e.printStackTrace();
        }
    	return groupPermList;
    }

	public void updateGroup(Group group){
    	PreparedStatement stmt = null;
    	Connection con = null;
        try {
            con = getConnection();
 			con.setAutoCommit(false);
 			String updateQuery = "UPDATE auth_group SET name = ? "
 								+ "WHERE id = ?";
 			stmt = con.prepareStatement(updateQuery);
 			stmt.setString(1, group.getName());
 			stmt.setInt(2, group.getId());
 			stmt.execute();
 			con.commit();
 			stmt.close();
 			con.close();
        } catch (NamingException e) {
			 e.printStackTrace();
		} catch (SQLException e) { 
   		 	e.printStackTrace();
       }
	}

	public void deleteGroup(long group_id){
    	PreparedStatement stmt = null;
    	Connection con = null;
        try {
            con = getConnection();
 			con.setAutoCommit(false);

 	        String delQuery = "DELETE FROM auth_group_permissions where group_id = ?";
 			stmt = con.prepareStatement(delQuery);
 			stmt.setLong(1, group_id);
 			// execute the prepared statement
 		    stmt.execute();

 	        delQuery = "DELETE FROM auth_group where id = ?";
 			stmt = con.prepareStatement(delQuery);
 			stmt.setLong(1, group_id);
 			// execute the prepared statement
 		    stmt.execute();
 		    con.commit();   
 		    con.close();
 			
    	} catch (NamingException e) {
			 e.printStackTrace();
		} catch (SQLException e) { 
    		 e.printStackTrace();
        }
    }

	public List<UserPermissionDummy> getAllUserPermission() throws NamingException {
        Statement statement = null;
        List<UserPermissionDummy> list= new ArrayList<UserPermissionDummy>();
        ResultSet resultSet = null;
        Connection con = null;
        try {
        	con = getConnection();
            // Create the statement to be used to get the results.
            statement = con.createStatement();
            String query = "SELECT a.app_label app_label, a.name content_name, b.name perm_name FROM django_content_type a, auth_permission b "
            		+ "WHERE a.id = b.content_type_id order by a.app_label, a.name, b.name";

            // Execute the query and get the result set.
            resultSet = statement.executeQuery(query);
            //out.println("<strong>Printing result using context file...</strong><br>");
            while (resultSet.next()) {
            	UserPermissionDummy userPermDummy = new UserPermissionDummy();
            	userPermDummy.setAppLabel(resultSet.getString("app_label"));
            	userPermDummy.setContentName(resultSet.getString("content_name"));
            	userPermDummy.setPermissionName(resultSet.getString("perm_name"));
            	list.add(userPermDummy);
            }
            // Close the connection and release the resources used.
            resultSet.close();
      	  	con.close();
      	  	statement.close();

        } catch (SQLException e) {
        	e.printStackTrace();
        } 
        return list;
    }

	public List<UserPermissionDummy> getUserPermissionByUser(int user_id) throws NamingException {
        Statement statement = null;
        List<UserPermissionDummy> list= new ArrayList<UserPermissionDummy>();
        ResultSet resultSet = null;
        Connection con = null;
        try {
        	con = getConnection();
            // Create the statement to be used to get the results.
            statement = con.createStatement();
            String query = "SELECT a.app_label app_label, a.name content_name, b.name perm_name FROM django_content_type a, auth_permission b, auth_user_user_permissions c "
            		+ "WHERE a.id = b.content_type_id AND b.id = c.permission_id AND c.user_id = " + user_id + " order by a.app_label, a.name, b.name";
            //System.out.println(query);
            // Execute the query and get the result set.
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
            	UserPermissionDummy userPermDummy = new UserPermissionDummy();
            	userPermDummy.setAppLabel(resultSet.getString("app_label"));
            	userPermDummy.setContentName(resultSet.getString("content_name"));
            	userPermDummy.setPermissionName(resultSet.getString("perm_name"));
            	list.add(userPermDummy);
            }
            // Close the connection and release the resources used.
            resultSet.close();
      	  	con.close();
      	  	statement.close();

        } catch (SQLException e) {
        	e.printStackTrace();
        }
        return list;
    }

	public List<XMLFeed> displayXMLFeeds(int offset, int noOfRecords) throws NamingException {
        Statement statement = null;
        List<XMLFeed> list= new ArrayList<XMLFeed>();
        ResultSet resultSet = null;
        Connection con = null;
        try {
        	con = getConnection();
            // Create the statement to be used to get the results.
            statement = con.createStatement();
            String query = "SELECT SQL_CALC_FOUND_ROWS * FROM service_locator_xmlfeed order by id desc "
            		+ "limit " + offset + ", " + noOfRecords;

            // Execute the query and get the result set.
            resultSet = statement.executeQuery(query);
            //out.println("<strong>Printing result using context file...</strong><br>");
            while (resultSet.next()) {
                XMLFeed feed = new XMLFeed();
                feed.setId(resultSet.getLong("id"));
                if (resultSet.getString("created_on") != null && resultSet.getString("created_on") != ""){
                	feed.setCreated_on(java.sql.Timestamp.valueOf(resultSet.getString("created_on")));
                }
                if (resultSet.getString("processed_on") != null && resultSet.getString("processed_on") != ""){
                	feed.setProcessed_on(java.sql.Timestamp.valueOf(resultSet.getString("processed_on")));
                }
            	feed.setStatus(resultSet.getString("status"));
            	feed.setXml_file(resultSet.getString("xml_file"));
            	feed.setLog(resultSet.getString("log"));
            	list.add(feed);
            }
         // Close the connection and release the resources used.
            resultSet.close();
            resultSet = statement.executeQuery("SELECT FOUND_ROWS()");
            if(resultSet.next())
                this.noOfRecords = resultSet.getInt(1);
            resultSet.close();
      	  	con.close();
      	  	statement.close();

        } catch (SQLException e) {
        	e.printStackTrace();
        } 
        return list;
    }

	public List<XMLFeed> sortXMLFeeds(String o, int offset, int noOfRecords) throws NamingException {
        Statement statement = null;
        List<XMLFeed> list= new ArrayList<XMLFeed>();
        ResultSet resultSet = null;
        Connection con = null;
        try {
        	con = getConnection();
            // Create the statement to be used to get the results.
            statement = con.createStatement();
            String query = "SELECT SQL_CALC_FOUND_ROWS * FROM service_locator_xmlfeed limit " + offset + ", " + noOfRecords + " order by " + o + " desc";

            // Execute the query and get the result set.
            resultSet = statement.executeQuery(query);
            //out.println("<strong>Printing result using context file...</strong><br>");
            while (resultSet.next()) {
                XMLFeed feed = new XMLFeed();
                feed.setId(resultSet.getLong("id"));
                if (resultSet.getString("created_on") != null && resultSet.getString("created_on") != ""){
                	feed.setCreated_on(java.sql.Timestamp.valueOf(resultSet.getString("created_on")));
                }
                if (resultSet.getString("processed_on") != null && resultSet.getString("processed_on") != ""){
                	feed.setProcessed_on(java.sql.Timestamp.valueOf(resultSet.getString("processed_on")));
                }
            	feed.setStatus(resultSet.getString("status"));
            	feed.setXml_file(resultSet.getString("xml_file"));
            	feed.setLog(resultSet.getString("log"));
            	list.add(feed);
            }
         // Close the connection and release the resources used.
            resultSet.close();
            resultSet = statement.executeQuery("SELECT FOUND_ROWS()");
            if(resultSet.next())
                this.noOfRecords = resultSet.getInt(1);
            resultSet.close();
      	  	con.close();
      	  	statement.close();

        } catch (SQLException e) {
        	e.printStackTrace();
        } 
        return list;
    }

	public long saveXMLFeed(XMLFeed feed){
    	PreparedStatement stmt = null;
    	Connection con = null;
    	ResultSet resultSet = null;
        try {
            con = getConnection();
 			con.setAutoCommit(false);
 			long feed_id = 0;
 	    	String selectQuery = "SELECT MAX(id) FROM service_locator_xmlfeed";
 			String insertQuery = "INSERT INTO service_locator_xmlfeed(created_on, "
 								+ "status, xml_file, log) "
 								+ "VALUES(?, ?, ?, ?)";
 			stmt = con.prepareStatement(selectQuery);
 			resultSet = stmt.executeQuery(selectQuery);
 			if(resultSet!=null && resultSet.next()){
 				feed_id = resultSet.getLong(1) + 1;
	 			stmt = con.prepareStatement(insertQuery);
	 			java.sql.Timestamp now = new java.sql.Timestamp(new java.util.Date().getTime());
	 			stmt.setTimestamp(1, now);
	 			stmt.setString(2, Constants.FEED_STATUS_NEW);
	 			stmt.setString(3, feed.getXml_file() + "-" + feed_id);
	 			stmt.setString(4, feed.getLog());
            }
 			stmt.execute();
            con.commit();
            resultSet.close();
 			stmt.close();
 			con.close();
        } catch (NamingException e) {
			 e.printStackTrace();
		} catch (SQLException e) { 
   		 	e.printStackTrace();
		}
        return feed.getId();
	}

	public void updateXMLFeed(XMLFeed feed){
    	PreparedStatement stmt = null;
    	Connection con = null;
        try {
            con = getConnection();
 			con.setAutoCommit(false);
 			String updateQuery = "UPDATE service_locator_xmlfeed SET xml_file = ? "
 								+ "WHERE id = ?";
 			stmt = con.prepareStatement(updateQuery);
 			stmt.setString(1, feed.getXml_file() + "-" + feed.getId());
 			stmt.setLong(2, feed.getId());
 			stmt.execute();
 			con.commit();
 			stmt.close();
 			con.close();
        }catch (NamingException e) {
			 e.printStackTrace();
		}catch (SQLException e) { 
   		 	e.printStackTrace();
       }
	}

	public XMLFeed editXMLFeed(long feed_id){
    	PreparedStatement stmt = null;
    	Connection con = null;
    	ResultSet resultSet = null;
    	XMLFeed feed = null;
        try {
            con = getConnection();
 			con.setAutoCommit(false);
 	        String editQuery = "SELECT * FROM service_locator_xmlfeed where id = ?";
 			stmt = con.prepareStatement(editQuery);
 			stmt.setLong(1, feed_id);
 			
 			// execute the prepared statement
 			resultSet = stmt.executeQuery();
            while (resultSet.next()) {
            	feed = new XMLFeed();
            	feed.setId(resultSet.getLong("id"));
                if (resultSet.getString("created_on") != null && resultSet.getString("created_on") != ""){
                	feed.setCreated_on(java.sql.Timestamp.valueOf(resultSet.getString("created_on")));
                }
                if (resultSet.getString("processed_on") != null && resultSet.getString("processed_on") != ""){
                	feed.setProcessed_on(java.sql.Timestamp.valueOf(resultSet.getString("processed_on")));
                }
            	feed.setStatus(resultSet.getString("status"));
            	feed.setXml_file(resultSet.getString("xml_file"));
            	feed.setLog(resultSet.getString("log"));
            }
            resultSet.close();
            stmt.close();
            con.close();
            
    	} catch (NamingException e) {
			 e.printStackTrace();
		} catch (SQLException e) { 
    		 e.printStackTrace();
        }
    	return feed;
    }

    public void deleteXMLFeed(long feed_id){
    	PreparedStatement stmt = null;
    	Connection con = null;
        try {
            con = getConnection();
 			con.setAutoCommit(false);

 	        String delQuery = "DELETE FROM service_locator_xmlfeed where id = ?";
 			stmt = con.prepareStatement(delQuery);
 			stmt.setLong(1, feed_id);
 			// execute the prepared statement
 		    stmt.execute();
 		    con.commit();   
 		    con.close();
 			
    	} catch (NamingException e) {
			 e.printStackTrace();
		}catch (SQLException e) { 
    		 e.printStackTrace();
        }
    }

    public void queryProducts(PrintWriter out) throws NamingException {
        Context context = null;
        DataSource datasource = null;
        Connection connect = null;
        Statement statement = null;

        try {
            // Get the context and create a connection
            context = new InitialContext();
            //for QA
            //datasource = (DataSource) context.lookup("java:/comp/env/jdbc/dlocator");
            //for dev
            datasource = (DataSource) context.lookup("java:/comp/env/jdbc/dealerlocator");
            connect = datasource.getConnection();

            // Create the statement to be used to get the results.
            statement = connect.createStatement();
            String query = "SELECT * FROM service_locator_product";

            // Execute the query and get the result set.
            ResultSet resultSet = statement.executeQuery(query);
            out.println("<strong>Printing result using context file...</strong><br>");
            while (resultSet.next()) {
                String prodName = resultSet.getString("name");
                String slug = resultSet.getString("slug");
                boolean flagActive = resultSet.getBoolean("active");
                String category = resultSet.getString("categories");
                out.println("Product Name: " + prodName + 
                        ", slug: " + slug + 
                        ", active: " + flagActive +
                        ", category: "+ category + "<br>");
            }
        } catch (SQLException e) { e.printStackTrace(out);
        } finally {
            // Close the connection and release the resources used.
            try { statement.close(); } catch (SQLException e) { e.printStackTrace(out); }
            try { connect.close(); } catch (SQLException e) { e.printStackTrace(out); }
        }
    }
}
