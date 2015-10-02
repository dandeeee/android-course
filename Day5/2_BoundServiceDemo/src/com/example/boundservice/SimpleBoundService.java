package com.example.boundservice;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;


public class SimpleBoundService extends Service {

	// class Binder implements IBinder 
	class MyBinder extends Binder {
		public SimpleBoundService getInstance() {
			return SimpleBoundService.this;
		}
	}
	
	public void startOperation() {
		try {
			Log.e("Service", "going to sleep for 2 sec :)");
			Thread.sleep(2000);
			Log.e("Service", "now I am alive :(");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.e("Service", "onCreate()");
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		Log.e("Service", "onBind()");
		MyBinder binder = new MyBinder();
		return binder;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.e("Service", "onDestroy()");
	}
	
	@Override
	public boolean onUnbind(Intent intent) {
		Log.e("Service", "onUnbind()");

		return super.onUnbind(intent);
	}
}
