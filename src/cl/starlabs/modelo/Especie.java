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
    @NamedQuery(name = "Especie.findAll", query = "SELECT e FROM Especie e"),
    @NamedQuery(name = "Especie.findAllDesc", query = "SELECT e FROM Especie e ORDER BY e.idEspecie DESC"),
    @NamedQuery(name = "Especie.findByIdEspecie", query = "SELECT e FROM Especie e WHERE e.idEspecie = :idEspecie"),
    @NamedQuery(name = "Especie.findByNombre", query = "SELECT e FROM Especie e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "Especie.findByFamilia", query = "SELECT e FROM Especie e WHERE e.familia = :familia")})
public class Especie implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_especie", nullable = false)
    private Integer idEspecie;
    @Basic(optional = false)
    @Column(nullable = false, length = 40)
    private String nombre;
    @Basic(optional = false)
    @Column(nullable = false, length = 50)
    private String familia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "especie")
    private List<Raza> razaList;

    public Especie() {
    }

    public Especie(Integer idEspecie) {
        this.idEspecie = idEspecie;
    }

    public Especie(Integer idEspecie, String nombre, String familia) {
        this.idEspecie = idEspecie;
        this.nombre = nombre;
        this.familia = familia;
    }
    
    public Especie(Integer idEspecie, String nombre, String familia, List<Raza> raza) {
        this.idEspecie = idEspecie;
        this.nombre = nombre;
        this.familia = familia;
        this.razaList = raza;
    }

    public Integer getIdEspecie() {
        return idEspecie;
    }

    public void setIdEspecie(Integer idEspecie) {
        this.idEspecie = idEspecie;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    @XmlTransient
    public List<Raza> getRazaList() {
        return razaList;
    }

    public void setRazaList(List<Raza> razaList) {
        this.razaList = razaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEspecie != null ? idEspecie.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Especie)) {
            return false;
        }
        Especie other = (Especie) object;
        if ((this.idEspecie == null && other.idEspecie != null) || (this.idEspecie != null && !this.idEspecie.equals(other.idEspecie))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.starlabs.modelo.Especie[ idEspecie=" + idEspecie + " ]";
    }
    
}
