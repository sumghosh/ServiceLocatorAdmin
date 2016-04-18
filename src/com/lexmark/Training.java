/**
 * 
 */
package com.lexmark;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author sumghosh
 *
 */
public class Training {
	private int id;
	private int technicianId;
	private int productId;
	private String type;
	private Timestamp lastModified;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the technicianId
	 */
	public int getTechnicianId() {
		return technicianId;
	}
	/**
	 * @param technicianId the technicianId to set
	 */
	public void setTechnicianId(int technicianId) {
		this.technicianId = technicianId;
	}
	/**
	 * @return the productId
	 */
	public int getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(int productId) {
		this.productId = productId;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the lastModified
	 */
	public Timestamp getLastModified() {
		return lastModified;
	}
	/**
	 * @param lastModified the lastModified to set
	 */
	public void setLastModified(Timestamp lastModified) {
		this.lastModified = lastModified;
	}
}
