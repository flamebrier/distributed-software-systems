package ru.flamebrier.sessionbean.Dao;

import java.util.List;

import ru.flamebrier.sessionbean.Exceptions.DaoException;
import ru.flamebrier.sessionbean.Models.Car;

public interface CarDao extends Dao<Car>{
	@Override
	boolean create(Car car) throws DaoException;
	@Override
	List<Car> getAll() throws DaoException;
	@Override
    Car getById(long id) throws DaoException;
	@Override
	boolean update(Car car) throws DaoException;
	@Override
	boolean delete(long id) throws DaoException;
}