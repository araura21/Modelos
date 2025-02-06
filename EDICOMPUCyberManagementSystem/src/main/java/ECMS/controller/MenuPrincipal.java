package ECMS.controller;

import ECMS.view.PanelGestionClientes;
import ECMS.view.PanelGestionCiber;
import ECMS.view.PantallaLogin;
import ECMS.view.PanelPagos;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class MenuPrincipal extends JFrame {
    private GestorCyber gestorCyber;
    private JPanel panelPrincipal;
    private CardLayout diseñoTarjetas;
    private JPanel panelLateral;
    private JLabel etiquetaEstado;
    private JLabel etiquetaReloj;

    public MenuPrincipal() {
        gestorCyber = new GestorCyber();
        
        inicializarVentana();
        setJMenuBar(crearBarraMenu());
        add(crearPanelLateral(), BorderLayout.WEST);
        add(crearPanelPrincipal(), BorderLayout.CENTER);
        add(crearBarraEstado(), BorderLayout.SOUTH);

        diseñoTarjetas.show(panelPrincipal, "Inicio");
    }

    private void inicializarVentana() {
        setTitle("Sistema de Gestión Cyber EDICOMPU");
        setSize(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private JMenuBar crearBarraMenu() {
        JMenuBar barraMenu = new JMenuBar();
        barraMenu.setBackground(new Color(26, 35, 126));

        JMenu menuEditar = crearMenu("Editar", Color.WHITE, "Preferencias", e -> mostrarDialogo("Editar preferencias"));
        JMenu menuVer = crearMenu("Ver", Color.WHITE, "Pantalla Completa", e -> mostrarDialogo("Alternar pantalla completa"));
        JMenu menuAyuda = crearMenu("Ayuda", Color.WHITE, "Acerca de", e -> mostrarDialogo("Acerca de EDICOMPU CMS"));

        barraMenu.add(menuEditar);
        barraMenu.add(menuVer);
        barraMenu.add(menuAyuda);

        return barraMenu;
    }

    private JMenu crearMenu(String titulo, Color color, String etiquetaItem, ActionListener listener) {
        JMenu menu = new JMenu(titulo);
        menu.setForeground(color);
        agregarElementoMenu(menu, etiquetaItem, listener);
        return menu;
    }

    private void mostrarDialogo(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    private JPanel crearPanelLateral() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(26, 35, 126));

        String[] etiquetasBotones = {"Inicio", "Clientes", "Gestion Cyber", "Pagos"};
        for (String etiqueta : etiquetasBotones) {
            JButton boton = crearBotonLateral(etiqueta);
            boton.addActionListener(e -> diseñoTarjetas.show(panelPrincipal, etiqueta.replace(" ", "")));
            panel.add(boton);
            panel.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        JButton botonCerrarSesion = crearBotonLateral("Cerrar Sesión");
        botonCerrarSesion.addActionListener(e -> cerrarSesion());
        panel.add(Box.createVerticalGlue());
        panel.add(botonCerrarSesion);

        return panel;
    }

    private JButton crearBotonLateral(String etiqueta) {
        JButton boton = new JButton(etiqueta);
        boton.setAlignmentX(Component.LEFT_ALIGNMENT);
        boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, boton.getMinimumSize().height));
        boton.setBackground(new Color(63, 81, 181));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        return boton;
    }

    private JPanel crearPanelPrincipal() {
        diseñoTarjetas = new CardLayout();
        panelPrincipal = new JPanel(diseñoTarjetas) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                pintarFondoDegradado(g);
            }
        };

        panelPrincipal.add(crearPanelInicio(), "Inicio");
        panelPrincipal.add(crearPanelClientes(), "Clientes");
        panelPrincipal.add(crearPanelGestionCyber(), "GestionCyber");
        panelPrincipal.add(crearPanelPagos(), "Pagos");

        return panelPrincipal;
    }

    private void pintarFondoDegradado(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Color color1 = new Color(245, 245, 245);
        Color color2 = new Color(230, 230, 230);
        GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    private JPanel crearBarraEstado() {
        JPanel barraEstado = new JPanel(new BorderLayout());
        barraEstado.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        barraEstado.setBackground(new Color(245, 245, 245));

        etiquetaEstado = new JLabel("Listo");
        barraEstado.add(etiquetaEstado, BorderLayout.WEST);

        etiquetaReloj = new JLabel();
        iniciarReloj();
        barraEstado.add(etiquetaReloj, BorderLayout.EAST);

        return barraEstado;
    }

    private void iniciarReloj() {
        actualizarReloj();
        Timer temporizador = new Timer(1000, e -> actualizarReloj());
        temporizador.start();
    }

    private void actualizarReloj() {
        LocalDateTime ahora = LocalDateTime.now();
        etiquetaReloj.setText(ahora.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    private void agregarElementoMenu(JMenu menu, String etiqueta, ActionListener listener) {
        JMenuItem elementoMenu = new JMenuItem(etiqueta);
        elementoMenu.setForeground(Color.BLACK);
        elementoMenu.addActionListener(listener);
        menu.add(elementoMenu);
    }

    private JPanel crearPanelInicio() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Bienvenido al Sistema de Gestión Cyber EDICOMPU", SwingConstants.CENTER), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelClientes() {
        return new PanelGestionClientes();
    }

    private JPanel crearPanelGestionCyber() {
        return new PanelGestionCiber(gestorCyber);
    }

    private JPanel crearPanelPagos() {
        return new PanelPagos(gestorCyber);
    }

    private void cerrarSesion() {
        int confirmar = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea cerrar sesión?", "Confirmación de Cierre de Sesión",
                JOptionPane.YES_NO_OPTION);
        if (confirmar == JOptionPane.YES_OPTION) {
            new PantallaLogin().setVisible(true);
            dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new MenuPrincipal().setVisible(true);
        });
    }
}
