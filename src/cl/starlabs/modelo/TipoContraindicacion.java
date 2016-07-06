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
@Table(name = "Tipo_Contraindicacion", catalog = "syncpet", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoContraindicacion.findAll", query = "SELECT t FROM TipoContraindicacion t"),
    @NamedQuery(name = "TipoContraindicacion.findAllDesc", query = "SELECT t FROM TipoContraindicacion t ORDER BY t.idTipoContraindicacion DESC"),
    @NamedQuery(name = "TipoContraindicacion.findByIdTipoContraindicacion", query = "SELECT t FROM TipoContraindicacion t WHERE t.idTipoContraindicacion = :idTipoContraindicacion"),
    @NamedQuery(name = "TipoContraindicacion.findByNombreContraindicacion", query = "SELECT t FROM TipoContraindicacion t WHERE t.nombreContraindicacion = :nombreContraindicacion"),
    @NamedQuery(name = "TipoContraindicacion.findByTipoContraindicacion", query = "SELECT t FROM TipoContraindicacion t WHERE t.tipoContraindicacion = :tipoContraindicacion")})
public class TipoContraindicacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_tipo_contraindicacion", nullable = false)
    private Integer idTipoContraindicacion;
    @Basic(optional = false)
    @Column(name = "nombre_contraindicacion", nullable = false, length = 250)
    private String nombreContraindicacion;
    @Basic(optional = false)
    @Column(name = "tipo_contraindicacion", nullable = false, length = 70)
    private String tipoContraindicacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoContraindicicacion")
    private List<Contraindicaciones> contraindicacionesList;

    public TipoContraindicacion() {
    }

    public TipoContraindicacion(Integer idTipoContraindicacion) {
        this.idTipoContraindicacion = idTipoContraindicacion;
    }

    public TipoContraindicacion(Integer idTipoContraindicacion, String nombreContraindicacion, String tipoContraindicacion) {
        this.idTipoContraindicacion = idTipoContraindicacion;
        this.nombreContraindicacion = nombreContraindicacion;
        this.tipoContraindicacion = tipoContraindicacion;
    }

    public Integer getIdTipoContraindicacion() {
        return idTipoContraindicacion;
    }

    public void setIdTipoContraindicacion(Integer idTipoContraindicacion) {
        this.idTipoContraindicacion = idTipoContraindicacion;
    }

    public String getNombreContraindicacion() {
        return nombreContraindicacion;
    }

    public void setNombreContraindicacion(String nombreContraindicacion) {
        this.nombreContraindicacion = nombreContraindicacion;
    }

    public String getTipoContraindicacion() {
        return tipoContraindicacion;
    }

    public void setTipoContraindicacion(String tipoContraindicacion) {
        this.tipoContraindicacion = tipoContraindicacion;
    }

    @XmlTransient
    public List<Contraindicaciones> getContraindicacionesList() {
        return contraindicacionesList;
    }

    public void setContraindicacionesList(List<Contraindicaciones> contraindicacionesList) {
        this.contraindicacionesList = contraindicacionesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoContraindicacion != null ? idTipoContraindicacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoContraindicacion)) {
            return false;
        }
        TipoContraindicacion other = (TipoContraindicacion) object;
        if ((this.idTipoContraindicacion == null && other.idTipoContraindicacion != null) || (this.idTipoContraindicacion != null && !this.idTipoContraindicacion.equals(other.idTipoContraindicacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.starlabs.modelo.TipoContraindicacion[ idTipoContraindicacion=" + idTipoContraindicacion + " ]";
    }
    
}
