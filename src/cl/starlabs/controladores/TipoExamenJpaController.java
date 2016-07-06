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
import cl.starlabs.modelo.Examenes;
import cl.starlabs.modelo.TipoExamen;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor Manuel Araya
 */
public class TipoExamenJpaController implements Serializable {

    public TipoExamenJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoExamen tipoExamen) throws PreexistingEntityException, Exception {
        if (tipoExamen.getExamenesList() == null) {
            tipoExamen.setExamenesList(new ArrayList<Examenes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Examenes> attachedExamenesList = new ArrayList<Examenes>();
            for (Examenes examenesListExamenesToAttach : tipoExamen.getExamenesList()) {
                examenesListExamenesToAttach = em.getReference(examenesListExamenesToAttach.getClass(), examenesListExamenesToAttach.getIdExamen());
                attachedExamenesList.add(examenesListExamenesToAttach);
            }
            tipoExamen.setExamenesList(attachedExamenesList);
            em.persist(tipoExamen);
            for (Examenes examenesListExamenes : tipoExamen.getExamenesList()) {
                TipoExamen oldTipoExamenOfExamenesListExamenes = examenesListExamenes.getTipoExamen();
                examenesListExamenes.setTipoExamen(tipoExamen);
                examenesListExamenes = em.merge(examenesListExamenes);
                if (oldTipoExamenOfExamenesListExamenes != null) {
                    oldTipoExamenOfExamenesListExamenes.getExamenesList().remove(examenesListExamenes);
                    oldTipoExamenOfExamenesListExamenes = em.merge(oldTipoExamenOfExamenesListExamenes);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoExamen(tipoExamen.getIdTipoExamen()) != null) {
                throw new PreexistingEntityException("TipoExamen " + tipoExamen + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoExamen tipoExamen) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoExamen persistentTipoExamen = em.find(TipoExamen.class, tipoExamen.getIdTipoExamen());
            List<Examenes> examenesListOld = persistentTipoExamen.getExamenesList();
            List<Examenes> examenesListNew = tipoExamen.getExamenesList();
            List<String> illegalOrphanMessages = null;
            for (Examenes examenesListOldExamenes : examenesListOld) {
                if (!examenesListNew.contains(examenesListOldExamenes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Examenes " + examenesListOldExamenes + " since its tipoExamen field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Examenes> attachedExamenesListNew = new ArrayList<Examenes>();
            for (Examenes examenesListNewExamenesToAttach : examenesListNew) {
                examenesListNewExamenesToAttach = em.getReference(examenesListNewExamenesToAttach.getClass(), examenesListNewExamenesToAttach.getIdExamen());
                attachedExamenesListNew.add(examenesListNewExamenesToAttach);
            }
            examenesListNew = attachedExamenesListNew;
            tipoExamen.setExamenesList(examenesListNew);
            tipoExamen = em.merge(tipoExamen);
            for (Examenes examenesListNewExamenes : examenesListNew) {
                if (!examenesListOld.contains(examenesListNewExamenes)) {
                    TipoExamen oldTipoExamenOfExamenesListNewExamenes = examenesListNewExamenes.getTipoExamen();
                    examenesListNewExamenes.setTipoExamen(tipoExamen);
                    examenesListNewExamenes = em.merge(examenesListNewExamenes);
                    if (oldTipoExamenOfExamenesListNewExamenes != null && !oldTipoExamenOfExamenesListNewExamenes.equals(tipoExamen)) {
                        oldTipoExamenOfExamenesListNewExamenes.getExamenesList().remove(examenesListNewExamenes);
                        oldTipoExamenOfExamenesListNewExamenes = em.merge(oldTipoExamenOfExamenesListNewExamenes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoExamen.getIdTipoExamen();
                if (findTipoExamen(id) == null) {
                    throw new NonexistentEntityException("The tipoExamen with id " + id + " no longer exists.");
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
            TipoExamen tipoExamen;
            try {
                tipoExamen = em.getReference(TipoExamen.class, id);
                tipoExamen.getIdTipoExamen();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoExamen with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Examenes> examenesListOrphanCheck = tipoExamen.getExamenesList();
            for (Examenes examenesListOrphanCheckExamenes : examenesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoExamen (" + tipoExamen + ") cannot be destroyed since the Examenes " + examenesListOrphanCheckExamenes + " in its examenesList field has a non-nullable tipoExamen field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoExamen);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoExamen> findTipoExamenEntities() {
        return findTipoExamenEntities(true, -1, -1);
    }

    public List<TipoExamen> findTipoExamenEntities(int maxResults, int firstResult) {
        return findTipoExamenEntities(false, maxResults, firstResult);
    }

    private List<TipoExamen> findTipoExamenEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoExamen.class));
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

    public TipoExamen findTipoExamen(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoExamen.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoExamenCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoExamen> rt = cq.from(TipoExamen.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Integer ultimo() {
        try {
            Query consulta = getEntityManager().createNamedQuery("TipoExamen.findAllDesc");
            consulta.setMaxResults(1);
            return ((TipoExamen)consulta.getSingleResult()).getIdTipoExamen()+1;
        } catch (Exception e) {
            return 1;
        }
    }
    
    public TipoExamen buscarPorNombre(String nombre) {
        try {
            Query consulta = getEntityManager().createNamedQuery("TipoExamen.findByNombreExamen");
            consulta.setParameter("nombreExamen", nombre);
            return (TipoExamen)consulta.getSingleResult();
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
