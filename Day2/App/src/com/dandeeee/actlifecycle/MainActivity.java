package com.dandeeee.actlifecycle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

	public static final int SECOND_ACT = 1;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Log.e("LifeCycle I", "onCreate");
        setContentView(R.layout.activity_main);
        
        
        Button btn = (Button) findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondAct.class);

                startActivityForResult(intent, SECOND_ACT);
            }
        });
        
        
    }

    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
            switch (requestCode) {
                case SECOND_ACT :
                    Log.e("MainActivity", data.getStringExtra("msgFrom2"));
                    break;
                default :
                    break;
            }

        }

    }
    
    

    
    @Override
    protected void onStart() {
        super.onStart();
        Log.e("LifeCycle I", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("LifeCycle I", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("LifeCycle I", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("LifeCycle I", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("LifeCycle I", "onDestroy");
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
