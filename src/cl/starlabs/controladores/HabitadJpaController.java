/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.controladores;

import cl.starlabs.controladores.exceptions.IllegalOrphanException;
import cl.starlabs.controladores.exceptions.NonexistentEntityException;
import cl.starlabs.controladores.exceptions.PreexistingEntityException;
import cl.starlabs.modelo.Habitad;
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
public class HabitadJpaController implements Serializable {

    public HabitadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Habitad habitad) throws PreexistingEntityException, Exception {
        if (habitad.getMascotaList() == null) {
            habitad.setMascotaList(new ArrayList<Mascota>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Mascota> attachedMascotaList = new ArrayList<Mascota>();
            for (Mascota mascotaListMascotaToAttach : habitad.getMascotaList()) {
                mascotaListMascotaToAttach = em.getReference(mascotaListMascotaToAttach.getClass(), mascotaListMascotaToAttach.getIdMascota());
                attachedMascotaList.add(mascotaListMascotaToAttach);
            }
            habitad.setMascotaList(attachedMascotaList);
            em.persist(habitad);
            for (Mascota mascotaListMascota : habitad.getMascotaList()) {
                Habitad oldHabitadOfMascotaListMascota = mascotaListMascota.getHabitad();
                mascotaListMascota.setHabitad(habitad);
                mascotaListMascota = em.merge(mascotaListMascota);
                if (oldHabitadOfMascotaListMascota != null) {
                    oldHabitadOfMascotaListMascota.getMascotaList().remove(mascotaListMascota);
                    oldHabitadOfMascotaListMascota = em.merge(oldHabitadOfMascotaListMascota);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findHabitad(habitad.getIdHabitad()) != null) {
                throw new PreexistingEntityException("Habitad " + habitad + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Habitad habitad) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Habitad persistentHabitad = em.find(Habitad.class, habitad.getIdHabitad());
            List<Mascota> mascotaListOld = persistentHabitad.getMascotaList();
            List<Mascota> mascotaListNew = habitad.getMascotaList();
            List<String> illegalOrphanMessages = null;
            for (Mascota mascotaListOldMascota : mascotaListOld) {
                if (!mascotaListNew.contains(mascotaListOldMascota)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Mascota " + mascotaListOldMascota + " since its habitad field is not nullable.");
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
            habitad.setMascotaList(mascotaListNew);
            habitad = em.merge(habitad);
            for (Mascota mascotaListNewMascota : mascotaListNew) {
                if (!mascotaListOld.contains(mascotaListNewMascota)) {
                    Habitad oldHabitadOfMascotaListNewMascota = mascotaListNewMascota.getHabitad();
                    mascotaListNewMascota.setHabitad(habitad);
                    mascotaListNewMascota = em.merge(mascotaListNewMascota);
                    if (oldHabitadOfMascotaListNewMascota != null && !oldHabitadOfMascotaListNewMascota.equals(habitad)) {
                        oldHabitadOfMascotaListNewMascota.getMascotaList().remove(mascotaListNewMascota);
                        oldHabitadOfMascotaListNewMascota = em.merge(oldHabitadOfMascotaListNewMascota);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = habitad.getIdHabitad();
                if (findHabitad(id) == null) {
                    throw new NonexistentEntityException("The habitad with id " + id + " no longer exists.");
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
            Habitad habitad;
            try {
                habitad = em.getReference(Habitad.class, id);
                habitad.getIdHabitad();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The habitad with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Mascota> mascotaListOrphanCheck = habitad.getMascotaList();
            for (Mascota mascotaListOrphanCheckMascota : mascotaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Habitad (" + habitad + ") cannot be destroyed since the Mascota " + mascotaListOrphanCheckMascota + " in its mascotaList field has a non-nullable habitad field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(habitad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Habitad> findHabitadEntities() {
        return findHabitadEntities(true, -1, -1);
    }

    public List<Habitad> findHabitadEntities(int maxResults, int firstResult) {
        return findHabitadEntities(false, maxResults, firstResult);
    }

    private List<Habitad> findHabitadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Habitad.class));
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

    public Habitad findHabitad(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Habitad.class, id);
        } finally {
            em.close();
        }
    }

    public int getHabitadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Habitad> rt = cq.from(Habitad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Habitad buscarHabitad(String nombre) {
        try {
            Query consulta = getEntityManager().createNamedQuery("Habitad.findByNombre");
            consulta.setParameter("nombre", nombre);
            return (Habitad)consulta.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    public Integer ultimo() {
        try {
            Query consulta = getEntityManager().createNamedQuery("Habitad.findAllDesc");
            consulta.setMaxResults(1);
            return ((Habitad)consulta.getSingleResult()).getIdHabitad()+1;
        } catch (Exception e) {
            return 1;
        }
    }
    
    public Habitad buscarPorNombre(String nombre) {
        try {
            Query consulta = getEntityManager().createNamedQuery("Habitad.findByNombre");
            consulta.setParameter("nombre", nombre);
            return (Habitad)consulta.getSingleResult();
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
