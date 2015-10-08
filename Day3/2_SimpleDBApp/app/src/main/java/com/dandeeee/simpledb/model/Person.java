package com.dandeeee.simpledb.model;

public class Person {

	public String name;
	public String address;
	public int personId;
	public int personAge;
	
	@Override
	public String toString() {
		return "Name: " + this.name + " Address:" + this.address;
	}
}