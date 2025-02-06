package ECMS.model;

import ECMS.utils.GestorContraseñas;

public class Usuarios {
    private String nombreUsuario;
    private String contraseña;

    public Usuarios(String nombreUsuario, String contraseña) {
        this.nombreUsuario = nombreUsuario;
        this.contraseña = GestorContraseñas.encriptar(contraseña); // Encriptar la contraseña al crear el usuario
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContraseña() {
        return GestorContraseñas.desencriptar(contraseña); // Desencriptar la contraseña al obtenerla
    }

    public void setContraseña(String contraseña) {
        this.contraseña = GestorContraseñas.encriptar(contraseña); // Encriptar la contraseña al establecerla
    }
}
