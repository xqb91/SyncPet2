/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.controladores;

import cl.starlabs.controladores.exceptions.IllegalOrphanException;
import cl.starlabs.controladores.exceptions.NonexistentEntityException;
import cl.starlabs.controladores.exceptions.PreexistingEntityException;
import cl.starlabs.modelo.Clinica;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.starlabs.modelo.Comuna;
import cl.starlabs.modelo.Sucursal;
import cl.starlabs.modelo.Mascota;
import cl.starlabs.modelo.Propietario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor Manuel Araya
 */
public class PropietarioJpaController implements Serializable {

    public PropietarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Propietario propietario) throws PreexistingEntityException, Exception {
        if (propietario.getMascotaList() == null) {
            propietario.setMascotaList(new ArrayList<Mascota>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comuna comuna = propietario.getComuna();
            if (comuna != null) {
                comuna = em.getReference(comuna.getClass(), comuna.getIdComuna());
                propietario.setComuna(comuna);
            }
            Sucursal sucursal = propietario.getSucursal();
            if (sucursal != null) {
                sucursal = em.getReference(sucursal.getClass(), sucursal.getIdSucursal());
                propietario.setSucursal(sucursal);
            }
            List<Mascota> attachedMascotaList = new ArrayList<Mascota>();
            for (Mascota mascotaListMascotaToAttach : propietario.getMascotaList()) {
                mascotaListMascotaToAttach = em.getReference(mascotaListMascotaToAttach.getClass(), mascotaListMascotaToAttach.getIdMascota());
                attachedMascotaList.add(mascotaListMascotaToAttach);
            }
            propietario.setMascotaList(attachedMascotaList);
            em.persist(propietario);
            if (comuna != null) {
                comuna.getPropietarioList().add(propietario);
                comuna = em.merge(comuna);
            }
            if (sucursal != null) {
                sucursal.getPropietarioList().add(propietario);
                sucursal = em.merge(sucursal);
            }
            for (Mascota mascotaListMascota : propietario.getMascotaList()) {
                Propietario oldPropietarioOfMascotaListMascota = mascotaListMascota.getPropietario();
                mascotaListMascota.setPropietario(propietario);
                mascotaListMascota = em.merge(mascotaListMascota);
                if (oldPropietarioOfMascotaListMascota != null) {
                    oldPropietarioOfMascotaListMascota.getMascotaList().remove(mascotaListMascota);
                    oldPropietarioOfMascotaListMascota = em.merge(oldPropietarioOfMascotaListMascota);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPropietario(propietario.getIdPropietario()) != null) {
                throw new PreexistingEntityException("Propietario " + propietario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Propietario propietario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Propietario persistentPropietario = em.find(Propietario.class, propietario.getIdPropietario());
            Comuna comunaOld = persistentPropietario.getComuna();
            Comuna comunaNew = propietario.getComuna();
            Sucursal sucursalOld = persistentPropietario.getSucursal();
            Sucursal sucursalNew = propietario.getSucursal();
            List<Mascota> mascotaListOld = persistentPropietario.getMascotaList();
            List<Mascota> mascotaListNew = propietario.getMascotaList();
            List<String> illegalOrphanMessages = null;
            for (Mascota mascotaListOldMascota : mascotaListOld) {
                if (!mascotaListNew.contains(mascotaListOldMascota)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Mascota " + mascotaListOldMascota + " since its propietario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (comunaNew != null) {
                comunaNew = em.getReference(comunaNew.getClass(), comunaNew.getIdComuna());
                propietario.setComuna(comunaNew);
            }
            if (sucursalNew != null) {
                sucursalNew = em.getReference(sucursalNew.getClass(), sucursalNew.getIdSucursal());
                propietario.setSucursal(sucursalNew);
            }
            List<Mascota> attachedMascotaListNew = new ArrayList<Mascota>();
            for (Mascota mascotaListNewMascotaToAttach : mascotaListNew) {
                mascotaListNewMascotaToAttach = em.getReference(mascotaListNewMascotaToAttach.getClass(), mascotaListNewMascotaToAttach.getIdMascota());
                attachedMascotaListNew.add(mascotaListNewMascotaToAttach);
            }
            mascotaListNew = attachedMascotaListNew;
            propietario.setMascotaList(mascotaListNew);
            propietario = em.merge(propietario);
            if (comunaOld != null && !comunaOld.equals(comunaNew)) {
                comunaOld.getPropietarioList().remove(propietario);
                comunaOld = em.merge(comunaOld);
            }
            if (comunaNew != null && !comunaNew.equals(comunaOld)) {
                comunaNew.getPropietarioList().add(propietario);
                comunaNew = em.merge(comunaNew);
            }
            if (sucursalOld != null && !sucursalOld.equals(sucursalNew)) {
                sucursalOld.getPropietarioList().remove(propietario);
                sucursalOld = em.merge(sucursalOld);
            }
            if (sucursalNew != null && !sucursalNew.equals(sucursalOld)) {
                sucursalNew.getPropietarioList().add(propietario);
                sucursalNew = em.merge(sucursalNew);
            }
            for (Mascota mascotaListNewMascota : mascotaListNew) {
                if (!mascotaListOld.contains(mascotaListNewMascota)) {
                    Propietario oldPropietarioOfMascotaListNewMascota = mascotaListNewMascota.getPropietario();
                    mascotaListNewMascota.setPropietario(propietario);
                    mascotaListNewMascota = em.merge(mascotaListNewMascota);
                    if (oldPropietarioOfMascotaListNewMascota != null && !oldPropietarioOfMascotaListNewMascota.equals(propietario)) {
                        oldPropietarioOfMascotaListNewMascota.getMascotaList().remove(mascotaListNewMascota);
                        oldPropietarioOfMascotaListNewMascota = em.merge(oldPropietarioOfMascotaListNewMascota);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = propietario.getIdPropietario();
                if (findPropietario(id) == null) {
                    throw new NonexistentEntityException("The propietario with id " + id + " no longer exists.");
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
            Propietario propietario;
            try {
                propietario = em.getReference(Propietario.class, id);
                propietario.getIdPropietario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The propietario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Mascota> mascotaListOrphanCheck = propietario.getMascotaList();
            for (Mascota mascotaListOrphanCheckMascota : mascotaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Propietario (" + propietario + ") cannot be destroyed since the Mascota " + mascotaListOrphanCheckMascota + " in its mascotaList field has a non-nullable propietario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Comuna comuna = propietario.getComuna();
            if (comuna != null) {
                comuna.getPropietarioList().remove(propietario);
                comuna = em.merge(comuna);
            }
            Sucursal sucursal = propietario.getSucursal();
            if (sucursal != null) {
                sucursal.getPropietarioList().remove(propietario);
                sucursal = em.merge(sucursal);
            }
            em.remove(propietario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Propietario> findPropietarioEntities() {
        return findPropietarioEntities(true, -1, -1);
    }

    public List<Propietario> findPropietarioEntities(int maxResults, int firstResult) {
        return findPropietarioEntities(false, maxResults, firstResult);
    }

    private List<Propietario> findPropietarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Propietario.class));
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

    public Propietario findPropietario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Propietario.class, id);
        } finally {
            em.close();
        }
    }

    public int getPropietarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Propietario> rt = cq.from(Propietario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Integer ultimoIdentificador() {
        try {
            Query consulta = getEntityManager().createNamedQuery("Propietario.findAll");
            consulta.setMaxResults(1);
            Propietario p = (Propietario)consulta.getSingleResult();
            return p.getIdPropietario()+1;
        } catch (Exception e) {
            return 1;
        }
    }
    
    public Propietario buscarPorRut(String rutCh) {
        try {
            String run = rutCh.replace(".", "");
            run = run.split("-")[0];
            Query consulta = getEntityManager().createNamedQuery("Propietario.findByRut");
            consulta.setParameter("rut", Integer.parseInt(run));
            return (Propietario)consulta.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<Propietario> buscarPorClinica(Clinica clinica) {
        try {
            Query consulta = getEntityManager().createNamedQuery("Propietario.findByClinica");
            consulta.setParameter("clinica", clinica);
            return consulta.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<Propietario> buscarMultiple(String campo, String valor) {
        switch(campo) {
            case "Run":
                try {
                    Query consulta = getEntityManager().createNamedQuery("Propietario.findByRut");
                    Integer numero = Integer.parseInt(valor.replace(".", "").split("-")[0]);
                    consulta.setParameter("rut", numero);
                    return consulta.getResultList();
                } catch (Exception e) {
                    return null;
                }
            case "Nombres":
                try {
                    Query consulta = getEntityManager().createNamedQuery("Propietario.findByNombres");
                    consulta.setParameter("nombres", valor);
                    return consulta.getResultList();
                } catch (Exception e) {
                    return null;
                }
            case "Apellido Paterno":
                try {
                    Query consulta = getEntityManager().createNamedQuery("Propietario.findByApaterno");
                    consulta.setParameter("apaterno", valor);
                    return consulta.getResultList();
                } catch (Exception e) {
                    return null;
                }
            case "Apellido Materno":
                try {
                    Query consulta = getEntityManager().createNamedQuery("Propietario.findByAmaterno");
                    consulta.setParameter("amaterno", valor);
                    return consulta.getResultList();
                } catch (Exception e) {
                    return null;
                }
            case "Correo Electrónico":
                try {
                    Query consulta = getEntityManager().createNamedQuery("Propietario.findByCorreo");
                    consulta.setParameter("email", valor);
                    return consulta.getResultList();
                } catch (Exception e) {
                    return null;
                }
            case "Téfono Fijo":
                try {
                    Query consulta = getEntityManager().createNamedQuery("Propietario.findByTelefono");
                    consulta.setParameter("telefono", Integer.parseInt(valor.replace("+", "")));
                    return consulta.getResultList();
                } catch (Exception e) {
                    return null;
                }
            case "Teléfono Celular":
                try {
                    Query consulta = getEntityManager().createNamedQuery("Propietario.findByCelular");
                    consulta.setParameter("celular", Integer.parseInt(valor.replace("+", "")));
                    return consulta.getResultList();
                } catch (Exception e) {
                    return null;
                }

            default:
                return null;
        }
    }
    
}
