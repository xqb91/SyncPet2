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
@Table(name = "Tipo_Farmaco", catalog = "syncpet", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoFarmaco.findAll", query = "SELECT t FROM TipoFarmaco t"),
    @NamedQuery(name = "TipoFarmaco.findAllDesc", query = "SELECT t FROM TipoFarmaco t ORDER BY t.idFarmaco DESC"),
    @NamedQuery(name = "TipoFarmaco.findByIdFarmaco", query = "SELECT t FROM TipoFarmaco t WHERE t.idFarmaco = :idFarmaco"),
    @NamedQuery(name = "TipoFarmaco.findByNombreComercial", query = "SELECT t FROM TipoFarmaco t WHERE t.nombreComercial = :nombreComercial"),
    @NamedQuery(name = "TipoFarmaco.findByPrincipioActivo", query = "SELECT t FROM TipoFarmaco t WHERE t.principioActivo = :principioActivo"),
    @NamedQuery(name = "TipoFarmaco.findByViaAdministracion", query = "SELECT t FROM TipoFarmaco t WHERE t.viaAdministracion = :viaAdministracion"),
    @NamedQuery(name = "TipoFarmaco.findByCategoria", query = "SELECT t FROM TipoFarmaco t WHERE t.categoria = :categoria"),
    @NamedQuery(name = "TipoFarmaco.findByPresentacion", query = "SELECT t FROM TipoFarmaco t WHERE t.presentacion = :presentacion"),
    @NamedQuery(name = "TipoFarmaco.findByProporcion", query = "SELECT t FROM TipoFarmaco t WHERE t.proporcion = :proporcion")})
public class TipoFarmaco implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_farmaco", nullable = false)
    private Integer idFarmaco;
    @Basic(optional = false)
    @Column(name = "nombre_comercial", nullable = false, length = 250)
    private String nombreComercial;
    @Basic(optional = false)
    @Column(name = "principio_activo", nullable = false, length = 250)
    private String principioActivo;
    @Basic(optional = false)
    @Lob
    @Column(name = "interacciones_farmacologicas", nullable = false, length = 2147483647)
    private String interaccionesFarmacologicas;
    @Basic(optional = false)
    @Column(name = "via_administracion", nullable = false, length = 75)
    private String viaAdministracion;
    @Basic(optional = false)
    @Column(nullable = false, length = 120)
    private String categoria;
    @Basic(optional = false)
    @Column(nullable = false, length = 75)
    private String presentacion;
    @Basic(optional = false)
    @Column(nullable = false, length = 20)
    private String proporcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "farmaco")
    private List<Farmacos> farmacosList;

    public TipoFarmaco() {
    }

    public TipoFarmaco(Integer idFarmaco) {
        this.idFarmaco = idFarmaco;
    }

    public TipoFarmaco(Integer idFarmaco, String nombreComercial, String principioActivo, String interaccionesFarmacologicas, String viaAdministracion, String categoria, String presentacion, String proporcion) {
        this.idFarmaco = idFarmaco;
        this.nombreComercial = nombreComercial;
        this.principioActivo = principioActivo;
        this.interaccionesFarmacologicas = interaccionesFarmacologicas;
        this.viaAdministracion = viaAdministracion;
        this.categoria = categoria;
        this.presentacion = presentacion;
        this.proporcion = proporcion;
    }

    public Integer getIdFarmaco() {
        return idFarmaco;
    }

    public void setIdFarmaco(Integer idFarmaco) {
        this.idFarmaco = idFarmaco;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getPrincipioActivo() {
        return principioActivo;
    }

    public void setPrincipioActivo(String principioActivo) {
        this.principioActivo = principioActivo;
    }

    public String getInteraccionesFarmacologicas() {
        return interaccionesFarmacologicas;
    }

    public void setInteraccionesFarmacologicas(String interaccionesFarmacologicas) {
        this.interaccionesFarmacologicas = interaccionesFarmacologicas;
    }

    public String getViaAdministracion() {
        return viaAdministracion;
    }

    public void setViaAdministracion(String viaAdministracion) {
        this.viaAdministracion = viaAdministracion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public String getProporcion() {
        return proporcion;
    }

    public void setProporcion(String proporcion) {
        this.proporcion = proporcion;
    }

    @XmlTransient
    public List<Farmacos> getFarmacosList() {
        return farmacosList;
    }

    public void setFarmacosList(List<Farmacos> farmacosList) {
        this.farmacosList = farmacosList;
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
        if (!(object instanceof TipoFarmaco)) {
            return false;
        }
        TipoFarmaco other = (TipoFarmaco) object;
        if ((this.idFarmaco == null && other.idFarmaco != null) || (this.idFarmaco != null && !this.idFarmaco.equals(other.idFarmaco))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.starlabs.modelo.TipoFarmaco[ idFarmaco=" + idFarmaco + " ]";
    }
    
}
