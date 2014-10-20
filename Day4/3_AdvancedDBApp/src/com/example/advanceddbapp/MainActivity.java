package com.example.advanceddbapp;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.*;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Bundle;
import static com.example.advanceddbapp.Constants.*;

public class MainActivity extends Activity implements OnItemClickListener {
	
    private ListView listView;
    private ArrayList<Person> array;
	private ArrayAdapter<Person> adapter;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        initViews();
        
        fetchDataFromDB();
    }

	private void fetchDataFromDB() {
		
		array.clear();
		
		DBHelper helper = new DBHelper(this);
		SQLiteDatabase db = helper.getWritableDatabase();
		
		String columns [] = new String[] {
			PERSONID,
			NAME, 
			ADDRESS, 
			PHONE, 
			EMAIL,
			DATE, 
			TIME,
			RATING
		}; 
		
		Cursor cursor = db.query(PERSONS, columns, null, null, null, null, null);
		
		if (!cursor.isAfterLast()) {
			cursor.moveToFirst();
			
			while(!cursor.isAfterLast()) {
				
				Person p = new Person();
				p.personId = cursor.getInt(cursor.getColumnIndex(PERSONID));
				p.name = cursor.getString(cursor.getColumnIndex(NAME));
				p.address = cursor.getString(cursor.getColumnIndex(ADDRESS));
				p.phone = cursor.getString(cursor.getColumnIndex(PHONE));
				p.email = cursor.getString(cursor.getColumnIndex(EMAIL));
				p.date = cursor.getString(cursor.getColumnIndex(DATE));
				p.time = cursor.getString(cursor.getColumnIndex(TIME));
				p.rating = cursor.getFloat(cursor.getColumnIndex(RATING));
				
				array.add(p);
				
				cursor.moveToNext();
			}
		}
		
		cursor.close();
	}

	private void initViews() {
		array = new ArrayList<Person>();
		listView = (ListView) findViewById(R.id.listView);
		adapter = new ArrayAdapter<Person>(this, android.R.layout.simple_list_item_1, array);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		registerForContextMenu(listView);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		AdapterContextMenuInfo adapterContextInfo = (AdapterContextMenuInfo) menuInfo;
		int itemId = adapterContextInfo.position;
		
		menu.setHeaderTitle("Options");
		// adding item in the menu
		menu.add(Menu.NONE, itemId, 1, "Edit");
		menu.add(Menu.NONE, itemId, 2, "Delete");
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		int position = item.getItemId();
		final Person p = array.get(position);
		
		if (item.getTitle().equals("Delete")) {
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Question");
			builder.setMessage("Are you sure you want to deletet this record?");
			
			// button positions
			builder.setPositiveButton("Yes", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// delete from persons where personId = p.personId;
					DBHelper helper = new DBHelper(MainActivity.this);
					SQLiteDatabase db = helper.getWritableDatabase();
					
					String whereClause = String.format("%s=%d", Constants.PERSONID, p.personId);
					db.delete(Constants.PERSONS, whereClause, null);
					
					fetchDataFromDB();
					adapter.notifyDataSetChanged();
				}
			});
			
//			builder.setNeutralButton("Cancel", new OnClickListener() {
//				
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					Toast.makeText(MainActivity.this, "canceling dialog", Toast.LENGTH_SHORT).show();
//					dialog.cancel();
//				}
//			});
			
			builder.setNegativeButton("No", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Toast.makeText(MainActivity.this, "Delete is canceled", Toast.LENGTH_SHORT).show();
				}
			});
			
			builder.create().show();
			
		} else if (item.getTitle().equals("Edit")) {
			Intent intent = new Intent(this, EditActivity.class);
			intent.putExtra("Person", p);
			startActivity(intent);
		}
		
		return super.onContextItemSelected(item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// to create the menus
		// when menu button is pressed
		MenuInflater inflater = this.getMenuInflater(); 
		inflater.inflate(R.menu.mymenu, menu);
//		menu.add("Add New Employee");
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// will be invoked when a menu is selected
		
//		if (item.getTitle().equals("Add New Employee")) {
//			Log.e("Options", "Add new employee is selected");
//		} else if (item.getTitle().equals("test")) {
//			Log.e("Options", "Test is selected");
//		}
		
		if (item.getItemId() == R.id.menuAdd) {
			Intent intent = new Intent(this, InputActivity.class);
			startActivityForResult(intent, 0);
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (data != null) {
			Person p = data.getParcelableExtra("Person");
			array.add(p);
			adapter.notifyDataSetChanged();
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();

		fetchDataFromDB();
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		Person p = array.get(position);
		Intent intent = new Intent(this, DisplayActivity.class);
		intent.putExtra("Person", p);
		startActivity(intent);
	}
}