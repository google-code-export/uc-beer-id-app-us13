package edu.uc.beeridapp.dto;

public class SocialUser extends User {

	private String network;
	private String networkID;
	private String accessToken;
	private String refreshToken;
	
	/**
	 * Describes a general social user for the BeedID application to be able to sign in with social networking sites
	 * @param email
	 * @param name
	 * @param dob
	 * @param password
	 */
	public SocialUser(String email, String name, String dob, String password) {
		super(email, name, dob, password);
		// TODO Auto-generated constructor stub
	}

	public SocialUser() {
		// TODO Auto-generated constructor stub
	}

	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public String getNetworkID() {
		return networkID;
	}

	public void setNetworkID(String networkID) {
		this.networkID = networkID;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}



}
