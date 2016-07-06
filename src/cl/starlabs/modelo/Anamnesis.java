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
    @NamedQuery(name = "Anamnesis.findAll", query = "SELECT a FROM Anamnesis a"),
    @NamedQuery(name = "Anamnesis.findByIdAnamnesis", query = "SELECT a FROM Anamnesis a WHERE a.idAnamnesis = :idAnamnesis"),
    @NamedQuery(name = "Anamnesis.findByFechaAnamnesis", query = "SELECT a FROM Anamnesis a WHERE a.fechaAnamnesis = :fechaAnamnesis"),
    @NamedQuery(name = "Anamnesis.findByPeso", query = "SELECT a FROM Anamnesis a WHERE a.peso = :peso"),
    @NamedQuery(name = "Anamnesis.findByTemperatura", query = "SELECT a FROM Anamnesis a WHERE a.temperatura = :temperatura"),
    @NamedQuery(name = "Anamnesis.findByFrecuenciaCardiaca", query = "SELECT a FROM Anamnesis a WHERE a.frecuenciaCardiaca = :frecuenciaCardiaca"),
    @NamedQuery(name = "Anamnesis.findByFrecuenciaRespiratoria", query = "SELECT a FROM Anamnesis a WHERE a.frecuenciaRespiratoria = :frecuenciaRespiratoria")})
public class Anamnesis implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_anamnesis", nullable = false)
    private Integer idAnamnesis;
    @Basic(optional = false)
    @Column(name = "fecha_anamnesis", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAnamnesis;
    @Basic(optional = false)
    @Column(nullable = false)
    private long peso;
    @Basic(optional = false)
    @Column(nullable = false)
    private long temperatura;
    @Basic(optional = false)
    @Lob
    @Column(name = "inspeccion_visual", nullable = false, length = 2147483647)
    private String inspeccionVisual;
    @Lob
    @Column(length = 2147483647)
    private String palpacion;
    @Lob
    @Column(length = 2147483647)
    private String percusion;
    @Basic(optional = false)
    @Lob
    @Column(nullable = false, length = 2147483647)
    private String auscultacion;
    @Lob
    @Column(length = 2147483647)
    private String olfaccion;
    @Basic(optional = false)
    @Lob
    @Column(name = "estado_conciencia", nullable = false, length = 2147483647)
    private String estadoConciencia;
    @Basic(optional = false)
    @Lob
    @Column(name = "nivel_movilidad", nullable = false, length = 2147483647)
    private String nivelMovilidad;
    @Lob
    @Column(length = 2147483647)
    private String actitud;
    @Lob
    @Column(name = "estado_nutricion", length = 2147483647)
    private String estadoNutricion;
    @Basic(optional = false)
    @Lob
    @Column(name = "grado_hidratacion", nullable = false, length = 2147483647)
    private String gradoHidratacion;
    @Basic(optional = false)
    @Column(name = "frecuencia_cardiaca", nullable = false, length = 20)
    private String frecuenciaCardiaca;
    @Basic(optional = false)
    @Column(name = "frecuencia_respiratoria", nullable = false, length = 20)
    private String frecuenciaRespiratoria;
    @JoinColumn(name = "hospitalizacion", referencedColumnName = "id_hospitalizacion", nullable = false)
    @ManyToOne(optional = false)
    private Hospitalizacion hospitalizacion;
    @JoinColumn(name = "mascota", referencedColumnName = "id_mascota", nullable = false)
    @ManyToOne(optional = false)
    private Mascota mascota;
    @JoinColumn(name = "veterinario", referencedColumnName = "id_veterinario", nullable = false)
    @ManyToOne(optional = false)
    private Veterinario veterinario;

    public Anamnesis() {
    }

    public Anamnesis(Integer idAnamnesis) {
        this.idAnamnesis = idAnamnesis;
    }

    public Anamnesis(Integer idAnamnesis, Date fechaAnamnesis, long peso, long temperatura, String inspeccionVisual, String auscultacion, String estadoConciencia, String nivelMovilidad, String gradoHidratacion, String frecuenciaCardiaca, String frecuenciaRespiratoria) {
        this.idAnamnesis = idAnamnesis;
        this.fechaAnamnesis = fechaAnamnesis;
        this.peso = peso;
        this.temperatura = temperatura;
        this.inspeccionVisual = inspeccionVisual;
        this.auscultacion = auscultacion;
        this.estadoConciencia = estadoConciencia;
        this.nivelMovilidad = nivelMovilidad;
        this.gradoHidratacion = gradoHidratacion;
        this.frecuenciaCardiaca = frecuenciaCardiaca;
        this.frecuenciaRespiratoria = frecuenciaRespiratoria;
    }

    public Integer getIdAnamnesis() {
        return idAnamnesis;
    }

    public void setIdAnamnesis(Integer idAnamnesis) {
        this.idAnamnesis = idAnamnesis;
    }

    public Date getFechaAnamnesis() {
        return fechaAnamnesis;
    }

    public void setFechaAnamnesis(Date fechaAnamnesis) {
        this.fechaAnamnesis = fechaAnamnesis;
    }

    public long getPeso() {
        return peso;
    }

    public void setPeso(long peso) {
        this.peso = peso;
    }

    public long getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(long temperatura) {
        this.temperatura = temperatura;
    }

    public String getInspeccionVisual() {
        return inspeccionVisual;
    }

    public void setInspeccionVisual(String inspeccionVisual) {
        this.inspeccionVisual = inspeccionVisual;
    }

    public String getPalpacion() {
        return palpacion;
    }

    public void setPalpacion(String palpacion) {
        this.palpacion = palpacion;
    }

    public String getPercusion() {
        return percusion;
    }

    public void setPercusion(String percusion) {
        this.percusion = percusion;
    }

    public String getAuscultacion() {
        return auscultacion;
    }

    public void setAuscultacion(String auscultacion) {
        this.auscultacion = auscultacion;
    }

    public String getOlfaccion() {
        return olfaccion;
    }

    public void setOlfaccion(String olfaccion) {
        this.olfaccion = olfaccion;
    }

    public String getEstadoConciencia() {
        return estadoConciencia;
    }

    public void setEstadoConciencia(String estadoConciencia) {
        this.estadoConciencia = estadoConciencia;
    }

    public String getNivelMovilidad() {
        return nivelMovilidad;
    }

    public void setNivelMovilidad(String nivelMovilidad) {
        this.nivelMovilidad = nivelMovilidad;
    }

    public String getActitud() {
        return actitud;
    }

    public void setActitud(String actitud) {
        this.actitud = actitud;
    }

    public String getEstadoNutricion() {
        return estadoNutricion;
    }

    public void setEstadoNutricion(String estadoNutricion) {
        this.estadoNutricion = estadoNutricion;
    }

    public String getGradoHidratacion() {
        return gradoHidratacion;
    }

    public void setGradoHidratacion(String gradoHidratacion) {
        this.gradoHidratacion = gradoHidratacion;
    }

    public String getFrecuenciaCardiaca() {
        return frecuenciaCardiaca;
    }

    public void setFrecuenciaCardiaca(String frecuenciaCardiaca) {
        this.frecuenciaCardiaca = frecuenciaCardiaca;
    }

    public String getFrecuenciaRespiratoria() {
        return frecuenciaRespiratoria;
    }

    public void setFrecuenciaRespiratoria(String frecuenciaRespiratoria) {
        this.frecuenciaRespiratoria = frecuenciaRespiratoria;
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

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAnamnesis != null ? idAnamnesis.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Anamnesis)) {
            return false;
        }
        Anamnesis other = (Anamnesis) object;
        if ((this.idAnamnesis == null && other.idAnamnesis != null) || (this.idAnamnesis != null && !this.idAnamnesis.equals(other.idAnamnesis))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.starlabs.modelo.Anamnesis[ idAnamnesis=" + idAnamnesis + " ]";
    }
    
}
