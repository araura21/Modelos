package ECMS.controller;

import java.util.ArrayList;
import java.util.List;

public class GestorEscuchadores {
    private List<Runnable> escuchadoresActualizacionHistorial = new ArrayList<>();

    public void agregarEscuchadorActualizacionHistorial(Runnable escuchador) {
        escuchadoresActualizacionHistorial.add(escuchador);
    }

    public void notificarEscuchadoresActualizacionHistorial() {
        escuchadoresActualizacionHistorial.forEach(Runnable::run);
    }
}
