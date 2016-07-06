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
import cl.starlabs.modelo.Examenes;
import java.util.ArrayList;
import java.util.List;
import cl.starlabs.modelo.Historialvacunas;
import cl.starlabs.modelo.Contraindicaciones;
import cl.starlabs.modelo.Desparacitaciones;
import cl.starlabs.modelo.Farmacos;
import cl.starlabs.modelo.Procedimientos;
import cl.starlabs.modelo.Hospitalizacion;
import cl.starlabs.modelo.AgendaDetalle;
import cl.starlabs.modelo.DetalleUsuarios;
import cl.starlabs.modelo.Anamnesis;
import cl.starlabs.modelo.Patologias;
import cl.starlabs.modelo.Veterinario;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor Manuel Araya
 */
public class VeterinarioJpaController implements Serializable {

    public VeterinarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Veterinario veterinario) throws PreexistingEntityException, Exception {
        if (veterinario.getExamenesList() == null) {
            veterinario.setExamenesList(new ArrayList<Examenes>());
        }
        if (veterinario.getHistorialvacunasList() == null) {
            veterinario.setHistorialvacunasList(new ArrayList<Historialvacunas>());
        }
        if (veterinario.getContraindicacionesList() == null) {
            veterinario.setContraindicacionesList(new ArrayList<Contraindicaciones>());
        }
        if (veterinario.getDesparacitacionesList() == null) {
            veterinario.setDesparacitacionesList(new ArrayList<Desparacitaciones>());
        }
        if (veterinario.getFarmacosList() == null) {
            veterinario.setFarmacosList(new ArrayList<Farmacos>());
        }
        if (veterinario.getProcedimientosList() == null) {
            veterinario.setProcedimientosList(new ArrayList<Procedimientos>());
        }
        if (veterinario.getHospitalizacionList() == null) {
            veterinario.setHospitalizacionList(new ArrayList<Hospitalizacion>());
        }
        if (veterinario.getAgendaDetalleList() == null) {
            veterinario.setAgendaDetalleList(new ArrayList<AgendaDetalle>());
        }
        if (veterinario.getDetalleUsuariosList() == null) {
            veterinario.setDetalleUsuariosList(new ArrayList<DetalleUsuarios>());
        }
        if (veterinario.getAnamnesisList() == null) {
            veterinario.setAnamnesisList(new ArrayList<Anamnesis>());
        }
        if (veterinario.getPatologiasList() == null) {
            veterinario.setPatologiasList(new ArrayList<Patologias>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Examenes> attachedExamenesList = new ArrayList<Examenes>();
            for (Examenes examenesListExamenesToAttach : veterinario.getExamenesList()) {
                examenesListExamenesToAttach = em.getReference(examenesListExamenesToAttach.getClass(), examenesListExamenesToAttach.getIdExamen());
                attachedExamenesList.add(examenesListExamenesToAttach);
            }
            veterinario.setExamenesList(attachedExamenesList);
            List<Historialvacunas> attachedHistorialvacunasList = new ArrayList<Historialvacunas>();
            for (Historialvacunas historialvacunasListHistorialvacunasToAttach : veterinario.getHistorialvacunasList()) {
                historialvacunasListHistorialvacunasToAttach = em.getReference(historialvacunasListHistorialvacunasToAttach.getClass(), historialvacunasListHistorialvacunasToAttach.getIdEvento());
                attachedHistorialvacunasList.add(historialvacunasListHistorialvacunasToAttach);
            }
            veterinario.setHistorialvacunasList(attachedHistorialvacunasList);
            List<Contraindicaciones> attachedContraindicacionesList = new ArrayList<Contraindicaciones>();
            for (Contraindicaciones contraindicacionesListContraindicacionesToAttach : veterinario.getContraindicacionesList()) {
                contraindicacionesListContraindicacionesToAttach = em.getReference(contraindicacionesListContraindicacionesToAttach.getClass(), contraindicacionesListContraindicacionesToAttach.getIdContraindicacion());
                attachedContraindicacionesList.add(contraindicacionesListContraindicacionesToAttach);
            }
            veterinario.setContraindicacionesList(attachedContraindicacionesList);
            List<Desparacitaciones> attachedDesparacitacionesList = new ArrayList<Desparacitaciones>();
            for (Desparacitaciones desparacitacionesListDesparacitacionesToAttach : veterinario.getDesparacitacionesList()) {
                desparacitacionesListDesparacitacionesToAttach = em.getReference(desparacitacionesListDesparacitacionesToAttach.getClass(), desparacitacionesListDesparacitacionesToAttach.getIdDesparacitacion());
                attachedDesparacitacionesList.add(desparacitacionesListDesparacitacionesToAttach);
            }
            veterinario.setDesparacitacionesList(attachedDesparacitacionesList);
            List<Farmacos> attachedFarmacosList = new ArrayList<Farmacos>();
            for (Farmacos farmacosListFarmacosToAttach : veterinario.getFarmacosList()) {
                farmacosListFarmacosToAttach = em.getReference(farmacosListFarmacosToAttach.getClass(), farmacosListFarmacosToAttach.getIdFarmaco());
                attachedFarmacosList.add(farmacosListFarmacosToAttach);
            }
            veterinario.setFarmacosList(attachedFarmacosList);
            List<Procedimientos> attachedProcedimientosList = new ArrayList<Procedimientos>();
            for (Procedimientos procedimientosListProcedimientosToAttach : veterinario.getProcedimientosList()) {
                procedimientosListProcedimientosToAttach = em.getReference(procedimientosListProcedimientosToAttach.getClass(), procedimientosListProcedimientosToAttach.getIdProcedimiento());
                attachedProcedimientosList.add(procedimientosListProcedimientosToAttach);
            }
            veterinario.setProcedimientosList(attachedProcedimientosList);
            List<Hospitalizacion> attachedHospitalizacionList = new ArrayList<Hospitalizacion>();
            for (Hospitalizacion hospitalizacionListHospitalizacionToAttach : veterinario.getHospitalizacionList()) {
                hospitalizacionListHospitalizacionToAttach = em.getReference(hospitalizacionListHospitalizacionToAttach.getClass(), hospitalizacionListHospitalizacionToAttach.getIdHospitalizacion());
                attachedHospitalizacionList.add(hospitalizacionListHospitalizacionToAttach);
            }
            veterinario.setHospitalizacionList(attachedHospitalizacionList);
            List<AgendaDetalle> attachedAgendaDetalleList = new ArrayList<AgendaDetalle>();
            for (AgendaDetalle agendaDetalleListAgendaDetalleToAttach : veterinario.getAgendaDetalleList()) {
                agendaDetalleListAgendaDetalleToAttach = em.getReference(agendaDetalleListAgendaDetalleToAttach.getClass(), agendaDetalleListAgendaDetalleToAttach.getIdDetalle());
                attachedAgendaDetalleList.add(agendaDetalleListAgendaDetalleToAttach);
            }
            veterinario.setAgendaDetalleList(attachedAgendaDetalleList);
            List<DetalleUsuarios> attachedDetalleUsuariosList = new ArrayList<DetalleUsuarios>();
            for (DetalleUsuarios detalleUsuariosListDetalleUsuariosToAttach : veterinario.getDetalleUsuariosList()) {
                detalleUsuariosListDetalleUsuariosToAttach = em.getReference(detalleUsuariosListDetalleUsuariosToAttach.getClass(), detalleUsuariosListDetalleUsuariosToAttach.getId());
                attachedDetalleUsuariosList.add(detalleUsuariosListDetalleUsuariosToAttach);
            }
            veterinario.setDetalleUsuariosList(attachedDetalleUsuariosList);
            List<Anamnesis> attachedAnamnesisList = new ArrayList<Anamnesis>();
            for (Anamnesis anamnesisListAnamnesisToAttach : veterinario.getAnamnesisList()) {
                anamnesisListAnamnesisToAttach = em.getReference(anamnesisListAnamnesisToAttach.getClass(), anamnesisListAnamnesisToAttach.getIdAnamnesis());
                attachedAnamnesisList.add(anamnesisListAnamnesisToAttach);
            }
            veterinario.setAnamnesisList(attachedAnamnesisList);
            List<Patologias> attachedPatologiasList = new ArrayList<Patologias>();
            for (Patologias patologiasListPatologiasToAttach : veterinario.getPatologiasList()) {
                patologiasListPatologiasToAttach = em.getReference(patologiasListPatologiasToAttach.getClass(), patologiasListPatologiasToAttach.getIdPatologia());
                attachedPatologiasList.add(patologiasListPatologiasToAttach);
            }
            veterinario.setPatologiasList(attachedPatologiasList);
            em.persist(veterinario);
            for (Examenes examenesListExamenes : veterinario.getExamenesList()) {
                Veterinario oldVeterinarioOfExamenesListExamenes = examenesListExamenes.getVeterinario();
                examenesListExamenes.setVeterinario(veterinario);
                examenesListExamenes = em.merge(examenesListExamenes);
                if (oldVeterinarioOfExamenesListExamenes != null) {
                    oldVeterinarioOfExamenesListExamenes.getExamenesList().remove(examenesListExamenes);
                    oldVeterinarioOfExamenesListExamenes = em.merge(oldVeterinarioOfExamenesListExamenes);
                }
            }
            for (Historialvacunas historialvacunasListHistorialvacunas : veterinario.getHistorialvacunasList()) {
                Veterinario oldVeterinarioOfHistorialvacunasListHistorialvacunas = historialvacunasListHistorialvacunas.getVeterinario();
                historialvacunasListHistorialvacunas.setVeterinario(veterinario);
                historialvacunasListHistorialvacunas = em.merge(historialvacunasListHistorialvacunas);
                if (oldVeterinarioOfHistorialvacunasListHistorialvacunas != null) {
                    oldVeterinarioOfHistorialvacunasListHistorialvacunas.getHistorialvacunasList().remove(historialvacunasListHistorialvacunas);
                    oldVeterinarioOfHistorialvacunasListHistorialvacunas = em.merge(oldVeterinarioOfHistorialvacunasListHistorialvacunas);
                }
            }
            for (Contraindicaciones contraindicacionesListContraindicaciones : veterinario.getContraindicacionesList()) {
                Veterinario oldVeterinarioOfContraindicacionesListContraindicaciones = contraindicacionesListContraindicaciones.getVeterinario();
                contraindicacionesListContraindicaciones.setVeterinario(veterinario);
                contraindicacionesListContraindicaciones = em.merge(contraindicacionesListContraindicaciones);
                if (oldVeterinarioOfContraindicacionesListContraindicaciones != null) {
                    oldVeterinarioOfContraindicacionesListContraindicaciones.getContraindicacionesList().remove(contraindicacionesListContraindicaciones);
                    oldVeterinarioOfContraindicacionesListContraindicaciones = em.merge(oldVeterinarioOfContraindicacionesListContraindicaciones);
                }
            }
            for (Desparacitaciones desparacitacionesListDesparacitaciones : veterinario.getDesparacitacionesList()) {
                Veterinario oldEspecialistaOfDesparacitacionesListDesparacitaciones = desparacitacionesListDesparacitaciones.getEspecialista();
                desparacitacionesListDesparacitaciones.setEspecialista(veterinario);
                desparacitacionesListDesparacitaciones = em.merge(desparacitacionesListDesparacitaciones);
                if (oldEspecialistaOfDesparacitacionesListDesparacitaciones != null) {
                    oldEspecialistaOfDesparacitacionesListDesparacitaciones.getDesparacitacionesList().remove(desparacitacionesListDesparacitaciones);
                    oldEspecialistaOfDesparacitacionesListDesparacitaciones = em.merge(oldEspecialistaOfDesparacitacionesListDesparacitaciones);
                }
            }
            for (Farmacos farmacosListFarmacos : veterinario.getFarmacosList()) {
                Veterinario oldVeterinarioOfFarmacosListFarmacos = farmacosListFarmacos.getVeterinario();
                farmacosListFarmacos.setVeterinario(veterinario);
                farmacosListFarmacos = em.merge(farmacosListFarmacos);
                if (oldVeterinarioOfFarmacosListFarmacos != null) {
                    oldVeterinarioOfFarmacosListFarmacos.getFarmacosList().remove(farmacosListFarmacos);
                    oldVeterinarioOfFarmacosListFarmacos = em.merge(oldVeterinarioOfFarmacosListFarmacos);
                }
            }
            for (Procedimientos procedimientosListProcedimientos : veterinario.getProcedimientosList()) {
                Veterinario oldVeterinarioOfProcedimientosListProcedimientos = procedimientosListProcedimientos.getVeterinario();
                procedimientosListProcedimientos.setVeterinario(veterinario);
                procedimientosListProcedimientos = em.merge(procedimientosListProcedimientos);
                if (oldVeterinarioOfProcedimientosListProcedimientos != null) {
                    oldVeterinarioOfProcedimientosListProcedimientos.getProcedimientosList().remove(procedimientosListProcedimientos);
                    oldVeterinarioOfProcedimientosListProcedimientos = em.merge(oldVeterinarioOfProcedimientosListProcedimientos);
                }
            }
            for (Hospitalizacion hospitalizacionListHospitalizacion : veterinario.getHospitalizacionList()) {
                Veterinario oldVeterinarioOfHospitalizacionListHospitalizacion = hospitalizacionListHospitalizacion.getVeterinario();
                hospitalizacionListHospitalizacion.setVeterinario(veterinario);
                hospitalizacionListHospitalizacion = em.merge(hospitalizacionListHospitalizacion);
                if (oldVeterinarioOfHospitalizacionListHospitalizacion != null) {
                    oldVeterinarioOfHospitalizacionListHospitalizacion.getHospitalizacionList().remove(hospitalizacionListHospitalizacion);
                    oldVeterinarioOfHospitalizacionListHospitalizacion = em.merge(oldVeterinarioOfHospitalizacionListHospitalizacion);
                }
            }
            for (AgendaDetalle agendaDetalleListAgendaDetalle : veterinario.getAgendaDetalleList()) {
                Veterinario oldVeterinarioOfAgendaDetalleListAgendaDetalle = agendaDetalleListAgendaDetalle.getVeterinario();
                agendaDetalleListAgendaDetalle.setVeterinario(veterinario);
                agendaDetalleListAgendaDetalle = em.merge(agendaDetalleListAgendaDetalle);
                if (oldVeterinarioOfAgendaDetalleListAgendaDetalle != null) {
                    oldVeterinarioOfAgendaDetalleListAgendaDetalle.getAgendaDetalleList().remove(agendaDetalleListAgendaDetalle);
                    oldVeterinarioOfAgendaDetalleListAgendaDetalle = em.merge(oldVeterinarioOfAgendaDetalleListAgendaDetalle);
                }
            }
            for (DetalleUsuarios detalleUsuariosListDetalleUsuarios : veterinario.getDetalleUsuariosList()) {
                Veterinario oldVeterinarioOfDetalleUsuariosListDetalleUsuarios = detalleUsuariosListDetalleUsuarios.getVeterinario();
                detalleUsuariosListDetalleUsuarios.setVeterinario(veterinario);
                detalleUsuariosListDetalleUsuarios = em.merge(detalleUsuariosListDetalleUsuarios);
                if (oldVeterinarioOfDetalleUsuariosListDetalleUsuarios != null) {
                    oldVeterinarioOfDetalleUsuariosListDetalleUsuarios.getDetalleUsuariosList().remove(detalleUsuariosListDetalleUsuarios);
                    oldVeterinarioOfDetalleUsuariosListDetalleUsuarios = em.merge(oldVeterinarioOfDetalleUsuariosListDetalleUsuarios);
                }
            }
            for (Anamnesis anamnesisListAnamnesis : veterinario.getAnamnesisList()) {
                Veterinario oldVeterinarioOfAnamnesisListAnamnesis = anamnesisListAnamnesis.getVeterinario();
                anamnesisListAnamnesis.setVeterinario(veterinario);
                anamnesisListAnamnesis = em.merge(anamnesisListAnamnesis);
                if (oldVeterinarioOfAnamnesisListAnamnesis != null) {
                    oldVeterinarioOfAnamnesisListAnamnesis.getAnamnesisList().remove(anamnesisListAnamnesis);
                    oldVeterinarioOfAnamnesisListAnamnesis = em.merge(oldVeterinarioOfAnamnesisListAnamnesis);
                }
            }
            for (Patologias patologiasListPatologias : veterinario.getPatologiasList()) {
                Veterinario oldVeterinarioOfPatologiasListPatologias = patologiasListPatologias.getVeterinario();
                patologiasListPatologias.setVeterinario(veterinario);
                patologiasListPatologias = em.merge(patologiasListPatologias);
                if (oldVeterinarioOfPatologiasListPatologias != null) {
                    oldVeterinarioOfPatologiasListPatologias.getPatologiasList().remove(patologiasListPatologias);
                    oldVeterinarioOfPatologiasListPatologias = em.merge(oldVeterinarioOfPatologiasListPatologias);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVeterinario(veterinario.getIdVeterinario()) != null) {
                throw new PreexistingEntityException("Veterinario " + veterinario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Veterinario veterinario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Veterinario persistentVeterinario = em.find(Veterinario.class, veterinario.getIdVeterinario());
            List<Examenes> examenesListOld = persistentVeterinario.getExamenesList();
            List<Examenes> examenesListNew = veterinario.getExamenesList();
            List<Historialvacunas> historialvacunasListOld = persistentVeterinario.getHistorialvacunasList();
            List<Historialvacunas> historialvacunasListNew = veterinario.getHistorialvacunasList();
            List<Contraindicaciones> contraindicacionesListOld = persistentVeterinario.getContraindicacionesList();
            List<Contraindicaciones> contraindicacionesListNew = veterinario.getContraindicacionesList();
            List<Desparacitaciones> desparacitacionesListOld = persistentVeterinario.getDesparacitacionesList();
            List<Desparacitaciones> desparacitacionesListNew = veterinario.getDesparacitacionesList();
            List<Farmacos> farmacosListOld = persistentVeterinario.getFarmacosList();
            List<Farmacos> farmacosListNew = veterinario.getFarmacosList();
            List<Procedimientos> procedimientosListOld = persistentVeterinario.getProcedimientosList();
            List<Procedimientos> procedimientosListNew = veterinario.getProcedimientosList();
            List<Hospitalizacion> hospitalizacionListOld = persistentVeterinario.getHospitalizacionList();
            List<Hospitalizacion> hospitalizacionListNew = veterinario.getHospitalizacionList();
            List<AgendaDetalle> agendaDetalleListOld = persistentVeterinario.getAgendaDetalleList();
            List<AgendaDetalle> agendaDetalleListNew = veterinario.getAgendaDetalleList();
            List<DetalleUsuarios> detalleUsuariosListOld = persistentVeterinario.getDetalleUsuariosList();
            List<DetalleUsuarios> detalleUsuariosListNew = veterinario.getDetalleUsuariosList();
            List<Anamnesis> anamnesisListOld = persistentVeterinario.getAnamnesisList();
            List<Anamnesis> anamnesisListNew = veterinario.getAnamnesisList();
            List<Patologias> patologiasListOld = persistentVeterinario.getPatologiasList();
            List<Patologias> patologiasListNew = veterinario.getPatologiasList();
            List<String> illegalOrphanMessages = null;
            for (Examenes examenesListOldExamenes : examenesListOld) {
                if (!examenesListNew.contains(examenesListOldExamenes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Examenes " + examenesListOldExamenes + " since its veterinario field is not nullable.");
                }
            }
            for (Historialvacunas historialvacunasListOldHistorialvacunas : historialvacunasListOld) {
                if (!historialvacunasListNew.contains(historialvacunasListOldHistorialvacunas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Historialvacunas " + historialvacunasListOldHistorialvacunas + " since its veterinario field is not nullable.");
                }
            }
            for (Contraindicaciones contraindicacionesListOldContraindicaciones : contraindicacionesListOld) {
                if (!contraindicacionesListNew.contains(contraindicacionesListOldContraindicaciones)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Contraindicaciones " + contraindicacionesListOldContraindicaciones + " since its veterinario field is not nullable.");
                }
            }
            for (Desparacitaciones desparacitacionesListOldDesparacitaciones : desparacitacionesListOld) {
                if (!desparacitacionesListNew.contains(desparacitacionesListOldDesparacitaciones)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Desparacitaciones " + desparacitacionesListOldDesparacitaciones + " since its especialista field is not nullable.");
                }
            }
            for (Farmacos farmacosListOldFarmacos : farmacosListOld) {
                if (!farmacosListNew.contains(farmacosListOldFarmacos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Farmacos " + farmacosListOldFarmacos + " since its veterinario field is not nullable.");
                }
            }
            for (Procedimientos procedimientosListOldProcedimientos : procedimientosListOld) {
                if (!procedimientosListNew.contains(procedimientosListOldProcedimientos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Procedimientos " + procedimientosListOldProcedimientos + " since its veterinario field is not nullable.");
                }
            }
            for (Hospitalizacion hospitalizacionListOldHospitalizacion : hospitalizacionListOld) {
                if (!hospitalizacionListNew.contains(hospitalizacionListOldHospitalizacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Hospitalizacion " + hospitalizacionListOldHospitalizacion + " since its veterinario field is not nullable.");
                }
            }
            for (AgendaDetalle agendaDetalleListOldAgendaDetalle : agendaDetalleListOld) {
                if (!agendaDetalleListNew.contains(agendaDetalleListOldAgendaDetalle)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AgendaDetalle " + agendaDetalleListOldAgendaDetalle + " since its veterinario field is not nullable.");
                }
            }
            for (Anamnesis anamnesisListOldAnamnesis : anamnesisListOld) {
                if (!anamnesisListNew.contains(anamnesisListOldAnamnesis)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Anamnesis " + anamnesisListOldAnamnesis + " since its veterinario field is not nullable.");
                }
            }
            for (Patologias patologiasListOldPatologias : patologiasListOld) {
                if (!patologiasListNew.contains(patologiasListOldPatologias)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Patologias " + patologiasListOldPatologias + " since its veterinario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Examenes> attachedExamenesListNew = new ArrayList<Examenes>();
            for (Examenes examenesListNewExamenesToAttach : examenesListNew) {
                examenesListNewExamenesToAttach = em.getReference(examenesListNewExamenesToAttach.getClass(), examenesListNewExamenesToAttach.getIdExamen());
                attachedExamenesListNew.add(examenesListNewExamenesToAttach);
            }
            examenesListNew = attachedExamenesListNew;
            veterinario.setExamenesList(examenesListNew);
            List<Historialvacunas> attachedHistorialvacunasListNew = new ArrayList<Historialvacunas>();
            for (Historialvacunas historialvacunasListNewHistorialvacunasToAttach : historialvacunasListNew) {
                historialvacunasListNewHistorialvacunasToAttach = em.getReference(historialvacunasListNewHistorialvacunasToAttach.getClass(), historialvacunasListNewHistorialvacunasToAttach.getIdEvento());
                attachedHistorialvacunasListNew.add(historialvacunasListNewHistorialvacunasToAttach);
            }
            historialvacunasListNew = attachedHistorialvacunasListNew;
            veterinario.setHistorialvacunasList(historialvacunasListNew);
            List<Contraindicaciones> attachedContraindicacionesListNew = new ArrayList<Contraindicaciones>();
            for (Contraindicaciones contraindicacionesListNewContraindicacionesToAttach : contraindicacionesListNew) {
                contraindicacionesListNewContraindicacionesToAttach = em.getReference(contraindicacionesListNewContraindicacionesToAttach.getClass(), contraindicacionesListNewContraindicacionesToAttach.getIdContraindicacion());
                attachedContraindicacionesListNew.add(contraindicacionesListNewContraindicacionesToAttach);
            }
            contraindicacionesListNew = attachedContraindicacionesListNew;
            veterinario.setContraindicacionesList(contraindicacionesListNew);
            List<Desparacitaciones> attachedDesparacitacionesListNew = new ArrayList<Desparacitaciones>();
            for (Desparacitaciones desparacitacionesListNewDesparacitacionesToAttach : desparacitacionesListNew) {
                desparacitacionesListNewDesparacitacionesToAttach = em.getReference(desparacitacionesListNewDesparacitacionesToAttach.getClass(), desparacitacionesListNewDesparacitacionesToAttach.getIdDesparacitacion());
                attachedDesparacitacionesListNew.add(desparacitacionesListNewDesparacitacionesToAttach);
            }
            desparacitacionesListNew = attachedDesparacitacionesListNew;
            veterinario.setDesparacitacionesList(desparacitacionesListNew);
            List<Farmacos> attachedFarmacosListNew = new ArrayList<Farmacos>();
            for (Farmacos farmacosListNewFarmacosToAttach : farmacosListNew) {
                farmacosListNewFarmacosToAttach = em.getReference(farmacosListNewFarmacosToAttach.getClass(), farmacosListNewFarmacosToAttach.getIdFarmaco());
                attachedFarmacosListNew.add(farmacosListNewFarmacosToAttach);
            }
            farmacosListNew = attachedFarmacosListNew;
            veterinario.setFarmacosList(farmacosListNew);
            List<Procedimientos> attachedProcedimientosListNew = new ArrayList<Procedimientos>();
            for (Procedimientos procedimientosListNewProcedimientosToAttach : procedimientosListNew) {
                procedimientosListNewProcedimientosToAttach = em.getReference(procedimientosListNewProcedimientosToAttach.getClass(), procedimientosListNewProcedimientosToAttach.getIdProcedimiento());
                attachedProcedimientosListNew.add(procedimientosListNewProcedimientosToAttach);
            }
            procedimientosListNew = attachedProcedimientosListNew;
            veterinario.setProcedimientosList(procedimientosListNew);
            List<Hospitalizacion> attachedHospitalizacionListNew = new ArrayList<Hospitalizacion>();
            for (Hospitalizacion hospitalizacionListNewHospitalizacionToAttach : hospitalizacionListNew) {
                hospitalizacionListNewHospitalizacionToAttach = em.getReference(hospitalizacionListNewHospitalizacionToAttach.getClass(), hospitalizacionListNewHospitalizacionToAttach.getIdHospitalizacion());
                attachedHospitalizacionListNew.add(hospitalizacionListNewHospitalizacionToAttach);
            }
            hospitalizacionListNew = attachedHospitalizacionListNew;
            veterinario.setHospitalizacionList(hospitalizacionListNew);
            List<AgendaDetalle> attachedAgendaDetalleListNew = new ArrayList<AgendaDetalle>();
            for (AgendaDetalle agendaDetalleListNewAgendaDetalleToAttach : agendaDetalleListNew) {
                agendaDetalleListNewAgendaDetalleToAttach = em.getReference(agendaDetalleListNewAgendaDetalleToAttach.getClass(), agendaDetalleListNewAgendaDetalleToAttach.getIdDetalle());
                attachedAgendaDetalleListNew.add(agendaDetalleListNewAgendaDetalleToAttach);
            }
            agendaDetalleListNew = attachedAgendaDetalleListNew;
            veterinario.setAgendaDetalleList(agendaDetalleListNew);
            List<DetalleUsuarios> attachedDetalleUsuariosListNew = new ArrayList<DetalleUsuarios>();
            for (DetalleUsuarios detalleUsuariosListNewDetalleUsuariosToAttach : detalleUsuariosListNew) {
                detalleUsuariosListNewDetalleUsuariosToAttach = em.getReference(detalleUsuariosListNewDetalleUsuariosToAttach.getClass(), detalleUsuariosListNewDetalleUsuariosToAttach.getId());
                attachedDetalleUsuariosListNew.add(detalleUsuariosListNewDetalleUsuariosToAttach);
            }
            detalleUsuariosListNew = attachedDetalleUsuariosListNew;
            veterinario.setDetalleUsuariosList(detalleUsuariosListNew);
            List<Anamnesis> attachedAnamnesisListNew = new ArrayList<Anamnesis>();
            for (Anamnesis anamnesisListNewAnamnesisToAttach : anamnesisListNew) {
                anamnesisListNewAnamnesisToAttach = em.getReference(anamnesisListNewAnamnesisToAttach.getClass(), anamnesisListNewAnamnesisToAttach.getIdAnamnesis());
                attachedAnamnesisListNew.add(anamnesisListNewAnamnesisToAttach);
            }
            anamnesisListNew = attachedAnamnesisListNew;
            veterinario.setAnamnesisList(anamnesisListNew);
            List<Patologias> attachedPatologiasListNew = new ArrayList<Patologias>();
            for (Patologias patologiasListNewPatologiasToAttach : patologiasListNew) {
                patologiasListNewPatologiasToAttach = em.getReference(patologiasListNewPatologiasToAttach.getClass(), patologiasListNewPatologiasToAttach.getIdPatologia());
                attachedPatologiasListNew.add(patologiasListNewPatologiasToAttach);
            }
            patologiasListNew = attachedPatologiasListNew;
            veterinario.setPatologiasList(patologiasListNew);
            veterinario = em.merge(veterinario);
            for (Examenes examenesListNewExamenes : examenesListNew) {
                if (!examenesListOld.contains(examenesListNewExamenes)) {
                    Veterinario oldVeterinarioOfExamenesListNewExamenes = examenesListNewExamenes.getVeterinario();
                    examenesListNewExamenes.setVeterinario(veterinario);
                    examenesListNewExamenes = em.merge(examenesListNewExamenes);
                    if (oldVeterinarioOfExamenesListNewExamenes != null && !oldVeterinarioOfExamenesListNewExamenes.equals(veterinario)) {
                        oldVeterinarioOfExamenesListNewExamenes.getExamenesList().remove(examenesListNewExamenes);
                        oldVeterinarioOfExamenesListNewExamenes = em.merge(oldVeterinarioOfExamenesListNewExamenes);
                    }
                }
            }
            for (Historialvacunas historialvacunasListNewHistorialvacunas : historialvacunasListNew) {
                if (!historialvacunasListOld.contains(historialvacunasListNewHistorialvacunas)) {
                    Veterinario oldVeterinarioOfHistorialvacunasListNewHistorialvacunas = historialvacunasListNewHistorialvacunas.getVeterinario();
                    historialvacunasListNewHistorialvacunas.setVeterinario(veterinario);
                    historialvacunasListNewHistorialvacunas = em.merge(historialvacunasListNewHistorialvacunas);
                    if (oldVeterinarioOfHistorialvacunasListNewHistorialvacunas != null && !oldVeterinarioOfHistorialvacunasListNewHistorialvacunas.equals(veterinario)) {
                        oldVeterinarioOfHistorialvacunasListNewHistorialvacunas.getHistorialvacunasList().remove(historialvacunasListNewHistorialvacunas);
                        oldVeterinarioOfHistorialvacunasListNewHistorialvacunas = em.merge(oldVeterinarioOfHistorialvacunasListNewHistorialvacunas);
                    }
                }
            }
            for (Contraindicaciones contraindicacionesListNewContraindicaciones : contraindicacionesListNew) {
                if (!contraindicacionesListOld.contains(contraindicacionesListNewContraindicaciones)) {
                    Veterinario oldVeterinarioOfContraindicacionesListNewContraindicaciones = contraindicacionesListNewContraindicaciones.getVeterinario();
                    contraindicacionesListNewContraindicaciones.setVeterinario(veterinario);
                    contraindicacionesListNewContraindicaciones = em.merge(contraindicacionesListNewContraindicaciones);
                    if (oldVeterinarioOfContraindicacionesListNewContraindicaciones != null && !oldVeterinarioOfContraindicacionesListNewContraindicaciones.equals(veterinario)) {
                        oldVeterinarioOfContraindicacionesListNewContraindicaciones.getContraindicacionesList().remove(contraindicacionesListNewContraindicaciones);
                        oldVeterinarioOfContraindicacionesListNewContraindicaciones = em.merge(oldVeterinarioOfContraindicacionesListNewContraindicaciones);
                    }
                }
            }
            for (Desparacitaciones desparacitacionesListNewDesparacitaciones : desparacitacionesListNew) {
                if (!desparacitacionesListOld.contains(desparacitacionesListNewDesparacitaciones)) {
                    Veterinario oldEspecialistaOfDesparacitacionesListNewDesparacitaciones = desparacitacionesListNewDesparacitaciones.getEspecialista();
                    desparacitacionesListNewDesparacitaciones.setEspecialista(veterinario);
                    desparacitacionesListNewDesparacitaciones = em.merge(desparacitacionesListNewDesparacitaciones);
                    if (oldEspecialistaOfDesparacitacionesListNewDesparacitaciones != null && !oldEspecialistaOfDesparacitacionesListNewDesparacitaciones.equals(veterinario)) {
                        oldEspecialistaOfDesparacitacionesListNewDesparacitaciones.getDesparacitacionesList().remove(desparacitacionesListNewDesparacitaciones);
                        oldEspecialistaOfDesparacitacionesListNewDesparacitaciones = em.merge(oldEspecialistaOfDesparacitacionesListNewDesparacitaciones);
                    }
                }
            }
            for (Farmacos farmacosListNewFarmacos : farmacosListNew) {
                if (!farmacosListOld.contains(farmacosListNewFarmacos)) {
                    Veterinario oldVeterinarioOfFarmacosListNewFarmacos = farmacosListNewFarmacos.getVeterinario();
                    farmacosListNewFarmacos.setVeterinario(veterinario);
                    farmacosListNewFarmacos = em.merge(farmacosListNewFarmacos);
                    if (oldVeterinarioOfFarmacosListNewFarmacos != null && !oldVeterinarioOfFarmacosListNewFarmacos.equals(veterinario)) {
                        oldVeterinarioOfFarmacosListNewFarmacos.getFarmacosList().remove(farmacosListNewFarmacos);
                        oldVeterinarioOfFarmacosListNewFarmacos = em.merge(oldVeterinarioOfFarmacosListNewFarmacos);
                    }
                }
            }
            for (Procedimientos procedimientosListNewProcedimientos : procedimientosListNew) {
                if (!procedimientosListOld.contains(procedimientosListNewProcedimientos)) {
                    Veterinario oldVeterinarioOfProcedimientosListNewProcedimientos = procedimientosListNewProcedimientos.getVeterinario();
                    procedimientosListNewProcedimientos.setVeterinario(veterinario);
                    procedimientosListNewProcedimientos = em.merge(procedimientosListNewProcedimientos);
                    if (oldVeterinarioOfProcedimientosListNewProcedimientos != null && !oldVeterinarioOfProcedimientosListNewProcedimientos.equals(veterinario)) {
                        oldVeterinarioOfProcedimientosListNewProcedimientos.getProcedimientosList().remove(procedimientosListNewProcedimientos);
                        oldVeterinarioOfProcedimientosListNewProcedimientos = em.merge(oldVeterinarioOfProcedimientosListNewProcedimientos);
                    }
                }
            }
            for (Hospitalizacion hospitalizacionListNewHospitalizacion : hospitalizacionListNew) {
                if (!hospitalizacionListOld.contains(hospitalizacionListNewHospitalizacion)) {
                    Veterinario oldVeterinarioOfHospitalizacionListNewHospitalizacion = hospitalizacionListNewHospitalizacion.getVeterinario();
                    hospitalizacionListNewHospitalizacion.setVeterinario(veterinario);
                    hospitalizacionListNewHospitalizacion = em.merge(hospitalizacionListNewHospitalizacion);
                    if (oldVeterinarioOfHospitalizacionListNewHospitalizacion != null && !oldVeterinarioOfHospitalizacionListNewHospitalizacion.equals(veterinario)) {
                        oldVeterinarioOfHospitalizacionListNewHospitalizacion.getHospitalizacionList().remove(hospitalizacionListNewHospitalizacion);
                        oldVeterinarioOfHospitalizacionListNewHospitalizacion = em.merge(oldVeterinarioOfHospitalizacionListNewHospitalizacion);
                    }
                }
            }
            for (AgendaDetalle agendaDetalleListNewAgendaDetalle : agendaDetalleListNew) {
                if (!agendaDetalleListOld.contains(agendaDetalleListNewAgendaDetalle)) {
                    Veterinario oldVeterinarioOfAgendaDetalleListNewAgendaDetalle = agendaDetalleListNewAgendaDetalle.getVeterinario();
                    agendaDetalleListNewAgendaDetalle.setVeterinario(veterinario);
                    agendaDetalleListNewAgendaDetalle = em.merge(agendaDetalleListNewAgendaDetalle);
                    if (oldVeterinarioOfAgendaDetalleListNewAgendaDetalle != null && !oldVeterinarioOfAgendaDetalleListNewAgendaDetalle.equals(veterinario)) {
                        oldVeterinarioOfAgendaDetalleListNewAgendaDetalle.getAgendaDetalleList().remove(agendaDetalleListNewAgendaDetalle);
                        oldVeterinarioOfAgendaDetalleListNewAgendaDetalle = em.merge(oldVeterinarioOfAgendaDetalleListNewAgendaDetalle);
                    }
                }
            }
            for (DetalleUsuarios detalleUsuariosListOldDetalleUsuarios : detalleUsuariosListOld) {
                if (!detalleUsuariosListNew.contains(detalleUsuariosListOldDetalleUsuarios)) {
                    detalleUsuariosListOldDetalleUsuarios.setVeterinario(null);
                    detalleUsuariosListOldDetalleUsuarios = em.merge(detalleUsuariosListOldDetalleUsuarios);
                }
            }
            for (DetalleUsuarios detalleUsuariosListNewDetalleUsuarios : detalleUsuariosListNew) {
                if (!detalleUsuariosListOld.contains(detalleUsuariosListNewDetalleUsuarios)) {
                    Veterinario oldVeterinarioOfDetalleUsuariosListNewDetalleUsuarios = detalleUsuariosListNewDetalleUsuarios.getVeterinario();
                    detalleUsuariosListNewDetalleUsuarios.setVeterinario(veterinario);
                    detalleUsuariosListNewDetalleUsuarios = em.merge(detalleUsuariosListNewDetalleUsuarios);
                    if (oldVeterinarioOfDetalleUsuariosListNewDetalleUsuarios != null && !oldVeterinarioOfDetalleUsuariosListNewDetalleUsuarios.equals(veterinario)) {
                        oldVeterinarioOfDetalleUsuariosListNewDetalleUsuarios.getDetalleUsuariosList().remove(detalleUsuariosListNewDetalleUsuarios);
                        oldVeterinarioOfDetalleUsuariosListNewDetalleUsuarios = em.merge(oldVeterinarioOfDetalleUsuariosListNewDetalleUsuarios);
                    }
                }
            }
            for (Anamnesis anamnesisListNewAnamnesis : anamnesisListNew) {
                if (!anamnesisListOld.contains(anamnesisListNewAnamnesis)) {
                    Veterinario oldVeterinarioOfAnamnesisListNewAnamnesis = anamnesisListNewAnamnesis.getVeterinario();
                    anamnesisListNewAnamnesis.setVeterinario(veterinario);
                    anamnesisListNewAnamnesis = em.merge(anamnesisListNewAnamnesis);
                    if (oldVeterinarioOfAnamnesisListNewAnamnesis != null && !oldVeterinarioOfAnamnesisListNewAnamnesis.equals(veterinario)) {
                        oldVeterinarioOfAnamnesisListNewAnamnesis.getAnamnesisList().remove(anamnesisListNewAnamnesis);
                        oldVeterinarioOfAnamnesisListNewAnamnesis = em.merge(oldVeterinarioOfAnamnesisListNewAnamnesis);
                    }
                }
            }
            for (Patologias patologiasListNewPatologias : patologiasListNew) {
                if (!patologiasListOld.contains(patologiasListNewPatologias)) {
                    Veterinario oldVeterinarioOfPatologiasListNewPatologias = patologiasListNewPatologias.getVeterinario();
                    patologiasListNewPatologias.setVeterinario(veterinario);
                    patologiasListNewPatologias = em.merge(patologiasListNewPatologias);
                    if (oldVeterinarioOfPatologiasListNewPatologias != null && !oldVeterinarioOfPatologiasListNewPatologias.equals(veterinario)) {
                        oldVeterinarioOfPatologiasListNewPatologias.getPatologiasList().remove(patologiasListNewPatologias);
                        oldVeterinarioOfPatologiasListNewPatologias = em.merge(oldVeterinarioOfPatologiasListNewPatologias);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = veterinario.getIdVeterinario();
                if (findVeterinario(id) == null) {
                    throw new NonexistentEntityException("The veterinario with id " + id + " no longer exists.");
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
            Veterinario veterinario;
            try {
                veterinario = em.getReference(Veterinario.class, id);
                veterinario.getIdVeterinario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The veterinario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Examenes> examenesListOrphanCheck = veterinario.getExamenesList();
            for (Examenes examenesListOrphanCheckExamenes : examenesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Veterinario (" + veterinario + ") cannot be destroyed since the Examenes " + examenesListOrphanCheckExamenes + " in its examenesList field has a non-nullable veterinario field.");
            }
            List<Historialvacunas> historialvacunasListOrphanCheck = veterinario.getHistorialvacunasList();
            for (Historialvacunas historialvacunasListOrphanCheckHistorialvacunas : historialvacunasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Veterinario (" + veterinario + ") cannot be destroyed since the Historialvacunas " + historialvacunasListOrphanCheckHistorialvacunas + " in its historialvacunasList field has a non-nullable veterinario field.");
            }
            List<Contraindicaciones> contraindicacionesListOrphanCheck = veterinario.getContraindicacionesList();
            for (Contraindicaciones contraindicacionesListOrphanCheckContraindicaciones : contraindicacionesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Veterinario (" + veterinario + ") cannot be destroyed since the Contraindicaciones " + contraindicacionesListOrphanCheckContraindicaciones + " in its contraindicacionesList field has a non-nullable veterinario field.");
            }
            List<Desparacitaciones> desparacitacionesListOrphanCheck = veterinario.getDesparacitacionesList();
            for (Desparacitaciones desparacitacionesListOrphanCheckDesparacitaciones : desparacitacionesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Veterinario (" + veterinario + ") cannot be destroyed since the Desparacitaciones " + desparacitacionesListOrphanCheckDesparacitaciones + " in its desparacitacionesList field has a non-nullable especialista field.");
            }
            List<Farmacos> farmacosListOrphanCheck = veterinario.getFarmacosList();
            for (Farmacos farmacosListOrphanCheckFarmacos : farmacosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Veterinario (" + veterinario + ") cannot be destroyed since the Farmacos " + farmacosListOrphanCheckFarmacos + " in its farmacosList field has a non-nullable veterinario field.");
            }
            List<Procedimientos> procedimientosListOrphanCheck = veterinario.getProcedimientosList();
            for (Procedimientos procedimientosListOrphanCheckProcedimientos : procedimientosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Veterinario (" + veterinario + ") cannot be destroyed since the Procedimientos " + procedimientosListOrphanCheckProcedimientos + " in its procedimientosList field has a non-nullable veterinario field.");
            }
            List<Hospitalizacion> hospitalizacionListOrphanCheck = veterinario.getHospitalizacionList();
            for (Hospitalizacion hospitalizacionListOrphanCheckHospitalizacion : hospitalizacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Veterinario (" + veterinario + ") cannot be destroyed since the Hospitalizacion " + hospitalizacionListOrphanCheckHospitalizacion + " in its hospitalizacionList field has a non-nullable veterinario field.");
            }
            List<AgendaDetalle> agendaDetalleListOrphanCheck = veterinario.getAgendaDetalleList();
            for (AgendaDetalle agendaDetalleListOrphanCheckAgendaDetalle : agendaDetalleListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Veterinario (" + veterinario + ") cannot be destroyed since the AgendaDetalle " + agendaDetalleListOrphanCheckAgendaDetalle + " in its agendaDetalleList field has a non-nullable veterinario field.");
            }
            List<Anamnesis> anamnesisListOrphanCheck = veterinario.getAnamnesisList();
            for (Anamnesis anamnesisListOrphanCheckAnamnesis : anamnesisListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Veterinario (" + veterinario + ") cannot be destroyed since the Anamnesis " + anamnesisListOrphanCheckAnamnesis + " in its anamnesisList field has a non-nullable veterinario field.");
            }
            List<Patologias> patologiasListOrphanCheck = veterinario.getPatologiasList();
            for (Patologias patologiasListOrphanCheckPatologias : patologiasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Veterinario (" + veterinario + ") cannot be destroyed since the Patologias " + patologiasListOrphanCheckPatologias + " in its patologiasList field has a non-nullable veterinario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<DetalleUsuarios> detalleUsuariosList = veterinario.getDetalleUsuariosList();
            for (DetalleUsuarios detalleUsuariosListDetalleUsuarios : detalleUsuariosList) {
                detalleUsuariosListDetalleUsuarios.setVeterinario(null);
                detalleUsuariosListDetalleUsuarios = em.merge(detalleUsuariosListDetalleUsuarios);
            }
            em.remove(veterinario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Veterinario> findVeterinarioEntities() {
        return findVeterinarioEntities(true, -1, -1);
    }

    public List<Veterinario> findVeterinarioEntities(int maxResults, int firstResult) {
        return findVeterinarioEntities(false, maxResults, firstResult);
    }

    private List<Veterinario> findVeterinarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Veterinario.class));
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

    public Veterinario findVeterinario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Veterinario.class, id);
        } finally {
            em.close();
        }
    }

    public int getVeterinarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Veterinario> rt = cq.from(Veterinario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Veterinario buscarVeterinarioPorRut(String rut) {
        try {
            Query consulta = getEntityManager().createNamedQuery("Veterinario.findByRut");
            consulta.setParameter("rut", rut);
            return (Veterinario)consulta.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    public Integer ultimo() {
        try {
            Query consulta = getEntityManager().createNamedQuery("Veterinario.findAllDesc");
            consulta.setMaxResults(1);
            return ((Veterinario)consulta.getSingleResult()).getIdVeterinario()+1;
        } catch (Exception e) {
            return 1;
        }
    }
    
    public List<Veterinario> buscarMultiple(String campo, String valor) {
        switch(campo) {
            case "Run":
                try {
                    Query consulta = getEntityManager().createNamedQuery("Veterinario.findByRut");
                    consulta.setParameter("rut", valor.replace(".", "").split("-")[0]);
                    return consulta.getResultList();
                    } catch (Exception e) {
                    return null;
                }
            case "Nombres":
                try {
                    Query consulta = getEntityManager().createNamedQuery("Veterinario.findByNombres");
                    consulta.setParameter("nombres", valor);
                    return consulta.getResultList();
                } catch (Exception e) {
                    return null;
                }
            case "Apellido Paterno":
                try {
                    Query consulta = getEntityManager().createNamedQuery("Veterinario.findByApaterno");
                    consulta.setParameter("apaterno", valor);
                    return consulta.getResultList();
                } catch (Exception e) {
                    return null;
                }
            case "Apellido Materno":
                try {
                    Query consulta = getEntityManager().createNamedQuery("Veterinario.findByAmaterno");
                    consulta.setParameter("amaterno", valor);
                    return consulta.getResultList();
                } catch (Exception e) {
                    return null;
                }
            case "Especialidad":
                try {
                    Query consulta = getEntityManager().createNamedQuery("Veterinario.findByEspecialidad");
                    consulta.setParameter("especialidad", valor);
                    return consulta.getResultList();
                } catch (Exception e) {
                    return null;
                }

                    default:
                return null;
        }
    }
    
    public void actualizar(Veterinario v) {
        EntityManager em;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(v);
            em.getTransaction().commit();
        } catch (Exception e) {
            em = getEntityManager();
            em.getTransaction().begin();
            em.getTransaction().rollback();
            em.close();
        }
    }
}
