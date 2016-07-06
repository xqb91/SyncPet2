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
import cl.starlabs.modelo.Procedimientos;
import cl.starlabs.modelo.TipoProcedimiento;
import cl.starlabs.modelo.Veterinario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor Manuel Araya
 */
public class ProcedimientosJpaController implements Serializable {

    public ProcedimientosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Procedimientos procedimientos) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Hospitalizacion hospitalizacion = procedimientos.getHospitalizacion();
            if (hospitalizacion != null) {
                hospitalizacion = em.getReference(hospitalizacion.getClass(), hospitalizacion.getIdHospitalizacion());
                procedimientos.setHospitalizacion(hospitalizacion);
            }
            Mascota mascota = procedimientos.getMascota();
            if (mascota != null) {
                mascota = em.getReference(mascota.getClass(), mascota.getIdMascota());
                procedimientos.setMascota(mascota);
            }
            TipoProcedimiento tipoProcedimiento = procedimientos.getTipoProcedimiento();
            if (tipoProcedimiento != null) {
                tipoProcedimiento = em.getReference(tipoProcedimiento.getClass(), tipoProcedimiento.getIdTipoProcedimiento());
                procedimientos.setTipoProcedimiento(tipoProcedimiento);
            }
            Veterinario veterinario = procedimientos.getVeterinario();
            if (veterinario != null) {
                veterinario = em.getReference(veterinario.getClass(), veterinario.getIdVeterinario());
                procedimientos.setVeterinario(veterinario);
            }
            em.persist(procedimientos);
            if (hospitalizacion != null) {
                hospitalizacion.getProcedimientosList().add(procedimientos);
                hospitalizacion = em.merge(hospitalizacion);
            }
            if (mascota != null) {
                mascota.getProcedimientosList().add(procedimientos);
                mascota = em.merge(mascota);
            }
            if (tipoProcedimiento != null) {
                tipoProcedimiento.getProcedimientosList().add(procedimientos);
                tipoProcedimiento = em.merge(tipoProcedimiento);
            }
            if (veterinario != null) {
                veterinario.getProcedimientosList().add(procedimientos);
                veterinario = em.merge(veterinario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProcedimientos(procedimientos.getIdProcedimiento()) != null) {
                throw new PreexistingEntityException("Procedimientos " + procedimientos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Procedimientos procedimientos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Procedimientos persistentProcedimientos = em.find(Procedimientos.class, procedimientos.getIdProcedimiento());
            Hospitalizacion hospitalizacionOld = persistentProcedimientos.getHospitalizacion();
            Hospitalizacion hospitalizacionNew = procedimientos.getHospitalizacion();
            Mascota mascotaOld = persistentProcedimientos.getMascota();
            Mascota mascotaNew = procedimientos.getMascota();
            TipoProcedimiento tipoProcedimientoOld = persistentProcedimientos.getTipoProcedimiento();
            TipoProcedimiento tipoProcedimientoNew = procedimientos.getTipoProcedimiento();
            Veterinario veterinarioOld = persistentProcedimientos.getVeterinario();
            Veterinario veterinarioNew = procedimientos.getVeterinario();
            if (hospitalizacionNew != null) {
                hospitalizacionNew = em.getReference(hospitalizacionNew.getClass(), hospitalizacionNew.getIdHospitalizacion());
                procedimientos.setHospitalizacion(hospitalizacionNew);
            }
            if (mascotaNew != null) {
                mascotaNew = em.getReference(mascotaNew.getClass(), mascotaNew.getIdMascota());
                procedimientos.setMascota(mascotaNew);
            }
            if (tipoProcedimientoNew != null) {
                tipoProcedimientoNew = em.getReference(tipoProcedimientoNew.getClass(), tipoProcedimientoNew.getIdTipoProcedimiento());
                procedimientos.setTipoProcedimiento(tipoProcedimientoNew);
            }
            if (veterinarioNew != null) {
                veterinarioNew = em.getReference(veterinarioNew.getClass(), veterinarioNew.getIdVeterinario());
                procedimientos.setVeterinario(veterinarioNew);
            }
            procedimientos = em.merge(procedimientos);
            if (hospitalizacionOld != null && !hospitalizacionOld.equals(hospitalizacionNew)) {
                hospitalizacionOld.getProcedimientosList().remove(procedimientos);
                hospitalizacionOld = em.merge(hospitalizacionOld);
            }
            if (hospitalizacionNew != null && !hospitalizacionNew.equals(hospitalizacionOld)) {
                hospitalizacionNew.getProcedimientosList().add(procedimientos);
                hospitalizacionNew = em.merge(hospitalizacionNew);
            }
            if (mascotaOld != null && !mascotaOld.equals(mascotaNew)) {
                mascotaOld.getProcedimientosList().remove(procedimientos);
                mascotaOld = em.merge(mascotaOld);
            }
            if (mascotaNew != null && !mascotaNew.equals(mascotaOld)) {
                mascotaNew.getProcedimientosList().add(procedimientos);
                mascotaNew = em.merge(mascotaNew);
            }
            if (tipoProcedimientoOld != null && !tipoProcedimientoOld.equals(tipoProcedimientoNew)) {
                tipoProcedimientoOld.getProcedimientosList().remove(procedimientos);
                tipoProcedimientoOld = em.merge(tipoProcedimientoOld);
            }
            if (tipoProcedimientoNew != null && !tipoProcedimientoNew.equals(tipoProcedimientoOld)) {
                tipoProcedimientoNew.getProcedimientosList().add(procedimientos);
                tipoProcedimientoNew = em.merge(tipoProcedimientoNew);
            }
            if (veterinarioOld != null && !veterinarioOld.equals(veterinarioNew)) {
                veterinarioOld.getProcedimientosList().remove(procedimientos);
                veterinarioOld = em.merge(veterinarioOld);
            }
            if (veterinarioNew != null && !veterinarioNew.equals(veterinarioOld)) {
                veterinarioNew.getProcedimientosList().add(procedimientos);
                veterinarioNew = em.merge(veterinarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = procedimientos.getIdProcedimiento();
                if (findProcedimientos(id) == null) {
                    throw new NonexistentEntityException("The procedimientos with id " + id + " no longer exists.");
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
            Procedimientos procedimientos;
            try {
                procedimientos = em.getReference(Procedimientos.class, id);
                procedimientos.getIdProcedimiento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The procedimientos with id " + id + " no longer exists.", enfe);
            }
            Hospitalizacion hospitalizacion = procedimientos.getHospitalizacion();
            if (hospitalizacion != null) {
                hospitalizacion.getProcedimientosList().remove(procedimientos);
                hospitalizacion = em.merge(hospitalizacion);
            }
            Mascota mascota = procedimientos.getMascota();
            if (mascota != null) {
                mascota.getProcedimientosList().remove(procedimientos);
                mascota = em.merge(mascota);
            }
            TipoProcedimiento tipoProcedimiento = procedimientos.getTipoProcedimiento();
            if (tipoProcedimiento != null) {
                tipoProcedimiento.getProcedimientosList().remove(procedimientos);
                tipoProcedimiento = em.merge(tipoProcedimiento);
            }
            Veterinario veterinario = procedimientos.getVeterinario();
            if (veterinario != null) {
                veterinario.getProcedimientosList().remove(procedimientos);
                veterinario = em.merge(veterinario);
            }
            em.remove(procedimientos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Procedimientos> findProcedimientosEntities() {
        return findProcedimientosEntities(true, -1, -1);
    }

    public List<Procedimientos> findProcedimientosEntities(int maxResults, int firstResult) {
        return findProcedimientosEntities(false, maxResults, firstResult);
    }

    private List<Procedimientos> findProcedimientosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Procedimientos.class));
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

    public Procedimientos findProcedimientos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Procedimientos.class, id);
        } finally {
            em.close();
        }
    }

    public int getProcedimientosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Procedimientos> rt = cq.from(Procedimientos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Integer ultimo(){
        try {
            Query consulta = getEntityManager().createNamedQuery("Procedimientos.findAllDesc");
            consulta.setMaxResults(1);
            return ((Procedimientos)consulta.getSingleResult()).getIdProcedimiento()+1;
        } catch (Exception e) {
            return 1;
        }
    }
}
