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
import cl.starlabs.modelo.Historialvacunas;
import cl.starlabs.modelo.Vacunas;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor Manuel Araya
 */
public class VacunasJpaController implements Serializable {

    public VacunasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vacunas vacunas) throws PreexistingEntityException, Exception {
        if (vacunas.getHistorialvacunasList() == null) {
            vacunas.setHistorialvacunasList(new ArrayList<Historialvacunas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Historialvacunas> attachedHistorialvacunasList = new ArrayList<Historialvacunas>();
            for (Historialvacunas historialvacunasListHistorialvacunasToAttach : vacunas.getHistorialvacunasList()) {
                historialvacunasListHistorialvacunasToAttach = em.getReference(historialvacunasListHistorialvacunasToAttach.getClass(), historialvacunasListHistorialvacunasToAttach.getIdEvento());
                attachedHistorialvacunasList.add(historialvacunasListHistorialvacunasToAttach);
            }
            vacunas.setHistorialvacunasList(attachedHistorialvacunasList);
            em.persist(vacunas);
            for (Historialvacunas historialvacunasListHistorialvacunas : vacunas.getHistorialvacunasList()) {
                Vacunas oldVacunaOfHistorialvacunasListHistorialvacunas = historialvacunasListHistorialvacunas.getVacuna();
                historialvacunasListHistorialvacunas.setVacuna(vacunas);
                historialvacunasListHistorialvacunas = em.merge(historialvacunasListHistorialvacunas);
                if (oldVacunaOfHistorialvacunasListHistorialvacunas != null) {
                    oldVacunaOfHistorialvacunasListHistorialvacunas.getHistorialvacunasList().remove(historialvacunasListHistorialvacunas);
                    oldVacunaOfHistorialvacunasListHistorialvacunas = em.merge(oldVacunaOfHistorialvacunasListHistorialvacunas);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVacunas(vacunas.getIdVacuna()) != null) {
                throw new PreexistingEntityException("Vacunas " + vacunas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vacunas vacunas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vacunas persistentVacunas = em.find(Vacunas.class, vacunas.getIdVacuna());
            List<Historialvacunas> historialvacunasListOld = persistentVacunas.getHistorialvacunasList();
            List<Historialvacunas> historialvacunasListNew = vacunas.getHistorialvacunasList();
            List<String> illegalOrphanMessages = null;
            for (Historialvacunas historialvacunasListOldHistorialvacunas : historialvacunasListOld) {
                if (!historialvacunasListNew.contains(historialvacunasListOldHistorialvacunas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Historialvacunas " + historialvacunasListOldHistorialvacunas + " since its vacuna field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Historialvacunas> attachedHistorialvacunasListNew = new ArrayList<Historialvacunas>();
            for (Historialvacunas historialvacunasListNewHistorialvacunasToAttach : historialvacunasListNew) {
                historialvacunasListNewHistorialvacunasToAttach = em.getReference(historialvacunasListNewHistorialvacunasToAttach.getClass(), historialvacunasListNewHistorialvacunasToAttach.getIdEvento());
                attachedHistorialvacunasListNew.add(historialvacunasListNewHistorialvacunasToAttach);
            }
            historialvacunasListNew = attachedHistorialvacunasListNew;
            vacunas.setHistorialvacunasList(historialvacunasListNew);
            vacunas = em.merge(vacunas);
            for (Historialvacunas historialvacunasListNewHistorialvacunas : historialvacunasListNew) {
                if (!historialvacunasListOld.contains(historialvacunasListNewHistorialvacunas)) {
                    Vacunas oldVacunaOfHistorialvacunasListNewHistorialvacunas = historialvacunasListNewHistorialvacunas.getVacuna();
                    historialvacunasListNewHistorialvacunas.setVacuna(vacunas);
                    historialvacunasListNewHistorialvacunas = em.merge(historialvacunasListNewHistorialvacunas);
                    if (oldVacunaOfHistorialvacunasListNewHistorialvacunas != null && !oldVacunaOfHistorialvacunasListNewHistorialvacunas.equals(vacunas)) {
                        oldVacunaOfHistorialvacunasListNewHistorialvacunas.getHistorialvacunasList().remove(historialvacunasListNewHistorialvacunas);
                        oldVacunaOfHistorialvacunasListNewHistorialvacunas = em.merge(oldVacunaOfHistorialvacunasListNewHistorialvacunas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = vacunas.getIdVacuna();
                if (findVacunas(id) == null) {
                    throw new NonexistentEntityException("The vacunas with id " + id + " no longer exists.");
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
            Vacunas vacunas;
            try {
                vacunas = em.getReference(Vacunas.class, id);
                vacunas.getIdVacuna();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vacunas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Historialvacunas> historialvacunasListOrphanCheck = vacunas.getHistorialvacunasList();
            for (Historialvacunas historialvacunasListOrphanCheckHistorialvacunas : historialvacunasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Vacunas (" + vacunas + ") cannot be destroyed since the Historialvacunas " + historialvacunasListOrphanCheckHistorialvacunas + " in its historialvacunasList field has a non-nullable vacuna field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(vacunas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Vacunas> findVacunasEntities() {
        return findVacunasEntities(true, -1, -1);
    }

    public List<Vacunas> findVacunasEntities(int maxResults, int firstResult) {
        return findVacunasEntities(false, maxResults, firstResult);
    }

    private List<Vacunas> findVacunasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vacunas.class));
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

    public Vacunas findVacunas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vacunas.class, id);
        } finally {
            em.close();
        }
    }

    public int getVacunasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vacunas> rt = cq.from(Vacunas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Integer ultimo() {
        try {
            Query consulta = getEntityManager().createNamedQuery("Vacunas.findAllDesc");
            consulta.setMaxResults(1);
            return ((Vacunas)consulta.getSingleResult()).getIdVacuna()+1;
        } catch (Exception e) {
            return 1;
        }
    }
    
    public Vacunas buscarPorNombre(String nombre) {
        try {
            Query consulta = getEntityManager().createNamedQuery("Vacunas.findByNombre");
            consulta.setParameter("nombre", nombre);
            return (Vacunas)consulta.getSingleResult();
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
