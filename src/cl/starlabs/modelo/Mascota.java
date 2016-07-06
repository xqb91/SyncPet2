/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.modelo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
    @NamedQuery(name = "Mascota.findAll", query = "SELECT m FROM Mascota m"),
    @NamedQuery(name = "Mascota.findAllDesc", query = "SELECT m FROM Mascota m ORDER BY m.idMascota DESC"),
    @NamedQuery(name = "Mascota.findByIdMascota", query = "SELECT m FROM Mascota m WHERE m.idMascota = :idMascota"),
    @NamedQuery(name = "Mascota.findByNombre", query = "SELECT m FROM Mascota m WHERE m.nombre = :nombre"),
    @NamedQuery(name = "Mascota.findByFechaNacimiento", query = "SELECT m FROM Mascota m WHERE m.fechaNacimiento = :fechaNacimiento"),
    @NamedQuery(name = "Mascota.findByDefuncion", query = "SELECT m FROM Mascota m WHERE m.defuncion = :defuncion"),
    @NamedQuery(name = "Mascota.findByPeso", query = "SELECT m FROM Mascota m WHERE m.peso = :peso"),
    @NamedQuery(name = "Mascota.findBySexo", query = "SELECT m FROM Mascota m WHERE m.sexo = :sexo"),
    @NamedQuery(name = "Mascota.findByPropietario", query = "SELECT m FROM Mascota m WHERE m.propietario = :propietario"),
    @NamedQuery(name = "Mascota.findByNumeroChip", query = "SELECT m FROM Mascota m WHERE m.numeroChip = :numeroChip"),
    @NamedQuery(name = "Mascota.findByGrupoSanguineo", query = "SELECT m FROM Mascota m WHERE m.grupoSanguineo = :grupoSanguineo")})
public class Mascota implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_mascota", nullable = false)
    private Integer idMascota;
    @Basic(optional = false)
    @Column(nullable = false, length = 75)
    private String nombre;
    @Basic(optional = false)
    @Column(name = "fecha_nacimiento", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Temporal(TemporalType.DATE)
    private Date defuncion;
    private BigInteger peso;
    @Basic(optional = false)
    @Column(nullable = false)
    private Character sexo;
    @Column(name = "numero_chip")
    private Integer numeroChip;
    @Basic(optional = false)
    @Column(name = "grupo_sanguineo", nullable = false, length = 10)
    private String grupoSanguineo;
    @Lob
    @Column(length = 2147483647)
    private String foto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mascota")
    private List<Examenes> examenesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mascota")
    private List<Historialvacunas> historialvacunasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mascota")
    private List<Contraindicaciones> contraindicacionesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mascota")
    private List<Desparacitaciones> desparacitacionesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mascota")
    private List<Alergias> alergiasList;
    @JoinColumn(name = "caracter", referencedColumnName = "id_caracter", nullable = false)
    @ManyToOne(optional = false)
    private Caracter caracter;
    @JoinColumn(name = "habitad", referencedColumnName = "id_habitad", nullable = false)
    @ManyToOne(optional = false)
    private Habitad habitad;
    @OneToMany(mappedBy = "madre")
    private List<Mascota> mascotaList;
    @JoinColumn(name = "madre", referencedColumnName = "id_mascota")
    @ManyToOne
    private Mascota madre;
    @OneToMany(mappedBy = "padre")
    private List<Mascota> mascotaList1;
    @JoinColumn(name = "padre", referencedColumnName = "id_mascota")
    @ManyToOne
    private Mascota padre;
    @JoinColumn(name = "propietario", referencedColumnName = "id_propietario", nullable = false)
    @ManyToOne(optional = false)
    private Propietario propietario;
    @JoinColumn(name = "raza", referencedColumnName = "id_raza", nullable = false)
    @ManyToOne(optional = false)
    private Raza raza;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mascota")
    private List<Farmacos> farmacosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mascota")
    private List<Procedimientos> procedimientosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mascota")
    private List<Hospitalizacion> hospitalizacionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mascota")
    private List<AgendaDetalle> agendaDetalleList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mascota")
    private List<Anamnesis> anamnesisList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mascota")
    private List<Patologias> patologiasList;

    public Mascota() {
    }

    public Mascota(Integer idMascota) {
        this.idMascota = idMascota;
    }

    public Mascota(Integer idMascota, String nombre, Date fechaNacimiento, Character sexo, String grupoSanguineo) {
        this.idMascota = idMascota;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.grupoSanguineo = grupoSanguineo;
    }
    
    public Mascota(String nombre, Date fechaNacimiento, Raza r, Caracter c, Character sexo, Integer numeroChip, Habitad h, String grupoSanguineo, Propietario pro) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.raza = r;
        this.caracter = c;
        this.sexo = sexo;
        this.numeroChip = numeroChip;
        this.habitad = h;
        this.grupoSanguineo = grupoSanguineo;
        this.propietario = pro;
    }

    public Integer getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(Integer idMascota) {
        this.idMascota = idMascota;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Date getDefuncion() {
        return defuncion;
    }

    public void setDefuncion(Date defuncion) {
        this.defuncion = defuncion;
    }

    public BigInteger getPeso() {
        return peso;
    }

    public void setPeso(BigInteger peso) {
        this.peso = peso;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public Integer getNumeroChip() {
        return numeroChip;
    }

    public void setNumeroChip(Integer numeroChip) {
        this.numeroChip = numeroChip;
    }

    public String getGrupoSanguineo() {
        return grupoSanguineo;
    }

    public void setGrupoSanguineo(String grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
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
    public List<Alergias> getAlergiasList() {
        return alergiasList;
    }

    public void setAlergiasList(List<Alergias> alergiasList) {
        this.alergiasList = alergiasList;
    }

    public Caracter getCaracter() {
        return caracter;
    }

    public void setCaracter(Caracter caracter) {
        this.caracter = caracter;
    }

    public Habitad getHabitad() {
        return habitad;
    }

    public void setHabitad(Habitad habitad) {
        this.habitad = habitad;
    }

    @XmlTransient
    public List<Mascota> getMascotaList() {
        return mascotaList;
    }

    public void setMascotaList(List<Mascota> mascotaList) {
        this.mascotaList = mascotaList;
    }

    public Mascota getMadre() {
        return madre;
    }

    public void setMadre(Mascota madre) {
        this.madre = madre;
    }

    @XmlTransient
    public List<Mascota> getMascotaList1() {
        return mascotaList1;
    }

    public void setMascotaList1(List<Mascota> mascotaList1) {
        this.mascotaList1 = mascotaList1;
    }

    public Mascota getPadre() {
        return padre;
    }

    public void setPadre(Mascota padre) {
        this.padre = padre;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    public Raza getRaza() {
        return raza;
    }

    public void setRaza(Raza raza) {
        this.raza = raza;
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
        hash += (idMascota != null ? idMascota.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mascota)) {
            return false;
        }
        Mascota other = (Mascota) object;
        if ((this.idMascota == null && other.idMascota != null) || (this.idMascota != null && !this.idMascota.equals(other.idMascota))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.starlabs.modelo.Mascota[ idMascota=" + idMascota + " ]";
    }
    
}
