package com.dandeeee.advanceddbapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

import com.dandeeee.advanceddbapp.R;
import com.dandeeee.advanceddbapp.db.DBHelper;
import com.dandeeee.advanceddbapp.model.Person;

import com.dandeeee.advanceddbapp.db.DBSchema;

public class DisplayActivity extends Activity implements OnClickListener {

	private Button buttonDelete;
	private Button buttonBack;
	private TextView textViewInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display_activity);
		
		initViews();
		
		Intent intent = getIntent();
		Person p = intent.getParcelableExtra("Person");
		textViewInfo.setText("Name : " + p.name);
	}

	private void initViews() {
		buttonDelete = (Button) findViewById(R.id.buttonDelete);
		buttonDelete.setOnClickListener(this);
		
		buttonBack = (Button) findViewById(R.id.buttonBack);
		buttonBack.setOnClickListener(this);
		
		textViewInfo = (TextView) findViewById(R.id.textInfo);
	}

	@Override
	public void onClick(View v) {
		if(v.getId()== R.id.buttonDelete){
			DBHelper helper = new DBHelper(this);
			SQLiteDatabase  db = helper.getWritableDatabase();
			
			Intent intent = getIntent();
			Person person = intent.getParcelableExtra("Person");
	
			String whereClause = String.format("%s = %d", DBSchema.PERSONID, person.personId);
	
			db.delete(DBSchema.PERSONS, whereClause, null);
			
			Toast.makeText(this, "Record is deleted :)", Toast.LENGTH_SHORT).show();
		} else if(v.getId()==R.id.buttonBack){
			finish();
		}
	}
}
