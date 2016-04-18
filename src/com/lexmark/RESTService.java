package com.lexmark;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

@Path("/xmlServices")
public class RESTService {
	@POST
	@Path("/send")
	@Consumes(MediaType.APPLICATION_XML)
	public Response consumeXML( Student student ) {

		String output = student.toString();

		return Response.status(200).entity(output).build();
	}
	
	@POST
	@Path("/bulkupload")
	@Consumes(MediaType.APPLICATION_XML)
	public Response bulkupload(Document doc) throws TransformerConfigurationException, TransformerException
	{
	    //System.out.println("Inside receive....");
	    Logger.getLogger("Inside bulkupload....");
	    String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
	    String year = new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());
	    String month = new SimpleDateFormat("MM").format(Calendar.getInstance().getTime());
		Transformer tf = TransformerFactory.newInstance().newTransformer();
	    //tf.transform(new DOMSource(doc), new StreamResult(System.out));
		File files = new File(Constants.FEED_DIRECTORY + year + Constants.DIR_SEPARATOR + month);
		if(!files.exists()){
			files.mkdirs();
			Logger.getLogger("Directory created");
			//System.out.println("Directory created");
		}
	    UpdateDatabase db = new UpdateDatabase();
	    long feed_id = db.saveXMLFeed(timeStamp);
	    String filePath = Constants.FEED_DIRECTORY +
	    		year + Constants.DIR_SEPARATOR + month + Constants.DIR_SEPARATOR + timeStamp + "-" + feed_id + ".xml";
	    tf.transform(new DOMSource(doc), new StreamResult(filePath));
	    //Save data
	     
	    SAXParsing saxp = new SAXParsing();
	    saxp.saveData(filePath);
	  //process xml feed in a separate thread
	    return Response.status(Constants.STATUS_OK).entity("Success").build();
	}
	
	@GET
	@Path("/getSample")
	public Response welcome(){
		System.out.println("Welcome user...");
		Logger.getLogger("Welcome user");
		return Response.status(200).entity("Welcome to Lexmark..").build();
	}
}
