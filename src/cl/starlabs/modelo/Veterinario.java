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
    @NamedQuery(name = "Veterinario.findAll", query = "SELECT v FROM Veterinario v"),
    @NamedQuery(name = "Veterinario.findAllDesc", query = "SELECT v FROM Veterinario v ORDER BY v.idVeterinario DESC"),
    @NamedQuery(name = "Veterinario.findByIdVeterinario", query = "SELECT v FROM Veterinario v WHERE v.idVeterinario = :idVeterinario"),
    @NamedQuery(name = "Veterinario.findByRut", query = "SELECT v FROM Veterinario v WHERE v.rut = :rut"),
    @NamedQuery(name = "Veterinario.findByDv", query = "SELECT v FROM Veterinario v WHERE v.dv = :dv"),
    @NamedQuery(name = "Veterinario.findByNombres", query = "SELECT v FROM Veterinario v WHERE v.nombres = :nombres"),
    @NamedQuery(name = "Veterinario.findByApaterno", query = "SELECT v FROM Veterinario v WHERE v.apaterno = :apaterno"),
    @NamedQuery(name = "Veterinario.findByAmaterno", query = "SELECT v FROM Veterinario v WHERE v.amaterno = :amaterno"),
    @NamedQuery(name = "Veterinario.findByEspecialidad", query = "SELECT v FROM Veterinario v WHERE v.especialidad = :especialidad")})
public class Veterinario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_veterinario", nullable = false)
    private Integer idVeterinario;
    @Basic(optional = false)
    @Column(nullable = false, length = 8)
    private String rut;
    @Basic(optional = false)
    @Column(nullable = false)
    private Character dv;
    @Basic(optional = false)
    @Column(nullable = false, length = 75)
    private String nombres;
    @Basic(optional = false)
    @Column(nullable = false, length = 75)
    private String apaterno;
    @Basic(optional = false)
    @Column(nullable = false, length = 75)
    private String amaterno;
    @Basic(optional = false)
    @Column(nullable = false, length = 110)
    private String especialidad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "veterinario")
    private List<Examenes> examenesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "veterinario")
    private List<Historialvacunas> historialvacunasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "veterinario")
    private List<Contraindicaciones> contraindicacionesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "especialista")
    private List<Desparacitaciones> desparacitacionesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "veterinario")
    private List<Farmacos> farmacosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "veterinario")
    private List<Procedimientos> procedimientosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "veterinario")
    private List<Hospitalizacion> hospitalizacionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "veterinario")
    private List<AgendaDetalle> agendaDetalleList;
    @OneToMany(mappedBy = "veterinario")
    private List<DetalleUsuarios> detalleUsuariosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "veterinario")
    private List<Anamnesis> anamnesisList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "veterinario")
    private List<Patologias> patologiasList;

    public Veterinario() {
    }

    public Veterinario(Integer idVeterinario) {
        this.idVeterinario = idVeterinario;
    }

    public Veterinario(Integer idVeterinario, String rut, Character dv, String nombres, String apaterno, String amaterno, String especialidad) {
        this.idVeterinario = idVeterinario;
        this.rut = rut;
        this.dv = dv;
        this.nombres = nombres;
        this.apaterno = apaterno;
        this.amaterno = amaterno;
        this.especialidad = especialidad;
    }

    public Integer getIdVeterinario() {
        return idVeterinario;
    }

    public void setIdVeterinario(Integer idVeterinario) {
        this.idVeterinario = idVeterinario;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public Character getDv() {
        return dv;
    }

    public void setDv(Character dv) {
        this.dv = dv;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApaterno() {
        return apaterno;
    }

    public void setApaterno(String apaterno) {
        this.apaterno = apaterno;
    }

    public String getAmaterno() {
        return amaterno;
    }

    public void setAmaterno(String amaterno) {
        this.amaterno = amaterno;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
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

    @XmlTransient
    public List<Hospitalizacion> getHospitalizacionList() {
        return hospitalizacionList;
    }

    public void setHospitalizacionList(List<Hospitalizacion> hospitalizacionList) {
        this.hospitalizacionList = hospitalizacionList;
    }

    @XmlTransient
    public List<AgendaDetalle> getAgendaDetalleList() {
        return agendaDetalleList;
    }

    public void setAgendaDetalleList(List<AgendaDetalle> agendaDetalleList) {
        this.agendaDetalleList = agendaDetalleList;
    }

    @XmlTransient
    public List<DetalleUsuarios> getDetalleUsuariosList() {
        return detalleUsuariosList;
    }

    public void setDetalleUsuariosList(List<DetalleUsuarios> detalleUsuariosList) {
        this.detalleUsuariosList = detalleUsuariosList;
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
        hash += (idVeterinario != null ? idVeterinario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Veterinario)) {
            return false;
        }
        Veterinario other = (Veterinario) object;
        if ((this.idVeterinario == null && other.idVeterinario != null) || (this.idVeterinario != null && !this.idVeterinario.equals(other.idVeterinario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.starlabs.modelo.Veterinario[ idVeterinario=" + idVeterinario + " ]";
    }
    
}
