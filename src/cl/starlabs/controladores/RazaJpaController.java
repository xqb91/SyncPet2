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
import cl.starlabs.modelo.Especie;
import cl.starlabs.modelo.Mascota;
import cl.starlabs.modelo.Raza;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor Manuel Araya
 */
public class RazaJpaController implements Serializable {

    public RazaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Raza raza) throws PreexistingEntityException, Exception {
        if (raza.getMascotaList() == null) {
            raza.setMascotaList(new ArrayList<Mascota>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Especie especie = raza.getEspecie();
            if (especie != null) {
                especie = em.getReference(especie.getClass(), especie.getIdEspecie());
                raza.setEspecie(especie);
            }
            List<Mascota> attachedMascotaList = new ArrayList<Mascota>();
            for (Mascota mascotaListMascotaToAttach : raza.getMascotaList()) {
                mascotaListMascotaToAttach = em.getReference(mascotaListMascotaToAttach.getClass(), mascotaListMascotaToAttach.getIdMascota());
                attachedMascotaList.add(mascotaListMascotaToAttach);
            }
            raza.setMascotaList(attachedMascotaList);
            em.persist(raza);
            if (especie != null) {
                especie.getRazaList().add(raza);
                especie = em.merge(especie);
            }
            for (Mascota mascotaListMascota : raza.getMascotaList()) {
                Raza oldRazaOfMascotaListMascota = mascotaListMascota.getRaza();
                mascotaListMascota.setRaza(raza);
                mascotaListMascota = em.merge(mascotaListMascota);
                if (oldRazaOfMascotaListMascota != null) {
                    oldRazaOfMascotaListMascota.getMascotaList().remove(mascotaListMascota);
                    oldRazaOfMascotaListMascota = em.merge(oldRazaOfMascotaListMascota);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRaza(raza.getIdRaza()) != null) {
                throw new PreexistingEntityException("Raza " + raza + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Raza raza) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Raza persistentRaza = em.find(Raza.class, raza.getIdRaza());
            Especie especieOld = persistentRaza.getEspecie();
            Especie especieNew = raza.getEspecie();
            List<Mascota> mascotaListOld = persistentRaza.getMascotaList();
            List<Mascota> mascotaListNew = raza.getMascotaList();
            List<String> illegalOrphanMessages = null;
            for (Mascota mascotaListOldMascota : mascotaListOld) {
                if (!mascotaListNew.contains(mascotaListOldMascota)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Mascota " + mascotaListOldMascota + " since its raza field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (especieNew != null) {
                especieNew = em.getReference(especieNew.getClass(), especieNew.getIdEspecie());
                raza.setEspecie(especieNew);
            }
            List<Mascota> attachedMascotaListNew = new ArrayList<Mascota>();
            for (Mascota mascotaListNewMascotaToAttach : mascotaListNew) {
                mascotaListNewMascotaToAttach = em.getReference(mascotaListNewMascotaToAttach.getClass(), mascotaListNewMascotaToAttach.getIdMascota());
                attachedMascotaListNew.add(mascotaListNewMascotaToAttach);
            }
            mascotaListNew = attachedMascotaListNew;
            raza.setMascotaList(mascotaListNew);
            raza = em.merge(raza);
            if (especieOld != null && !especieOld.equals(especieNew)) {
                especieOld.getRazaList().remove(raza);
                especieOld = em.merge(especieOld);
            }
            if (especieNew != null && !especieNew.equals(especieOld)) {
                especieNew.getRazaList().add(raza);
                especieNew = em.merge(especieNew);
            }
            for (Mascota mascotaListNewMascota : mascotaListNew) {
                if (!mascotaListOld.contains(mascotaListNewMascota)) {
                    Raza oldRazaOfMascotaListNewMascota = mascotaListNewMascota.getRaza();
                    mascotaListNewMascota.setRaza(raza);
                    mascotaListNewMascota = em.merge(mascotaListNewMascota);
                    if (oldRazaOfMascotaListNewMascota != null && !oldRazaOfMascotaListNewMascota.equals(raza)) {
                        oldRazaOfMascotaListNewMascota.getMascotaList().remove(mascotaListNewMascota);
                        oldRazaOfMascotaListNewMascota = em.merge(oldRazaOfMascotaListNewMascota);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = raza.getIdRaza();
                if (findRaza(id) == null) {
                    throw new NonexistentEntityException("The raza with id " + id + " no longer exists.");
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
            Raza raza;
            try {
                raza = em.getReference(Raza.class, id);
                raza.getIdRaza();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The raza with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Mascota> mascotaListOrphanCheck = raza.getMascotaList();
            for (Mascota mascotaListOrphanCheckMascota : mascotaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Raza (" + raza + ") cannot be destroyed since the Mascota " + mascotaListOrphanCheckMascota + " in its mascotaList field has a non-nullable raza field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Especie especie = raza.getEspecie();
            if (especie != null) {
                especie.getRazaList().remove(raza);
                especie = em.merge(especie);
            }
            em.remove(raza);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Raza> findRazaEntities() {
        return findRazaEntities(true, -1, -1);
    }

    public List<Raza> findRazaEntities(int maxResults, int firstResult) {
        return findRazaEntities(false, maxResults, firstResult);
    }

    private List<Raza> findRazaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Raza.class));
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

    public Raza findRaza(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Raza.class, id);
        } finally {
            em.close();
        }
    }

    public int getRazaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Raza> rt = cq.from(Raza.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Raza buscarRaza(String nombre) {
        try {
            Query consulta = getEntityManager().createNamedQuery("Raza.findByNombre");
            consulta.setParameter("nombre", nombre);
            return (Raza)consulta.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    
   public Integer ultimo() {
        try {
            Query consulta = getEntityManager().createNamedQuery("Raza.findAllDesc");
            consulta.setMaxResults(1);
            return ((Raza)consulta.getSingleResult()).getIdRaza()+1;
        } catch (Exception e) {
            return 1;
        }
    }
    
    public Raza buscarPorNombre(String nombre) {
        try {
            Query consulta = getEntityManager().createNamedQuery("Raza.findByNombre");
            consulta.setParameter("nombre", nombre);
            return (Raza)consulta.getSingleResult();
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
    
    public List<Raza> buscarPorEspecie(Especie es) {
        try {
            Query consulta = getEntityManager().createNamedQuery("Raza.findByEspecie");
            consulta.setParameter("especie", es);
            return consulta.getResultList();
        }catch (Exception e) {
            return null;
        }
    }
    
}
