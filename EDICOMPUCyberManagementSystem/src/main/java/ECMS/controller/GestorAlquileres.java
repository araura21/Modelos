package ECMS.controller;

import ECMS.model.Computadora;
import ECMS.model.Cliente;
import ECMS.model.Alquiler;
import ECMS.model.Tarifa;

import java.util.ArrayList;
import java.util.List;

public class GestorAlquileres {
    private List<Alquiler> alquileres;
    private List<Cliente> clientes;
    private List<Computadora> computadoras;
    private int siguienteIDAlquiler;

    public GestorAlquileres() {
        alquileres = new ArrayList<>();
        clientes = new ArrayList<>();
        computadoras = new ArrayList<>();
        siguienteIDAlquiler = 1;
        // Inicializar computadoras con una tarifa predeterminada
        Tarifa tarifaPredeterminada = new Tarifa(1.0, 0.15); // $1.0 por hora, $0.15 de cargo mínimo
        for (int i = 1; i <= 10; i++) {
            computadoras.add(new Computadora(i, tarifaPredeterminada));
        }
    }

    public void agregarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public void iniciarAlquiler(String idCliente, int idComputadora) {
        Cliente cliente = encontrarClientePorId(idCliente);
        Computadora computadora = encontrarComputadoraPorId(idComputadora);

        if (cliente != null && computadora != null && !computadora.estaActiva()) {
            computadora.iniciar();
            Alquiler renta = new Alquiler(siguienteIDAlquiler++, cliente, computadora);
            alquileres.add(renta);
            System.out.println("¡Computadora rentada con éxito!");
        } else {
            System.out.println("Error: ID de cliente/computadora inválido o la computadora ya está activa.");
        }
    }

    public void finalizarAlquiler(int idAlquiler) {
        Alquiler renta = encontrarAlquilerPorId(idAlquiler);
        if (renta != null) {
            renta.getComputadora().detener();
            double costoTotal = renta.getComputadora().calcularCosto();
            System.out.println("Alquiler finalizada. Pago total: $" + costoTotal);
        } else {
            System.out.println("Error: ID de renta no encontrado.");
        }
    }

    public void mostrarAlquileres() {
        for (Alquiler renta : alquileres) {
            System.out.println(renta);
        }
    }

    private Cliente encontrarClientePorId(String id) {
        for (Cliente cliente : clientes) {
            if (cliente.getId().equals(id)) {
                return cliente;
            }
        }
        return null;
    }

    private Computadora encontrarComputadoraPorId(int id) {
        for (Computadora computadora : computadoras) {
            if (computadora.getId() == id) {
                return computadora;
            }
        }
        return null;
    }

    private Alquiler encontrarAlquilerPorId(int id) {
        for (Alquiler renta : alquileres) {
            if (renta.getIdAlquiler() == id) {
                return renta;
            }
        }
        return null;
    }
}
