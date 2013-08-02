package edu.uc.beeridapp.dto;

/**
 * Describes a barcode search result object
 * 
 * @author Tim Guibord
 * 
 */
public class BarcodeSearchResult extends Beer {

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
}
