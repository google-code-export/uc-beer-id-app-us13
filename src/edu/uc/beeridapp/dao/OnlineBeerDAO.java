package edu.uc.beeridapp.dao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;
import edu.uc.beeridapp.dto.Beer;
import edu.uc.beeridapp.dto.BeerSearch;
import edu.uc.beeridapp.dto.BeerStyle;

/**
 * Beer DAO to perform data access via online data sources
 * 
 * @author Tim Guibord
 * 
 */
public class OnlineBeerDAO implements IBeerDAO {

	private static final String BEER_STYLES_URL = "http://beerid-api.herokuapp.com/beer_styles.json";
	private static final String BEER_SEARCH_URL_BASE = "http://beerid-api.herokuapp.com/search/beer.json";

	private NetworkDAO networkDAO;

	// initialize a NetworkDAO object for API calls
	public OnlineBeerDAO() {
		networkDAO = new NetworkDAO();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<BeerStyle> fetchStyles() throws Exception {
		// initialize a BeerStyle ArrayList
		ArrayList<BeerStyle> allStyles = new ArrayList<BeerStyle>();

		// add a prompted for the spinner
		BeerStyle prompt = new BeerStyle();
		prompt.setGuid("-1");
		prompt.setStyle("Select a Beer Style...");
		allStyles.add(prompt);

		// get JSON string from the API
		String result = networkDAO.request(BEER_STYLES_URL);

		// pull JSONArray from returned JSON string
		JSONArray stylesJSON = new JSONArray(result);

		// iterate through the JSONArray
		for (int i = 0; i < stylesJSON.length(); i++) {
			// Pull JSONObject from array
			JSONObject jo = (JSONObject) stylesJSON.get(i);

			// create a BeerStyle object from the JSONObject and add it to the
			// ArrayList
			BeerStyle bs = new BeerStyle();
			bs.setGuid(jo.getString("guid"));
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

		// initialize a Beers ArrayList
		ArrayList<Beer> beers = new ArrayList<Beer>();

		// List to hold search params
		List<NameValuePair> paramsArray = new LinkedList<NameValuePair>();


		// if a name search criteria was entered, add the param to the array
		if (!TextUtils.isEmpty(beerSearch.getName())) {
			paramsArray
					.add(new BasicNameValuePair("name", beerSearch.getName()));
		}

		// if a min ABV search criteria was entered, add the param to the array
		if (!TextUtils.isEmpty(beerSearch.getLessThanABV())) {
			paramsArray.add(new BasicNameValuePair("abv", beerSearch
					.getLessThanABV()));
		}

		// if a max ABV search criteria was entered, add the param to the array
		if (!TextUtils.isEmpty(beerSearch.getLessThanCalories())) {
			paramsArray.add(new BasicNameValuePair("cal", beerSearch
					.getLessThanCalories()));
		}

		// if a beer style search criteria was entered, add the param to the
		// array
		if (!TextUtils.isEmpty(beerSearch.getStyleGUID())) {
			paramsArray.add(new BasicNameValuePair("type", beerSearch
					.getStyleGUID()));
		}

		String result = "";

		// check to see if the search has params
		if (paramsArray.size() > 0) {
			// yes, perform search with params
			result = networkDAO.request(BEER_SEARCH_URL_BASE, paramsArray);
		} else {
			// no, perform search w/o params
			result = networkDAO.request(BEER_SEARCH_URL_BASE);
		}

		// pull a JSONArray from the results string
		JSONArray beersJSON = new JSONArray(result);

		// iterate through the JSONArray
		for (int i = 0; i < beersJSON.length(); i++) {
			// Pull JSONObject from array
			JSONObject jo = (JSONObject) beersJSON.get(i);

			// create a Beer object from the JSONObject and add it to the
			// ArrayList
			Beer b = new Beer();
			b.setGuid(jo.getInt("guid"));
			b.setName(jo.getString("name"));
			b.setAbv(jo.getString("abv"));
			b.setCalories(jo.getString("calories"));
			b.setStyle(jo.getString("style"));
			beers.add(b);
		}

		return beers;
	}

}
