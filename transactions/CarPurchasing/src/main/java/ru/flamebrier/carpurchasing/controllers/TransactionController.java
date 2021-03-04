package ru.flamebrier.carpurchasing.controllers;

import ru.flamebrier.carpurchasing.beans.TransactionBean;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author flamebrier
 */
@Named("transactionController")
@SessionScoped
public class TransactionController implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @EJB
    private TransactionBean bean;
    
    public void successTrans() {
        bean.successTransaction();
    }    
    
    public void carFailPurchaserReq() {
        bean.carDbFailWithPurchaserDbRequired();
    }
    
    public void purchaserFailCarReq() {
        bean.purchaserDbFailWithCarDbRequired();
    }
    
    public void carFailPurchaserNotSup() {
        bean.carDbFailWithPurchaserDbNotSupport();
    }
    
    public void purchaserFailCarNotSup() {
        bean.purchaserDbFailWithCarDbNotSupport();
    }
}