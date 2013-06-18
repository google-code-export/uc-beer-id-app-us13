package edu.uc.beeridapp.dto;

public class User {

	private String email;
	private String name;
	private String dob;
	private String password;
	
	public User(String email, String name, String dob, String password) {
		super();
		this.email = email;
		this.name = name;
		this.dob = dob;
		this.password = password;
	}
	
	public User(){ }

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDOB() {
		return dob;
	}
	public void setDOB(String dob) {
		this.dob = dob;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
