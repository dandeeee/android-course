package com.dandeeee.simpledb.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqliteHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "persons.db";
	private static final int VERSION = 1;
	
	public MySqliteHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + DBShema.TABLE_PERSONS +
				   " (" + DBShema.COLUMN_PERSON_ID + " integer,"
				   		+ DBShema.COLUMN_NAME + " text,"
				   		+ DBShema.COLUMN_ADDRESS +"  text,"
				   		+ DBShema.COLUMN_AGE + " integer);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

}
