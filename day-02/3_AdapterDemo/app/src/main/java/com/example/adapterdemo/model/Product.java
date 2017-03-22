package com.example.adapterdemo.model;

public class Product {
    public String name;
    public int price;
    public int image;
    public boolean checked;

    public Product(String _describe, int _price, int _image, boolean _checked) {
        name = _describe;
        price = _price;
        image = _image;
        checked = _checked;
    }
}