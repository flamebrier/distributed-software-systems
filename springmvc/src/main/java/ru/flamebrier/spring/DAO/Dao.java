package ru.flamebrier.spring.DAO;

import java.util.List;

public interface Dao<T> {
	T getById(int id);
	List<T> getAll();
	boolean insert(T t);
	boolean update(T t);
	boolean delete(int id);
}