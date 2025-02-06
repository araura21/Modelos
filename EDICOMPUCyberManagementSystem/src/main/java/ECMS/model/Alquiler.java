package ECMS.model;

public class Alquiler {
    private int idAlquiler;
    private Cliente cliente;
    private Computadora computadora;

    public Alquiler(int idAlquiler, Cliente cliente, Computadora computadora) {
        this.idAlquiler = idAlquiler;
        this.cliente = cliente;
        this.computadora = computadora;
    }

    public int getIdAlquiler() {
        return idAlquiler;
    }

    public void setIdAlquiler(int idAlquiler) {
        this.idAlquiler = idAlquiler;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Computadora getComputadora() {
        return computadora;
    }

    public void setComputadora(Computadora computadora) {
        this.computadora = computadora;
    }

    @Override
    public String toString() {
        return "Alquiler{" + "idAlquiler=" + idAlquiler + ", cliente=" + cliente + ", computadora=" + computadora + '}';
    }
}
