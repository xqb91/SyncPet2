/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.modelo;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Victor Manuel Araya
 */
@Entity
@Table(catalog = "syncpet", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Alergias.findAll", query = "SELECT a FROM Alergias a"),
    @NamedQuery(name = "Alergias.findAllDesc", query = "SELECT a FROM Alergias a ORDER BY a.idAlergia DESC"),
    @NamedQuery(name = "Alergias.findByIdAlergia", query = "SELECT a FROM Alergias a WHERE a.idAlergia = :idAlergia")})
public class Alergias implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_alergia", nullable = false)
    private Integer idAlergia;
    @Basic(optional = false)
    @Lob
    @Column(nullable = false, length = 2147483647)
    private String descripcion;
    @JoinColumn(name = "mascota", referencedColumnName = "id_mascota", nullable = false)
    @ManyToOne(optional = false)
    private Mascota mascota;
    @JoinColumn(name = "tipo_alergia", referencedColumnName = "id_tipo_alergia", nullable = false)
    @ManyToOne(optional = false)
    private TipoAlergia tipoAlergia;

    public Alergias() {
    }

    public Alergias(Integer idAlergia) {
        this.idAlergia = idAlergia;
    }

    public Alergias(Integer idAlergia, String descripcion) {
        this.idAlergia = idAlergia;
        this.descripcion = descripcion;
    }

    public Integer getIdAlergia() {
        return idAlergia;
    }

    public void setIdAlergia(Integer idAlergia) {
        this.idAlergia = idAlergia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public TipoAlergia getTipoAlergia() {
        return tipoAlergia;
    }

    public void setTipoAlergia(TipoAlergia tipoAlergia) {
        this.tipoAlergia = tipoAlergia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAlergia != null ? idAlergia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Alergias)) {
            return false;
        }
        Alergias other = (Alergias) object;
        if ((this.idAlergia == null && other.idAlergia != null) || (this.idAlergia != null && !this.idAlergia.equals(other.idAlergia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.starlabs.modelo.Alergias[ idAlergia=" + idAlergia + " ]";
    }
    
}
