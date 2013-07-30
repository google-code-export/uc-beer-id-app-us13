package edu.uc.beeridapp.dao;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import org.apache.commons.lang3.StringUtils;

import edu.uc.beeridapp.dto.Beer;
import edu.uc.beeridapp.dto.BeerSearch;
import edu.uc.beeridapp.dto.BeerStyle;

public class OnlineBeerDAO implements IBeerDAO {
	
	private static final String BEER_STYLES_URL = "http://beerid-api.herokuapp.com/beer_styles.json";
	private static final String BEER_SEARCH_URL_BASE = "http://beerid-api.herokuapp.com/search/beer.json";
	
	private NetworkDAO networkDAO;
	
	//initialize a NetworkDAO object for API calls
	public OnlineBeerDAO() {
		networkDAO = new NetworkDAO();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<BeerStyle> fetchStyles() throws Exception {
		//initialize a BeerStyle ArrayList
		ArrayList<BeerStyle> allStyles = new ArrayList<BeerStyle>(); 
		
		//get JSON string from the API
		String result = networkDAO.request(BEER_STYLES_URL);
		
		//pull JSONArray from returned JSON string
		JSONArray stylesJSON = new JSONArray(result);
		
		//iterate through the JSONArray
		for (int i = 0; i < stylesJSON.length(); i++) {
			//Pull JSONObject from array
			JSONObject jo = (JSONObject) stylesJSON.get(i);
			
			//create a BeerStyle object from the JSONObject and add it to the ArrayList
			BeerStyle bs = new BeerStyle();
			bs.setGuid(jo.getInt("guid"));
			bs.setStyle(jo.getString("style"));
			allStyles.add(bs);
		}

		return allStyles;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<Beer> searchBeers(BeerSearch beerSearch) throws Exception {
		
		//initialize a Beers ArrayList
		ArrayList<Beer> beers = new ArrayList<Beer>();
		
		//ArrayList to hold search params
		ArrayList<String> paramsArray = new ArrayList<String>();
		
		//initialize the search url container
		String searchURL ="";
		
		//if a name search criteria was entered, add the param to the array
		if(!beerSearch.getName().isEmpty()) {
			paramsArray.add("s=" + beerSearch.getName());
		}
		
		//if a min ABV search criteria was entered, add the param to the array
		if(!beerSearch.getGreaterThanABV().isEmpty()) {
			paramsArray.add("g=" + beerSearch.getGreaterThanABV());
		}
		
		//if a max ABV search criteria was entered, add the param to the array
		if(!beerSearch.getLessThanABV().isEmpty()) {
			paramsArray.add("l=" + beerSearch.getLessThanABV());
		}
		
		//if a beer style search criteria was entered, add the param to the array
		if(!beerSearch.getStyleGUID().isEmpty()) {
			paramsArray.add("t=" + beerSearch.getStyleGUID());
		}

		//if params exist, build the search url
		if (paramsArray.size() > 0) {
			//join the params into a valid URL query string
			String params = StringUtils.join(paramsArray, "&");
			
			//build the search URL
			searchURL = BEER_SEARCH_URL_BASE + "?" + params;
		}
		
		
		//get the JSON string from the API
		String result = networkDAO.request(searchURL);
		
		//pull a JSONArray from the results string
		JSONArray beersJSON = new JSONArray(result);
		
		//iterate through the JSONArray
		for (int i = 0; i < beersJSON.length(); i++) {
			//Pull JSONObject from array
			JSONObject jo = (JSONObject) beersJSON.get(i);
			
			//create a Beer object from the JSONObject and add it to the ArrayList
			Beer b = new Beer();
			b.setGuid(jo.getInt("guid"));
			b.setName(jo.getString("name"));
			b.setAbv(jo.getString("abv"));
			b.setStyle(jo.getString("style"));
			beers.add(b);
		}
		
		return beers;
	}

}
