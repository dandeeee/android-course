package com.dandeeee.app1;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class Activity2 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        Toast.makeText(Activity2.this, Boolean.toString(getIntent().getBooleanExtra("EXTRA", false)), Toast.LENGTH_LONG).show();
    }


}
