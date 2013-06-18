package edu.beeridapp.dao;

import java.util.ArrayList;
import java.util.List;

import edu.beeridapp.dto.Beer;
import edu.beeridapp.dto.Beer.beerType;
import edu.beeridapp.dto.BeerSearch;

public class BeerDAOStub implements IBeerDAO{

	public List<Beer> fetchBeer(BeerSearch beerSearch)
	{
		List<Beer> allBeer = new ArrayList<Beer>();
		
		if (beerSearch.getBeer().equalsIgnoreCase("bud light"))
		{
			Beer budLight = new Beer();
			budLight.setPercentAlcohol(4.2);
			budLight.setBeer("Bud Light");
			budLight.setCalories(110);
			budLight.setId(1);
			budLight.setType(beerType.Pilsner);
			allBeer.add(budLight);
		}
		
		if (beerSearch.getBeer().equalsIgnoreCase("blue moon"))
		{
			Beer blueMoon = new Beer();
			blueMoon.setBeer("Blue Moon");
			blueMoon.setType(beerType.Wheat);
			blueMoon.setPercentAlcohol(5.4);
			blueMoon.setCalories(164);
		    blueMoon.setId(2);
		    allBeer.add(blueMoon);
		}
		
		return allBeer;
	}
}
