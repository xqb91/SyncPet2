/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.modelo;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Victor Manuel Araya
 */
@Entity
@Table(catalog = "syncpet", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contraindicaciones.findAll", query = "SELECT c FROM Contraindicaciones c"),
    @NamedQuery(name = "Contraindicaciones.findAllDesc", query = "SELECT c FROM Contraindicaciones c ORDER BY c.idContraindicacion DESC"),
    @NamedQuery(name = "Contraindicaciones.findByIdContraindicacion", query = "SELECT c FROM Contraindicaciones c WHERE c.idContraindicacion = :idContraindicacion")})
public class Contraindicaciones implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_contraindicacion", nullable = false)
    private Integer idContraindicacion;
    @Lob
    @Column(length = 2147483647)
    private String observaciones;
    @JoinColumn(name = "hospitalizacion", referencedColumnName = "id_hospitalizacion", nullable = false)
    @ManyToOne(optional = false)
    private Hospitalizacion hospitalizacion;
    @JoinColumn(name = "mascota", referencedColumnName = "id_mascota", nullable = false)
    @ManyToOne(optional = false)
    private Mascota mascota;
    @JoinColumn(name = "tipo_contraindicicacion", referencedColumnName = "id_tipo_contraindicacion", nullable = false)
    @ManyToOne(optional = false)
    private TipoContraindicacion tipoContraindicicacion;
    @JoinColumn(name = "veterinario", referencedColumnName = "id_veterinario", nullable = false)
    @ManyToOne(optional = false)
    private Veterinario veterinario;

    public Contraindicaciones() {
    }

    public Contraindicaciones(Integer idContraindicacion) {
        this.idContraindicacion = idContraindicacion;
    }

    public Integer getIdContraindicacion() {
        return idContraindicacion;
    }

    public void setIdContraindicacion(Integer idContraindicacion) {
        this.idContraindicacion = idContraindicacion;
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

    public TipoContraindicacion getTipoContraindicicacion() {
        return tipoContraindicicacion;
    }

    public void setTipoContraindicicacion(TipoContraindicacion tipoContraindicicacion) {
        this.tipoContraindicicacion = tipoContraindicicacion;
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
        hash += (idContraindicacion != null ? idContraindicacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contraindicaciones)) {
            return false;
        }
        Contraindicaciones other = (Contraindicaciones) object;
        if ((this.idContraindicacion == null && other.idContraindicacion != null) || (this.idContraindicacion != null && !this.idContraindicacion.equals(other.idContraindicacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.starlabs.modelo.Contraindicaciones[ idContraindicacion=" + idContraindicacion + " ]";
    }
    
}
