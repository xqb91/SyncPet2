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
    @NamedQuery(name = "Vacunas.findAll", query = "SELECT v FROM Vacunas v"),
    @NamedQuery(name = "Vacunas.findAllDesc", query = "SELECT v FROM Vacunas v ORDER BY v.idVacuna DESC"),
    @NamedQuery(name = "Vacunas.findByIdVacuna", query = "SELECT v FROM Vacunas v WHERE v.idVacuna = :idVacuna"),
    @NamedQuery(name = "Vacunas.findByNombre", query = "SELECT v FROM Vacunas v WHERE v.nombre = :nombre"),
    @NamedQuery(name = "Vacunas.findByCompuestoActivo", query = "SELECT v FROM Vacunas v WHERE v.compuestoActivo = :compuestoActivo")})
public class Vacunas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_vacuna", nullable = false)
    private Integer idVacuna;
    @Basic(optional = false)
    @Column(nullable = false, length = 250)
    private String nombre;
    @Lob
    @Column(length = 2147483647)
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "compuesto_activo", nullable = false, length = 150)
    private String compuestoActivo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vacuna")
    private List<Historialvacunas> historialvacunasList;

    public Vacunas() {
    }

    public Vacunas(Integer idVacuna) {
        this.idVacuna = idVacuna;
    }

    public Vacunas(Integer idVacuna, String nombre, String compuestoActivo) {
        this.idVacuna = idVacuna;
        this.nombre = nombre;
        this.compuestoActivo = compuestoActivo;
    }
    
    public Vacunas(Integer idVacuna, String nombre, String descripcion, String compuestoActivo) {
        this.idVacuna = idVacuna;
        this.nombre = nombre;
        this.compuestoActivo = compuestoActivo;
        this.descripcion = descripcion;
    }

    public Integer getIdVacuna() {
        return idVacuna;
    }

    public void setIdVacuna(Integer idVacuna) {
        this.idVacuna = idVacuna;
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

    public String getCompuestoActivo() {
        return compuestoActivo;
    }

    public void setCompuestoActivo(String compuestoActivo) {
        this.compuestoActivo = compuestoActivo;
    }

    @XmlTransient
    public List<Historialvacunas> getHistorialvacunasList() {
        return historialvacunasList;
    }

    public void setHistorialvacunasList(List<Historialvacunas> historialvacunasList) {
        this.historialvacunasList = historialvacunasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVacuna != null ? idVacuna.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vacunas)) {
            return false;
        }
        Vacunas other = (Vacunas) object;
        if ((this.idVacuna == null && other.idVacuna != null) || (this.idVacuna != null && !this.idVacuna.equals(other.idVacuna))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.starlabs.modelo.Vacunas[ idVacuna=" + idVacuna + " ]";
    }
    
}
