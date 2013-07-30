package edu.uc.beeridapp.dao;

import java.util.ArrayList;

import edu.uc.beeridapp.dto.BeerStyle;

public interface IOnlineBeerDAO {

	public ArrayList<BeerStyle> fetchStyles() throws Exception;

}
