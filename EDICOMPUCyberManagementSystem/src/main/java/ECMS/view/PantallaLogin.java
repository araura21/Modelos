package ECMS.view;

import ECMS.controller.GestorClientes;
import ECMS.model.Usuarios;
import javax.swing.*;
import java.awt.*;

public class PantallaLogin extends JFrame {
    private GestorClientes gestorClientes;
    private JTextField campoUsuario;
    private JPasswordField campoContrasena;

    public PantallaLogin() {
        this.gestorClientes = new GestorClientes();

        // Configurar el JFrame
        setTitle("Iniciar sesión");
        setSize(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear panel con fondo personalizado
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
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Agregar icono de usuario
        JLabel iconoUsuario = new JLabel(new ImageIcon("..\\Logotipo - EDICOMPU\\Logotipo - EDICOMPU.png"));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(iconoUsuario, gbc);

        // Campo de texto para el usuario
        JLabel etiquetaUsuario = new JLabel("Usuario");
        etiquetaUsuario.setForeground(new Color(173, 216, 230)); // Azul claro
        etiquetaUsuario.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(etiquetaUsuario, gbc);

        campoUsuario = new JTextField(20);
        campoUsuario.setBackground(new Color(240, 248, 255)); // Azul muy claro
        campoUsuario.setBorder(BorderFactory.createLineBorder(new Color(0, 149, 255)));
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(campoUsuario, gbc);

        // Campo de texto para la contraseña
        JLabel etiquetaContrasena = new JLabel("Contraseña");
        etiquetaContrasena.setForeground(new Color(173, 216, 230)); // Azul claro
        etiquetaContrasena.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(etiquetaContrasena, gbc);

        campoContrasena = new JPasswordField(20);
        campoContrasena.setBackground(new Color(240, 248, 255)); // Azul muy claro
        campoContrasena.setBorder(BorderFactory.createLineBorder(new Color(0, 149, 255)));
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(campoContrasena, gbc);

        // Botón de inicio de sesión
        JButton botonLogin = new JButton("INICIAR SESIÓN");
        botonLogin.setBackground(new Color(24, 2, 64)); // Azul brillante
        botonLogin.setForeground(Color.WHITE);
        botonLogin.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panel.add(botonLogin, gbc);

        // Acción del botón de login
        botonLogin.addActionListener(e -> {
            String usuario = campoUsuario.getText().toLowerCase();
            String contrasena = new String(campoContrasena.getPassword());

            if (contrasena.length() < 6) {
                JOptionPane.showMessageDialog(null, "La contraseña debe tener al menos 6 caracteres", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (autenticar(usuario, contrasena)) {
                JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso");
                new MenuPrincipal().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos", "Error de inicio de sesión", JOptionPane.ERROR_MESSAGE);
                campoContrasena.setText("");
                campoUsuario.setText("");
            }
        });

        // Botón de registro
        JButton botonRegistro = new JButton("Registrar");
        botonRegistro.setBackground(new Color(24, 2, 64)); // Azul brillante
        botonRegistro.setForeground(Color.WHITE);
        botonRegistro.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        panel.add(botonRegistro, gbc);

        // Acción del botón de registro
        botonRegistro.addActionListener(e -> {
            String usuario = campoUsuario.getText().toLowerCase();
            String contrasena = new String(campoContrasena.getPassword());

            if (usuario.isEmpty() || contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(null, "El usuario y la contraseña no pueden estar vacíos", "Error de registro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (contrasena.length() < 6) {
                JOptionPane.showMessageDialog(null, "La contraseña debe tener al menos 6 caracteres", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Usuarios nuevoUsuario = new Usuarios(usuario, contrasena);
            gestorClientes.agregarUsuarios(nuevoUsuario);
            JOptionPane.showMessageDialog(null, "Usuario registrado exitosamente");
        });

        // Agregar el panel al frame
        add(panel);
    }

    private boolean autenticar(String usuario, String contrasena) {
        Usuarios usuarioObj = gestorClientes.obtenerUsuarios(usuario.toLowerCase());
        return usuarioObj != null && usuarioObj.getContraseña().equals(contrasena);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PantallaLogin().setVisible(true));
    }
}
