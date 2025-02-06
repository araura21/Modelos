package ECMS.view;

import ECMS.controller.GestorClientes;
import ECMS.model.Usuarios;
import ECMS.utils.GestorContraseñas;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login {

    private GestorClientes gestorClientes;

    public Login() {
        this.gestorClientes = new GestorClientes();
    }

    public boolean autenticar(String nombreUsuario, String contrasena) {
        Usuarios usuario = gestorClientes.obtenerUsuarios(nombreUsuario);
        if (usuario != null && GestorContraseñas.desencriptar(usuario.getContraseña()).equals(contrasena)) {
            return true;
        }
        return false;
    }

    public boolean mostrarMenuLogin() {
        JTextField campoUsuario = new JTextField();
        JPasswordField campoContrasena = new JPasswordField();

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Usuario"));
        panel.add(campoUsuario);
        panel.add(new JLabel("Contraseña: "));
        panel.add(campoContrasena);

        int opcion = JOptionPane.showConfirmDialog(null, panel, "Iniciar sesión",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (opcion == JOptionPane.OK_OPTION) {
            String nombreUsuario = campoUsuario.getText();
            char[] contrasenaArray = campoContrasena.getPassword();
            String contrasena = new String(contrasenaArray);

            if (autenticar(nombreUsuario, contrasena)) {
                JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Inicio de sesión cancelado");
            return false;
        }
    }
}
