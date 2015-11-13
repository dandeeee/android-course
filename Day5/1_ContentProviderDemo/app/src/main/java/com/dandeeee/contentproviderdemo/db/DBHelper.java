package com.dandeeee.contentproviderdemo.db;

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
		db.execSQL("create table " + DBSchema.TABLE_CAR + " ( "
				+ DBSchema.CAR_CAR_ID + " integer primary key autoincrement,"
				+ DBSchema.CAR_NAME + " text, "
				+ DBSchema.CAR_COMPANY + " text, "
				+ DBSchema.CAR_COLOR + " text, "
				+ DBSchema.CAR_PRICE + " float);");
		
		db.execSQL("create table " + DBSchema.TABLE_PERSON + " ( "
				+ DBSchema.PERSON_PID + " integer primary key autoincrement,"
				+ DBSchema.PERSON_PNAME + " text, "
				+ DBSchema.PERSON_ADDRESS + " text);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	}

}
