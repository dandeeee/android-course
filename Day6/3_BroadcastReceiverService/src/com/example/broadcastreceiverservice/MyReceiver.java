package com.example.broadcastreceiverservice;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;

public class MyReceiver extends BroadcastReceiver {

    public static ArrayList<Integer> arr = new ArrayList<Integer>();
    private static String LOG_TAG = "Receiver";

    // действия при получении сообщений
    public void onReceive(Context context, Intent intent) {

        int task = intent.getIntExtra(Constants.PARAM_TASK, 0);
        int status = intent.getIntExtra(Constants.PARAM_STATUS, 0);

        Log.d(LOG_TAG, "onReceive: task = " + task + ", status = " + status);

        // Ловим сообщения о старте задач
        if (status == Constants.STATUS_START) {
            switch (task) {
                case Constants.TASK1_CODE:
                    StartActivity.tvTask1.setText("Task1 start");
                    arr.add(1);
                    break;
                case Constants.TASK2_CODE:
                    StartActivity.tvTask2.setText("Task2 start");
                    arr.add(2);
                    break;
                case Constants.TASK3_CODE:
                    StartActivity.tvTask3.setText("Task3 start");
                    arr.add(3);
                    break;
            }
        }

        // Ловим сообщения об окончании задач
        if (status == Constants.STATUS_FINISH) {
            int result = intent.getIntExtra(Constants.PARAM_RESULT, 0);
            switch (task) {
                case Constants.TASK1_CODE:
                    StartActivity.tvTask1.setText("Task1 finish, result = " + result);
                    arr.add(-1);
                    break;
                case Constants.TASK2_CODE:
                    StartActivity.tvTask2.setText("Task2 finish, result = " + result);
                    arr.add(-2);
                    break;
                case Constants.TASK3_CODE:
                    StartActivity.tvTask3.setText("Task3 finish, result = " + result);
                    arr.add(-3);
                    break;
            }
        }
    }
}

