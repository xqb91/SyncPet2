/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.modelo;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    @NamedQuery(name = "Hospitalizacion.findAll", query = "SELECT h FROM Hospitalizacion h"),
    @NamedQuery(name = "Hospitalizacion.findByIdHospitalizacion", query = "SELECT h FROM Hospitalizacion h WHERE h.idHospitalizacion = :idHospitalizacion"),
    @NamedQuery(name = "Hospitalizacion.findByFechaIngreso", query = "SELECT h FROM Hospitalizacion h WHERE h.fechaIngreso = :fechaIngreso"),
    @NamedQuery(name = "Hospitalizacion.findByFechaAltaMedica", query = "SELECT h FROM Hospitalizacion h WHERE h.fechaAltaMedica = :fechaAltaMedica")})
public class Hospitalizacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_hospitalizacion", nullable = false)
    private Integer idHospitalizacion;
    @Basic(optional = false)
    @Column(name = "fecha_ingreso", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIngreso;
    @Column(name = "fecha_alta_medica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAltaMedica;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hospitalizacion")
    private List<Examenes> examenesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hospitalizacion")
    private List<Historialvacunas> historialvacunasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hospitalizacion")
    private List<Contraindicaciones> contraindicacionesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hospitalizacion")
    private List<Desparacitaciones> desparacitacionesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hospitalizacion")
    private List<Farmacos> farmacosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hospitalizacion")
    private List<Procedimientos> procedimientosList;
    @JoinColumn(name = "mascota", referencedColumnName = "id_mascota", nullable = false)
    @ManyToOne(optional = false)
    private Mascota mascota;
    @JoinColumn(name = "veterinario", referencedColumnName = "id_veterinario", nullable = false)
    @ManyToOne(optional = false)
    private Veterinario veterinario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hospitalizacion")
    private List<Anamnesis> anamnesisList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hospitalizacion")
    private List<Patologias> patologiasList;

    public Hospitalizacion() {
    }

    public Hospitalizacion(Integer idHospitalizacion) {
        this.idHospitalizacion = idHospitalizacion;
    }

    public Hospitalizacion(Integer idHospitalizacion, Date fechaIngreso) {
        this.idHospitalizacion = idHospitalizacion;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getIdHospitalizacion() {
        return idHospitalizacion;
    }

    public void setIdHospitalizacion(Integer idHospitalizacion) {
        this.idHospitalizacion = idHospitalizacion;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaAltaMedica() {
        return fechaAltaMedica;
    }

    public void setFechaAltaMedica(Date fechaAltaMedica) {
        this.fechaAltaMedica = fechaAltaMedica;
    }

    @XmlTransient
    public List<Examenes> getExamenesList() {
        return examenesList;
    }

    public void setExamenesList(List<Examenes> examenesList) {
        this.examenesList = examenesList;
    }

    @XmlTransient
    public List<Historialvacunas> getHistorialvacunasList() {
        return historialvacunasList;
    }

    public void setHistorialvacunasList(List<Historialvacunas> historialvacunasList) {
        this.historialvacunasList = historialvacunasList;
    }

    @XmlTransient
    public List<Contraindicaciones> getContraindicacionesList() {
        return contraindicacionesList;
    }

    public void setContraindicacionesList(List<Contraindicaciones> contraindicacionesList) {
        this.contraindicacionesList = contraindicacionesList;
    }

    @XmlTransient
    public List<Desparacitaciones> getDesparacitacionesList() {
        return desparacitacionesList;
    }

    public void setDesparacitacionesList(List<Desparacitaciones> desparacitacionesList) {
        this.desparacitacionesList = desparacitacionesList;
    }

    @XmlTransient
    public List<Farmacos> getFarmacosList() {
        return farmacosList;
    }

    public void setFarmacosList(List<Farmacos> farmacosList) {
        this.farmacosList = farmacosList;
    }

    @XmlTransient
    public List<Procedimientos> getProcedimientosList() {
        return procedimientosList;
    }

    public void setProcedimientosList(List<Procedimientos> procedimientosList) {
        this.procedimientosList = procedimientosList;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }

    @XmlTransient
    public List<Anamnesis> getAnamnesisList() {
        return anamnesisList;
    }

    public void setAnamnesisList(List<Anamnesis> anamnesisList) {
        this.anamnesisList = anamnesisList;
    }

    @XmlTransient
    public List<Patologias> getPatologiasList() {
        return patologiasList;
    }

    public void setPatologiasList(List<Patologias> patologiasList) {
        this.patologiasList = patologiasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHospitalizacion != null ? idHospitalizacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Hospitalizacion)) {
            return false;
        }
        Hospitalizacion other = (Hospitalizacion) object;
        if ((this.idHospitalizacion == null && other.idHospitalizacion != null) || (this.idHospitalizacion != null && !this.idHospitalizacion.equals(other.idHospitalizacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.starlabs.modelo.Hospitalizacion[ idHospitalizacion=" + idHospitalizacion + " ]";
    }
    
}
