/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.controladores;

import cl.starlabs.controladores.exceptions.IllegalOrphanException;
import cl.starlabs.controladores.exceptions.NonexistentEntityException;
import cl.starlabs.controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.starlabs.modelo.Procedimientos;
import cl.starlabs.modelo.TipoProcedimiento;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor Manuel Araya
 */
public class TipoProcedimientoJpaController implements Serializable {

    public TipoProcedimientoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoProcedimiento tipoProcedimiento) throws PreexistingEntityException, Exception {
        if (tipoProcedimiento.getProcedimientosList() == null) {
            tipoProcedimiento.setProcedimientosList(new ArrayList<Procedimientos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Procedimientos> attachedProcedimientosList = new ArrayList<Procedimientos>();
            for (Procedimientos procedimientosListProcedimientosToAttach : tipoProcedimiento.getProcedimientosList()) {
                procedimientosListProcedimientosToAttach = em.getReference(procedimientosListProcedimientosToAttach.getClass(), procedimientosListProcedimientosToAttach.getIdProcedimiento());
                attachedProcedimientosList.add(procedimientosListProcedimientosToAttach);
            }
            tipoProcedimiento.setProcedimientosList(attachedProcedimientosList);
            em.persist(tipoProcedimiento);
            for (Procedimientos procedimientosListProcedimientos : tipoProcedimiento.getProcedimientosList()) {
                TipoProcedimiento oldTipoProcedimientoOfProcedimientosListProcedimientos = procedimientosListProcedimientos.getTipoProcedimiento();
                procedimientosListProcedimientos.setTipoProcedimiento(tipoProcedimiento);
                procedimientosListProcedimientos = em.merge(procedimientosListProcedimientos);
                if (oldTipoProcedimientoOfProcedimientosListProcedimientos != null) {
                    oldTipoProcedimientoOfProcedimientosListProcedimientos.getProcedimientosList().remove(procedimientosListProcedimientos);
                    oldTipoProcedimientoOfProcedimientosListProcedimientos = em.merge(oldTipoProcedimientoOfProcedimientosListProcedimientos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoProcedimiento(tipoProcedimiento.getIdTipoProcedimiento()) != null) {
                throw new PreexistingEntityException("TipoProcedimiento " + tipoProcedimiento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoProcedimiento tipoProcedimiento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoProcedimiento persistentTipoProcedimiento = em.find(TipoProcedimiento.class, tipoProcedimiento.getIdTipoProcedimiento());
            List<Procedimientos> procedimientosListOld = persistentTipoProcedimiento.getProcedimientosList();
            List<Procedimientos> procedimientosListNew = tipoProcedimiento.getProcedimientosList();
            List<String> illegalOrphanMessages = null;
            for (Procedimientos procedimientosListOldProcedimientos : procedimientosListOld) {
                if (!procedimientosListNew.contains(procedimientosListOldProcedimientos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Procedimientos " + procedimientosListOldProcedimientos + " since its tipoProcedimiento field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Procedimientos> attachedProcedimientosListNew = new ArrayList<Procedimientos>();
            for (Procedimientos procedimientosListNewProcedimientosToAttach : procedimientosListNew) {
                procedimientosListNewProcedimientosToAttach = em.getReference(procedimientosListNewProcedimientosToAttach.getClass(), procedimientosListNewProcedimientosToAttach.getIdProcedimiento());
                attachedProcedimientosListNew.add(procedimientosListNewProcedimientosToAttach);
            }
            procedimientosListNew = attachedProcedimientosListNew;
            tipoProcedimiento.setProcedimientosList(procedimientosListNew);
            tipoProcedimiento = em.merge(tipoProcedimiento);
            for (Procedimientos procedimientosListNewProcedimientos : procedimientosListNew) {
                if (!procedimientosListOld.contains(procedimientosListNewProcedimientos)) {
                    TipoProcedimiento oldTipoProcedimientoOfProcedimientosListNewProcedimientos = procedimientosListNewProcedimientos.getTipoProcedimiento();
                    procedimientosListNewProcedimientos.setTipoProcedimiento(tipoProcedimiento);
                    procedimientosListNewProcedimientos = em.merge(procedimientosListNewProcedimientos);
                    if (oldTipoProcedimientoOfProcedimientosListNewProcedimientos != null && !oldTipoProcedimientoOfProcedimientosListNewProcedimientos.equals(tipoProcedimiento)) {
                        oldTipoProcedimientoOfProcedimientosListNewProcedimientos.getProcedimientosList().remove(procedimientosListNewProcedimientos);
                        oldTipoProcedimientoOfProcedimientosListNewProcedimientos = em.merge(oldTipoProcedimientoOfProcedimientosListNewProcedimientos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoProcedimiento.getIdTipoProcedimiento();
                if (findTipoProcedimiento(id) == null) {
                    throw new NonexistentEntityException("The tipoProcedimiento with id " + id + " no longer exists.");
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
            TipoProcedimiento tipoProcedimiento;
            try {
                tipoProcedimiento = em.getReference(TipoProcedimiento.class, id);
                tipoProcedimiento.getIdTipoProcedimiento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoProcedimiento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Procedimientos> procedimientosListOrphanCheck = tipoProcedimiento.getProcedimientosList();
            for (Procedimientos procedimientosListOrphanCheckProcedimientos : procedimientosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoProcedimiento (" + tipoProcedimiento + ") cannot be destroyed since the Procedimientos " + procedimientosListOrphanCheckProcedimientos + " in its procedimientosList field has a non-nullable tipoProcedimiento field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoProcedimiento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoProcedimiento> findTipoProcedimientoEntities() {
        return findTipoProcedimientoEntities(true, -1, -1);
    }

    public List<TipoProcedimiento> findTipoProcedimientoEntities(int maxResults, int firstResult) {
        return findTipoProcedimientoEntities(false, maxResults, firstResult);
    }

    private List<TipoProcedimiento> findTipoProcedimientoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoProcedimiento.class));
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

    public TipoProcedimiento findTipoProcedimiento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoProcedimiento.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoProcedimientoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoProcedimiento> rt = cq.from(TipoProcedimiento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Integer ultimo() {
        try {
            Query consulta = getEntityManager().createNamedQuery("TipoProcedimiento.findAllDesc");
            consulta.setMaxResults(1);
            return ((TipoProcedimiento)consulta.getSingleResult()).getIdTipoProcedimiento()+1;
        } catch (Exception e) {
            return 1;
        }
    }
    
    public TipoProcedimiento buscarPorNombre(String nombre) {
        try {
            Query consulta = getEntityManager().createNamedQuery("TipoProcedimiento.findByNombreProcedimiento");
            consulta.setParameter("nombreProcedimiento", nombre);
            return (TipoProcedimiento)consulta.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    public boolean existeTipo(String nombre) {
        if(this.buscarPorNombre(nombre) == null) {
            return false;
        }else{
            return true;
        }
    }
    
}
