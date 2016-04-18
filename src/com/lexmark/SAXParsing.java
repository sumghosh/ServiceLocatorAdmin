package com.lexmark;

import com.lexmark.SAXHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

@SuppressWarnings("serial")
public class SAXParsing extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        SAXParserFactory parserFactor = SAXParserFactory.newInstance();
        SAXParser parser;
        SAXHandler handler = null;
		try {
			parser = parserFactor.newSAXParser();
			handler = new SAXHandler();
			
			String xmlPath = getServletContext().getRealPath("xml/sample.xml");
			System.out.println("------------------->"+xmlPath);
			
			File file = new File(xmlPath);
			FileInputStream fis = new FileInputStream(file );
	        parser.parse(fis, handler);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
        

        //Insert the data obtained from XML into database
		/*UpdateDatabase db = new UpdateDatabase();
        for ( Company comp : handler.compList) {
        	out.println(comp.getCompName() + " : " + comp.getCompLocId() + " : " + comp.getCompPhone()
        		  + " : " + comp.getCompAddress().getCountry());
        	//db.saveCompany(comp);
        }
        for (Technician tech : handler.techList) {
        	out.println(tech.getUserId() + " : " + tech.getFirstName() + " " +tech.getLastName() 
        			+ " : " + tech.getTechAddress().getCity());
        	//db.saveTechnician(tech);
        }*/
        
    }
    public void saveData(String filePath) {
        //response.setContentType("text/html");
        //PrintWriter out = response.getWriter();
        SAXParserFactory parserFactor = SAXParserFactory.newInstance();
        SAXParser parser;
        SAXHandler handler = null;
    	try {
    		parser = parserFactor.newSAXParser();
    		handler = new SAXHandler();
    		
    		//String xmlPath = getServletContext().getRealPath("xml/sample.xml");
    		System.out.println("File Path::"+filePath);
    		
    		File file = new File(filePath);
    		FileInputStream fis;
			fis = new FileInputStream(file );
			parser.parse(fis, handler);
			
    	} catch (FileNotFoundException e) {
			e.printStackTrace();
    	} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
    		e.printStackTrace();
    	} catch (SAXException e) {
    		e.printStackTrace();
    	}
        

        //Insert the data obtained from XML into database
    	UpdateDatabase db = new UpdateDatabase();
        for ( Company comp : handler.compList) {
    		//out.println(comp.getCompName() + " : " + comp.getCompLocId() + " : " + comp.getCompPhone()
    		//	  + " : " + comp.getCompAddress().getCountry());
        	long comp_id = db.saveCompany(comp);
        	for (Technician tech : handler.techList) {
        		//db.saveEmployment(comp.get, tech_id);
        		//out.println(tech.getUserId() + " : " + tech.getFirstName() + " " +tech.getLastName() 
            	//		+ " : " + tech.getTechAddress().getCity());
            	long tech_id = db.saveTechnician(tech); //todo save or get technician
            	db.saveEmployment(comp_id, tech_id);
            	for ( Product prod : handler.prodList){
            		long prod_id = db.saveProduct(prod); //todo save or get product
            		//db.saveProduct(prod);
            		if (tech_id > 0 && prod_id > 0 ){
            			db.saveTraining(tech_id, prod_id);
            		}
            	}
        	}
        }
    }

}



/**
 * The Handler for SAX Events.
 */
class SAXHandler extends DefaultHandler {

  List<Company> compList = new ArrayList<Company>();
  List<Technician> techList = new ArrayList<Technician>();
  List<Product> prodList = new ArrayList<Product>();
  Company comp = null;
  Technician technician = null;
  Address compAddress = null;
  Address techAddress = null;
  Product prod = null;
  String content = null;
  boolean compAddrFlag = false;
  boolean techAddrFlag = false;
  @Override
  //Triggered when the start of tag is found.
  public void startElement(String uri, String localName, 
                           String qName, Attributes attributes) 
                           throws SAXException {

    if(qName.equals("CompanyInformation")){
      //Create a new Employee object when the start tag is found
        comp = new Company();
        //comp.setCompName(attributes.getValue(""));
    } else if (qName.equals("CompanyAddress")){
    	compAddress = new Address();
    	compAddrFlag = true;
    } else if (qName.equals("TechnicianInformation")){
    	technician = new Technician();
    } else if (qName.equals("Address")){
    	techAddress = new Address();
    	techAddrFlag = true;
    } else if (qName.equals("ProductInformation")){
    	prod = new Product();
    }
    
  }

  @Override
  public void endElement(String uri, String localName, 
                         String qName) throws SAXException {
	   if(qName.equals("CompanyInformation")){
	     //Add the employee to list once end tag is found
	       compList.add(comp);     
	   } else if (qName.equals("CompanyName")) {   
	     //For all other end tags the employee has to be updated.
	       comp.setCompName(content);
	   } else if (qName.equals("CompanyLocationIdentifier")) {
	       comp.setCompLocId(content);
	   } else if (qName.equals("CompanyPrimaryPhoneNumber")) {
	       comp.setCompPhone(content);
	   } else if (qName.equals("CompanyEmail")){
		   comp.setCompEmail(content);
	   } else if (qName.equals("TechnicianInformation")){
		   techList.add(technician);
	   } else if (qName.equals("UserID")) {
		   technician.setUserId(content);
	   } else if (qName.equals("FirstName")) {
		   technician.setFirstName(content);
	   } else if (qName.equals("LastName")) {
		   technician.setLastName(content);
	   } 
	   
	   if (techAddrFlag){
		   technician.setTechAddress(techAddress);
		   if (qName.equals("AddressLine1")) {
			   techAddress.setAddressLine1(content);
		   } else if (qName.equals("AddressLine2")) {
			   techAddress.setAddressLine2(content);
		   } else if (qName.equals("City")) {
			   techAddress.setCity(content);
		   } else if (qName.equals("Region")) {
			   techAddress.setRegion(content);
		   } else if (qName.equals("PostalCode")) {
			   techAddress.setPostalCode(content);
		   } else if (qName.equals("Country")){
			   techAddress.setCountry(content);
		   } else if (qName.equals("Latitude")) {
			   if (content != null && !content.isEmpty())
				   techAddress.setLatitude(Float.parseFloat(content));
			   else{
				   techAddress.setLatitude(0);
			   }
		   } else if (qName.equals("Longitude")) {
			   if(content != null && !content.isEmpty())
				   techAddress.setLongitude(Float.parseFloat(content));
			   else{
				   techAddress.setLongitude(0);
			   }
			   techAddrFlag = false;
		   }
	   }
	   
	   if (compAddrFlag){
		   comp.setCompAddress(compAddress);
		   if (qName.equals("AddressLine1")) {
			   compAddress.setAddressLine1(content);
		   } else if (qName.equals("AddressLine2")) {
			   compAddress.setAddressLine2(content);
		   } else if (qName.equals("City")) {
			   compAddress.setCity(content);
		   } else if (qName.equals("Region")) {
			   compAddress.setRegion(content);
		   } else if (qName.equals("PostalCode")) {
			   compAddress.setPostalCode(content);
		   } else if (qName.equals("Country")){
			   compAddress.setCountry(content);
		   } else if (qName.equals("Latitude")) {
			   if (content != null && !content.isEmpty())
				   compAddress.setLatitude(Double.parseDouble(content));
			   else{
				   compAddress.setLatitude(0);
			   }
		   } else if (qName.equals("Longitude")) {
			   if(content != null && !content.isEmpty())
				   compAddress.setLongitude(Double.parseDouble(content));
			   else{
				   compAddress.setLongitude(0);
			   }
			   compAddrFlag = false;
		   }
	   }
	   if (qName.equalsIgnoreCase("ProductInformation")){
		   prodList.add(prod);
	   }
	   if(qName.equals("ProductModel")){
		   prod.setName(content);
	   }
  }

  @Override
  public void characters(char[] ch, int start, int length) 
          throws SAXException {
    content = String.copyValueOf(ch, start, length).trim();
  }

}
