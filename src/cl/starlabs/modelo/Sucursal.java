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
    @NamedQuery(name = "Sucursal.findAll", query = "SELECT s FROM Sucursal s"),
    @NamedQuery(name = "Sucursal.findAllById", query = "SELECT s FROM Sucursal s ORDER BY s.idSucursal DESC"),
    @NamedQuery(name = "Sucursal.findByIdSucursal", query = "SELECT s FROM Sucursal s WHERE s.idSucursal = :idSucursal"),
    @NamedQuery(name = "Sucursal.findByClinica", query = "SELECT s FROM Sucursal s WHERE s.clinica = :clinica"),
    @NamedQuery(name = "Sucursal.findByNombre", query = "SELECT s FROM Sucursal s WHERE s.nombre = :nombre"),
    @NamedQuery(name = "Sucursal.findByDireccion", query = "SELECT s FROM Sucursal s WHERE s.direccion = :direccion"),
    @NamedQuery(name = "Sucursal.findByTelefono", query = "SELECT s FROM Sucursal s WHERE s.telefono = :telefono")})
public class Sucursal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_sucursal", nullable = false)
    private Integer idSucursal;
    @Basic(optional = false)
    @Column(nullable = false, length = 250)
    private String nombre;
    @Basic(optional = false)
    @Column(nullable = false, length = 250)
    private String direccion;
    @Basic(optional = false)
    @Lob
    @Column(nullable = false, length = 2147483647)
    private String email;
    @Basic(optional = false)
    @Column(nullable = false)
    private int telefono;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sucursal")
    private List<Agenda> agendaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sucursal")
    private List<DetalleUsuarios> detalleUsuariosList;
    @JoinColumn(name = "clinica", referencedColumnName = "id_clinica", nullable = false)
    @ManyToOne(optional = false)
    private Clinica clinica;
    @JoinColumn(name = "comuna", referencedColumnName = "id_comuna", nullable = false)
    @ManyToOne(optional = false)
    private Comuna comuna;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sucursal")
    private List<Propietario> propietarioList;

    public Sucursal() {
    }

    public Sucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public Sucursal(Integer idSucursal, String nombre, String direccion, String email, int telefono) {
        this.idSucursal = idSucursal;
        this.nombre = nombre;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
    }
    
    public Sucursal(Integer idSucursal, String nombre, String direccion, String email, int telefono, Clinica clinica, Comuna comuna) {
        this.idSucursal = idSucursal;
        this.nombre = nombre;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        this.clinica = clinica;
        this.comuna = comuna;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    @XmlTransient
    public List<Agenda> getAgendaList() {
        return agendaList;
    }

    public void setAgendaList(List<Agenda> agendaList) {
        this.agendaList = agendaList;
    }

    @XmlTransient
    public List<DetalleUsuarios> getDetalleUsuariosList() {
        return detalleUsuariosList;
    }

    public void setDetalleUsuariosList(List<DetalleUsuarios> detalleUsuariosList) {
        this.detalleUsuariosList = detalleUsuariosList;
    }

    public Clinica getClinica() {
        return clinica;
    }

    public void setClinica(Clinica clinica) {
        this.clinica = clinica;
    }

    public Comuna getComuna() {
        return comuna;
    }

    public void setComuna(Comuna comuna) {
        this.comuna = comuna;
    }

    @XmlTransient
    public List<Propietario> getPropietarioList() {
        return propietarioList;
    }

    public void setPropietarioList(List<Propietario> propietarioList) {
        this.propietarioList = propietarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSucursal != null ? idSucursal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sucursal)) {
            return false;
        }
        Sucursal other = (Sucursal) object;
        if ((this.idSucursal == null && other.idSucursal != null) || (this.idSucursal != null && !this.idSucursal.equals(other.idSucursal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.starlabs.modelo.Sucursal[ idSucursal=" + idSucursal + " ]";
    }
    
}
