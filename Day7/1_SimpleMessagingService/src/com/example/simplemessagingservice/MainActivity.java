package com.example.simplemessagingservice;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;


public class MainActivity extends Activity implements OnClickListener {

	private static String LOGTAG = "Activity";
	ServiceConnector serviceConnector;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        serviceConnector = new ServiceConnector(this);
        
        findViewById(R.id.btnSay).setOnClickListener(this);
        findViewById(R.id.btnStart).setOnClickListener(this);
        findViewById(R.id.btnStop).setOnClickListener(this);
        
    }

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btnStart){
			serviceConnector.bindService();
		}else if(v.getId()==R.id.btnSay){
			serviceConnector.sayHello();
		}else if(v.getId()==R.id.btnStop){
			serviceConnector.unbindService();
		}
	}



}
