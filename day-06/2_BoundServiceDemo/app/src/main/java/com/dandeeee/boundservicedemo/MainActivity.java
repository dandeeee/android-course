package com.dandeeee.boundservicedemo;

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

import com.dandeeee.boundservicedemo.SimpleBoundService.MyBinder;

public class MainActivity extends Activity  implements OnClickListener {

    private static String LOG_TAG = "ActivityLOG";

    Button buttonStart;
    Button buttonStop;
    View buttonCall;

    SimpleBoundService myservice = null;
    ServiceConnection serviceConnection;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(this);

        buttonStop = (Button) findViewById(R.id.buttonStop);
        buttonStop.setOnClickListener(this);

        buttonCall = (Button) findViewById(R.id.buttonCallMethod);
        buttonCall.setOnClickListener(this);

        serviceConnection = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName name, IBinder serviceBinder) {
                Log.d(LOG_TAG, "onServiceConnected");

                SimpleBoundService.MyBinder myBinder = (SimpleBoundService.MyBinder) serviceBinder;
                myservice = myBinder.getInstance();

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d(LOG_TAG, "onServiceDisconnected");
                myservice = null;
            }

        };
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonStart) {
            Intent intent = new Intent(this, SimpleBoundService.class);
            bindService(intent, this.serviceConnection, BIND_AUTO_CREATE);
        } else if (v.getId() == R.id.buttonStop) {
            this.myservice = null;
            unbindService(this.serviceConnection);
        } else if (v.getId() == R.id.buttonCallMethod) {
            if (this.myservice != null) {
                this.myservice.startOperation();
            }
        }
    }
}