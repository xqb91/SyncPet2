/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Victor Manuel Araya
 */
@Entity
@Table(catalog = "syncpet", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Desparacitaciones.findAll", query = "SELECT d FROM Desparacitaciones d"),
    @NamedQuery(name = "Desparacitaciones.findAllDesc", query = "SELECT d FROM Desparacitaciones d ORDER BY d.idDesparacitacion DESC"),
    @NamedQuery(name = "Desparacitaciones.findByIdDesparacitacion", query = "SELECT d FROM Desparacitaciones d WHERE d.idDesparacitacion = :idDesparacitacion"),
    @NamedQuery(name = "Desparacitaciones.findByFecha", query = "SELECT d FROM Desparacitaciones d WHERE d.fecha = :fecha")})
public class Desparacitaciones implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_desparacitacion", nullable = false)
    private Integer idDesparacitacion;
    @Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Lob
    @Column(length = 2147483647)
    private String descripcion;
    @JoinColumn(name = "hospitalizacion", referencedColumnName = "id_hospitalizacion", nullable = false)
    @ManyToOne(optional = false)
    private Hospitalizacion hospitalizacion;
    @JoinColumn(name = "mascota", referencedColumnName = "id_mascota", nullable = false)
    @ManyToOne(optional = false)
    private Mascota mascota;
    @JoinColumn(name = "especialista", referencedColumnName = "id_veterinario", nullable = false)
    @ManyToOne(optional = false)
    private Veterinario especialista;

    public Desparacitaciones() {
    }

    public Desparacitaciones(Integer idDesparacitacion) {
        this.idDesparacitacion = idDesparacitacion;
    }

    public Desparacitaciones(Integer idDesparacitacion, Date fecha) {
        this.idDesparacitacion = idDesparacitacion;
        this.fecha = fecha;
    }

    public Integer getIdDesparacitacion() {
        return idDesparacitacion;
    }

    public void setIdDesparacitacion(Integer idDesparacitacion) {
        this.idDesparacitacion = idDesparacitacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Hospitalizacion getHospitalizacion() {
        return hospitalizacion;
    }

    public void setHospitalizacion(Hospitalizacion hospitalizacion) {
        this.hospitalizacion = hospitalizacion;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public Veterinario getEspecialista() {
        return especialista;
    }

    public void setEspecialista(Veterinario especialista) {
        this.especialista = especialista;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDesparacitacion != null ? idDesparacitacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Desparacitaciones)) {
            return false;
        }
        Desparacitaciones other = (Desparacitaciones) object;
        if ((this.idDesparacitacion == null && other.idDesparacitacion != null) || (this.idDesparacitacion != null && !this.idDesparacitacion.equals(other.idDesparacitacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.starlabs.modelo.Desparacitaciones[ idDesparacitacion=" + idDesparacitacion + " ]";
    }
    
}
