package com.example.simplemessagingservice;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

public class MessengerService extends Service {
    /** Command to the service to register client binder */
    static final int MSG_REGISTER = 1;
    /** Command to the service to display a message */
    static final int MSG_SAY_HELLO = 2;
    
    public static String LOGTAG = "Service";

    /**
     * Target we publish for clients to send messages to IncomingHandler.Note
     * that calls to its binder are sequential!
     */
    final Messenger mServiceMessenger = new Messenger(new IncomingHandler());
    
    
    /**
     * Handler of incoming messages from clients.
     */
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MSG_REGISTER:
                /*
                 * Do whatever we want with the client messenger: Messenger
                 * clientMessenger = msg.replyTo
                 */
                Log.e(LOGTAG,"Registered client");
                break;
            case MSG_SAY_HELLO:
                /*
                 * Do whatever we want with the client messenger: Messenger
                 * clientMessenger = msg.replyTo
                 */
            		Log.e(LOGTAG,"Received hello!");
                
                // Create and send answer message to the client, using a supported 'what' value
                Message answer = Message.obtain(null, MSG_SAY_HELLO, 0, 0);
                try {
	                	msg.replyTo.send(answer);
	                	Log.e(LOGTAG,"Answered hello!");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                
                break;
            default:
                super.handleMessage(msg);
            }
        }
    }



    /**
     * When binding to the service, we return an interface to our messenger for
     * sending messages to the service.
     */
    @Override
    public IBinder onBind(Intent intent) {
    	Log.e(LOGTAG,"binding");
        return mServiceMessenger.getBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        /*
         * You might want to start this Service on Foreground so it doesnt get
         * killed
         */
    }
}