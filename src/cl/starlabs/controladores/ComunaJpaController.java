/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.controladores;

import cl.starlabs.controladores.exceptions.IllegalOrphanException;
import cl.starlabs.controladores.exceptions.NonexistentEntityException;
import cl.starlabs.controladores.exceptions.PreexistingEntityException;
import cl.starlabs.modelo.Comuna;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.starlabs.modelo.Provincia;
import cl.starlabs.modelo.Sucursal;
import java.util.ArrayList;
import java.util.List;
import cl.starlabs.modelo.Propietario;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor Manuel Araya
 */
public class ComunaJpaController implements Serializable {

    public ComunaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Comuna comuna) throws PreexistingEntityException, Exception {
        if (comuna.getSucursalList() == null) {
            comuna.setSucursalList(new ArrayList<Sucursal>());
        }
        if (comuna.getPropietarioList() == null) {
            comuna.setPropietarioList(new ArrayList<Propietario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Provincia provincia = comuna.getProvincia();
            if (provincia != null) {
                provincia = em.getReference(provincia.getClass(), provincia.getIdProvincia());
                comuna.setProvincia(provincia);
            }
            List<Sucursal> attachedSucursalList = new ArrayList<Sucursal>();
            for (Sucursal sucursalListSucursalToAttach : comuna.getSucursalList()) {
                sucursalListSucursalToAttach = em.getReference(sucursalListSucursalToAttach.getClass(), sucursalListSucursalToAttach.getIdSucursal());
                attachedSucursalList.add(sucursalListSucursalToAttach);
            }
            comuna.setSucursalList(attachedSucursalList);
            List<Propietario> attachedPropietarioList = new ArrayList<Propietario>();
            for (Propietario propietarioListPropietarioToAttach : comuna.getPropietarioList()) {
                propietarioListPropietarioToAttach = em.getReference(propietarioListPropietarioToAttach.getClass(), propietarioListPropietarioToAttach.getIdPropietario());
                attachedPropietarioList.add(propietarioListPropietarioToAttach);
            }
            comuna.setPropietarioList(attachedPropietarioList);
            em.persist(comuna);
            if (provincia != null) {
                provincia.getComunaList().add(comuna);
                provincia = em.merge(provincia);
            }
            for (Sucursal sucursalListSucursal : comuna.getSucursalList()) {
                Comuna oldComunaOfSucursalListSucursal = sucursalListSucursal.getComuna();
                sucursalListSucursal.setComuna(comuna);
                sucursalListSucursal = em.merge(sucursalListSucursal);
                if (oldComunaOfSucursalListSucursal != null) {
                    oldComunaOfSucursalListSucursal.getSucursalList().remove(sucursalListSucursal);
                    oldComunaOfSucursalListSucursal = em.merge(oldComunaOfSucursalListSucursal);
                }
            }
            for (Propietario propietarioListPropietario : comuna.getPropietarioList()) {
                Comuna oldComunaOfPropietarioListPropietario = propietarioListPropietario.getComuna();
                propietarioListPropietario.setComuna(comuna);
                propietarioListPropietario = em.merge(propietarioListPropietario);
                if (oldComunaOfPropietarioListPropietario != null) {
                    oldComunaOfPropietarioListPropietario.getPropietarioList().remove(propietarioListPropietario);
                    oldComunaOfPropietarioListPropietario = em.merge(oldComunaOfPropietarioListPropietario);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findComuna(comuna.getIdComuna()) != null) {
                throw new PreexistingEntityException("Comuna " + comuna + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Comuna comuna) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comuna persistentComuna = em.find(Comuna.class, comuna.getIdComuna());
            Provincia provinciaOld = persistentComuna.getProvincia();
            Provincia provinciaNew = comuna.getProvincia();
            List<Sucursal> sucursalListOld = persistentComuna.getSucursalList();
            List<Sucursal> sucursalListNew = comuna.getSucursalList();
            List<Propietario> propietarioListOld = persistentComuna.getPropietarioList();
            List<Propietario> propietarioListNew = comuna.getPropietarioList();
            List<String> illegalOrphanMessages = null;
            for (Sucursal sucursalListOldSucursal : sucursalListOld) {
                if (!sucursalListNew.contains(sucursalListOldSucursal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Sucursal " + sucursalListOldSucursal + " since its comuna field is not nullable.");
                }
            }
            for (Propietario propietarioListOldPropietario : propietarioListOld) {
                if (!propietarioListNew.contains(propietarioListOldPropietario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Propietario " + propietarioListOldPropietario + " since its comuna field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (provinciaNew != null) {
                provinciaNew = em.getReference(provinciaNew.getClass(), provinciaNew.getIdProvincia());
                comuna.setProvincia(provinciaNew);
            }
            List<Sucursal> attachedSucursalListNew = new ArrayList<Sucursal>();
            for (Sucursal sucursalListNewSucursalToAttach : sucursalListNew) {
                sucursalListNewSucursalToAttach = em.getReference(sucursalListNewSucursalToAttach.getClass(), sucursalListNewSucursalToAttach.getIdSucursal());
                attachedSucursalListNew.add(sucursalListNewSucursalToAttach);
            }
            sucursalListNew = attachedSucursalListNew;
            comuna.setSucursalList(sucursalListNew);
            List<Propietario> attachedPropietarioListNew = new ArrayList<Propietario>();
            for (Propietario propietarioListNewPropietarioToAttach : propietarioListNew) {
                propietarioListNewPropietarioToAttach = em.getReference(propietarioListNewPropietarioToAttach.getClass(), propietarioListNewPropietarioToAttach.getIdPropietario());
                attachedPropietarioListNew.add(propietarioListNewPropietarioToAttach);
            }
            propietarioListNew = attachedPropietarioListNew;
            comuna.setPropietarioList(propietarioListNew);
            comuna = em.merge(comuna);
            if (provinciaOld != null && !provinciaOld.equals(provinciaNew)) {
                provinciaOld.getComunaList().remove(comuna);
                provinciaOld = em.merge(provinciaOld);
            }
            if (provinciaNew != null && !provinciaNew.equals(provinciaOld)) {
                provinciaNew.getComunaList().add(comuna);
                provinciaNew = em.merge(provinciaNew);
            }
            for (Sucursal sucursalListNewSucursal : sucursalListNew) {
                if (!sucursalListOld.contains(sucursalListNewSucursal)) {
                    Comuna oldComunaOfSucursalListNewSucursal = sucursalListNewSucursal.getComuna();
                    sucursalListNewSucursal.setComuna(comuna);
                    sucursalListNewSucursal = em.merge(sucursalListNewSucursal);
                    if (oldComunaOfSucursalListNewSucursal != null && !oldComunaOfSucursalListNewSucursal.equals(comuna)) {
                        oldComunaOfSucursalListNewSucursal.getSucursalList().remove(sucursalListNewSucursal);
                        oldComunaOfSucursalListNewSucursal = em.merge(oldComunaOfSucursalListNewSucursal);
                    }
                }
            }
            for (Propietario propietarioListNewPropietario : propietarioListNew) {
                if (!propietarioListOld.contains(propietarioListNewPropietario)) {
                    Comuna oldComunaOfPropietarioListNewPropietario = propietarioListNewPropietario.getComuna();
                    propietarioListNewPropietario.setComuna(comuna);
                    propietarioListNewPropietario = em.merge(propietarioListNewPropietario);
                    if (oldComunaOfPropietarioListNewPropietario != null && !oldComunaOfPropietarioListNewPropietario.equals(comuna)) {
                        oldComunaOfPropietarioListNewPropietario.getPropietarioList().remove(propietarioListNewPropietario);
                        oldComunaOfPropietarioListNewPropietario = em.merge(oldComunaOfPropietarioListNewPropietario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = comuna.getIdComuna();
                if (findComuna(id) == null) {
                    throw new NonexistentEntityException("The comuna with id " + id + " no longer exists.");
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
            Comuna comuna;
            try {
                comuna = em.getReference(Comuna.class, id);
                comuna.getIdComuna();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comuna with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Sucursal> sucursalListOrphanCheck = comuna.getSucursalList();
            for (Sucursal sucursalListOrphanCheckSucursal : sucursalListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Comuna (" + comuna + ") cannot be destroyed since the Sucursal " + sucursalListOrphanCheckSucursal + " in its sucursalList field has a non-nullable comuna field.");
            }
            List<Propietario> propietarioListOrphanCheck = comuna.getPropietarioList();
            for (Propietario propietarioListOrphanCheckPropietario : propietarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Comuna (" + comuna + ") cannot be destroyed since the Propietario " + propietarioListOrphanCheckPropietario + " in its propietarioList field has a non-nullable comuna field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Provincia provincia = comuna.getProvincia();
            if (provincia != null) {
                provincia.getComunaList().remove(comuna);
                provincia = em.merge(provincia);
            }
            em.remove(comuna);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Comuna> findComunaEntities() {
        return findComunaEntities(true, -1, -1);
    }

    public List<Comuna> findComunaEntities(int maxResults, int firstResult) {
        return findComunaEntities(false, maxResults, firstResult);
    }

    private List<Comuna> findComunaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Comuna.class));
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

    public Comuna findComuna(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Comuna.class, id);
        } finally {
            em.close();
        }
    }

    public int getComunaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Comuna> rt = cq.from(Comuna.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Comuna> listar() {
        try {
            Query consulta = getEntityManager().createNamedQuery("Comuna.findAll");
            return consulta.getResultList();
        }catch(Exception e) {
            return null;
        }
    }
    
    public Comuna buscarComunaPorNombre(String comuna) {
        try {
            Query consulta = getEntityManager().createNamedQuery("Comuna.findByNombre");
            consulta.setParameter("nombre", comuna);
            return (Comuna)consulta.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    public Integer ultimo() {
        try {
            Query consulta = getEntityManager().createNamedQuery("Comuna.findAllById");
            consulta.setMaxResults(1);
            return ((Comuna)consulta.getSingleResult()).getIdComuna()+1;
        } catch (Exception e) {
            return 1;
        }
    }
    
}
