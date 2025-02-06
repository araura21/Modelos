/*
 * Haz clic en nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt para cambiar esta licencia
 * Haz clic en nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java para editar esta plantilla
 */
package ec.edu.espe.EDICOMPUCMS.controller;

import ECMS.controller.GestorAlquileres;
import ECMS.model.Cliente;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @autor KENNED
 */
public class GestorAlquileresTest {
    
    public GestorAlquileresTest() {
    }
    
    @BeforeAll
    public static void configurarClase() {
    }
    
    @AfterAll
    public static void finalizarClase() {
    }
    
    @BeforeEach
    public void configurar() {
    }
    
    @AfterEach
    public void finalizar() {
    }

    /**
     * Prueba del método agregarCliente, de la clase GestorAlquileres.
     */
    @Test
    public void probarAgregarCliente() {
        GestorAlquileres instancia = new GestorAlquileres();
        
        // Prueba 1: Agregar un cliente válido
        Cliente cliente1 = new Cliente("C001", "John Doe", "123 Main St", "555-1234", "john@example.com");
        instancia.agregarCliente(cliente1);
        
        // Prueba 2: Agregar otro cliente válido
        Cliente cliente2 = new Cliente("C002", "Jane Smith", "456 Elm St", "555-5678", "jane@example.com");
        instancia.agregarCliente(cliente2);
        
        // Prueba 3: Agregar un cliente con ID nulo
        Cliente cliente3 = new Cliente(null, "Cliente Inválido", "789 Oak St", "555-9012", "invalid@example.com");
        instancia.agregarCliente(cliente3);
        
        // Prueba 4: Agregar un cliente con ID vacío
        Cliente cliente4 = new Cliente("", "Cliente con ID vacío", "321 Pine St", "555-3456", "empty@example.com");
        instancia.agregarCliente(cliente4);
        
        // Prueba 5: Agregar un cliente con nombre nulo
        Cliente cliente5 = new Cliente("C005", null, "654 Birch St", "555-7890", "null@example.com");
        instancia.agregarCliente(cliente5);
        
        // Prueba 6: Agregar un cliente con nombre vacío
        Cliente cliente6 = new Cliente("C006", "", "987 Cedar St", "555-2345", "empty@example.com");
        instancia.agregarCliente(cliente6);
        
        // Prueba 7: Agregar un cliente duplicado
        Cliente cliente7 = new Cliente("C001", "Duplicate John Doe", "111 Duplicate St", "555-1111", "duplicate@example.com");
        instancia.agregarCliente(cliente7);
        
        // Prueba 8: Agregar una gran cantidad de clientes
        for (int i = 0; i < 1000; i++) {
            instancia.agregarCliente(new Cliente("C" + (1000 + i), "Cliente " + i, "Dirección " + i, "555-" + i, "customer" + i + "@example.com"));
        }
        
        // Prueba 9: Agregar un cliente con ID y nombre muy largos
        Cliente cliente9 = new Cliente("C" + "0".repeat(1000), "A".repeat(1000), "Dirección larga", "555-LONG", "long@example.com");
        instancia.agregarCliente(cliente9);
        
        // Prueba 10: Agregar un cliente nulo
        instancia.agregarCliente(null);
    }

    /**
     * Prueba del método iniciarAlquiler, de la clase GestorAlquileres.
     */
    @Test
    public void probarIniciarAlquiler() {
        GestorAlquileres instancia = new GestorAlquileres();
        
        // Agregar un cliente de prueba
        Cliente clientePrueba = new Cliente("C001", "Cliente de prueba", "Dirección de prueba", "555-TEST", "test@example.com");
        instancia.agregarCliente(clientePrueba);
        
        // Prueba 1: Iniciar un alquiler válido
        instancia.iniciarAlquiler("C001", 1);
        
        // Prueba 2: Intentar alquilar una computadora ya alquilada
        instancia.iniciarAlquiler("C001", 1);
        
        // Prueba 3: Intentar alquilar con un ID de cliente inválido
        instancia.iniciarAlquiler("C999", 2);
        
        // Prueba 4: Intentar alquilar con un ID de computadora inválido
        instancia.iniciarAlquiler("C001", 100);
        
        // Prueba 5: Intentar alquilar con ID de cliente nulo
        instancia.iniciarAlquiler(null, 3);
        
        // Prueba 6: Intentar alquilar con ID de cliente vacío
        instancia.iniciarAlquiler("", 4);
        
        // Prueba 7: Intentar alquilar con ID de computadora negativo
        instancia.iniciarAlquiler("C001", -1);
        
        // Prueba 8: Alquilar múltiples computadoras
        for (int i = 2; i <= 10; i++) {
            instancia.iniciarAlquiler("C001", i);
        }
        
        // Prueba 9: Intentar alquilar cuando todas las computadoras están ocupadas
        instancia.iniciarAlquiler("C001", 1);
        
        // Prueba 10: Intentar alquilar con un ID de cliente muy largo
        instancia.iniciarAlquiler("C" + "0".repeat(1000), 1);
    }

    /**
     * Prueba del método terminarAlquiler, de la clase GestorAlquileres.
     */
    @Test
    public void probarTerminarAlquiler() {
        GestorAlquileres instancia = new GestorAlquileres();
        
        // Agregar un cliente de prueba y comenzar algunos alquileres
        Cliente clientePrueba = new Cliente("C001", "Cliente de prueba", "Dirección de prueba", "555-TEST", "test@example.com");
        instancia.agregarCliente(clientePrueba);
        instancia.iniciarAlquiler("C001", 1);
        instancia.iniciarAlquiler("C001", 2);
        
        // Prueba 1: Terminar un alquiler válido
        instancia.finalizarAlquiler(1);
        
        // Prueba 2: Intentar terminar un alquiler ya terminado
        instancia.finalizarAlquiler(1);
        
        // Prueba 3: Intentar terminar un alquiler no existente
        instancia.finalizarAlquiler(100);
        
        // Prueba 4: Terminar múltiples alquileres
        instancia.iniciarAlquiler("C001", 3);
        instancia.iniciarAlquiler("C001", 4);
        instancia.finalizarAlquiler(2);
        instancia.finalizarAlquiler(3);
        instancia.finalizarAlquiler(4);
        
        // Prueba 5: Intentar terminar un alquiler con ID negativo
        instancia.finalizarAlquiler(-1);
        
        // Prueba 6: Intentar terminar un alquiler con ID cero
        instancia.finalizarAlquiler(0);
        
        // Prueba 7: Terminar un alquiler inmediatamente después de iniciarlo
        instancia.iniciarAlquiler("C001", 5);
        instancia.finalizarAlquiler(5);
        
        // Prueba 8: Iniciar y terminar múltiples alquileres en secuencia
        for (int i = 6; i <= 10; i++) {
            instancia.iniciarAlquiler("C001", i);
            instancia.finalizarAlquiler(i);
        }
        
        // Prueba 9: Intentar terminar todos los alquileres, incluidos los no existentes
        for (int i = 1; i <= 20; i++) {
            instancia.finalizarAlquiler(i);
        }
        
        // Prueba 10: Intentar terminar un alquiler con un ID muy grande
        instancia.finalizarAlquiler(Integer.MAX_VALUE);
    }

    /**
     * Prueba del método mostrarAlquileres, de la clase GestorAlquileres.
     */
    @Test
    public void probarMostrarAlquileres() {
        GestorAlquileres instancia = new GestorAlquileres();
        
        // Prueba 1: Mostrar alquileres cuando no hay alquileres
        instancia.mostrarAlquileres();
        
        // Agregar un cliente de prueba y comenzar algunos alquileres
        Cliente clientePrueba = new Cliente("C001", "Cliente de prueba", "Dirección de prueba", "555-TEST", "test@example.com");
        instancia.agregarCliente(clientePrueba);
        
        // Prueba 2: Mostrar alquileres con un alquiler activo
        instancia.iniciarAlquiler("C001", 1);
        instancia.mostrarAlquileres();
        
        // Prueba 3: Mostrar alquileres con múltiples alquileres activos
        instancia.iniciarAlquiler("C001", 2);
        instancia.iniciarAlquiler("C001", 3);
        instancia.mostrarAlquileres();
        
        // Prueba 4: Mostrar alquileres después de terminar un alquiler
        instancia.finalizarAlquiler(2);
        instancia.mostrarAlquileres();
        
        // Prueba 5: Mostrar alquileres con una mezcla de alquileres activos y terminados
        instancia.iniciarAlquiler("C001", 4);
        instancia.finalizarAlquiler(1);
        instancia.mostrarAlquileres();
        
        // Prueba 6: Mostrar alquileres después de terminar todos los alquileres
        instancia.finalizarAlquiler(3);
        instancia.finalizarAlquiler(4);
        instancia.mostrarAlquileres();
        
        // Prueba 7: Mostrar alquileres después de iniciar y terminar alquileres de inmediato
        instancia.iniciarAlquiler("C001", 5);
        instancia.finalizarAlquiler(5);
        instancia.mostrarAlquileres();
        
        // Prueba 8: Mostrar alquileres con una gran cantidad de alquileres
        for (int i = 1; i <= 100; i++) {
            instancia.iniciarAlquiler("C001", i % 10 + 1);
            if (i % 2 == 0) {
                instancia.finalizarAlquiler(i);
            }
        }
        instancia.mostrarAlquileres();
        
        // Prueba 9: Mostrar alquileres después de borrar todos los alquileres (si existe tal método)
        // Suponiendo que existe un método clearRentals()
        // instancia.clearRentals();
        instancia.mostrarAlquileres();
        
        // Prueba 10: Mostrar alquileres después de agregar un alquiler con un nombre de cliente muy largo
        Cliente clienteNombreLargo = new Cliente("C002", "A".repeat(1000), "Dirección de nombre largo", "555-LONG", "longname@example.com");
        instancia.agregarCliente(clienteNombreLargo);
        instancia.iniciarAlquiler("C002", 1);
        instancia.mostrarAlquileres();
    }
}
