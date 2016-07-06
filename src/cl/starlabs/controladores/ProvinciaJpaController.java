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
import cl.starlabs.modelo.Region;
import cl.starlabs.modelo.Comuna;
import cl.starlabs.modelo.Provincia;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor Manuel Araya
 */
public class ProvinciaJpaController implements Serializable {

    public ProvinciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Provincia provincia) throws PreexistingEntityException, Exception {
        if (provincia.getComunaList() == null) {
            provincia.setComunaList(new ArrayList<Comuna>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Region region = provincia.getRegion();
            if (region != null) {
                region = em.getReference(region.getClass(), region.getIdRegion());
                provincia.setRegion(region);
            }
            List<Comuna> attachedComunaList = new ArrayList<Comuna>();
            for (Comuna comunaListComunaToAttach : provincia.getComunaList()) {
                comunaListComunaToAttach = em.getReference(comunaListComunaToAttach.getClass(), comunaListComunaToAttach.getIdComuna());
                attachedComunaList.add(comunaListComunaToAttach);
            }
            provincia.setComunaList(attachedComunaList);
            em.persist(provincia);
            if (region != null) {
                region.getProvinciaList().add(provincia);
                region = em.merge(region);
            }
            for (Comuna comunaListComuna : provincia.getComunaList()) {
                Provincia oldProvinciaOfComunaListComuna = comunaListComuna.getProvincia();
                comunaListComuna.setProvincia(provincia);
                comunaListComuna = em.merge(comunaListComuna);
                if (oldProvinciaOfComunaListComuna != null) {
                    oldProvinciaOfComunaListComuna.getComunaList().remove(comunaListComuna);
                    oldProvinciaOfComunaListComuna = em.merge(oldProvinciaOfComunaListComuna);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProvincia(provincia.getIdProvincia()) != null) {
                throw new PreexistingEntityException("Provincia " + provincia + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Provincia provincia) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Provincia persistentProvincia = em.find(Provincia.class, provincia.getIdProvincia());
            Region regionOld = persistentProvincia.getRegion();
            Region regionNew = provincia.getRegion();
            List<Comuna> comunaListOld = persistentProvincia.getComunaList();
            List<Comuna> comunaListNew = provincia.getComunaList();
            List<String> illegalOrphanMessages = null;
            for (Comuna comunaListOldComuna : comunaListOld) {
                if (!comunaListNew.contains(comunaListOldComuna)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Comuna " + comunaListOldComuna + " since its provincia field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (regionNew != null) {
                regionNew = em.getReference(regionNew.getClass(), regionNew.getIdRegion());
                provincia.setRegion(regionNew);
            }
            List<Comuna> attachedComunaListNew = new ArrayList<Comuna>();
            for (Comuna comunaListNewComunaToAttach : comunaListNew) {
                comunaListNewComunaToAttach = em.getReference(comunaListNewComunaToAttach.getClass(), comunaListNewComunaToAttach.getIdComuna());
                attachedComunaListNew.add(comunaListNewComunaToAttach);
            }
            comunaListNew = attachedComunaListNew;
            provincia.setComunaList(comunaListNew);
            provincia = em.merge(provincia);
            if (regionOld != null && !regionOld.equals(regionNew)) {
                regionOld.getProvinciaList().remove(provincia);
                regionOld = em.merge(regionOld);
            }
            if (regionNew != null && !regionNew.equals(regionOld)) {
                regionNew.getProvinciaList().add(provincia);
                regionNew = em.merge(regionNew);
            }
            for (Comuna comunaListNewComuna : comunaListNew) {
                if (!comunaListOld.contains(comunaListNewComuna)) {
                    Provincia oldProvinciaOfComunaListNewComuna = comunaListNewComuna.getProvincia();
                    comunaListNewComuna.setProvincia(provincia);
                    comunaListNewComuna = em.merge(comunaListNewComuna);
                    if (oldProvinciaOfComunaListNewComuna != null && !oldProvinciaOfComunaListNewComuna.equals(provincia)) {
                        oldProvinciaOfComunaListNewComuna.getComunaList().remove(comunaListNewComuna);
                        oldProvinciaOfComunaListNewComuna = em.merge(oldProvinciaOfComunaListNewComuna);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = provincia.getIdProvincia();
                if (findProvincia(id) == null) {
                    throw new NonexistentEntityException("The provincia with id " + id + " no longer exists.");
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
            Provincia provincia;
            try {
                provincia = em.getReference(Provincia.class, id);
                provincia.getIdProvincia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The provincia with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Comuna> comunaListOrphanCheck = provincia.getComunaList();
            for (Comuna comunaListOrphanCheckComuna : comunaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Provincia (" + provincia + ") cannot be destroyed since the Comuna " + comunaListOrphanCheckComuna + " in its comunaList field has a non-nullable provincia field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Region region = provincia.getRegion();
            if (region != null) {
                region.getProvinciaList().remove(provincia);
                region = em.merge(region);
            }
            em.remove(provincia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Provincia> findProvinciaEntities() {
        return findProvinciaEntities(true, -1, -1);
    }

    public List<Provincia> findProvinciaEntities(int maxResults, int firstResult) {
        return findProvinciaEntities(false, maxResults, firstResult);
    }

    private List<Provincia> findProvinciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Provincia.class));
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

    public Provincia findProvincia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Provincia.class, id);
        } finally {
            em.close();
        }
    }

    public int getProvinciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Provincia> rt = cq.from(Provincia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Integer ultimo() {
        try {
            Query consulta = getEntityManager().createNamedQuery("Provincia.findAllById");
            consulta.setMaxResults(1);
            return ((Provincia)consulta.getSingleResult()).getIdProvincia()+1;
        } catch (Exception e) {
            return 1;
        }
    }
    
}
