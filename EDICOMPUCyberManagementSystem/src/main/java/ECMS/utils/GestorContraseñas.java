package ECMS.utils;

public class GestorContraseñas {

    public static String encriptar(String contraseña) {
        StringBuilder encriptada = new StringBuilder();
        for (char ch : contraseña.toCharArray()) {
            encriptada.append((char) (ch + 1));
        }
        return encriptada.toString();
    }

    public static String desencriptar(String contraseña) {
        StringBuilder desencriptada = new StringBuilder();
        for (char ch : contraseña.toCharArray()) {
            desencriptada.append((char) (ch - 1));
        }
        return desencriptada.toString();
    }

}
