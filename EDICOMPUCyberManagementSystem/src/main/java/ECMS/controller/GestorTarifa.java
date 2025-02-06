package ECMS.controller;

import ECMS.model.Tarifa;

public class GestorTarifa {
    private Tarifa tarifa;

    public GestorTarifa(double tarifaPorHora, double cargoMinimo) {
        this.tarifa = new Tarifa(tarifaPorHora, cargoMinimo);
    }

    public Tarifa obtenerTarifa() {
        return tarifa;
    }
}
