package com.example.adapterdemo.repo;


import com.example.adapterdemo.R;
import com.example.adapterdemo.model.Product;

import java.util.ArrayList;

public class ProductsRepo {

    public static ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<Product>();
        for (int i = 1; i <= 10; i++) {
            boolean isOdd = ((i+1 & (i) ) == 0);
            products.add(new Product("Product " + i, i * 1000, R.drawable.ic_launcher, isOdd ));
        }
        return products;
    }

}
