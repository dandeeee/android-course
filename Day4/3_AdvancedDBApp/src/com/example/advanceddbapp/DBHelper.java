package com.example.advanceddbapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static com.example.advanceddbapp.Constants.*;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "employee.db";
	private static final int VERSION = 1;

	public DBHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table " + PERSONS + " (" 
								   + PERSONID + " integer primary key autoincrement, "
								   + NAME + " text,"
								   + ADDRESS + " text," 
								   + PHONE + " text,"
								   + EMAIL + " text,"
								   + DATE + " text,"
								   + TIME + " text,"
								   + RATING +" float);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	}

}
