package com.dandeeee.actlifecycle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class SecondAct extends Activity {

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_act);
        Log.e("LifeCycle II", "onCreate");

        Button btn = (Button) findViewById(R.id.buttonOK);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("msgFrom2","OK BLA BLA!");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    
    @Override
    protected void onStart() {
        super.onStart();
        Log.e("LifeCycle II", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("LifeCycle II", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("LifeCycle II", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("LifeCycle II", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("LifeCycle II", "onDestroy");
    }
    
    
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
