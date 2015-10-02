package com.example.boundservice;

import com.example.boundservice.SimpleBoundService.MyBinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class MainActivity extends Activity  implements OnClickListener {

    private Button buttonStart;
	private Button buttonStop;
	private View buttonCall;
	
	private static String LOG_TAG = "Activity";

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(this);
        
        buttonStop = (Button) findViewById(R.id.buttonStop);
        buttonStop.setOnClickListener(this);
        
        buttonCall = (Button) findViewById(R.id.buttonCallMethod);
        buttonCall.setOnClickListener(this);
        
    }

	SimpleBoundService myservice = null;
	ServiceConnection connection = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.d(LOG_TAG, "onServiceDisconnected");
			myservice = null;
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder serviceBinder) {
			Log.d(LOG_TAG, "onServiceConnected");
			
			MyBinder myBinder = (MyBinder) serviceBinder;
			myservice = myBinder.getInstance();
			
		}
	};
	
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.buttonStart) {
			
			Intent intent = new Intent(this, SimpleBoundService.class);
			bindService(intent, connection, BIND_AUTO_CREATE);
			
		} else if (v.getId() == R.id.buttonStop) {
			myservice = null;
			unbindService(connection);
		} else if (v.getId() == R.id.buttonCallMethod) {
			// just to make sure that the service
			// is connected (bounded) to the application
			if (myservice != null) {
				myservice.startOperation();
			}
		}
	}
}