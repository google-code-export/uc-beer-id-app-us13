package edu.uc.beeridapp.dao;

import java.util.HashMap;

import edu.uc.beeridapp.dto.User;

public class UserDAOStub implements IUserDAO {

	@Override
	public User fetch(String email) throws Exception {
		User test = new User("test@email.com", "Test User", "08/10/1979", "password");
		
		HashMap<String, User> userMap = new HashMap<String, User>();
		userMap.put(test.getEmail(), test);
		
		if(userMap.containsKey(email)) {
			return userMap.get(email);
		} else {
			throw new Exception();
		}
	}

	@Override
	public void save(User user) throws Exception {
		if(user.getEmail().isEmpty() || user.getName().isEmpty() || user.getDOB().isEmpty() || user.getPassword().isEmpty()) {
			throw new Exception();
		}
		
	}
}
