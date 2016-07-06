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
import cl.starlabs.modelo.Contraindicaciones;
import cl.starlabs.modelo.TipoContraindicacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor Manuel Araya
 */
public class TipoContraindicacionJpaController implements Serializable {

    public TipoContraindicacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoContraindicacion tipoContraindicacion) throws PreexistingEntityException, Exception {
        if (tipoContraindicacion.getContraindicacionesList() == null) {
            tipoContraindicacion.setContraindicacionesList(new ArrayList<Contraindicaciones>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Contraindicaciones> attachedContraindicacionesList = new ArrayList<Contraindicaciones>();
            for (Contraindicaciones contraindicacionesListContraindicacionesToAttach : tipoContraindicacion.getContraindicacionesList()) {
                contraindicacionesListContraindicacionesToAttach = em.getReference(contraindicacionesListContraindicacionesToAttach.getClass(), contraindicacionesListContraindicacionesToAttach.getIdContraindicacion());
                attachedContraindicacionesList.add(contraindicacionesListContraindicacionesToAttach);
            }
            tipoContraindicacion.setContraindicacionesList(attachedContraindicacionesList);
            em.persist(tipoContraindicacion);
            for (Contraindicaciones contraindicacionesListContraindicaciones : tipoContraindicacion.getContraindicacionesList()) {
                TipoContraindicacion oldTipoContraindicicacionOfContraindicacionesListContraindicaciones = contraindicacionesListContraindicaciones.getTipoContraindicicacion();
                contraindicacionesListContraindicaciones.setTipoContraindicicacion(tipoContraindicacion);
                contraindicacionesListContraindicaciones = em.merge(contraindicacionesListContraindicaciones);
                if (oldTipoContraindicicacionOfContraindicacionesListContraindicaciones != null) {
                    oldTipoContraindicicacionOfContraindicacionesListContraindicaciones.getContraindicacionesList().remove(contraindicacionesListContraindicaciones);
                    oldTipoContraindicicacionOfContraindicacionesListContraindicaciones = em.merge(oldTipoContraindicicacionOfContraindicacionesListContraindicaciones);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoContraindicacion(tipoContraindicacion.getIdTipoContraindicacion()) != null) {
                throw new PreexistingEntityException("TipoContraindicacion " + tipoContraindicacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoContraindicacion tipoContraindicacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoContraindicacion persistentTipoContraindicacion = em.find(TipoContraindicacion.class, tipoContraindicacion.getIdTipoContraindicacion());
            List<Contraindicaciones> contraindicacionesListOld = persistentTipoContraindicacion.getContraindicacionesList();
            List<Contraindicaciones> contraindicacionesListNew = tipoContraindicacion.getContraindicacionesList();
            List<String> illegalOrphanMessages = null;
            for (Contraindicaciones contraindicacionesListOldContraindicaciones : contraindicacionesListOld) {
                if (!contraindicacionesListNew.contains(contraindicacionesListOldContraindicaciones)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Contraindicaciones " + contraindicacionesListOldContraindicaciones + " since its tipoContraindicicacion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Contraindicaciones> attachedContraindicacionesListNew = new ArrayList<Contraindicaciones>();
            for (Contraindicaciones contraindicacionesListNewContraindicacionesToAttach : contraindicacionesListNew) {
                contraindicacionesListNewContraindicacionesToAttach = em.getReference(contraindicacionesListNewContraindicacionesToAttach.getClass(), contraindicacionesListNewContraindicacionesToAttach.getIdContraindicacion());
                attachedContraindicacionesListNew.add(contraindicacionesListNewContraindicacionesToAttach);
            }
            contraindicacionesListNew = attachedContraindicacionesListNew;
            tipoContraindicacion.setContraindicacionesList(contraindicacionesListNew);
            tipoContraindicacion = em.merge(tipoContraindicacion);
            for (Contraindicaciones contraindicacionesListNewContraindicaciones : contraindicacionesListNew) {
                if (!contraindicacionesListOld.contains(contraindicacionesListNewContraindicaciones)) {
                    TipoContraindicacion oldTipoContraindicicacionOfContraindicacionesListNewContraindicaciones = contraindicacionesListNewContraindicaciones.getTipoContraindicicacion();
                    contraindicacionesListNewContraindicaciones.setTipoContraindicicacion(tipoContraindicacion);
                    contraindicacionesListNewContraindicaciones = em.merge(contraindicacionesListNewContraindicaciones);
                    if (oldTipoContraindicicacionOfContraindicacionesListNewContraindicaciones != null && !oldTipoContraindicicacionOfContraindicacionesListNewContraindicaciones.equals(tipoContraindicacion)) {
                        oldTipoContraindicicacionOfContraindicacionesListNewContraindicaciones.getContraindicacionesList().remove(contraindicacionesListNewContraindicaciones);
                        oldTipoContraindicicacionOfContraindicacionesListNewContraindicaciones = em.merge(oldTipoContraindicicacionOfContraindicacionesListNewContraindicaciones);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoContraindicacion.getIdTipoContraindicacion();
                if (findTipoContraindicacion(id) == null) {
                    throw new NonexistentEntityException("The tipoContraindicacion with id " + id + " no longer exists.");
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
            TipoContraindicacion tipoContraindicacion;
            try {
                tipoContraindicacion = em.getReference(TipoContraindicacion.class, id);
                tipoContraindicacion.getIdTipoContraindicacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoContraindicacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Contraindicaciones> contraindicacionesListOrphanCheck = tipoContraindicacion.getContraindicacionesList();
            for (Contraindicaciones contraindicacionesListOrphanCheckContraindicaciones : contraindicacionesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoContraindicacion (" + tipoContraindicacion + ") cannot be destroyed since the Contraindicaciones " + contraindicacionesListOrphanCheckContraindicaciones + " in its contraindicacionesList field has a non-nullable tipoContraindicicacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoContraindicacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoContraindicacion> findTipoContraindicacionEntities() {
        return findTipoContraindicacionEntities(true, -1, -1);
    }

    public List<TipoContraindicacion> findTipoContraindicacionEntities(int maxResults, int firstResult) {
        return findTipoContraindicacionEntities(false, maxResults, firstResult);
    }

    private List<TipoContraindicacion> findTipoContraindicacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoContraindicacion.class));
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

    public TipoContraindicacion findTipoContraindicacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoContraindicacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoContraindicacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoContraindicacion> rt = cq.from(TipoContraindicacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Integer ultimo() {
        try {
            Query consulta = getEntityManager().createNamedQuery("TipoContraindicacion.findAllDesc");
            consulta.setMaxResults(1);
            return ((TipoContraindicacion)consulta.getSingleResult()).getIdTipoContraindicacion()+1;
        } catch (Exception e) {
            return 1;
        }
    }
    
    public TipoContraindicacion buscarPorNombre(String nombre) {
        try {
            Query consulta = getEntityManager().createNamedQuery("TipoContraindicacion.findByNombreContraindicacion");
            consulta.setParameter("nombreContraindicacion", nombre);
            return (TipoContraindicacion)consulta.getSingleResult();
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
