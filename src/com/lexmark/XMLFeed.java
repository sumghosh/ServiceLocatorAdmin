package com.lexmark;

import java.sql.Timestamp;

public class XMLFeed {
	long id;
	Timestamp created_on;
	Timestamp processed_on;
	String status;
	String xml_file;
	String log;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Timestamp getCreated_on() {
		return created_on;
	}
	public void setCreated_on(Timestamp created_on) {
		this.created_on = created_on;
	}
	public Timestamp getProcessed_on() {
		return processed_on;
	}
	public void setProcessed_on(Timestamp processed_on) {
		this.processed_on = processed_on;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getXml_file() {
		return xml_file;
	}
	public void setXml_file(String xml_file) {
		this.xml_file = xml_file;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
}
