package ru.flamebrier.lab03.carshopdaoapp;

import org.junit.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DAOTest {
    private static DAO dao;
    @Before
    public void createDAO() {
        dao = new DAO();
    }

    @After
    public void closeDAO() {
        dao.closeDAO();
    }

    @Test
    public void insertTest1() {
        System.out.println("@Test - run insertTest1");

        Calendar calendar = new GregorianCalendar(2000, 1, 1);

        Owner owner = new Owner(
                15,
                "AAA",
                "AAA",
                "ivanov@ivanov.ru",
                "+71234567890",
                calendar.getTime()
        );
        dao.insertOwner(owner);

        Owner[] selected = dao.selectAllOwners();
        Assert.assertEquals("Name check", owner.getName(), selected[0].getName());
        Assert.assertEquals("Lastname check", owner.getLastName(), selected[0].getLastName());
        Assert.assertEquals("Email check", owner.getEmail(), selected[0].getEmail());
        Assert.assertEquals("Phone check", owner.getPhone(), selected[0].getPhone());
    }

    @Test
    public void insertTest2() {
        System.out.println("@Test - run insertTest2");

        DAO dao = new DAO();

        Calendar calendar = new GregorianCalendar(1951, 0, 0);

        Car car = new Car(
                15,
                "Test",
                "000",
                "Blue",
                calendar.getTime(),
                10000,
                2,
                BigDecimal.valueOf(150000)
        );
        dao.insertCar(car);

        Car[] selected = dao.selectAllCars();
        Assert.assertEquals("Brand check", car.getBrand(), selected[selected.length - 1].getBrand());
        Assert.assertEquals("Model check", car.getModel(), selected[selected.length - 1].getModel());
        Assert.assertEquals("Color check", car.getColor(), selected[selected.length - 1].getColor());
        Assert.assertEquals("Mile check", car.getMileage(), selected[selected.length - 1].getMileage());
        Assert.assertEquals("Owner check", car.getOwner(), selected[selected.length - 1].getOwner());
        Assert.assertEquals("Price check", car.getPrice(), selected[selected.length - 1].getPrice());
    }
}
