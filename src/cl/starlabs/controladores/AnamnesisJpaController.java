/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.controladores;

import cl.starlabs.controladores.exceptions.NonexistentEntityException;
import cl.starlabs.controladores.exceptions.PreexistingEntityException;
import cl.starlabs.modelo.Anamnesis;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.starlabs.modelo.Hospitalizacion;
import cl.starlabs.modelo.Mascota;
import cl.starlabs.modelo.Veterinario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor Manuel Araya
 */
public class AnamnesisJpaController implements Serializable {

    public AnamnesisJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Anamnesis anamnesis) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Hospitalizacion hospitalizacion = anamnesis.getHospitalizacion();
            if (hospitalizacion != null) {
                hospitalizacion = em.getReference(hospitalizacion.getClass(), hospitalizacion.getIdHospitalizacion());
                anamnesis.setHospitalizacion(hospitalizacion);
            }
            Mascota mascota = anamnesis.getMascota();
            if (mascota != null) {
                mascota = em.getReference(mascota.getClass(), mascota.getIdMascota());
                anamnesis.setMascota(mascota);
            }
            Veterinario veterinario = anamnesis.getVeterinario();
            if (veterinario != null) {
                veterinario = em.getReference(veterinario.getClass(), veterinario.getIdVeterinario());
                anamnesis.setVeterinario(veterinario);
            }
            em.persist(anamnesis);
            if (hospitalizacion != null) {
                hospitalizacion.getAnamnesisList().add(anamnesis);
                hospitalizacion = em.merge(hospitalizacion);
            }
            if (mascota != null) {
                mascota.getAnamnesisList().add(anamnesis);
                mascota = em.merge(mascota);
            }
            if (veterinario != null) {
                veterinario.getAnamnesisList().add(anamnesis);
                veterinario = em.merge(veterinario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAnamnesis(anamnesis.getIdAnamnesis()) != null) {
                throw new PreexistingEntityException("Anamnesis " + anamnesis + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Anamnesis anamnesis) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Anamnesis persistentAnamnesis = em.find(Anamnesis.class, anamnesis.getIdAnamnesis());
            Hospitalizacion hospitalizacionOld = persistentAnamnesis.getHospitalizacion();
            Hospitalizacion hospitalizacionNew = anamnesis.getHospitalizacion();
            Mascota mascotaOld = persistentAnamnesis.getMascota();
            Mascota mascotaNew = anamnesis.getMascota();
            Veterinario veterinarioOld = persistentAnamnesis.getVeterinario();
            Veterinario veterinarioNew = anamnesis.getVeterinario();
            if (hospitalizacionNew != null) {
                hospitalizacionNew = em.getReference(hospitalizacionNew.getClass(), hospitalizacionNew.getIdHospitalizacion());
                anamnesis.setHospitalizacion(hospitalizacionNew);
            }
            if (mascotaNew != null) {
                mascotaNew = em.getReference(mascotaNew.getClass(), mascotaNew.getIdMascota());
                anamnesis.setMascota(mascotaNew);
            }
            if (veterinarioNew != null) {
                veterinarioNew = em.getReference(veterinarioNew.getClass(), veterinarioNew.getIdVeterinario());
                anamnesis.setVeterinario(veterinarioNew);
            }
            anamnesis = em.merge(anamnesis);
            if (hospitalizacionOld != null && !hospitalizacionOld.equals(hospitalizacionNew)) {
                hospitalizacionOld.getAnamnesisList().remove(anamnesis);
                hospitalizacionOld = em.merge(hospitalizacionOld);
            }
            if (hospitalizacionNew != null && !hospitalizacionNew.equals(hospitalizacionOld)) {
                hospitalizacionNew.getAnamnesisList().add(anamnesis);
                hospitalizacionNew = em.merge(hospitalizacionNew);
            }
            if (mascotaOld != null && !mascotaOld.equals(mascotaNew)) {
                mascotaOld.getAnamnesisList().remove(anamnesis);
                mascotaOld = em.merge(mascotaOld);
            }
            if (mascotaNew != null && !mascotaNew.equals(mascotaOld)) {
                mascotaNew.getAnamnesisList().add(anamnesis);
                mascotaNew = em.merge(mascotaNew);
            }
            if (veterinarioOld != null && !veterinarioOld.equals(veterinarioNew)) {
                veterinarioOld.getAnamnesisList().remove(anamnesis);
                veterinarioOld = em.merge(veterinarioOld);
            }
            if (veterinarioNew != null && !veterinarioNew.equals(veterinarioOld)) {
                veterinarioNew.getAnamnesisList().add(anamnesis);
                veterinarioNew = em.merge(veterinarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = anamnesis.getIdAnamnesis();
                if (findAnamnesis(id) == null) {
                    throw new NonexistentEntityException("The anamnesis with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Anamnesis anamnesis;
            try {
                anamnesis = em.getReference(Anamnesis.class, id);
                anamnesis.getIdAnamnesis();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The anamnesis with id " + id + " no longer exists.", enfe);
            }
            Hospitalizacion hospitalizacion = anamnesis.getHospitalizacion();
            if (hospitalizacion != null) {
                hospitalizacion.getAnamnesisList().remove(anamnesis);
                hospitalizacion = em.merge(hospitalizacion);
            }
            Mascota mascota = anamnesis.getMascota();
            if (mascota != null) {
                mascota.getAnamnesisList().remove(anamnesis);
                mascota = em.merge(mascota);
            }
            Veterinario veterinario = anamnesis.getVeterinario();
            if (veterinario != null) {
                veterinario.getAnamnesisList().remove(anamnesis);
                veterinario = em.merge(veterinario);
            }
            em.remove(anamnesis);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Anamnesis> findAnamnesisEntities() {
        return findAnamnesisEntities(true, -1, -1);
    }

    public List<Anamnesis> findAnamnesisEntities(int maxResults, int firstResult) {
        return findAnamnesisEntities(false, maxResults, firstResult);
    }

    private List<Anamnesis> findAnamnesisEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Anamnesis.class));
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

    public Anamnesis findAnamnesis(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Anamnesis.class, id);
        } finally {
            em.close();
        }
    }

    public int getAnamnesisCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Anamnesis> rt = cq.from(Anamnesis.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
