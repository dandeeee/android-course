package com.examples.TestMessengingService;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

public class ServiceWithMessenging extends Service {

	public void starOperation() {
		Log.e("Service", "Is now going to sleep for 5 sec");
		try {
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.e("Service", "Is now available");
		
		try {
			Message message = Message.obtain(null, Constants.MSG_OPERATION_COMPLETE);
			sendingMessenger.send(message);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	// is used to ONLY receive the messages
	Messenger serviceMessenger = new Messenger(new ServiceMessageHandler());
	
	// is used to ONLY send messages
	Messenger sendingMessenger = null;
	
	class ServiceMessageHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == Constants.MSG_LOL)  {
				Log.e("Service", "Received LOL :)");
			} else if(msg.what == Constants.MSG_CALL_METHOD) {
				starOperation();
			} else if(msg.what == Constants.MSG_HANDSHAKE) {
				Toast.makeText(getApplicationContext(), "Handshake message", Toast.LENGTH_SHORT).show();
				// hand  shake
				sendingMessenger = msg.replyTo;
			}
		}
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		return serviceMessenger.getBinder();
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		Toast.makeText(getApplicationContext(), "Oncreate()", Toast.LENGTH_SHORT).show();
	}

	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
