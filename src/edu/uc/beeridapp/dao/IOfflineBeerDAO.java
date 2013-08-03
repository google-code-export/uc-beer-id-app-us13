package edu.uc.beeridapp.dao;

import edu.uc.beeridapp.dto.BeerStyle;

public interface IOfflineBeerDAO extends IBeerDAO {

	Object searchBeerByGuid(String guid);

	void insert(BeerStyle beerStyle);

	

}
