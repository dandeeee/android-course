package com.dandeeee.sharedpreffs;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {

	public static final String TIMES_STARTED = "com.dandeeee.sharedpreffs.TIMES_STARTED";
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


}
