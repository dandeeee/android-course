package com.dandeeee.simplemessagingservice.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class ServiceConnector {
    public static String LOGTAG = "Connector";

    /** Command to the service to register client binder */
    static final int MSG_REGISTER = 1;
    /** Command to the service to display a message */
    static final int MSG_SAY_HELLO = 2;

    /** Messenger for sending messages to the service. */
    Messenger sendingMessenger = null;
    /** Messenger for receiving messages from the service. */
    Messenger receivingMessenger = null;

    /**
     * Target we publish for clients to send messages to IncomingHandler. Note
     * that calls to its binder are sequential!
     */
    private final IncomingHandler handler;

    /**
     * Handler thread to avoid running on the main thread (UI)
     */
    private final HandlerThread handlerThread;

    /** Flag indicating whether we have called bind on the service. */
    boolean mBound;

    /** Context of the activity from which this connector was launched */
    private Context mCtx;

    public ServiceConnector(Context ctx) {
        mCtx = ctx;
        handlerThread = new HandlerThread("IPChandlerThread");
        handlerThread.start();
        handler = new IncomingHandler(handlerThread);
        receivingMessenger = new Messenger(handler);
    }


    /**
     * Handler of incoming messages from service.
     */
    class IncomingHandler extends Handler {

        public IncomingHandler(HandlerThread thr) {
            super(thr.getLooper());
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SAY_HELLO:
                    Log.e(LOGTAG,"Received hello!");
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    /**
     * Class for interacting with the main interface of the service.
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the object we can use to
            // interact with the service. We are communicating with the
            // service using a Messenger, so here we get a client-side
            // representation of that from the raw IBinder object.
            sendingMessenger = new Messenger(service);

            // Now that we have the service messenger, lets send our messenger
            Message msg = Message.obtain(null, MSG_REGISTER, 0, 0);
            msg.replyTo = receivingMessenger;

            /*
             * In case we would want to send extra data, we could use Bundles:
             * Bundle b = new Bundle(); b.putString("key", "hello world");
             * msg.setData(b);
             */

            try {
                sendingMessenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            mBound = true;
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            sendingMessenger = null;
            mBound = false;
        }
    };



    /**
     * Method used for binding with the service
     */
    public boolean bindService() {
        /*
         * Note that this is an implicit Intent that must be defined in the
         * Android Manifest.
         */
        Log.e(LOGTAG, "Binding service");

        Intent i = new Intent(mCtx, MessengerService.class);

        Boolean flag = mCtx.getApplicationContext().bindService(i, mConnection, Context.BIND_AUTO_CREATE);

        return flag;
    }

    public void unbindService() {
        if (mBound) {
            mCtx.getApplicationContext().unbindService(mConnection);
            mBound = false;
            Log.e(LOGTAG, "Service unbound");
        }
    }

    public void sayHello() {
        if (!mBound)
            return;

        // Create and send a message to the service, using a supported 'what' value
        Message msg = Message.obtain(null, MSG_SAY_HELLO, 0, 0);
        msg.replyTo = receivingMessenger;
        try {
            Log.e(LOGTAG,"Sent hello!");
            sendingMessenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}