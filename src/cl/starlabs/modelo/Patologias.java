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
    @NamedQuery(name = "Patologias.findAll", query = "SELECT p FROM Patologias p"),
    @NamedQuery(name = "Patologias.findAllDesc", query = "SELECT p FROM Patologias p ORDER BY p.idPatologia DESC"),
    @NamedQuery(name = "Patologias.findByIdPatologia", query = "SELECT p FROM Patologias p WHERE p.idPatologia = :idPatologia"),
    @NamedQuery(name = "Patologias.findByFechaDeteccion", query = "SELECT p FROM Patologias p WHERE p.fechaDeteccion = :fechaDeteccion")})
public class Patologias implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_patologia", nullable = false)
    private Integer idPatologia;
    @Basic(optional = false)
    @Column(name = "fecha_deteccion", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaDeteccion;
    @Lob
    @Column(length = 2147483647)
    private String observacion;
    @JoinColumn(name = "hospitalizacion", referencedColumnName = "id_hospitalizacion", nullable = false)
    @ManyToOne(optional = false)
    private Hospitalizacion hospitalizacion;
    @JoinColumn(name = "mascota", referencedColumnName = "id_mascota", nullable = false)
    @ManyToOne(optional = false)
    private Mascota mascota;
    @JoinColumn(name = "tipo_patologia", referencedColumnName = "id_tipo_patologia", nullable = false)
    @ManyToOne(optional = false)
    private TipoPatologia tipoPatologia;
    @JoinColumn(name = "veterinario", referencedColumnName = "id_veterinario", nullable = false)
    @ManyToOne(optional = false)
    private Veterinario veterinario;

    public Patologias() {
    }

    public Patologias(Integer idPatologia) {
        this.idPatologia = idPatologia;
    }

    public Patologias(Integer idPatologia, Date fechaDeteccion) {
        this.idPatologia = idPatologia;
        this.fechaDeteccion = fechaDeteccion;
    }

    public Integer getIdPatologia() {
        return idPatologia;
    }

    public void setIdPatologia(Integer idPatologia) {
        this.idPatologia = idPatologia;
    }

    public Date getFechaDeteccion() {
        return fechaDeteccion;
    }

    public void setFechaDeteccion(Date fechaDeteccion) {
        this.fechaDeteccion = fechaDeteccion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
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

    public TipoPatologia getTipoPatologia() {
        return tipoPatologia;
    }

    public void setTipoPatologia(TipoPatologia tipoPatologia) {
        this.tipoPatologia = tipoPatologia;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPatologia != null ? idPatologia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Patologias)) {
            return false;
        }
        Patologias other = (Patologias) object;
        if ((this.idPatologia == null && other.idPatologia != null) || (this.idPatologia != null && !this.idPatologia.equals(other.idPatologia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.starlabs.modelo.Patologias[ idPatologia=" + idPatologia + " ]";
    }
    
}
