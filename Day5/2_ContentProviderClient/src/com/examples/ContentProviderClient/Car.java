package com.examples.ContentProviderClient;

import java.io.Serializable;

public class Car implements Serializable {
	private static final long serialVersionUID = 1L;

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
