package edu.uc.beeridapp.dao;

import edu.uc.beeridapp.dto.SocialUser;

public interface ISocialUserDAO {

	public SocialUser fetch(String email) throws Exception;

	public void save(SocialUser validSocialUser) throws Exception;

}
