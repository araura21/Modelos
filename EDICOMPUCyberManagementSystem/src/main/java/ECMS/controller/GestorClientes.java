package ECMS.controller;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import ECMS.model.Cliente;
import ECMS.model.Usuarios;
import ECMS.model.ConexionBaseDatos;
import ECMS.model.Historial;
import ECMS.utils.GestorContraseñas;
import org.bson.Document;
import com.mongodb.client.model.Filters;
import java.time.Instant;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GestorClientes {
    private MongoCollection<Document> coleccionClientes;
    private MongoCollection<Document> coleccionUsuarioss;
    private MongoCollection<Document> coleccionHistorial;

    public GestorClientes() {
        MongoDatabase baseDatos = ConexionBaseDatos.getInstancia().getBaseDatos();
        this.coleccionClientes = baseDatos.getCollection("Customer"); // Colección para clientes
        this.coleccionUsuarioss = baseDatos.getCollection("Users"); // Colección para usuarios
        this.coleccionHistorial = baseDatos.getCollection("Historial"); //Coleccion para historial
    }

    public void agregarHistorial(Historial historial){
        Date horaInicio = Date.from(historial.getHoraInicio());
        Date horaFin = Date.from(historial.getHoraFin());

        Document documento = new Document("idcomputadora", historial.getIdComputadora())
                .append("horaInicio", horaInicio) // Guardamos como Date
                .append("horaFin", horaFin)       // Guardamos como Date
                .append("duracion", historial.getDuracion().toMillis())
                .append("costo", historial.getCosto())
                .append("idcliente", historial.getIdCliente());

        coleccionHistorial.insertOne(documento);
        System.out.println("Se agrego"+ historial.getIdCliente());
    }
    
    public List<Historial> obtenerHistorial(){
        List<Historial> historial = new ArrayList<>();
        for (Document documento : coleccionHistorial.find()) {
            Date horaInicioMongo = documento.getDate("horaInicio"); // Recupera la fecha como Date
            Date horaFinMongo = documento.getDate("horaFin");       // Recupera la fecha como Date
            Instant horaInicio = horaInicioMongo.toInstant(); // Convierte a Instant
            Instant horaFin = horaFinMongo.toInstant();         // Convierte a Instant
            double costo = documento.getDouble("costo"); // Asegúrate de que sea el tipo correcto

            historial.add(new Historial(
                    documento.getInteger("idcomputadora"),
                    horaInicio,
                    horaFin,
                    costo,
                    documento.getString("idcliente")
            ));
        }
        return historial;
    }

    
    public void agregarCliente(Cliente cliente) {
        Document documento = new Document("id", cliente.getId())
                .append("name", cliente.getNombre())
                .append("address", cliente.getDireccion())
                .append("phone", cliente.getTelefono())
                .append("email", cliente.getCorreo());
        coleccionClientes.insertOne(documento);
    }

    public void agregarUsuarios(Usuarios usuario) {
        String contrasenaEncriptada = GestorContraseñas.encriptar(usuario.getContraseña());
        Document documento = new Document("username", usuario.getNombreUsuario())
                .append("password", contrasenaEncriptada); // La contraseña ya está encriptada
        coleccionUsuarioss.insertOne(documento);
    }

    public Usuarios obtenerUsuarios(String usuario) {
        Document documento = coleccionUsuarioss.find(Filters.eq("username", usuario)).first();
        if (documento != null) {
            return new Usuarios(documento.getString("username"), GestorContraseñas.desencriptar(documento.getString("password")));
        }
        return null;
    }

    public List<Cliente> obtenerClientes() {
        List<Cliente> clientes = new ArrayList<>();
        for (Document documento : coleccionClientes.find()) {
            clientes.add(new Cliente(
                    documento.getString("id"),
                    documento.getString("name"),
                    documento.getString("address"),
                    documento.getString("phone"),
                    documento.getString("email")
            ));
        }
        return clientes;
    }

    public void actualizarCliente(Cliente cliente) {
        Document documentoActualizado = new Document("$set", new Document("name", cliente.getNombre())
                .append("address", cliente.getDireccion())
                .append("phone", cliente.getTelefono())
                .append("email", cliente.getCorreo()));
        coleccionClientes.updateOne(Filters.eq("id", cliente.getId()), documentoActualizado);
    }

    public void eliminarCliente(String id) {
        coleccionClientes.deleteOne(Filters.eq("id", id));
    }
    
    public boolean existeCliente(String id) {
        Document cliente = coleccionClientes.find(Filters.eq("id", id)).first();
        return cliente != null; // Si hay un cliente con ese ID, retorna true
    }
}
