package ru.flamebrier.carpurchasing.beans;

import ru.flamebrier.carmodule.beans.JpaCarDao;
import ru.flamebrier.carmodule.models.Car;
import ru.flamebrier.purchasermodule.bean.JpaPurchaserDao;
import ru.flamebrier.purchasermodule.models.Purchaser;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

/**
 *
 * @author flamebrier
 */

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TransactionBean {
    
    @EJB
    private JpaCarDao carDao;
    @EJB
    private JpaPurchaserDao purchaserDao;
    
    public void successTransaction() {
        carDao.createRequiredAttr(new Car("GAZ 2752"));
        purchaserDao.createRequiredAttr(new Purchaser("Roshchina A.I.", new Date()));
    }
    
    public void carDbFailWithPurchaserDbRequired() {
        purchaserDao.createRequiredAttr(new Purchaser("Mercedes S6", new Date()));
        carDao.createNeverAttr(new Car("Petrov V.A."));
    }
    
    public void purchaserDbFailWithCarDbRequired() {
        carDao.createRequiredAttr(new Car("GAZ 2752"));
        purchaserDao.createNeverAttr(new Purchaser("Roshchin I.E.", new Date()));
    }
    
    public void carDbFailWithPurchaserDbNotSupport() {
        purchaserDao.createNotSupportedAttr(new Purchaser("Mercedes S6", new Date()));
        carDao.createNeverAttr(new Car("Roshchina A.I."));
    }
    
    public void purchaserDbFailWithCarDbNotSupport() {
        carDao.createNotSupportedAttr(new Car("GAZ 2217"));
        purchaserDao.createRequiredAttr(new Purchaser("Petrov V.A.", new Date()));
    }
}