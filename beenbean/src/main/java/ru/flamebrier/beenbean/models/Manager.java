package ru.flamebrier.beenbean.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostPersist;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author flamebrier
 */
@Entity
@Table(name = "manager")
public class Manager implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "stage")
    private int stage;
    @Column(name = "name")
    @Size(min = 1, max = 75)
    private String name;
    @ManyToOne
    @JoinColumn(name = "carshop_id", referencedColumnName = "id")
    private Carshop carshop;

    public Manager(Long id, int stage, String name) {
        this.id = id;
        this.stage = stage;
        this.name = name;
    }

    public Manager(Long id) {
        this.id = id;
    }

    public Manager() {
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Carshop getCarshop() {
        return carshop;
    }

    public void setCarshop(Carshop carshop) {
        this.carshop = carshop;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @PostPersist
    public void printPersistSmt() {
        System.out.println("Manager model has been preserved");
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
        if (!(object instanceof Manager)) {
            return false;
        }
        Manager other = (Manager) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Manager[ name=" + name + " ]";
    }
    
}