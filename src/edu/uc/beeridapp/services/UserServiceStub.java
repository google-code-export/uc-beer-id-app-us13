package edu.uc.beeridapp.services;

import edu.uc.beeridapp.dao.IUserDAO;
import edu.uc.beeridapp.dao.UserDAOStub;
import edu.uc.beeridapp.dto.User;

public class UserServiceStub implements IUserService {

	IUserDAO userDAO;

	/**
	 * attempts to login to the BeerID App
	 */
	@Override
	public boolean logon(String email, String password) throws Exception {

		userDAO = new UserDAOStub();
		User testUser = userDAO.fetch(email);
		//if the username and password match, return true, else return false
		if (testUser.getPassword().equals(password)) {
			return true;
		} else if(testUser.equals(null)) {
			return false;
		}

		return false;

	}

}
