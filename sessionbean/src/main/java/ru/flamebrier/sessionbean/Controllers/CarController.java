package ru.flamebrier.sessionbean.Controllers;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import ru.flamebrier.sessionbean.Dao.JdbcCarDao;
import ru.flamebrier.sessionbean.Exceptions.DaoException;
import ru.flamebrier.sessionbean.Models.Car;

import java.io.Serializable;

@Named
@Stateful
@RequestScoped
public class CarController implements Serializable {
	
	private static final long serialVersionUID = -1507373694984101022L;
	
	@EJB
	private JdbcCarDao dao;
	private Car car = new Car();
	
	
	public String createCar() {
		try {
			dao.create(car);
			this.car = new Car();
		} catch (DaoException e) {
			 System.out.println(e.getMessage());
		}
		
		return "index.xhtml";
	}
	
	public Car getCar() {
		return car;
	}
	
	public List<Car> getCarList() {
		try {
			return dao.getAll();
		} catch (DaoException e) {
			System.out.println(e.getMessage());
		}
		
		List<Car> retCars = new ArrayList<Car>();
		retCars.add(new Car("2752", "ГАЗ", 2005));
		
		return retCars;
	}
	
	
	public void getCarById() {
		try {
			car = dao.getById(car.getId());
		} catch (DaoException e) {
			 System.out.println(e.getMessage());
		}
	}
	
	public void editCar() {
		try {
			dao.update(car);
		} catch (DaoException e) {
			 System.out.println(e.getMessage());
		}
	}
	
	public void deleteCar(long id) {
		try {
			dao.delete(id);
		} catch (DaoException e) {
			System.out.println(e.getMessage());
		}
	}
}