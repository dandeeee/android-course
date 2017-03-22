package com.dandeeee.contentproviderdemo.model;

public class Car {

	public String name;
	public String color;
	public String company;
	public float price;
	
	@Override
	public String toString() {
		return "Car [name=" + name + ", company=" + company + "]";
	}


	public static Car getDummyInstance(){
		Car car = new Car();
		car.name = "Kalina"; car.company = "Lada"; car.color = "Black"; car.price = 100000f;
		return car;
	}

}
