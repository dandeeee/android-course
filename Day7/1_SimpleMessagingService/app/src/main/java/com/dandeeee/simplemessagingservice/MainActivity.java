package com.dandeeee.simplemessagingservice;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.dandeeee.simplemessagingservice.service.DownloadService;
import com.dandeeee.simplemessagingservice.service.ServiceConnector;
import com.dandeeee.simplemessagingservice.service.SimpleService;


public class MainActivity extends Activity implements OnClickListener {

    private static String LOGTAG = "Activity";
    ServiceConnector simpleServiceConnector;
    ServiceConnector downloadServiceConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        simpleServiceConnector = new ServiceConnector(this, SimpleService.class);
        downloadServiceConnector = new ServiceConnector(this, DownloadService.class);

        findViewById(R.id.btnSaySimple).setOnClickListener(this);
        findViewById(R.id.btnSayDownload).setOnClickListener(this);
        findViewById(R.id.btnDoDownload).setOnClickListener(this);
        findViewById(R.id.btnStart).setOnClickListener(this);
        findViewById(R.id.btnStop).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnStart){
            simpleServiceConnector.bindService();
            downloadServiceConnector.bindService();
        }else if(v.getId()==R.id.btnSaySimple){
            simpleServiceConnector.sayHello();
        }else if(v.getId()==R.id.btnSayDownload){
            downloadServiceConnector.sayHello();
        }else if(v.getId()==R.id.btnDoDownload){
            downloadServiceConnector.sayDoDownload();
        }else if(v.getId()==R.id.btnStop){
            simpleServiceConnector.unbindService();
            downloadServiceConnector.unbindService();
        }
    }


}