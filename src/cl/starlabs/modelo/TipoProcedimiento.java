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
@Table(name = "Tipo_Procedimiento", catalog = "syncpet", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoProcedimiento.findAll", query = "SELECT t FROM TipoProcedimiento t"),
    @NamedQuery(name = "TipoProcedimiento.findAllDesc", query = "SELECT t FROM TipoProcedimiento t ORDER BY t.idTipoProcedimiento DESC"),
    @NamedQuery(name = "TipoProcedimiento.findByIdTipoProcedimiento", query = "SELECT t FROM TipoProcedimiento t WHERE t.idTipoProcedimiento = :idTipoProcedimiento"),
    @NamedQuery(name = "TipoProcedimiento.findByClasificacion", query = "SELECT t FROM TipoProcedimiento t WHERE t.clasificacion = :clasificacion"),
    @NamedQuery(name = "TipoProcedimiento.findByNombreProcedimiento", query = "SELECT t FROM TipoProcedimiento t WHERE t.nombreProcedimiento = :nombreProcedimiento"),
    @NamedQuery(name = "TipoProcedimiento.findByTipoProcedimiento", query = "SELECT t FROM TipoProcedimiento t WHERE t.tipoProcedimiento = :tipoProcedimiento")})
public class TipoProcedimiento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_tipo_procedimiento", nullable = false)
    private Integer idTipoProcedimiento;
    @Basic(optional = false)
    @Column(nullable = false)
    private Character clasificacion;
    @Basic(optional = false)
    @Column(name = "nombre_procedimiento", nullable = false, length = 120)
    private String nombreProcedimiento;
    @Basic(optional = false)
    @Column(name = "tipo_procedimiento", nullable = false, length = 75)
    private String tipoProcedimiento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoProcedimiento")
    private List<Procedimientos> procedimientosList;

    public TipoProcedimiento() {
    }

    public TipoProcedimiento(Integer idTipoProcedimiento) {
        this.idTipoProcedimiento = idTipoProcedimiento;
    }

    public TipoProcedimiento(Integer idTipoProcedimiento, Character clasificacion, String nombreProcedimiento, String tipoProcedimiento) {
        this.idTipoProcedimiento = idTipoProcedimiento;
        this.clasificacion = clasificacion;
        this.nombreProcedimiento = nombreProcedimiento;
        this.tipoProcedimiento = tipoProcedimiento;
    }

    public Integer getIdTipoProcedimiento() {
        return idTipoProcedimiento;
    }

    public void setIdTipoProcedimiento(Integer idTipoProcedimiento) {
        this.idTipoProcedimiento = idTipoProcedimiento;
    }

    public Character getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(Character clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getNombreProcedimiento() {
        return nombreProcedimiento;
    }

    public void setNombreProcedimiento(String nombreProcedimiento) {
        this.nombreProcedimiento = nombreProcedimiento;
    }

    public String getTipoProcedimiento() {
        return tipoProcedimiento;
    }

    public void setTipoProcedimiento(String tipoProcedimiento) {
        this.tipoProcedimiento = tipoProcedimiento;
    }

    @XmlTransient
    public List<Procedimientos> getProcedimientosList() {
        return procedimientosList;
    }

    public void setProcedimientosList(List<Procedimientos> procedimientosList) {
        this.procedimientosList = procedimientosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoProcedimiento != null ? idTipoProcedimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoProcedimiento)) {
            return false;
        }
        TipoProcedimiento other = (TipoProcedimiento) object;
        if ((this.idTipoProcedimiento == null && other.idTipoProcedimiento != null) || (this.idTipoProcedimiento != null && !this.idTipoProcedimiento.equals(other.idTipoProcedimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.starlabs.modelo.TipoProcedimiento[ idTipoProcedimiento=" + idTipoProcedimiento + " ]";
    }
    
}
