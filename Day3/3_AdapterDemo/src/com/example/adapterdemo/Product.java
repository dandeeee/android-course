package com.example.adapterdemo;

public class Product {
    String name;

    int price;
    int image;
    boolean checked;

    Product(String _describe, int _price, int _image, boolean _checked) {
        name = _describe;
        price = _price;
        image = _image;
        checked = _checked;
    }
}