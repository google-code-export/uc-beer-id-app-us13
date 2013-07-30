package edu.uc.beeridapp.services;

import java.util.HashMap;
import java.util.List;

import edu.uc.beeridapp.dto.Beer;
import edu.uc.beeridapp.dto.BeerSearch;
import edu.uc.beeridapp.dto.BeerStyle;

public class BeerServiceStub implements IBeerService {

	@Override
	public Beer fetchBeer(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Beer> seachBeers(BeerSearch bs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<Integer, String> fetchBeerStyles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Beer seachBarcode(String code) {
		// TODO Auto-generated method stub
		return null;
	}

}
