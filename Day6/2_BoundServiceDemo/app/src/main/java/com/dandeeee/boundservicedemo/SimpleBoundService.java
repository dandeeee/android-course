package com.dandeeee.boundservicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;


public class SimpleBoundService extends Service {

	static final String LOG_TAG = "ServiceLOG";

	class MyBinder extends Binder {

		public SimpleBoundService getInstance() {
			return SimpleBoundService.this;
		}

	}
	
	public void startOperation() {
		try {
			Log.e(LOG_TAG, "going to sleep for 2 sec");
			Thread.sleep(2000);
			Log.e(LOG_TAG, "now I am alive");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.e(LOG_TAG, "onCreate()");
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		Log.e(LOG_TAG, "onBind()");
		MyBinder binder = new MyBinder();
		return binder;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.e(LOG_TAG, "onDestroy()");
	}
	
	@Override
	public boolean onUnbind(Intent intent) {
		Log.e(LOG_TAG, "onUnbind()");

		return super.onUnbind(intent);
	}
}
