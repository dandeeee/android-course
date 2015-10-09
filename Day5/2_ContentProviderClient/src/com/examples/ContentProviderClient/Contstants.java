package com.examples.ContentProviderClient;

import android.net.Uri;

public class Contstants {
	
	// constants for content provider
	// content://<authority>
	// content://com.examples.ContentProviderDemo
	public static final String AUTHORITY = "com.examples.ContentProviderDemo";
	public static final String PATH_CAR = "Car";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + PATH_CAR);
	
	
	// constants for car table
	public static final String TABLE_CAR = "car";
	public static final String COLUMN_PRICE = "price";
	public static final String COLUMN_COLOR = "color";
	public static final String COLUMN_COMPANY = "company";
	public static final String COLUMN_CAR_ID = "carId";
	public static final String COLUMN_NAME = "carName";

}
