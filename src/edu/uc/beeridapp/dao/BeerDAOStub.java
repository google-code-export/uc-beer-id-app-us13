package edu.uc.beeridapp.dao;

import java.util.ArrayList;
import java.util.List;

import edu.uc.beeridapp.dto.Beer;
import edu.uc.beeridapp.dto.Beer.beerColor;
import edu.uc.beeridapp.dto.BeerSearch;
import edu.uc.beeridapp.dto.Beer.beerType;

public class BeerDAOStub implements IBeerDAO{

	/**
	 * fetches a beer from a list of beers depending on beersearch criteria
	 */
	public List<Beer> fetchBeer(BeerSearch beerSearch)
	{
		List<Beer> allBeer = new ArrayList<Beer>();
		//if the search is budlight, populate a bud light object and return it
		if (beerSearch.getBeerName().equalsIgnoreCase("bud light"))
		{
			Beer budLight = new Beer();
			budLight.setPercentAlcohol(4.2);
			budLight.setBeer("Bud Light");
			budLight.setCalories(110);
			budLight.setId(1);
			budLight.setType(beerType.Lager);
			budLight.setColor(beerColor.Pale);
			allBeer.add(budLight);
		}
		//if the search is blue moon, populate a blue moon object and return it
		if (beerSearch.getBeerName().equalsIgnoreCase("blue moon"))
		{
			Beer blueMoon = new Beer();
			blueMoon.setBeer("Blue Moon");
			blueMoon.setType(beerType.Ale);
			blueMoon.setColor(beerColor.Pale);
			blueMoon.setPercentAlcohol(5.4);
			blueMoon.setCalories(164);
		    blueMoon.setId(2);
		    allBeer.add(blueMoon);
		}
		
		return allBeer;
	}
}
