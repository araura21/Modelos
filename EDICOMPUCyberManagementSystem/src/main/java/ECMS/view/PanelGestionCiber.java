package ECMS.view;

import ECMS.controller.GestorCyber;
import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import java.text.DecimalFormat;

public class PanelGestionCiber extends JPanel {
    
    private GestorCyber gestorCiber;
    private JPanel cuadrillaComputadoras;
    private JTextArea registroActividad;
    private Timer temporizador;

    private static final Color COLOR_FONDO = new Color(240, 240, 240);
    private static final Color COLOR_ACENTO = new Color(70, 130, 180);
    private static final Color COLOR_BOTON_INICIAR = new Color(100, 180, 100);
    private static final Color COLOR_BOTON_PARAR = new Color(180, 100, 100);
    private static final Font FUENTE_TITULO = new Font("Arial", Font.BOLD, 24);
    private static final Font FUENTE_REGULAR = new Font("Arial", Font.PLAIN, 12);

    public PanelGestionCiber(GestorCyber gestorCiber) {
        this.gestorCiber = gestorCiber;
        gestorCiber = new GestorCyber();
        setLayout(new BorderLayout());
        setBackground(COLOR_FONDO);
        
        initComponents();
        iniciarTemporizador();
    }

    private void initComponents() {
        // Título
        JLabel etiquetaTitulo = crearEtiquetaEstilo("Gestión de Ciber", FUENTE_TITULO, COLOR_ACENTO);
        add(etiquetaTitulo, BorderLayout.NORTH);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(COLOR_FONDO);
        add(panelPrincipal, BorderLayout.CENTER);

        // Cuadrilla de computadoras
        cuadrillaComputadoras = new JPanel(new GridLayout(2, 5, 10, 10));
        cuadrillaComputadoras.setBackground(COLOR_FONDO);
        panelPrincipal.add(cuadrillaComputadoras, BorderLayout.CENTER);

        // Registro de actividad
        registroActividad = new JTextArea(5, 30);
        registroActividad.setEditable(false);
        registroActividad.setFont(FUENTE_REGULAR);
        registroActividad.setForeground(COLOR_ACENTO);
        JScrollPane scrollPane = new JScrollPane(registroActividad);
        panelPrincipal.add(scrollPane, BorderLayout.SOUTH);

        inicializarCuadrillaComputadoras();
    }

    private JLabel crearEtiquetaEstilo(String texto, Font fuente, Color color) {
        JLabel etiqueta = new JLabel(texto, SwingConstants.CENTER);
        etiqueta.setFont(fuente);
        etiqueta.setForeground(color);
        return etiqueta;
    }

    private void inicializarCuadrillaComputadoras() {
        for (int i = 1; i <= 10; i++) {
            JPanel panelComputadora = crearPanelComputadora(i);
            cuadrillaComputadoras.add(panelComputadora);
        }
    }

    private JPanel crearPanelComputadora(int id) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(COLOR_ACENTO, 1));
        panel.putClientProperty("idComputadora", id);

        JLabel etiquetaIcono = new JLabel(new ImageIcon("C:\\Users\\PACO\\Downloads\\CODE312\\CODE\\EDICOMPUCyberManagementSystem(Maven)\\Logotipo - EDICOMPU\\Compu.png"));
        panel.add(etiquetaIcono, BorderLayout.CENTER);

        JLabel etiquetaEstado = crearEtiquetaEstilo("Inactiva", FUENTE_REGULAR, Color.RED);
        panel.add(etiquetaEstado, BorderLayout.NORTH);

        JLabel etiquetaTiempo = crearEtiquetaEstilo("00:00:00", FUENTE_REGULAR, Color.BLACK);
        panel.add(etiquetaTiempo, BorderLayout.WEST);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        panelBotones.setBackground(Color.WHITE);
        JButton botonIniciar = crearBotonEstilo("Iniciar", COLOR_BOTON_INICIAR);
        JButton botonDetener = crearBotonEstilo("Detener", COLOR_BOTON_PARAR);

        botonIniciar.addActionListener(e -> iniciarComputadora(id, panel, etiquetaEstado, etiquetaTiempo, botonIniciar, botonDetener));
        botonDetener.addActionListener(e -> detenerComputadora(id, panel, etiquetaEstado, etiquetaTiempo, botonIniciar, botonDetener));

        panelBotones.add(botonIniciar);
        panelBotones.add(botonDetener);
        panel.add(panelBotones, BorderLayout.SOUTH);

        return panel;
    }

    private JButton crearBotonEstilo(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(FUENTE_REGULAR);
        boton.setForeground(Color.WHITE);
        boton.setBackground(color);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        return boton;
    }

    private void iniciarComputadora(int id, JPanel panelComputadora, JLabel etiquetaEstado, JLabel etiquetaTiempo, JButton botonIniciar, JButton botonDetener) {
        gestorCiber.iniciarComputadora(id);
        etiquetaEstado.setText("Activa");
        etiquetaEstado.setForeground(COLOR_BOTON_INICIAR);
        botonIniciar.setEnabled(false);
        botonDetener.setEnabled(true);
        actualizarRegistro("Computadora " + id + " iniciada.");
    }

    private void detenerComputadora(int id, JPanel panelComputadora, JLabel etiquetaEstado, JLabel etiquetaTiempo, JButton botonIniciar, JButton botonDetener) {
        String tiempoActivo = gestorCiber.obtenerTiempoActivoComputadora(id);
        double costo = gestorCiber.detenerComputadora(id);
        etiquetaEstado.setText("Inactiva");
        etiquetaEstado.setForeground(Color.RED);
        etiquetaTiempo.setText("00:00:00");
        botonIniciar.setEnabled(true);
        botonDetener.setEnabled(false);
        
        DecimalFormat df = new DecimalFormat("#.##");
        actualizarRegistro("Computadora " + id + " detenida. Tiempo activo: " + tiempoActivo + ", Costo: $" + df.format(costo));
    }

    private void actualizarRegistro(String mensaje) {
        registroActividad.append(mensaje + "\n");
        registroActividad.setCaretPosition(registroActividad.getDocument().getLength());
    }

    private void iniciarTemporizador() {
        temporizador = new Timer();
        temporizador.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> actualizarEstadoComputadoras());
            }
        }, 0, 1000);
    }

    private void actualizarEstadoComputadoras() {
        for (Component componente : cuadrillaComputadoras.getComponents()) {
            if (componente instanceof JPanel) {
                JPanel panelComputadora = (JPanel) componente;
                int id = (int) panelComputadora.getClientProperty("idComputadora");
                JLabel etiquetaEstado = (JLabel) panelComputadora.getComponent(1);
                JLabel etiquetaTiempo = (JLabel) panelComputadora.getComponent(2);

                if (gestorCiber.estaComputadoraActiva(id)) {
                    etiquetaEstado.setText("Activa");
                    etiquetaEstado.setForeground(COLOR_BOTON_INICIAR);
                    etiquetaTiempo.setText(gestorCiber.obtenerTiempoActivoComputadora(id));
                } else {
                    etiquetaEstado.setText("Inactiva");
                    etiquetaEstado.setForeground(Color.RED);
                }
            }
        }
    }
}
