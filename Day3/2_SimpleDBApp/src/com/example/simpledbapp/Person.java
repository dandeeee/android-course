package com.example.simpledbapp;

class Person {
	String name;
	String address;
	int personId;
	int personAge;
	
	@Override
	public String toString() {
		return "Name: " + this.name + " Address:" + this.address;
	}
}