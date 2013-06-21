package edu.uc.beeridapp.dao;

import java.util.HashMap;

import edu.uc.beeridapp.dto.SocialUser;

public class SocialUserDAOStub implements ISocialUserDAO {

	/**
	 * fetches a Social User object depending on the received email
	 */
	@Override
	public SocialUser fetch(String email) throws Exception 
	{
		//creates a new user and populates the object
		SocialUser test = new SocialUser("test@email.com", "Test User", "08/10/1979", "password");
		test.setNetwork("fb");
		test.setNetworkID("1234567890");
		test.setAccessToken("ABC1234567890");
		test.setRefreshToken("XYZ0987654321");
		
		//puts the user in the hasmap
		HashMap<String, SocialUser> userMap = new HashMap<String, SocialUser>();
		userMap.put(test.getEmail(), test);
		
		//ensures the user is in the hasmap
		if(userMap.containsKey(email)) {
			return userMap.get(email);
		} else {
			throw new Exception();
		}
	}

	/**
	 * saves a SocialUser to the SocialUser DB
	 */
	@Override
	public void save(SocialUser validSocialUser) throws Exception {
		//Makes sure all required fields are not empty
		if(validSocialUser.getNetwork().isEmpty()) {
			throw new Exception();
		}
		
		if(validSocialUser.getNetworkID().isEmpty()) {
			throw new Exception();		
		}
		
		if(validSocialUser.getAccessToken().isEmpty()) {
			throw new Exception();
		}
		
		if(validSocialUser.getRefreshToken().isEmpty()) {
			throw new Exception();
		}
		
	}
}
