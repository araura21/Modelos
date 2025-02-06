
package ec.edu.espe.EDICOMPUCMS.controller;

import ECMS.controller.GestorClientes;
import ECMS.model.Cliente;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GestorClientesTest {
    
    public GestorClientesTest() {
    }
    
    @BeforeAll
    public static void configurarClase() {
    }
    
    @AfterAll
    public static void destruirClase() {
    }
    
    @BeforeEach
    public void configurar() {
    }
    
    @AfterEach
    public void destruir() {
    }

    /**
     * Prueba del método agregarCliente, de la clase GestorClientes.
     */
    @Test
public void testAgregarCliente() {
    GestorClientes instancia = new GestorClientes();
    
    // Prueba 1: Añadir un cliente válido
    Cliente cliente1 = new Cliente("C001", "Juan Pérez", "123 Calle Principal", "555-1234", "juan@ejemplo.com");
    instancia.agregarCliente(cliente1);
    assertTrue(instancia.obtenerClientes().stream().anyMatch(c -> c.getId().equals("C001")));
    
    // Prueba 2: Añadir otro cliente válido
    Cliente cliente2 = new Cliente("C002", "Ana López", "456 Calle Secundaria", "555-5678", "ana@ejemplo.com");
    instancia.agregarCliente(cliente2);
    assertTrue(instancia.obtenerClientes().stream().anyMatch(c -> c.getId().equals("C002")));
    
    // Prueba 3: Añadir un cliente con ID nulo (debería lanzar excepción)
    Cliente cliente3 = new Cliente(null, "Cliente Inválido", "789 Calle Roble", "555-9012", "invalido@ejemplo.com");
    assertThrows(IllegalArgumentException.class, () -> instancia.agregarCliente(cliente3));
    
    // Prueba 4: Añadir un cliente con ID vacío (debería lanzar excepción)
    Cliente cliente4 = new Cliente("", "Cliente con ID vacío", "321 Calle Pino", "555-3456", "vacío@ejemplo.com");
    assertThrows(IllegalArgumentException.class, () -> instancia.agregarCliente(cliente4));
    
    // Prueba 5: Añadir un cliente con nombre nulo (debería lanzar excepción)
    Cliente cliente5 = new Cliente("C005", null, "654 Calle Abeto", "555-7890", "nulo@ejemplo.com");
    assertThrows(IllegalArgumentException.class, () -> instancia.agregarCliente(cliente5));
    
    // Prueba 6: Añadir un cliente con nombre vacío
    Cliente cliente6 = new Cliente("C006", "", "987 Calle Cedro", "555-2345", "vacío@ejemplo.com");
    instancia.agregarCliente(cliente6);
    assertTrue(instancia.obtenerClientes().stream().anyMatch(c -> c.getId().equals("C006")));
    
    // Prueba 7: Añadir un cliente duplicado (debería lanzar excepción)
    Cliente cliente7 = new Cliente("C001", "Juan Pérez Duplicado", "111 Calle Duplicada", "555-1111", "duplicado@ejemplo.com");
    assertThrows(IllegalArgumentException.class, () -> instancia.agregarCliente(cliente7));
    
    // Prueba 8: Añadir un cliente con valores muy largos
    Cliente cliente8 = new Cliente("C008", "A".repeat(1000), "B".repeat(1000), "C".repeat(1000), "D".repeat(1000));
    instancia.agregarCliente(cliente8);
    assertTrue(instancia.obtenerClientes().stream().anyMatch(c -> c.getId().equals("C008")));
    
    // Prueba 9: Añadir un cliente con caracteres especiales
    Cliente cliente9 = new Cliente("C009", "Juan O'Doe", "123 Calle Principal", "555-LLAMAR", "juan+doe@ejemplo.com");
    instancia.agregarCliente(cliente9);
    assertTrue(instancia.obtenerClientes().stream().anyMatch(c -> c.getId().equals("C009")));
    
    // Prueba 10: Añadir un cliente nulo (debería lanzar excepción)
    assertThrows(NullPointerException.class, () -> instancia.agregarCliente(null));
}

    /**
     * Prueba del método eliminarCliente, de la clase GestorClientes.
     */
    @Test
public void testEliminarCliente() {
    GestorClientes instancia = new GestorClientes();
    
    // Configuración: Añadir algunos clientes
    instancia.agregarCliente(new Cliente("C001", "Juan Pérez", "123 Calle Principal", "555-1234", "juan@ejemplo.com"));
    instancia.agregarCliente(new Cliente("C002", "Ana López", "456 Calle Secundaria", "555-5678", "ana@ejemplo.com"));
    
    // Prueba 1: Eliminar un cliente existente
    instancia.eliminarCliente("C001");
    assertFalse(instancia.obtenerClientes().stream().anyMatch(c -> c.getId().equals("C001")));
    
    // Prueba 2: Eliminar un cliente no existente
    instancia.eliminarCliente("C999");
    assertEquals(1, instancia.obtenerClientes().size());
    
    // Prueba 3: Eliminar con ID nulo (debería lanzar excepción)
    assertThrows(IllegalArgumentException.class, () -> instancia.eliminarCliente(null));
    
    // Prueba 4: Eliminar con ID vacío
    instancia.eliminarCliente("");
    assertEquals(1, instancia.obtenerClientes().size());
    
    // Prueba 5: Eliminar el último cliente
    instancia.eliminarCliente("C002");
    assertTrue(instancia.obtenerClientes().isEmpty());
    
    // Prueba 6: Eliminar de una lista vacía
    instancia.eliminarCliente("C003");
    assertTrue(instancia.obtenerClientes().isEmpty());
    
    // Prueba 7: Eliminar con un ID muy largo
    instancia.agregarCliente(new Cliente("C" + "0".repeat(1000), "ID Largo", "Dirección", "Teléfono", "email@ejemplo.com"));
    instancia.eliminarCliente("C" + "0".repeat(1000));
    assertTrue(instancia.obtenerClientes().isEmpty());
    
    // Prueba 8: Eliminar varias veces
    instancia.agregarCliente(new Cliente("C003", "Prueba", "Dirección", "Teléfono", "email@ejemplo.com"));
    instancia.eliminarCliente("C003");
    instancia.eliminarCliente("C003");
    assertTrue(instancia.obtenerClientes().isEmpty());
    
    // Prueba 9: Eliminar con caracteres especiales en el ID
    instancia.agregarCliente(new Cliente("C#004", "Especial", "Dirección", "Teléfono", "email@ejemplo.com"));
    instancia.eliminarCliente("C#004");
    assertTrue(instancia.obtenerClientes().isEmpty());
    
    // Prueba 10: Intentar eliminar todos los clientes (si existe dicho método)
    instancia.agregarCliente(new Cliente("C005", "Prueba1", "Dirección1", "Teléfono1", "email1@ejemplo.com"));
    instancia.agregarCliente(new Cliente("C006", "Prueba2", "Dirección2", "Teléfono2", "email2@ejemplo.com"));
    instancia.eliminarCliente("C005");
    instancia.eliminarCliente("C006");
    assertTrue(instancia.obtenerClientes().isEmpty());
}

    /**
     * Prueba del método obtenerClientes, de la clase GestorClientes.
     */
    @Test
public void testObtenerClientes() {
    GestorClientes instancia = new GestorClientes();
    
    // Prueba 1: Obtener clientes de una lista vacía
    assertTrue(instancia.obtenerClientes().isEmpty());
    
    // Prueba 2: Obtener clientes después de añadir uno
    instancia.agregarCliente(new Cliente("C001", "Juan Pérez", "123 Calle Principal", "555-1234", "juan@ejemplo.com"));
    assertEquals(1, instancia.obtenerClientes().size());
    
    // Prueba 3: Obtener clientes después de añadir varios
    instancia.agregarCliente(new Cliente("C002", "Ana López", "456 Calle Secundaria", "555-5678", "ana@ejemplo.com"));
    instancia.agregarCliente(new Cliente("C003", "Bob Johnson", "789 Calle Roble", "555-9012", "bob@ejemplo.com"));
    assertEquals(3, instancia.obtenerClientes().size());
    
    // Prueba 4: Verificar detalles del cliente
    Cliente clienteRecuperado = instancia.obtenerClientes().stream().filter(c -> c.getId().equals("C001")).findFirst().orElse(null);
    assertNotNull(clienteRecuperado);
    assertEquals("Juan Pérez", clienteRecuperado.getNombre());
    
    // Prueba 5: Obtener clientes después de eliminar uno
    instancia.eliminarCliente("C002");
    assertEquals(2, instancia.obtenerClientes().size());
    
    // Prueba 6: Verificar el orden de la lista (si el orden se mantiene)
    List<Cliente> clientes = instancia.obtenerClientes();
    assertEquals("C001", clientes.get(0).getId());
    assertEquals("C003", clientes.get(1).getId());
    
    // Prueba 7: Obtener clientes después de actualizar uno
    Cliente clienteActualizado = new Cliente("C001", "Juan Actualizado", "Nueva Dirección", "Nuevo Teléfono", "nuevo@ejemplo.com");
    instancia.actualizarCliente(clienteActualizado);
    clienteRecuperado = instancia.obtenerClientes().stream().filter(c -> c.getId().equals("C001")).findFirst().orElse(null);
    assertNotNull(clienteRecuperado);
    assertEquals("Juan Actualizado", clienteRecuperado.getNombre());
    
    // Prueba 8: Obtener clientes después de añadir un cliente con valores muy largos
    instancia.agregarCliente(new Cliente("C004", "A".repeat(1000), "B".repeat(1000), "C".repeat(1000), "D".repeat(1000)));
    assertEquals(3, instancia.obtenerClientes().size());
    
    // Prueba 9: Obtener clientes después de añadir y eliminar varias veces
    for (int i = 5; i <= 10; i++) {
        instancia.agregarCliente(new Cliente("C00" + i, "Nombre" + i, "Dirección" + i, "Teléfono" + i, "email" + i + "@ejemplo.com"));
    }
    for (int i = 5; i <= 7; i++) {
        instancia.eliminarCliente("C00" + i);
    }
    assertEquals(6, instancia.obtenerClientes().size());
    
    // Prueba 10: Verificar que obtenerClientes devuelve una nueva lista (no una referencia a la lista interna)
    List<Cliente> listaClientes1 = instancia.obtenerClientes();
    List<Cliente> listaClientes2 = instancia.obtenerClientes();
    assertNotSame(listaClientes1, listaClientes2);
}

    /**
     * Prueba del método actualizarCliente, de la clase GestorClientes.
     */
    @Test
public void testActualizarCliente() {
    GestorClientes instancia = new GestorClientes();
    
    // Configuración: Añadir un cliente
    Cliente clienteOriginal = new Cliente("C001", "Juan Pérez", "123 Calle Principal", "555-1234", "juan@ejemplo.com");
    instancia.agregarCliente(clienteOriginal);
    
    // Prueba 1: Actualizar un cliente existente
    Cliente clienteActualizado = new Cliente("C001", "Juan Actualizado", "456 Calle Secundaria", "555-5678", "juan.actualizado@ejemplo.com");
    instancia.actualizarCliente(clienteActualizado);
    Cliente clienteRecuperado = instancia.obtenerClientes().stream().filter(c -> c.getId().equals("C001")).findFirst().orElse(null);
    assertNotNull(clienteRecuperado);
    assertEquals("Juan Actualizado", clienteRecuperado.getNombre());
    assertEquals("456 Calle Secundaria", clienteRecuperado.getDireccion());
    
    // Prueba 2: Actualizar un cliente no existente (debería añadir un nuevo cliente)
    Cliente nuevoCliente = new Cliente("C002", "Ana López", "789 Calle Roble", "555-9012", "ana@ejemplo.com");
    instancia.actualizarCliente(nuevoCliente);
    assertEquals(2, instancia.obtenerClientes().size());
    
    // Prueba 3: Actualizar con cliente nulo (debería lanzar excepción)
    assertThrows(NullPointerException.class, () -> instancia.actualizarCliente(null));
    
    // Prueba 4: Actualizar con ID nulo (debería lanzar excepción)
    Cliente clienteConIdNulo = new Cliente(null, "Inválido", "Dirección", "Teléfono", "email@ejemplo.com");
    assertThrows(IllegalArgumentException.class, () -> instancia.actualizarCliente(clienteConIdNulo));
    
    // Prueba 5: Actualizar con ID vacío
    Cliente clienteConIdVacío = new Cliente("", "ID vacío", "Dirección", "Teléfono", "email@ejemplo.com");
    instancia.actualizarCliente(clienteConIdVacío);
    assertEquals(3, instancia.obtenerClientes().size());
    
    // Prueba 6: Actualizar con nombre nulo
    Cliente clienteConNombreNulo = new Cliente("C001", null, "Dirección", "Teléfono", "email@ejemplo.com");
    instancia.actualizarCliente(clienteConNombreNulo);
    clienteRecuperado = instancia.obtenerClientes().stream().filter(c -> c.getId().equals("C001")).findFirst().orElse(null);
    assertNotNull(clienteRecuperado);
    assertNull(clienteRecuperado.getNombre());
    
    // Prueba 7: Actualizar con valores muy largos
    Cliente clienteConValoresLargos = new Cliente("C001", "A".repeat(1000), "B".repeat(1000), "C".repeat(1000), "D".repeat(1000));
    instancia.actualizarCliente(clienteConValoresLargos);
    clienteRecuperado = instancia.obtenerClientes().stream().filter(c -> c.getId().equals("C001")).findFirst().orElse(null);
    assertNotNull(clienteRecuperado);
    assertEquals("A".repeat(1000), clienteRecuperado.getNombre());
    
    // Prueba 8: Actualizar con caracteres especiales
    Cliente clienteConCaracteresEspeciales = new Cliente("C001", "Juan O'Doe", "123 Calle Principal", "555-LLAMAR", "juan+doe@ejemplo.com");
    instancia.actualizarCliente(clienteConCaracteresEspeciales);
    clienteRecuperado = instancia.obtenerClientes().stream().filter(c -> c.getId().equals("C001")).findFirst().orElse(null);
    assertNotNull(clienteRecuperado);
    assertEquals("Juan O'Doe", clienteRecuperado.getNombre());
    
    // Prueba 9: Actualizar un cliente sin cambios (debería mantener el mismo valor)
    instancia.actualizarCliente(clienteActualizado);
    clienteRecuperado = instancia.obtenerClientes().stream().filter(c -> c.getId().equals("C001")).findFirst().orElse(null);
    assertEquals("Juan Actualizado", clienteRecuperado.getNombre());
    
    // Prueba 10: Actualizar después de eliminar un cliente
    instancia.eliminarCliente("C001");
    instancia.actualizarCliente(clienteActualizado);
    clienteRecuperado = instancia.obtenerClientes().stream().filter(c -> c.getId().equals("C001")).findFirst().orElse(null);
    assertNotNull(clienteRecuperado);
    assertEquals("Juan Actualizado", clienteRecuperado.getNombre());
}
}
