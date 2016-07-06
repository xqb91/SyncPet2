/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.controladores;

import cl.starlabs.controladores.exceptions.NonexistentEntityException;
import cl.starlabs.controladores.exceptions.PreexistingEntityException;
import cl.starlabs.modelo.Contraindicaciones;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.starlabs.modelo.Hospitalizacion;
import cl.starlabs.modelo.Mascota;
import cl.starlabs.modelo.TipoContraindicacion;
import cl.starlabs.modelo.Veterinario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor Manuel Araya
 */
public class ContraindicacionesJpaController implements Serializable {

    public ContraindicacionesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Contraindicaciones contraindicaciones) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Hospitalizacion hospitalizacion = contraindicaciones.getHospitalizacion();
            if (hospitalizacion != null) {
                hospitalizacion = em.getReference(hospitalizacion.getClass(), hospitalizacion.getIdHospitalizacion());
                contraindicaciones.setHospitalizacion(hospitalizacion);
            }
            Mascota mascota = contraindicaciones.getMascota();
            if (mascota != null) {
                mascota = em.getReference(mascota.getClass(), mascota.getIdMascota());
                contraindicaciones.setMascota(mascota);
            }
            TipoContraindicacion tipoContraindicicacion = contraindicaciones.getTipoContraindicicacion();
            if (tipoContraindicicacion != null) {
                tipoContraindicicacion = em.getReference(tipoContraindicicacion.getClass(), tipoContraindicicacion.getIdTipoContraindicacion());
                contraindicaciones.setTipoContraindicicacion(tipoContraindicicacion);
            }
            Veterinario veterinario = contraindicaciones.getVeterinario();
            if (veterinario != null) {
                veterinario = em.getReference(veterinario.getClass(), veterinario.getIdVeterinario());
                contraindicaciones.setVeterinario(veterinario);
            }
            em.persist(contraindicaciones);
            if (hospitalizacion != null) {
                hospitalizacion.getContraindicacionesList().add(contraindicaciones);
                hospitalizacion = em.merge(hospitalizacion);
            }
            if (mascota != null) {
                mascota.getContraindicacionesList().add(contraindicaciones);
                mascota = em.merge(mascota);
            }
            if (tipoContraindicicacion != null) {
                tipoContraindicicacion.getContraindicacionesList().add(contraindicaciones);
                tipoContraindicicacion = em.merge(tipoContraindicicacion);
            }
            if (veterinario != null) {
                veterinario.getContraindicacionesList().add(contraindicaciones);
                veterinario = em.merge(veterinario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findContraindicaciones(contraindicaciones.getIdContraindicacion()) != null) {
                throw new PreexistingEntityException("Contraindicaciones " + contraindicaciones + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Contraindicaciones contraindicaciones) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Contraindicaciones persistentContraindicaciones = em.find(Contraindicaciones.class, contraindicaciones.getIdContraindicacion());
            Hospitalizacion hospitalizacionOld = persistentContraindicaciones.getHospitalizacion();
            Hospitalizacion hospitalizacionNew = contraindicaciones.getHospitalizacion();
            Mascota mascotaOld = persistentContraindicaciones.getMascota();
            Mascota mascotaNew = contraindicaciones.getMascota();
            TipoContraindicacion tipoContraindicicacionOld = persistentContraindicaciones.getTipoContraindicicacion();
            TipoContraindicacion tipoContraindicicacionNew = contraindicaciones.getTipoContraindicicacion();
            Veterinario veterinarioOld = persistentContraindicaciones.getVeterinario();
            Veterinario veterinarioNew = contraindicaciones.getVeterinario();
            if (hospitalizacionNew != null) {
                hospitalizacionNew = em.getReference(hospitalizacionNew.getClass(), hospitalizacionNew.getIdHospitalizacion());
                contraindicaciones.setHospitalizacion(hospitalizacionNew);
            }
            if (mascotaNew != null) {
                mascotaNew = em.getReference(mascotaNew.getClass(), mascotaNew.getIdMascota());
                contraindicaciones.setMascota(mascotaNew);
            }
            if (tipoContraindicicacionNew != null) {
                tipoContraindicicacionNew = em.getReference(tipoContraindicicacionNew.getClass(), tipoContraindicicacionNew.getIdTipoContraindicacion());
                contraindicaciones.setTipoContraindicicacion(tipoContraindicicacionNew);
            }
            if (veterinarioNew != null) {
                veterinarioNew = em.getReference(veterinarioNew.getClass(), veterinarioNew.getIdVeterinario());
                contraindicaciones.setVeterinario(veterinarioNew);
            }
            contraindicaciones = em.merge(contraindicaciones);
            if (hospitalizacionOld != null && !hospitalizacionOld.equals(hospitalizacionNew)) {
                hospitalizacionOld.getContraindicacionesList().remove(contraindicaciones);
                hospitalizacionOld = em.merge(hospitalizacionOld);
            }
            if (hospitalizacionNew != null && !hospitalizacionNew.equals(hospitalizacionOld)) {
                hospitalizacionNew.getContraindicacionesList().add(contraindicaciones);
                hospitalizacionNew = em.merge(hospitalizacionNew);
            }
            if (mascotaOld != null && !mascotaOld.equals(mascotaNew)) {
                mascotaOld.getContraindicacionesList().remove(contraindicaciones);
                mascotaOld = em.merge(mascotaOld);
            }
            if (mascotaNew != null && !mascotaNew.equals(mascotaOld)) {
                mascotaNew.getContraindicacionesList().add(contraindicaciones);
                mascotaNew = em.merge(mascotaNew);
            }
            if (tipoContraindicicacionOld != null && !tipoContraindicicacionOld.equals(tipoContraindicicacionNew)) {
                tipoContraindicicacionOld.getContraindicacionesList().remove(contraindicaciones);
                tipoContraindicicacionOld = em.merge(tipoContraindicicacionOld);
            }
            if (tipoContraindicicacionNew != null && !tipoContraindicicacionNew.equals(tipoContraindicicacionOld)) {
                tipoContraindicicacionNew.getContraindicacionesList().add(contraindicaciones);
                tipoContraindicicacionNew = em.merge(tipoContraindicicacionNew);
            }
            if (veterinarioOld != null && !veterinarioOld.equals(veterinarioNew)) {
                veterinarioOld.getContraindicacionesList().remove(contraindicaciones);
                veterinarioOld = em.merge(veterinarioOld);
            }
            if (veterinarioNew != null && !veterinarioNew.equals(veterinarioOld)) {
                veterinarioNew.getContraindicacionesList().add(contraindicaciones);
                veterinarioNew = em.merge(veterinarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = contraindicaciones.getIdContraindicacion();
                if (findContraindicaciones(id) == null) {
                    throw new NonexistentEntityException("The contraindicaciones with id " + id + " no longer exists.");
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
            Contraindicaciones contraindicaciones;
            try {
                contraindicaciones = em.getReference(Contraindicaciones.class, id);
                contraindicaciones.getIdContraindicacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contraindicaciones with id " + id + " no longer exists.", enfe);
            }
            Hospitalizacion hospitalizacion = contraindicaciones.getHospitalizacion();
            if (hospitalizacion != null) {
                hospitalizacion.getContraindicacionesList().remove(contraindicaciones);
                hospitalizacion = em.merge(hospitalizacion);
            }
            Mascota mascota = contraindicaciones.getMascota();
            if (mascota != null) {
                mascota.getContraindicacionesList().remove(contraindicaciones);
                mascota = em.merge(mascota);
            }
            TipoContraindicacion tipoContraindicicacion = contraindicaciones.getTipoContraindicicacion();
            if (tipoContraindicicacion != null) {
                tipoContraindicicacion.getContraindicacionesList().remove(contraindicaciones);
                tipoContraindicicacion = em.merge(tipoContraindicicacion);
            }
            Veterinario veterinario = contraindicaciones.getVeterinario();
            if (veterinario != null) {
                veterinario.getContraindicacionesList().remove(contraindicaciones);
                veterinario = em.merge(veterinario);
            }
            em.remove(contraindicaciones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Contraindicaciones> findContraindicacionesEntities() {
        return findContraindicacionesEntities(true, -1, -1);
    }

    public List<Contraindicaciones> findContraindicacionesEntities(int maxResults, int firstResult) {
        return findContraindicacionesEntities(false, maxResults, firstResult);
    }

    private List<Contraindicaciones> findContraindicacionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Contraindicaciones.class));
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

    public Contraindicaciones findContraindicaciones(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Contraindicaciones.class, id);
        } finally {
            em.close();
        }
    }

    public int getContraindicacionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Contraindicaciones> rt = cq.from(Contraindicaciones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Integer ultimo(){
        try {
            Query consulta = getEntityManager().createNamedQuery("Contraindicaciones.findAllDesc");
            consulta.setMaxResults(1);
            return ((Contraindicaciones)consulta.getSingleResult()).getIdContraindicacion()+1;
        } catch (Exception e) {
            return 1;
        }
    }
}
