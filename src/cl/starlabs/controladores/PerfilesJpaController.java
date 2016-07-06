/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.controladores;

import cl.starlabs.controladores.exceptions.IllegalOrphanException;
import cl.starlabs.controladores.exceptions.NonexistentEntityException;
import cl.starlabs.controladores.exceptions.PreexistingEntityException;
import cl.starlabs.modelo.Perfiles;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.starlabs.modelo.Usuarios;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor Manuel Araya
 */
public class PerfilesJpaController implements Serializable {

    public PerfilesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Perfiles perfiles) throws PreexistingEntityException, Exception {
        if (perfiles.getUsuariosList() == null) {
            perfiles.setUsuariosList(new ArrayList<Usuarios>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Usuarios> attachedUsuariosList = new ArrayList<Usuarios>();
            for (Usuarios usuariosListUsuariosToAttach : perfiles.getUsuariosList()) {
                usuariosListUsuariosToAttach = em.getReference(usuariosListUsuariosToAttach.getClass(), usuariosListUsuariosToAttach.getId());
                attachedUsuariosList.add(usuariosListUsuariosToAttach);
            }
            perfiles.setUsuariosList(attachedUsuariosList);
            em.persist(perfiles);
            for (Usuarios usuariosListUsuarios : perfiles.getUsuariosList()) {
                Perfiles oldPerfilOfUsuariosListUsuarios = usuariosListUsuarios.getPerfil();
                usuariosListUsuarios.setPerfil(perfiles);
                usuariosListUsuarios = em.merge(usuariosListUsuarios);
                if (oldPerfilOfUsuariosListUsuarios != null) {
                    oldPerfilOfUsuariosListUsuarios.getUsuariosList().remove(usuariosListUsuarios);
                    oldPerfilOfUsuariosListUsuarios = em.merge(oldPerfilOfUsuariosListUsuarios);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPerfiles(perfiles.getId()) != null) {
                throw new PreexistingEntityException("Perfiles " + perfiles + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Perfiles perfiles) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Perfiles persistentPerfiles = em.find(Perfiles.class, perfiles.getId());
            List<Usuarios> usuariosListOld = persistentPerfiles.getUsuariosList();
            List<Usuarios> usuariosListNew = perfiles.getUsuariosList();
            List<String> illegalOrphanMessages = null;
            for (Usuarios usuariosListOldUsuarios : usuariosListOld) {
                if (!usuariosListNew.contains(usuariosListOldUsuarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuarios " + usuariosListOldUsuarios + " since its perfil field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Usuarios> attachedUsuariosListNew = new ArrayList<Usuarios>();
            for (Usuarios usuariosListNewUsuariosToAttach : usuariosListNew) {
                usuariosListNewUsuariosToAttach = em.getReference(usuariosListNewUsuariosToAttach.getClass(), usuariosListNewUsuariosToAttach.getId());
                attachedUsuariosListNew.add(usuariosListNewUsuariosToAttach);
            }
            usuariosListNew = attachedUsuariosListNew;
            perfiles.setUsuariosList(usuariosListNew);
            perfiles = em.merge(perfiles);
            for (Usuarios usuariosListNewUsuarios : usuariosListNew) {
                if (!usuariosListOld.contains(usuariosListNewUsuarios)) {
                    Perfiles oldPerfilOfUsuariosListNewUsuarios = usuariosListNewUsuarios.getPerfil();
                    usuariosListNewUsuarios.setPerfil(perfiles);
                    usuariosListNewUsuarios = em.merge(usuariosListNewUsuarios);
                    if (oldPerfilOfUsuariosListNewUsuarios != null && !oldPerfilOfUsuariosListNewUsuarios.equals(perfiles)) {
                        oldPerfilOfUsuariosListNewUsuarios.getUsuariosList().remove(usuariosListNewUsuarios);
                        oldPerfilOfUsuariosListNewUsuarios = em.merge(oldPerfilOfUsuariosListNewUsuarios);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = perfiles.getId();
                if (findPerfiles(id) == null) {
                    throw new NonexistentEntityException("The perfiles with id " + id + " no longer exists.");
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
            Perfiles perfiles;
            try {
                perfiles = em.getReference(Perfiles.class, id);
                perfiles.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The perfiles with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Usuarios> usuariosListOrphanCheck = perfiles.getUsuariosList();
            for (Usuarios usuariosListOrphanCheckUsuarios : usuariosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Perfiles (" + perfiles + ") cannot be destroyed since the Usuarios " + usuariosListOrphanCheckUsuarios + " in its usuariosList field has a non-nullable perfil field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(perfiles);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Perfiles> findPerfilesEntities() {
        return findPerfilesEntities(true, -1, -1);
    }

    public List<Perfiles> findPerfilesEntities(int maxResults, int firstResult) {
        return findPerfilesEntities(false, maxResults, firstResult);
    }

    private List<Perfiles> findPerfilesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Perfiles.class));
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

    public Perfiles findPerfiles(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Perfiles.class, id);
        } finally {
            em.close();
        }
    }

    public int getPerfilesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Perfiles> rt = cq.from(Perfiles.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Perfiles buscarPerfilPor(String nombre) {
        try {
            Query consulta = getEntityManager().createNamedQuery("Perfiles.findByPerfil");
            consulta.setParameter("perfil", nombre);
            return (Perfiles)consulta.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
}
