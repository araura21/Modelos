package ECMS.view;

import ECMS.controller.GestorClientes;
import ECMS.controller.GestorCyber;
import ECMS.model.Cliente;
import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class PanelGestionCiber extends JPanel {
    private GestorClientes gestorClientes;
    private GestorCyber gestorCiber;
    private JPanel cuadrillaComputadoras;
    private JTextArea registroActividad;
    private Timer temporizador;
    private Map<Integer, String> computadorasClientes;

    private static final Color COLOR_FONDO = new Color(240, 240, 240);
    private static final Color COLOR_ACENTO = new Color(70, 130, 180);
    private static final Color COLOR_BOTON_INICIAR = new Color(100, 180, 100);
    private static final Color COLOR_BOTON_PARAR = new Color(180, 100, 100);
    private static final Font FUENTE_TITULO = new Font("Arial", Font.BOLD, 24);
    private static final Font FUENTE_REGULAR = new Font("Arial", Font.PLAIN, 12);

    public PanelGestionCiber(GestorCyber gestorCiber) {
        computadorasClientes = new HashMap<>(); // Mapa que guarda los clientes por computadora.
        gestorClientes = new GestorClientes();
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

        JLabel etiquetaIcono = new JLabel(new ImageIcon("..\\Logotipo - EDICOMPU\\Compu.png"));
        panel.add(etiquetaIcono, BorderLayout.CENTER);

        JLabel etiquetaEstado = crearEtiquetaEstilo("Inactiva", FUENTE_REGULAR, Color.RED);
        panel.add(etiquetaEstado, BorderLayout.NORTH);

        JLabel etiquetaTiempo = crearEtiquetaEstilo("00:00:00", FUENTE_REGULAR, Color.BLACK);
        panel.add(etiquetaTiempo, BorderLayout.WEST);

        // ComboBox para seleccionar cliente
        JComboBox<String> comboClientes = new JComboBox<>();
        comboClientes.addItem("Seleccionar Cliente");
        
        for (Cliente cliente : gestorClientes.obtenerClientes()) {
            comboClientes.addItem(cliente.getId() + " - " + cliente.getNombre());
        }
        comboClientes.setPreferredSize(new Dimension(110, 25)); // Tamaño similar al de los botones
        actualizarComboClientes(comboClientes, id);
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        panelBotones.setBackground(Color.WHITE);
        JButton botonIniciar = crearBotonEstilo("Iniciar", COLOR_BOTON_INICIAR);
        JButton botonDetener = crearBotonEstilo("Detener", COLOR_BOTON_PARAR);
        botonDetener.setEnabled(false);

        botonIniciar.addActionListener(e -> {
            String idcliente = (String) comboClientes.getSelectedItem();
            if (idcliente == null || idcliente.equals("Seleccionar Cliente")) {
                JOptionPane.showMessageDialog(panel, "Debe seleccionar un cliente antes de iniciar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            comboClientes.setEnabled(false); // Deshabilitar selección del cliente
            computadorasClientes.put(id, idcliente); // Registrar cliente para la computadora
            iniciarComputadora(id, panel, etiquetaEstado, etiquetaTiempo, botonIniciar, botonDetener);
        });

        botonDetener.addActionListener(e -> { 
            String idcliente = (String) comboClientes.getSelectedItem();
            computadorasClientes.remove(id); // Eliminar cliente cuando se detiene la computadora
            comboClientes.setEnabled(true); // Habilitar selección del cliente
            detenerComputadora(id, panel, etiquetaEstado, etiquetaTiempo, botonIniciar, botonDetener, idcliente);
        });

        panelBotones.add(comboClientes);
        panelBotones.add(botonIniciar);
        panelBotones.add(botonDetener);

        panel.add(panelBotones, BorderLayout.SOUTH);

        return panel;
    }
    
    private void actualizarComboClientes(JComboBox<String> comboClientes, int id) {
        // Deshabilitar a los clientes ya seleccionados en otras computadoras
        for (int i = 1; i <= 10; i++) {
            if (computadorasClientes.containsKey(i)) {
                String clienteSeleccionado = computadorasClientes.get(i);
                for (int j = 0; j < comboClientes.getItemCount(); j++) {
                    if (comboClientes.getItemAt(j).equals(clienteSeleccionado)) {
                        comboClientes.removeItemAt(j);
                        break;
                    }
                }
            }
        }
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

    private void detenerComputadora(int id, JPanel panelComputadora, JLabel etiquetaEstado, JLabel etiquetaTiempo, JButton botonIniciar, JButton botonDetener, String idcliente) {
        String tiempoActivo = gestorCiber.obtenerTiempoActivoComputadora(id);
        double costo = gestorCiber.detenerComputadora(id, idcliente);
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
