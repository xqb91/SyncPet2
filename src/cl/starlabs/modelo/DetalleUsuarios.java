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
    @NamedQuery(name = "DetalleUsuarios.findAll", query = "SELECT d FROM DetalleUsuarios d"),
    @NamedQuery(name = "DetalleUsuarios.findAllDesc", query = "SELECT d FROM DetalleUsuarios d ORDER BY d.id DESC"),
    @NamedQuery(name = "DetalleUsuarios.findByUsuario", query = "SELECT d FROM DetalleUsuarios d WHERE d.usuario = :usuario"),
    @NamedQuery(name = "DetalleUsuarios.findByVeterinario", query = "SELECT d FROM DetalleUsuarios d WHERE d.veterinario = :veterinario"),
    @NamedQuery(name = "DetalleUsuarios.findBySucursal", query = "SELECT d FROM DetalleUsuarios d WHERE d.sucursal = :sucursal"),
    @NamedQuery(name = "DetalleUsuarios.findById", query = "SELECT d FROM DetalleUsuarios d WHERE d.id = :id")})
public class DetalleUsuarios implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @JoinColumn(name = "sucursal", referencedColumnName = "id_sucursal", nullable = false)
    @ManyToOne(optional = false)
    private Sucursal sucursal;
    @JoinColumn(name = "usuario", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Usuarios usuario;
    @JoinColumn(name = "veterinario", referencedColumnName = "id_veterinario")
    @ManyToOne
    private Veterinario veterinario;

    public DetalleUsuarios() {
    }

    public DetalleUsuarios(Integer id) {
        this.id = id;
    }
    
     public DetalleUsuarios(Integer id, Usuarios usuario, Sucursal sucursal) {
        this.id = id;
        this.usuario = usuario;
        this.sucursal = sucursal;
    }
    
     public DetalleUsuarios(Integer id, Usuarios usuario, Veterinario veterinario, Sucursal sucursal) {
        this.id = id;
        this.usuario = usuario;
        this.veterinario = veterinario;
        this.sucursal = sucursal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleUsuarios)) {
            return false;
        }
        DetalleUsuarios other = (DetalleUsuarios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.starlabs.modelo.DetalleUsuarios[ id=" + id + " ]";
    }
    
}
