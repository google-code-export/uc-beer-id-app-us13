package edu.uc.beeridapp.dao;

import edu.uc.beeridapp.dto.User;

public interface IUserDAO {

	public User fetch(String email) throws Exception;

	public void save(User user) throws Exception;

}
