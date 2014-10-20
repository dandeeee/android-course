package com.example.simpledbapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqliteHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "persons.db";
	private static final int VERSION = 1;
	
	public static final String COLUMN_PERSON_ID = "personId";
	public static final String COLUMN_NAME = "personName";
	public static final String COLUMN_ADDRESS = "personAddress";
	public static final String COLUMN_AGE = "personAge";
	public static final String TABLE_PERSONS = "persons";

	public MySqliteHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table " + TABLE_PERSONS + 
				   " (" + COLUMN_PERSON_ID + " integer,"
				   		+ COLUMN_NAME + " text,"
				   		+ COLUMN_ADDRESS +"  text,"
				   		+ COLUMN_AGE + " integer);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
