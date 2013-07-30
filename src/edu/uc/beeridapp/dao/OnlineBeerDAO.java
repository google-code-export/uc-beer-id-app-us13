package edu.uc.beeridapp.dao;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.uc.beeridapp.dto.BeerStyle;

public class OnlineBeerDAO implements IOnlineBeerDAO {
	
	private NetworkDAO networkDAO;
	
	public OnlineBeerDAO() {
		networkDAO = new NetworkDAO();
	}

	@Override
	public ArrayList<BeerStyle> fetchStyles() throws Exception {
		
		ArrayList<BeerStyle> allStyles = new ArrayList<BeerStyle>(); 
		String url = "http://beerid-api.herokuapp.com/beer_styles.json";
		
		String result = networkDAO.request(url);
		JSONArray stylesJSON = new JSONArray(result);
		
		for (int i = 0; i < stylesJSON.length(); i++) {
			JSONObject jo = (JSONObject) stylesJSON.get(i);
			BeerStyle bs = new BeerStyle();
			bs.setGuid(jo.getInt("guid"));
			bs.setStyle(jo.getString("style"));
			allStyles.add(bs);
		}

		return allStyles;
	}

}
