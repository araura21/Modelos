package ECMS.model;

import java.time.Duration;
import java.time.Instant;

public class Historial {
    private int idComputadora;
    private Instant horaInicio;
    private Instant horaFin;
    private Duration duracion;
    private double costo;

    public Historial(int idComputadora, Instant horaInicio, Instant horaFin, double costo) {
        this.idComputadora = idComputadora;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.duracion = Duration.between(horaInicio, horaFin);
        this.costo = costo;
    }

    public int getIdComputadora() {
        return idComputadora;
    }

    public Instant getHoraInicio() {
        return horaInicio;
    }

    public Instant getHoraFin() {
        return horaFin;
    }

    public Duration getDuracion() {
        return duracion;
    }

    public double getCosto() {
        return costo;
    }

    @Override
    public String toString() {
        long horas = duracion.toHours();
        long minutos = duracion.toMinutes() % 60;
        return "Computadora " + idComputadora + ": " + horas + " horas " + minutos + " minutos, Costo: $" + costo;
    }
}
