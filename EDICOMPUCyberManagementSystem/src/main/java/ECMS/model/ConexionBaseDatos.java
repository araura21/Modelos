package ECMS.model;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class ConexionBaseDatos {
    private static ConexionBaseDatos instancia;
    private MongoClient clienteMongo;
    private MongoDatabase baseDeDatos;

    private static final String CADENA_CONEXION = "mongodb+srv://rauraandrea:rauraandrea@cluster0.izygxtz.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
    private static final String NOMBRE_BASE_DATOS = "ECMSystem";

    private ConexionBaseDatos() {
        try {
            clienteMongo = MongoClients.create(CADENA_CONEXION);
            baseDeDatos = clienteMongo.getDatabase(NOMBRE_BASE_DATOS);
            System.out.println("Conexión a MongoDB exitosa");
        } catch (Exception e) {
            System.err.println("Error al conectar a MongoDB: " + e.getMessage());
            throw new RuntimeException("No se pudo conectar a MongoDB", e);
        }
    }

    public static synchronized ConexionBaseDatos getInstancia() {
        if (instancia == null) {
            instancia = new ConexionBaseDatos();
        }
        return instancia;
    }

    public MongoDatabase getBaseDatos() {
        return baseDeDatos;
    }

    public void cerrarConexion() {
        if (clienteMongo != null) {
            clienteMongo.close();
            System.out.println("Conexión a MongoDB cerrada");
        }
    }
}
