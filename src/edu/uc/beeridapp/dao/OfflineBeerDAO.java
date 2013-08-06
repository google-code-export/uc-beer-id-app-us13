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

/**
 * Beer DAO for accessing offline data 
 * 
 * @author metzgecl
 *
 */
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<BeerStyle> fetchStyles() throws Exception {
		
		final int ID_COLUMN_INDEX = 0;
		final int GUID_COLUMN_INDEX = 1;
		final int STYLE_COLUMN_INDEX = 2;
		
		ArrayList<BeerStyle> allStyles = new ArrayList<BeerStyle>();
		BeerStyle thisBS;

		// add a prompted for the spinner
		BeerStyle prompt = new BeerStyle();
		prompt.setGuid("-1");
		prompt.setStyle("Select a Beer Style...");
		allStyles.add(prompt);
		
		//SQL to get all columns/records from style table
		String selectStylesSQL = "SELECT * FROM " + STYLE_TABLE;
		
		Cursor cursor = getReadableDatabase().rawQuery(selectStylesSQL, null);
		
		//If any records are found, each record's data is added to a BeerStlye object and the 
		//  object is added to the ArrayList
		if(cursor.getCount() > 0) {
			
			cursor.moveToFirst();			
			
			while (!cursor.isAfterLast()) {
				
				thisBS = new BeerStyle();
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<Beer> searchBeers(BeerSearch beerSearch) throws Exception {
		
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BarcodeSearchResult searchBeerByBarcode(String code) {
		
		//SQL to get all columns from the record in the barcode table that matches the passed barcode
		//'Limit 1' added in case of duplicated data, but should not be necessary
		String selectBeerByBarcodeSQL = "SELECT * FROM " + BARCODE_TABLE + " WHERE " + BARCODE + "=" + code + " LIMIT 1;";

		final int ID_COLUMN_INDEX = 0;
		final int GUID_COLUMN_INDEX = 1;
		final int BARCODE_GUID_COLUMN_INDEX = 2;
		final int BARCODE_COLUMN_INDEX = 3;
		
		BarcodeSearchResult thisBSR = null;
		
		/*
		 *  Execute the SQL.  If record found, set BarcodeSearchResult object's 
		 *   attributes to match the record.
		 */
		
		Cursor barcodeCursor = getReadableDatabase().rawQuery(selectBeerByBarcodeSQL, null);

		if(barcodeCursor.getCount() > 0) {

			barcodeCursor.moveToFirst();

			thisBSR = new BarcodeSearchResult();

			thisBSR.setBarcodeID(barcodeCursor.getInt(ID_COLUMN_INDEX));
			thisBSR.setGuid(barcodeCursor.getInt(GUID_COLUMN_INDEX));
			thisBSR.setBarcodeGuid(barcodeCursor.getInt(BARCODE_GUID_COLUMN_INDEX));
			thisBSR.setBarcode(barcodeCursor.getString(BARCODE_COLUMN_INDEX));
			
		}

		barcodeCursor.close();
		
		/*
		 *  Retrieve the data for the matching GUID from the Beer table, only
		 *  if a barcode record was found for it.
		 *  
		 */
		
		if (thisBSR != null){
			Beer thisBeer = searchBeerByGuid(Integer.toString(thisBSR.getGuid()));
			thisBSR.setId(thisBeer.getId());
			thisBSR.setName(thisBeer.getName());
			thisBSR.setStyle(thisBeer.getStyle());
			thisBSR.setCalories(thisBeer.getCalories());
			thisBSR.setAbv(thisBeer.getAbv());
		}
		
		return thisBSR;
	
	}

	/**
	 * Inserts a Beer record into the offline database
	 * 
	 * @param beer
	 */
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
	
	/**
	 * Inserts a BarcodeSearchResult record into the offline database
	 * 
	 * @param bsr
	 */
	public void insert(BarcodeSearchResult bsr) {

		ContentValues barcodeValues = new ContentValues();
		
		barcodeValues.put(GUID, bsr.getGuid());
		barcodeValues.put(BARCODE_GUID, bsr.getBarcodeGuid());
		barcodeValues.put(BARCODE, bsr.getBarcode());
		
		long id = getWritableDatabase().insert(BARCODE_TABLE, BARCODE, barcodeValues);

		bsr.setBarcodeID((int)id);		

	}
	
	/**
	 * Inserts a BeerStyle record into the offline database
	 * 
	 * @param beerStyle
	 */
	public void insert(BeerStyle bs) {
		
		ContentValues values = new ContentValues();
		values.put(GUID, bs.getGuid());
		values.put(STYLE, bs.getStyle());
		
		long id = getWritableDatabase().insert(STYLE_TABLE, STYLE, values);
		
		
		bs.setId((int)id);		
		
	}

	/**
	 * Creation of the offline database tables
	 * 
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {

		// Table to store the beer information
		String createBeerTableSQL = "CREATE TABLE " + BEER_TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, "
										   + GUID       + " INTEGER, "
										   + NAME       + " TEXT, "
										   + STYLE      + " TEXT, "
										   + CALORIES   + " REAL, "
										   + ABV		+ " REAL"
										   + ");";
		
		//Table to store the barcode information
		//GUID in the table pairs to the GUID of the beer record it belongs to
		String createBarCodeTableSQL = "CREATE TABLE " + BARCODE_TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, "
										   + GUID		   + " INTEGER, "
										   + BARCODE_GUID  + " INTEGER, "
										   + BARCODE       + " TEXT"
										   + ");";
		
		//Table to store the Beer Style info for the DetailsSearchActivity Spinner
		String createStyleTableSQL = "CREATE TABLE " + STYLE_TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, "
										   + GUID		   + " INTEGER, "
										   + STYLE         + " TEXT"
										   + ");";
		
		db.execSQL(createBeerTableSQL);
		db.execSQL(createBarCodeTableSQL);
		db.execSQL(createStyleTableSQL);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		
	}

	/**
	 * Searches the Beer Table in the Offline database for a record 
	 * with the specified GUID and returns the data contained in a Beer object
	 * 
	 * @param guid
	 * @return Beer
	 */
	public Beer searchBeerByGuid(String guid2) {
		
		//SQL to get all columns from the record in the beer table that matches the passed GUID
		//'Limit 1' added in case of duplicated data, but should not be necessary
		String selectBeerByGuidSQL = "SELECT * FROM " + BEER_TABLE + " WHERE " + GUID + "=" + guid2 + " LIMIT 1;";
		
		final int ID_COLUMN_INDEX = 0;
		final int GUID_COLUMN_INDEX = 1;
		final int NAME_COLUMN_INDEX = 2;
		final int STYLE_COLUMN_INDEX = 3;
		final int CALORIES_COLUMN_INDEX = 4;
		final int ABV_COLUMN_INDEX = 5;
		
		Beer thisBeer = null;
		
		/*
		 *  Execute the SQL.  If record found, set Beer object's 
		 *   attributes to match the record.
		 */
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

	/**
	 * Searches the Styles Table in the Offline database for a record 
	 * with the specified GUID and returns the data in a BeerStyle object
	 * 
	 * @param guid
	 * @return BeerStyle
	 */
	@Override
	public BeerStyle searchBeerStyleByGuid(String guid) {
		
		//SQL to get all columns from the record in the style table that matches the passed GUID
		//  Limit 1' added in case of duplicated data, but should not be necessary
		String selectBeerStyleByGuidSQL = "SELECT * FROM " + STYLE_TABLE + " WHERE " + GUID + "=" + guid + " LIMIT 1;";

		final int ID_COLUMN_INDEX = 0;
		final int GUID_COLUMN_INDEX = 1;
		final int STYLE_COLUMN_INDEX = 2;
		
		BeerStyle thisBS = null;

		/*
		 *  Execute the SQL.  If record found, set BeerStyle object's 
		 *   attributes to match the record.
		 */
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

	/**
	 * Returns an ArrayList of all Beer names in the Offline Database
	 * @return ArrayList
	 */
	@Override
	public ArrayList<String> fetchBeerNames() {
		
		ArrayList<String> allNames = new ArrayList<String>();
		
		//SQL to return all the Beer Names without duplication
		String selectName = "SELECT DISTINCT " + NAME + " FROM " + BEER_TABLE;
		
		Cursor cursor = getReadableDatabase().rawQuery(selectName, null);
		
		//For each record found, add the names the ArrayList
		if (cursor.getCount() > 0) {
			
			cursor.moveToFirst();
			
			while (!cursor.isAfterLast()) {
				
				 allNames.add(cursor.getString(0));
				 
				 cursor.moveToNext();
			}
			
			cursor.close();
		}
		
		return allNames;
		
	}
	

}
