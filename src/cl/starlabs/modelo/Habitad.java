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
import javax.persistence.Lob;
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
    @NamedQuery(name = "Habitad.findAll", query = "SELECT h FROM Habitad h"),
    @NamedQuery(name = "Habitad.findAllDesc", query = "SELECT h FROM Habitad h ORDER BY h.idHabitad DESC"),
    @NamedQuery(name = "Habitad.findByIdHabitad", query = "SELECT h FROM Habitad h WHERE h.idHabitad = :idHabitad"),
    @NamedQuery(name = "Habitad.findByNombre", query = "SELECT h FROM Habitad h WHERE h.nombre = :nombre")})
public class Habitad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_habitad", nullable = false)
    private Integer idHabitad;
    @Basic(optional = false)
    @Column(nullable = false, length = 75)
    private String nombre;
    @Lob
    @Column(length = 2147483647)
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "habitad")
    private List<Mascota> mascotaList;

    public Habitad() {
    }

    public Habitad(Integer idHabitad) {
        this.idHabitad = idHabitad;
    }

    public Habitad(Integer idHabitad, String nombre) {
        this.idHabitad = idHabitad;
        this.nombre = nombre;
    }

    public Integer getIdHabitad() {
        return idHabitad;
    }

    public void setIdHabitad(Integer idHabitad) {
        this.idHabitad = idHabitad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        hash += (idHabitad != null ? idHabitad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Habitad)) {
            return false;
        }
        Habitad other = (Habitad) object;
        if ((this.idHabitad == null && other.idHabitad != null) || (this.idHabitad != null && !this.idHabitad.equals(other.idHabitad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.starlabs.modelo.Habitad[ idHabitad=" + idHabitad + " ]";
    }
    
}
