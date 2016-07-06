/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.controladores;

import cl.starlabs.controladores.exceptions.IllegalOrphanException;
import cl.starlabs.controladores.exceptions.NonexistentEntityException;
import cl.starlabs.controladores.exceptions.PreexistingEntityException;
import cl.starlabs.modelo.Clinica;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.starlabs.modelo.Sucursal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor Manuel Araya
 */
public class ClinicaJpaController implements Serializable {

    public ClinicaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Clinica clinica) throws PreexistingEntityException, Exception {
        if (clinica.getSucursalList() == null) {
            clinica.setSucursalList(new ArrayList<Sucursal>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Sucursal> attachedSucursalList = new ArrayList<Sucursal>();
            for (Sucursal sucursalListSucursalToAttach : clinica.getSucursalList()) {
                sucursalListSucursalToAttach = em.getReference(sucursalListSucursalToAttach.getClass(), sucursalListSucursalToAttach.getIdSucursal());
                attachedSucursalList.add(sucursalListSucursalToAttach);
            }
            clinica.setSucursalList(attachedSucursalList);
            em.persist(clinica);
            for (Sucursal sucursalListSucursal : clinica.getSucursalList()) {
                Clinica oldClinicaOfSucursalListSucursal = sucursalListSucursal.getClinica();
                sucursalListSucursal.setClinica(clinica);
                sucursalListSucursal = em.merge(sucursalListSucursal);
                if (oldClinicaOfSucursalListSucursal != null) {
                    oldClinicaOfSucursalListSucursal.getSucursalList().remove(sucursalListSucursal);
                    oldClinicaOfSucursalListSucursal = em.merge(oldClinicaOfSucursalListSucursal);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findClinica(clinica.getIdClinica()) != null) {
                throw new PreexistingEntityException("Clinica " + clinica + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Clinica clinica) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clinica persistentClinica = em.find(Clinica.class, clinica.getIdClinica());
            List<Sucursal> sucursalListOld = persistentClinica.getSucursalList();
            List<Sucursal> sucursalListNew = clinica.getSucursalList();
            List<String> illegalOrphanMessages = null;
            for (Sucursal sucursalListOldSucursal : sucursalListOld) {
                if (!sucursalListNew.contains(sucursalListOldSucursal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Sucursal " + sucursalListOldSucursal + " since its clinica field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Sucursal> attachedSucursalListNew = new ArrayList<Sucursal>();
            for (Sucursal sucursalListNewSucursalToAttach : sucursalListNew) {
                sucursalListNewSucursalToAttach = em.getReference(sucursalListNewSucursalToAttach.getClass(), sucursalListNewSucursalToAttach.getIdSucursal());
                attachedSucursalListNew.add(sucursalListNewSucursalToAttach);
            }
            sucursalListNew = attachedSucursalListNew;
            clinica.setSucursalList(sucursalListNew);
            clinica = em.merge(clinica);
            for (Sucursal sucursalListNewSucursal : sucursalListNew) {
                if (!sucursalListOld.contains(sucursalListNewSucursal)) {
                    Clinica oldClinicaOfSucursalListNewSucursal = sucursalListNewSucursal.getClinica();
                    sucursalListNewSucursal.setClinica(clinica);
                    sucursalListNewSucursal = em.merge(sucursalListNewSucursal);
                    if (oldClinicaOfSucursalListNewSucursal != null && !oldClinicaOfSucursalListNewSucursal.equals(clinica)) {
                        oldClinicaOfSucursalListNewSucursal.getSucursalList().remove(sucursalListNewSucursal);
                        oldClinicaOfSucursalListNewSucursal = em.merge(oldClinicaOfSucursalListNewSucursal);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = clinica.getIdClinica();
                if (findClinica(id) == null) {
                    throw new NonexistentEntityException("The clinica with id " + id + " no longer exists.");
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
            Clinica clinica;
            try {
                clinica = em.getReference(Clinica.class, id);
                clinica.getIdClinica();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clinica with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Sucursal> sucursalListOrphanCheck = clinica.getSucursalList();
            for (Sucursal sucursalListOrphanCheckSucursal : sucursalListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clinica (" + clinica + ") cannot be destroyed since the Sucursal " + sucursalListOrphanCheckSucursal + " in its sucursalList field has a non-nullable clinica field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(clinica);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Clinica> findClinicaEntities() {
        return findClinicaEntities(true, -1, -1);
    }

    public List<Clinica> findClinicaEntities(int maxResults, int firstResult) {
        return findClinicaEntities(false, maxResults, firstResult);
    }

    private List<Clinica> findClinicaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Clinica.class));
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

    public Clinica findClinica(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Clinica.class, id);
        } finally {
            em.close();
        }
    }

    public int getClinicaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Clinica> rt = cq.from(Clinica.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public void actualizar(Clinica c) {
        try {
            EntityManager em = getEntityManager();
            getEntityManager().getTransaction().begin();
            em.merge(c);
            getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
}
