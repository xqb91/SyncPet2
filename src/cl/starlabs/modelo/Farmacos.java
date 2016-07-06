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
    @NamedQuery(name = "Farmacos.findAll", query = "SELECT f FROM Farmacos f"),
    @NamedQuery(name = "Farmacos.findAllDesc", query = "SELECT f FROM Farmacos f ORDER BY f.idFarmaco DESC"),
    @NamedQuery(name = "Farmacos.findByIdFarmaco", query = "SELECT f FROM Farmacos f WHERE f.idFarmaco = :idFarmaco"),
    @NamedQuery(name = "Farmacos.findByFechaSuministro", query = "SELECT f FROM Farmacos f WHERE f.fechaSuministro = :fechaSuministro")})
public class Farmacos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_farmaco", nullable = false)
    private Integer idFarmaco;
    @Basic(optional = false)
    @Column(name = "fecha_suministro", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSuministro;
    @Lob
    @Column(length = 2147483647)
    private String observacion;
    @JoinColumn(name = "hospitalizacion", referencedColumnName = "id_hospitalizacion", nullable = false)
    @ManyToOne(optional = false)
    private Hospitalizacion hospitalizacion;
    @JoinColumn(name = "mascota", referencedColumnName = "id_mascota", nullable = false)
    @ManyToOne(optional = false)
    private Mascota mascota;
    @JoinColumn(name = "farmaco", referencedColumnName = "id_farmaco", nullable = false)
    @ManyToOne(optional = false)
    private TipoFarmaco farmaco;
    @JoinColumn(name = "veterinario", referencedColumnName = "id_veterinario", nullable = false)
    @ManyToOne(optional = false)
    private Veterinario veterinario;

    public Farmacos() {
    }

    public Farmacos(Integer idFarmaco) {
        this.idFarmaco = idFarmaco;
    }

    public Farmacos(Integer idFarmaco, Date fechaSuministro) {
        this.idFarmaco = idFarmaco;
        this.fechaSuministro = fechaSuministro;
    }

    public Integer getIdFarmaco() {
        return idFarmaco;
    }

    public void setIdFarmaco(Integer idFarmaco) {
        this.idFarmaco = idFarmaco;
    }

    public Date getFechaSuministro() {
        return fechaSuministro;
    }

    public void setFechaSuministro(Date fechaSuministro) {
        this.fechaSuministro = fechaSuministro;
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

    public TipoFarmaco getFarmaco() {
        return farmaco;
    }

    public void setFarmaco(TipoFarmaco farmaco) {
        this.farmaco = farmaco;
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
        hash += (idFarmaco != null ? idFarmaco.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Farmacos)) {
            return false;
        }
        Farmacos other = (Farmacos) object;
        if ((this.idFarmaco == null && other.idFarmaco != null) || (this.idFarmaco != null && !this.idFarmaco.equals(other.idFarmaco))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.starlabs.modelo.Farmacos[ idFarmaco=" + idFarmaco + " ]";
    }
    
}
