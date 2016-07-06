/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.controladores;

import cl.starlabs.controladores.exceptions.IllegalOrphanException;
import cl.starlabs.controladores.exceptions.NonexistentEntityException;
import cl.starlabs.controladores.exceptions.PreexistingEntityException;
import cl.starlabs.modelo.Especie;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.starlabs.modelo.Raza;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor Manuel Araya
 */
public class EspecieJpaController implements Serializable {

    public EspecieJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Especie especie) throws PreexistingEntityException, Exception {
        if (especie.getRazaList() == null) {
            especie.setRazaList(new ArrayList<Raza>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Raza> attachedRazaList = new ArrayList<Raza>();
            for (Raza razaListRazaToAttach : especie.getRazaList()) {
                razaListRazaToAttach = em.getReference(razaListRazaToAttach.getClass(), razaListRazaToAttach.getIdRaza());
                attachedRazaList.add(razaListRazaToAttach);
            }
            especie.setRazaList(attachedRazaList);
            em.persist(especie);
            for (Raza razaListRaza : especie.getRazaList()) {
                Especie oldEspecieOfRazaListRaza = razaListRaza.getEspecie();
                razaListRaza.setEspecie(especie);
                razaListRaza = em.merge(razaListRaza);
                if (oldEspecieOfRazaListRaza != null) {
                    oldEspecieOfRazaListRaza.getRazaList().remove(razaListRaza);
                    oldEspecieOfRazaListRaza = em.merge(oldEspecieOfRazaListRaza);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEspecie(especie.getIdEspecie()) != null) {
                throw new PreexistingEntityException("Especie " + especie + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Especie especie) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Especie persistentEspecie = em.find(Especie.class, especie.getIdEspecie());
            List<Raza> razaListOld = persistentEspecie.getRazaList();
            List<Raza> razaListNew = especie.getRazaList();
            List<String> illegalOrphanMessages = null;
            for (Raza razaListOldRaza : razaListOld) {
                if (!razaListNew.contains(razaListOldRaza)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Raza " + razaListOldRaza + " since its especie field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Raza> attachedRazaListNew = new ArrayList<Raza>();
            for (Raza razaListNewRazaToAttach : razaListNew) {
                razaListNewRazaToAttach = em.getReference(razaListNewRazaToAttach.getClass(), razaListNewRazaToAttach.getIdRaza());
                attachedRazaListNew.add(razaListNewRazaToAttach);
            }
            razaListNew = attachedRazaListNew;
            especie.setRazaList(razaListNew);
            especie = em.merge(especie);
            for (Raza razaListNewRaza : razaListNew) {
                if (!razaListOld.contains(razaListNewRaza)) {
                    Especie oldEspecieOfRazaListNewRaza = razaListNewRaza.getEspecie();
                    razaListNewRaza.setEspecie(especie);
                    razaListNewRaza = em.merge(razaListNewRaza);
                    if (oldEspecieOfRazaListNewRaza != null && !oldEspecieOfRazaListNewRaza.equals(especie)) {
                        oldEspecieOfRazaListNewRaza.getRazaList().remove(razaListNewRaza);
                        oldEspecieOfRazaListNewRaza = em.merge(oldEspecieOfRazaListNewRaza);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = especie.getIdEspecie();
                if (findEspecie(id) == null) {
                    throw new NonexistentEntityException("The especie with id " + id + " no longer exists.");
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
            Especie especie;
            try {
                especie = em.getReference(Especie.class, id);
                especie.getIdEspecie();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The especie with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Raza> razaListOrphanCheck = especie.getRazaList();
            for (Raza razaListOrphanCheckRaza : razaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Especie (" + especie + ") cannot be destroyed since the Raza " + razaListOrphanCheckRaza + " in its razaList field has a non-nullable especie field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(especie);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Especie> findEspecieEntities() {
        return findEspecieEntities(true, -1, -1);
    }

    public List<Especie> findEspecieEntities(int maxResults, int firstResult) {
        return findEspecieEntities(false, maxResults, firstResult);
    }

    private List<Especie> findEspecieEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Especie.class));
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

    public Especie findEspecie(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Especie.class, id);
        } finally {
            em.close();
        }
    }

    public int getEspecieCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Especie> rt = cq.from(Especie.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Especie buscarRaza(String especie) {
        try {
            Query consulta = getEntityManager().createNamedQuery("Especie.findByNombre");
            consulta.setParameter("nombre", especie);
            return (Especie)consulta.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    //eliminado version anterior
    /*public List<Especie> buscarPorNombre(String nombre) {
        try {
            Query consulta = getEntityManager().createNamedQuery("Especie.findByNombre");
            consulta.setParameter("nombre", nombre);
            return consulta.getResultList();
        } catch (Exception e) {
            return null;
        }
    }*/
    
    public boolean existeTipoAlergia(String nombre) {
        try {
            if(this.buscarPorNombre(nombre).getNombre().isEmpty()) {
                return false;
            }else{
                return true;
            }
        } catch (Exception e) {
            return true;
        }
    }
    
    public Integer ultimo() {
        try {
            Query consulta = getEntityManager().createNamedQuery("Especie.findAllDesc");
            consulta.setMaxResults(1);
            return ((Especie)consulta.getSingleResult()).getIdEspecie()+1;
        } catch (Exception e) {
            return 1;
        }
    }
    
    public Especie buscarPorNombre(String nombre) {
        try {
            Query consulta = getEntityManager().createNamedQuery("Especie.findByNombre");
            consulta.setParameter("nombre", nombre);
            return (Especie)consulta.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    public boolean existeTipo(String nombre) {
        if(this.buscarPorNombre(nombre) == null) {
            return false;
        }else{
            return true;
        }
    }
    
}
