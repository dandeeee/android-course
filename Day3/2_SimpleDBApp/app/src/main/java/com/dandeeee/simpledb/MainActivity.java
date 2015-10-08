package com.dandeeee.simpledb;


import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.os.Bundle;

import com.dandeeee.simpledb.db.DBShema;
import com.dandeeee.simpledb.db.MySqliteHelper;
import com.dandeeee.simpledb.model.Person;

import java.util.ArrayList;

public class MainActivity extends Activity implements OnClickListener {

	private Button buttonInsert;
	private Button buttonQuery;
	private ListView listView;
	private EditText editName;
	private EditText editAddress;

	ArrayList<Person> array;
	private ArrayAdapter<Person> adapter;

	private MySqliteHelper DBhelper;
	private SQLiteDatabase db;

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
		DBhelper = new MySqliteHelper(this);
		db = DBhelper.getWritableDatabase();
        
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
//			values.put(DBShema.COLUMN_PERSON_ID, 2);
			values.put(DBShema.COLUMN_NAME, editName.getText().toString());
			values.put(DBShema.COLUMN_ADDRESS, editAddress.getText().toString());
//			values.put(DBShema.COLUMN_AGE, 56);
			
			// insert into persons
			db.insert(DBShema.TABLE_PERSONS, null, values);
			
			Toast.makeText(this, "Inserted the values :)", Toast.LENGTH_SHORT).show();
			
		} else if (v.getId() == R.id.buttonQuery) {
			// select personId, personName, personAddress, personAge from persons;
			
			array.clear();
			
			String columns[] = new String[] {
					DBShema.COLUMN_PERSON_ID,
					DBShema.COLUMN_NAME,
					DBShema.COLUMN_ADDRESS,
					DBShema.COLUMN_AGE
			};

			Cursor cursor = db.query(DBShema.TABLE_PERSONS, columns, null, null, null, null, null);
			
			if(!cursor.isAfterLast()) {
				cursor.moveToFirst();
			
				while(!cursor.isAfterLast()) {
					Person p = new Person();
					
					p.personId = cursor.getInt(cursor.getColumnIndex(DBShema.COLUMN_PERSON_ID));
					p.name = cursor.getString(cursor.getColumnIndex(DBShema.COLUMN_NAME));
					p.address = cursor.getString(cursor.getColumnIndex(DBShema.COLUMN_ADDRESS));
					p.personAge = cursor.getInt(cursor.getColumnIndex(DBShema.COLUMN_AGE));

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