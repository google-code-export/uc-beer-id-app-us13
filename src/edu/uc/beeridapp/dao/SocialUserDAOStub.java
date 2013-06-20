package edu.uc.beeridapp.dao;

import java.util.HashMap;

import edu.uc.beeridapp.dto.SocialUser;

public class SocialUserDAOStub implements ISocialUserDAO {

	@Override
	public SocialUser fetch(String email) throws Exception {
		SocialUser test = new SocialUser("test@email.com", "Test User", "08/10/1979", "password");
		test.setNetwork("fb");
		test.setNetworkID("1234567890");
		test.setAccessToken("ABC1234567890");
		test.setRefreshToken("XYZ0987654321");
		
		HashMap<String, SocialUser> userMap = new HashMap<String, SocialUser>();
		userMap.put(test.getEmail(), test);
		
		if(userMap.containsKey(email)) {
			return userMap.get(email);
		} else {
			throw new Exception();
		}
	}

	@Override
	public void save(SocialUser validSocialUser) throws Exception {
		
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
