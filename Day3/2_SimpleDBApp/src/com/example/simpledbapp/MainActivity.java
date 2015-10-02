package com.example.simpledbapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.os.Bundle;
import static com.example.simpledbapp.MySqliteHelper.*;

public class MainActivity extends Activity implements OnClickListener {

	private Button buttonInsert;
	private Button buttonQuery;
	private ListView listView;
	private EditText editName;
	private EditText editAddress;

	ArrayList<Person> array;
	private ArrayAdapter<Person> adapter;
	
	private MySqliteHelper helper;
	private SQLiteDatabase db;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
		helper = new MySqliteHelper(this);
		db = helper.getWritableDatabase();
        
        buttonInsert = (Button) findViewById(R.id.buttonInsert);
        buttonInsert.setOnClickListener(this);
        
        buttonQuery = (Button)  findViewById(R.id.buttonQuery);
        buttonQuery.setOnClickListener(this);
        
        editName = (EditText)  findViewById(R.id.editName);
        editAddress = (EditText)  findViewById(R.id.editAddress);
        
        array = new ArrayList<Person>();
        adapter = new ArrayAdapter<Person>(this, android.R.layout.simple_list_item_1, array);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.buttonInsert) {
			ContentValues values = new ContentValues();
//			values.put(COLUMN_PERSON_ID, 2);
			values.put(COLUMN_NAME, editName.getText().toString());
			values.put(COLUMN_ADDRESS, editAddress.getText().toString());
//			values.put(COLUMN_AGE, 56);
			
			// insert into persons
			db.insert(TABLE_PERSONS, null, values);
			
			Toast.makeText(this, "Inserted the values :)", Toast.LENGTH_SHORT).show();
			
		} else if (v.getId() == R.id.buttonQuery) {
			// select personId, personName, personAddress, personAge from persons;
			
			array.clear();
			
			String columns[] = new String[] {COLUMN_PERSON_ID, COLUMN_NAME, COLUMN_ADDRESS, COLUMN_AGE};
			Cursor cursor = db.query(TABLE_PERSONS, columns, null, null, null, null, null);
			
			if(!cursor.isAfterLast()) {
				cursor.moveToFirst();
			
				while(!cursor.isAfterLast()) {
					Person p = new Person();
					
					p.personId = cursor.getInt(cursor.getColumnIndex(COLUMN_PERSON_ID));
					p.name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
					p.address = cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS));
					p.personAge = cursor.getInt(cursor.getColumnIndex(COLUMN_AGE));

					array.add(p);

//					Log.e("Person info", String.format("Id:%d, Name:%s, Address:%s, Age:%d", personId, personName, personAddress, personAge));
					cursor.moveToNext();
				}
			}
			
			cursor.close();
			adapter.notifyDataSetChanged();
		}
	}
}