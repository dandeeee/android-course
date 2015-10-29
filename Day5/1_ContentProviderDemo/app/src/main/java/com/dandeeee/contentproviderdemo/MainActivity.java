package com.dandeeee.contentproviderdemo;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

import com.dandeeee.contentproviderdemo.contentprovider.ContentProviderCoordinates;
import com.dandeeee.contentproviderdemo.db.DBSchema;
import com.dandeeee.contentproviderdemo.model.Car;

public class MainActivity extends Activity implements OnClickListener {
	
    private Button buttonInsert;
	private Button buttonQuery;
	private View buttonQueryPerson;
	private View buttonInsertPerson;

	ContentResolver resolver;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        buttonInsert = (Button) findViewById(R.id.buttonInsert);
        buttonInsert.setOnClickListener(this);
        
        buttonQuery = (Button) findViewById(R.id.buttonQuery);
        buttonQuery.setOnClickListener(this);

        buttonInsertPerson = (Button) findViewById(R.id.buttonInsertPerson);
        buttonInsertPerson.setOnClickListener(this);
        
        buttonQueryPerson = (Button) findViewById(R.id.buttonQueryPerson);
        buttonQueryPerson.setOnClickListener(this);

		this.resolver = getContentResolver();
        
    }

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.buttonInsert) {
			insertNewRecordForCar();
		} else if (v.getId() == R.id.buttonQuery) {
			queryDatabaseForCars();
		} else if (v.getId() == R.id.buttonQueryPerson) {
			queryDatabaseForPerson();
		} else if (v.getId() == R.id.buttonInsertPerson) {
			insertNewRecordForPerson();
		}
	}
	
	private void insertNewRecordForCar() {
		Car car = Car.getDummyInstance();

		ContentValues values = new ContentValues();
		values.put(DBSchema.CAR_NAME, car.name);
		values.put(DBSchema.CAR_COMPANY, car.company);
		values.put(DBSchema.CAR_COLOR, car.color);
		values.put(DBSchema.CAR_PRICE, car.price);

		resolver.insert(ContentProviderCoordinates.CONTENT_URI_CAR, values);
	}
	
	private void queryDatabaseForCars() {
		
		String projection[] = new String[] {
				DBSchema.CAR_CAR_ID,
				DBSchema.CAR_COLOR,
				DBSchema.CAR_COMPANY,
				DBSchema.CAR_NAME,
				DBSchema.CAR_PRICE};

		Cursor cursor = resolver.query(ContentProviderCoordinates.CONTENT_URI_CAR, projection, null, null, null);
		
		StringBuilder sb = new StringBuilder();
		if (!cursor.isAfterLast()) {
			cursor.moveToFirst();
			while(!cursor.isAfterLast()) {
				
				Car car = new Car();
				car.name = cursor.getString(cursor.getColumnIndex(DBSchema.CAR_NAME));
				car.company = cursor.getString(cursor.getColumnIndex(DBSchema.CAR_COMPANY));
				car.color = cursor.getString(cursor.getColumnIndex(DBSchema.CAR_COLOR));
				car.price = cursor.getFloat(cursor.getColumnIndex(DBSchema.CAR_PRICE));
				
				sb.append(car.toString()).append("\n");
				
				cursor.moveToNext();
			}
		}
		
		cursor.close();
		Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();
	}

	private void queryDatabaseForPerson() {
		ContentResolver resolver = getContentResolver();
		
		String projection[] = new String[] {
				DBSchema.PERSON_PID,
				DBSchema.PERSON_PNAME,
				DBSchema.PERSON_ADDRESS
		};

		Cursor cursor = resolver.query(ContentProviderCoordinates.CONTENT_URI_PERSON, projection, null, null, null);
		
		StringBuilder sb = new StringBuilder();
		if (!cursor.isAfterLast()) {
			cursor.moveToFirst();
			while(!cursor.isAfterLast()) {
				
				int pId = cursor.getInt(cursor.getColumnIndex(DBSchema.PERSON_PID));
				String pname = cursor.getString(cursor.getColumnIndex(DBSchema.PERSON_PNAME));
				String paddress = cursor.getString(cursor.getColumnIndex(DBSchema.PERSON_ADDRESS));

				sb.append("Person" + String.format("Id:%d Name:%s Address:%s", pId, pname, paddress)).append("\n");
				cursor.moveToNext();
			}
		}
		
		cursor.close();
		Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();
	}

	private void insertNewRecordForPerson() {
		ContentValues values = new ContentValues();
		values.put(DBSchema.PERSON_PNAME, "person1");
		values.put(DBSchema.PERSON_ADDRESS, "Minsk");

		resolver.insert(ContentProviderCoordinates.CONTENT_URI_PERSON, values);
	}


    
}