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

@Service
public class LaboratorioService {

    private final LaboratoryMongoRepository laboratorioRepository;

    @Autowired
    public LaboratorioService(LaboratoryMongoRepository laboratorioRepository) {
        this.laboratorioRepository = laboratorioRepository;
    }

    // Crea un nuevo laboratorio en la base de datos
    public Laboratory createLaboratory(Laboratory laboratory) {
        LaboratoryMongodb labMongo = new LaboratoryMongodb(laboratory);
        return laboratorioRepository.save(labMongo);
    }

    // Obtiene todos los laboratorios
    public List<Laboratory> getAllLaboratories() {
        List<LaboratoryMongodb> mongoList = laboratorioRepository.findAll();
        List<Laboratory> result = new ArrayList<>();
        
        for (LaboratoryMongodb mongo : mongoList) {
            Laboratory lab = new Laboratory();
            lab.setName(mongo.getName());
            lab.setAbbreviation(mongo.getAbbreviation());
            lab.setTotalCapacity(mongo.getTotalCapacity());
            lab.setLocation(mongo.getLocation());
            lab.setScheduleReferences(mongo.getScheduleReferences());
            result.add(lab);
        }
        
        return result;
    }

    // Busca un laboratorio por su abreviatura
    public Laboratory getLaboratoryByAbbreviation(String abbreviation) {
        return laboratorioRepository.findByAbbreviation(abbreviation);
    }

    // Actualiza un laboratorio existente
    public Laboratory updateLaboratory(String abbreviation, Laboratory updatedLab) {
        LaboratoryMongodb existingLab = laboratorioRepository.findByAbbreviation(abbreviation);
        
        if (existingLab != null) {
            existingLab.setName(updatedLab.getName());
            existingLab.setTotalCapacity(updatedLab.getTotalCapacity());
            existingLab.setLocation(updatedLab.getLocation());
            existingLab.setScheduleReferences(updatedLab.getScheduleReferences());
            
            return laboratorioRepository.save(existingLab);
        }
        
        return null;
    }

    // Elimina un laboratorio por su abreviatura
    public boolean deleteLaboratory(String abbreviation) {
        if (laboratorioRepository.existsByAbbreviation(abbreviation)) {
            laboratorioRepository.deleteByAbbreviation(abbreviation);
            return true;
        }
        return false;
    }

    // Agrega un horario disponible a un laboratorio
    public Laboratory addAvailableDay(String abbreviation, DayOfWeek day, LocalTime openingTime, 
                                      LocalTime closingTime) {
        LaboratoryMongodb lab = laboratorioRepository.findByAbbreviation(abbreviation);
        
        if (lab != null) {
            lab.addAvailableDay(day, openingTime, closingTime);
            return laboratorioRepository.save(lab);
        }
        
        return null;
    }

    // Verifica si un laboratorio está disponible para un horario específico
    public boolean isLaboratoryAvailable(String abbreviation, Schedule schedule) {
        Laboratory lab = getLaboratoryByAbbreviation(abbreviation);
        
        if (lab != null) {
            ScheduleReference scheduleRef = lab.getScheduleReferenceForDay(schedule.getDay());
            
            if (scheduleRef != null) {
                return scheduleRef.isAvailable(schedule);
            }
        }
        
        return false;
    }
}