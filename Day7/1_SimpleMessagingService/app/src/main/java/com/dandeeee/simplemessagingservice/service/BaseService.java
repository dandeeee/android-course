package com.dandeeee.simplemessagingservice.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public abstract class BaseService extends Service {

    public static final int MSG_REGISTER = 1;
    public static final int MSG_SAY_HELLO = 2;

    public abstract String getLogTag();

    public Messenger replyToMessenger;

    public Messenger incomingMessenger = new Messenger(new BaseIncomingMessagesHandler());
    public class BaseIncomingMessagesHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            replyToMessenger = msg.replyTo;

            switch (msg.what) {

                case MSG_REGISTER:
                    Log.e(getLogTag(), "Registered client");
                    break;

                case MSG_SAY_HELLO:
                    Log.e(getLogTag(),"Received hello!");

                    Message answer = Message.obtain(null, MSG_SAY_HELLO, 0, 0);
                    try {
                        msg.replyTo.send(answer);
                        Log.e(getLogTag(),"Answered hello!");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        Log.e(getLogTag(), "binding");
        return incomingMessenger.getBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


}
