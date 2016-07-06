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
import cl.starlabs.modelo.Pais;
import cl.starlabs.modelo.Provincia;
import cl.starlabs.modelo.Region;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor Manuel Araya
 */
public class RegionJpaController implements Serializable {

    public RegionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Region region) throws PreexistingEntityException, Exception {
        if (region.getProvinciaList() == null) {
            region.setProvinciaList(new ArrayList<Provincia>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pais pais = region.getPais();
            if (pais != null) {
                pais = em.getReference(pais.getClass(), pais.getIdPais());
                region.setPais(pais);
            }
            List<Provincia> attachedProvinciaList = new ArrayList<Provincia>();
            for (Provincia provinciaListProvinciaToAttach : region.getProvinciaList()) {
                provinciaListProvinciaToAttach = em.getReference(provinciaListProvinciaToAttach.getClass(), provinciaListProvinciaToAttach.getIdProvincia());
                attachedProvinciaList.add(provinciaListProvinciaToAttach);
            }
            region.setProvinciaList(attachedProvinciaList);
            em.persist(region);
            if (pais != null) {
                pais.getRegionList().add(region);
                pais = em.merge(pais);
            }
            for (Provincia provinciaListProvincia : region.getProvinciaList()) {
                Region oldRegionOfProvinciaListProvincia = provinciaListProvincia.getRegion();
                provinciaListProvincia.setRegion(region);
                provinciaListProvincia = em.merge(provinciaListProvincia);
                if (oldRegionOfProvinciaListProvincia != null) {
                    oldRegionOfProvinciaListProvincia.getProvinciaList().remove(provinciaListProvincia);
                    oldRegionOfProvinciaListProvincia = em.merge(oldRegionOfProvinciaListProvincia);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRegion(region.getIdRegion()) != null) {
                throw new PreexistingEntityException("Region " + region + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Region region) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Region persistentRegion = em.find(Region.class, region.getIdRegion());
            Pais paisOld = persistentRegion.getPais();
            Pais paisNew = region.getPais();
            List<Provincia> provinciaListOld = persistentRegion.getProvinciaList();
            List<Provincia> provinciaListNew = region.getProvinciaList();
            List<String> illegalOrphanMessages = null;
            for (Provincia provinciaListOldProvincia : provinciaListOld) {
                if (!provinciaListNew.contains(provinciaListOldProvincia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Provincia " + provinciaListOldProvincia + " since its region field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (paisNew != null) {
                paisNew = em.getReference(paisNew.getClass(), paisNew.getIdPais());
                region.setPais(paisNew);
            }
            List<Provincia> attachedProvinciaListNew = new ArrayList<Provincia>();
            for (Provincia provinciaListNewProvinciaToAttach : provinciaListNew) {
                provinciaListNewProvinciaToAttach = em.getReference(provinciaListNewProvinciaToAttach.getClass(), provinciaListNewProvinciaToAttach.getIdProvincia());
                attachedProvinciaListNew.add(provinciaListNewProvinciaToAttach);
            }
            provinciaListNew = attachedProvinciaListNew;
            region.setProvinciaList(provinciaListNew);
            region = em.merge(region);
            if (paisOld != null && !paisOld.equals(paisNew)) {
                paisOld.getRegionList().remove(region);
                paisOld = em.merge(paisOld);
            }
            if (paisNew != null && !paisNew.equals(paisOld)) {
                paisNew.getRegionList().add(region);
                paisNew = em.merge(paisNew);
            }
            for (Provincia provinciaListNewProvincia : provinciaListNew) {
                if (!provinciaListOld.contains(provinciaListNewProvincia)) {
                    Region oldRegionOfProvinciaListNewProvincia = provinciaListNewProvincia.getRegion();
                    provinciaListNewProvincia.setRegion(region);
                    provinciaListNewProvincia = em.merge(provinciaListNewProvincia);
                    if (oldRegionOfProvinciaListNewProvincia != null && !oldRegionOfProvinciaListNewProvincia.equals(region)) {
                        oldRegionOfProvinciaListNewProvincia.getProvinciaList().remove(provinciaListNewProvincia);
                        oldRegionOfProvinciaListNewProvincia = em.merge(oldRegionOfProvinciaListNewProvincia);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = region.getIdRegion();
                if (findRegion(id) == null) {
                    throw new NonexistentEntityException("The region with id " + id + " no longer exists.");
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
            Region region;
            try {
                region = em.getReference(Region.class, id);
                region.getIdRegion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The region with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Provincia> provinciaListOrphanCheck = region.getProvinciaList();
            for (Provincia provinciaListOrphanCheckProvincia : provinciaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Region (" + region + ") cannot be destroyed since the Provincia " + provinciaListOrphanCheckProvincia + " in its provinciaList field has a non-nullable region field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Pais pais = region.getPais();
            if (pais != null) {
                pais.getRegionList().remove(region);
                pais = em.merge(pais);
            }
            em.remove(region);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Region> findRegionEntities() {
        return findRegionEntities(true, -1, -1);
    }

    public List<Region> findRegionEntities(int maxResults, int firstResult) {
        return findRegionEntities(false, maxResults, firstResult);
    }

    private List<Region> findRegionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Region.class));
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

    public Region findRegion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Region.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Region> rt = cq.from(Region.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Integer ultimo() {
        try {
            Query consulta = getEntityManager().createNamedQuery("Region.findAllById");
            consulta.setMaxResults(1);
            return ((Region)consulta.getSingleResult()).getIdRegion()+1;
        } catch (Exception e) {
            return 1;
        }
    }
    
}
