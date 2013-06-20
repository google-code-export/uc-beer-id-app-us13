package edu.uc.beeridapp.dao;

import edu.uc.beeridapp.dto.SocialUser;

/**
 * Social User DAO Interface
 * @author Tim Guibord
 *
 */
public interface ISocialUserDAO {

	/**
	 * Fetches Social User by email address
	 * @param email
	 * @return Social User with matching email address
	 * @throws Exception
	 */
	public SocialUser fetch(String email) throws Exception;

	/**
	 * Save a new Social User
	 * @param socialUser
	 * @throws Exception
	 */
	public void save(SocialUser socialUser) throws Exception;

}
