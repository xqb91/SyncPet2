/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.controladores;

import cl.starlabs.controladores.exceptions.NonexistentEntityException;
import cl.starlabs.controladores.exceptions.PreexistingEntityException;
import cl.starlabs.modelo.Historialvacunas;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.starlabs.modelo.Hospitalizacion;
import cl.starlabs.modelo.Mascota;
import cl.starlabs.modelo.Vacunas;
import cl.starlabs.modelo.Veterinario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor Manuel Araya
 */
public class HistorialvacunasJpaController implements Serializable {

    public HistorialvacunasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Historialvacunas historialvacunas) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Hospitalizacion hospitalizacion = historialvacunas.getHospitalizacion();
            if (hospitalizacion != null) {
                hospitalizacion = em.getReference(hospitalizacion.getClass(), hospitalizacion.getIdHospitalizacion());
                historialvacunas.setHospitalizacion(hospitalizacion);
            }
            Mascota mascota = historialvacunas.getMascota();
            if (mascota != null) {
                mascota = em.getReference(mascota.getClass(), mascota.getIdMascota());
                historialvacunas.setMascota(mascota);
            }
            Vacunas vacuna = historialvacunas.getVacuna();
            if (vacuna != null) {
                vacuna = em.getReference(vacuna.getClass(), vacuna.getIdVacuna());
                historialvacunas.setVacuna(vacuna);
            }
            Veterinario veterinario = historialvacunas.getVeterinario();
            if (veterinario != null) {
                veterinario = em.getReference(veterinario.getClass(), veterinario.getIdVeterinario());
                historialvacunas.setVeterinario(veterinario);
            }
            em.persist(historialvacunas);
            if (hospitalizacion != null) {
                hospitalizacion.getHistorialvacunasList().add(historialvacunas);
                hospitalizacion = em.merge(hospitalizacion);
            }
            if (mascota != null) {
                mascota.getHistorialvacunasList().add(historialvacunas);
                mascota = em.merge(mascota);
            }
            if (vacuna != null) {
                vacuna.getHistorialvacunasList().add(historialvacunas);
                vacuna = em.merge(vacuna);
            }
            if (veterinario != null) {
                veterinario.getHistorialvacunasList().add(historialvacunas);
                veterinario = em.merge(veterinario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findHistorialvacunas(historialvacunas.getIdEvento()) != null) {
                throw new PreexistingEntityException("Historialvacunas " + historialvacunas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Historialvacunas historialvacunas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Historialvacunas persistentHistorialvacunas = em.find(Historialvacunas.class, historialvacunas.getIdEvento());
            Hospitalizacion hospitalizacionOld = persistentHistorialvacunas.getHospitalizacion();
            Hospitalizacion hospitalizacionNew = historialvacunas.getHospitalizacion();
            Mascota mascotaOld = persistentHistorialvacunas.getMascota();
            Mascota mascotaNew = historialvacunas.getMascota();
            Vacunas vacunaOld = persistentHistorialvacunas.getVacuna();
            Vacunas vacunaNew = historialvacunas.getVacuna();
            Veterinario veterinarioOld = persistentHistorialvacunas.getVeterinario();
            Veterinario veterinarioNew = historialvacunas.getVeterinario();
            if (hospitalizacionNew != null) {
                hospitalizacionNew = em.getReference(hospitalizacionNew.getClass(), hospitalizacionNew.getIdHospitalizacion());
                historialvacunas.setHospitalizacion(hospitalizacionNew);
            }
            if (mascotaNew != null) {
                mascotaNew = em.getReference(mascotaNew.getClass(), mascotaNew.getIdMascota());
                historialvacunas.setMascota(mascotaNew);
            }
            if (vacunaNew != null) {
                vacunaNew = em.getReference(vacunaNew.getClass(), vacunaNew.getIdVacuna());
                historialvacunas.setVacuna(vacunaNew);
            }
            if (veterinarioNew != null) {
                veterinarioNew = em.getReference(veterinarioNew.getClass(), veterinarioNew.getIdVeterinario());
                historialvacunas.setVeterinario(veterinarioNew);
            }
            historialvacunas = em.merge(historialvacunas);
            if (hospitalizacionOld != null && !hospitalizacionOld.equals(hospitalizacionNew)) {
                hospitalizacionOld.getHistorialvacunasList().remove(historialvacunas);
                hospitalizacionOld = em.merge(hospitalizacionOld);
            }
            if (hospitalizacionNew != null && !hospitalizacionNew.equals(hospitalizacionOld)) {
                hospitalizacionNew.getHistorialvacunasList().add(historialvacunas);
                hospitalizacionNew = em.merge(hospitalizacionNew);
            }
            if (mascotaOld != null && !mascotaOld.equals(mascotaNew)) {
                mascotaOld.getHistorialvacunasList().remove(historialvacunas);
                mascotaOld = em.merge(mascotaOld);
            }
            if (mascotaNew != null && !mascotaNew.equals(mascotaOld)) {
                mascotaNew.getHistorialvacunasList().add(historialvacunas);
                mascotaNew = em.merge(mascotaNew);
            }
            if (vacunaOld != null && !vacunaOld.equals(vacunaNew)) {
                vacunaOld.getHistorialvacunasList().remove(historialvacunas);
                vacunaOld = em.merge(vacunaOld);
            }
            if (vacunaNew != null && !vacunaNew.equals(vacunaOld)) {
                vacunaNew.getHistorialvacunasList().add(historialvacunas);
                vacunaNew = em.merge(vacunaNew);
            }
            if (veterinarioOld != null && !veterinarioOld.equals(veterinarioNew)) {
                veterinarioOld.getHistorialvacunasList().remove(historialvacunas);
                veterinarioOld = em.merge(veterinarioOld);
            }
            if (veterinarioNew != null && !veterinarioNew.equals(veterinarioOld)) {
                veterinarioNew.getHistorialvacunasList().add(historialvacunas);
                veterinarioNew = em.merge(veterinarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = historialvacunas.getIdEvento();
                if (findHistorialvacunas(id) == null) {
                    throw new NonexistentEntityException("The historialvacunas with id " + id + " no longer exists.");
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
            Historialvacunas historialvacunas;
            try {
                historialvacunas = em.getReference(Historialvacunas.class, id);
                historialvacunas.getIdEvento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historialvacunas with id " + id + " no longer exists.", enfe);
            }
            Hospitalizacion hospitalizacion = historialvacunas.getHospitalizacion();
            if (hospitalizacion != null) {
                hospitalizacion.getHistorialvacunasList().remove(historialvacunas);
                hospitalizacion = em.merge(hospitalizacion);
            }
            Mascota mascota = historialvacunas.getMascota();
            if (mascota != null) {
                mascota.getHistorialvacunasList().remove(historialvacunas);
                mascota = em.merge(mascota);
            }
            Vacunas vacuna = historialvacunas.getVacuna();
            if (vacuna != null) {
                vacuna.getHistorialvacunasList().remove(historialvacunas);
                vacuna = em.merge(vacuna);
            }
            Veterinario veterinario = historialvacunas.getVeterinario();
            if (veterinario != null) {
                veterinario.getHistorialvacunasList().remove(historialvacunas);
                veterinario = em.merge(veterinario);
            }
            em.remove(historialvacunas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Historialvacunas> findHistorialvacunasEntities() {
        return findHistorialvacunasEntities(true, -1, -1);
    }

    public List<Historialvacunas> findHistorialvacunasEntities(int maxResults, int firstResult) {
        return findHistorialvacunasEntities(false, maxResults, firstResult);
    }

    private List<Historialvacunas> findHistorialvacunasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Historialvacunas.class));
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

    public Historialvacunas findHistorialvacunas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Historialvacunas.class, id);
        } finally {
            em.close();
        }
    }

    public int getHistorialvacunasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Historialvacunas> rt = cq.from(Historialvacunas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Integer ultimo(){
        try {
            Query consulta = getEntityManager().createNamedQuery("Historialvacunas.findAllDesc");
            consulta.setMaxResults(1);
            return ((Historialvacunas)consulta.getSingleResult()).getIdEvento()+1;
        } catch (Exception e) {
            return 1;
        }
    }
}
