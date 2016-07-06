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
import cl.starlabs.modelo.Clinica;
import cl.starlabs.modelo.Comuna;
import cl.starlabs.modelo.Agenda;
import java.util.ArrayList;
import java.util.List;
import cl.starlabs.modelo.DetalleUsuarios;
import cl.starlabs.modelo.Propietario;
import cl.starlabs.modelo.Sucursal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor Manuel Araya
 */
public class SucursalJpaController implements Serializable {

    public SucursalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sucursal sucursal) throws PreexistingEntityException, Exception {
        if (sucursal.getAgendaList() == null) {
            sucursal.setAgendaList(new ArrayList<Agenda>());
        }
        if (sucursal.getDetalleUsuariosList() == null) {
            sucursal.setDetalleUsuariosList(new ArrayList<DetalleUsuarios>());
        }
        if (sucursal.getPropietarioList() == null) {
            sucursal.setPropietarioList(new ArrayList<Propietario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clinica clinica = sucursal.getClinica();
            if (clinica != null) {
                clinica = em.getReference(clinica.getClass(), clinica.getIdClinica());
                sucursal.setClinica(clinica);
            }
            Comuna comuna = sucursal.getComuna();
            if (comuna != null) {
                comuna = em.getReference(comuna.getClass(), comuna.getIdComuna());
                sucursal.setComuna(comuna);
            }
            List<Agenda> attachedAgendaList = new ArrayList<Agenda>();
            for (Agenda agendaListAgendaToAttach : sucursal.getAgendaList()) {
                agendaListAgendaToAttach = em.getReference(agendaListAgendaToAttach.getClass(), agendaListAgendaToAttach.getIdEvento());
                attachedAgendaList.add(agendaListAgendaToAttach);
            }
            sucursal.setAgendaList(attachedAgendaList);
            List<DetalleUsuarios> attachedDetalleUsuariosList = new ArrayList<DetalleUsuarios>();
            for (DetalleUsuarios detalleUsuariosListDetalleUsuariosToAttach : sucursal.getDetalleUsuariosList()) {
                detalleUsuariosListDetalleUsuariosToAttach = em.getReference(detalleUsuariosListDetalleUsuariosToAttach.getClass(), detalleUsuariosListDetalleUsuariosToAttach.getId());
                attachedDetalleUsuariosList.add(detalleUsuariosListDetalleUsuariosToAttach);
            }
            sucursal.setDetalleUsuariosList(attachedDetalleUsuariosList);
            List<Propietario> attachedPropietarioList = new ArrayList<Propietario>();
            for (Propietario propietarioListPropietarioToAttach : sucursal.getPropietarioList()) {
                propietarioListPropietarioToAttach = em.getReference(propietarioListPropietarioToAttach.getClass(), propietarioListPropietarioToAttach.getIdPropietario());
                attachedPropietarioList.add(propietarioListPropietarioToAttach);
            }
            sucursal.setPropietarioList(attachedPropietarioList);
            em.persist(sucursal);
            if (clinica != null) {
                clinica.getSucursalList().add(sucursal);
                clinica = em.merge(clinica);
            }
            if (comuna != null) {
                comuna.getSucursalList().add(sucursal);
                comuna = em.merge(comuna);
            }
            for (Agenda agendaListAgenda : sucursal.getAgendaList()) {
                Sucursal oldSucursalOfAgendaListAgenda = agendaListAgenda.getSucursal();
                agendaListAgenda.setSucursal(sucursal);
                agendaListAgenda = em.merge(agendaListAgenda);
                if (oldSucursalOfAgendaListAgenda != null) {
                    oldSucursalOfAgendaListAgenda.getAgendaList().remove(agendaListAgenda);
                    oldSucursalOfAgendaListAgenda = em.merge(oldSucursalOfAgendaListAgenda);
                }
            }
            for (DetalleUsuarios detalleUsuariosListDetalleUsuarios : sucursal.getDetalleUsuariosList()) {
                Sucursal oldSucursalOfDetalleUsuariosListDetalleUsuarios = detalleUsuariosListDetalleUsuarios.getSucursal();
                detalleUsuariosListDetalleUsuarios.setSucursal(sucursal);
                detalleUsuariosListDetalleUsuarios = em.merge(detalleUsuariosListDetalleUsuarios);
                if (oldSucursalOfDetalleUsuariosListDetalleUsuarios != null) {
                    oldSucursalOfDetalleUsuariosListDetalleUsuarios.getDetalleUsuariosList().remove(detalleUsuariosListDetalleUsuarios);
                    oldSucursalOfDetalleUsuariosListDetalleUsuarios = em.merge(oldSucursalOfDetalleUsuariosListDetalleUsuarios);
                }
            }
            for (Propietario propietarioListPropietario : sucursal.getPropietarioList()) {
                Sucursal oldSucursalOfPropietarioListPropietario = propietarioListPropietario.getSucursal();
                propietarioListPropietario.setSucursal(sucursal);
                propietarioListPropietario = em.merge(propietarioListPropietario);
                if (oldSucursalOfPropietarioListPropietario != null) {
                    oldSucursalOfPropietarioListPropietario.getPropietarioList().remove(propietarioListPropietario);
                    oldSucursalOfPropietarioListPropietario = em.merge(oldSucursalOfPropietarioListPropietario);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSucursal(sucursal.getIdSucursal()) != null) {
                throw new PreexistingEntityException("Sucursal " + sucursal + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sucursal sucursal) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sucursal persistentSucursal = em.find(Sucursal.class, sucursal.getIdSucursal());
            Clinica clinicaOld = persistentSucursal.getClinica();
            Clinica clinicaNew = sucursal.getClinica();
            Comuna comunaOld = persistentSucursal.getComuna();
            Comuna comunaNew = sucursal.getComuna();
            List<Agenda> agendaListOld = persistentSucursal.getAgendaList();
            List<Agenda> agendaListNew = sucursal.getAgendaList();
            List<DetalleUsuarios> detalleUsuariosListOld = persistentSucursal.getDetalleUsuariosList();
            List<DetalleUsuarios> detalleUsuariosListNew = sucursal.getDetalleUsuariosList();
            List<Propietario> propietarioListOld = persistentSucursal.getPropietarioList();
            List<Propietario> propietarioListNew = sucursal.getPropietarioList();
            List<String> illegalOrphanMessages = null;
            for (Agenda agendaListOldAgenda : agendaListOld) {
                if (!agendaListNew.contains(agendaListOldAgenda)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Agenda " + agendaListOldAgenda + " since its sucursal field is not nullable.");
                }
            }
            for (DetalleUsuarios detalleUsuariosListOldDetalleUsuarios : detalleUsuariosListOld) {
                if (!detalleUsuariosListNew.contains(detalleUsuariosListOldDetalleUsuarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetalleUsuarios " + detalleUsuariosListOldDetalleUsuarios + " since its sucursal field is not nullable.");
                }
            }
            for (Propietario propietarioListOldPropietario : propietarioListOld) {
                if (!propietarioListNew.contains(propietarioListOldPropietario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Propietario " + propietarioListOldPropietario + " since its sucursal field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clinicaNew != null) {
                clinicaNew = em.getReference(clinicaNew.getClass(), clinicaNew.getIdClinica());
                sucursal.setClinica(clinicaNew);
            }
            if (comunaNew != null) {
                comunaNew = em.getReference(comunaNew.getClass(), comunaNew.getIdComuna());
                sucursal.setComuna(comunaNew);
            }
            List<Agenda> attachedAgendaListNew = new ArrayList<Agenda>();
            for (Agenda agendaListNewAgendaToAttach : agendaListNew) {
                agendaListNewAgendaToAttach = em.getReference(agendaListNewAgendaToAttach.getClass(), agendaListNewAgendaToAttach.getIdEvento());
                attachedAgendaListNew.add(agendaListNewAgendaToAttach);
            }
            agendaListNew = attachedAgendaListNew;
            sucursal.setAgendaList(agendaListNew);
            List<DetalleUsuarios> attachedDetalleUsuariosListNew = new ArrayList<DetalleUsuarios>();
            for (DetalleUsuarios detalleUsuariosListNewDetalleUsuariosToAttach : detalleUsuariosListNew) {
                detalleUsuariosListNewDetalleUsuariosToAttach = em.getReference(detalleUsuariosListNewDetalleUsuariosToAttach.getClass(), detalleUsuariosListNewDetalleUsuariosToAttach.getId());
                attachedDetalleUsuariosListNew.add(detalleUsuariosListNewDetalleUsuariosToAttach);
            }
            detalleUsuariosListNew = attachedDetalleUsuariosListNew;
            sucursal.setDetalleUsuariosList(detalleUsuariosListNew);
            List<Propietario> attachedPropietarioListNew = new ArrayList<Propietario>();
            for (Propietario propietarioListNewPropietarioToAttach : propietarioListNew) {
                propietarioListNewPropietarioToAttach = em.getReference(propietarioListNewPropietarioToAttach.getClass(), propietarioListNewPropietarioToAttach.getIdPropietario());
                attachedPropietarioListNew.add(propietarioListNewPropietarioToAttach);
            }
            propietarioListNew = attachedPropietarioListNew;
            sucursal.setPropietarioList(propietarioListNew);
            sucursal = em.merge(sucursal);
            if (clinicaOld != null && !clinicaOld.equals(clinicaNew)) {
                clinicaOld.getSucursalList().remove(sucursal);
                clinicaOld = em.merge(clinicaOld);
            }
            if (clinicaNew != null && !clinicaNew.equals(clinicaOld)) {
                clinicaNew.getSucursalList().add(sucursal);
                clinicaNew = em.merge(clinicaNew);
            }
            if (comunaOld != null && !comunaOld.equals(comunaNew)) {
                comunaOld.getSucursalList().remove(sucursal);
                comunaOld = em.merge(comunaOld);
            }
            if (comunaNew != null && !comunaNew.equals(comunaOld)) {
                comunaNew.getSucursalList().add(sucursal);
                comunaNew = em.merge(comunaNew);
            }
            for (Agenda agendaListNewAgenda : agendaListNew) {
                if (!agendaListOld.contains(agendaListNewAgenda)) {
                    Sucursal oldSucursalOfAgendaListNewAgenda = agendaListNewAgenda.getSucursal();
                    agendaListNewAgenda.setSucursal(sucursal);
                    agendaListNewAgenda = em.merge(agendaListNewAgenda);
                    if (oldSucursalOfAgendaListNewAgenda != null && !oldSucursalOfAgendaListNewAgenda.equals(sucursal)) {
                        oldSucursalOfAgendaListNewAgenda.getAgendaList().remove(agendaListNewAgenda);
                        oldSucursalOfAgendaListNewAgenda = em.merge(oldSucursalOfAgendaListNewAgenda);
                    }
                }
            }
            for (DetalleUsuarios detalleUsuariosListNewDetalleUsuarios : detalleUsuariosListNew) {
                if (!detalleUsuariosListOld.contains(detalleUsuariosListNewDetalleUsuarios)) {
                    Sucursal oldSucursalOfDetalleUsuariosListNewDetalleUsuarios = detalleUsuariosListNewDetalleUsuarios.getSucursal();
                    detalleUsuariosListNewDetalleUsuarios.setSucursal(sucursal);
                    detalleUsuariosListNewDetalleUsuarios = em.merge(detalleUsuariosListNewDetalleUsuarios);
                    if (oldSucursalOfDetalleUsuariosListNewDetalleUsuarios != null && !oldSucursalOfDetalleUsuariosListNewDetalleUsuarios.equals(sucursal)) {
                        oldSucursalOfDetalleUsuariosListNewDetalleUsuarios.getDetalleUsuariosList().remove(detalleUsuariosListNewDetalleUsuarios);
                        oldSucursalOfDetalleUsuariosListNewDetalleUsuarios = em.merge(oldSucursalOfDetalleUsuariosListNewDetalleUsuarios);
                    }
                }
            }
            for (Propietario propietarioListNewPropietario : propietarioListNew) {
                if (!propietarioListOld.contains(propietarioListNewPropietario)) {
                    Sucursal oldSucursalOfPropietarioListNewPropietario = propietarioListNewPropietario.getSucursal();
                    propietarioListNewPropietario.setSucursal(sucursal);
                    propietarioListNewPropietario = em.merge(propietarioListNewPropietario);
                    if (oldSucursalOfPropietarioListNewPropietario != null && !oldSucursalOfPropietarioListNewPropietario.equals(sucursal)) {
                        oldSucursalOfPropietarioListNewPropietario.getPropietarioList().remove(propietarioListNewPropietario);
                        oldSucursalOfPropietarioListNewPropietario = em.merge(oldSucursalOfPropietarioListNewPropietario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = sucursal.getIdSucursal();
                if (findSucursal(id) == null) {
                    throw new NonexistentEntityException("The sucursal with id " + id + " no longer exists.");
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
            Sucursal sucursal;
            try {
                sucursal = em.getReference(Sucursal.class, id);
                sucursal.getIdSucursal();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sucursal with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Agenda> agendaListOrphanCheck = sucursal.getAgendaList();
            for (Agenda agendaListOrphanCheckAgenda : agendaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sucursal (" + sucursal + ") cannot be destroyed since the Agenda " + agendaListOrphanCheckAgenda + " in its agendaList field has a non-nullable sucursal field.");
            }
            List<DetalleUsuarios> detalleUsuariosListOrphanCheck = sucursal.getDetalleUsuariosList();
            for (DetalleUsuarios detalleUsuariosListOrphanCheckDetalleUsuarios : detalleUsuariosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sucursal (" + sucursal + ") cannot be destroyed since the DetalleUsuarios " + detalleUsuariosListOrphanCheckDetalleUsuarios + " in its detalleUsuariosList field has a non-nullable sucursal field.");
            }
            List<Propietario> propietarioListOrphanCheck = sucursal.getPropietarioList();
            for (Propietario propietarioListOrphanCheckPropietario : propietarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sucursal (" + sucursal + ") cannot be destroyed since the Propietario " + propietarioListOrphanCheckPropietario + " in its propietarioList field has a non-nullable sucursal field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Clinica clinica = sucursal.getClinica();
            if (clinica != null) {
                clinica.getSucursalList().remove(sucursal);
                clinica = em.merge(clinica);
            }
            Comuna comuna = sucursal.getComuna();
            if (comuna != null) {
                comuna.getSucursalList().remove(sucursal);
                comuna = em.merge(comuna);
            }
            em.remove(sucursal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sucursal> findSucursalEntities() {
        return findSucursalEntities(true, -1, -1);
    }

    public List<Sucursal> findSucursalEntities(int maxResults, int firstResult) {
        return findSucursalEntities(false, maxResults, firstResult);
    }

    private List<Sucursal> findSucursalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sucursal.class));
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

    public Sucursal findSucursal(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sucursal.class, id);
        } finally {
            em.close();
        }
    }

    public int getSucursalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sucursal> rt = cq.from(Sucursal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    
    public List<Sucursal> buscarSucursales(Clinica c) {
        try {
            Query consulta = getEntityManager().createNamedQuery("Sucursal.findByClinica");
            consulta.setParameter("clinica", c);
            return consulta.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
    
    public Integer ultimo() {
        try {
            Query consulta = getEntityManager().createNamedQuery("Sucursal.findAllById");
            consulta.setMaxResults(1);
            return ((Sucursal)consulta.getSingleResult()).getIdSucursal()+1;
        } catch (Exception e) {
            return 1;
        }
    }
}
