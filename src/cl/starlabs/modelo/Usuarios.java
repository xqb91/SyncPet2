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
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u"),
    @NamedQuery(name = "Usuarios.findAllDesc", query = "SELECT u FROM Usuarios u ORDER BY u.id DESC"),
    @NamedQuery(name = "Usuarios.findById", query = "SELECT u FROM Usuarios u WHERE u.id = :id"),
    @NamedQuery(name = "Usuarios.findByUsuario", query = "SELECT u FROM Usuarios u WHERE u.usuario = :usuario"),
    @NamedQuery(name = "Usuarios.findByContrasena", query = "SELECT u FROM Usuarios u WHERE u.contrasena = :contrasena"),
    @NamedQuery(name = "Usuarios.findByNombres", query = "SELECT u FROM Usuarios u WHERE u.nombres = :nombres"),
    @NamedQuery(name = "Usuarios.findByApaterno", query = "SELECT u FROM Usuarios u WHERE u.apaterno = :apaterno"),
    @NamedQuery(name = "Usuarios.findByAmaterno", query = "SELECT u FROM Usuarios u WHERE u.amaterno = :amaterno"),
    @NamedQuery(name = "Usuarios.findByBloqueado", query = "SELECT u FROM Usuarios u WHERE u.bloqueado = :bloqueado")})
public class Usuarios implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(nullable = false, length = 100)
    private String usuario;
    @Basic(optional = false)
    @Column(nullable = false, length = 250)
    private String contrasena;
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
    @Lob
    @Column(nullable = false, length = 2147483647)
    private String correo;
    @Basic(optional = false)
    @Column(nullable = false)
    private Character bloqueado;
    @JoinColumn(name = "perfil", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Perfiles perfil;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<DetalleUsuarios> detalleUsuariosList;

    public Usuarios() {
    }

    public Usuarios(Integer id) {
        this.id = id;
    }

    public Usuarios(Integer id, String usuario, String contrasena, String nombres, String apaterno, String amaterno, String correo, Character bloqueado) {
        this.id = id;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.nombres = nombres;
        this.apaterno = apaterno;
        this.amaterno = amaterno;
        this.correo = correo;
        this.bloqueado = bloqueado;
    }
    
    public Usuarios(Integer id, String usuario, String contrasena, String nombres, String apaterno, String amaterno, String correo, Character bloqueado, Perfiles per) {
        this.id = id;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.nombres = nombres;
        this.apaterno = apaterno;
        this.amaterno = amaterno;
        this.correo = correo;
        this.bloqueado = bloqueado;
        this.perfil = per;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Character getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(Character bloqueado) {
        this.bloqueado = bloqueado;
    }

    public Perfiles getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfiles perfil) {
        this.perfil = perfil;
    }

    @XmlTransient
    public List<DetalleUsuarios> getDetalleUsuariosList() {
        return detalleUsuariosList;
    }

    public void setDetalleUsuariosList(List<DetalleUsuarios> detalleUsuariosList) {
        this.detalleUsuariosList = detalleUsuariosList;
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
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.starlabs.modelo.Usuarios[ id=" + id + " ]";
    }
    
}
