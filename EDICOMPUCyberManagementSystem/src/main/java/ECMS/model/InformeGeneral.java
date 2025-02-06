package ECMS.model;

import java.util.ArrayList;
import java.util.List;

public class InformeGeneral {
    private static int contadorInformes = 0;
    private int idInforme;
    private List<Cliente> clientes;
    private List<Computadora> computadoras;
    private double gananciasTotales;

    public InformeGeneral() {
        this.idInforme = ++contadorInformes;
        this.clientes = new ArrayList<>();
        this.computadoras = new ArrayList<>();
        this.gananciasTotales = 0.0;
    }

    public int getIdInforme() {
        return idInforme;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Computadora> getComputadoras() {
        return computadoras;
    }

    public void setComputadoras(List<Computadora> computadoras) {
        this.computadoras = computadoras;
    }

    public double getGananciasTotales() {
        return gananciasTotales;
    }

    public void setGananciasTotales(double gananciasTotales) {
        this.gananciasTotales = gananciasTotales;
    }

    public void agregarCliente(Cliente cliente) {
        this.clientes.add(cliente);
    }

    public void agregarComputadora(Computadora computadora) {
        this.computadoras.add(computadora);
    }

    public void agregarAGanancias(double ganancias) {
        this.gananciasTotales += ganancias;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("----- Informe ").append(idInforme).append(" -----\n");

        sb.append("Clientes\n");
        for (Cliente cliente : clientes) {
            sb.append(cliente.toString()).append("\n");
        }
        sb.append("\n");

        sb.append("Computadoras\n");
        for (Computadora computadora : computadoras) {
            sb.append(computadora.toString()).append("\n");
        }
        sb.append("\n");

        sb.append("Ganancias Totales: ").append(gananciasTotales).append("\n");

        return sb.toString();
    }
}
