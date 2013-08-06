package edu.uc.beeridapp.dao;

import java.util.ArrayList;

import edu.uc.beeridapp.dto.Beer;
import edu.uc.beeridapp.dto.BeerStyle;

public interface IOfflineBeerDAO extends IBeerDAO {

	public Beer searchBeerByGuid(String guid);

	void insert(BeerStyle beerStyle);

	public BeerStyle searchBeerStyleByGuid(String guid);

	public ArrayList<String> fetchBeerNames();

	

}
