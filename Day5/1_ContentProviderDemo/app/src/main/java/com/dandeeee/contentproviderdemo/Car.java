package com.dandeeee.contentproviderdemo;

public class Car {
	String name;
	String color;
	String company;
	float price;
	
	@Override
	public String toString() {
		return "Car [name=" + name + ", company=" + company + "]";
	}
	
	public Car() {
		super();
	}
}
