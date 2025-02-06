package ECMS.view;

import javax.swing.*;
import java.awt.*;

public class PantallaBienvenida extends JFrame {
    public PantallaBienvenida() {
        setTitle("Bienvenido");
        setSize(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("..\\Logotipo - EDICOMPU\\fondoA.jpg");
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Logo (sin cambios)
        JLabel logoLabel = new JLabel();
        ImageIcon logoIcon = new ImageIcon("..\\Logotipo - EDICOMPU\\Logotipo - EDICOMPU.png");
        logoLabel.setIcon(logoIcon);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(logoLabel, gbc);

        // Nombre del sistema
        JLabel nombreSistema = new JLabel("EDICOMPU Sistema de Gestión de Ciber");
        nombreSistema.setFont(new Font("Georgia", Font.BOLD, 28));
        nombreSistema.setForeground(new Color(173, 216, 230)); // Color azul claro
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(nombreSistema, gbc);

        // Nombre del grupo
        JLabel nombreGrupo = new JLabel("Desarrollado por el GRUPO 5 :3");
        nombreGrupo.setFont(new Font("Calibri", Font.ITALIC, 18));
        nombreGrupo.setForeground(new Color(135, 206, 250)); // Color azul cielo
        gbc.gridy = 2;
        panel.add(nombreGrupo, gbc);

        // Botón de continuar
        JButton botonContinuar = new JButton("Continuar");
        botonContinuar.setFont(new Font("Arial", Font.BOLD, 16));
        botonContinuar.setForeground(new Color(25, 25, 112)); // Color azul oscuro
        botonContinuar.setBackground(new Color(173, 216, 230)); // Fondo azul claro
        botonContinuar.setPreferredSize(new Dimension(150, 40));
        botonContinuar.setFocusPainted(false);
        botonContinuar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(25, 25, 112), 2),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        botonContinuar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonContinuar.addActionListener(e -> {
            new PantallaLogin().setVisible(true);
            dispose();
        });
        botonContinuar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botonContinuar.setBackground(new Color(135, 206, 250)); // Azul más claro al pasar el ratón
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botonContinuar.setBackground(new Color(173, 216, 230)); // Volver al color original
            }
        });
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(botonContinuar, gbc);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PantallaBienvenida().setVisible(true));
    }
}
