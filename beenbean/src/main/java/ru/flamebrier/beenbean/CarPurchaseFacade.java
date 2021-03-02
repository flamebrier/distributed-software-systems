/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.flamebrier.beenbean;

import ru.flamebrier.beenbean.models.CarPurchase;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author flamebrier
 */
@Stateless
public class CarPurchaseFacade extends AbstractFacade<CarPurchase> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CarPurchaseFacade() {
        super(CarPurchase.class);
    }
    
}
