/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Victor Manuel Araya
 */
@Entity
@Table(catalog = "syncpet", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Raza.findAll", query = "SELECT r FROM Raza r"),
    @NamedQuery(name = "Raza.findAllDesc", query = "SELECT r FROM Raza r ORDER BY r.idRaza DESC"),
    @NamedQuery(name = "Raza.findByEspecie", query = "SELECT r FROM Raza r WHERE r.especie = :especie"),
    @NamedQuery(name = "Raza.findByIdRaza", query = "SELECT r FROM Raza r WHERE r.idRaza = :idRaza"),
    @NamedQuery(name = "Raza.findByNombre", query = "SELECT r FROM Raza r WHERE r.nombre = :nombre")})
public class Raza implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_raza", nullable = false)
    private Integer idRaza;
    @Basic(optional = false)
    @Column(nullable = false, length = 45)
    private String nombre;
    @JoinColumn(name = "especie", referencedColumnName = "id_especie", nullable = false)
    @ManyToOne(optional = false)
    private Especie especie;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "raza")
    private List<Mascota> mascotaList;

    public Raza() {
    }

    public Raza(Integer idRaza) {
        this.idRaza = idRaza;
    }
    
    public Raza(Integer idRaza, String nombre, Especie especie) {
        this.idRaza = idRaza;
        this.nombre = nombre;
        this.especie = especie;
    }

    public Integer getIdRaza() {
        return idRaza;
    }

    public void setIdRaza(Integer idRaza) {
        this.idRaza = idRaza;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    @XmlTransient
    public List<Mascota> getMascotaList() {
        return mascotaList;
    }

    public void setMascotaList(List<Mascota> mascotaList) {
        this.mascotaList = mascotaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRaza != null ? idRaza.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Raza)) {
            return false;
        }
        Raza other = (Raza) object;
        if ((this.idRaza == null && other.idRaza != null) || (this.idRaza != null && !this.idRaza.equals(other.idRaza))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.starlabs.modelo.Raza[ idRaza=" + idRaza + " ]";
    }
    
}
