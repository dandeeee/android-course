package com.examples.TestMessengingService;

import java.io.InputStream;

import com.examples.TestMessengingService.MyService.MyBinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class MainActivity extends Activity implements OnClickListener {
    private Button buttonStart;
	private Button buttonStop;
	private Button buttonCall;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        initViews();
    }

	private void initViews() {
		buttonStart = (Button) findViewById(R.id.buttonStart);
		buttonStart.setOnClickListener(this);
		
		buttonStop = (Button) findViewById(R.id.buttonStop);
		buttonStop.setOnClickListener(this);
		
		buttonCall = (Button) findViewById(R.id.buttonDownloadImage);
		buttonCall.setOnClickListener(this);
	}

	// used to send messages (to service)
	Messenger activitySendingMessenger = null;
	ServiceConnection connection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName arg0) {
		}
		
		@Override
		public void onServiceConnected(ComponentName arg0, IBinder binder) {
//			MyBinder myBinder = (MyBinder) binder;
//			MyService service = myBinder.getInstance();
//			service.startOperation();
			
			activitySendingMessenger = new Messenger(binder);
			
			try {
				Message msg = Message.obtain(null, Constants.MSG_HANDSHAKE);
				msg.replyTo = downloadReceiveMessenger;
				activitySendingMessenger.send(msg);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	};
	
	// this will receive the messages
	Messenger clientHandlerMessenger = new Messenger(new ClientHandler());
	class ClientHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			if (msg.what == Constants.MSG_OPERATION_COMPLETE) {
				Toast.makeText(MainActivity.this, "Operation is complete :)", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.buttonStart) {
			// start
			Toast.makeText(MainActivity.this, "starting service", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(this, DownloadService.class);
			bindService(intent, connection, BIND_AUTO_CREATE);
			
		} else if (v.getId() == R.id.buttonStop) {
			// stop
			unbindService(connection);
			
		} else if(v.getId() == R.id.buttonDownloadImage) {
			try {
				
				Toast.makeText(MainActivity.this, "sending msg to start downloading", Toast.LENGTH_SHORT).show();
				Message message = Message.obtain(null, Constants.MSG_START_DOWNLOADING_IMAGE);
				activitySendingMessenger.send(message);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
//		else  if (v.getId() == R.id.buttonCallMethod) {
//			// start
//			try {
//				Message message = Message.obtain(null, Constants.MSG_CALL_METHOD);
//				activityMessenger.send(message);
//			} catch (RemoteException e) {
//				e.printStackTrace();
//			}
//		}
	}
	
	Messenger downloadReceiveMessenger = new Messenger(new DownloadClientHandler());
	class DownloadClientHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			if (msg.what == Constants.MSG_DOWNLOADING_FINISHED) {
				InputStream stream = (InputStream) msg.obj;
				Bitmap bm = BitmapFactory.decodeStream(stream);
				ImageView imageView = (ImageView) findViewById(R.id.imageView);
				imageView.setImageBitmap(bm);
			}
		}
	}
}