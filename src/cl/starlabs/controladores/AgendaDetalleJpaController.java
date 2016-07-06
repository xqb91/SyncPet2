/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.controladores;

import cl.starlabs.controladores.exceptions.NonexistentEntityException;
import cl.starlabs.controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.starlabs.modelo.Agenda;
import cl.starlabs.modelo.AgendaDetalle;
import cl.starlabs.modelo.Mascota;
import cl.starlabs.modelo.Propietario;
import cl.starlabs.modelo.Veterinario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor Manuel Araya
 */
public class AgendaDetalleJpaController implements Serializable {

    public AgendaDetalleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AgendaDetalle agendaDetalle) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Agenda eventoAgenda = agendaDetalle.getEventoAgenda();
            if (eventoAgenda != null) {
                eventoAgenda = em.getReference(eventoAgenda.getClass(), eventoAgenda.getIdEvento());
                agendaDetalle.setEventoAgenda(eventoAgenda);
            }
            Mascota mascota = agendaDetalle.getMascota();
            if (mascota != null) {
                mascota = em.getReference(mascota.getClass(), mascota.getIdMascota());
                agendaDetalle.setMascota(mascota);
            }
            Veterinario veterinario = agendaDetalle.getVeterinario();
            if (veterinario != null) {
                veterinario = em.getReference(veterinario.getClass(), veterinario.getIdVeterinario());
                agendaDetalle.setVeterinario(veterinario);
            }
            em.persist(agendaDetalle);
            if (eventoAgenda != null) {
                eventoAgenda.getAgendaDetalleList().add(agendaDetalle);
                eventoAgenda = em.merge(eventoAgenda);
            }
            if (mascota != null) {
                mascota.getAgendaDetalleList().add(agendaDetalle);
                mascota = em.merge(mascota);
            }
            if (veterinario != null) {
                veterinario.getAgendaDetalleList().add(agendaDetalle);
                veterinario = em.merge(veterinario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAgendaDetalle(agendaDetalle.getIdDetalle()) != null) {
                throw new PreexistingEntityException("AgendaDetalle " + agendaDetalle + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AgendaDetalle agendaDetalle) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AgendaDetalle persistentAgendaDetalle = em.find(AgendaDetalle.class, agendaDetalle.getIdDetalle());
            Agenda eventoAgendaOld = persistentAgendaDetalle.getEventoAgenda();
            Agenda eventoAgendaNew = agendaDetalle.getEventoAgenda();
            Mascota mascotaOld = persistentAgendaDetalle.getMascota();
            Mascota mascotaNew = agendaDetalle.getMascota();
            Veterinario veterinarioOld = persistentAgendaDetalle.getVeterinario();
            Veterinario veterinarioNew = agendaDetalle.getVeterinario();
            if (eventoAgendaNew != null) {
                eventoAgendaNew = em.getReference(eventoAgendaNew.getClass(), eventoAgendaNew.getIdEvento());
                agendaDetalle.setEventoAgenda(eventoAgendaNew);
            }
            if (mascotaNew != null) {
                mascotaNew = em.getReference(mascotaNew.getClass(), mascotaNew.getIdMascota());
                agendaDetalle.setMascota(mascotaNew);
            }
            if (veterinarioNew != null) {
                veterinarioNew = em.getReference(veterinarioNew.getClass(), veterinarioNew.getIdVeterinario());
                agendaDetalle.setVeterinario(veterinarioNew);
            }
            agendaDetalle = em.merge(agendaDetalle);
            if (eventoAgendaOld != null && !eventoAgendaOld.equals(eventoAgendaNew)) {
                eventoAgendaOld.getAgendaDetalleList().remove(agendaDetalle);
                eventoAgendaOld = em.merge(eventoAgendaOld);
            }
            if (eventoAgendaNew != null && !eventoAgendaNew.equals(eventoAgendaOld)) {
                eventoAgendaNew.getAgendaDetalleList().add(agendaDetalle);
                eventoAgendaNew = em.merge(eventoAgendaNew);
            }
            if (mascotaOld != null && !mascotaOld.equals(mascotaNew)) {
                mascotaOld.getAgendaDetalleList().remove(agendaDetalle);
                mascotaOld = em.merge(mascotaOld);
            }
            if (mascotaNew != null && !mascotaNew.equals(mascotaOld)) {
                mascotaNew.getAgendaDetalleList().add(agendaDetalle);
                mascotaNew = em.merge(mascotaNew);
            }
            if (veterinarioOld != null && !veterinarioOld.equals(veterinarioNew)) {
                veterinarioOld.getAgendaDetalleList().remove(agendaDetalle);
                veterinarioOld = em.merge(veterinarioOld);
            }
            if (veterinarioNew != null && !veterinarioNew.equals(veterinarioOld)) {
                veterinarioNew.getAgendaDetalleList().add(agendaDetalle);
                veterinarioNew = em.merge(veterinarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = agendaDetalle.getIdDetalle();
                if (findAgendaDetalle(id) == null) {
                    throw new NonexistentEntityException("The agendaDetalle with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AgendaDetalle agendaDetalle;
            try {
                agendaDetalle = em.getReference(AgendaDetalle.class, id);
                agendaDetalle.getIdDetalle();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The agendaDetalle with id " + id + " no longer exists.", enfe);
            }
            Agenda eventoAgenda = agendaDetalle.getEventoAgenda();
            if (eventoAgenda != null) {
                eventoAgenda.getAgendaDetalleList().remove(agendaDetalle);
                eventoAgenda = em.merge(eventoAgenda);
            }
            Mascota mascota = agendaDetalle.getMascota();
            if (mascota != null) {
                mascota.getAgendaDetalleList().remove(agendaDetalle);
                mascota = em.merge(mascota);
            }
            Veterinario veterinario = agendaDetalle.getVeterinario();
            if (veterinario != null) {
                veterinario.getAgendaDetalleList().remove(agendaDetalle);
                veterinario = em.merge(veterinario);
            }
            em.remove(agendaDetalle);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AgendaDetalle> findAgendaDetalleEntities() {
        return findAgendaDetalleEntities(true, -1, -1);
    }

    public List<AgendaDetalle> findAgendaDetalleEntities(int maxResults, int firstResult) {
        return findAgendaDetalleEntities(false, maxResults, firstResult);
    }

    private List<AgendaDetalle> findAgendaDetalleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AgendaDetalle.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public AgendaDetalle findAgendaDetalle(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AgendaDetalle.class, id);
        } finally {
            em.close();
        }
    }

    public int getAgendaDetalleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AgendaDetalle> rt = cq.from(AgendaDetalle.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<AgendaDetalle> buscarPorMascota(Mascota m) {
        try {
            Query consulta = getEntityManager().createNamedQuery("AgendaDetalle.findByMascota");
            consulta.setParameter("mascota", m);
            return consulta.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
    
    public ArrayList<Agenda> buscarPorPropietario(String rut) {
        try {
            //Obteniendo propietario para realizar consultas posteriores
            PropietarioJpaController jpa = new PropietarioJpaController(emf);
            Propietario pro = jpa.buscarPorRut(rut);
            if(pro == null) {
                return null;
            }else{
                //si propietario no es null... se buscan sus mascotas
                MascotaJpaController jpb = new MascotaJpaController(emf);
                List<Mascota> mas = jpb.buscarPorPropietario(pro);
                //si el propietario no posee mascotas retorna null
                if(mas == null) {
                    return null;
                }else{
                    //de lo contrario se recorren la lista de mascotas que hayan sido agendadas
                    
                    //se crea un arraylist para almacenar los eventos
                    ArrayList<Agenda> agendaList = new ArrayList<Agenda>();
                    
                    //se recorren las mascotas desde el arreglo de mascotas retornado segun el propietario
                    for(Mascota m : mas){
                        //si esta mascota recorrida no esta vacía... entonces
                        if(m != null) {
                            //se debe recorrer cada una de las mascotas en busca de atenciones dentro del detalle de la agenda
                            for(AgendaDetalle ad : m.getAgendaDetalleList()) {
                                //si el elemento de atencion de agenda no es nulo se agrega al arraylist para retornarlo
                                if(ad.getEventoAgenda() != null) {
                                    agendaList.add(ad.getEventoAgenda());
                                }
                            }
                        }
                        //revisar la siguiente mascota ... (continuar iterando entre resultados)
                    }
                    //cuando finaliza
                    //retornando los resultados
                    return agendaList;
                }
            }
        } catch (Exception e) {
            return null;
        }
    }
    
    public Integer ultimo() {
        try {
            Query consulta = getEntityManager().createNamedQuery("AgendaDetalle.findAllDesc");
            consulta.setMaxResults(1);
            return ((AgendaDetalle)consulta.getSingleResult()).getIdDetalle()+1;
        } catch (Exception e) {
            return 1;
        }
    }
    
}
