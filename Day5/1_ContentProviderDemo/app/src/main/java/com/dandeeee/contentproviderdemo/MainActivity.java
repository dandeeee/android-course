package com.dandeeee.contentproviderdemo;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import static com.dandeeee.contentproviderdemo.Contstants.*;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class MainActivity extends Activity implements OnClickListener {
	
    private Button buttonInsert;
	private Button buttonQuery;
	private View buttonQueryPerson;
	private View buttonInsertPerson;

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
		Car car = new Car();
        car.name = "Kalina";
        car.company = "Lada";
        car.color = "Black";
        car.price = 100000f;
        
        ContentResolver resolver = getContentResolver(); // !!!
        
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, car.name);
        values.put(COLUMN_COMPANY, car.company);
        values.put(COLUMN_COLOR, car.color);
        values.put(COLUMN_PRICE, car.price);
        
        // CarConentProvider
        // CP.insert(..);
        resolver.insert(CONTENT_URI_CAR, values); // !!!
	}
	
	private void queryDatabaseForCars() {
		ContentResolver resolver = getContentResolver();
		
		String projection[] = new String[] {COLUMN_CAR_ID, COLUMN_COLOR, COLUMN_COMPANY, COLUMN_NAME, COLUMN_PRICE};
		Cursor cursor = resolver.query(CONTENT_URI_CAR, projection, null, null, null);
		
		StringBuilder sb = new StringBuilder();
		if (!cursor.isAfterLast()) {
			cursor.moveToFirst();
			while(!cursor.isAfterLast()) {
				
				Car car = new Car();
				car.name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
				car.company = cursor.getString(cursor.getColumnIndex(COLUMN_COMPANY));
				car.color = cursor.getString(cursor.getColumnIndex(COLUMN_COLOR));
				car.price = cursor.getFloat(cursor.getColumnIndex(COLUMN_PRICE));
				
				sb.append(car.toString()).append("\n");
				
				cursor.moveToNext();
			}
		}
		
		cursor.close();
		Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();
	}

	private void queryDatabaseForPerson() {
		ContentResolver resolver = getContentResolver();
		
		String projection[] = new String[] {COLUMN_PID, COLUMN_PNAME, COLUMN_ADDRESS};
		Cursor cursor = resolver.query(CONTENT_URI_PERSON, projection, null, null, null);
		
		StringBuilder sb = new StringBuilder();
		if (!cursor.isAfterLast()) {
			cursor.moveToFirst();
			while(!cursor.isAfterLast()) {
				
				int pId = cursor.getInt(cursor.getColumnIndex(COLUMN_PID));
				String pname = cursor.getString(cursor.getColumnIndex(COLUMN_PNAME));
				String paddress = cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS));

				sb.append("Person" + String.format("Id:%d Name:%s Address:%s", pId, pname, paddress)).append("\n");
				cursor.moveToNext();
			}
		}
		
		cursor.close();
		Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();
	}

	private void insertNewRecordForPerson() {
		ContentValues values = new ContentValues();
		values.put(COLUMN_PNAME, "person1");
		values.put(COLUMN_ADDRESS, "Minsk");
		ContentResolver resolver = getContentResolver();
		// content://<authority>
		// content://<authority>/Person
		resolver.insert(CONTENT_URI_PERSON, values);
	}


    
}