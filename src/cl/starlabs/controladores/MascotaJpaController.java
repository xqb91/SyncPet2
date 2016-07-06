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
import cl.starlabs.modelo.Caracter;
import cl.starlabs.modelo.Habitad;
import cl.starlabs.modelo.Mascota;
import cl.starlabs.modelo.Propietario;
import cl.starlabs.modelo.Raza;
import cl.starlabs.modelo.Examenes;
import java.util.ArrayList;
import java.util.List;
import cl.starlabs.modelo.Historialvacunas;
import cl.starlabs.modelo.Contraindicaciones;
import cl.starlabs.modelo.Desparacitaciones;
import cl.starlabs.modelo.Alergias;
import cl.starlabs.modelo.Farmacos;
import cl.starlabs.modelo.Procedimientos;
import cl.starlabs.modelo.Hospitalizacion;
import cl.starlabs.modelo.AgendaDetalle;
import cl.starlabs.modelo.Anamnesis;
import cl.starlabs.modelo.Patologias;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor Manuel Araya
 */
public class MascotaJpaController implements Serializable {

    public MascotaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Mascota mascota) throws PreexistingEntityException, Exception {
        if (mascota.getExamenesList() == null) {
            mascota.setExamenesList(new ArrayList<Examenes>());
        }
        if (mascota.getHistorialvacunasList() == null) {
            mascota.setHistorialvacunasList(new ArrayList<Historialvacunas>());
        }
        if (mascota.getContraindicacionesList() == null) {
            mascota.setContraindicacionesList(new ArrayList<Contraindicaciones>());
        }
        if (mascota.getDesparacitacionesList() == null) {
            mascota.setDesparacitacionesList(new ArrayList<Desparacitaciones>());
        }
        if (mascota.getAlergiasList() == null) {
            mascota.setAlergiasList(new ArrayList<Alergias>());
        }
        if (mascota.getMascotaList() == null) {
            mascota.setMascotaList(new ArrayList<Mascota>());
        }
        if (mascota.getMascotaList1() == null) {
            mascota.setMascotaList1(new ArrayList<Mascota>());
        }
        if (mascota.getFarmacosList() == null) {
            mascota.setFarmacosList(new ArrayList<Farmacos>());
        }
        if (mascota.getProcedimientosList() == null) {
            mascota.setProcedimientosList(new ArrayList<Procedimientos>());
        }
        if (mascota.getHospitalizacionList() == null) {
            mascota.setHospitalizacionList(new ArrayList<Hospitalizacion>());
        }
        if (mascota.getAgendaDetalleList() == null) {
            mascota.setAgendaDetalleList(new ArrayList<AgendaDetalle>());
        }
        if (mascota.getAnamnesisList() == null) {
            mascota.setAnamnesisList(new ArrayList<Anamnesis>());
        }
        if (mascota.getPatologiasList() == null) {
            mascota.setPatologiasList(new ArrayList<Patologias>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Caracter caracter = mascota.getCaracter();
            if (caracter != null) {
                caracter = em.getReference(caracter.getClass(), caracter.getIdCaracter());
                mascota.setCaracter(caracter);
            }
            Habitad habitad = mascota.getHabitad();
            if (habitad != null) {
                habitad = em.getReference(habitad.getClass(), habitad.getIdHabitad());
                mascota.setHabitad(habitad);
            }
            Mascota madre = mascota.getMadre();
            if (madre != null) {
                madre = em.getReference(madre.getClass(), madre.getIdMascota());
                mascota.setMadre(madre);
            }
            Mascota padre = mascota.getPadre();
            if (padre != null) {
                padre = em.getReference(padre.getClass(), padre.getIdMascota());
                mascota.setPadre(padre);
            }
            Propietario propietario = mascota.getPropietario();
            if (propietario != null) {
                propietario = em.getReference(propietario.getClass(), propietario.getIdPropietario());
                mascota.setPropietario(propietario);
            }
            Raza raza = mascota.getRaza();
            if (raza != null) {
                raza = em.getReference(raza.getClass(), raza.getIdRaza());
                mascota.setRaza(raza);
            }
            List<Examenes> attachedExamenesList = new ArrayList<Examenes>();
            for (Examenes examenesListExamenesToAttach : mascota.getExamenesList()) {
                examenesListExamenesToAttach = em.getReference(examenesListExamenesToAttach.getClass(), examenesListExamenesToAttach.getIdExamen());
                attachedExamenesList.add(examenesListExamenesToAttach);
            }
            mascota.setExamenesList(attachedExamenesList);
            List<Historialvacunas> attachedHistorialvacunasList = new ArrayList<Historialvacunas>();
            for (Historialvacunas historialvacunasListHistorialvacunasToAttach : mascota.getHistorialvacunasList()) {
                historialvacunasListHistorialvacunasToAttach = em.getReference(historialvacunasListHistorialvacunasToAttach.getClass(), historialvacunasListHistorialvacunasToAttach.getIdEvento());
                attachedHistorialvacunasList.add(historialvacunasListHistorialvacunasToAttach);
            }
            mascota.setHistorialvacunasList(attachedHistorialvacunasList);
            List<Contraindicaciones> attachedContraindicacionesList = new ArrayList<Contraindicaciones>();
            for (Contraindicaciones contraindicacionesListContraindicacionesToAttach : mascota.getContraindicacionesList()) {
                contraindicacionesListContraindicacionesToAttach = em.getReference(contraindicacionesListContraindicacionesToAttach.getClass(), contraindicacionesListContraindicacionesToAttach.getIdContraindicacion());
                attachedContraindicacionesList.add(contraindicacionesListContraindicacionesToAttach);
            }
            mascota.setContraindicacionesList(attachedContraindicacionesList);
            List<Desparacitaciones> attachedDesparacitacionesList = new ArrayList<Desparacitaciones>();
            for (Desparacitaciones desparacitacionesListDesparacitacionesToAttach : mascota.getDesparacitacionesList()) {
                desparacitacionesListDesparacitacionesToAttach = em.getReference(desparacitacionesListDesparacitacionesToAttach.getClass(), desparacitacionesListDesparacitacionesToAttach.getIdDesparacitacion());
                attachedDesparacitacionesList.add(desparacitacionesListDesparacitacionesToAttach);
            }
            mascota.setDesparacitacionesList(attachedDesparacitacionesList);
            List<Alergias> attachedAlergiasList = new ArrayList<Alergias>();
            for (Alergias alergiasListAlergiasToAttach : mascota.getAlergiasList()) {
                alergiasListAlergiasToAttach = em.getReference(alergiasListAlergiasToAttach.getClass(), alergiasListAlergiasToAttach.getIdAlergia());
                attachedAlergiasList.add(alergiasListAlergiasToAttach);
            }
            mascota.setAlergiasList(attachedAlergiasList);
            List<Mascota> attachedMascotaList = new ArrayList<Mascota>();
            for (Mascota mascotaListMascotaToAttach : mascota.getMascotaList()) {
                mascotaListMascotaToAttach = em.getReference(mascotaListMascotaToAttach.getClass(), mascotaListMascotaToAttach.getIdMascota());
                attachedMascotaList.add(mascotaListMascotaToAttach);
            }
            mascota.setMascotaList(attachedMascotaList);
            List<Mascota> attachedMascotaList1 = new ArrayList<Mascota>();
            for (Mascota mascotaList1MascotaToAttach : mascota.getMascotaList1()) {
                mascotaList1MascotaToAttach = em.getReference(mascotaList1MascotaToAttach.getClass(), mascotaList1MascotaToAttach.getIdMascota());
                attachedMascotaList1.add(mascotaList1MascotaToAttach);
            }
            mascota.setMascotaList1(attachedMascotaList1);
            List<Farmacos> attachedFarmacosList = new ArrayList<Farmacos>();
            for (Farmacos farmacosListFarmacosToAttach : mascota.getFarmacosList()) {
                farmacosListFarmacosToAttach = em.getReference(farmacosListFarmacosToAttach.getClass(), farmacosListFarmacosToAttach.getIdFarmaco());
                attachedFarmacosList.add(farmacosListFarmacosToAttach);
            }
            mascota.setFarmacosList(attachedFarmacosList);
            List<Procedimientos> attachedProcedimientosList = new ArrayList<Procedimientos>();
            for (Procedimientos procedimientosListProcedimientosToAttach : mascota.getProcedimientosList()) {
                procedimientosListProcedimientosToAttach = em.getReference(procedimientosListProcedimientosToAttach.getClass(), procedimientosListProcedimientosToAttach.getIdProcedimiento());
                attachedProcedimientosList.add(procedimientosListProcedimientosToAttach);
            }
            mascota.setProcedimientosList(attachedProcedimientosList);
            List<Hospitalizacion> attachedHospitalizacionList = new ArrayList<Hospitalizacion>();
            for (Hospitalizacion hospitalizacionListHospitalizacionToAttach : mascota.getHospitalizacionList()) {
                hospitalizacionListHospitalizacionToAttach = em.getReference(hospitalizacionListHospitalizacionToAttach.getClass(), hospitalizacionListHospitalizacionToAttach.getIdHospitalizacion());
                attachedHospitalizacionList.add(hospitalizacionListHospitalizacionToAttach);
            }
            mascota.setHospitalizacionList(attachedHospitalizacionList);
            List<AgendaDetalle> attachedAgendaDetalleList = new ArrayList<AgendaDetalle>();
            for (AgendaDetalle agendaDetalleListAgendaDetalleToAttach : mascota.getAgendaDetalleList()) {
                agendaDetalleListAgendaDetalleToAttach = em.getReference(agendaDetalleListAgendaDetalleToAttach.getClass(), agendaDetalleListAgendaDetalleToAttach.getIdDetalle());
                attachedAgendaDetalleList.add(agendaDetalleListAgendaDetalleToAttach);
            }
            mascota.setAgendaDetalleList(attachedAgendaDetalleList);
            List<Anamnesis> attachedAnamnesisList = new ArrayList<Anamnesis>();
            for (Anamnesis anamnesisListAnamnesisToAttach : mascota.getAnamnesisList()) {
                anamnesisListAnamnesisToAttach = em.getReference(anamnesisListAnamnesisToAttach.getClass(), anamnesisListAnamnesisToAttach.getIdAnamnesis());
                attachedAnamnesisList.add(anamnesisListAnamnesisToAttach);
            }
            mascota.setAnamnesisList(attachedAnamnesisList);
            List<Patologias> attachedPatologiasList = new ArrayList<Patologias>();
            for (Patologias patologiasListPatologiasToAttach : mascota.getPatologiasList()) {
                patologiasListPatologiasToAttach = em.getReference(patologiasListPatologiasToAttach.getClass(), patologiasListPatologiasToAttach.getIdPatologia());
                attachedPatologiasList.add(patologiasListPatologiasToAttach);
            }
            mascota.setPatologiasList(attachedPatologiasList);
            em.persist(mascota);
            if (caracter != null) {
                caracter.getMascotaList().add(mascota);
                caracter = em.merge(caracter);
            }
            if (habitad != null) {
                habitad.getMascotaList().add(mascota);
                habitad = em.merge(habitad);
            }
            if (madre != null) {
                madre.getMascotaList().add(mascota);
                madre = em.merge(madre);
            }
            if (padre != null) {
                padre.getMascotaList().add(mascota);
                padre = em.merge(padre);
            }
            if (propietario != null) {
                propietario.getMascotaList().add(mascota);
                propietario = em.merge(propietario);
            }
            if (raza != null) {
                raza.getMascotaList().add(mascota);
                raza = em.merge(raza);
            }
            for (Examenes examenesListExamenes : mascota.getExamenesList()) {
                Mascota oldMascotaOfExamenesListExamenes = examenesListExamenes.getMascota();
                examenesListExamenes.setMascota(mascota);
                examenesListExamenes = em.merge(examenesListExamenes);
                if (oldMascotaOfExamenesListExamenes != null) {
                    oldMascotaOfExamenesListExamenes.getExamenesList().remove(examenesListExamenes);
                    oldMascotaOfExamenesListExamenes = em.merge(oldMascotaOfExamenesListExamenes);
                }
            }
            for (Historialvacunas historialvacunasListHistorialvacunas : mascota.getHistorialvacunasList()) {
                Mascota oldMascotaOfHistorialvacunasListHistorialvacunas = historialvacunasListHistorialvacunas.getMascota();
                historialvacunasListHistorialvacunas.setMascota(mascota);
                historialvacunasListHistorialvacunas = em.merge(historialvacunasListHistorialvacunas);
                if (oldMascotaOfHistorialvacunasListHistorialvacunas != null) {
                    oldMascotaOfHistorialvacunasListHistorialvacunas.getHistorialvacunasList().remove(historialvacunasListHistorialvacunas);
                    oldMascotaOfHistorialvacunasListHistorialvacunas = em.merge(oldMascotaOfHistorialvacunasListHistorialvacunas);
                }
            }
            for (Contraindicaciones contraindicacionesListContraindicaciones : mascota.getContraindicacionesList()) {
                Mascota oldMascotaOfContraindicacionesListContraindicaciones = contraindicacionesListContraindicaciones.getMascota();
                contraindicacionesListContraindicaciones.setMascota(mascota);
                contraindicacionesListContraindicaciones = em.merge(contraindicacionesListContraindicaciones);
                if (oldMascotaOfContraindicacionesListContraindicaciones != null) {
                    oldMascotaOfContraindicacionesListContraindicaciones.getContraindicacionesList().remove(contraindicacionesListContraindicaciones);
                    oldMascotaOfContraindicacionesListContraindicaciones = em.merge(oldMascotaOfContraindicacionesListContraindicaciones);
                }
            }
            for (Desparacitaciones desparacitacionesListDesparacitaciones : mascota.getDesparacitacionesList()) {
                Mascota oldMascotaOfDesparacitacionesListDesparacitaciones = desparacitacionesListDesparacitaciones.getMascota();
                desparacitacionesListDesparacitaciones.setMascota(mascota);
                desparacitacionesListDesparacitaciones = em.merge(desparacitacionesListDesparacitaciones);
                if (oldMascotaOfDesparacitacionesListDesparacitaciones != null) {
                    oldMascotaOfDesparacitacionesListDesparacitaciones.getDesparacitacionesList().remove(desparacitacionesListDesparacitaciones);
                    oldMascotaOfDesparacitacionesListDesparacitaciones = em.merge(oldMascotaOfDesparacitacionesListDesparacitaciones);
                }
            }
            for (Alergias alergiasListAlergias : mascota.getAlergiasList()) {
                Mascota oldMascotaOfAlergiasListAlergias = alergiasListAlergias.getMascota();
                alergiasListAlergias.setMascota(mascota);
                alergiasListAlergias = em.merge(alergiasListAlergias);
                if (oldMascotaOfAlergiasListAlergias != null) {
                    oldMascotaOfAlergiasListAlergias.getAlergiasList().remove(alergiasListAlergias);
                    oldMascotaOfAlergiasListAlergias = em.merge(oldMascotaOfAlergiasListAlergias);
                }
            }
            for (Mascota mascotaListMascota : mascota.getMascotaList()) {
                Mascota oldMadreOfMascotaListMascota = mascotaListMascota.getMadre();
                mascotaListMascota.setMadre(mascota);
                mascotaListMascota = em.merge(mascotaListMascota);
                if (oldMadreOfMascotaListMascota != null) {
                    oldMadreOfMascotaListMascota.getMascotaList().remove(mascotaListMascota);
                    oldMadreOfMascotaListMascota = em.merge(oldMadreOfMascotaListMascota);
                }
            }
            for (Mascota mascotaList1Mascota : mascota.getMascotaList1()) {
                Mascota oldPadreOfMascotaList1Mascota = mascotaList1Mascota.getPadre();
                mascotaList1Mascota.setPadre(mascota);
                mascotaList1Mascota = em.merge(mascotaList1Mascota);
                if (oldPadreOfMascotaList1Mascota != null) {
                    oldPadreOfMascotaList1Mascota.getMascotaList1().remove(mascotaList1Mascota);
                    oldPadreOfMascotaList1Mascota = em.merge(oldPadreOfMascotaList1Mascota);
                }
            }
            for (Farmacos farmacosListFarmacos : mascota.getFarmacosList()) {
                Mascota oldMascotaOfFarmacosListFarmacos = farmacosListFarmacos.getMascota();
                farmacosListFarmacos.setMascota(mascota);
                farmacosListFarmacos = em.merge(farmacosListFarmacos);
                if (oldMascotaOfFarmacosListFarmacos != null) {
                    oldMascotaOfFarmacosListFarmacos.getFarmacosList().remove(farmacosListFarmacos);
                    oldMascotaOfFarmacosListFarmacos = em.merge(oldMascotaOfFarmacosListFarmacos);
                }
            }
            for (Procedimientos procedimientosListProcedimientos : mascota.getProcedimientosList()) {
                Mascota oldMascotaOfProcedimientosListProcedimientos = procedimientosListProcedimientos.getMascota();
                procedimientosListProcedimientos.setMascota(mascota);
                procedimientosListProcedimientos = em.merge(procedimientosListProcedimientos);
                if (oldMascotaOfProcedimientosListProcedimientos != null) {
                    oldMascotaOfProcedimientosListProcedimientos.getProcedimientosList().remove(procedimientosListProcedimientos);
                    oldMascotaOfProcedimientosListProcedimientos = em.merge(oldMascotaOfProcedimientosListProcedimientos);
                }
            }
            for (Hospitalizacion hospitalizacionListHospitalizacion : mascota.getHospitalizacionList()) {
                Mascota oldMascotaOfHospitalizacionListHospitalizacion = hospitalizacionListHospitalizacion.getMascota();
                hospitalizacionListHospitalizacion.setMascota(mascota);
                hospitalizacionListHospitalizacion = em.merge(hospitalizacionListHospitalizacion);
                if (oldMascotaOfHospitalizacionListHospitalizacion != null) {
                    oldMascotaOfHospitalizacionListHospitalizacion.getHospitalizacionList().remove(hospitalizacionListHospitalizacion);
                    oldMascotaOfHospitalizacionListHospitalizacion = em.merge(oldMascotaOfHospitalizacionListHospitalizacion);
                }
            }
            for (AgendaDetalle agendaDetalleListAgendaDetalle : mascota.getAgendaDetalleList()) {
                Mascota oldMascotaOfAgendaDetalleListAgendaDetalle = agendaDetalleListAgendaDetalle.getMascota();
                agendaDetalleListAgendaDetalle.setMascota(mascota);
                agendaDetalleListAgendaDetalle = em.merge(agendaDetalleListAgendaDetalle);
                if (oldMascotaOfAgendaDetalleListAgendaDetalle != null) {
                    oldMascotaOfAgendaDetalleListAgendaDetalle.getAgendaDetalleList().remove(agendaDetalleListAgendaDetalle);
                    oldMascotaOfAgendaDetalleListAgendaDetalle = em.merge(oldMascotaOfAgendaDetalleListAgendaDetalle);
                }
            }
            for (Anamnesis anamnesisListAnamnesis : mascota.getAnamnesisList()) {
                Mascota oldMascotaOfAnamnesisListAnamnesis = anamnesisListAnamnesis.getMascota();
                anamnesisListAnamnesis.setMascota(mascota);
                anamnesisListAnamnesis = em.merge(anamnesisListAnamnesis);
                if (oldMascotaOfAnamnesisListAnamnesis != null) {
                    oldMascotaOfAnamnesisListAnamnesis.getAnamnesisList().remove(anamnesisListAnamnesis);
                    oldMascotaOfAnamnesisListAnamnesis = em.merge(oldMascotaOfAnamnesisListAnamnesis);
                }
            }
            for (Patologias patologiasListPatologias : mascota.getPatologiasList()) {
                Mascota oldMascotaOfPatologiasListPatologias = patologiasListPatologias.getMascota();
                patologiasListPatologias.setMascota(mascota);
                patologiasListPatologias = em.merge(patologiasListPatologias);
                if (oldMascotaOfPatologiasListPatologias != null) {
                    oldMascotaOfPatologiasListPatologias.getPatologiasList().remove(patologiasListPatologias);
                    oldMascotaOfPatologiasListPatologias = em.merge(oldMascotaOfPatologiasListPatologias);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMascota(mascota.getIdMascota()) != null) {
                throw new PreexistingEntityException("Mascota " + mascota + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Mascota mascota) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mascota persistentMascota = em.find(Mascota.class, mascota.getIdMascota());
            Caracter caracterOld = persistentMascota.getCaracter();
            Caracter caracterNew = mascota.getCaracter();
            Habitad habitadOld = persistentMascota.getHabitad();
            Habitad habitadNew = mascota.getHabitad();
            Mascota madreOld = persistentMascota.getMadre();
            Mascota madreNew = mascota.getMadre();
            Mascota padreOld = persistentMascota.getPadre();
            Mascota padreNew = mascota.getPadre();
            Propietario propietarioOld = persistentMascota.getPropietario();
            Propietario propietarioNew = mascota.getPropietario();
            Raza razaOld = persistentMascota.getRaza();
            Raza razaNew = mascota.getRaza();
            List<Examenes> examenesListOld = persistentMascota.getExamenesList();
            List<Examenes> examenesListNew = mascota.getExamenesList();
            List<Historialvacunas> historialvacunasListOld = persistentMascota.getHistorialvacunasList();
            List<Historialvacunas> historialvacunasListNew = mascota.getHistorialvacunasList();
            List<Contraindicaciones> contraindicacionesListOld = persistentMascota.getContraindicacionesList();
            List<Contraindicaciones> contraindicacionesListNew = mascota.getContraindicacionesList();
            List<Desparacitaciones> desparacitacionesListOld = persistentMascota.getDesparacitacionesList();
            List<Desparacitaciones> desparacitacionesListNew = mascota.getDesparacitacionesList();
            List<Alergias> alergiasListOld = persistentMascota.getAlergiasList();
            List<Alergias> alergiasListNew = mascota.getAlergiasList();
            List<Mascota> mascotaListOld = persistentMascota.getMascotaList();
            List<Mascota> mascotaListNew = mascota.getMascotaList();
            List<Mascota> mascotaList1Old = persistentMascota.getMascotaList1();
            List<Mascota> mascotaList1New = mascota.getMascotaList1();
            List<Farmacos> farmacosListOld = persistentMascota.getFarmacosList();
            List<Farmacos> farmacosListNew = mascota.getFarmacosList();
            List<Procedimientos> procedimientosListOld = persistentMascota.getProcedimientosList();
            List<Procedimientos> procedimientosListNew = mascota.getProcedimientosList();
            List<Hospitalizacion> hospitalizacionListOld = persistentMascota.getHospitalizacionList();
            List<Hospitalizacion> hospitalizacionListNew = mascota.getHospitalizacionList();
            List<AgendaDetalle> agendaDetalleListOld = persistentMascota.getAgendaDetalleList();
            List<AgendaDetalle> agendaDetalleListNew = mascota.getAgendaDetalleList();
            List<Anamnesis> anamnesisListOld = persistentMascota.getAnamnesisList();
            List<Anamnesis> anamnesisListNew = mascota.getAnamnesisList();
            List<Patologias> patologiasListOld = persistentMascota.getPatologiasList();
            List<Patologias> patologiasListNew = mascota.getPatologiasList();
            List<String> illegalOrphanMessages = null;
            for (Examenes examenesListOldExamenes : examenesListOld) {
                if (!examenesListNew.contains(examenesListOldExamenes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Examenes " + examenesListOldExamenes + " since its mascota field is not nullable.");
                }
            }
            for (Historialvacunas historialvacunasListOldHistorialvacunas : historialvacunasListOld) {
                if (!historialvacunasListNew.contains(historialvacunasListOldHistorialvacunas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Historialvacunas " + historialvacunasListOldHistorialvacunas + " since its mascota field is not nullable.");
                }
            }
            for (Contraindicaciones contraindicacionesListOldContraindicaciones : contraindicacionesListOld) {
                if (!contraindicacionesListNew.contains(contraindicacionesListOldContraindicaciones)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Contraindicaciones " + contraindicacionesListOldContraindicaciones + " since its mascota field is not nullable.");
                }
            }
            for (Desparacitaciones desparacitacionesListOldDesparacitaciones : desparacitacionesListOld) {
                if (!desparacitacionesListNew.contains(desparacitacionesListOldDesparacitaciones)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Desparacitaciones " + desparacitacionesListOldDesparacitaciones + " since its mascota field is not nullable.");
                }
            }
            for (Alergias alergiasListOldAlergias : alergiasListOld) {
                if (!alergiasListNew.contains(alergiasListOldAlergias)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Alergias " + alergiasListOldAlergias + " since its mascota field is not nullable.");
                }
            }
            for (Farmacos farmacosListOldFarmacos : farmacosListOld) {
                if (!farmacosListNew.contains(farmacosListOldFarmacos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Farmacos " + farmacosListOldFarmacos + " since its mascota field is not nullable.");
                }
            }
            for (Procedimientos procedimientosListOldProcedimientos : procedimientosListOld) {
                if (!procedimientosListNew.contains(procedimientosListOldProcedimientos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Procedimientos " + procedimientosListOldProcedimientos + " since its mascota field is not nullable.");
                }
            }
            for (Hospitalizacion hospitalizacionListOldHospitalizacion : hospitalizacionListOld) {
                if (!hospitalizacionListNew.contains(hospitalizacionListOldHospitalizacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Hospitalizacion " + hospitalizacionListOldHospitalizacion + " since its mascota field is not nullable.");
                }
            }
            for (AgendaDetalle agendaDetalleListOldAgendaDetalle : agendaDetalleListOld) {
                if (!agendaDetalleListNew.contains(agendaDetalleListOldAgendaDetalle)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AgendaDetalle " + agendaDetalleListOldAgendaDetalle + " since its mascota field is not nullable.");
                }
            }
            for (Anamnesis anamnesisListOldAnamnesis : anamnesisListOld) {
                if (!anamnesisListNew.contains(anamnesisListOldAnamnesis)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Anamnesis " + anamnesisListOldAnamnesis + " since its mascota field is not nullable.");
                }
            }
            for (Patologias patologiasListOldPatologias : patologiasListOld) {
                if (!patologiasListNew.contains(patologiasListOldPatologias)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Patologias " + patologiasListOldPatologias + " since its mascota field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (caracterNew != null) {
                caracterNew = em.getReference(caracterNew.getClass(), caracterNew.getIdCaracter());
                mascota.setCaracter(caracterNew);
            }
            if (habitadNew != null) {
                habitadNew = em.getReference(habitadNew.getClass(), habitadNew.getIdHabitad());
                mascota.setHabitad(habitadNew);
            }
            if (madreNew != null) {
                madreNew = em.getReference(madreNew.getClass(), madreNew.getIdMascota());
                mascota.setMadre(madreNew);
            }
            if (padreNew != null) {
                padreNew = em.getReference(padreNew.getClass(), padreNew.getIdMascota());
                mascota.setPadre(padreNew);
            }
            if (propietarioNew != null) {
                propietarioNew = em.getReference(propietarioNew.getClass(), propietarioNew.getIdPropietario());
                mascota.setPropietario(propietarioNew);
            }
            if (razaNew != null) {
                razaNew = em.getReference(razaNew.getClass(), razaNew.getIdRaza());
                mascota.setRaza(razaNew);
            }
            List<Examenes> attachedExamenesListNew = new ArrayList<Examenes>();
            for (Examenes examenesListNewExamenesToAttach : examenesListNew) {
                examenesListNewExamenesToAttach = em.getReference(examenesListNewExamenesToAttach.getClass(), examenesListNewExamenesToAttach.getIdExamen());
                attachedExamenesListNew.add(examenesListNewExamenesToAttach);
            }
            examenesListNew = attachedExamenesListNew;
            mascota.setExamenesList(examenesListNew);
            List<Historialvacunas> attachedHistorialvacunasListNew = new ArrayList<Historialvacunas>();
            for (Historialvacunas historialvacunasListNewHistorialvacunasToAttach : historialvacunasListNew) {
                historialvacunasListNewHistorialvacunasToAttach = em.getReference(historialvacunasListNewHistorialvacunasToAttach.getClass(), historialvacunasListNewHistorialvacunasToAttach.getIdEvento());
                attachedHistorialvacunasListNew.add(historialvacunasListNewHistorialvacunasToAttach);
            }
            historialvacunasListNew = attachedHistorialvacunasListNew;
            mascota.setHistorialvacunasList(historialvacunasListNew);
            List<Contraindicaciones> attachedContraindicacionesListNew = new ArrayList<Contraindicaciones>();
            for (Contraindicaciones contraindicacionesListNewContraindicacionesToAttach : contraindicacionesListNew) {
                contraindicacionesListNewContraindicacionesToAttach = em.getReference(contraindicacionesListNewContraindicacionesToAttach.getClass(), contraindicacionesListNewContraindicacionesToAttach.getIdContraindicacion());
                attachedContraindicacionesListNew.add(contraindicacionesListNewContraindicacionesToAttach);
            }
            contraindicacionesListNew = attachedContraindicacionesListNew;
            mascota.setContraindicacionesList(contraindicacionesListNew);
            List<Desparacitaciones> attachedDesparacitacionesListNew = new ArrayList<Desparacitaciones>();
            for (Desparacitaciones desparacitacionesListNewDesparacitacionesToAttach : desparacitacionesListNew) {
                desparacitacionesListNewDesparacitacionesToAttach = em.getReference(desparacitacionesListNewDesparacitacionesToAttach.getClass(), desparacitacionesListNewDesparacitacionesToAttach.getIdDesparacitacion());
                attachedDesparacitacionesListNew.add(desparacitacionesListNewDesparacitacionesToAttach);
            }
            desparacitacionesListNew = attachedDesparacitacionesListNew;
            mascota.setDesparacitacionesList(desparacitacionesListNew);
            List<Alergias> attachedAlergiasListNew = new ArrayList<Alergias>();
            for (Alergias alergiasListNewAlergiasToAttach : alergiasListNew) {
                alergiasListNewAlergiasToAttach = em.getReference(alergiasListNewAlergiasToAttach.getClass(), alergiasListNewAlergiasToAttach.getIdAlergia());
                attachedAlergiasListNew.add(alergiasListNewAlergiasToAttach);
            }
            alergiasListNew = attachedAlergiasListNew;
            mascota.setAlergiasList(alergiasListNew);
            List<Mascota> attachedMascotaListNew = new ArrayList<Mascota>();
            for (Mascota mascotaListNewMascotaToAttach : mascotaListNew) {
                mascotaListNewMascotaToAttach = em.getReference(mascotaListNewMascotaToAttach.getClass(), mascotaListNewMascotaToAttach.getIdMascota());
                attachedMascotaListNew.add(mascotaListNewMascotaToAttach);
            }
            mascotaListNew = attachedMascotaListNew;
            mascota.setMascotaList(mascotaListNew);
            List<Mascota> attachedMascotaList1New = new ArrayList<Mascota>();
            for (Mascota mascotaList1NewMascotaToAttach : mascotaList1New) {
                mascotaList1NewMascotaToAttach = em.getReference(mascotaList1NewMascotaToAttach.getClass(), mascotaList1NewMascotaToAttach.getIdMascota());
                attachedMascotaList1New.add(mascotaList1NewMascotaToAttach);
            }
            mascotaList1New = attachedMascotaList1New;
            mascota.setMascotaList1(mascotaList1New);
            List<Farmacos> attachedFarmacosListNew = new ArrayList<Farmacos>();
            for (Farmacos farmacosListNewFarmacosToAttach : farmacosListNew) {
                farmacosListNewFarmacosToAttach = em.getReference(farmacosListNewFarmacosToAttach.getClass(), farmacosListNewFarmacosToAttach.getIdFarmaco());
                attachedFarmacosListNew.add(farmacosListNewFarmacosToAttach);
            }
            farmacosListNew = attachedFarmacosListNew;
            mascota.setFarmacosList(farmacosListNew);
            List<Procedimientos> attachedProcedimientosListNew = new ArrayList<Procedimientos>();
            for (Procedimientos procedimientosListNewProcedimientosToAttach : procedimientosListNew) {
                procedimientosListNewProcedimientosToAttach = em.getReference(procedimientosListNewProcedimientosToAttach.getClass(), procedimientosListNewProcedimientosToAttach.getIdProcedimiento());
                attachedProcedimientosListNew.add(procedimientosListNewProcedimientosToAttach);
            }
            procedimientosListNew = attachedProcedimientosListNew;
            mascota.setProcedimientosList(procedimientosListNew);
            List<Hospitalizacion> attachedHospitalizacionListNew = new ArrayList<Hospitalizacion>();
            for (Hospitalizacion hospitalizacionListNewHospitalizacionToAttach : hospitalizacionListNew) {
                hospitalizacionListNewHospitalizacionToAttach = em.getReference(hospitalizacionListNewHospitalizacionToAttach.getClass(), hospitalizacionListNewHospitalizacionToAttach.getIdHospitalizacion());
                attachedHospitalizacionListNew.add(hospitalizacionListNewHospitalizacionToAttach);
            }
            hospitalizacionListNew = attachedHospitalizacionListNew;
            mascota.setHospitalizacionList(hospitalizacionListNew);
            List<AgendaDetalle> attachedAgendaDetalleListNew = new ArrayList<AgendaDetalle>();
            for (AgendaDetalle agendaDetalleListNewAgendaDetalleToAttach : agendaDetalleListNew) {
                agendaDetalleListNewAgendaDetalleToAttach = em.getReference(agendaDetalleListNewAgendaDetalleToAttach.getClass(), agendaDetalleListNewAgendaDetalleToAttach.getIdDetalle());
                attachedAgendaDetalleListNew.add(agendaDetalleListNewAgendaDetalleToAttach);
            }
            agendaDetalleListNew = attachedAgendaDetalleListNew;
            mascota.setAgendaDetalleList(agendaDetalleListNew);
            List<Anamnesis> attachedAnamnesisListNew = new ArrayList<Anamnesis>();
            for (Anamnesis anamnesisListNewAnamnesisToAttach : anamnesisListNew) {
                anamnesisListNewAnamnesisToAttach = em.getReference(anamnesisListNewAnamnesisToAttach.getClass(), anamnesisListNewAnamnesisToAttach.getIdAnamnesis());
                attachedAnamnesisListNew.add(anamnesisListNewAnamnesisToAttach);
            }
            anamnesisListNew = attachedAnamnesisListNew;
            mascota.setAnamnesisList(anamnesisListNew);
            List<Patologias> attachedPatologiasListNew = new ArrayList<Patologias>();
            for (Patologias patologiasListNewPatologiasToAttach : patologiasListNew) {
                patologiasListNewPatologiasToAttach = em.getReference(patologiasListNewPatologiasToAttach.getClass(), patologiasListNewPatologiasToAttach.getIdPatologia());
                attachedPatologiasListNew.add(patologiasListNewPatologiasToAttach);
            }
            patologiasListNew = attachedPatologiasListNew;
            mascota.setPatologiasList(patologiasListNew);
            mascota = em.merge(mascota);
            if (caracterOld != null && !caracterOld.equals(caracterNew)) {
                caracterOld.getMascotaList().remove(mascota);
                caracterOld = em.merge(caracterOld);
            }
            if (caracterNew != null && !caracterNew.equals(caracterOld)) {
                caracterNew.getMascotaList().add(mascota);
                caracterNew = em.merge(caracterNew);
            }
            if (habitadOld != null && !habitadOld.equals(habitadNew)) {
                habitadOld.getMascotaList().remove(mascota);
                habitadOld = em.merge(habitadOld);
            }
            if (habitadNew != null && !habitadNew.equals(habitadOld)) {
                habitadNew.getMascotaList().add(mascota);
                habitadNew = em.merge(habitadNew);
            }
            if (madreOld != null && !madreOld.equals(madreNew)) {
                madreOld.getMascotaList().remove(mascota);
                madreOld = em.merge(madreOld);
            }
            if (madreNew != null && !madreNew.equals(madreOld)) {
                madreNew.getMascotaList().add(mascota);
                madreNew = em.merge(madreNew);
            }
            if (padreOld != null && !padreOld.equals(padreNew)) {
                padreOld.getMascotaList().remove(mascota);
                padreOld = em.merge(padreOld);
            }
            if (padreNew != null && !padreNew.equals(padreOld)) {
                padreNew.getMascotaList().add(mascota);
                padreNew = em.merge(padreNew);
            }
            if (propietarioOld != null && !propietarioOld.equals(propietarioNew)) {
                propietarioOld.getMascotaList().remove(mascota);
                propietarioOld = em.merge(propietarioOld);
            }
            if (propietarioNew != null && !propietarioNew.equals(propietarioOld)) {
                propietarioNew.getMascotaList().add(mascota);
                propietarioNew = em.merge(propietarioNew);
            }
            if (razaOld != null && !razaOld.equals(razaNew)) {
                razaOld.getMascotaList().remove(mascota);
                razaOld = em.merge(razaOld);
            }
            if (razaNew != null && !razaNew.equals(razaOld)) {
                razaNew.getMascotaList().add(mascota);
                razaNew = em.merge(razaNew);
            }
            for (Examenes examenesListNewExamenes : examenesListNew) {
                if (!examenesListOld.contains(examenesListNewExamenes)) {
                    Mascota oldMascotaOfExamenesListNewExamenes = examenesListNewExamenes.getMascota();
                    examenesListNewExamenes.setMascota(mascota);
                    examenesListNewExamenes = em.merge(examenesListNewExamenes);
                    if (oldMascotaOfExamenesListNewExamenes != null && !oldMascotaOfExamenesListNewExamenes.equals(mascota)) {
                        oldMascotaOfExamenesListNewExamenes.getExamenesList().remove(examenesListNewExamenes);
                        oldMascotaOfExamenesListNewExamenes = em.merge(oldMascotaOfExamenesListNewExamenes);
                    }
                }
            }
            for (Historialvacunas historialvacunasListNewHistorialvacunas : historialvacunasListNew) {
                if (!historialvacunasListOld.contains(historialvacunasListNewHistorialvacunas)) {
                    Mascota oldMascotaOfHistorialvacunasListNewHistorialvacunas = historialvacunasListNewHistorialvacunas.getMascota();
                    historialvacunasListNewHistorialvacunas.setMascota(mascota);
                    historialvacunasListNewHistorialvacunas = em.merge(historialvacunasListNewHistorialvacunas);
                    if (oldMascotaOfHistorialvacunasListNewHistorialvacunas != null && !oldMascotaOfHistorialvacunasListNewHistorialvacunas.equals(mascota)) {
                        oldMascotaOfHistorialvacunasListNewHistorialvacunas.getHistorialvacunasList().remove(historialvacunasListNewHistorialvacunas);
                        oldMascotaOfHistorialvacunasListNewHistorialvacunas = em.merge(oldMascotaOfHistorialvacunasListNewHistorialvacunas);
                    }
                }
            }
            for (Contraindicaciones contraindicacionesListNewContraindicaciones : contraindicacionesListNew) {
                if (!contraindicacionesListOld.contains(contraindicacionesListNewContraindicaciones)) {
                    Mascota oldMascotaOfContraindicacionesListNewContraindicaciones = contraindicacionesListNewContraindicaciones.getMascota();
                    contraindicacionesListNewContraindicaciones.setMascota(mascota);
                    contraindicacionesListNewContraindicaciones = em.merge(contraindicacionesListNewContraindicaciones);
                    if (oldMascotaOfContraindicacionesListNewContraindicaciones != null && !oldMascotaOfContraindicacionesListNewContraindicaciones.equals(mascota)) {
                        oldMascotaOfContraindicacionesListNewContraindicaciones.getContraindicacionesList().remove(contraindicacionesListNewContraindicaciones);
                        oldMascotaOfContraindicacionesListNewContraindicaciones = em.merge(oldMascotaOfContraindicacionesListNewContraindicaciones);
                    }
                }
            }
            for (Desparacitaciones desparacitacionesListNewDesparacitaciones : desparacitacionesListNew) {
                if (!desparacitacionesListOld.contains(desparacitacionesListNewDesparacitaciones)) {
                    Mascota oldMascotaOfDesparacitacionesListNewDesparacitaciones = desparacitacionesListNewDesparacitaciones.getMascota();
                    desparacitacionesListNewDesparacitaciones.setMascota(mascota);
                    desparacitacionesListNewDesparacitaciones = em.merge(desparacitacionesListNewDesparacitaciones);
                    if (oldMascotaOfDesparacitacionesListNewDesparacitaciones != null && !oldMascotaOfDesparacitacionesListNewDesparacitaciones.equals(mascota)) {
                        oldMascotaOfDesparacitacionesListNewDesparacitaciones.getDesparacitacionesList().remove(desparacitacionesListNewDesparacitaciones);
                        oldMascotaOfDesparacitacionesListNewDesparacitaciones = em.merge(oldMascotaOfDesparacitacionesListNewDesparacitaciones);
                    }
                }
            }
            for (Alergias alergiasListNewAlergias : alergiasListNew) {
                if (!alergiasListOld.contains(alergiasListNewAlergias)) {
                    Mascota oldMascotaOfAlergiasListNewAlergias = alergiasListNewAlergias.getMascota();
                    alergiasListNewAlergias.setMascota(mascota);
                    alergiasListNewAlergias = em.merge(alergiasListNewAlergias);
                    if (oldMascotaOfAlergiasListNewAlergias != null && !oldMascotaOfAlergiasListNewAlergias.equals(mascota)) {
                        oldMascotaOfAlergiasListNewAlergias.getAlergiasList().remove(alergiasListNewAlergias);
                        oldMascotaOfAlergiasListNewAlergias = em.merge(oldMascotaOfAlergiasListNewAlergias);
                    }
                }
            }
            for (Mascota mascotaListOldMascota : mascotaListOld) {
                if (!mascotaListNew.contains(mascotaListOldMascota)) {
                    mascotaListOldMascota.setMadre(null);
                    mascotaListOldMascota = em.merge(mascotaListOldMascota);
                }
            }
            for (Mascota mascotaListNewMascota : mascotaListNew) {
                if (!mascotaListOld.contains(mascotaListNewMascota)) {
                    Mascota oldMadreOfMascotaListNewMascota = mascotaListNewMascota.getMadre();
                    mascotaListNewMascota.setMadre(mascota);
                    mascotaListNewMascota = em.merge(mascotaListNewMascota);
                    if (oldMadreOfMascotaListNewMascota != null && !oldMadreOfMascotaListNewMascota.equals(mascota)) {
                        oldMadreOfMascotaListNewMascota.getMascotaList().remove(mascotaListNewMascota);
                        oldMadreOfMascotaListNewMascota = em.merge(oldMadreOfMascotaListNewMascota);
                    }
                }
            }
            for (Mascota mascotaList1OldMascota : mascotaList1Old) {
                if (!mascotaList1New.contains(mascotaList1OldMascota)) {
                    mascotaList1OldMascota.setPadre(null);
                    mascotaList1OldMascota = em.merge(mascotaList1OldMascota);
                }
            }
            for (Mascota mascotaList1NewMascota : mascotaList1New) {
                if (!mascotaList1Old.contains(mascotaList1NewMascota)) {
                    Mascota oldPadreOfMascotaList1NewMascota = mascotaList1NewMascota.getPadre();
                    mascotaList1NewMascota.setPadre(mascota);
                    mascotaList1NewMascota = em.merge(mascotaList1NewMascota);
                    if (oldPadreOfMascotaList1NewMascota != null && !oldPadreOfMascotaList1NewMascota.equals(mascota)) {
                        oldPadreOfMascotaList1NewMascota.getMascotaList1().remove(mascotaList1NewMascota);
                        oldPadreOfMascotaList1NewMascota = em.merge(oldPadreOfMascotaList1NewMascota);
                    }
                }
            }
            for (Farmacos farmacosListNewFarmacos : farmacosListNew) {
                if (!farmacosListOld.contains(farmacosListNewFarmacos)) {
                    Mascota oldMascotaOfFarmacosListNewFarmacos = farmacosListNewFarmacos.getMascota();
                    farmacosListNewFarmacos.setMascota(mascota);
                    farmacosListNewFarmacos = em.merge(farmacosListNewFarmacos);
                    if (oldMascotaOfFarmacosListNewFarmacos != null && !oldMascotaOfFarmacosListNewFarmacos.equals(mascota)) {
                        oldMascotaOfFarmacosListNewFarmacos.getFarmacosList().remove(farmacosListNewFarmacos);
                        oldMascotaOfFarmacosListNewFarmacos = em.merge(oldMascotaOfFarmacosListNewFarmacos);
                    }
                }
            }
            for (Procedimientos procedimientosListNewProcedimientos : procedimientosListNew) {
                if (!procedimientosListOld.contains(procedimientosListNewProcedimientos)) {
                    Mascota oldMascotaOfProcedimientosListNewProcedimientos = procedimientosListNewProcedimientos.getMascota();
                    procedimientosListNewProcedimientos.setMascota(mascota);
                    procedimientosListNewProcedimientos = em.merge(procedimientosListNewProcedimientos);
                    if (oldMascotaOfProcedimientosListNewProcedimientos != null && !oldMascotaOfProcedimientosListNewProcedimientos.equals(mascota)) {
                        oldMascotaOfProcedimientosListNewProcedimientos.getProcedimientosList().remove(procedimientosListNewProcedimientos);
                        oldMascotaOfProcedimientosListNewProcedimientos = em.merge(oldMascotaOfProcedimientosListNewProcedimientos);
                    }
                }
            }
            for (Hospitalizacion hospitalizacionListNewHospitalizacion : hospitalizacionListNew) {
                if (!hospitalizacionListOld.contains(hospitalizacionListNewHospitalizacion)) {
                    Mascota oldMascotaOfHospitalizacionListNewHospitalizacion = hospitalizacionListNewHospitalizacion.getMascota();
                    hospitalizacionListNewHospitalizacion.setMascota(mascota);
                    hospitalizacionListNewHospitalizacion = em.merge(hospitalizacionListNewHospitalizacion);
                    if (oldMascotaOfHospitalizacionListNewHospitalizacion != null && !oldMascotaOfHospitalizacionListNewHospitalizacion.equals(mascota)) {
                        oldMascotaOfHospitalizacionListNewHospitalizacion.getHospitalizacionList().remove(hospitalizacionListNewHospitalizacion);
                        oldMascotaOfHospitalizacionListNewHospitalizacion = em.merge(oldMascotaOfHospitalizacionListNewHospitalizacion);
                    }
                }
            }
            for (AgendaDetalle agendaDetalleListNewAgendaDetalle : agendaDetalleListNew) {
                if (!agendaDetalleListOld.contains(agendaDetalleListNewAgendaDetalle)) {
                    Mascota oldMascotaOfAgendaDetalleListNewAgendaDetalle = agendaDetalleListNewAgendaDetalle.getMascota();
                    agendaDetalleListNewAgendaDetalle.setMascota(mascota);
                    agendaDetalleListNewAgendaDetalle = em.merge(agendaDetalleListNewAgendaDetalle);
                    if (oldMascotaOfAgendaDetalleListNewAgendaDetalle != null && !oldMascotaOfAgendaDetalleListNewAgendaDetalle.equals(mascota)) {
                        oldMascotaOfAgendaDetalleListNewAgendaDetalle.getAgendaDetalleList().remove(agendaDetalleListNewAgendaDetalle);
                        oldMascotaOfAgendaDetalleListNewAgendaDetalle = em.merge(oldMascotaOfAgendaDetalleListNewAgendaDetalle);
                    }
                }
            }
            for (Anamnesis anamnesisListNewAnamnesis : anamnesisListNew) {
                if (!anamnesisListOld.contains(anamnesisListNewAnamnesis)) {
                    Mascota oldMascotaOfAnamnesisListNewAnamnesis = anamnesisListNewAnamnesis.getMascota();
                    anamnesisListNewAnamnesis.setMascota(mascota);
                    anamnesisListNewAnamnesis = em.merge(anamnesisListNewAnamnesis);
                    if (oldMascotaOfAnamnesisListNewAnamnesis != null && !oldMascotaOfAnamnesisListNewAnamnesis.equals(mascota)) {
                        oldMascotaOfAnamnesisListNewAnamnesis.getAnamnesisList().remove(anamnesisListNewAnamnesis);
                        oldMascotaOfAnamnesisListNewAnamnesis = em.merge(oldMascotaOfAnamnesisListNewAnamnesis);
                    }
                }
            }
            for (Patologias patologiasListNewPatologias : patologiasListNew) {
                if (!patologiasListOld.contains(patologiasListNewPatologias)) {
                    Mascota oldMascotaOfPatologiasListNewPatologias = patologiasListNewPatologias.getMascota();
                    patologiasListNewPatologias.setMascota(mascota);
                    patologiasListNewPatologias = em.merge(patologiasListNewPatologias);
                    if (oldMascotaOfPatologiasListNewPatologias != null && !oldMascotaOfPatologiasListNewPatologias.equals(mascota)) {
                        oldMascotaOfPatologiasListNewPatologias.getPatologiasList().remove(patologiasListNewPatologias);
                        oldMascotaOfPatologiasListNewPatologias = em.merge(oldMascotaOfPatologiasListNewPatologias);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = mascota.getIdMascota();
                if (findMascota(id) == null) {
                    throw new NonexistentEntityException("The mascota with id " + id + " no longer exists.");
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
            Mascota mascota;
            try {
                mascota = em.getReference(Mascota.class, id);
                mascota.getIdMascota();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mascota with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Examenes> examenesListOrphanCheck = mascota.getExamenesList();
            for (Examenes examenesListOrphanCheckExamenes : examenesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Mascota (" + mascota + ") cannot be destroyed since the Examenes " + examenesListOrphanCheckExamenes + " in its examenesList field has a non-nullable mascota field.");
            }
            List<Historialvacunas> historialvacunasListOrphanCheck = mascota.getHistorialvacunasList();
            for (Historialvacunas historialvacunasListOrphanCheckHistorialvacunas : historialvacunasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Mascota (" + mascota + ") cannot be destroyed since the Historialvacunas " + historialvacunasListOrphanCheckHistorialvacunas + " in its historialvacunasList field has a non-nullable mascota field.");
            }
            List<Contraindicaciones> contraindicacionesListOrphanCheck = mascota.getContraindicacionesList();
            for (Contraindicaciones contraindicacionesListOrphanCheckContraindicaciones : contraindicacionesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Mascota (" + mascota + ") cannot be destroyed since the Contraindicaciones " + contraindicacionesListOrphanCheckContraindicaciones + " in its contraindicacionesList field has a non-nullable mascota field.");
            }
            List<Desparacitaciones> desparacitacionesListOrphanCheck = mascota.getDesparacitacionesList();
            for (Desparacitaciones desparacitacionesListOrphanCheckDesparacitaciones : desparacitacionesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Mascota (" + mascota + ") cannot be destroyed since the Desparacitaciones " + desparacitacionesListOrphanCheckDesparacitaciones + " in its desparacitacionesList field has a non-nullable mascota field.");
            }
            List<Alergias> alergiasListOrphanCheck = mascota.getAlergiasList();
            for (Alergias alergiasListOrphanCheckAlergias : alergiasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Mascota (" + mascota + ") cannot be destroyed since the Alergias " + alergiasListOrphanCheckAlergias + " in its alergiasList field has a non-nullable mascota field.");
            }
            List<Farmacos> farmacosListOrphanCheck = mascota.getFarmacosList();
            for (Farmacos farmacosListOrphanCheckFarmacos : farmacosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Mascota (" + mascota + ") cannot be destroyed since the Farmacos " + farmacosListOrphanCheckFarmacos + " in its farmacosList field has a non-nullable mascota field.");
            }
            List<Procedimientos> procedimientosListOrphanCheck = mascota.getProcedimientosList();
            for (Procedimientos procedimientosListOrphanCheckProcedimientos : procedimientosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Mascota (" + mascota + ") cannot be destroyed since the Procedimientos " + procedimientosListOrphanCheckProcedimientos + " in its procedimientosList field has a non-nullable mascota field.");
            }
            List<Hospitalizacion> hospitalizacionListOrphanCheck = mascota.getHospitalizacionList();
            for (Hospitalizacion hospitalizacionListOrphanCheckHospitalizacion : hospitalizacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Mascota (" + mascota + ") cannot be destroyed since the Hospitalizacion " + hospitalizacionListOrphanCheckHospitalizacion + " in its hospitalizacionList field has a non-nullable mascota field.");
            }
            List<AgendaDetalle> agendaDetalleListOrphanCheck = mascota.getAgendaDetalleList();
            for (AgendaDetalle agendaDetalleListOrphanCheckAgendaDetalle : agendaDetalleListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Mascota (" + mascota + ") cannot be destroyed since the AgendaDetalle " + agendaDetalleListOrphanCheckAgendaDetalle + " in its agendaDetalleList field has a non-nullable mascota field.");
            }
            List<Anamnesis> anamnesisListOrphanCheck = mascota.getAnamnesisList();
            for (Anamnesis anamnesisListOrphanCheckAnamnesis : anamnesisListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Mascota (" + mascota + ") cannot be destroyed since the Anamnesis " + anamnesisListOrphanCheckAnamnesis + " in its anamnesisList field has a non-nullable mascota field.");
            }
            List<Patologias> patologiasListOrphanCheck = mascota.getPatologiasList();
            for (Patologias patologiasListOrphanCheckPatologias : patologiasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Mascota (" + mascota + ") cannot be destroyed since the Patologias " + patologiasListOrphanCheckPatologias + " in its patologiasList field has a non-nullable mascota field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Caracter caracter = mascota.getCaracter();
            if (caracter != null) {
                caracter.getMascotaList().remove(mascota);
                caracter = em.merge(caracter);
            }
            Habitad habitad = mascota.getHabitad();
            if (habitad != null) {
                habitad.getMascotaList().remove(mascota);
                habitad = em.merge(habitad);
            }
            Mascota madre = mascota.getMadre();
            if (madre != null) {
                madre.getMascotaList().remove(mascota);
                madre = em.merge(madre);
            }
            Mascota padre = mascota.getPadre();
            if (padre != null) {
                padre.getMascotaList().remove(mascota);
                padre = em.merge(padre);
            }
            Propietario propietario = mascota.getPropietario();
            if (propietario != null) {
                propietario.getMascotaList().remove(mascota);
                propietario = em.merge(propietario);
            }
            Raza raza = mascota.getRaza();
            if (raza != null) {
                raza.getMascotaList().remove(mascota);
                raza = em.merge(raza);
            }
            List<Mascota> mascotaList = mascota.getMascotaList();
            for (Mascota mascotaListMascota : mascotaList) {
                mascotaListMascota.setMadre(null);
                mascotaListMascota = em.merge(mascotaListMascota);
            }
            List<Mascota> mascotaList1 = mascota.getMascotaList1();
            for (Mascota mascotaList1Mascota : mascotaList1) {
                mascotaList1Mascota.setPadre(null);
                mascotaList1Mascota = em.merge(mascotaList1Mascota);
            }
            em.remove(mascota);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Mascota> findMascotaEntities() {
        return findMascotaEntities(true, -1, -1);
    }

    public List<Mascota> findMascotaEntities(int maxResults, int firstResult) {
        return findMascotaEntities(false, maxResults, firstResult);
    }

    private List<Mascota> findMascotaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Mascota.class));
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

    public Mascota findMascota(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Mascota.class, id);
        } finally {
            em.close();
        }
    }

    public int getMascotaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Mascota> rt = cq.from(Mascota.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Integer ultimo() {
        try {
            Query consulta = getEntityManager().createNamedQuery("Mascota.findAllDesc");
            consulta.setMaxResults(1);
            return ((Mascota)consulta.getSingleResult()).getIdMascota()+1;
        } catch (Exception e) {
            return 1;
        }
    }
    
    /*public List<Mascota> mascotasCumpleanos() {
        try {
            Query consulta = getEntityManager().createNamedQuery("");
        } catch (Exception e) {
            return null;
        }
    }*/
    
    public boolean editar(Mascota ma) {
        try {
            getEntityManager().getTransaction().begin();
            getEntityManager().merge(ma);
            getEntityManager().getTransaction().commit();
            getEntityManager().close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public List<Mascota> buscarPorPropietario(Propietario pro) {
        try {
            Query consulta = getEntityManager().createNamedQuery("Mascota.findByPropietario");
            consulta.setParameter("propietario", pro);
            return consulta.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
