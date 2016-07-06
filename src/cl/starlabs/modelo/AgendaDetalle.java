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
@Table(name = "Agenda_Detalle", catalog = "syncpet", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AgendaDetalle.findAll", query = "SELECT a FROM AgendaDetalle a"),
    @NamedQuery(name = "AgendaDetalle.findAllDesc", query = "SELECT a FROM AgendaDetalle a ORDER BY a.idDetalle DESC"),
    @NamedQuery(name = "AgendaDetalle.findByMascota", query = "SELECT a FROM AgendaDetalle a WHERE a.mascota = :mascota"),
    @NamedQuery(name = "AgendaDetalle.findByIdDetalle", query = "SELECT a FROM AgendaDetalle a WHERE a.idDetalle = :idDetalle")})
public class AgendaDetalle implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_detalle", nullable = false)
    private Integer idDetalle;
    @JoinColumn(name = "evento_agenda", referencedColumnName = "id_evento", nullable = false)
    @ManyToOne(optional = false)
    private Agenda eventoAgenda;
    @JoinColumn(name = "mascota", referencedColumnName = "id_mascota", nullable = false)
    @ManyToOne(optional = false)
    private Mascota mascota;
    @JoinColumn(name = "veterinario", referencedColumnName = "id_veterinario", nullable = false)
    @ManyToOne(optional = false)
    private Veterinario veterinario;

    public AgendaDetalle() {
    }

    public AgendaDetalle(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Integer getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Agenda getEventoAgenda() {
        return eventoAgenda;
    }

    public void setEventoAgenda(Agenda eventoAgenda) {
        this.eventoAgenda = eventoAgenda;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
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
        hash += (idDetalle != null ? idDetalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AgendaDetalle)) {
            return false;
        }
        AgendaDetalle other = (AgendaDetalle) object;
        if ((this.idDetalle == null && other.idDetalle != null) || (this.idDetalle != null && !this.idDetalle.equals(other.idDetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.starlabs.modelo.AgendaDetalle[ idDetalle=" + idDetalle + " ]";
    }
    
}
