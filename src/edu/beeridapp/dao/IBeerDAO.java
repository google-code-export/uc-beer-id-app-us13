package edu.beeridapp.dao;

import java.util.List;

import edu.beeridapp.dto.Beer;
import edu.beeridapp.dto.BeerSearch;



public interface IBeerDAO {
	public List<Beer> fetchBeer(BeerSearch beerSearch);
}
