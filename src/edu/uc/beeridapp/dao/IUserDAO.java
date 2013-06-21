package edu.uc.beeridapp.dao;

import edu.uc.beeridapp.dto.User;

/**
 * User DAO Interface
 * 
 * @author Tim Guibord
 *
 */
public interface IUserDAO {

	/**
	 * Fetches a user by email address
	 * @param email
	 * @return User with matching email
	 * @throws Exception
	 */
	public User fetch(String email) throws Exception;

	/**
	 * Saves a new user
	 * @param user
	 * @throws Exception
	 */
	public void save(User user) throws Exception;

}
