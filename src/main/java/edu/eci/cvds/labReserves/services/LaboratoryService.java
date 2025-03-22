package edu.eci.cvds.labReserves.services;

import edu.eci.cvds.labReserves.collections.LaboratoryMongodb;
import edu.eci.cvds.labReserves.repository.mongodb.LaboratoryMongoRepository;
import edu.eci.cvds.labReserves.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio para la gestión de laboratorios en el sistema.
 */
@Service
public class LaboratoryService {

    @Autowired
    private LaboratoryMongoRepository laboratoryRepository;

    /**
     * Crea un nuevo laboratorio en la base de datos.
     * @param laboratory Objeto Laboratory con la información del laboratorio.
     * @return El laboratorio creado.
     */
    public Laboratory createLaboratory(Laboratory laboratory) {
        LaboratoryMongodb labMongo = new LaboratoryMongodb(laboratory);
        return laboratoryRepository.save(labMongo);
    }

    /**
     * Obtiene todos los laboratorios registrados en la base de datos.
     * @return Lista de todos los laboratorios.
     */
    public List<LaboratoryMongodb> getAllLaboratories() {
        return laboratoryRepository.findAll();
    }

    /**
     * Busca un laboratorio por su abreviatura.
     * @param abbreviation Abreviatura del laboratorio.
     * @return El laboratorio encontrado o null si no existe.
     */
    public LaboratoryMongodb getLaboratoryByAbbreviation(String abbreviation) {
        return laboratoryRepository.findByAbbreviation(abbreviation);
    }

    /**
     * Actualiza la referencia de horario de un laboratorio existente.
     * @param abbreviation Abreviatura del laboratorio a actualizar.
     * @param totalcapacity nueva capacidad del laboratorio.
     */
    public void updateLaboratoryTotalCapacity(String abbreviation, int totalcapacity) {
        LaboratoryMongodb existingLab = laboratoryRepository.findByAbbreviation(abbreviation);
        // Esta linea se va mañana RECORDATORIOOOO (2 lineas)
        /*List<ScheduleReference> listschedule = existingLab.getScheduleReferences();
        for (ScheduleReference schedule : listschedule){
            if (schedule.getDayOfWeek().equals(schedulereference.getDayOfWeek())){
                schedule.setOpeningTime(schedulereference.getOpeningTime());
                schedule.setClosingTime(schedulereference.getClosingTime());
            }
        }
        existingLab.setScheduleReferences(listschedule);*/
        existingLab.setTotalCapacity(totalcapacity);
        laboratoryRepository.save(existingLab);

    }

    /**
     * Actualiza la referencia de horario de un laboratorio existente.
     * @param abbreviation Abreviatura del laboratorio a actualizar.
     * @param newAbbreviation nueva abreviatura del laboratorio.
     */
    public void updateLaboratoryAbbreviation(String abbreviation, String newAbbreviation) {
        LaboratoryMongodb existingLab = laboratoryRepository.findByAbbreviation(abbreviation);
        existingLab.setAbbreviation(newAbbreviation);
        laboratoryRepository.save(existingLab);

    }

    /**
     * Actualiza el nombre de horario de un laboratorio existente.
     * @param abbreviation Abreviatura del laboratorio a actualizar.
     * @param newName nuevo nombre del laboratorio a actualizar.
     */
    public void updateLaboratoryName(String abbreviation, String newName) {
        LaboratoryMongodb existingLab = laboratoryRepository.findByAbbreviation(abbreviation);
        existingLab.setName(newName);
        laboratoryRepository.save(existingLab);

    }

    /**
     * Actualiza el nombre de horario de un laboratorio existente.
     * @param abbreviation Abreviatura del laboratorio a actualizar.
     * @param newLocation nueva locación del laboratorio a actualizar.
     */
    public void updateLaboratoryLocation(String abbreviation, String newLocation) {
        LaboratoryMongodb existingLab = laboratoryRepository.findByAbbreviation(abbreviation);
        existingLab.setLocation(newLocation);
        laboratoryRepository.save(existingLab);

    }

    /**
     * Actualiza el nombre de horario de un laboratorio existente.
     * @param abbreviation Abreviatura del laboratorio a actualizar.
     * @param physical nueva locación del laboratorio a actualizar.
     */
    public void updateLaboratoryPhysicsResources(String abbreviation, Physical physical) {
        LaboratoryMongodb existingLab = laboratoryRepository.findByAbbreviation(abbreviation);
        existingLab.setPhysicalResource(physical);
        laboratoryRepository.save(existingLab);

    }

    /**
     * Actualiza el nombre de horario de un laboratorio existente.
     * @param abbreviation Abreviatura del laboratorio a actualizar.
     * @param software nueva locación del laboratorio a actualizar.
     */
    public void updateLaboratorySoftwareResources(String abbreviation, Software software) {
        LaboratoryMongodb existingLab = laboratoryRepository.findByAbbreviation(abbreviation);
        existingLab.setSoftwareResource(software);
        laboratoryRepository.save(existingLab);

    }

    /**
     * Actualiza el nombre de horario de un laboratorio existente.
     * @param abbreviation Abreviatura del laboratorio a actualizar.
     * @param scheduleReference nueva locación del laboratorio a actualizar.
     */
    public void updateLaboratoryScheduleReference(String abbreviation, ScheduleReference scheduleReference) {
        LaboratoryMongodb existingLab = laboratoryRepository.findByAbbreviation(abbreviation);
        existingLab.setScheduleReference(scheduleReference);
        laboratoryRepository.save(existingLab);

    }

    /**
     * Elimina un laboratorio por su objeto de referencia.
     * @param laboratory Objeto Laboratory que se desea eliminar.
     * @return true si el laboratorio fue eliminado, false si no existe.
     */
    public boolean deleteLaboratory(Laboratory laboratory) {
        if (laboratoryRepository.existsByAbbreviation(laboratory.getAbbreviation())) {
            Laboratory lab = getLaboratoryByAbbreviation(laboratory.getAbbreviation());
            laboratoryRepository.deleteByAbbreviation(laboratory.getAbbreviation());
            return true;
        }
        return false;
    }

    /**
     * Elimina un laboratorio por su abreviatura.
     * @param abbreviation Abreviatura del laboratorio a eliminar.
     */
    public void deleteByAbbreviation(String abbreviation) {
        laboratoryRepository.deleteByAbbreviation(abbreviation);
    }

    /**
     * Agrega un horario disponible a un laboratorio.
     * @param abbreviation Abreviatura del laboratorio.
     * @param day Día de la semana en el que el laboratorio estará disponible.
     * @param openingTime Hora de apertura.
     * @param closingTime Hora de cierre.
     */
    public void addAvailableDay(String abbreviation, DayOfWeek day, LocalTime openingTime,
                                      LocalTime closingTime) {
        LaboratoryMongodb lab = laboratoryRepository.findByAbbreviation(abbreviation);

        ScheduleReference newSchedule = new ScheduleReference(day, openingTime, closingTime);
        lab.getScheduleReferences().add(newSchedule);
        laboratoryRepository.save(lab);
    }

    /**
     * Verifica si un laboratorio está disponible para un horario específico.
     * @param abbreviation Abreviatura del laboratorio.
     * @param schedule Objeto Schedule con el horario a verificar.
     * @return true si el laboratorio está disponible, false en caso contrario.
     */
    public boolean isLaboratoryAvailable(String abbreviation, Schedule schedule) {
        Laboratory lab = getLaboratoryByAbbreviation(abbreviation);

        if (lab != null) {
            ScheduleReference scheduleRef = lab.getScheduleReferenceForDay(schedule.getDay());
            DayOfWeek scheduleDay = schedule.getDay();
            LocalTime scheduleStartTime = schedule.getStartHour();
            LocalTime scheduleEndTime = schedule.getEndHour();
            if (scheduleRef != null) {
                return scheduleRef.isAvailable(scheduleDay, scheduleStartTime, scheduleEndTime);
            }
        }

        return false;
    }


}