package edu.uc.beeridapp.dao;

import java.util.List;

import edu.uc.beeridapp.dto.Beer;
import edu.uc.beeridapp.dto.BeerSearch;



public interface IBeerDAO {
	public List<Beer> fetchBeer(BeerSearch beerSearch);
}
