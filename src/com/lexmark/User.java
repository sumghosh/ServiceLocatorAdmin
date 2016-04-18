package com.lexmark;

import java.sql.Date;
import java.sql.Timestamp;

public class User {
	int id;
	String password;
	Timestamp lastLogin;
	short isSuperuser;
	String username;
	String firstName;
	String lastName;
	String email;
	short isStaff;
	short isActive;
	Timestamp dateJoined;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Timestamp getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}
	public short getIsSuperuser() {
		return isSuperuser;
	}
	public void setIsSuperuser(short isSuperuser) {
		this.isSuperuser = isSuperuser;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public short getIsStaff() {
		return isStaff;
	}
	public void setIsStaff(short isStaff) {
		this.isStaff = isStaff;
	}
	public short getIsActive() {
		return isActive;
	}
	public void setIsActive(short isActive) {
		this.isActive = isActive;
	}
	public Timestamp getDateJoined() {
		return dateJoined;
	}
	public void setDateJoined(Timestamp dateJoined) {
		this.dateJoined = dateJoined;
	}
}
