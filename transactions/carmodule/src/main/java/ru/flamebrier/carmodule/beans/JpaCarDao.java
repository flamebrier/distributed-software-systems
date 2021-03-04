package ru.flamebrier.carmodule.beans;

import ru.flamebrier.carmodule.dao.CarDao;
import ru.flamebrier.carmodule.models.Car;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author flamebrier
 */
@Stateless
@LocalBean
public class JpaCarDao implements CarDao {

    @PersistenceContext(unitName = "my_persistencex_car")
    private EntityManager em;
    
    @Override
    public void create(Car car) {
        em.persist(car);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createRequiredAttr(Car car) {
        em.persist(car);
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void createNotSupportedAttr(Car car) {
        em.persist(car);
    }
    
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public void createNeverAttr(Car car) {
        em.persist(car);
    }
    
    @Override
    public Car getById(long id) {
        return em.find(Car.class, id);
    }

    @Override
    public List<Car> getAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Car> query = cb.createQuery(Car.class);
        Root<Car> c = query.from(Car.class);
        query.select(c);
        return em.createQuery(query).getResultList();
    }

    @Override
    public void update(Car car) {
        em.merge(car);
    }

    @Override
    public void delete(long id) {
        em.remove(em.find(Car.class, id));
    }
}