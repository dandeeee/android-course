package com.dandeeee.advanceddbapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "employee.db";
	private static final int VERSION = 1;

	public DBHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table " + DBSchema.PERSONS + " ("
								   + DBSchema.PERSONID + " integer primary key autoincrement, "
								   + DBSchema.NAME + " text,"
								   + DBSchema.ADDRESS + " text,"
								   + DBSchema.PHONE + " text,"
								   + DBSchema.EMAIL + " text,"
								   + DBSchema.DATE + " text,"
								   + DBSchema.TIME + " text,"
								   + DBSchema.RATING +" float);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	}

}
