package com.examples.TestMessengingService;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

public class MyService extends Service {

	class MyBinder extends Binder {
		public MyService getInstance() {
			return MyService.this;
		}
	}
	
	Messenger receivingMessenger = new Messenger(new ServiceHandler()); 
	
	// service can now receive the messages
	class ServiceHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			if (msg.what == Constants.MSG_HELLO) {
				Log.e("Service", "Hello message received");
			}
		}
	}
	
	
	@Override
	public IBinder onBind(Intent arg0) {
//		return new MyBinder();
		return receivingMessenger.getBinder();
	}
	
	public void startOperation () {
		
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
