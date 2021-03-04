package ru.flamebrier.purchasermodule.dao;

import ru.flamebrier.purchasermodule.models.Purchaser;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author flamebrier
 */
@Local
public interface PurchaserDao extends Dao<Purchaser> {
    @Override
    void create(Purchaser purchaser);
    @Override
    Purchaser getById(long id);
    @Override
    List<Purchaser> getAll();
    @Override
    void update(Purchaser purchaser);
    @Override
    void delete(long id);
}