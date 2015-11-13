package com.dandeeee.broadcastreceiverservice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

    ExecutorService es;
    String LOG_TAG = "Service";
    
    public void onCreate() {
        super.onCreate();
        Log.e(LOG_TAG, "MyService onCreate");
        es = Executors.newFixedThreadPool(3);
    }

    public void onDestroy() {
        super.onDestroy();
        Log.e(LOG_TAG, "MyService onDestroy");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(LOG_TAG, "MyService onStartCommand");

        int time = intent.getIntExtra(Constants.PARAM_TIME, 1);
        int task = intent.getIntExtra(Constants.PARAM_TASK, 0);

        MyWorker mr = new MyWorker(startId, time, task);
        es.execute(mr);

        return super.onStartCommand(intent, flags, startId);
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }

    class MyWorker implements Runnable {

        int time;
        int startId;
        int task;

        public MyWorker(int startId, int time, int task) {
            this.time = time;
            this.startId = startId;
            this.task = task;
            Log.e(LOG_TAG, "MyWorker#" + startId + " create");
        }

        public void run() {

            Log.e(LOG_TAG, "MyWorker#" + startId + " start, time = " + time);

            try {
                Intent intent = new Intent(Constants.BROADCAST_ACTION);

                // сообщаем об старте задачи
                intent.putExtra(Constants.PARAM_TASK, task);
                intent.putExtra(Constants.PARAM_STATUS, Constants.STATUS_START);
                sendBroadcast(intent);

                // начинаем выполнение задачи
                TimeUnit.SECONDS.sleep(time);

                // сообщаем об окончании задачи
                intent.putExtra(Constants.PARAM_STATUS, Constants.STATUS_FINISH);
                intent.putExtra(Constants.PARAM_RESULT, time * 100);

                // посылаем интент на ресивер
                sendBroadcast(intent);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            stop();
        }

        void stop() {
            Log.e(LOG_TAG, "MyWorker#" + startId +
                    " end, Arr=" + MyReceiver.arr +
                    ", stopSelfResult(" + startId + ") = "+
                    stopSelfResult(startId));
        }

    }
}