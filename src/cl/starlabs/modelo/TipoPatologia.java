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
import javax.persistence.Lob;
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
@Table(name = "Tipo_Patologia", catalog = "syncpet", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoPatologia.findAll", query = "SELECT t FROM TipoPatologia t"),
    @NamedQuery(name = "TipoPatologia.findAllDesc", query = "SELECT t FROM TipoPatologia t ORDER BY t.idTipoPatologia DESC"),
    @NamedQuery(name = "TipoPatologia.findByIdTipoPatologia", query = "SELECT t FROM TipoPatologia t WHERE t.idTipoPatologia = :idTipoPatologia"),
    @NamedQuery(name = "TipoPatologia.findByNombrePatologia", query = "SELECT t FROM TipoPatologia t WHERE t.nombrePatologia = :nombre")})
public class TipoPatologia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_tipo_patologia", nullable = false)
    private Integer idTipoPatologia;
    @Basic(optional = false)
    @Column(name = "nombre_patologia", nullable = false, length = 250)
    private String nombrePatologia;
    @Basic(optional = false)
    @Lob
    @Column(nullable = false, length = 2147483647)
    private String etiologia;
    @Basic(optional = false)
    @Lob
    @Column(nullable = false, length = 2147483647)
    private String patogenia;
    @Basic(optional = false)
    @Lob
    @Column(nullable = false, length = 2147483647)
    private String morfologia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoPatologia")
    private List<Patologias> patologiasList;

    public TipoPatologia() {
    }

    public TipoPatologia(Integer idTipoPatologia) {
        this.idTipoPatologia = idTipoPatologia;
    }

    public TipoPatologia(Integer idTipoPatologia, String nombrePatologia, String etiologia, String patogenia, String morfologia) {
        this.idTipoPatologia = idTipoPatologia;
        this.nombrePatologia = nombrePatologia;
        this.etiologia = etiologia;
        this.patogenia = patogenia;
        this.morfologia = morfologia;
    }

    public Integer getIdTipoPatologia() {
        return idTipoPatologia;
    }

    public void setIdTipoPatologia(Integer idTipoPatologia) {
        this.idTipoPatologia = idTipoPatologia;
    }

    public String getNombrePatologia() {
        return nombrePatologia;
    }

    public void setNombrePatologia(String nombrePatologia) {
        this.nombrePatologia = nombrePatologia;
    }

    public String getEtiologia() {
        return etiologia;
    }

    public void setEtiologia(String etiologia) {
        this.etiologia = etiologia;
    }

    public String getPatogenia() {
        return patogenia;
    }

    public void setPatogenia(String patogenia) {
        this.patogenia = patogenia;
    }

    public String getMorfologia() {
        return morfologia;
    }

    public void setMorfologia(String morfologia) {
        this.morfologia = morfologia;
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
        hash += (idTipoPatologia != null ? idTipoPatologia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoPatologia)) {
            return false;
        }
        TipoPatologia other = (TipoPatologia) object;
        if ((this.idTipoPatologia == null && other.idTipoPatologia != null) || (this.idTipoPatologia != null && !this.idTipoPatologia.equals(other.idTipoPatologia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.starlabs.modelo.TipoPatolog\u00eda[ idTipoPatologia=" + idTipoPatologia + " ]";
    }
    
}
