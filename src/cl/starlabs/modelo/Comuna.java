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
    @NamedQuery(name = "Comuna.findAll", query = "SELECT c FROM Comuna c ORDER BY c.nombre ASC"),
    @NamedQuery(name = "Comuna.findAllById", query = "SELECT c FROM Comuna c ORDER BY c.idComuna DESC"),
    @NamedQuery(name = "Comuna.findByIdComuna", query = "SELECT c FROM Comuna c WHERE c.idComuna = :idComuna"),
    @NamedQuery(name = "Comuna.findByNombre", query = "SELECT c FROM Comuna c WHERE c.nombre = :nombre")})
public class Comuna implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_comuna", nullable = false)
    private Integer idComuna;
    @Basic(optional = false)
    @Column(nullable = false, length = 200)
    private String nombre;
    @JoinColumn(name = "provincia", referencedColumnName = "id_provincia", nullable = false)
    @ManyToOne(optional = false)
    private Provincia provincia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comuna")
    private List<Sucursal> sucursalList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comuna")
    private List<Propietario> propietarioList;

    public Comuna() {
    }

    public Comuna(Integer idComuna) {
        this.idComuna = idComuna;
    }

    public Comuna(Integer idComuna, String nombre) {
        this.idComuna = idComuna;
        this.nombre = nombre;
    }
    
    public Comuna(Integer idComuna, String nombre, Provincia provincia) {
        this.idComuna = idComuna;
        this.nombre = nombre;
        this.provincia = provincia;
    }

    public Integer getIdComuna() {
        return idComuna;
    }

    public void setIdComuna(Integer idComuna) {
        this.idComuna = idComuna;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    @XmlTransient
    public List<Sucursal> getSucursalList() {
        return sucursalList;
    }

    public void setSucursalList(List<Sucursal> sucursalList) {
        this.sucursalList = sucursalList;
    }

    @XmlTransient
    public List<Propietario> getPropietarioList() {
        return propietarioList;
    }

    public void setPropietarioList(List<Propietario> propietarioList) {
        this.propietarioList = propietarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComuna != null ? idComuna.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comuna)) {
            return false;
        }
        Comuna other = (Comuna) object;
        if ((this.idComuna == null && other.idComuna != null) || (this.idComuna != null && !this.idComuna.equals(other.idComuna))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.starlabs.modelo.Comuna[ idComuna=" + idComuna + " ]";
    }
    
}
