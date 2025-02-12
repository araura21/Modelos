package ECMS.controller;

import ECMS.model.Computadora;
import ECMS.model.Historial;

import java.util.ArrayList;
import java.util.List;

public class GestorHistorial {
    private List<Historial> historial = new ArrayList<>();
    private GestorClientes gestorClientes = new GestorClientes();

    public void agregarEntradaHistorial(Computadora computadora, double costo, String idcliente) {
        Historial entrada = new Historial(computadora.getId(), computadora.getHoraInicio(), computadora.getHoraFin(), costo, idcliente);
        //historial.add(entrada);
        gestorClientes.agregarHistorial(entrada);
    }

    public List<Historial> obtenerHistorial() {
        // Obtenemos el historial desde la base de datos a través de gestorClientes
        List<Historial> historialBaseDatos = gestorClientes.obtenerHistorial();
        
        // Creamos una nueva lista y agregamos primero los elementos de la base de datos
        List<Historial> historialTotal = new ArrayList<>(historialBaseDatos);
        
        // Luego agregamos los elementos de la lista en memoria
        historialTotal.addAll(historial);
        
        // Retornamos el historial combinado
        return historialTotal;
    }
}
