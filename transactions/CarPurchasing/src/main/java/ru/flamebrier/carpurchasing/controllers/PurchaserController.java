package ru.flamebrier.carpurchasing.controllers;

import ru.flamebrier.purchasermodule.dao.PurchaserDao;
import ru.flamebrier.purchasermodule.models.Purchaser;
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
@Named("purchaserController")
@SessionScoped
public class PurchaserController implements Serializable {
    
    private static final long serialVersionUID = -1507373694984101022L;
    
    @EJB(beanName = "JpaPurchaserDao")
    private PurchaserDao purchaserDao;
    
    private Purchaser purchaser;

    public Purchaser getPurchaser() {
        return purchaser;
    }

    public void setPurchaser(Purchaser purchaser) {
        this.purchaser = purchaser;
    }
    
    public void gePurchaserById(long id) {
        this.purchaser = purchaserDao.getById(purchaser.getId());
    }
    
    public String createPurchaser() {
        purchaserDao.create(purchaser);
        return "toIndex";
    }
    
    public List<Purchaser> getPurchasers() {
        return purchaserDao.getAll();
    }

    public void deletePurchaser(long id) {
        purchaserDao.delete(id);
    }
    
    public void updatePurchaser(Purchaser purchaser) {
        purchaserDao.update(purchaser);
    }
    
    @PostConstruct
    public void init() {
        purchaser = new Purchaser();
    }
}