package ECMS.controller;

import ECMS.model.Computadora;
import ECMS.model.Historial;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class GestorCyber {
    private GestorEscuchadores gestorEscuchadores;    
    private List<Computadora> computadoras;
    private GestorTarifa gestorTarifas;
    private GestorHistorial gestorHistorial;

    public GestorCyber() {
        gestorTarifas = new GestorTarifa(1.00, 0.15);  // 1 dólar por hora, tarifa mínima 15 centavos
        computadoras = new ArrayList<>();
        gestorHistorial = new GestorHistorial();
        gestorEscuchadores = new GestorEscuchadores();
        inicializarComputadoras();
    }

    private void inicializarComputadoras() {
        for (int i = 1; i <= 10; i++) {
            computadoras.add(new Computadora(i, gestorTarifas.obtenerTarifa()));
        }
    }

    public void agregarEscuchadorActualizacionHistorial(Runnable escuchador) {
        gestorEscuchadores.agregarEscuchadorActualizacionHistorial(escuchador);
    }

    private void notificarEscuchadoresActualizacionHistorial() {
        gestorEscuchadores.notificarEscuchadoresActualizacionHistorial();
    }

    public void iniciarComputadora(int id) {
        Computadora computadora = obtenerComputadora(id);
        if (computadora != null && !computadora.estaActiva()) {
            computadora.iniciar();
        }
    }

    public double detenerComputadora(int id, String idcliente) {
        Computadora computadora = obtenerComputadora(id);
        if (computadora != null && computadora.estaActiva()) {
            computadora.detener();
            double costo = computadora.calcularCosto();
            gestorHistorial.agregarEntradaHistorial(computadora, costo, idcliente);
            notificarEscuchadoresActualizacionHistorial();
            return costo;
        }
        return 0.0;
    }

    public boolean estaComputadoraActiva(int id) {
        Computadora computadora = obtenerComputadora(id);
        return computadora != null && computadora.estaActiva();
    }

    public String obtenerTiempoActivoComputadora(int id) {
        Computadora computadora = obtenerComputadora(id);
        return (computadora != null) ? formatearDuracion(computadora.obtenerDuracionActiva()) : "00:00:00";
    }

    private String formatearDuracion(Duration duracion) {
        long horas = duracion.toHours();
        long minutos = duracion.toMinutesPart();
        long segundos = duracion.toSecondsPart();
        return String.format("%02d:%02d:%02d", horas, minutos, segundos);
    }

    public double obtenerCostoComputadora(int id) {
        Computadora computadora = obtenerComputadora(id);
        return (computadora != null) ? computadora.calcularCosto() : 0.0;
    }

    private Computadora obtenerComputadora(int id) {
        return computadoras.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Historial> obtenerHistorial() {
        return gestorHistorial.obtenerHistorial();
    }
}
