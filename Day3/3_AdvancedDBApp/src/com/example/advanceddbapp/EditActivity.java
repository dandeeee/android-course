package com.example.advanceddbapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends Activity implements OnClickListener {
	private EditText editName;
	private EditText editAddress;
	private EditText editPhone;
	private EditText editEmail;
	private View buttonSave;
	private View buttonCancel;
	private Person person;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_activity);

		Intent intent = getIntent();
		person = intent.getParcelableExtra("Person");

		editName = (EditText) findViewById(R.id.editName);
		editAddress = (EditText) findViewById(R.id.editAddress);
		editPhone = (EditText) findViewById(R.id.editPhone);
		editEmail = (EditText) findViewById(R.id.editEmail);

		editName.setText(person.name);
		editAddress.setText(person.address);
		editPhone.setText(person.phone);
		editEmail.setText(person.email);
		
		// Buttons
		buttonSave = (Button) findViewById(R.id.buttonSave);
		buttonSave.setOnClickListener(this);

		buttonCancel = (Button) findViewById(R.id.buttonCancel);
		buttonCancel.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.buttonSave) {
			// update
			// update persons set name=?, address = ? where personId = 1;
			
			DBHelper helper = new DBHelper(this);
			SQLiteDatabase db = helper.getWritableDatabase();
			
			ContentValues values = new ContentValues();
			values.put(Constants.NAME, editName.getText().toString());
			values.put(Constants.ADDRESS, editAddress.getText().toString());
			values.put(Constants.EMAIL, editEmail.getText().toString());
			values.put(Constants.PHONE, editPhone.getText().toString());
			
			String whereClause = String.format("%s=%d", Constants.PERSONID, person.personId);
			db.update(Constants.PERSONS, values, whereClause, null);
			Toast.makeText(this, "The record is updated :)", Toast.LENGTH_SHORT).show();
			finish();
		} else if (v.getId() == R.id.buttonCancel) {
			finish();
		}
	}
}
