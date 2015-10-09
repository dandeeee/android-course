package com.examples.TestMessengingService;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import android.os.*;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.widget.Toast;

public class DownloadService extends Service {
	
	Messenger receivingMessenger = new Messenger(new DownloadServiceHandler()); 
	class DownloadServiceHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what == Constants.MSG_START_DOWNLOADING_IMAGE) {
				Toast.makeText(getApplicationContext(), "downloading started", Toast.LENGTH_SHORT).show();
				Thread th = new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							URL url = new URL ("http://upload.wikimedia.org/wikipedia/commons/2/2a/Junonia_lemonias_DSF_upper_by_Kadavoor.JPG");
							URLConnection connection = url.openConnection();
							InputStream stream = connection.getInputStream();
							// Bitmap  = BitmapFactory.decodeStream(stream);
							
							Toast.makeText(getApplicationContext(), "Dowloadin finished :)", Toast.LENGTH_SHORT).show();
							try {
								Message msg = Message.obtain(null, Constants.MSG_DOWNLOADING_FINISHED);
								msg.obj = stream;
								sendingMessenger.send(msg);
							} catch (RemoteException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						} catch (MalformedURLException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}						
					}
				});
				th.start();
				
			} else if (msg.what == Constants.MSG_HANDSHAKE) {
				sendingMessenger = msg.replyTo;
			}
		}
	}
	
	Messenger sendingMessenger = null;
	public void startDownload() {
		
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		return receivingMessenger.getBinder();
	}
	
	public void onDestroy() {
		super.onDestroy();
	};
}
