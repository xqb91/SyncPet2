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
@Table(name = "Historial_vacunas", catalog = "syncpet", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Historialvacunas.findAll", query = "SELECT h FROM Historialvacunas h"),
    @NamedQuery(name = "Historialvacunas.findAllDesc", query = "SELECT h FROM Historialvacunas h ORDER BY h.idEvento DESC"),
    @NamedQuery(name = "Historialvacunas.findByIdEvento", query = "SELECT h FROM Historialvacunas h WHERE h.idEvento = :idEvento"),
    @NamedQuery(name = "Historialvacunas.findByFecha", query = "SELECT h FROM Historialvacunas h WHERE h.fecha = :fecha")})
public class Historialvacunas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_evento", nullable = false)
    private Integer idEvento;
    @Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Lob
    @Column(length = 2147483647)
    private String observaciones;
    @JoinColumn(name = "hospitalizacion", referencedColumnName = "id_hospitalizacion", nullable = false)
    @ManyToOne(optional = false)
    private Hospitalizacion hospitalizacion;
    @JoinColumn(name = "mascota", referencedColumnName = "id_mascota", nullable = false)
    @ManyToOne(optional = false)
    private Mascota mascota;
    @JoinColumn(name = "vacuna", referencedColumnName = "id_vacuna", nullable = false)
    @ManyToOne(optional = false)
    private Vacunas vacuna;
    @JoinColumn(name = "veterinario", referencedColumnName = "id_veterinario", nullable = false)
    @ManyToOne(optional = false)
    private Veterinario veterinario;

    public Historialvacunas() {
    }

    public Historialvacunas(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public Historialvacunas(Integer idEvento, Date fecha) {
        this.idEvento = idEvento;
        this.fecha = fecha;
    }

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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

    public Vacunas getVacuna() {
        return vacuna;
    }

    public void setVacuna(Vacunas vacuna) {
        this.vacuna = vacuna;
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
        hash += (idEvento != null ? idEvento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Historialvacunas)) {
            return false;
        }
        Historialvacunas other = (Historialvacunas) object;
        if ((this.idEvento == null && other.idEvento != null) || (this.idEvento != null && !this.idEvento.equals(other.idEvento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.starlabs.modelo.Historialvacunas[ idEvento=" + idEvento + " ]";
    }
    
}
