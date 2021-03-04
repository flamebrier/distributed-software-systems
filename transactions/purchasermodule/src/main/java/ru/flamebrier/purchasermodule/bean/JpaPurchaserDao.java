package ru.flamebrier.purchasermodule.bean;

import ru.flamebrier.purchasermodule.dao.PurchaserDao;
import ru.flamebrier.purchasermodule.models.Purchaser;
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
public class JpaPurchaserDao implements PurchaserDao {

    @PersistenceContext(unitName = "my_persistencex_purchaser")
    private EntityManager em; 
    
    @Override
    public void create(Purchaser purchaser) {
        em.persist(purchaser);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createRequiredAttr(Purchaser purchaser) {
        em.persist(purchaser);
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void createNotSupportedAttr(Purchaser purchaser) {
        em.persist(purchaser);
    }
    
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public void createNeverAttr(Purchaser purchaser) {
        em.persist(purchaser);
    } 

    @Override
    public Purchaser getById(long id) {
        return em.find(Purchaser.class, id);
    }

    @Override
    public List<Purchaser> getAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Purchaser> query = cb.createQuery(Purchaser.class);
        Root<Purchaser> c = query.from(Purchaser.class);
        query.select(c);
        return em.createQuery(query).getResultList();
    }

    @Override
    public void update(Purchaser purchaser) {
        em.merge(purchaser);
    }

    @Override
    public void delete(long id) {
        em.remove(em.find(Purchaser.class, id));
    }
}