package ru.flamebrier.carpurchasing.controllers;

import ru.flamebrier.carmodule.dao.CarDao;
import ru.flamebrier.carmodule.models.Car;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author flamebrier
 */
@Named("carController")
@SessionScoped
public class CarController implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @EJB(beanName = "JpaCarDao")
    private CarDao carDao;
    
    private Car car;

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
    
    public void getCarById() {
        this.car = carDao.getById(car.getId());
    }
    
    public String createCar() {
        carDao.create(car);
        return "toIndex";
    }
    
    public List<Car> getCars() {
        return carDao.getAll();
    }

    public void deleteCar(long id) {
        carDao.delete(id);
    }
    
    public void updateCar(Car car) {
        carDao.update(car);
    }
    
    @PostConstruct
    public void init() {
        car = new Car();
    }
    
}