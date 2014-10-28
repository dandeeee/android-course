package com.example.broadcastreceiverservice;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class StartActivity extends Activity {

    public static TextView tvTask1 = null;
    public static TextView tvTask2 = null;
    public static TextView tvTask3 = null;

    BroadcastReceiver broadcastReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        tvTask1 = (TextView) findViewById(R.id.tvTask1);
        tvTask1.setText("Task1");
        tvTask2 = (TextView) findViewById(R.id.tvTask2);
        tvTask2.setText("Task2");
        tvTask3 = (TextView) findViewById(R.id.tvTask3);
        tvTask3.setText("Task3");

        broadcastReceiver = new MyReceiver();
        IntentFilter intFilt = new IntentFilter(Constants.BROADCAST_ACTION);
        registerReceiver(broadcastReceiver, intFilt);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(broadcastReceiver);
    }

    public void onClickStart(View v) {

        // Создаем Intent для вызова сервиса,
        // кладем туда параметр времени и код задачи, стартуем сервис
        Intent intent;

        intent = new Intent(this, MyService.class)
                .putExtra(Constants.PARAM_TIME, 7)
                .putExtra(Constants.PARAM_TASK, Constants.TASK1_CODE);
    	startService(intent);

        intent = new Intent(this, MyService.class)
                .putExtra(Constants.PARAM_TIME, 4)
                .putExtra(Constants.PARAM_TASK, Constants.TASK2_CODE);
        startService(intent);

        intent = new Intent(this, MyService.class)
                .putExtra(Constants.PARAM_TIME, 6)
                .putExtra(Constants.PARAM_TASK, Constants.TASK3_CODE);
        startService(intent);
    }
}