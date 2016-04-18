package com.lexmark;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.NamingException;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;


public class QueryWithoutContext {

    public static void displayCompanies(PrintWriter out) throws NamingException {
        MysqlDataSource ds = null;
        Connection connect = null;
        Statement statement = null;

        try {
            // Create a new DataSource (MySQL specifically)
            // and provide the relevant information to be used by Tomcat.
            ds = new MysqlDataSource();
            //for QA
            /*ds.setUrl("jdbc:mysql://localhost:3306/dlocator");
            ds.setUser("root");
            ds.setPassword("");*/
            //for dev
            ds.setUrl("jdbc:mysql://localhost:3306/dealerlocator");
            ds.setUser("dl_user");
            ds.setPassword("lexmark123");
            
            connect = ds.getConnection();

            // Create the statement to be used to get the results.
            statement = connect.createStatement();
            String query = "SELECT * FROM service_locator_company";

            // Execute the query and get the result set.
            ResultSet resultSet = statement.executeQuery(query);
            out.println("<strong>Printing result using DataSource...</strong><br>");
            while (resultSet.next()) {
                String CompName = resultSet.getString("name");
                String address1 = resultSet.getString("address1");
                String city = resultSet.getString("city");
                out.println("Company Name: " + CompName + 
                        ", Address1: " + address1 + 
                        ", City: " + city + "<br>");
            }
        } catch (SQLException e) { e.printStackTrace(out);
        } finally {
            // Close the connection and release the resources used.
            try { statement.close(); } catch (SQLException e) { e.printStackTrace(out); }
            try { connect.close(); } catch (SQLException e) { e.printStackTrace(out); }
        }
    }
    public static void queryProducts(PrintWriter out) throws NamingException {
        MysqlDataSource ds = null;
        Connection connect = null;
        Statement statement = null;

        try {
            // Create a new DataSource (MySQL specifically)
            // and provide the relevant information to be used by Tomcat.
            ds = new MysqlDataSource();
            //for QA
            ds.setUrl("jdbc:mysql://localhost:3306/dlocator");
            ds.setUser("root");
            ds.setPassword("");
            //for dev
            /*ds.setUrl("jdbc:mysql://localhost:3306/dealerlocator");
            ds.setUser("dl_user");
            ds.setPassword("lexmark123");*/
            
            connect = ds.getConnection();

            // Create the statement to be used to get the results.
            statement = connect.createStatement();
            String query = "SELECT * FROM service_locator_product";

            // Execute the query and get the result set.
            ResultSet resultSet = statement.executeQuery(query);
            out.println("<strong>Printing result using DataSource...</strong><br>");
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
