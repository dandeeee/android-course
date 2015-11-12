package com.dandeeee.simplemessagingservice.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.widget.ImageView;

import com.dandeeee.simplemessagingservice.R;

import java.io.InputStream;

public class ServiceConnector {
    public static String LOGTAG = "Connector";

    boolean isServiceBound;
    private Context ctx;
    private Class serviceClass;

    private Messenger sendingMessenger = null;
    private Messenger receivingMessenger = null;


    private final HandlerThread handlerThread;
    private final IncomingHandler incomingHandler;

    class IncomingHandler extends Handler {

//        public IncomingHandler(HandlerThread thr) {
//            super(thr.getLooper());
//        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BaseService.MSG_SAY_HELLO:
                    Log.e(LOGTAG,"Received hello!");
                    break;

                case DownloadService.MSG_DO_DOWNLOAD:
                    Log.e(LOGTAG,"Received download result!");

                    InputStream stream = (InputStream) msg.obj;
                    Bitmap bm = BitmapFactory.decodeStream(stream);
                    // dirty hack
                    ImageView imageView = (ImageView) ((Activity )ctx).findViewById(R.id.imageView);
                    imageView.setImageBitmap(bm);

                    break;

                default:
                    super.handleMessage(msg);
            }
        }
    }


    public ServiceConnector(Context ctx, Class serviceClass) {
        this.ctx = ctx;
        this.serviceClass = serviceClass;

        handlerThread = new HandlerThread("IPChandlerThread");
        handlerThread.start();
        incomingHandler = new IncomingHandler();
        receivingMessenger = new Messenger(incomingHandler);
    }

    private ServiceConnection connection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder binder) {
            sendingMessenger = new Messenger(binder);
            Message msg = Message.obtain(null, BaseService.MSG_REGISTER, 0, 0);
            msg.replyTo = receivingMessenger;

            try {
                sendingMessenger.send(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }

            isServiceBound = true;
        }

        public void onServiceDisconnected(ComponentName className) {
            sendingMessenger = null;
            isServiceBound = false;
        }
    };


    public boolean bindService() {
        Log.e(LOGTAG, "Binding service " + this.serviceClass.getCanonicalName());
        Intent i = new Intent(ctx, this.serviceClass);
        Boolean flag = ctx.getApplicationContext().bindService(i, connection, Context.BIND_AUTO_CREATE);
        return flag;
    }

    public void unbindService() {
        if (isServiceBound) {
            ctx.getApplicationContext().unbindService(connection);
            isServiceBound = false;
            Log.e(LOGTAG, "Service " + this.serviceClass.getCanonicalName() + " is unbound");
        }
    }

    public void sayHello() {
        if (!isServiceBound)
            return;

        Message msg = Message.obtain(null, BaseService.MSG_SAY_HELLO, 0, 0);
        msg.replyTo = receivingMessenger;
        try {
            Log.e(LOGTAG,"Sent hello!");
            sendingMessenger.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sayDoDownload(){
        if (!isServiceBound)
            return;

        Message msg = Message.obtain(null, DownloadService.MSG_DO_DOWNLOAD, 0, 0);
        msg.replyTo = receivingMessenger;
        try {
            Log.e(LOGTAG,"Sent download command!");
            sendingMessenger.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}