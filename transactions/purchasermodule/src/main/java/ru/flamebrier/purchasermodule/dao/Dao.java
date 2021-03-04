package ru.flamebrier.purchasermodule.dao;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author flamebrier
 */
@Local
public interface Dao<T> {
    void create(T t);
    T getById(long id);
    List<T> getAll();
    void update(T t);
    void delete(long id);
}