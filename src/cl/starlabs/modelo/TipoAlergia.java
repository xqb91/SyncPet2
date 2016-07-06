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
    @NamedQuery(name = "TipoAlergia.findAll", query = "SELECT t FROM TipoAlergia t"),
    @NamedQuery(name = "TipoAlergia.findAllDesc", query = "SELECT t FROM TipoAlergia t ORDER BY t.idTipoAlergia DESC"),
    @NamedQuery(name = "TipoAlergia.findByIdTipoAlergia", query = "SELECT t FROM TipoAlergia t WHERE t.idTipoAlergia = :idTipoAlergia"),
    @NamedQuery(name = "TipoAlergia.findByNombre", query = "SELECT t FROM TipoAlergia t WHERE t.nombre = :nombre")})
public class TipoAlergia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_tipo_alergia", nullable = false)
    private Integer idTipoAlergia;
    @Basic(optional = false)
    @Column(nullable = false, length = 100)
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoAlergia")
    private List<Alergias> alergiasList;

    public TipoAlergia() {
    }

    public TipoAlergia(Integer idTipoAlergia) {
        this.idTipoAlergia = idTipoAlergia;
    }

    public TipoAlergia(Integer idTipoAlergia, String nombre) {
        this.idTipoAlergia = idTipoAlergia;
        this.nombre = nombre;
    }

    public Integer getIdTipoAlergia() {
        return idTipoAlergia;
    }

    public void setIdTipoAlergia(Integer idTipoAlergia) {
        this.idTipoAlergia = idTipoAlergia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<Alergias> getAlergiasList() {
        return alergiasList;
    }

    public void setAlergiasList(List<Alergias> alergiasList) {
        this.alergiasList = alergiasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoAlergia != null ? idTipoAlergia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoAlergia)) {
            return false;
        }
        TipoAlergia other = (TipoAlergia) object;
        if ((this.idTipoAlergia == null && other.idTipoAlergia != null) || (this.idTipoAlergia != null && !this.idTipoAlergia.equals(other.idTipoAlergia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.starlabs.modelo.TipoAlergia[ idTipoAlergia=" + idTipoAlergia + " ]";
    }
    
}
