/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.controladores;

import cl.starlabs.controladores.exceptions.IllegalOrphanException;
import cl.starlabs.controladores.exceptions.NonexistentEntityException;
import cl.starlabs.controladores.exceptions.PreexistingEntityException;
import cl.starlabs.modelo.Agenda;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.starlabs.modelo.Sucursal;
import cl.starlabs.modelo.AgendaDetalle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor Manuel Araya
 */
public class AgendaJpaController implements Serializable {

    public AgendaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Agenda agenda) throws PreexistingEntityException, Exception {
        if (agenda.getAgendaDetalleList() == null) {
            agenda.setAgendaDetalleList(new ArrayList<AgendaDetalle>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sucursal sucursal = agenda.getSucursal();
            if (sucursal != null) {
                sucursal = em.getReference(sucursal.getClass(), sucursal.getIdSucursal());
                agenda.setSucursal(sucursal);
            }
            List<AgendaDetalle> attachedAgendaDetalleList = new ArrayList<AgendaDetalle>();
            for (AgendaDetalle agendaDetalleListAgendaDetalleToAttach : agenda.getAgendaDetalleList()) {
                agendaDetalleListAgendaDetalleToAttach = em.getReference(agendaDetalleListAgendaDetalleToAttach.getClass(), agendaDetalleListAgendaDetalleToAttach.getIdDetalle());
                attachedAgendaDetalleList.add(agendaDetalleListAgendaDetalleToAttach);
            }
            agenda.setAgendaDetalleList(attachedAgendaDetalleList);
            em.persist(agenda);
            if (sucursal != null) {
                sucursal.getAgendaList().add(agenda);
                sucursal = em.merge(sucursal);
            }
            for (AgendaDetalle agendaDetalleListAgendaDetalle : agenda.getAgendaDetalleList()) {
                Agenda oldEventoAgendaOfAgendaDetalleListAgendaDetalle = agendaDetalleListAgendaDetalle.getEventoAgenda();
                agendaDetalleListAgendaDetalle.setEventoAgenda(agenda);
                agendaDetalleListAgendaDetalle = em.merge(agendaDetalleListAgendaDetalle);
                if (oldEventoAgendaOfAgendaDetalleListAgendaDetalle != null) {
                    oldEventoAgendaOfAgendaDetalleListAgendaDetalle.getAgendaDetalleList().remove(agendaDetalleListAgendaDetalle);
                    oldEventoAgendaOfAgendaDetalleListAgendaDetalle = em.merge(oldEventoAgendaOfAgendaDetalleListAgendaDetalle);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAgenda(agenda.getIdEvento()) != null) {
                throw new PreexistingEntityException("Agenda " + agenda + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Agenda agenda) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Agenda persistentAgenda = em.find(Agenda.class, agenda.getIdEvento());
            Sucursal sucursalOld = persistentAgenda.getSucursal();
            Sucursal sucursalNew = agenda.getSucursal();
            List<AgendaDetalle> agendaDetalleListOld = persistentAgenda.getAgendaDetalleList();
            List<AgendaDetalle> agendaDetalleListNew = agenda.getAgendaDetalleList();
            List<String> illegalOrphanMessages = null;
            for (AgendaDetalle agendaDetalleListOldAgendaDetalle : agendaDetalleListOld) {
                if (!agendaDetalleListNew.contains(agendaDetalleListOldAgendaDetalle)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AgendaDetalle " + agendaDetalleListOldAgendaDetalle + " since its eventoAgenda field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sucursalNew != null) {
                sucursalNew = em.getReference(sucursalNew.getClass(), sucursalNew.getIdSucursal());
                agenda.setSucursal(sucursalNew);
            }
            List<AgendaDetalle> attachedAgendaDetalleListNew = new ArrayList<AgendaDetalle>();
            for (AgendaDetalle agendaDetalleListNewAgendaDetalleToAttach : agendaDetalleListNew) {
                agendaDetalleListNewAgendaDetalleToAttach = em.getReference(agendaDetalleListNewAgendaDetalleToAttach.getClass(), agendaDetalleListNewAgendaDetalleToAttach.getIdDetalle());
                attachedAgendaDetalleListNew.add(agendaDetalleListNewAgendaDetalleToAttach);
            }
            agendaDetalleListNew = attachedAgendaDetalleListNew;
            agenda.setAgendaDetalleList(agendaDetalleListNew);
            agenda = em.merge(agenda);
            if (sucursalOld != null && !sucursalOld.equals(sucursalNew)) {
                sucursalOld.getAgendaList().remove(agenda);
                sucursalOld = em.merge(sucursalOld);
            }
            if (sucursalNew != null && !sucursalNew.equals(sucursalOld)) {
                sucursalNew.getAgendaList().add(agenda);
                sucursalNew = em.merge(sucursalNew);
            }
            for (AgendaDetalle agendaDetalleListNewAgendaDetalle : agendaDetalleListNew) {
                if (!agendaDetalleListOld.contains(agendaDetalleListNewAgendaDetalle)) {
                    Agenda oldEventoAgendaOfAgendaDetalleListNewAgendaDetalle = agendaDetalleListNewAgendaDetalle.getEventoAgenda();
                    agendaDetalleListNewAgendaDetalle.setEventoAgenda(agenda);
                    agendaDetalleListNewAgendaDetalle = em.merge(agendaDetalleListNewAgendaDetalle);
                    if (oldEventoAgendaOfAgendaDetalleListNewAgendaDetalle != null && !oldEventoAgendaOfAgendaDetalleListNewAgendaDetalle.equals(agenda)) {
                        oldEventoAgendaOfAgendaDetalleListNewAgendaDetalle.getAgendaDetalleList().remove(agendaDetalleListNewAgendaDetalle);
                        oldEventoAgendaOfAgendaDetalleListNewAgendaDetalle = em.merge(oldEventoAgendaOfAgendaDetalleListNewAgendaDetalle);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = agenda.getIdEvento();
                if (findAgenda(id) == null) {
                    throw new NonexistentEntityException("The agenda with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Agenda agenda;
            try {
                agenda = em.getReference(Agenda.class, id);
                agenda.getIdEvento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The agenda with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<AgendaDetalle> agendaDetalleListOrphanCheck = agenda.getAgendaDetalleList();
            for (AgendaDetalle agendaDetalleListOrphanCheckAgendaDetalle : agendaDetalleListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Agenda (" + agenda + ") cannot be destroyed since the AgendaDetalle " + agendaDetalleListOrphanCheckAgendaDetalle + " in its agendaDetalleList field has a non-nullable eventoAgenda field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Sucursal sucursal = agenda.getSucursal();
            if (sucursal != null) {
                sucursal.getAgendaList().remove(agenda);
                sucursal = em.merge(sucursal);
            }
            em.remove(agenda);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Agenda> findAgendaEntities() {
        return findAgendaEntities(true, -1, -1);
    }

    public List<Agenda> findAgendaEntities(int maxResults, int firstResult) {
        return findAgendaEntities(false, maxResults, firstResult);
    }

    private List<Agenda> findAgendaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Agenda.class));
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

    public Agenda findAgenda(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Agenda.class, id);
        } finally {
            em.close();
        }
    }

    public int getAgendaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Agenda> rt = cq.from(Agenda.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Agenda> eventosPorFecha(Date fechaInicio, Date fechaTermino) {
        try {
            Query consulta = getEntityManager().createNamedQuery("Agenda.findBetween");
            consulta.setParameter("fecInicio", fechaInicio);
            consulta.setParameter("fecTermino", fechaTermino);
            return consulta.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<Agenda> eventos(Date fecha) {
        try {
            Query consulta = getEntityManager().createNamedQuery("Agenda.findByFechaEvento");
            consulta.setParameter("fechaEvento", fecha);
            List<Agenda> resultados = consulta.getResultList();
            return resultados;
        } catch (Exception e) {
            return null;
        }
    }
    
    public Integer ultimo() {
        try {
            Query consulta = getEntityManager().createNamedQuery("Agenda.findAllDesc");
            consulta.setMaxResults(1);
            return ((Agenda)consulta.getSingleResult()).getIdEvento()+1;
        } catch (Exception e) {
            return 1;
        }
    }
    
    
}
