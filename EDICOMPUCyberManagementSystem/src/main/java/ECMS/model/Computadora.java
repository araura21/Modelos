package ECMS.model;

import java.time.Duration;
import java.time.Instant;

public class Computadora {
    private int id;
    private boolean estaActiva;
    private Instant horaInicio;
    private Instant horaFin;
    private Tarifa tarifa;

    public Computadora(int id, Tarifa tarifa) {
        this.id = id;
        this.estaActiva = false;
        this.tarifa = tarifa;
    }

    public int getId() {
        return id;
    }

    public boolean estaActiva() {
        return estaActiva;
    }

    public void iniciar() {
        this.estaActiva = true;
        this.horaInicio = Instant.now();
        this.horaFin = null;
    }

    public void detener() {
        this.estaActiva = false;
        this.horaFin = Instant.now();
    }

    public Duration obtenerDuracionActiva() {
        if (horaInicio == null) {
            return Duration.ZERO;
        }

        if (estaActiva) {
            return Duration.between(horaInicio, Instant.now());
        } else {
            return Duration.between(horaInicio, horaFin);
        }
    }

    public double calcularCosto() {
        return tarifa.calcularCosto(obtenerDuracionActiva());
    }

    public void setActivo(boolean activo) {
        this.estaActiva = activo;
    }

    public Instant getHoraInicio() {
        return horaInicio;
    }

    public Instant getHoraFin() {
        return horaFin;
    }

    @Override
    public String toString() {
        return "Computadora{" + "id=" + id + ", estaActiva=" + estaActiva + ", horaInicio=" + horaInicio + ", horaFin=" + horaFin + '}';
    }
}
