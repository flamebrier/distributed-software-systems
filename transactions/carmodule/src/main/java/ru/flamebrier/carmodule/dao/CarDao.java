package ru.flamebrier.carmodule.dao;

import ru.flamebrier.carmodule.models.Car;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author flamebrier
 */
@Local
public interface CarDao extends Dao<Car>{
    @Override
    void create(Car car);
    @Override
    Car getById(long id);
    @Override
    List<Car> getAll();
    @Override
    void update(Car car);
    @Override
    void delete(long id);
}