/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.controladores;

import cl.starlabs.controladores.exceptions.IllegalOrphanException;
import cl.starlabs.controladores.exceptions.NonexistentEntityException;
import cl.starlabs.controladores.exceptions.PreexistingEntityException;
import cl.starlabs.modelo.Caracter;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.starlabs.modelo.Mascota;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor Manuel Araya
 */
public class CaracterJpaController implements Serializable {

    public CaracterJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Caracter caracter) throws PreexistingEntityException, Exception {
        if (caracter.getMascotaList() == null) {
            caracter.setMascotaList(new ArrayList<Mascota>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Mascota> attachedMascotaList = new ArrayList<Mascota>();
            for (Mascota mascotaListMascotaToAttach : caracter.getMascotaList()) {
                mascotaListMascotaToAttach = em.getReference(mascotaListMascotaToAttach.getClass(), mascotaListMascotaToAttach.getIdMascota());
                attachedMascotaList.add(mascotaListMascotaToAttach);
            }
            caracter.setMascotaList(attachedMascotaList);
            em.persist(caracter);
            for (Mascota mascotaListMascota : caracter.getMascotaList()) {
                Caracter oldCaracterOfMascotaListMascota = mascotaListMascota.getCaracter();
                mascotaListMascota.setCaracter(caracter);
                mascotaListMascota = em.merge(mascotaListMascota);
                if (oldCaracterOfMascotaListMascota != null) {
                    oldCaracterOfMascotaListMascota.getMascotaList().remove(mascotaListMascota);
                    oldCaracterOfMascotaListMascota = em.merge(oldCaracterOfMascotaListMascota);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCaracter(caracter.getIdCaracter()) != null) {
                throw new PreexistingEntityException("Caracter " + caracter + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Caracter caracter) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Caracter persistentCaracter = em.find(Caracter.class, caracter.getIdCaracter());
            List<Mascota> mascotaListOld = persistentCaracter.getMascotaList();
            List<Mascota> mascotaListNew = caracter.getMascotaList();
            List<String> illegalOrphanMessages = null;
            for (Mascota mascotaListOldMascota : mascotaListOld) {
                if (!mascotaListNew.contains(mascotaListOldMascota)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Mascota " + mascotaListOldMascota + " since its caracter field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Mascota> attachedMascotaListNew = new ArrayList<Mascota>();
            for (Mascota mascotaListNewMascotaToAttach : mascotaListNew) {
                mascotaListNewMascotaToAttach = em.getReference(mascotaListNewMascotaToAttach.getClass(), mascotaListNewMascotaToAttach.getIdMascota());
                attachedMascotaListNew.add(mascotaListNewMascotaToAttach);
            }
            mascotaListNew = attachedMascotaListNew;
            caracter.setMascotaList(mascotaListNew);
            caracter = em.merge(caracter);
            for (Mascota mascotaListNewMascota : mascotaListNew) {
                if (!mascotaListOld.contains(mascotaListNewMascota)) {
                    Caracter oldCaracterOfMascotaListNewMascota = mascotaListNewMascota.getCaracter();
                    mascotaListNewMascota.setCaracter(caracter);
                    mascotaListNewMascota = em.merge(mascotaListNewMascota);
                    if (oldCaracterOfMascotaListNewMascota != null && !oldCaracterOfMascotaListNewMascota.equals(caracter)) {
                        oldCaracterOfMascotaListNewMascota.getMascotaList().remove(mascotaListNewMascota);
                        oldCaracterOfMascotaListNewMascota = em.merge(oldCaracterOfMascotaListNewMascota);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = caracter.getIdCaracter();
                if (findCaracter(id) == null) {
                    throw new NonexistentEntityException("The caracter with id " + id + " no longer exists.");
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
            Caracter caracter;
            try {
                caracter = em.getReference(Caracter.class, id);
                caracter.getIdCaracter();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The caracter with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Mascota> mascotaListOrphanCheck = caracter.getMascotaList();
            for (Mascota mascotaListOrphanCheckMascota : mascotaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Caracter (" + caracter + ") cannot be destroyed since the Mascota " + mascotaListOrphanCheckMascota + " in its mascotaList field has a non-nullable caracter field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(caracter);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Caracter> findCaracterEntities() {
        return findCaracterEntities(true, -1, -1);
    }

    public List<Caracter> findCaracterEntities(int maxResults, int firstResult) {
        return findCaracterEntities(false, maxResults, firstResult);
    }

    private List<Caracter> findCaracterEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Caracter.class));
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

    public Caracter findCaracter(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Caracter.class, id);
        } finally {
            em.close();
        }
    }

    public int getCaracterCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Caracter> rt = cq.from(Caracter.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Caracter buscarCaracter(String nombre) {
        try {
            Query consulta = getEntityManager().createNamedQuery("Caracter.findByNombre");
            consulta.setParameter("nombre", nombre);
            return (Caracter)consulta.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    public Integer ultimo() {
        try {
            Query consulta = getEntityManager().createNamedQuery("Caracter.findAllDesc");
            consulta.setMaxResults(1);
            return ((Caracter)consulta.getSingleResult()).getIdCaracter()+1;
        } catch (Exception e) {
            return 1;
        }
    }
    
    public Caracter buscarPorNombre(String nombre) {
        try {
            Query consulta = getEntityManager().createNamedQuery("Caracter.findByNombre");
            consulta.setParameter("nombre", nombre);
            return (Caracter)consulta.getSingleResult();
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
