package edu.uc.beeridapp.dao;

import java.util.HashMap;

import edu.uc.beeridapp.dto.User;

/**
 * Stubs for User DAO
 * @author Tim Guibord
 *
 */
public class UserDAOStub implements IUserDAO {

	/**
	 * Fetches a user by email address
	 * 
	 * @param email
	 * @return a user with matching email
	 * @throws Exception
	 */
	@Override
	public User fetch(String email) throws Exception {
		//create a user
		User test = new User();
		test.setEmail("test@email.com");
		test.setName("Test User");
		test.setDOB("08/10/1979");
		test.setPassword("password");
		
		//create a user HashMap for future use;  adds user to HashMap
		HashMap<String, User> userMap = new HashMap<String, User>();
		userMap.put(test.getEmail(), test);
		
		//if HashMap has user with email returns the user object
		if(userMap.containsKey(email)) {
			return userMap.get(email);
		} else {
			throw new Exception();
		}
	}

	/**
	 * Save a new user
	 * 
	 * @param user
	 * @throws Exception
	 */
	@Override
	public void save(User user) throws Exception {
		
		//checks to see if an user attribute is missing
		if(user.getEmail().isEmpty()) {
			throw new Exception();
		}else if(user.getName().isEmpty()) {
			throw new Exception();
		} else if(user.getDOB().isEmpty()){
			throw new Exception();
		} else if(user.getPassword().isEmpty()) {
			throw new Exception();
		}
	}
}
