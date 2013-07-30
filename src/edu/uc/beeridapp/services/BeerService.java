package edu.uc.beeridapp.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.content.Context;

import edu.uc.beeridapp.dao.IOfflineBeerDAO;
import edu.uc.beeridapp.dao.IOnlineBeerDAO;
import edu.uc.beeridapp.dao.OfflineBeerDAO;
import edu.uc.beeridapp.dao.OnlineBeerDAO;
import edu.uc.beeridapp.dto.Beer;
import edu.uc.beeridapp.dto.BeerSearch;
import edu.uc.beeridapp.dto.BeerStyle;

public class BeerService implements IBeerService {
	
	private IOnlineBeerDAO onlineBeerDAO;
	private IOfflineBeerDAO offlineBeerDAO;
	
	public BeerService(Context context) {
		onlineBeerDAO = new OnlineBeerDAO();
		offlineBeerDAO = new OfflineBeerDAO(context);
	}

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
		
		try {
			ArrayList<BeerStyle> allStyles = onlineBeerDAO.fetchStyles();
			HashMap<Integer, String> beerStylesMap = new HashMap<Integer, String>();
			
			for (BeerStyle style: allStyles) {
				beerStylesMap.put(style.getGuid(), style.getStyle());
			}
			
//			cacheStyles((ArrayList<BeerStyle>) allStyles.clone());
			return beerStylesMap;
		} catch (Exception e) {
			return offlineBeerDAO.fetchStyles();
		}
	}

	private void cacheStyles(ArrayList<BeerStyle> allStyles) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Beer seachBarcode(String code) {
		// TODO Auto-generated method stub
		return null;
	}

}
