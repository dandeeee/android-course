package com.dandeeee.undoundservicedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends Activity {

    final String LOG_TAG = "myLogs";
    Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnStartBlockingClickHandler(View v) {
        SimpleUnboundService.doJob(5);
    }


    public void btnStartClickHandler(View v) {
        serviceIntent = new Intent(this, SimpleUnboundService.class);
        startService(serviceIntent);
    }

    public void btnStopClickHandler(View v) {
        stopService(serviceIntent);
    }

}
