package com.lexmark;

import java.sql.Date;
import java.sql.Timestamp;

public class Address {
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String postalCode;
	private String country;
	private String region;
	private String addressHash;
	private String locationType;
	private double latitude;
	private double longitude;
	private Timestamp geocodedOn;
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	/**
	 * @return the addresHash
	 */
	public String getAddressHash() {
		return addressHash;
	}
	/**
	 * @param addresHash the addresHash to set
	 */
	public void setAddressHash(String addressHash) {
		this.addressHash = addressHash;
	}
	/**
	 * @return the locationType
	 */
	public String getLocationType() {
		return locationType;
	}
	/**
	 * @param locationType the locationType to set
	 */
	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	/**
	 * @return the geocodedOn
	 */
	public Timestamp getGeocodedOn() {
		return geocodedOn;
	}
	/**
	 * @param geocodedOn the geocodedOn to set
	 */
	public void setGeocodedOn(Timestamp geocodedOn) {
		this.geocodedOn = geocodedOn;
	}
}
