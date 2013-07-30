package edu.uc.beeridapp.dao;

import java.util.HashMap;
import java.util.List;

import edu.uc.beeridapp.dto.BeerStyle;

public interface IOfflineBeerDAO {

	public HashMap<Integer, String> fetchStyles();

}
