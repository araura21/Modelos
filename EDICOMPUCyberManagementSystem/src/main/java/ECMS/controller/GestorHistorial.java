package ECMS.controller;

import ECMS.model.Computadora;
import ECMS.model.Historial;

import java.util.ArrayList;
import java.util.List;

public class GestorHistorial {
    private List<Historial> historial = new ArrayList<>();

    public void agregarEntradaHistorial(Computadora computadora, double costo, String idcliente) {
        Historial entrada = new Historial(computadora.getId(), computadora.getHoraInicio(), computadora.getHoraFin(), costo, idcliente);
        historial.add(entrada);
    }

    public List<Historial> obtenerHistorial() {
        return new ArrayList<>(historial);
    }
}
