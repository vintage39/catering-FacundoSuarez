package ar.edu.unahur.obj2.catering;

// Justificacion: Contrato registra la compra en un instante
// No implementa Propuesta para evitar uso forzado
public class Contrato {
    private final String cliente;
    private final Double importe;
    private final String descripcion;

    public Contrato(String cliente, Propuesta propuesta) {
        this.cliente = cliente;
        this.importe = propuesta.getPrecio();
        this.descripcion = propuesta.getDescripcion();
    }

    public String getCliente() {
        return cliente;
    }

    public Double getImporte() {
        return importe;
    }

    public String getDescripcion() {
        return descripcion;
    }
}