package ru.flamebrier.sessionbean.Dao;

import java.util.List;

import javax.ejb.Remote;

import ru.flamebrier.sessionbean.Exceptions.DaoException;

@Remote
public interface Dao<T> {
	boolean create(T t) throws DaoException;
	List<T> getAll() throws DaoException;
	T getById(long id) throws DaoException;
	boolean update(T t) throws DaoException;
	boolean delete(long id) throws DaoException;
}