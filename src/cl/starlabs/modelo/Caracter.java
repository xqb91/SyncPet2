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
    @NamedQuery(name = "Caracter.findAll", query = "SELECT c FROM Caracter c"),
    @NamedQuery(name = "Caracter.findAllDesc", query = "SELECT c FROM Caracter c ORDER BY c.idCaracter DESC"),
    @NamedQuery(name = "Caracter.findByIdCaracter", query = "SELECT c FROM Caracter c WHERE c.idCaracter = :idCaracter"),
    @NamedQuery(name = "Caracter.findByNombre", query = "SELECT c FROM Caracter c WHERE c.nombre = :nombre")})
public class Caracter implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_caracter", nullable = false)
    private Integer idCaracter;
    @Basic(optional = false)
    @Column(nullable = false, length = 40)
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "caracter")
    private List<Mascota> mascotaList;

    public Caracter() {
    }

    public Caracter(Integer idCaracter) {
        this.idCaracter = idCaracter;
    }

    public Caracter(Integer idCaracter, String nombre) {
        this.idCaracter = idCaracter;
        this.nombre = nombre;
    }

    public Integer getIdCaracter() {
        return idCaracter;
    }

    public void setIdCaracter(Integer idCaracter) {
        this.idCaracter = idCaracter;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
        hash += (idCaracter != null ? idCaracter.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Caracter)) {
            return false;
        }
        Caracter other = (Caracter) object;
        if ((this.idCaracter == null && other.idCaracter != null) || (this.idCaracter != null && !this.idCaracter.equals(other.idCaracter))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.starlabs.modelo.Caracter[ idCaracter=" + idCaracter + " ]";
    }
    
}
