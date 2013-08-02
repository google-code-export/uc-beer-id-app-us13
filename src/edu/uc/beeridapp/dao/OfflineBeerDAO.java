package edu.uc.beeridapp.dao;

import java.util.ArrayList;

import android.content.Context;
import edu.uc.beeridapp.dto.BarcodeSearchResult;
import edu.uc.beeridapp.dto.Beer;
import edu.uc.beeridapp.dto.BeerSearch;
import edu.uc.beeridapp.dto.BeerStyle;

public class OfflineBeerDAO implements IBeerDAO {

	public OfflineBeerDAO(Context context) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<BeerStyle> fetchStyles() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Beer> searchBeers(BeerSearch beerSearch) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BarcodeSearchResult searchBeerByBarcode(String code) {
		// TODO Auto-generated method stub
		return null;
	}



}
