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
import cl.starlabs.modelo.Patologias;
import cl.starlabs.modelo.TipoPatologia;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor Manuel Araya
 */
public class TipoPatologiaJpaController implements Serializable {

    public TipoPatologiaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoPatologia tipoPatologia) throws PreexistingEntityException, Exception {
        if (tipoPatologia.getPatologiasList() == null) {
            tipoPatologia.setPatologiasList(new ArrayList<Patologias>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Patologias> attachedPatologiasList = new ArrayList<Patologias>();
            for (Patologias patologiasListPatologiasToAttach : tipoPatologia.getPatologiasList()) {
                patologiasListPatologiasToAttach = em.getReference(patologiasListPatologiasToAttach.getClass(), patologiasListPatologiasToAttach.getIdPatologia());
                attachedPatologiasList.add(patologiasListPatologiasToAttach);
            }
            tipoPatologia.setPatologiasList(attachedPatologiasList);
            em.persist(tipoPatologia);
            for (Patologias patologiasListPatologias : tipoPatologia.getPatologiasList()) {
                TipoPatologia oldTipoPatologiaOfPatologiasListPatologias = patologiasListPatologias.getTipoPatologia();
                patologiasListPatologias.setTipoPatologia(tipoPatologia);
                patologiasListPatologias = em.merge(patologiasListPatologias);
                if (oldTipoPatologiaOfPatologiasListPatologias != null) {
                    oldTipoPatologiaOfPatologiasListPatologias.getPatologiasList().remove(patologiasListPatologias);
                    oldTipoPatologiaOfPatologiasListPatologias = em.merge(oldTipoPatologiaOfPatologiasListPatologias);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoPatologia(tipoPatologia.getIdTipoPatologia()) != null) {
                throw new PreexistingEntityException("TipoPatologia " + tipoPatologia + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoPatologia tipoPatologia) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoPatologia persistentTipoPatologia = em.find(TipoPatologia.class, tipoPatologia.getIdTipoPatologia());
            List<Patologias> patologiasListOld = persistentTipoPatologia.getPatologiasList();
            List<Patologias> patologiasListNew = tipoPatologia.getPatologiasList();
            List<String> illegalOrphanMessages = null;
            for (Patologias patologiasListOldPatologias : patologiasListOld) {
                if (!patologiasListNew.contains(patologiasListOldPatologias)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Patologias " + patologiasListOldPatologias + " since its tipoPatologia field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Patologias> attachedPatologiasListNew = new ArrayList<Patologias>();
            for (Patologias patologiasListNewPatologiasToAttach : patologiasListNew) {
                patologiasListNewPatologiasToAttach = em.getReference(patologiasListNewPatologiasToAttach.getClass(), patologiasListNewPatologiasToAttach.getIdPatologia());
                attachedPatologiasListNew.add(patologiasListNewPatologiasToAttach);
            }
            patologiasListNew = attachedPatologiasListNew;
            tipoPatologia.setPatologiasList(patologiasListNew);
            tipoPatologia = em.merge(tipoPatologia);
            for (Patologias patologiasListNewPatologias : patologiasListNew) {
                if (!patologiasListOld.contains(patologiasListNewPatologias)) {
                    TipoPatologia oldTipoPatologiaOfPatologiasListNewPatologias = patologiasListNewPatologias.getTipoPatologia();
                    patologiasListNewPatologias.setTipoPatologia(tipoPatologia);
                    patologiasListNewPatologias = em.merge(patologiasListNewPatologias);
                    if (oldTipoPatologiaOfPatologiasListNewPatologias != null && !oldTipoPatologiaOfPatologiasListNewPatologias.equals(tipoPatologia)) {
                        oldTipoPatologiaOfPatologiasListNewPatologias.getPatologiasList().remove(patologiasListNewPatologias);
                        oldTipoPatologiaOfPatologiasListNewPatologias = em.merge(oldTipoPatologiaOfPatologiasListNewPatologias);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoPatologia.getIdTipoPatologia();
                if (findTipoPatologia(id) == null) {
                    throw new NonexistentEntityException("The tipoPatologia with id " + id + " no longer exists.");
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
            TipoPatologia tipoPatologia;
            try {
                tipoPatologia = em.getReference(TipoPatologia.class, id);
                tipoPatologia.getIdTipoPatologia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoPatologia with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Patologias> patologiasListOrphanCheck = tipoPatologia.getPatologiasList();
            for (Patologias patologiasListOrphanCheckPatologias : patologiasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoPatologia (" + tipoPatologia + ") cannot be destroyed since the Patologias " + patologiasListOrphanCheckPatologias + " in its patologiasList field has a non-nullable tipoPatologia field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoPatologia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoPatologia> findTipoPatologiaEntities() {
        return findTipoPatologiaEntities(true, -1, -1);
    }

    public List<TipoPatologia> findTipoPatologiaEntities(int maxResults, int firstResult) {
        return findTipoPatologiaEntities(false, maxResults, firstResult);
    }

    private List<TipoPatologia> findTipoPatologiaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoPatologia.class));
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

    public TipoPatologia findTipoPatologia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoPatologia.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoPatologiaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoPatologia> rt = cq.from(TipoPatologia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Integer ultimo() {
        try {
            Query consulta = getEntityManager().createNamedQuery("TipoPatologia.findAllDesc");
            consulta.setMaxResults(1);
            return ((TipoPatologia)consulta.getSingleResult()).getIdTipoPatologia()+1;
        } catch (Exception e) {
            return 1;
        }
    }
    
    public TipoPatologia buscarPorNombre(String nombre) {
        try {
            Query consulta = getEntityManager().createNamedQuery("TipoPatologia.findByNombrePatologia");
            consulta.setParameter("nombre", nombre);
            return (TipoPatologia)consulta.getSingleResult();
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
