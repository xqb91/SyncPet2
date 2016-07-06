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
    @NamedQuery(name = "Clinica.findAll", query = "SELECT c FROM Clinica c"),
    @NamedQuery(name = "Clinica.findByIdClinica", query = "SELECT c FROM Clinica c WHERE c.idClinica = :idClinica"),
    @NamedQuery(name = "Clinica.findByNombreFantasia", query = "SELECT c FROM Clinica c WHERE c.nombreFantasia = :nombreFantasia"),
    @NamedQuery(name = "Clinica.findByNombreReal", query = "SELECT c FROM Clinica c WHERE c.nombreReal = :nombreReal"),
    @NamedQuery(name = "Clinica.findByRut", query = "SELECT c FROM Clinica c WHERE c.rut = :rut"),
    @NamedQuery(name = "Clinica.findByDv", query = "SELECT c FROM Clinica c WHERE c.dv = :dv")})
public class Clinica implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_clinica", nullable = false)
    private Integer idClinica;
    @Basic(optional = false)
    @Column(name = "nombre_fantasia", nullable = false, length = 250)
    private String nombreFantasia;
    @Basic(optional = false)
    @Column(name = "nombre_real", nullable = false, length = 250)
    private String nombreReal;
    @Basic(optional = false)
    @Column(nullable = false)
    private int rut;
    @Basic(optional = false)
    @Column(nullable = false)
    private Character dv;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clinica")
    private List<Sucursal> sucursalList;

    public Clinica() {
    }

    public Clinica(Integer idClinica) {
        this.idClinica = idClinica;
    }

    public Clinica(Integer idClinica, String nombreFantasia, String nombreReal, int rut, Character dv) {
        this.idClinica = idClinica;
        this.nombreFantasia = nombreFantasia;
        this.nombreReal = nombreReal;
        this.rut = rut;
        this.dv = dv;
    }

    public Integer getIdClinica() {
        return idClinica;
    }

    public void setIdClinica(Integer idClinica) {
        this.idClinica = idClinica;
    }

    public String getNombreFantasia() {
        return nombreFantasia;
    }

    public void setNombreFantasia(String nombreFantasia) {
        this.nombreFantasia = nombreFantasia;
    }

    public String getNombreReal() {
        return nombreReal;
    }

    public void setNombreReal(String nombreReal) {
        this.nombreReal = nombreReal;
    }

    public int getRut() {
        return rut;
    }

    public void setRut(int rut) {
        this.rut = rut;
    }

    public Character getDv() {
        return dv;
    }

    public void setDv(Character dv) {
        this.dv = dv;
    }

    @XmlTransient
    public List<Sucursal> getSucursalList() {
        return sucursalList;
    }

    public void setSucursalList(List<Sucursal> sucursalList) {
        this.sucursalList = sucursalList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idClinica != null ? idClinica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clinica)) {
            return false;
        }
        Clinica other = (Clinica) object;
        if ((this.idClinica == null && other.idClinica != null) || (this.idClinica != null && !this.idClinica.equals(other.idClinica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.starlabs.modelo.Clinica[ idClinica=" + idClinica + " ]";
    }
    
}
