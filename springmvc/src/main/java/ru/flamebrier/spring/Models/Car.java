package ru.flamebrier.spring.Models;

public class Car {
	
	private int id;
	private String model;
	private short year;
	
	public Car(int id, String model, short year) {
		this.setId(id);
		this.model = model;
		this.year = year;
	}
	
	public Car(String model, short year) {
		this.model = model;
		this.year = year;
	}
	
	public Car() {}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}

	public short getYear() {
		return year;
	}
	public void setYear(short year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", model=" + model + ", year=" + year + "]";
	}
}