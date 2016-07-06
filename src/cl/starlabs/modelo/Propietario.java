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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
    @NamedQuery(name = "Propietario.findAll", query = "SELECT p FROM Propietario p ORDER BY p.idPropietario DESC"),
    @NamedQuery(name = "Propietario.findByIdPropietario", query = "SELECT p FROM Propietario p WHERE p.idPropietario = :idPropietario"),
    @NamedQuery(name = "Propietario.findByNombres", query = "SELECT p FROM Propietario p WHERE p.nombres = :nombres"),
    @NamedQuery(name = "Propietario.findByApaterno", query = "SELECT p FROM Propietario p WHERE p.apaterno = :apaterno"),
    @NamedQuery(name = "Propietario.findByAmaterno", query = "SELECT p FROM Propietario p WHERE p.amaterno = :amaterno"),
    @NamedQuery(name = "Propietario.findByCorreo", query = "SELECT p FROM Propietario p WHERE p.email = :email"),
    @NamedQuery(name = "Propietario.findByRut", query = "SELECT p FROM Propietario p WHERE p.rut = :rut"),
    @NamedQuery(name = "Propietario.findByDv", query = "SELECT p FROM Propietario p WHERE p.dv = :dv"),
    @NamedQuery(name = "Propietario.findByDireccion", query = "SELECT p FROM Propietario p WHERE p.direccion = :direccion"),
    @NamedQuery(name = "Propietario.findByTelefono", query = "SELECT p FROM Propietario p WHERE p.telefono = :telefono"),
    @NamedQuery(name = "Propietario.findByClinica", query = "SELECT p FROM Propietario p WHERE p.sucursal.clinica = :clinica"),
    @NamedQuery(name = "Propietario.findByCelular", query = "SELECT p FROM Propietario p WHERE p.celular = :celular")})
public class Propietario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_propietario", nullable = false)
    private Integer idPropietario;
    @Basic(optional = false)
    @Column(nullable = false, length = 75)
    private String nombres;
    @Basic(optional = false)
    @Column(nullable = false, length = 75)
    private String apaterno;
    @Basic(optional = false)
    @Column(nullable = false, length = 75)
    private String amaterno;
    @Basic(optional = false)
    @Column(nullable = false)
    private int rut;
    @Basic(optional = false)
    @Column(nullable = false)
    private Character dv;
    @Basic(optional = false)
    @Column(nullable = false, length = 250)
    private String direccion;
    @Lob
    @Column(length = 2147483647)
    private String email;
    private Integer telefono;
    private Integer celular;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "propietario")
    private List<Mascota> mascotaList;
    @JoinColumn(name = "comuna", referencedColumnName = "id_comuna", nullable = false)
    @ManyToOne(optional = false)
    private Comuna comuna;
    @JoinColumn(name = "sucursal", referencedColumnName = "id_sucursal", nullable = false)
    @ManyToOne(optional = false)
    private Sucursal sucursal;

    public Propietario() {
    }

    public Propietario(Integer idPropietario) {
        this.idPropietario = idPropietario;
    }

    public Propietario(Integer idPropietario, String nombres, String apaterno, String amaterno, int rut, Character dv, String direccion) {
        this.idPropietario = idPropietario;
        this.nombres = nombres;
        this.apaterno = apaterno;
        this.amaterno = amaterno;
        this.rut = rut;
        this.dv = dv;
        this.direccion = direccion;
    }

    public Integer getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(Integer idPropietario) {
        this.idPropietario = idPropietario;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApaterno() {
        return apaterno;
    }

    public void setApaterno(String apaterno) {
        this.apaterno = apaterno;
    }

    public String getAmaterno() {
        return amaterno;
    }

    public void setAmaterno(String amaterno) {
        this.amaterno = amaterno;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public Integer getCelular() {
        return celular;
    }

    public void setCelular(Integer celular) {
        this.celular = celular;
    }

    @XmlTransient
    public List<Mascota> getMascotaList() {
        return mascotaList;
    }

    public void setMascotaList(List<Mascota> mascotaList) {
        this.mascotaList = mascotaList;
    }

    public Comuna getComuna() {
        return comuna;
    }

    public void setComuna(Comuna comuna) {
        this.comuna = comuna;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPropietario != null ? idPropietario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Propietario)) {
            return false;
        }
        Propietario other = (Propietario) object;
        if ((this.idPropietario == null && other.idPropietario != null) || (this.idPropietario != null && !this.idPropietario.equals(other.idPropietario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.starlabs.modelo.Propietario[ idPropietario=" + idPropietario + " ]";
    }
    
}
