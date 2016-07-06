/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.controladores;

import cl.starlabs.controladores.exceptions.NonexistentEntityException;
import cl.starlabs.controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.starlabs.modelo.Hospitalizacion;
import cl.starlabs.modelo.Mascota;
import cl.starlabs.modelo.Patologias;
import cl.starlabs.modelo.TipoPatologia;
import cl.starlabs.modelo.Veterinario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor Manuel Araya
 */
public class PatologiasJpaController implements Serializable {

    public PatologiasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Patologias patologias) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Hospitalizacion hospitalizacion = patologias.getHospitalizacion();
            if (hospitalizacion != null) {
                hospitalizacion = em.getReference(hospitalizacion.getClass(), hospitalizacion.getIdHospitalizacion());
                patologias.setHospitalizacion(hospitalizacion);
            }
            Mascota mascota = patologias.getMascota();
            if (mascota != null) {
                mascota = em.getReference(mascota.getClass(), mascota.getIdMascota());
                patologias.setMascota(mascota);
            }
            TipoPatologia tipoPatologia = patologias.getTipoPatologia();
            if (tipoPatologia != null) {
                tipoPatologia = em.getReference(tipoPatologia.getClass(), tipoPatologia.getIdTipoPatologia());
                patologias.setTipoPatologia(tipoPatologia);
            }
            Veterinario veterinario = patologias.getVeterinario();
            if (veterinario != null) {
                veterinario = em.getReference(veterinario.getClass(), veterinario.getIdVeterinario());
                patologias.setVeterinario(veterinario);
            }
            em.persist(patologias);
            if (hospitalizacion != null) {
                hospitalizacion.getPatologiasList().add(patologias);
                hospitalizacion = em.merge(hospitalizacion);
            }
            if (mascota != null) {
                mascota.getPatologiasList().add(patologias);
                mascota = em.merge(mascota);
            }
            if (tipoPatologia != null) {
                tipoPatologia.getPatologiasList().add(patologias);
                tipoPatologia = em.merge(tipoPatologia);
            }
            if (veterinario != null) {
                veterinario.getPatologiasList().add(patologias);
                veterinario = em.merge(veterinario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPatologias(patologias.getIdPatologia()) != null) {
                throw new PreexistingEntityException("Patologias " + patologias + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Patologias patologias) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Patologias persistentPatologias = em.find(Patologias.class, patologias.getIdPatologia());
            Hospitalizacion hospitalizacionOld = persistentPatologias.getHospitalizacion();
            Hospitalizacion hospitalizacionNew = patologias.getHospitalizacion();
            Mascota mascotaOld = persistentPatologias.getMascota();
            Mascota mascotaNew = patologias.getMascota();
            TipoPatologia tipoPatologiaOld = persistentPatologias.getTipoPatologia();
            TipoPatologia tipoPatologiaNew = patologias.getTipoPatologia();
            Veterinario veterinarioOld = persistentPatologias.getVeterinario();
            Veterinario veterinarioNew = patologias.getVeterinario();
            if (hospitalizacionNew != null) {
                hospitalizacionNew = em.getReference(hospitalizacionNew.getClass(), hospitalizacionNew.getIdHospitalizacion());
                patologias.setHospitalizacion(hospitalizacionNew);
            }
            if (mascotaNew != null) {
                mascotaNew = em.getReference(mascotaNew.getClass(), mascotaNew.getIdMascota());
                patologias.setMascota(mascotaNew);
            }
            if (tipoPatologiaNew != null) {
                tipoPatologiaNew = em.getReference(tipoPatologiaNew.getClass(), tipoPatologiaNew.getIdTipoPatologia());
                patologias.setTipoPatologia(tipoPatologiaNew);
            }
            if (veterinarioNew != null) {
                veterinarioNew = em.getReference(veterinarioNew.getClass(), veterinarioNew.getIdVeterinario());
                patologias.setVeterinario(veterinarioNew);
            }
            patologias = em.merge(patologias);
            if (hospitalizacionOld != null && !hospitalizacionOld.equals(hospitalizacionNew)) {
                hospitalizacionOld.getPatologiasList().remove(patologias);
                hospitalizacionOld = em.merge(hospitalizacionOld);
            }
            if (hospitalizacionNew != null && !hospitalizacionNew.equals(hospitalizacionOld)) {
                hospitalizacionNew.getPatologiasList().add(patologias);
                hospitalizacionNew = em.merge(hospitalizacionNew);
            }
            if (mascotaOld != null && !mascotaOld.equals(mascotaNew)) {
                mascotaOld.getPatologiasList().remove(patologias);
                mascotaOld = em.merge(mascotaOld);
            }
            if (mascotaNew != null && !mascotaNew.equals(mascotaOld)) {
                mascotaNew.getPatologiasList().add(patologias);
                mascotaNew = em.merge(mascotaNew);
            }
            if (tipoPatologiaOld != null && !tipoPatologiaOld.equals(tipoPatologiaNew)) {
                tipoPatologiaOld.getPatologiasList().remove(patologias);
                tipoPatologiaOld = em.merge(tipoPatologiaOld);
            }
            if (tipoPatologiaNew != null && !tipoPatologiaNew.equals(tipoPatologiaOld)) {
                tipoPatologiaNew.getPatologiasList().add(patologias);
                tipoPatologiaNew = em.merge(tipoPatologiaNew);
            }
            if (veterinarioOld != null && !veterinarioOld.equals(veterinarioNew)) {
                veterinarioOld.getPatologiasList().remove(patologias);
                veterinarioOld = em.merge(veterinarioOld);
            }
            if (veterinarioNew != null && !veterinarioNew.equals(veterinarioOld)) {
                veterinarioNew.getPatologiasList().add(patologias);
                veterinarioNew = em.merge(veterinarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = patologias.getIdPatologia();
                if (findPatologias(id) == null) {
                    throw new NonexistentEntityException("The patologias with id " + id + " no longer exists.");
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
            Patologias patologias;
            try {
                patologias = em.getReference(Patologias.class, id);
                patologias.getIdPatologia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The patologias with id " + id + " no longer exists.", enfe);
            }
            Hospitalizacion hospitalizacion = patologias.getHospitalizacion();
            if (hospitalizacion != null) {
                hospitalizacion.getPatologiasList().remove(patologias);
                hospitalizacion = em.merge(hospitalizacion);
            }
            Mascota mascota = patologias.getMascota();
            if (mascota != null) {
                mascota.getPatologiasList().remove(patologias);
                mascota = em.merge(mascota);
            }
            TipoPatologia tipoPatologia = patologias.getTipoPatologia();
            if (tipoPatologia != null) {
                tipoPatologia.getPatologiasList().remove(patologias);
                tipoPatologia = em.merge(tipoPatologia);
            }
            Veterinario veterinario = patologias.getVeterinario();
            if (veterinario != null) {
                veterinario.getPatologiasList().remove(patologias);
                veterinario = em.merge(veterinario);
            }
            em.remove(patologias);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Patologias> findPatologiasEntities() {
        return findPatologiasEntities(true, -1, -1);
    }

    public List<Patologias> findPatologiasEntities(int maxResults, int firstResult) {
        return findPatologiasEntities(false, maxResults, firstResult);
    }

    private List<Patologias> findPatologiasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Patologias.class));
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

    public Patologias findPatologias(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Patologias.class, id);
        } finally {
            em.close();
        }
    }

    public int getPatologiasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Patologias> rt = cq.from(Patologias.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Integer ultimo(){
        try {
            Query consulta = getEntityManager().createNamedQuery("Patologias.findAllDesc");
            consulta.setMaxResults(1);
            return ((Patologias)consulta.getSingleResult()).getIdPatologia()+1;
        } catch (Exception e) {
            return 1;
        }
    }
    
}
