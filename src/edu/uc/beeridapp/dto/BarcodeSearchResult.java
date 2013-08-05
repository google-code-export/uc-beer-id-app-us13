package edu.uc.beeridapp.dto;

/**
 * Describes a barcode search result object
 * 
 * @author Tim Guibord
 * 
 */
public class BarcodeSearchResult extends Beer implements Cloneable {

	private static final long serialVersionUID = 1L;

	private int barcodeGuid;
	private String barcode;

	public int getBarcodeGuid() {
		return barcodeGuid;
	}

	public void setBarcodeGuid(int barcodeGuid) {
		this.barcodeGuid = barcodeGuid;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	
	/**
	 * make the object thread safe for local caching after return from online API
	 */
	@Override
	public BarcodeSearchResult clone() {
		try {
			return (BarcodeSearchResult) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
