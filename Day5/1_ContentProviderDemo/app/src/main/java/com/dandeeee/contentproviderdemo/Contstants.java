package com.dandeeee.contentproviderdemo;
import android.net.Uri;

public class Contstants {
	
	// constants for content provider
	// content://<authority>
	// content://com.examples.ContentProviderDemo
	public static final String AUTHORITY = "com.dandeeee.contentproviderdemo";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);
	
	// specific details (PATH)
	// content://com.examples.ContentProviderDemo/Person
	public static final String PATH_PERSON = "Person";
	public static final Uri CONTENT_URI_PERSON = Uri.parse("content://" + AUTHORITY + "/" + PATH_PERSON);
	
	// content://com.examples.ContentProviderDemo/Car
	public static final String PATH_CAR = "Car";
	public static final Uri CONTENT_URI_CAR = Uri.parse("content://" + AUTHORITY + "/" + PATH_CAR);
	
	// constants for car table
	public static final String TABLE_CAR = "car";
	public static final String COLUMN_PRICE = "price";
	public static final String COLUMN_COLOR = "color";
	public static final String COLUMN_COMPANY = "company";
	public static final String COLUMN_CAR_ID = "carId";
	public static final String COLUMN_NAME = "carName";

	// constants for person table
	public static final String TABLE_PERSON = "persons";
	public static final String COLUMN_PID = "personId";
	public static final String COLUMN_PNAME = "personName";
	public static final String COLUMN_ADDRESS = "address";
	
}
