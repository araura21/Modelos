
package ec.edu.espe.EDICOMPUCMS.controller;

import ECMS.controller.GestorCyber;
import ECMS.model.Historial;
import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GestorCyberTest {
    
    private GestorCyber instancia;

    @BeforeAll
    public static void configurarClase() {
        System.out.println("Inicio de pruebas de GestorCyber");
    }

    @AfterAll
    public static void finalizarClase() {
        System.out.println("Fin de pruebas de GestorCyber");
    }

    @BeforeEach
    public void configurar() {
        instancia = new GestorCyber();
    }

    @AfterEach
    public void finalizar() {
        instancia = null;
    }

    @Test
    public void probarAgregarOyenteDeActualizacionDeHistorial_nullOyente() {
        System.out.println("agregarEscuchadorActualizacionHistorial - nullOyente");
        Runnable oyente = null;
        instancia.agregarEscuchadorActualizacionHistorial(oyente);
        assertTrue(true);  // Verificar que no se lanza una excepción
    }

    @Test
    public void probarAgregarOyenteDeActualizacionDeHistorial_oyenteValido() {
        System.out.println("agregarEscuchadorActualizacionHistorial - oyenteValido");
        Runnable oyente = () -> System.out.println("Oyente ejecutado");
        instancia.agregarEscuchadorActualizacionHistorial(oyente);
        assertTrue(true);  // Verificar que no se lanza una excepción
    }

    @Test
    public void probarAgregarOyenteDeActualizacionDeHistorial_variosOyentes() {
        System.out.println("agregarEscuchadorActualizacionHistorial - variosOyentes");
        Runnable oyente1 = () -> System.out.println("Oyente 1 ejecutado");
        Runnable oyente2 = () -> System.out.println("Oyente 2 ejecutado");
        instancia.agregarEscuchadorActualizacionHistorial(oyente1);
        instancia.agregarEscuchadorActualizacionHistorial(oyente2);
        assertTrue(true);  // Verificar que no se lanza una excepción
    }

    @Test
    public void probarAgregarOyenteDeActualizacionDeHistorial_ejecucion() {
        System.out.println("agregarEscuchadorActualizacionHistorial - ejecucion");
        final boolean[] ejecutado = {false};
        Runnable oyente = () -> ejecutado[0] = true;
        instancia.agregarEscuchadorActualizacionHistorial(oyente);
        instancia.iniciarComputadora(1);
        instancia.detenerComputadora(1);
        assertTrue(ejecutado[0]);
    }

    @Test
    public void probarAgregarOyenteDeActualizacionDeHistorial_ordenDeEjecucion() {
        System.out.println("agregarEscuchadorActualizacionHistorial - ordenDeEjecucion");
        List<String> ordenDeEjecucion = new ArrayList<>();
        Runnable oyente1 = () -> ordenDeEjecucion.add("Oyente 1");
        Runnable oyente2 = () -> ordenDeEjecucion.add("Oyente 2");
        instancia.agregarEscuchadorActualizacionHistorial(oyente1);
        instancia.agregarEscuchadorActualizacionHistorial(oyente2);
        instancia.iniciarComputadora(1);
        instancia.detenerComputadora(1);
        assertEquals(List.of("Oyente 1", "Oyente 2"), ordenDeEjecucion);
    }

    @Test
    public void probarIniciarComputadora_idValido() {
        System.out.println("iniciarComputadora - idValido");
        int id = 1;
        instancia.iniciarComputadora(id);
        assertTrue(instancia.estaComputadoraActiva(id));
    }

    @Test
    public void probarIniciarComputadora_idInvalido() {
        System.out.println("iniciarComputadora - idInvalido");
        int id = 11;
        instancia.iniciarComputadora(id);
        assertFalse(instancia.estaComputadoraActiva(id));
    }

    @Test
    public void probarIniciarComputadora_yaActiva() {
        System.out.println("iniciarComputadora - yaActiva");
        int id = 1;
        instancia.iniciarComputadora(id);
        instancia.iniciarComputadora(id);
        assertTrue(instancia.estaComputadoraActiva(id));
    }

    @Test
    public void probarIniciarComputadora_todasLasComputadoras() {
        System.out.println("iniciarComputadora - todasLasComputadoras");
        for (int id = 1; id <= 10; id++) {
            instancia.iniciarComputadora(id);
            assertTrue(instancia.estaComputadoraActiva(id));
        }
    }

    @Test
    public void probarIniciarComputadora_computadorasAlternadas() {
        System.out.println("iniciarComputadora - computadorasAlternadas");
        for (int id = 1; id <= 10; id += 2) {
            instancia.iniciarComputadora(id);
            assertTrue(instancia.estaComputadoraActiva(id));
        }
        for (int id = 2; id <= 10; id += 2) {
            assertFalse(instancia.estaComputadoraActiva(id));
        }
    }

    @Test
    public void probarDetenerComputadora_idValido() {
        System.out.println("detenerComputadora - idValido");
        int id = 1;
        instancia.iniciarComputadora(id);
        double costo = instancia.detenerComputadora(id);
        assertFalse(instancia.estaComputadoraActiva(id));
        assertTrue(costo > 0);
    }

    @Test
    public void probarDetenerComputadora_idInvalido() {
        System.out.println("detenerComputadora - idInvalido");
        int id = 11;
        double costo = instancia.detenerComputadora(id);
        assertEquals(0.0, costo);
    }

    @Test
    public void probarDetenerComputadora_noIniciada() {
        System.out.println("detenerComputadora - noIniciada");
        int id = 1;
        double costo = instancia.detenerComputadora(id);
        assertEquals(0.0, costo);
    }

    @Test
    public void probarDetenerComputadora_variosDetenidos() {
        System.out.println("detenerComputadora - variosDetenidos");
        int id = 1;
        instancia.iniciarComputadora(id);
        instancia.detenerComputadora(id);
        double costo = instancia.detenerComputadora(id);
        assertEquals(0.0, costo);
    }

    @Test
    public void probarDetenerComputadora_computadorasAlternadas() {
        System.out.println("detenerComputadora - computadorasAlternadas");
        for (int id = 1; id <= 10; id += 2) {
            instancia.iniciarComputadora(id);
        }
        for (int id = 1; id <= 10; id += 2) {
            double costo = instancia.detenerComputadora(id);
            assertTrue(costo > 0);
        }
    }

    @Test
    public void probarEsComputadoraActiva_activa() {
        System.out.println("estaComputadoraActiva - activa");
        int id = 1;
        instancia.iniciarComputadora(id);
        assertTrue(instancia.estaComputadoraActiva(id));
    }

    @Test
    public void probarEsComputadoraActiva_inactiva() {
        System.out.println("estaComputadoraActiva - inactiva");
        int id = 1;
        assertFalse(instancia.estaComputadoraActiva(id));
    }

    @Test
    public void probarEsComputadoraActiva_idInvalido() {
        System.out.println("estaComputadoraActiva - idInvalido");
        int id = 11;
        assertFalse(instancia.estaComputadoraActiva(id));
    }

    @Test
    public void probarEsComputadoraActiva_despuesDeDetener() {
        System.out.println("estaComputadoraActiva - despuesDeDetener");
        int id = 1;
        instancia.iniciarComputadora(id);
        instancia.detenerComputadora(id);
        assertFalse(instancia.estaComputadoraActiva(id));
    }

    @Test
    public void probarEsComputadoraActiva_variasComputadoras() {
        System.out.println("estaComputadoraActiva - variasComputadoras");
        for (int id = 1; id <= 5; id++) {
            instancia.iniciarComputadora(id);
        }
        for (int id = 1; id <= 5; id++) {
            assertTrue(instancia.estaComputadoraActiva(id));
        }
        for (int id = 6; id <= 10; id++) {
            assertFalse(instancia.estaComputadoraActiva(id));
        }
    }
}
