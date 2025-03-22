package edu.eci.cvds.labReserves.model;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Representa un laboratorio con sus respectivos recursos físicos, de software y horarios disponibles.
 */
public class Laboratory {

    private String abbreviation;
    private String name;
    private int totalCapacity;
    private String location;
    private Physical physicalResource;
    private Software softwareResource;
    private List<ScheduleReference> scheduleReferences;

    /**
     * Constructor por defecto. Inicializa las listas de recursos físicos, de software y referencias de horario.
     */
    public Laboratory() {
        this.scheduleReferences = new ArrayList<>();
    }

    /**
     * Constructor con parámetros para inicializar un laboratorio con datos específicos.
     *
     * @param name              Nombre del laboratorio.
     * @param abbreviation      Abreviatura del laboratorio.
     * @param totalCapacity     Capacidad total del laboratorio.
     * @param location          Ubicación del laboratorio.
     * @param scheduleReferences Lista de horarios de referencia del laboratorio.
     */
    public Laboratory(String name, String abbreviation, int totalCapacity, String location, List<ScheduleReference> scheduleReferences) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.totalCapacity = totalCapacity;
        this.location = location;
        this.scheduleReferences = scheduleReferences;
    }

    /**
     * Establece los horarios de referencia del laboratorio a partir de un mapa de días y horarios.
     *
     * @param daySchedules Mapa que asocia días de la semana con horarios de referencia.
     */
    public void setReferenceSchedules(Map<DayOfWeek, ScheduleReference> daySchedules) {
        this.scheduleReferences = new ArrayList<>(daySchedules.values());
    }

    /**
     * Agrega o actualiza un horario de referencia en función del día de la semana.
     * Si ya existe un horario para ese día, lo actualiza; de lo contrario, lo agrega.
     *
     * @param scheduleReference Horario de referencia a agregar o actualizar.
     */
    public void addScheduleReference(ScheduleReference scheduleReference) {
        DayOfWeek day = scheduleReference.getDayOfWeek();
        for (int i = 0; i < scheduleReferences.size(); i++) {
            if (scheduleReferences.get(i).getDayOfWeek().equals(day)) {
                scheduleReferences.set(i, scheduleReference);
                return;
            }
        }
        scheduleReferences.add(scheduleReference);
    }

    /**
     * Agrega un día disponible con un horario de apertura y cierre.
     *
     * @param day          Día de la semana en el que el laboratorio estará disponible.
     * @param openingTime  Hora de apertura.
     * @param closingTime  Hora de cierre.
     */
    public void addAvailableDay(DayOfWeek day, LocalTime openingTime, LocalTime closingTime) {
        ScheduleReference reference = new ScheduleReference(day, openingTime, closingTime);
        addScheduleReference(reference);
    }


    /**
     * Agrega un recurso físico al laboratorio.
     *
     * @param physicalResource Recurso físico a agregar.
     */
    public void setPhysicalResource(Physical physicalResource) {
        this.physicalResource = physicalResource;
    }

    public void setSoftwareResource(Software softwareResource) {
        this.softwareResource = softwareResource;
    }

    // Getters y setters
    /**
     * Obtiene el nombre del laboratorio.
     *
     * @return Nombre del laboratorio.
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del laboratorio.
     *
     * @param name Nuevo nombre del laboratorio.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene la abreviatura del laboratorio.
     *
     * @return Abreviatura del laboratorio.
     */
    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * Establece la abreviatura del laboratorio.
     *
     * @param abbreviation Nueva abreviatura del laboratorio.
     */
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    /**
     * Obtiene la capacidad total del laboratorio.
     *
     * @return Capacidad total.
     */
    public int getTotalCapacity() {
        return totalCapacity;
    }

    /**
     * Establece la capacidad total del laboratorio.
     *
     * @param totalCapacity Nueva capacidad total del laboratorio.
     */
    public void setTotalCapacity(int totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    /**
     * Obtiene la ubicación del laboratorio.
     *
     * @return Ubicación del laboratorio.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Establece la ubicación del laboratorio.
     *
     * @param location Nueva ubicación del laboratorio.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Obtiene la lista de recursos físicos del laboratorio.
     *
     * @return Lista de recursos físicos.
     */
    public Physical getPhysicalResource() {
        return physicalResource;
    }

    public Software getSoftwareResource() {
        return softwareResource;
    }

    /**
     * Obtiene la lista de horarios de referencia del laboratorio.
     *
     * @return Lista de horarios de referencia.
     */
    public List<ScheduleReference> getScheduleReferences() {
        return scheduleReferences;
    }

    /**
     * Establece la lista de horarios de referencia del laboratorio.
     *
     * @param scheduleReferences Nueva lista de horarios de referencia.
     */
    public void setScheduleReferences(List<ScheduleReference> scheduleReferences) {
        this.scheduleReferences = scheduleReferences;
    }

    /**
     * Obtiene el horario de referencia para un día específico.
     *
     * @param day Día de la semana a consultar.
     * @return Horario de referencia correspondiente, o null si no existe.
     */
    public ScheduleReference getScheduleReferenceForDay(DayOfWeek day) {
        for (ScheduleReference ref : scheduleReferences) {
            if (ref.getDayOfWeek().equals(day)) {
                return ref;
            }
        }
        return null;
    }

    public void setScheduleReference(ScheduleReference scheduleReference){
        boolean updated = false;

        for (int i = 0; i < scheduleReferences.size(); i++) {
            if (scheduleReferences.get(i).getDayOfWeek().equals(scheduleReference.getDayOfWeek())) {
                scheduleReferences.set(i, scheduleReference);
                updated = true;
                break;
            }
        }

        if (!updated) {
            scheduleReferences.add(scheduleReference);
        }
    }


}
