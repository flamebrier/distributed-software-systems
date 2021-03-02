package ru.flamebrier.sessionbean.Models;

public class Car {
	
	private long id;
	private String model;
	private String brand;
	private int year;
	
	public Car(String model, String brand, int year) {
		this.model = model;
		this.brand = brand;
		this.year = year;
	}
	
	public Car(long id, String model, String brand, int year) {
		this.id = id;
		this.model = model;
		this.brand = brand;
		this.year = year;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
}