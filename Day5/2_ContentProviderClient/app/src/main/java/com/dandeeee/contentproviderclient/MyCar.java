package com.dandeeee.contentproviderclient;

import java.io.Serializable;

public class MyCar implements Serializable {
	private static final long serialVersionUID = 1L;

	String name;
	String color;
	String company;
	float price;
	
	@Override
	public String toString() {
		return "MyCar [name=" + name + ", company=" + company + "]";
	}
	
	public MyCar() {
		super();
	}
}
