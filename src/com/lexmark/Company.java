package com.lexmark;

public class Company {
	private int id;
	private String compName;
	private String compLocId;
	private String compPhone;
	private String compEmail;
	private String compUrl;
	private Address compAddress;
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
	 * @return the compName
	 */
	public String getCompName() {
		return compName;
	}
	/**
	 * @param compName the compName to set
	 */
	public void setCompName(String compName) {
		this.compName = compName;
	}
	/**
	 * @return the compLocId
	 */
	public String getCompLocId() {
		return compLocId;
	}
	/**
	 * @param compLocId the compLocId to set
	 */
	public void setCompLocId(String compLocId) {
		this.compLocId = compLocId;
	}
	/**
	 * @return the compPhone
	 */
	public String getCompPhone() {
		return compPhone;
	}
	/**
	 * @param compPhone the compPhone to set
	 */
	public void setCompPhone(String compPhone) {
		this.compPhone = compPhone;
	}
	public String getCompEmail() {
		return compEmail;
	}
	public void setCompEmail(String compEmail) {
		this.compEmail = compEmail;
	}
	/**
	 * @return the compUrl
	 */
	public String getCompUrl() {
		return compUrl;
	}
	/**
	 * @param compUrl the compUrl to set
	 */
	public void setCompUrl(String compUrl) {
		this.compUrl = compUrl;
	}
	/**
	 * @return the compAddress
	 */
	public Address getCompAddress() {
		return compAddress;
	}
	/**
	 * @param compAddress the compAddress to set
	 */
	public void setCompAddress(Address compAddress) {
		this.compAddress = compAddress;
	}
}
