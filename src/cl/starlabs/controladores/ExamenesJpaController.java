/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.controladores;

import cl.starlabs.controladores.exceptions.NonexistentEntityException;
import cl.starlabs.controladores.exceptions.PreexistingEntityException;
import cl.starlabs.modelo.Examenes;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.starlabs.modelo.Hospitalizacion;
import cl.starlabs.modelo.Mascota;
import cl.starlabs.modelo.TipoExamen;
import cl.starlabs.modelo.Veterinario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor Manuel Araya
 */
public class ExamenesJpaController implements Serializable {

    public ExamenesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Examenes examenes) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Hospitalizacion hospitalizacion = examenes.getHospitalizacion();
            if (hospitalizacion != null) {
                hospitalizacion = em.getReference(hospitalizacion.getClass(), hospitalizacion.getIdHospitalizacion());
                examenes.setHospitalizacion(hospitalizacion);
            }
            Mascota mascota = examenes.getMascota();
            if (mascota != null) {
                mascota = em.getReference(mascota.getClass(), mascota.getIdMascota());
                examenes.setMascota(mascota);
            }
            TipoExamen tipoExamen = examenes.getTipoExamen();
            if (tipoExamen != null) {
                tipoExamen = em.getReference(tipoExamen.getClass(), tipoExamen.getIdTipoExamen());
                examenes.setTipoExamen(tipoExamen);
            }
            Veterinario veterinario = examenes.getVeterinario();
            if (veterinario != null) {
                veterinario = em.getReference(veterinario.getClass(), veterinario.getIdVeterinario());
                examenes.setVeterinario(veterinario);
            }
            em.persist(examenes);
            if (hospitalizacion != null) {
                hospitalizacion.getExamenesList().add(examenes);
                hospitalizacion = em.merge(hospitalizacion);
            }
            if (mascota != null) {
                mascota.getExamenesList().add(examenes);
                mascota = em.merge(mascota);
            }
            if (tipoExamen != null) {
                tipoExamen.getExamenesList().add(examenes);
                tipoExamen = em.merge(tipoExamen);
            }
            if (veterinario != null) {
                veterinario.getExamenesList().add(examenes);
                veterinario = em.merge(veterinario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findExamenes(examenes.getIdExamen()) != null) {
                throw new PreexistingEntityException("Examenes " + examenes + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Examenes examenes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Examenes persistentExamenes = em.find(Examenes.class, examenes.getIdExamen());
            Hospitalizacion hospitalizacionOld = persistentExamenes.getHospitalizacion();
            Hospitalizacion hospitalizacionNew = examenes.getHospitalizacion();
            Mascota mascotaOld = persistentExamenes.getMascota();
            Mascota mascotaNew = examenes.getMascota();
            TipoExamen tipoExamenOld = persistentExamenes.getTipoExamen();
            TipoExamen tipoExamenNew = examenes.getTipoExamen();
            Veterinario veterinarioOld = persistentExamenes.getVeterinario();
            Veterinario veterinarioNew = examenes.getVeterinario();
            if (hospitalizacionNew != null) {
                hospitalizacionNew = em.getReference(hospitalizacionNew.getClass(), hospitalizacionNew.getIdHospitalizacion());
                examenes.setHospitalizacion(hospitalizacionNew);
            }
            if (mascotaNew != null) {
                mascotaNew = em.getReference(mascotaNew.getClass(), mascotaNew.getIdMascota());
                examenes.setMascota(mascotaNew);
            }
            if (tipoExamenNew != null) {
                tipoExamenNew = em.getReference(tipoExamenNew.getClass(), tipoExamenNew.getIdTipoExamen());
                examenes.setTipoExamen(tipoExamenNew);
            }
            if (veterinarioNew != null) {
                veterinarioNew = em.getReference(veterinarioNew.getClass(), veterinarioNew.getIdVeterinario());
                examenes.setVeterinario(veterinarioNew);
            }
            examenes = em.merge(examenes);
            if (hospitalizacionOld != null && !hospitalizacionOld.equals(hospitalizacionNew)) {
                hospitalizacionOld.getExamenesList().remove(examenes);
                hospitalizacionOld = em.merge(hospitalizacionOld);
            }
            if (hospitalizacionNew != null && !hospitalizacionNew.equals(hospitalizacionOld)) {
                hospitalizacionNew.getExamenesList().add(examenes);
                hospitalizacionNew = em.merge(hospitalizacionNew);
            }
            if (mascotaOld != null && !mascotaOld.equals(mascotaNew)) {
                mascotaOld.getExamenesList().remove(examenes);
                mascotaOld = em.merge(mascotaOld);
            }
            if (mascotaNew != null && !mascotaNew.equals(mascotaOld)) {
                mascotaNew.getExamenesList().add(examenes);
                mascotaNew = em.merge(mascotaNew);
            }
            if (tipoExamenOld != null && !tipoExamenOld.equals(tipoExamenNew)) {
                tipoExamenOld.getExamenesList().remove(examenes);
                tipoExamenOld = em.merge(tipoExamenOld);
            }
            if (tipoExamenNew != null && !tipoExamenNew.equals(tipoExamenOld)) {
                tipoExamenNew.getExamenesList().add(examenes);
                tipoExamenNew = em.merge(tipoExamenNew);
            }
            if (veterinarioOld != null && !veterinarioOld.equals(veterinarioNew)) {
                veterinarioOld.getExamenesList().remove(examenes);
                veterinarioOld = em.merge(veterinarioOld);
            }
            if (veterinarioNew != null && !veterinarioNew.equals(veterinarioOld)) {
                veterinarioNew.getExamenesList().add(examenes);
                veterinarioNew = em.merge(veterinarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = examenes.getIdExamen();
                if (findExamenes(id) == null) {
                    throw new NonexistentEntityException("The examenes with id " + id + " no longer exists.");
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
            Examenes examenes;
            try {
                examenes = em.getReference(Examenes.class, id);
                examenes.getIdExamen();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The examenes with id " + id + " no longer exists.", enfe);
            }
            Hospitalizacion hospitalizacion = examenes.getHospitalizacion();
            if (hospitalizacion != null) {
                hospitalizacion.getExamenesList().remove(examenes);
                hospitalizacion = em.merge(hospitalizacion);
            }
            Mascota mascota = examenes.getMascota();
            if (mascota != null) {
                mascota.getExamenesList().remove(examenes);
                mascota = em.merge(mascota);
            }
            TipoExamen tipoExamen = examenes.getTipoExamen();
            if (tipoExamen != null) {
                tipoExamen.getExamenesList().remove(examenes);
                tipoExamen = em.merge(tipoExamen);
            }
            Veterinario veterinario = examenes.getVeterinario();
            if (veterinario != null) {
                veterinario.getExamenesList().remove(examenes);
                veterinario = em.merge(veterinario);
            }
            em.remove(examenes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Examenes> findExamenesEntities() {
        return findExamenesEntities(true, -1, -1);
    }

    public List<Examenes> findExamenesEntities(int maxResults, int firstResult) {
        return findExamenesEntities(false, maxResults, firstResult);
    }

    private List<Examenes> findExamenesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Examenes.class));
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

    public Examenes findExamenes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Examenes.class, id);
        } finally {
            em.close();
        }
    }

    public int getExamenesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Examenes> rt = cq.from(Examenes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Integer ultimo(){
        try {
            Query consulta = getEntityManager().createNamedQuery("Examenes.findAllDesc");
            consulta.setMaxResults(1);
            return ((Examenes)consulta.getSingleResult()).getIdExamen()+1;
        } catch (Exception e) {
            return 1;
        }
    }
}
