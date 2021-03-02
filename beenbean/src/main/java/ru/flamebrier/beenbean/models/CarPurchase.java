package ru.flamebrier.beenbean.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PostRemove;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author flamebrier
 */
@Entity
@Table(name = "carpurchase")
public class CarPurchase implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "purchaseDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date purchaseDate;
    @Column(name = "start_of_battle")
    @Temporal(TemporalType.TIMESTAMP)
    private Date carOnSaleDate;
    @ManyToOne
    @JoinColumn(name = "carshop_id", referencedColumnName = "id")
    private Carshop carshop;
    @ManyToMany(mappedBy = "purchases")
    private List<Purchaser> participants;

    public CarPurchase(Long id, Date purchaseDate, Date carOnSaleDate, Carshop carshop) {
        this.id = id;
        this.purchaseDate = purchaseDate;
        this.carOnSaleDate = carOnSaleDate;
        this.carshop = carshop;
    }

    public CarPurchase(Long id) {
        this.id = id;
    }
    
    public CarPurchase() {
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date endOfCarPurchase) {
        this.purchaseDate = endOfCarPurchase;
    }

    public Date getCarOnSaleDate() {
        return carOnSaleDate;
    }

    public void setCarOnSaleDate(Date startOfCarPurchase) {
        this.carOnSaleDate = startOfCarPurchase;
    }

    public Carshop getCarshop() {
        return carshop;
    }

    public void setCarshop(Carshop carshop) {
        this.carshop = carshop;
    }

    public List<Purchaser> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Purchaser> participants) {
        this.participants = participants;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @PostRemove
    public void printRemoveSmt() {
        System.out.println("CarPurchase model has been removed");
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CarPurchase)) {
            return false;
        }
        CarPurchase other = (CarPurchase) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CarPurchase[ id=" + id + " ]";
    }
    
}