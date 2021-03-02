package ru.flamebrier.beenbean.models;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author flamebrier
 */
@Entity
@Table(name = "purchaser", catalog = "carshop", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Purchaser.findAll", query = "SELECT u FROM Purchaser u"),
    @NamedQuery(name = "Purchaser.findById", query = "SELECT u FROM Purchaser u WHERE u.id = :id"),
    @NamedQuery(name = "Purchaser.findByLastName", query = "SELECT u FROM Purchaser u WHERE u.lastname = :lastname"),
    @NamedQuery(name = "Purchaser.findByName", query = "SELECT u FROM Purchaser u WHERE u.name = :name"),
    @NamedQuery(name = "Purchaser.findByRating", query = "SELECT u FROM Purchaser u WHERE u.rating = :rating")})
public class Purchaser implements Serializable {

    public List<CarPurchase> getpurchases() {
        return purchases;
    }

    public void setpurchases(List<CarPurchase> purchases) {
        this.purchases = purchases;
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "lastname")
    private String lastname;
    @Size(max = 255)
    @Column(name = "name")
    private String name;
    @Column(name = "rating")
    private BigInteger rating;
    @OneToMany(mappedBy = "purchaserId")
    private Collection<Car> carCollection;
    @ManyToMany
    @JoinTable(name = "purchase",
            joinColumns = @JoinColumn(name = "purchaser_id"),
            inverseJoinColumns = @JoinColumn(name = "car_purchase_id"))
    private List<CarPurchase> purchases;

    public Purchaser() {
    }

    public Purchaser(Long id) {
        this.id = id;
    }

    public Purchaser(Long id, String lastname) {
        this.id = id;
        this.lastname = lastname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lastname) {
        this.lastname = lastname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getRating() {
        return rating;
    }

    public void setRating(BigInteger rating) {
        this.rating = rating;
    }

    @XmlTransient
    public Collection<Car> getCarCollection() {
        return carCollection;
    }

    public void setCarCollection(Collection<Car> carCollection) {
        this.carCollection = carCollection;
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
        if (!(object instanceof Purchaser)) {
            return false;
        }
        Purchaser other = (Purchaser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Purchaser[ name=" + name + " ]";
    }
    
}