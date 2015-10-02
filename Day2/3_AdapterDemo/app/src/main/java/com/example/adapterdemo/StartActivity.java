package com.example.adapterdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.adapterdemo.model.Product;
import com.example.adapterdemo.repo.ProductsRepo;

import java.util.ArrayList;


public class StartActivity extends Activity {

    ArrayList<Product> products = ProductsRepo.getAllProducts();
    BoxAdapter boxAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ListView lvMain = (ListView) findViewById(R.id.lvMain);

        boxAdapter = new BoxAdapter(this, products);

        lvMain.setAdapter(boxAdapter);
    }

    public void showCart(View v) {
        String result = "Товары в корзине:";
        for (Product p : boxAdapter.getChecked())
            result += "\n" + p.name;

        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }
    


}
