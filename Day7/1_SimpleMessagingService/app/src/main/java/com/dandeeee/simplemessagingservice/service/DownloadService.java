package com.dandeeee.simplemessagingservice.service;


import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadService extends BaseService {

	public static final int MSG_DO_DOWNLOAD = 3;

	@Override
	public String getLogTag() {
		return "DownloadService";
	}

	public  DownloadService(){
		this.incomingMessenger = new Messenger(new DownloadIncomingMessagesHandler());
	}

	public class DownloadIncomingMessagesHandler extends BaseIncomingMessagesHandler {

		@Override
		public void handleMessage(Message msg) {
			replyToMessenger = msg.replyTo;

			switch (msg.what) {

				case MSG_DO_DOWNLOAD:
					Log.e(getLogTag(),"Received do download!");
					performDownloading();
					break;

				default:
					super.handleMessage(msg);
			}
		}
	}

	private void performDownloading() {

		Thread th = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Log.e(getLogTag(), "Downloading started");
					URL url = new URL("http://upload.wikimedia.org/wikipedia/commons/2/2a/Junonia_lemonias_DSF_upper_by_Kadavoor.JPG");
					URLConnection connection = url.openConnection();
					InputStream stream = connection.getInputStream();

					Message answer = Message.obtain(null, MSG_DO_DOWNLOAD);
					answer.obj = stream;
					replyToMessenger.send(answer);

					Log.e(getLogTag(), "Dowloadin finished :)");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		th.start();

	}

}
