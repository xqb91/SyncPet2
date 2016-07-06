/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    @NamedQuery(name = "Agenda.findAll", query = "SELECT a FROM Agenda a"),
    @NamedQuery(name = "Agenda.findAllDesc", query = "SELECT a FROM Agenda a ORDER BY a.idEvento DESC"),
    @NamedQuery(name = "Agenda.findBetween", query = "SELECT a FROM Agenda a WHERE a.fechaEvento BETWEEN :fecInicio AND :fecTermino ORDER BY a.fechaEvento ASC"),
    @NamedQuery(name = "Agenda.findByIdEvento", query = "SELECT a FROM Agenda a WHERE a.idEvento = :idEvento"),
    @NamedQuery(name = "Agenda.findByFechaEvento", query = "SELECT a FROM Agenda a WHERE a.fechaEvento = :fechaEvento"),
    @NamedQuery(name = "Agenda.findByEstado", query = "SELECT a FROM Agenda a WHERE a.estado = :estado")})
public class Agenda implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_evento", nullable = false)
    private Integer idEvento;
    @Basic(optional = false)
    @Column(name = "fecha_evento", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEvento;
    @Basic(optional = false)
    @Column(nullable = false, length = 30)
    private String estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventoAgenda")
    private List<AgendaDetalle> agendaDetalleList;
    @JoinColumn(name = "sucursal", referencedColumnName = "id_sucursal", nullable = false)
    @ManyToOne(optional = false)
    private Sucursal sucursal;

    public Agenda() {
    }

    public Agenda(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public Agenda(Integer idEvento, Date fechaEvento, String estado) {
        this.idEvento = idEvento;
        this.fechaEvento = fechaEvento;
        this.estado = estado;
    }

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<AgendaDetalle> getAgendaDetalleList() {
        return agendaDetalleList;
    }

    public void setAgendaDetalleList(List<AgendaDetalle> agendaDetalleList) {
        this.agendaDetalleList = agendaDetalleList;
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
        hash += (idEvento != null ? idEvento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Agenda)) {
            return false;
        }
        Agenda other = (Agenda) object;
        if ((this.idEvento == null && other.idEvento != null) || (this.idEvento != null && !this.idEvento.equals(other.idEvento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.starlabs.modelo.Agenda[ idEvento=" + idEvento + " ]";
    }
    
}
