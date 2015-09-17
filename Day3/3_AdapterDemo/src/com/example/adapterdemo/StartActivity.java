package com.example.adapterdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class StartActivity extends Activity {

    ArrayList<Product> products = new ArrayList<Product>();
    BoxAdapter boxAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        fillProducts();
        ListView lvMain = (ListView) findViewById(R.id.lvMain);

        boxAdapter = new BoxAdapter(this, products);

        lvMain.setAdapter(boxAdapter);
    }

    void fillProducts() {
        for (int i = 1; i <= 10; i++) {
            products.add(new Product("Product " + i, i * 1000,R.drawable.ic_launcher, false));
        }
    }

    public void showResult(View v) {
        String result = "Товары в корзине:";
        for (Product p : boxAdapter.getBox())
            result += "\n" + p.name;

        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
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
