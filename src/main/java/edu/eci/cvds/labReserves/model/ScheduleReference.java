package edu.eci.cvds.labReserves.model;

import java.io.IOException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.LocalTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Representa un horario de referencia para un laboratorio, incluyendo el día de la semana,
 * la hora de apertura y la hora de cierre. También proporciona métodos para verificar
 * si un horario específico está dentro del rango permitido o si está disponible.
 */
public class ScheduleReference {

    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime openingTime;
    
    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime closingTime;
    
    @JsonSerialize(using = DayOfWeekSerializer.class)
    @JsonDeserialize(using = DayOfWeekDeserializer.class)
    private DayOfWeek dayOfWeek;
    //private List<Schedule> reservedTimes;

    /**
     * Constructor por defecto que inicializa el horario sin valores definidos.
     */
    public ScheduleReference() {
        this.openingTime = null;
        this.closingTime = null;
        this.dayOfWeek = null;
        //this.reservedTimes = new ArrayList<>();
    }

    /**
     * Constructor que permite definir un horario de referencia.
     *
     * @param dayOfWeek   Día de la semana en el que aplica el horario.
     * @param openingTime Hora de apertura.
     * @param closingTime Hora de cierre.
     */
    public ScheduleReference(DayOfWeek dayOfWeek, LocalTime openingTime, LocalTime closingTime) {
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.dayOfWeek = dayOfWeek;
        //this.reservedTimes = new ArrayList<>();
    }

    public boolean isWithinSchedule(Schedule schedule) {
        DayOfWeek scheduleDay = schedule.getDay();
        if (!dayOfWeek.equals(scheduleDay)) {
            return false;
        }
        return !schedule.getStartHour().isBefore(openingTime) && !schedule.getEndHour().isAfter(closingTime);
    }

    /**
     * Verifica si un horario dado está disponible en este horario de reserva.
     *
     * @param scheduleDay Horario a verificar.
     * @return true si el horario está disponible, false en caso contrario.
     */
    public boolean isAvailable(DayOfWeek scheduleDay, LocalTime scheduleStartTime, LocalTime scheduleEndTime) {

        boolean isDayAvailable = dayOfWeek.equals(scheduleDay);
        boolean isTimeWithinRange = !scheduleStartTime.isBefore(this.openingTime) && !scheduleEndTime.isAfter(this.closingTime);

        return isDayAvailable && isTimeWithinRange;
    }


    // Getters y setters
    /**
     * Obtiene la hora de apertura del laboratorio.
     *
     * @return Hora de apertura.
     */
    public LocalTime getOpeningTime() {
        return openingTime;
    }

    /**
     * Establece la hora de apertura del laboratorio.
     *
     * @param openingTime Nueva hora de apertura.
     */
    public void setOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }

    /**
     * Obtiene la hora de cierre del laboratorio.
     *
     * @return Hora de cierre.
     */
    public LocalTime getClosingTime() {
        return closingTime;
    }

    /**
     * Establece la hora de cierre del laboratorio.
     *
     * @param closingTime Nueva hora de cierre.
     */
    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }

    /**
     * Obtiene el día de la semana en el que aplica este horario.
     *
     * @return Día de la semana.
     */
    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * Establece el día de la semana en el que aplica este horario.
     *
     * @param dayOfWeek Nuevo día de la semana.
     */
    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    // Clases internas para la serialización y deserialización de LocalTime y DayOfWeek
    public static class LocalTimeSerializer extends JsonSerializer<LocalTime> {
        @Override
        public void serialize(LocalTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            if (value != null) {
                gen.writeString(value.toString());
            }
        }
    }

    public static class LocalTimeDeserializer extends JsonDeserializer<LocalTime> {
        @Override
        public LocalTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            String timeString = p.getValueAsString();
            return timeString != null ? LocalTime.parse(timeString) : null;
        }
    }
    
    public static class DayOfWeekSerializer extends JsonSerializer<DayOfWeek> {
        @Override
        public void serialize(DayOfWeek value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            if (value != null) {
                gen.writeString(value.name());
            }
        }
    }
    
    public static class DayOfWeekDeserializer extends JsonDeserializer<DayOfWeek> {
        @Override
        public DayOfWeek deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            String dayString = p.getValueAsString();
            return dayString != null ? DayOfWeek.valueOf(dayString) : null;
        }
    }
}