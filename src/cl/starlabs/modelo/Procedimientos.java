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
    @NamedQuery(name = "Procedimientos.findAll", query = "SELECT p FROM Procedimientos p"),
    @NamedQuery(name = "Procedimientos.findAllDesc", query = "SELECT p FROM Procedimientos p ORDER BY p.idProcedimiento DESC"),
    @NamedQuery(name = "Procedimientos.findByIdProcedimiento", query = "SELECT p FROM Procedimientos p WHERE p.idProcedimiento = :idProcedimiento"),
    @NamedQuery(name = "Procedimientos.findByFecha", query = "SELECT p FROM Procedimientos p WHERE p.fecha = :fecha")})
public class Procedimientos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_procedimiento", nullable = false)
    private Integer idProcedimiento;
    @Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
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
    @JoinColumn(name = "tipo_procedimiento", referencedColumnName = "id_tipo_procedimiento", nullable = false)
    @ManyToOne(optional = false)
    private TipoProcedimiento tipoProcedimiento;
    @JoinColumn(name = "veterinario", referencedColumnName = "id_veterinario", nullable = false)
    @ManyToOne(optional = false)
    private Veterinario veterinario;

    public Procedimientos() {
    }

    public Procedimientos(Integer idProcedimiento) {
        this.idProcedimiento = idProcedimiento;
    }

    public Procedimientos(Integer idProcedimiento, Date fecha) {
        this.idProcedimiento = idProcedimiento;
        this.fecha = fecha;
    }

    public Integer getIdProcedimiento() {
        return idProcedimiento;
    }

    public void setIdProcedimiento(Integer idProcedimiento) {
        this.idProcedimiento = idProcedimiento;
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

    public TipoProcedimiento getTipoProcedimiento() {
        return tipoProcedimiento;
    }

    public void setTipoProcedimiento(TipoProcedimiento tipoProcedimiento) {
        this.tipoProcedimiento = tipoProcedimiento;
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
        hash += (idProcedimiento != null ? idProcedimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Procedimientos)) {
            return false;
        }
        Procedimientos other = (Procedimientos) object;
        if ((this.idProcedimiento == null && other.idProcedimiento != null) || (this.idProcedimiento != null && !this.idProcedimiento.equals(other.idProcedimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.starlabs.modelo.Procedimientos[ idProcedimiento=" + idProcedimiento + " ]";
    }
    
}
