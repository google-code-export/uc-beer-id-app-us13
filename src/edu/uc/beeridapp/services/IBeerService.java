package edu.uc.beeridapp.services;

import java.util.HashMap;
import java.util.List;

import edu.uc.beeridapp.dto.Beer;
import edu.uc.beeridapp.dto.BeerSearch;
import edu.uc.beeridapp.dto.BeerStyle;

public interface IBeerService {
	public Beer fetchBeer(int id);
	public List<Beer> seachBeers(BeerSearch bs);
	public HashMap<Integer, String> fetchBeerStyles();
	public Beer seachBarcode(String code);
}
