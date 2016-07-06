/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.controladores;

import cl.starlabs.controladores.exceptions.NonexistentEntityException;
import cl.starlabs.controladores.exceptions.PreexistingEntityException;
import cl.starlabs.modelo.DetalleUsuarios;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.starlabs.modelo.Sucursal;
import cl.starlabs.modelo.Usuarios;
import cl.starlabs.modelo.Veterinario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor Manuel Araya
 */
public class DetalleUsuariosJpaController implements Serializable {

    public DetalleUsuariosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetalleUsuarios detalleUsuarios) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sucursal sucursal = detalleUsuarios.getSucursal();
            if (sucursal != null) {
                sucursal = em.getReference(sucursal.getClass(), sucursal.getIdSucursal());
                detalleUsuarios.setSucursal(sucursal);
            }
            Usuarios usuario = detalleUsuarios.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getId());
                detalleUsuarios.setUsuario(usuario);
            }
            Veterinario veterinario = detalleUsuarios.getVeterinario();
            if (veterinario != null) {
                veterinario = em.getReference(veterinario.getClass(), veterinario.getIdVeterinario());
                detalleUsuarios.setVeterinario(veterinario);
            }
            em.persist(detalleUsuarios);
            if (sucursal != null) {
                sucursal.getDetalleUsuariosList().add(detalleUsuarios);
                sucursal = em.merge(sucursal);
            }
            if (usuario != null) {
                usuario.getDetalleUsuariosList().add(detalleUsuarios);
                usuario = em.merge(usuario);
            }
            if (veterinario != null) {
                veterinario.getDetalleUsuariosList().add(detalleUsuarios);
                veterinario = em.merge(veterinario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDetalleUsuarios(detalleUsuarios.getId()) != null) {
                throw new PreexistingEntityException("DetalleUsuarios " + detalleUsuarios + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetalleUsuarios detalleUsuarios) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleUsuarios persistentDetalleUsuarios = em.find(DetalleUsuarios.class, detalleUsuarios.getId());
            Sucursal sucursalOld = persistentDetalleUsuarios.getSucursal();
            Sucursal sucursalNew = detalleUsuarios.getSucursal();
            Usuarios usuarioOld = persistentDetalleUsuarios.getUsuario();
            Usuarios usuarioNew = detalleUsuarios.getUsuario();
            Veterinario veterinarioOld = persistentDetalleUsuarios.getVeterinario();
            Veterinario veterinarioNew = detalleUsuarios.getVeterinario();
            if (sucursalNew != null) {
                sucursalNew = em.getReference(sucursalNew.getClass(), sucursalNew.getIdSucursal());
                detalleUsuarios.setSucursal(sucursalNew);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getId());
                detalleUsuarios.setUsuario(usuarioNew);
            }
            if (veterinarioNew != null) {
                veterinarioNew = em.getReference(veterinarioNew.getClass(), veterinarioNew.getIdVeterinario());
                detalleUsuarios.setVeterinario(veterinarioNew);
            }
            detalleUsuarios = em.merge(detalleUsuarios);
            if (sucursalOld != null && !sucursalOld.equals(sucursalNew)) {
                sucursalOld.getDetalleUsuariosList().remove(detalleUsuarios);
                sucursalOld = em.merge(sucursalOld);
            }
            if (sucursalNew != null && !sucursalNew.equals(sucursalOld)) {
                sucursalNew.getDetalleUsuariosList().add(detalleUsuarios);
                sucursalNew = em.merge(sucursalNew);
            }
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getDetalleUsuariosList().remove(detalleUsuarios);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getDetalleUsuariosList().add(detalleUsuarios);
                usuarioNew = em.merge(usuarioNew);
            }
            if (veterinarioOld != null && !veterinarioOld.equals(veterinarioNew)) {
                veterinarioOld.getDetalleUsuariosList().remove(detalleUsuarios);
                veterinarioOld = em.merge(veterinarioOld);
            }
            if (veterinarioNew != null && !veterinarioNew.equals(veterinarioOld)) {
                veterinarioNew.getDetalleUsuariosList().add(detalleUsuarios);
                veterinarioNew = em.merge(veterinarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detalleUsuarios.getId();
                if (findDetalleUsuarios(id) == null) {
                    throw new NonexistentEntityException("The detalleUsuarios with id " + id + " no longer exists.");
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
            DetalleUsuarios detalleUsuarios;
            try {
                detalleUsuarios = em.getReference(DetalleUsuarios.class, id);
                detalleUsuarios.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleUsuarios with id " + id + " no longer exists.", enfe);
            }
            Sucursal sucursal = detalleUsuarios.getSucursal();
            if (sucursal != null) {
                sucursal.getDetalleUsuariosList().remove(detalleUsuarios);
                sucursal = em.merge(sucursal);
            }
            Usuarios usuario = detalleUsuarios.getUsuario();
            if (usuario != null) {
                usuario.getDetalleUsuariosList().remove(detalleUsuarios);
                usuario = em.merge(usuario);
            }
            Veterinario veterinario = detalleUsuarios.getVeterinario();
            if (veterinario != null) {
                veterinario.getDetalleUsuariosList().remove(detalleUsuarios);
                veterinario = em.merge(veterinario);
            }
            em.remove(detalleUsuarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetalleUsuarios> findDetalleUsuariosEntities() {
        return findDetalleUsuariosEntities(true, -1, -1);
    }

    public List<DetalleUsuarios> findDetalleUsuariosEntities(int maxResults, int firstResult) {
        return findDetalleUsuariosEntities(false, maxResults, firstResult);
    }

    private List<DetalleUsuarios> findDetalleUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetalleUsuarios.class));
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

    public DetalleUsuarios findDetalleUsuarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetalleUsuarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleUsuariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetalleUsuarios> rt = cq.from(DetalleUsuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Integer ultimo() {
        try {
            Query consulta = getEntityManager().createNamedQuery("DetalleUsuarios.findAllDesc");
            consulta.setMaxResults(1);
            return ((DetalleUsuarios)consulta.getSingleResult()).getId()+1;
        } catch (Exception e) {
            return 1;
        }
    }
    
    public List<DetalleUsuarios> buscarPorUsuario(Usuarios u) {
        try {
            Query consulta = getEntityManager().createNamedQuery("DetalleUsuarios.findByUsuario");
            consulta.setParameter("usuario", u);
            return consulta.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<DetalleUsuarios> buscarPorSucursal(Sucursal s) {
        try {
            Query consulta = getEntityManager().createNamedQuery("DetalleUsuarios.findBySucursal");
            consulta.setParameter("sucursal", s);
            return consulta.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
    public DetalleUsuarios buscarPorVeterinario(Veterinario u) {
        try {
            Query consulta = getEntityManager().createNamedQuery("DetalleUsuarios.findByVeterinario");
            consulta.setParameter("veterinario", u);
            return (DetalleUsuarios)consulta.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
}
