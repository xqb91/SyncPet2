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
import cl.starlabs.modelo.Mascota;
import cl.starlabs.modelo.Veterinario;
import cl.starlabs.modelo.Examenes;
import java.util.ArrayList;
import java.util.List;
import cl.starlabs.modelo.Historialvacunas;
import cl.starlabs.modelo.Contraindicaciones;
import cl.starlabs.modelo.Desparacitaciones;
import cl.starlabs.modelo.Farmacos;
import cl.starlabs.modelo.Procedimientos;
import cl.starlabs.modelo.Anamnesis;
import cl.starlabs.modelo.Hospitalizacion;
import cl.starlabs.modelo.Patologias;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Victor Manuel Araya
 */
public class HospitalizacionJpaController implements Serializable {

    public HospitalizacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Hospitalizacion hospitalizacion) throws PreexistingEntityException, Exception {
        if (hospitalizacion.getExamenesList() == null) {
            hospitalizacion.setExamenesList(new ArrayList<Examenes>());
        }
        if (hospitalizacion.getHistorialvacunasList() == null) {
            hospitalizacion.setHistorialvacunasList(new ArrayList<Historialvacunas>());
        }
        if (hospitalizacion.getContraindicacionesList() == null) {
            hospitalizacion.setContraindicacionesList(new ArrayList<Contraindicaciones>());
        }
        if (hospitalizacion.getDesparacitacionesList() == null) {
            hospitalizacion.setDesparacitacionesList(new ArrayList<Desparacitaciones>());
        }
        if (hospitalizacion.getFarmacosList() == null) {
            hospitalizacion.setFarmacosList(new ArrayList<Farmacos>());
        }
        if (hospitalizacion.getProcedimientosList() == null) {
            hospitalizacion.setProcedimientosList(new ArrayList<Procedimientos>());
        }
        if (hospitalizacion.getAnamnesisList() == null) {
            hospitalizacion.setAnamnesisList(new ArrayList<Anamnesis>());
        }
        if (hospitalizacion.getPatologiasList() == null) {
            hospitalizacion.setPatologiasList(new ArrayList<Patologias>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mascota mascota = hospitalizacion.getMascota();
            if (mascota != null) {
                mascota = em.getReference(mascota.getClass(), mascota.getIdMascota());
                hospitalizacion.setMascota(mascota);
            }
            Veterinario veterinario = hospitalizacion.getVeterinario();
            if (veterinario != null) {
                veterinario = em.getReference(veterinario.getClass(), veterinario.getIdVeterinario());
                hospitalizacion.setVeterinario(veterinario);
            }
            List<Examenes> attachedExamenesList = new ArrayList<Examenes>();
            for (Examenes examenesListExamenesToAttach : hospitalizacion.getExamenesList()) {
                examenesListExamenesToAttach = em.getReference(examenesListExamenesToAttach.getClass(), examenesListExamenesToAttach.getIdExamen());
                attachedExamenesList.add(examenesListExamenesToAttach);
            }
            hospitalizacion.setExamenesList(attachedExamenesList);
            List<Historialvacunas> attachedHistorialvacunasList = new ArrayList<Historialvacunas>();
            for (Historialvacunas historialvacunasListHistorialvacunasToAttach : hospitalizacion.getHistorialvacunasList()) {
                historialvacunasListHistorialvacunasToAttach = em.getReference(historialvacunasListHistorialvacunasToAttach.getClass(), historialvacunasListHistorialvacunasToAttach.getIdEvento());
                attachedHistorialvacunasList.add(historialvacunasListHistorialvacunasToAttach);
            }
            hospitalizacion.setHistorialvacunasList(attachedHistorialvacunasList);
            List<Contraindicaciones> attachedContraindicacionesList = new ArrayList<Contraindicaciones>();
            for (Contraindicaciones contraindicacionesListContraindicacionesToAttach : hospitalizacion.getContraindicacionesList()) {
                contraindicacionesListContraindicacionesToAttach = em.getReference(contraindicacionesListContraindicacionesToAttach.getClass(), contraindicacionesListContraindicacionesToAttach.getIdContraindicacion());
                attachedContraindicacionesList.add(contraindicacionesListContraindicacionesToAttach);
            }
            hospitalizacion.setContraindicacionesList(attachedContraindicacionesList);
            List<Desparacitaciones> attachedDesparacitacionesList = new ArrayList<Desparacitaciones>();
            for (Desparacitaciones desparacitacionesListDesparacitacionesToAttach : hospitalizacion.getDesparacitacionesList()) {
                desparacitacionesListDesparacitacionesToAttach = em.getReference(desparacitacionesListDesparacitacionesToAttach.getClass(), desparacitacionesListDesparacitacionesToAttach.getIdDesparacitacion());
                attachedDesparacitacionesList.add(desparacitacionesListDesparacitacionesToAttach);
            }
            hospitalizacion.setDesparacitacionesList(attachedDesparacitacionesList);
            List<Farmacos> attachedFarmacosList = new ArrayList<Farmacos>();
            for (Farmacos farmacosListFarmacosToAttach : hospitalizacion.getFarmacosList()) {
                farmacosListFarmacosToAttach = em.getReference(farmacosListFarmacosToAttach.getClass(), farmacosListFarmacosToAttach.getIdFarmaco());
                attachedFarmacosList.add(farmacosListFarmacosToAttach);
            }
            hospitalizacion.setFarmacosList(attachedFarmacosList);
            List<Procedimientos> attachedProcedimientosList = new ArrayList<Procedimientos>();
            for (Procedimientos procedimientosListProcedimientosToAttach : hospitalizacion.getProcedimientosList()) {
                procedimientosListProcedimientosToAttach = em.getReference(procedimientosListProcedimientosToAttach.getClass(), procedimientosListProcedimientosToAttach.getIdProcedimiento());
                attachedProcedimientosList.add(procedimientosListProcedimientosToAttach);
            }
            hospitalizacion.setProcedimientosList(attachedProcedimientosList);
            List<Anamnesis> attachedAnamnesisList = new ArrayList<Anamnesis>();
            for (Anamnesis anamnesisListAnamnesisToAttach : hospitalizacion.getAnamnesisList()) {
                anamnesisListAnamnesisToAttach = em.getReference(anamnesisListAnamnesisToAttach.getClass(), anamnesisListAnamnesisToAttach.getIdAnamnesis());
                attachedAnamnesisList.add(anamnesisListAnamnesisToAttach);
            }
            hospitalizacion.setAnamnesisList(attachedAnamnesisList);
            List<Patologias> attachedPatologiasList = new ArrayList<Patologias>();
            for (Patologias patologiasListPatologiasToAttach : hospitalizacion.getPatologiasList()) {
                patologiasListPatologiasToAttach = em.getReference(patologiasListPatologiasToAttach.getClass(), patologiasListPatologiasToAttach.getIdPatologia());
                attachedPatologiasList.add(patologiasListPatologiasToAttach);
            }
            hospitalizacion.setPatologiasList(attachedPatologiasList);
            em.persist(hospitalizacion);
            if (mascota != null) {
                mascota.getHospitalizacionList().add(hospitalizacion);
                mascota = em.merge(mascota);
            }
            if (veterinario != null) {
                veterinario.getHospitalizacionList().add(hospitalizacion);
                veterinario = em.merge(veterinario);
            }
            for (Examenes examenesListExamenes : hospitalizacion.getExamenesList()) {
                Hospitalizacion oldHospitalizacionOfExamenesListExamenes = examenesListExamenes.getHospitalizacion();
                examenesListExamenes.setHospitalizacion(hospitalizacion);
                examenesListExamenes = em.merge(examenesListExamenes);
                if (oldHospitalizacionOfExamenesListExamenes != null) {
                    oldHospitalizacionOfExamenesListExamenes.getExamenesList().remove(examenesListExamenes);
                    oldHospitalizacionOfExamenesListExamenes = em.merge(oldHospitalizacionOfExamenesListExamenes);
                }
            }
            for (Historialvacunas historialvacunasListHistorialvacunas : hospitalizacion.getHistorialvacunasList()) {
                Hospitalizacion oldHospitalizacionOfHistorialvacunasListHistorialvacunas = historialvacunasListHistorialvacunas.getHospitalizacion();
                historialvacunasListHistorialvacunas.setHospitalizacion(hospitalizacion);
                historialvacunasListHistorialvacunas = em.merge(historialvacunasListHistorialvacunas);
                if (oldHospitalizacionOfHistorialvacunasListHistorialvacunas != null) {
                    oldHospitalizacionOfHistorialvacunasListHistorialvacunas.getHistorialvacunasList().remove(historialvacunasListHistorialvacunas);
                    oldHospitalizacionOfHistorialvacunasListHistorialvacunas = em.merge(oldHospitalizacionOfHistorialvacunasListHistorialvacunas);
                }
            }
            for (Contraindicaciones contraindicacionesListContraindicaciones : hospitalizacion.getContraindicacionesList()) {
                Hospitalizacion oldHospitalizacionOfContraindicacionesListContraindicaciones = contraindicacionesListContraindicaciones.getHospitalizacion();
                contraindicacionesListContraindicaciones.setHospitalizacion(hospitalizacion);
                contraindicacionesListContraindicaciones = em.merge(contraindicacionesListContraindicaciones);
                if (oldHospitalizacionOfContraindicacionesListContraindicaciones != null) {
                    oldHospitalizacionOfContraindicacionesListContraindicaciones.getContraindicacionesList().remove(contraindicacionesListContraindicaciones);
                    oldHospitalizacionOfContraindicacionesListContraindicaciones = em.merge(oldHospitalizacionOfContraindicacionesListContraindicaciones);
                }
            }
            for (Desparacitaciones desparacitacionesListDesparacitaciones : hospitalizacion.getDesparacitacionesList()) {
                Hospitalizacion oldHospitalizacionOfDesparacitacionesListDesparacitaciones = desparacitacionesListDesparacitaciones.getHospitalizacion();
                desparacitacionesListDesparacitaciones.setHospitalizacion(hospitalizacion);
                desparacitacionesListDesparacitaciones = em.merge(desparacitacionesListDesparacitaciones);
                if (oldHospitalizacionOfDesparacitacionesListDesparacitaciones != null) {
                    oldHospitalizacionOfDesparacitacionesListDesparacitaciones.getDesparacitacionesList().remove(desparacitacionesListDesparacitaciones);
                    oldHospitalizacionOfDesparacitacionesListDesparacitaciones = em.merge(oldHospitalizacionOfDesparacitacionesListDesparacitaciones);
                }
            }
            for (Farmacos farmacosListFarmacos : hospitalizacion.getFarmacosList()) {
                Hospitalizacion oldHospitalizacionOfFarmacosListFarmacos = farmacosListFarmacos.getHospitalizacion();
                farmacosListFarmacos.setHospitalizacion(hospitalizacion);
                farmacosListFarmacos = em.merge(farmacosListFarmacos);
                if (oldHospitalizacionOfFarmacosListFarmacos != null) {
                    oldHospitalizacionOfFarmacosListFarmacos.getFarmacosList().remove(farmacosListFarmacos);
                    oldHospitalizacionOfFarmacosListFarmacos = em.merge(oldHospitalizacionOfFarmacosListFarmacos);
                }
            }
            for (Procedimientos procedimientosListProcedimientos : hospitalizacion.getProcedimientosList()) {
                Hospitalizacion oldHospitalizacionOfProcedimientosListProcedimientos = procedimientosListProcedimientos.getHospitalizacion();
                procedimientosListProcedimientos.setHospitalizacion(hospitalizacion);
                procedimientosListProcedimientos = em.merge(procedimientosListProcedimientos);
                if (oldHospitalizacionOfProcedimientosListProcedimientos != null) {
                    oldHospitalizacionOfProcedimientosListProcedimientos.getProcedimientosList().remove(procedimientosListProcedimientos);
                    oldHospitalizacionOfProcedimientosListProcedimientos = em.merge(oldHospitalizacionOfProcedimientosListProcedimientos);
                }
            }
            for (Anamnesis anamnesisListAnamnesis : hospitalizacion.getAnamnesisList()) {
                Hospitalizacion oldHospitalizacionOfAnamnesisListAnamnesis = anamnesisListAnamnesis.getHospitalizacion();
                anamnesisListAnamnesis.setHospitalizacion(hospitalizacion);
                anamnesisListAnamnesis = em.merge(anamnesisListAnamnesis);
                if (oldHospitalizacionOfAnamnesisListAnamnesis != null) {
                    oldHospitalizacionOfAnamnesisListAnamnesis.getAnamnesisList().remove(anamnesisListAnamnesis);
                    oldHospitalizacionOfAnamnesisListAnamnesis = em.merge(oldHospitalizacionOfAnamnesisListAnamnesis);
                }
            }
            for (Patologias patologiasListPatologias : hospitalizacion.getPatologiasList()) {
                Hospitalizacion oldHospitalizacionOfPatologiasListPatologias = patologiasListPatologias.getHospitalizacion();
                patologiasListPatologias.setHospitalizacion(hospitalizacion);
                patologiasListPatologias = em.merge(patologiasListPatologias);
                if (oldHospitalizacionOfPatologiasListPatologias != null) {
                    oldHospitalizacionOfPatologiasListPatologias.getPatologiasList().remove(patologiasListPatologias);
                    oldHospitalizacionOfPatologiasListPatologias = em.merge(oldHospitalizacionOfPatologiasListPatologias);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findHospitalizacion(hospitalizacion.getIdHospitalizacion()) != null) {
                throw new PreexistingEntityException("Hospitalizacion " + hospitalizacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Hospitalizacion hospitalizacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Hospitalizacion persistentHospitalizacion = em.find(Hospitalizacion.class, hospitalizacion.getIdHospitalizacion());
            Mascota mascotaOld = persistentHospitalizacion.getMascota();
            Mascota mascotaNew = hospitalizacion.getMascota();
            Veterinario veterinarioOld = persistentHospitalizacion.getVeterinario();
            Veterinario veterinarioNew = hospitalizacion.getVeterinario();
            List<Examenes> examenesListOld = persistentHospitalizacion.getExamenesList();
            List<Examenes> examenesListNew = hospitalizacion.getExamenesList();
            List<Historialvacunas> historialvacunasListOld = persistentHospitalizacion.getHistorialvacunasList();
            List<Historialvacunas> historialvacunasListNew = hospitalizacion.getHistorialvacunasList();
            List<Contraindicaciones> contraindicacionesListOld = persistentHospitalizacion.getContraindicacionesList();
            List<Contraindicaciones> contraindicacionesListNew = hospitalizacion.getContraindicacionesList();
            List<Desparacitaciones> desparacitacionesListOld = persistentHospitalizacion.getDesparacitacionesList();
            List<Desparacitaciones> desparacitacionesListNew = hospitalizacion.getDesparacitacionesList();
            List<Farmacos> farmacosListOld = persistentHospitalizacion.getFarmacosList();
            List<Farmacos> farmacosListNew = hospitalizacion.getFarmacosList();
            List<Procedimientos> procedimientosListOld = persistentHospitalizacion.getProcedimientosList();
            List<Procedimientos> procedimientosListNew = hospitalizacion.getProcedimientosList();
            List<Anamnesis> anamnesisListOld = persistentHospitalizacion.getAnamnesisList();
            List<Anamnesis> anamnesisListNew = hospitalizacion.getAnamnesisList();
            List<Patologias> patologiasListOld = persistentHospitalizacion.getPatologiasList();
            List<Patologias> patologiasListNew = hospitalizacion.getPatologiasList();
            List<String> illegalOrphanMessages = null;
            for (Examenes examenesListOldExamenes : examenesListOld) {
                if (!examenesListNew.contains(examenesListOldExamenes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Examenes " + examenesListOldExamenes + " since its hospitalizacion field is not nullable.");
                }
            }
            for (Historialvacunas historialvacunasListOldHistorialvacunas : historialvacunasListOld) {
                if (!historialvacunasListNew.contains(historialvacunasListOldHistorialvacunas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Historialvacunas " + historialvacunasListOldHistorialvacunas + " since its hospitalizacion field is not nullable.");
                }
            }
            for (Contraindicaciones contraindicacionesListOldContraindicaciones : contraindicacionesListOld) {
                if (!contraindicacionesListNew.contains(contraindicacionesListOldContraindicaciones)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Contraindicaciones " + contraindicacionesListOldContraindicaciones + " since its hospitalizacion field is not nullable.");
                }
            }
            for (Desparacitaciones desparacitacionesListOldDesparacitaciones : desparacitacionesListOld) {
                if (!desparacitacionesListNew.contains(desparacitacionesListOldDesparacitaciones)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Desparacitaciones " + desparacitacionesListOldDesparacitaciones + " since its hospitalizacion field is not nullable.");
                }
            }
            for (Farmacos farmacosListOldFarmacos : farmacosListOld) {
                if (!farmacosListNew.contains(farmacosListOldFarmacos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Farmacos " + farmacosListOldFarmacos + " since its hospitalizacion field is not nullable.");
                }
            }
            for (Procedimientos procedimientosListOldProcedimientos : procedimientosListOld) {
                if (!procedimientosListNew.contains(procedimientosListOldProcedimientos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Procedimientos " + procedimientosListOldProcedimientos + " since its hospitalizacion field is not nullable.");
                }
            }
            for (Anamnesis anamnesisListOldAnamnesis : anamnesisListOld) {
                if (!anamnesisListNew.contains(anamnesisListOldAnamnesis)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Anamnesis " + anamnesisListOldAnamnesis + " since its hospitalizacion field is not nullable.");
                }
            }
            for (Patologias patologiasListOldPatologias : patologiasListOld) {
                if (!patologiasListNew.contains(patologiasListOldPatologias)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Patologias " + patologiasListOldPatologias + " since its hospitalizacion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (mascotaNew != null) {
                mascotaNew = em.getReference(mascotaNew.getClass(), mascotaNew.getIdMascota());
                hospitalizacion.setMascota(mascotaNew);
            }
            if (veterinarioNew != null) {
                veterinarioNew = em.getReference(veterinarioNew.getClass(), veterinarioNew.getIdVeterinario());
                hospitalizacion.setVeterinario(veterinarioNew);
            }
            List<Examenes> attachedExamenesListNew = new ArrayList<Examenes>();
            for (Examenes examenesListNewExamenesToAttach : examenesListNew) {
                examenesListNewExamenesToAttach = em.getReference(examenesListNewExamenesToAttach.getClass(), examenesListNewExamenesToAttach.getIdExamen());
                attachedExamenesListNew.add(examenesListNewExamenesToAttach);
            }
            examenesListNew = attachedExamenesListNew;
            hospitalizacion.setExamenesList(examenesListNew);
            List<Historialvacunas> attachedHistorialvacunasListNew = new ArrayList<Historialvacunas>();
            for (Historialvacunas historialvacunasListNewHistorialvacunasToAttach : historialvacunasListNew) {
                historialvacunasListNewHistorialvacunasToAttach = em.getReference(historialvacunasListNewHistorialvacunasToAttach.getClass(), historialvacunasListNewHistorialvacunasToAttach.getIdEvento());
                attachedHistorialvacunasListNew.add(historialvacunasListNewHistorialvacunasToAttach);
            }
            historialvacunasListNew = attachedHistorialvacunasListNew;
            hospitalizacion.setHistorialvacunasList(historialvacunasListNew);
            List<Contraindicaciones> attachedContraindicacionesListNew = new ArrayList<Contraindicaciones>();
            for (Contraindicaciones contraindicacionesListNewContraindicacionesToAttach : contraindicacionesListNew) {
                contraindicacionesListNewContraindicacionesToAttach = em.getReference(contraindicacionesListNewContraindicacionesToAttach.getClass(), contraindicacionesListNewContraindicacionesToAttach.getIdContraindicacion());
                attachedContraindicacionesListNew.add(contraindicacionesListNewContraindicacionesToAttach);
            }
            contraindicacionesListNew = attachedContraindicacionesListNew;
            hospitalizacion.setContraindicacionesList(contraindicacionesListNew);
            List<Desparacitaciones> attachedDesparacitacionesListNew = new ArrayList<Desparacitaciones>();
            for (Desparacitaciones desparacitacionesListNewDesparacitacionesToAttach : desparacitacionesListNew) {
                desparacitacionesListNewDesparacitacionesToAttach = em.getReference(desparacitacionesListNewDesparacitacionesToAttach.getClass(), desparacitacionesListNewDesparacitacionesToAttach.getIdDesparacitacion());
                attachedDesparacitacionesListNew.add(desparacitacionesListNewDesparacitacionesToAttach);
            }
            desparacitacionesListNew = attachedDesparacitacionesListNew;
            hospitalizacion.setDesparacitacionesList(desparacitacionesListNew);
            List<Farmacos> attachedFarmacosListNew = new ArrayList<Farmacos>();
            for (Farmacos farmacosListNewFarmacosToAttach : farmacosListNew) {
                farmacosListNewFarmacosToAttach = em.getReference(farmacosListNewFarmacosToAttach.getClass(), farmacosListNewFarmacosToAttach.getIdFarmaco());
                attachedFarmacosListNew.add(farmacosListNewFarmacosToAttach);
            }
            farmacosListNew = attachedFarmacosListNew;
            hospitalizacion.setFarmacosList(farmacosListNew);
            List<Procedimientos> attachedProcedimientosListNew = new ArrayList<Procedimientos>();
            for (Procedimientos procedimientosListNewProcedimientosToAttach : procedimientosListNew) {
                procedimientosListNewProcedimientosToAttach = em.getReference(procedimientosListNewProcedimientosToAttach.getClass(), procedimientosListNewProcedimientosToAttach.getIdProcedimiento());
                attachedProcedimientosListNew.add(procedimientosListNewProcedimientosToAttach);
            }
            procedimientosListNew = attachedProcedimientosListNew;
            hospitalizacion.setProcedimientosList(procedimientosListNew);
            List<Anamnesis> attachedAnamnesisListNew = new ArrayList<Anamnesis>();
            for (Anamnesis anamnesisListNewAnamnesisToAttach : anamnesisListNew) {
                anamnesisListNewAnamnesisToAttach = em.getReference(anamnesisListNewAnamnesisToAttach.getClass(), anamnesisListNewAnamnesisToAttach.getIdAnamnesis());
                attachedAnamnesisListNew.add(anamnesisListNewAnamnesisToAttach);
            }
            anamnesisListNew = attachedAnamnesisListNew;
            hospitalizacion.setAnamnesisList(anamnesisListNew);
            List<Patologias> attachedPatologiasListNew = new ArrayList<Patologias>();
            for (Patologias patologiasListNewPatologiasToAttach : patologiasListNew) {
                patologiasListNewPatologiasToAttach = em.getReference(patologiasListNewPatologiasToAttach.getClass(), patologiasListNewPatologiasToAttach.getIdPatologia());
                attachedPatologiasListNew.add(patologiasListNewPatologiasToAttach);
            }
            patologiasListNew = attachedPatologiasListNew;
            hospitalizacion.setPatologiasList(patologiasListNew);
            hospitalizacion = em.merge(hospitalizacion);
            if (mascotaOld != null && !mascotaOld.equals(mascotaNew)) {
                mascotaOld.getHospitalizacionList().remove(hospitalizacion);
                mascotaOld = em.merge(mascotaOld);
            }
            if (mascotaNew != null && !mascotaNew.equals(mascotaOld)) {
                mascotaNew.getHospitalizacionList().add(hospitalizacion);
                mascotaNew = em.merge(mascotaNew);
            }
            if (veterinarioOld != null && !veterinarioOld.equals(veterinarioNew)) {
                veterinarioOld.getHospitalizacionList().remove(hospitalizacion);
                veterinarioOld = em.merge(veterinarioOld);
            }
            if (veterinarioNew != null && !veterinarioNew.equals(veterinarioOld)) {
                veterinarioNew.getHospitalizacionList().add(hospitalizacion);
                veterinarioNew = em.merge(veterinarioNew);
            }
            for (Examenes examenesListNewExamenes : examenesListNew) {
                if (!examenesListOld.contains(examenesListNewExamenes)) {
                    Hospitalizacion oldHospitalizacionOfExamenesListNewExamenes = examenesListNewExamenes.getHospitalizacion();
                    examenesListNewExamenes.setHospitalizacion(hospitalizacion);
                    examenesListNewExamenes = em.merge(examenesListNewExamenes);
                    if (oldHospitalizacionOfExamenesListNewExamenes != null && !oldHospitalizacionOfExamenesListNewExamenes.equals(hospitalizacion)) {
                        oldHospitalizacionOfExamenesListNewExamenes.getExamenesList().remove(examenesListNewExamenes);
                        oldHospitalizacionOfExamenesListNewExamenes = em.merge(oldHospitalizacionOfExamenesListNewExamenes);
                    }
                }
            }
            for (Historialvacunas historialvacunasListNewHistorialvacunas : historialvacunasListNew) {
                if (!historialvacunasListOld.contains(historialvacunasListNewHistorialvacunas)) {
                    Hospitalizacion oldHospitalizacionOfHistorialvacunasListNewHistorialvacunas = historialvacunasListNewHistorialvacunas.getHospitalizacion();
                    historialvacunasListNewHistorialvacunas.setHospitalizacion(hospitalizacion);
                    historialvacunasListNewHistorialvacunas = em.merge(historialvacunasListNewHistorialvacunas);
                    if (oldHospitalizacionOfHistorialvacunasListNewHistorialvacunas != null && !oldHospitalizacionOfHistorialvacunasListNewHistorialvacunas.equals(hospitalizacion)) {
                        oldHospitalizacionOfHistorialvacunasListNewHistorialvacunas.getHistorialvacunasList().remove(historialvacunasListNewHistorialvacunas);
                        oldHospitalizacionOfHistorialvacunasListNewHistorialvacunas = em.merge(oldHospitalizacionOfHistorialvacunasListNewHistorialvacunas);
                    }
                }
            }
            for (Contraindicaciones contraindicacionesListNewContraindicaciones : contraindicacionesListNew) {
                if (!contraindicacionesListOld.contains(contraindicacionesListNewContraindicaciones)) {
                    Hospitalizacion oldHospitalizacionOfContraindicacionesListNewContraindicaciones = contraindicacionesListNewContraindicaciones.getHospitalizacion();
                    contraindicacionesListNewContraindicaciones.setHospitalizacion(hospitalizacion);
                    contraindicacionesListNewContraindicaciones = em.merge(contraindicacionesListNewContraindicaciones);
                    if (oldHospitalizacionOfContraindicacionesListNewContraindicaciones != null && !oldHospitalizacionOfContraindicacionesListNewContraindicaciones.equals(hospitalizacion)) {
                        oldHospitalizacionOfContraindicacionesListNewContraindicaciones.getContraindicacionesList().remove(contraindicacionesListNewContraindicaciones);
                        oldHospitalizacionOfContraindicacionesListNewContraindicaciones = em.merge(oldHospitalizacionOfContraindicacionesListNewContraindicaciones);
                    }
                }
            }
            for (Desparacitaciones desparacitacionesListNewDesparacitaciones : desparacitacionesListNew) {
                if (!desparacitacionesListOld.contains(desparacitacionesListNewDesparacitaciones)) {
                    Hospitalizacion oldHospitalizacionOfDesparacitacionesListNewDesparacitaciones = desparacitacionesListNewDesparacitaciones.getHospitalizacion();
                    desparacitacionesListNewDesparacitaciones.setHospitalizacion(hospitalizacion);
                    desparacitacionesListNewDesparacitaciones = em.merge(desparacitacionesListNewDesparacitaciones);
                    if (oldHospitalizacionOfDesparacitacionesListNewDesparacitaciones != null && !oldHospitalizacionOfDesparacitacionesListNewDesparacitaciones.equals(hospitalizacion)) {
                        oldHospitalizacionOfDesparacitacionesListNewDesparacitaciones.getDesparacitacionesList().remove(desparacitacionesListNewDesparacitaciones);
                        oldHospitalizacionOfDesparacitacionesListNewDesparacitaciones = em.merge(oldHospitalizacionOfDesparacitacionesListNewDesparacitaciones);
                    }
                }
            }
            for (Farmacos farmacosListNewFarmacos : farmacosListNew) {
                if (!farmacosListOld.contains(farmacosListNewFarmacos)) {
                    Hospitalizacion oldHospitalizacionOfFarmacosListNewFarmacos = farmacosListNewFarmacos.getHospitalizacion();
                    farmacosListNewFarmacos.setHospitalizacion(hospitalizacion);
                    farmacosListNewFarmacos = em.merge(farmacosListNewFarmacos);
                    if (oldHospitalizacionOfFarmacosListNewFarmacos != null && !oldHospitalizacionOfFarmacosListNewFarmacos.equals(hospitalizacion)) {
                        oldHospitalizacionOfFarmacosListNewFarmacos.getFarmacosList().remove(farmacosListNewFarmacos);
                        oldHospitalizacionOfFarmacosListNewFarmacos = em.merge(oldHospitalizacionOfFarmacosListNewFarmacos);
                    }
                }
            }
            for (Procedimientos procedimientosListNewProcedimientos : procedimientosListNew) {
                if (!procedimientosListOld.contains(procedimientosListNewProcedimientos)) {
                    Hospitalizacion oldHospitalizacionOfProcedimientosListNewProcedimientos = procedimientosListNewProcedimientos.getHospitalizacion();
                    procedimientosListNewProcedimientos.setHospitalizacion(hospitalizacion);
                    procedimientosListNewProcedimientos = em.merge(procedimientosListNewProcedimientos);
                    if (oldHospitalizacionOfProcedimientosListNewProcedimientos != null && !oldHospitalizacionOfProcedimientosListNewProcedimientos.equals(hospitalizacion)) {
                        oldHospitalizacionOfProcedimientosListNewProcedimientos.getProcedimientosList().remove(procedimientosListNewProcedimientos);
                        oldHospitalizacionOfProcedimientosListNewProcedimientos = em.merge(oldHospitalizacionOfProcedimientosListNewProcedimientos);
                    }
                }
            }
            for (Anamnesis anamnesisListNewAnamnesis : anamnesisListNew) {
                if (!anamnesisListOld.contains(anamnesisListNewAnamnesis)) {
                    Hospitalizacion oldHospitalizacionOfAnamnesisListNewAnamnesis = anamnesisListNewAnamnesis.getHospitalizacion();
                    anamnesisListNewAnamnesis.setHospitalizacion(hospitalizacion);
                    anamnesisListNewAnamnesis = em.merge(anamnesisListNewAnamnesis);
                    if (oldHospitalizacionOfAnamnesisListNewAnamnesis != null && !oldHospitalizacionOfAnamnesisListNewAnamnesis.equals(hospitalizacion)) {
                        oldHospitalizacionOfAnamnesisListNewAnamnesis.getAnamnesisList().remove(anamnesisListNewAnamnesis);
                        oldHospitalizacionOfAnamnesisListNewAnamnesis = em.merge(oldHospitalizacionOfAnamnesisListNewAnamnesis);
                    }
                }
            }
            for (Patologias patologiasListNewPatologias : patologiasListNew) {
                if (!patologiasListOld.contains(patologiasListNewPatologias)) {
                    Hospitalizacion oldHospitalizacionOfPatologiasListNewPatologias = patologiasListNewPatologias.getHospitalizacion();
                    patologiasListNewPatologias.setHospitalizacion(hospitalizacion);
                    patologiasListNewPatologias = em.merge(patologiasListNewPatologias);
                    if (oldHospitalizacionOfPatologiasListNewPatologias != null && !oldHospitalizacionOfPatologiasListNewPatologias.equals(hospitalizacion)) {
                        oldHospitalizacionOfPatologiasListNewPatologias.getPatologiasList().remove(patologiasListNewPatologias);
                        oldHospitalizacionOfPatologiasListNewPatologias = em.merge(oldHospitalizacionOfPatologiasListNewPatologias);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = hospitalizacion.getIdHospitalizacion();
                if (findHospitalizacion(id) == null) {
                    throw new NonexistentEntityException("The hospitalizacion with id " + id + " no longer exists.");
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
            Hospitalizacion hospitalizacion;
            try {
                hospitalizacion = em.getReference(Hospitalizacion.class, id);
                hospitalizacion.getIdHospitalizacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The hospitalizacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Examenes> examenesListOrphanCheck = hospitalizacion.getExamenesList();
            for (Examenes examenesListOrphanCheckExamenes : examenesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Hospitalizacion (" + hospitalizacion + ") cannot be destroyed since the Examenes " + examenesListOrphanCheckExamenes + " in its examenesList field has a non-nullable hospitalizacion field.");
            }
            List<Historialvacunas> historialvacunasListOrphanCheck = hospitalizacion.getHistorialvacunasList();
            for (Historialvacunas historialvacunasListOrphanCheckHistorialvacunas : historialvacunasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Hospitalizacion (" + hospitalizacion + ") cannot be destroyed since the Historialvacunas " + historialvacunasListOrphanCheckHistorialvacunas + " in its historialvacunasList field has a non-nullable hospitalizacion field.");
            }
            List<Contraindicaciones> contraindicacionesListOrphanCheck = hospitalizacion.getContraindicacionesList();
            for (Contraindicaciones contraindicacionesListOrphanCheckContraindicaciones : contraindicacionesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Hospitalizacion (" + hospitalizacion + ") cannot be destroyed since the Contraindicaciones " + contraindicacionesListOrphanCheckContraindicaciones + " in its contraindicacionesList field has a non-nullable hospitalizacion field.");
            }
            List<Desparacitaciones> desparacitacionesListOrphanCheck = hospitalizacion.getDesparacitacionesList();
            for (Desparacitaciones desparacitacionesListOrphanCheckDesparacitaciones : desparacitacionesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Hospitalizacion (" + hospitalizacion + ") cannot be destroyed since the Desparacitaciones " + desparacitacionesListOrphanCheckDesparacitaciones + " in its desparacitacionesList field has a non-nullable hospitalizacion field.");
            }
            List<Farmacos> farmacosListOrphanCheck = hospitalizacion.getFarmacosList();
            for (Farmacos farmacosListOrphanCheckFarmacos : farmacosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Hospitalizacion (" + hospitalizacion + ") cannot be destroyed since the Farmacos " + farmacosListOrphanCheckFarmacos + " in its farmacosList field has a non-nullable hospitalizacion field.");
            }
            List<Procedimientos> procedimientosListOrphanCheck = hospitalizacion.getProcedimientosList();
            for (Procedimientos procedimientosListOrphanCheckProcedimientos : procedimientosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Hospitalizacion (" + hospitalizacion + ") cannot be destroyed since the Procedimientos " + procedimientosListOrphanCheckProcedimientos + " in its procedimientosList field has a non-nullable hospitalizacion field.");
            }
            List<Anamnesis> anamnesisListOrphanCheck = hospitalizacion.getAnamnesisList();
            for (Anamnesis anamnesisListOrphanCheckAnamnesis : anamnesisListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Hospitalizacion (" + hospitalizacion + ") cannot be destroyed since the Anamnesis " + anamnesisListOrphanCheckAnamnesis + " in its anamnesisList field has a non-nullable hospitalizacion field.");
            }
            List<Patologias> patologiasListOrphanCheck = hospitalizacion.getPatologiasList();
            for (Patologias patologiasListOrphanCheckPatologias : patologiasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Hospitalizacion (" + hospitalizacion + ") cannot be destroyed since the Patologias " + patologiasListOrphanCheckPatologias + " in its patologiasList field has a non-nullable hospitalizacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Mascota mascota = hospitalizacion.getMascota();
            if (mascota != null) {
                mascota.getHospitalizacionList().remove(hospitalizacion);
                mascota = em.merge(mascota);
            }
            Veterinario veterinario = hospitalizacion.getVeterinario();
            if (veterinario != null) {
                veterinario.getHospitalizacionList().remove(hospitalizacion);
                veterinario = em.merge(veterinario);
            }
            em.remove(hospitalizacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Hospitalizacion> findHospitalizacionEntities() {
        return findHospitalizacionEntities(true, -1, -1);
    }

    public List<Hospitalizacion> findHospitalizacionEntities(int maxResults, int firstResult) {
        return findHospitalizacionEntities(false, maxResults, firstResult);
    }

    private List<Hospitalizacion> findHospitalizacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Hospitalizacion.class));
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

    public Hospitalizacion findHospitalizacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Hospitalizacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getHospitalizacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Hospitalizacion> rt = cq.from(Hospitalizacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
