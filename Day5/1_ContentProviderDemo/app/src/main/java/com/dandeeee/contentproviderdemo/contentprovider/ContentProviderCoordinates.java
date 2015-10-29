package com.dandeeee.contentproviderdemo.contentprovider;

import android.net.Uri;

public class ContentProviderCoordinates {
	
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

}
