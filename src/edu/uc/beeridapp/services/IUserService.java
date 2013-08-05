package edu.uc.beeridapp.services;
/**
 * User Service Interface
 * @author Dyllon Dekok
 *
 */
public interface IUserService 
{
    /**
     * login to the BeerID App
     * @param email
     * @param password
     * @return
     * @throws Exception
     */
	public boolean logon(String email, String password) throws Exception;

}
