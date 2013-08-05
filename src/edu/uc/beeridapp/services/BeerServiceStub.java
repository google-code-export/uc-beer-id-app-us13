package edu.uc.beeridapp.services;

import java.util.ArrayList;
import java.util.List;

import edu.uc.beeridapp.dto.BarcodeSearchResult;
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
	public List<Beer> fetchBeers(BeerSearch beerSearch) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BarcodeSearchResult fetchBeerByBarcode(String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<BeerStyle> fetchBeerStyles() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> fetchBeerNames() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
