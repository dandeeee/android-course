package com.dandeeee.advanceddbapp.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TimePicker;
import android.widget.RatingBar.OnRatingBarChangeListener;

import com.dandeeee.advanceddbapp.R;
import com.dandeeee.advanceddbapp.db.DBHelper;
import com.dandeeee.advanceddbapp.db.DBSchema;
import com.dandeeee.advanceddbapp.model.Person;

public class InputActivity extends Activity implements OnClickListener {

	private EditText editName;
	private EditText editAddress;
	private EditText editPhone;
	private EditText editEmail;
	private View buttonSave;
	private View buttonCancel;
	private Button buttonDate;
	private View buttonTime;
	private String birthTime;
	private String birthDate;
	private float personRating;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.input_activity);

		initViews();
	}

	private void initViews() {
		// Edit Text
		editName = (EditText) findViewById(R.id.editName);
		editAddress = (EditText) findViewById(R.id.editAddress);
		editPhone = (EditText) findViewById(R.id.editPhone);
		editEmail = (EditText) findViewById(R.id.editEmail);

		// Rating bar for person
		RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
		ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			public void onRatingChanged(RatingBar arg0, float rating, boolean arg2) {
				personRating = rating;
			}
		});

		// Buttons
		buttonSave = (Button) findViewById(R.id.buttonSave);
		buttonSave.setOnClickListener(this);

		buttonCancel = (Button) findViewById(R.id.buttonCancel);
		buttonCancel.setOnClickListener(this);

		buttonDate = (Button) findViewById(R.id.buttonBirthDate);
		buttonDate.setOnClickListener(this);

		buttonTime = (Button) findViewById(R.id.buttonTime);
		buttonTime.setOnClickListener(this);	}

	@Override
	public void onClick(View v) {
		if (v == buttonSave) {
			Person person = new Person();
			
			person.name = editName.getText().toString();
			person.address = editAddress.getText().toString();
			person.phone = editPhone.getText().toString();
			person.email = editEmail.getText().toString();
			person.date = birthDate;
			person.time = birthTime;
			person.rating = this.personRating;
			
			DBHelper helper = new DBHelper(this);
			SQLiteDatabase db = helper.getWritableDatabase();
			
			ContentValues values = new ContentValues();
			values.put(DBSchema.NAME, person.name);
			values.put(DBSchema.ADDRESS, person.address);
			values.put(DBSchema.PHONE, person.phone);
			values.put(DBSchema.EMAIL, person.email);
			values.put(DBSchema.DATE, person.date);
			values.put(DBSchema.TIME, person.time);
			values.put(DBSchema.RATING, person.rating);
			
			db.insert(DBSchema.PERSONS, null, values);
			
			Intent intent = new Intent();
			intent.putExtra("Person", person);
			setResult(0, intent);
			finish();

		} else if (v == buttonCancel) {
			finish();
		} else if (v == buttonDate) {
			// 1 : date
			showDialog(1);
		} else if (v == buttonTime) {
			showDialog(2);
		}		
	}
	
	TimePickerDialog.OnTimeSetListener timePickerCallBack = new OnTimeSetListener() {
		public void onTimeSet(TimePicker arg0, int hr, int min) {
			// always in 24 hours format
			Log.e("Time", "" + hr + ":" + min);
			birthTime = "" + hr + ":" + min;
		}
	};
	
	
	DatePickerDialog.OnDateSetListener callBack = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker arg0, int year, int month, int day) {
			Log.e("BirthDate", "" + year + "/" + month + "/" + day);
			birthDate = "" + year + "/" + month + "/" + day;
		}
	};
	
	@Override
	protected Dialog onCreateDialog(int id) {

		if (id == 1) {
			// Date

			// callBack methods:
			//	 which are written by a class but never called by same class
			//   these methods are written to be called from some other location
			// start with 0
			DatePickerDialog datePicker =
						new DatePickerDialog(this, callBack, 1985, 1, 1);
			datePicker.show();
		} else if (id == 2) {
			// time
			TimePickerDialog timePicker =
					new TimePickerDialog(this, timePickerCallBack, 1, 1, false);
			timePicker.show();
		}

		return super.onCreateDialog(id);
	}
}
