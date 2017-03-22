package com.example.daniel.inflaterdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout linear = (LinearLayout) findViewById(R.id.lin);
        LinearLayout rel = (LinearLayout) findViewById(R.id.rel);


        LayoutInflater inflater = getLayoutInflater();
        View item1 = inflater.inflate(R.layout.inflated_item, linear, false);
        View item2 = inflater.inflate(R.layout.inflated_item_2, rel, false);

        linear.addView(item1);
        rel.addView(item2);

    }

}
