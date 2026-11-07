package ar.edu.unahur.obj2.catering;

public class ModificarPrecioServicio implements Comando {
    private final Servicio servicio;
    private final Double nuevoPrecio;
    private Double precioAnterior;

    public ModificarPrecioServicio(Servicio servicio, Double nuevoPrecio) {
        this.servicio = servicio;
        this.nuevoPrecio = nuevoPrecio;
    }

    @Override
    public void ejecutar() {
        this.precioAnterior = servicio.getPrecio();
        servicio.setPrecioBase(nuevoPrecio);
    }

    @Override
    public void deshacer() {
        if (precioAnterior != null) {
            servicio.setPrecioBase(precioAnterior);
        }
    }
}