package com.examples.ContentProviderClient;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import static com.examples.ContentProviderClient.Contstants.*;

public class MainActivity extends Activity implements OnClickListener {
	private Button buttonInsert;
	private Button buttonQuery;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        buttonInsert = (Button) findViewById(R.id.buttonInsert);
        buttonInsert.setOnClickListener(this);
        
        buttonQuery = (Button) findViewById(R.id.buttonQuery);
        buttonQuery.setOnClickListener(this);
        
    }

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.buttonInsert) {
	        insertNewRecord();
		} else if (v.getId() == R.id.buttonQuery) {
			queryDatabase();
		}
	}
	
	private void insertNewRecord() {
		Car car = new Car();
		car.name = "A5";
        car.company = "Audi";
        car.color = "Red";
        car.price = 10000000f;
        
        ContentResolver resolver = getContentResolver();
        
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, car.name);
        values.put(COLUMN_COMPANY, car.company);
        values.put(COLUMN_COLOR, car.color);
        values.put(COLUMN_PRICE, car.price);
        
        // CarConentProvider
        // CP.insert(..);
        resolver.insert(CONTENT_URI, values);
	}

	private void queryDatabase() {
		ContentResolver resolver = getContentResolver();
		
		String projection[] = new String[] {COLUMN_CAR_ID, COLUMN_COLOR, COLUMN_COMPANY, COLUMN_NAME, COLUMN_PRICE};
		Cursor cursor = resolver.query(CONTENT_URI, projection, null, null, null);
		StringBuilder sb = new StringBuilder();
		if (!cursor.isAfterLast()) {
			cursor.moveToFirst();
			while(!cursor.isAfterLast()) {
				
				Car car = new Car();
				car.name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
				car.company = cursor.getString(cursor.getColumnIndex(COLUMN_COMPANY));
				car.color = cursor.getString(cursor.getColumnIndex(COLUMN_COLOR));
				car.price = cursor.getFloat(cursor.getColumnIndex(COLUMN_PRICE));
				
				sb.append("CarDetails"+car.toString()).append("\n");
				
				cursor.moveToNext();
			}
		}
		
		cursor.close();
		Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();
	}
}