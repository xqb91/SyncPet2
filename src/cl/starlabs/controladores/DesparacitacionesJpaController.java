/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.controladores;

import cl.starlabs.controladores.exceptions.NonexistentEntityException;
import cl.starlabs.controladores.exceptions.PreexistingEntityException;
import cl.starlabs.modelo.Desparacitaciones;
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
public class DesparacitacionesJpaController implements Serializable {

    public DesparacitacionesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Desparacitaciones desparacitaciones) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Hospitalizacion hospitalizacion = desparacitaciones.getHospitalizacion();
            if (hospitalizacion != null) {
                hospitalizacion = em.getReference(hospitalizacion.getClass(), hospitalizacion.getIdHospitalizacion());
                desparacitaciones.setHospitalizacion(hospitalizacion);
            }
            Mascota mascota = desparacitaciones.getMascota();
            if (mascota != null) {
                mascota = em.getReference(mascota.getClass(), mascota.getIdMascota());
                desparacitaciones.setMascota(mascota);
            }
            Veterinario especialista = desparacitaciones.getEspecialista();
            if (especialista != null) {
                especialista = em.getReference(especialista.getClass(), especialista.getIdVeterinario());
                desparacitaciones.setEspecialista(especialista);
            }
            em.persist(desparacitaciones);
            if (hospitalizacion != null) {
                hospitalizacion.getDesparacitacionesList().add(desparacitaciones);
                hospitalizacion = em.merge(hospitalizacion);
            }
            if (mascota != null) {
                mascota.getDesparacitacionesList().add(desparacitaciones);
                mascota = em.merge(mascota);
            }
            if (especialista != null) {
                especialista.getDesparacitacionesList().add(desparacitaciones);
                especialista = em.merge(especialista);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDesparacitaciones(desparacitaciones.getIdDesparacitacion()) != null) {
                throw new PreexistingEntityException("Desparacitaciones " + desparacitaciones + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Desparacitaciones desparacitaciones) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Desparacitaciones persistentDesparacitaciones = em.find(Desparacitaciones.class, desparacitaciones.getIdDesparacitacion());
            Hospitalizacion hospitalizacionOld = persistentDesparacitaciones.getHospitalizacion();
            Hospitalizacion hospitalizacionNew = desparacitaciones.getHospitalizacion();
            Mascota mascotaOld = persistentDesparacitaciones.getMascota();
            Mascota mascotaNew = desparacitaciones.getMascota();
            Veterinario especialistaOld = persistentDesparacitaciones.getEspecialista();
            Veterinario especialistaNew = desparacitaciones.getEspecialista();
            if (hospitalizacionNew != null) {
                hospitalizacionNew = em.getReference(hospitalizacionNew.getClass(), hospitalizacionNew.getIdHospitalizacion());
                desparacitaciones.setHospitalizacion(hospitalizacionNew);
            }
            if (mascotaNew != null) {
                mascotaNew = em.getReference(mascotaNew.getClass(), mascotaNew.getIdMascota());
                desparacitaciones.setMascota(mascotaNew);
            }
            if (especialistaNew != null) {
                especialistaNew = em.getReference(especialistaNew.getClass(), especialistaNew.getIdVeterinario());
                desparacitaciones.setEspecialista(especialistaNew);
            }
            desparacitaciones = em.merge(desparacitaciones);
            if (hospitalizacionOld != null && !hospitalizacionOld.equals(hospitalizacionNew)) {
                hospitalizacionOld.getDesparacitacionesList().remove(desparacitaciones);
                hospitalizacionOld = em.merge(hospitalizacionOld);
            }
            if (hospitalizacionNew != null && !hospitalizacionNew.equals(hospitalizacionOld)) {
                hospitalizacionNew.getDesparacitacionesList().add(desparacitaciones);
                hospitalizacionNew = em.merge(hospitalizacionNew);
            }
            if (mascotaOld != null && !mascotaOld.equals(mascotaNew)) {
                mascotaOld.getDesparacitacionesList().remove(desparacitaciones);
                mascotaOld = em.merge(mascotaOld);
            }
            if (mascotaNew != null && !mascotaNew.equals(mascotaOld)) {
                mascotaNew.getDesparacitacionesList().add(desparacitaciones);
                mascotaNew = em.merge(mascotaNew);
            }
            if (especialistaOld != null && !especialistaOld.equals(especialistaNew)) {
                especialistaOld.getDesparacitacionesList().remove(desparacitaciones);
                especialistaOld = em.merge(especialistaOld);
            }
            if (especialistaNew != null && !especialistaNew.equals(especialistaOld)) {
                especialistaNew.getDesparacitacionesList().add(desparacitaciones);
                especialistaNew = em.merge(especialistaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = desparacitaciones.getIdDesparacitacion();
                if (findDesparacitaciones(id) == null) {
                    throw new NonexistentEntityException("The desparacitaciones with id " + id + " no longer exists.");
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
            Desparacitaciones desparacitaciones;
            try {
                desparacitaciones = em.getReference(Desparacitaciones.class, id);
                desparacitaciones.getIdDesparacitacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The desparacitaciones with id " + id + " no longer exists.", enfe);
            }
            Hospitalizacion hospitalizacion = desparacitaciones.getHospitalizacion();
            if (hospitalizacion != null) {
                hospitalizacion.getDesparacitacionesList().remove(desparacitaciones);
                hospitalizacion = em.merge(hospitalizacion);
            }
            Mascota mascota = desparacitaciones.getMascota();
            if (mascota != null) {
                mascota.getDesparacitacionesList().remove(desparacitaciones);
                mascota = em.merge(mascota);
            }
            Veterinario especialista = desparacitaciones.getEspecialista();
            if (especialista != null) {
                especialista.getDesparacitacionesList().remove(desparacitaciones);
                especialista = em.merge(especialista);
            }
            em.remove(desparacitaciones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Desparacitaciones> findDesparacitacionesEntities() {
        return findDesparacitacionesEntities(true, -1, -1);
    }

    public List<Desparacitaciones> findDesparacitacionesEntities(int maxResults, int firstResult) {
        return findDesparacitacionesEntities(false, maxResults, firstResult);
    }

    private List<Desparacitaciones> findDesparacitacionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Desparacitaciones.class));
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

    public Desparacitaciones findDesparacitaciones(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Desparacitaciones.class, id);
        } finally {
            em.close();
        }
    }

    public int getDesparacitacionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Desparacitaciones> rt = cq.from(Desparacitaciones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Integer ultimo(){
        try {
            Query consulta = getEntityManager().createNamedQuery("Desparacitaciones.findAllDesc");
            consulta.setMaxResults(1);
            return ((Desparacitaciones)consulta.getSingleResult()).getIdDesparacitacion()+1;
        } catch (Exception e) {
            return 1;
        }
    }
}
