/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.controladores;

import cl.starlabs.controladores.exceptions.IllegalOrphanException;
import cl.starlabs.controladores.exceptions.NonexistentEntityException;
import cl.starlabs.controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.starlabs.modelo.Perfiles;
import cl.starlabs.modelo.DetalleUsuarios;
import cl.starlabs.modelo.Usuarios;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor Manuel Araya
 */
public class UsuariosJpaController implements Serializable {

    public UsuariosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuarios usuarios) throws PreexistingEntityException, Exception {
        if (usuarios.getDetalleUsuariosList() == null) {
            usuarios.setDetalleUsuariosList(new ArrayList<DetalleUsuarios>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Perfiles perfil = usuarios.getPerfil();
            if (perfil != null) {
                perfil = em.getReference(perfil.getClass(), perfil.getId());
                usuarios.setPerfil(perfil);
            }
            List<DetalleUsuarios> attachedDetalleUsuariosList = new ArrayList<DetalleUsuarios>();
            for (DetalleUsuarios detalleUsuariosListDetalleUsuariosToAttach : usuarios.getDetalleUsuariosList()) {
                detalleUsuariosListDetalleUsuariosToAttach = em.getReference(detalleUsuariosListDetalleUsuariosToAttach.getClass(), detalleUsuariosListDetalleUsuariosToAttach.getId());
                attachedDetalleUsuariosList.add(detalleUsuariosListDetalleUsuariosToAttach);
            }
            usuarios.setDetalleUsuariosList(attachedDetalleUsuariosList);
            em.persist(usuarios);
            if (perfil != null) {
                perfil.getUsuariosList().add(usuarios);
                perfil = em.merge(perfil);
            }
            for (DetalleUsuarios detalleUsuariosListDetalleUsuarios : usuarios.getDetalleUsuariosList()) {
                Usuarios oldUsuarioOfDetalleUsuariosListDetalleUsuarios = detalleUsuariosListDetalleUsuarios.getUsuario();
                detalleUsuariosListDetalleUsuarios.setUsuario(usuarios);
                detalleUsuariosListDetalleUsuarios = em.merge(detalleUsuariosListDetalleUsuarios);
                if (oldUsuarioOfDetalleUsuariosListDetalleUsuarios != null) {
                    oldUsuarioOfDetalleUsuariosListDetalleUsuarios.getDetalleUsuariosList().remove(detalleUsuariosListDetalleUsuarios);
                    oldUsuarioOfDetalleUsuariosListDetalleUsuarios = em.merge(oldUsuarioOfDetalleUsuariosListDetalleUsuarios);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuarios(usuarios.getId()) != null) {
                throw new PreexistingEntityException("Usuarios " + usuarios + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuarios usuarios) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios persistentUsuarios = em.find(Usuarios.class, usuarios.getId());
            Perfiles perfilOld = persistentUsuarios.getPerfil();
            Perfiles perfilNew = usuarios.getPerfil();
            List<DetalleUsuarios> detalleUsuariosListOld = persistentUsuarios.getDetalleUsuariosList();
            List<DetalleUsuarios> detalleUsuariosListNew = usuarios.getDetalleUsuariosList();
            List<String> illegalOrphanMessages = null;
            //for (DetalleUsuarios detalleUsuariosListOldDetalleUsuarios : detalleUsuariosListOld) {
                //if (!detalleUsuariosListNew.contains(detalleUsuariosListOldDetalleUsuarios)) {
                    //if (illegalOrphanMessages == null) {
                        //illegalOrphanMessages = new ArrayList<String>();
                    //}
                    //illegalOrphanMessages.add("You must retain DetalleUsuarios " + detalleUsuariosListOldDetalleUsuarios + " since its usuario field is not nullable.");
               // }
            //}
           // if (illegalOrphanMessages != null) {
           //     throw new IllegalOrphanException(illegalOrphanMessages);
           // }
            if (perfilNew != null) {
                perfilNew = em.getReference(perfilNew.getClass(), perfilNew.getId());
                usuarios.setPerfil(perfilNew);
            }
            List<DetalleUsuarios> attachedDetalleUsuariosListNew = new ArrayList<DetalleUsuarios>();
            for (DetalleUsuarios detalleUsuariosListNewDetalleUsuariosToAttach : detalleUsuariosListNew) {
                detalleUsuariosListNewDetalleUsuariosToAttach = em.getReference(detalleUsuariosListNewDetalleUsuariosToAttach.getClass(), detalleUsuariosListNewDetalleUsuariosToAttach.getId());
                attachedDetalleUsuariosListNew.add(detalleUsuariosListNewDetalleUsuariosToAttach);
            }
            detalleUsuariosListNew = attachedDetalleUsuariosListNew;
            usuarios.setDetalleUsuariosList(detalleUsuariosListNew);
            usuarios = em.merge(usuarios);
            if (perfilOld != null && !perfilOld.equals(perfilNew)) {
                perfilOld.getUsuariosList().remove(usuarios);
                perfilOld = em.merge(perfilOld);
            }
            if (perfilNew != null && !perfilNew.equals(perfilOld)) {
                perfilNew.getUsuariosList().add(usuarios);
                perfilNew = em.merge(perfilNew);
            }
            for (DetalleUsuarios detalleUsuariosListNewDetalleUsuarios : detalleUsuariosListNew) {
                if (!detalleUsuariosListOld.contains(detalleUsuariosListNewDetalleUsuarios)) {
                    Usuarios oldUsuarioOfDetalleUsuariosListNewDetalleUsuarios = detalleUsuariosListNewDetalleUsuarios.getUsuario();
                    detalleUsuariosListNewDetalleUsuarios.setUsuario(usuarios);
                    detalleUsuariosListNewDetalleUsuarios = em.merge(detalleUsuariosListNewDetalleUsuarios);
                    if (oldUsuarioOfDetalleUsuariosListNewDetalleUsuarios != null && !oldUsuarioOfDetalleUsuariosListNewDetalleUsuarios.equals(usuarios)) {
                        oldUsuarioOfDetalleUsuariosListNewDetalleUsuarios.getDetalleUsuariosList().remove(detalleUsuariosListNewDetalleUsuarios);
                        oldUsuarioOfDetalleUsuariosListNewDetalleUsuarios = em.merge(oldUsuarioOfDetalleUsuariosListNewDetalleUsuarios);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuarios.getId();
                if (findUsuarios(id) == null) {
                    throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.");
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
            Usuarios usuarios;
            try {
                usuarios = em.getReference(Usuarios.class, id);
                usuarios.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<DetalleUsuarios> detalleUsuariosListOrphanCheck = usuarios.getDetalleUsuariosList();
            for (DetalleUsuarios detalleUsuariosListOrphanCheckDetalleUsuarios : detalleUsuariosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the DetalleUsuarios " + detalleUsuariosListOrphanCheckDetalleUsuarios + " in its detalleUsuariosList field has a non-nullable usuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Perfiles perfil = usuarios.getPerfil();
            if (perfil != null) {
                perfil.getUsuariosList().remove(usuarios);
                perfil = em.merge(perfil);
            }
            em.remove(usuarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuarios> findUsuariosEntities() {
        return findUsuariosEntities(true, -1, -1);
    }

    public List<Usuarios> findUsuariosEntities(int maxResults, int firstResult) {
        return findUsuariosEntities(false, maxResults, firstResult);
    }

    private List<Usuarios> findUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuarios.class));
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

    public Usuarios findUsuarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuarios> rt = cq.from(Usuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Usuarios buscarUsuarioPorNickname(String nickname) {
        try {
            Query consulta = getEntityManager().createNamedQuery("Usuarios.findByUsuario");
            consulta.setParameter("usuario", nickname);
            return (Usuarios)consulta.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    public boolean existeUsuario(String usuario) {
        try {
            Query consulta = getEntityManager().createNamedQuery("Usuarios.findByUsuario");
            consulta.setParameter("usuario", usuario);
            if(consulta.getResultList().isEmpty()) {
                return false;
            }else{
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }
    
    public Integer ultimo() {
        try {
            Query consulta = getEntityManager().createNamedQuery("Usuarios.findAllDesc");
            consulta.setMaxResults(1);
            return ((Usuarios)consulta.getSingleResult()).getId()+1;
        } catch (Exception e) {
            return 1;
        }
    }
    
    public void actualizar(Usuarios u) {
        try {
            EntityManager em = getEntityManager();
            em.getTransaction().begin();
            em.merge(u);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());;
        }
    }
    
}
