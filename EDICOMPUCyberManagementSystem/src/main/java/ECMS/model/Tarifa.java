package ECMS.model;

import java.time.Duration;

public class Tarifa {
    private double tarifaPorHora;
    private double cargoMinimo;

    public Tarifa(double tarifaPorHora, double cargoMinimo) {
        this.tarifaPorHora = tarifaPorHora;
        this.cargoMinimo = cargoMinimo;
    }

    public double calcularCosto(Duration duracion) {
        double horas = duracion.toMinutes() / 60.0;
        double costo = horas * tarifaPorHora;
        return Math.max(costo, cargoMinimo);
    }

    public double getTarifaPorHora() {
        return tarifaPorHora;
    }

    public void setTarifaPorHora(double tarifaPorHora) {
        this.tarifaPorHora = tarifaPorHora;
    }

    public double getCargoMinimo() {
        return cargoMinimo;
    }

    public void setCargoMinimo(double cargoMinimo) {
        this.cargoMinimo = cargoMinimo;
    }
}
