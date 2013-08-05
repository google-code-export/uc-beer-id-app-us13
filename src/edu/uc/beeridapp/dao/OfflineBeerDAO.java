package edu.uc.beeridapp.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import edu.uc.beeridapp.dto.BarcodeSearchResult;
import edu.uc.beeridapp.dto.Beer;
import edu.uc.beeridapp.dto.BeerSearch;
import edu.uc.beeridapp.dto.BeerStyle;


public class OfflineBeerDAO extends SQLiteOpenHelper implements IOfflineBeerDAO {

	private static final String BARCODE_GUID = "barcode_guid";
	private static final String BARCODE = "barcode";
	private static final String GUID = "guid";
	private static final String STYLE = "style";
	private static final String CALORIES = "calories";
	private static final String ABV = "abv";
	private static final String NAME = "name";
	private static final String BEER_TABLE = "beers";
	private static final String BARCODE_TABLE = "barcodes";
	private static final String STYLE_TABLE = "styles";
	

	public OfflineBeerDAO(Context context) {
		super(context, "beeridapp.db", null, 1);
	}

	@Override
	public ArrayList<BeerStyle> fetchStyles() throws Exception {
		// TODO Unverified
		final int ID_COLUMN_INDEX = 0;
		final int GUID_COLUMN_INDEX = 1;
		final int STYLE_COLUMN_INDEX = 2;
		
		ArrayList<BeerStyle> allStyles = new ArrayList<BeerStyle>();
		BeerStyle thisBS;
		
		String selectStylesSQL = "SELECT * FROM " + STYLE_TABLE;
		
		Cursor cursor = getReadableDatabase().rawQuery(selectStylesSQL, null);
		
		if(cursor.getCount() > 0) {
			
			cursor.moveToFirst();
			
			thisBS = new BeerStyle();
			
			while (!cursor.isAfterLast()) {
				thisBS.setId(cursor.getInt(ID_COLUMN_INDEX));
				thisBS.setGuid(Integer.toString(cursor.getInt(GUID_COLUMN_INDEX)));
				thisBS.setStyle(cursor.getString(STYLE_COLUMN_INDEX));
				allStyles.add(thisBS);
				
				cursor.moveToNext();
			}
		}
		
		cursor.close();
		
		return allStyles;
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

	public void insert(Beer beer) {
		
		ContentValues values = new ContentValues();
		values.put(GUID, beer.getGuid());
		values.put(STYLE, beer.getStyle());
		values.put(CALORIES, beer.getCalories());
		values.put(ABV,  beer.getAbv());
		values.put(NAME, beer.getName());
		
		long id = getWritableDatabase().insert(BEER_TABLE, NAME, values);
		
		beer.setId((int)id);		
		
	}
	
	public void insert(BeerStyle bs) {
		
		ContentValues values = new ContentValues();
		values.put(GUID, bs.getGuid());
		values.put(STYLE, bs.getStyle());
		
		long id = getWritableDatabase().insert(STYLE_TABLE, STYLE, values);
		
		
		bs.setId((int)id);		
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		String createBeerTableSQL = "CREATE TABLE " + BEER_TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, "
										   + GUID       + " INTEGER, "
										   + NAME       + " TEXT, "
										   + STYLE      + " TEXT, "
										   + CALORIES   + " REAL, "
										   + ABV		+ " REAL"
										   + ");";
		
		String createBarCodeTableSQL = "CREATE TABLE " + BARCODE_TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, "
										   + GUID		   + " INTEGER, "
										   + BARCODE_GUID  + " INTEGER, "
										   + BARCODE       + " TEXT"
										   + ");";
		
		String createStyleTableSQL = "CREATE TABLE " + STYLE_TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, "
										   + GUID		   + " INTEGER, "
										   + STYLE         + " TEXT"
										   + ");";
		
		db.execSQL(createBeerTableSQL);
		db.execSQL(createBarCodeTableSQL);
		db.execSQL(createStyleTableSQL);
		//db.close();
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	// This is used when caching to keep duplicates from being inserted
	public Beer searchBeerByGuid(String guid2) {
		
		String selectBeerByGuidSQL = "SELECT * FROM " + BEER_TABLE + " WHERE " + GUID + "=" + guid2 + " LIMIT 1;";
		
		final int ID_COLUMN_INDEX = 0;
		final int GUID_COLUMN_INDEX = 1;
		final int NAME_COLUMN_INDEX = 2;
		final int STYLE_COLUMN_INDEX = 3;
		final int CALORIES_COLUMN_INDEX = 4;
		final int ABV_COLUMN_INDEX = 5;
		
		Beer thisBeer = null;
		
		Cursor cursor = getReadableDatabase().rawQuery(selectBeerByGuidSQL, null);
		
		if(cursor.getCount() > 0) {

			cursor.moveToFirst();

			thisBeer = new Beer();

			thisBeer.setId(cursor.getInt(ID_COLUMN_INDEX));
			thisBeer.setGuid(cursor.getInt(GUID_COLUMN_INDEX));
			thisBeer.setName(cursor.getString(NAME_COLUMN_INDEX));
			thisBeer.setStyle(cursor.getString(STYLE_COLUMN_INDEX));
			thisBeer.setCalories(Double.toString(cursor.getDouble(CALORIES_COLUMN_INDEX)));
			thisBeer.setAbv(Double.toString(cursor.getDouble(ABV_COLUMN_INDEX)));				

		}
		
		cursor.close();
		return thisBeer;
	}

	@Override
	public BeerStyle searchBeerStyleByGuid(String guid) {
		
		String selectBeerStyleByGuidSQL = "SELECT * FROM " + STYLE_TABLE + " WHERE " + GUID + "=" + guid + " LIMIT 1;";

		final int ID_COLUMN_INDEX = 0;
		final int GUID_COLUMN_INDEX = 1;
		final int STYLE_COLUMN_INDEX = 2;
		
		BeerStyle thisBS = null;

		Cursor cursor = getReadableDatabase().rawQuery(selectBeerStyleByGuidSQL, null);

		if(cursor.getCount() > 0) {

			cursor.moveToFirst();

			thisBS = new BeerStyle();

			thisBS.setId(cursor.getInt(ID_COLUMN_INDEX));
			thisBS.setGuid(Integer.toString(cursor.getInt(GUID_COLUMN_INDEX)));
			thisBS.setStyle(cursor.getString(STYLE_COLUMN_INDEX));
			
		}

		cursor.close();
		return thisBS;
	}

	@Override
	public ArrayList<String> fetchBeerNames() {
		// declare our return value.
		ArrayList<String> allNames = new ArrayList<String>();
		
		// the SQL query to select all unique beers.
		String selectName = "SELECT DISTINCT " + NAME + " FROM " + BEER_TABLE;
		
		// run the query.
		Cursor cursor = getReadableDatabase().rawQuery(selectName, null);
		
		// do we have at least one result?
		if (cursor.getCount() > 0) {
			// go to the first result.
			cursor.moveToFirst();
			
			// iterate over the result.
			while (!cursor.isAfterLast()) {
				// add the result to the collection.
				 allNames.add(cursor.getString(0));
				 
				 // move to the next row.
				 cursor.moveToNext();
			}
			// close cursor to free up memory
			cursor.close();
		}
		return allNames;
	}
	

}
