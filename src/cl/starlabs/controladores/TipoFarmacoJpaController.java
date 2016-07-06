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
import cl.starlabs.modelo.Farmacos;
import cl.starlabs.modelo.TipoFarmaco;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor Manuel Araya
 */
public class TipoFarmacoJpaController implements Serializable {

    public TipoFarmacoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoFarmaco tipoFarmaco) throws PreexistingEntityException, Exception {
        if (tipoFarmaco.getFarmacosList() == null) {
            tipoFarmaco.setFarmacosList(new ArrayList<Farmacos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Farmacos> attachedFarmacosList = new ArrayList<Farmacos>();
            for (Farmacos farmacosListFarmacosToAttach : tipoFarmaco.getFarmacosList()) {
                farmacosListFarmacosToAttach = em.getReference(farmacosListFarmacosToAttach.getClass(), farmacosListFarmacosToAttach.getIdFarmaco());
                attachedFarmacosList.add(farmacosListFarmacosToAttach);
            }
            tipoFarmaco.setFarmacosList(attachedFarmacosList);
            em.persist(tipoFarmaco);
            for (Farmacos farmacosListFarmacos : tipoFarmaco.getFarmacosList()) {
                TipoFarmaco oldFarmacoOfFarmacosListFarmacos = farmacosListFarmacos.getFarmaco();
                farmacosListFarmacos.setFarmaco(tipoFarmaco);
                farmacosListFarmacos = em.merge(farmacosListFarmacos);
                if (oldFarmacoOfFarmacosListFarmacos != null) {
                    oldFarmacoOfFarmacosListFarmacos.getFarmacosList().remove(farmacosListFarmacos);
                    oldFarmacoOfFarmacosListFarmacos = em.merge(oldFarmacoOfFarmacosListFarmacos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoFarmaco(tipoFarmaco.getIdFarmaco()) != null) {
                throw new PreexistingEntityException("TipoFarmaco " + tipoFarmaco + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoFarmaco tipoFarmaco) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoFarmaco persistentTipoFarmaco = em.find(TipoFarmaco.class, tipoFarmaco.getIdFarmaco());
            List<Farmacos> farmacosListOld = persistentTipoFarmaco.getFarmacosList();
            List<Farmacos> farmacosListNew = tipoFarmaco.getFarmacosList();
            List<String> illegalOrphanMessages = null;
            for (Farmacos farmacosListOldFarmacos : farmacosListOld) {
                if (!farmacosListNew.contains(farmacosListOldFarmacos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Farmacos " + farmacosListOldFarmacos + " since its farmaco field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Farmacos> attachedFarmacosListNew = new ArrayList<Farmacos>();
            for (Farmacos farmacosListNewFarmacosToAttach : farmacosListNew) {
                farmacosListNewFarmacosToAttach = em.getReference(farmacosListNewFarmacosToAttach.getClass(), farmacosListNewFarmacosToAttach.getIdFarmaco());
                attachedFarmacosListNew.add(farmacosListNewFarmacosToAttach);
            }
            farmacosListNew = attachedFarmacosListNew;
            tipoFarmaco.setFarmacosList(farmacosListNew);
            tipoFarmaco = em.merge(tipoFarmaco);
            for (Farmacos farmacosListNewFarmacos : farmacosListNew) {
                if (!farmacosListOld.contains(farmacosListNewFarmacos)) {
                    TipoFarmaco oldFarmacoOfFarmacosListNewFarmacos = farmacosListNewFarmacos.getFarmaco();
                    farmacosListNewFarmacos.setFarmaco(tipoFarmaco);
                    farmacosListNewFarmacos = em.merge(farmacosListNewFarmacos);
                    if (oldFarmacoOfFarmacosListNewFarmacos != null && !oldFarmacoOfFarmacosListNewFarmacos.equals(tipoFarmaco)) {
                        oldFarmacoOfFarmacosListNewFarmacos.getFarmacosList().remove(farmacosListNewFarmacos);
                        oldFarmacoOfFarmacosListNewFarmacos = em.merge(oldFarmacoOfFarmacosListNewFarmacos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoFarmaco.getIdFarmaco();
                if (findTipoFarmaco(id) == null) {
                    throw new NonexistentEntityException("The tipoFarmaco with id " + id + " no longer exists.");
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
            TipoFarmaco tipoFarmaco;
            try {
                tipoFarmaco = em.getReference(TipoFarmaco.class, id);
                tipoFarmaco.getIdFarmaco();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoFarmaco with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Farmacos> farmacosListOrphanCheck = tipoFarmaco.getFarmacosList();
            for (Farmacos farmacosListOrphanCheckFarmacos : farmacosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoFarmaco (" + tipoFarmaco + ") cannot be destroyed since the Farmacos " + farmacosListOrphanCheckFarmacos + " in its farmacosList field has a non-nullable farmaco field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoFarmaco);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoFarmaco> findTipoFarmacoEntities() {
        return findTipoFarmacoEntities(true, -1, -1);
    }

    public List<TipoFarmaco> findTipoFarmacoEntities(int maxResults, int firstResult) {
        return findTipoFarmacoEntities(false, maxResults, firstResult);
    }

    private List<TipoFarmaco> findTipoFarmacoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoFarmaco.class));
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

    public TipoFarmaco findTipoFarmaco(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoFarmaco.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoFarmacoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoFarmaco> rt = cq.from(TipoFarmaco.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Integer ultimo() {
        try {
            Query consulta = getEntityManager().createNamedQuery("TipoFarmaco.findAllDesc");
            consulta.setMaxResults(1);
            return ((TipoFarmaco)consulta.getSingleResult()).getIdFarmaco()+1;
        } catch (Exception e) {
            return 1;
        }
    }
    
    public TipoFarmaco buscarPorNombre(String nombre) {
        try {
            Query consulta = getEntityManager().createNamedQuery("TipoFarmaco.findByNombreComercial");
            consulta.setParameter("nombreComercial", nombre);
            return (TipoFarmaco)consulta.getSingleResult();
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
