package com.example.broadcastreceiverservice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    ExecutorService es;
    private static String LOG_TAG = "Service";
    
    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "MyService onCreate");
        es = Executors.newFixedThreadPool(2);
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "MyService onDestroy");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "MyService onStartCommand");

        int time = intent.getIntExtra(Constants.PARAM_TIME, 1);
        int task = intent.getIntExtra(Constants.PARAM_TASK, 0);

        MyRun mr = new MyRun(startId, time, task);
        es.execute(mr);

        return super.onStartCommand(intent, flags, startId);
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }

    class MyRun implements Runnable {

        int time;
        int startId;
        int task;

        public MyRun(int startId, int time, int task) {
            this.time = time;
            this.startId = startId;
            this.task = task;
            Log.d(LOG_TAG, "MyRun#" + startId + " create");
        }

        public void run() {
            Intent intent = new Intent(Constants.BROADCAST_ACTION);

            Log.d(LOG_TAG, "MyRun#" + startId + " start, time = " + time);

            try {

                // сообщаем об старте задачи
                intent.putExtra(Constants.PARAM_TASK, task);
                intent.putExtra(Constants.PARAM_STATUS, Constants.STATUS_START);
                sendBroadcast(intent);

                // начинаем выполнение задачи
                TimeUnit.SECONDS.sleep(time);

                // сообщаем об окончании задачи
                intent.putExtra(Constants.PARAM_STATUS, Constants.STATUS_FINISH);
                intent.putExtra(Constants.PARAM_RESULT, time * 100);
                sendBroadcast(intent);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            stop();
        }

        void stop() {
            Log.d(LOG_TAG, "MyRun#" + startId + " end, Arr=" + MyReceiver.arr + ", stopSelfResult(" + startId + ") = " + stopSelfResult(startId));
        }

    }
}