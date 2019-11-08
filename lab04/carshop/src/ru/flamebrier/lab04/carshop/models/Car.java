package ru.flamebrier.lab04.carshop.models;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Car {
    private int id;
    private String brand;
    private String model;
    private String color;
    private int year;
    private long mileage;
    private int owner;
    private BigDecimal price;

    public Car() {}

    public Car(int id, String brand, String model, String color, Date year, long mileage, int owner, BigDecimal price) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.color = color;

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(year);
        this.year = calendar.get(Calendar.YEAR);

        this.mileage = mileage;
        this.owner = owner;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getYear() {
        return year;
    }

    public Date getDateYear() {
        Calendar calendar = new GregorianCalendar(year, 0, 0);
        return calendar.getTime();
    }

    public void setYear(Date year) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(year);
        this.year = calendar.get(Calendar.YEAR);
    }

    public void setIntYear(int year) {
        this.year = year;
    }

    public long getMileage() {
        return mileage;
    }

    public void setMileage(long mileage) {
        this.mileage = mileage;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
