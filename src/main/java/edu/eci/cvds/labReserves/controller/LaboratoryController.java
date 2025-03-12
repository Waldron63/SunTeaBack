package edu.eci.cvds.labReserves.controller;

import edu.eci.cvds.labReserves.collections.LaboratoryMongodb;
import edu.eci.cvds.labReserves.model.Schedule;
import edu.eci.cvds.labReserves.model.Laboratory;
import edu.eci.cvds.labReserves.services.LaboratorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST para gestionar las operaciones de laboratorio.
 */
@RestController
@RequestMapping("/api/laboratories")
@CrossOrigin(origins = "*")
public class LaboratoryController {

    private final LaboratorioService laboratorioService;

    @Autowired
    public LaboratoryController(LaboratorioService laboratorioService) {
        this.laboratorioService = laboratorioService;
    }

    // Crea un nuevo laboratorio
    @PostMapping("/")
    public ResponseEntity<Laboratory> createLaboratory(@RequestBody Laboratory laboratory) {
        Laboratory createdLab = laboratorioService.createLaboratory(laboratory);
        return new ResponseEntity<>(createdLab, HttpStatus.CREATED);
    }

    // Obtiene todos los laboratorios
    @GetMapping("/")
    public ResponseEntity<List<Laboratory>> getAllLaboratories() {
        List<Laboratory> laboratories = laboratorioService.getAllLaboratories();
        return new ResponseEntity<>(laboratories, HttpStatus.OK);
    }

    // Obtiene un laboratorio por su abreviatura
    @GetMapping("/{abbreviation}")
    public ResponseEntity<Laboratory> getLaboratoryByAbbreviation(@PathVariable String abbreviation) {
        Laboratory laboratory = laboratorioService.getLaboratoryByAbbreviation(abbreviation);
        
        if (laboratory != null) {
            return new ResponseEntity<>(laboratory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Actualiza un laboratorio existente
    @PutMapping("/{abbreviation}/update")
    public ResponseEntity<Laboratory> updateLaboratory(@PathVariable String abbreviation, @RequestBody Laboratory laboratory) {
        Laboratory updatedLab = laboratorioService.updateLaboratory(abbreviation, laboratory);
        
        if (updatedLab != null) {
            return new ResponseEntity<>(updatedLab, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Elimina un laboratorio
    @DeleteMapping("/{abbreviation}/byelaboratory")
    public ResponseEntity<String> deleteLaboratory(@PathVariable String abbreviation) {
        boolean deleted = laboratorioService.deleteLaboratory(abbreviation);
        
        if (deleted) {
            return new ResponseEntity<>("Laboratorio eliminado con éxito", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Laboratorio no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    // Agrega un horario disponible a un laboratorio
    @PostMapping("/{abbreviation}/schedule")
    public ResponseEntity<Laboratory> addAvailableDay(
            @PathVariable String abbreviation,
            @RequestBody Map<String, String> requestBody) {
        
        // Convertir los valores del mapa a los tipos necesarios
        DayOfWeek day = DayOfWeek.valueOf(requestBody.get("day"));
        LocalTime openingTime = LocalTime.parse(requestBody.get("openingTime"));
        LocalTime closingTime = LocalTime.parse(requestBody.get("closingTime"));
        
        Laboratory updatedLab = laboratorioService.addAvailableDay(abbreviation, day, openingTime, closingTime);
        
        if (updatedLab != null) {
            return new ResponseEntity<>(updatedLab, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Verifica disponibilidad de un laboratorio para un horario
    @PostMapping("/{abbreviation}/availability")
    public ResponseEntity<Map<String, Boolean>> checkAvailability(
            @PathVariable String abbreviation,
            @RequestBody Schedule schedule) {
        
        boolean isAvailable = laboratorioService.isLaboratoryAvailable(abbreviation, schedule);
        Map<String, Boolean> response = Map.of("available", isAvailable);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
