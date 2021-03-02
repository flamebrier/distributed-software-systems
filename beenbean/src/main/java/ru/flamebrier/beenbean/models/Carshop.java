package ru.flamebrier.beenbean.models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PostUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author flamebrier
 */
@Entity
@Table(name = "carshop")
public class Carshop implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Size(min = 1, max = 100)
    @NotNull
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "carshop")
    private List<Manager> managers;
    @OneToMany(mappedBy = "carshop")
    private List<CarPurchase> purchases;

    public Carshop() {
    }

    public Carshop(Long id) {
        this.id = id;
    }

    public Carshop(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Manager> getManagers() {
        return managers;
    }

    public void setManagers(List<Manager> managers) {
        this.managers = managers;
    }

    public List<CarPurchase> getpurchases() {
        return purchases;
    }

    public void setpurchases(List<CarPurchase> purchases) {
        this.purchases = purchases;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @PostUpdate
    public void printUpdateSmt() {
        System.out.println("Carshop model has been updated");
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
        if (!(object instanceof Carshop)) {
            return false;
        }
        Carshop other = (Carshop) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Carshop[ name=" + name + " ]";
    }
    
}