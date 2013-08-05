package edu.uc.beeridapp.dao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;
import edu.uc.beeridapp.dto.BarcodeSearchResult;
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

	private static final String BARCODE_GUID = "barcode_guid";
	private static final String BARCODE = "barcode";
	private static final String GUID = "guid";
	private static final String STYLE = "style";
	private static final String CALORIES = "calories";
	private static final String ABV = "abv";
	private static final String NAME = "name";
	private static final String BEER_STYLES_URL = "http://beerid-api.herokuapp.com/beer_styles.json";
	private static final String BEER_SEARCH_URL_BASE = "http://beerid-api.herokuapp.com/search/beer.json";
	private static final String BARCODE_SEARCH_URL_BASE = "http://beerid-api.herokuapp.com/search/barcode.json?q=";

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
			bs.setGuid(jo.getString(GUID));
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
			paramsArray.add(new BasicNameValuePair(NAME, beerSearch.getName()));
		}

		// if a min ABV search criteria was entered, add the param to the array
		if (!TextUtils.isEmpty(beerSearch.getLessThanABV())) {
			paramsArray.add(new BasicNameValuePair(ABV, beerSearch
					.getLessThanABV()));
		}

		// if a max ABV search criteria was entered, add the param to the array
		if (!TextUtils.isEmpty(beerSearch.getLessThanCalories())) {
			paramsArray.add(new BasicNameValuePair(CALORIES, beerSearch
					.getLessThanCalories()));
		}

		// if a beer style search criteria was entered, add the param to the
		// array
		if (!TextUtils.isEmpty(beerSearch.getStyleGUID())) {
			paramsArray.add(new BasicNameValuePair(STYLE, beerSearch
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
			b.setGuid(jo.getInt(GUID));
			b.setName(jo.getString(NAME));
			b.setAbv(jo.getString(ABV));
			b.setCalories(jo.getString(CALORIES));
			b.setStyle(jo.getString(STYLE));
			beers.add(b);
		}

		return beers;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BarcodeSearchResult searchBeerByBarcode(String code)
			throws Exception {
		// initialize a result object
		BarcodeSearchResult bsr = new BarcodeSearchResult();

		// build the search url
		String searchUrl = BARCODE_SEARCH_URL_BASE + code;

		// get the JSON string from API
		String result = networkDAO.request(searchUrl);

		// pull a JSONObject from the JSON string and build a
		// BarcodeSearchResult object
		JSONObject jo = new JSONObject(result);
		bsr.setGuid(jo.getInt(GUID));
		bsr.setName(jo.getString(NAME));
		bsr.setAbv(jo.getString(ABV));
		bsr.setCalories(jo.getString(CALORIES));
		bsr.setStyle(jo.getString(STYLE));
		bsr.setBarcodeGuid(jo.getInt(BARCODE_GUID));
		bsr.setBarcode(jo.getString(BARCODE));

		return bsr;
	}

}
