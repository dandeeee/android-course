package com.dandeeee.contentproviderdemo.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.dandeeee.contentproviderdemo.db.DBHelper;

import static com.dandeeee.contentproviderdemo.db.DBSchema.*;


public class CarContentProvier extends ContentProvider {

	private static final int MATCHER_CODE_PERSON = 1;
	private static final int MATCHER_CODE_CAR = 2;
	static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
	
	static {
		matcher.addURI(
				ContentProviderCoordinates.AUTHORITY,
				ContentProviderCoordinates.PATH_PERSON,
				MATCHER_CODE_PERSON);

		matcher.addURI(
				ContentProviderCoordinates.AUTHORITY,
				ContentProviderCoordinates.PATH_CAR,
				MATCHER_CODE_CAR);
	}
	
	DBHelper helper;
	private SQLiteDatabase database;
	
	@Override
	public boolean onCreate() {
		helper = new DBHelper(getContext());
		database = helper.getWritableDatabase();
		return true;
	}
	
	@Override
	public String getType(Uri arg0) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {

		int code = matcher.match(uri);
		if (code == MATCHER_CODE_PERSON) {
			database.insert(TABLE_PERSON, null, values);
		} else if (code == MATCHER_CODE_CAR) {
			database.insert(TABLE_CAR, null, values);
		}
		
		return uri;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String orderBy) {
		
		Cursor cursor = null;
		
		int code = matcher.match(uri);
		if (code == MATCHER_CODE_PERSON) {
			cursor = database.query(TABLE_PERSON, projection, selection, selectionArgs, null, null, orderBy);
		} else if (code == MATCHER_CODE_CAR) {
			cursor = database.query(TABLE_CAR, projection, selection, selectionArgs, null, null, orderBy);
		}
		
		return cursor;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		return 0;
	}

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		return 0;
	}

}
