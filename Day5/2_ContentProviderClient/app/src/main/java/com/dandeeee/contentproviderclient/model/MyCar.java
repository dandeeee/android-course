package com.dandeeee.contentproviderclient.model;

import java.io.Serializable;

public class MyCar implements Serializable {
	private static final long serialVersionUID = 1L;

	public String name;
	public String color;
	public String company;
	public float price;
	
	@Override
	public String toString() {
		return "MyCar [name=" + name + ", company=" + company + "]";
	}

	public static MyCar getDummyInstance(){
		MyCar car = new MyCar();
		car.name = "A5";
		car.company = "Audi";
		car.color = "Red";
		car.price = 10000000f;
		return car;
	}
	
}
