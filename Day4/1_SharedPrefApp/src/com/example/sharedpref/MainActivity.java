package com.example.sharedpref;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {

	public static final String TIMES_STARTED = "com.example.sharedpref.TIMES_STARTED";
	public static final int TIMES_STARTED_DEFAULT = 0;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        Button btn = (Button)findViewById(R.id.finish);
        btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
        
        
        Context context = this;
        SharedPreferences sharedPref = context.getSharedPreferences(
        	getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        
        int timesStarted = sharedPref.getInt(MainActivity.TIMES_STARTED, TIMES_STARTED_DEFAULT);
        
        TextView v = (TextView)findViewById(R.id.tvTimesStartedVal);
        v.setText(Integer.toString(timesStarted) + " " + v.getText());
    
    }


    
    
	@Override
	protected void onDestroy() {
		super.onDestroy();
		 
		Context context = this;
		SharedPreferences sharedPref = context.getSharedPreferences(
				getString(R.string.preference_file_key), Context.MODE_PRIVATE);
		
		int timesStarted = sharedPref.getInt(MainActivity.TIMES_STARTED, TIMES_STARTED_DEFAULT);
		
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putInt(MainActivity.TIMES_STARTED, timesStarted + 1);
		editor.commit();
	}



	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
