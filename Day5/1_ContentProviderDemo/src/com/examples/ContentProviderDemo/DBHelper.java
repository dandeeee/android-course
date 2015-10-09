package com.examples.ContentProviderDemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final int VERSION = 1;
	private static final String DB_NAME = "cars.sqlite";

	public DBHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table " + Contstants.TABLE_CAR + " ( "
				+ Contstants.COLUMN_CAR_ID + " integer primary key autoincrement,"
				+ Contstants.COLUMN_NAME + " text, "
				+ Contstants.COLUMN_COMPANY + " text, "
				+ Contstants.COLUMN_COLOR + " text, "
				+ Contstants.COLUMN_PRICE + " float);");
		
		db.execSQL("create table " + Contstants.TABLE_PERSON + " ( "
				+ Contstants.COLUMN_PID + " integer primary key autoincrement,"
				+ Contstants.COLUMN_PNAME + " text, "
				+ Contstants.COLUMN_ADDRESS + " text);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	}

}
