package ar.edu.unahur.obj2.catering;

import ar.edu.unahur.obj2.catering.excepciones.*;

public class Servicio implements Propuesta {
    private final String nombre;
    private Double precio; 

    public Servicio(String nombre, Double precio) {
        if (precio < 0) {
            throw new PrecioNegativoException("El precio no puede ser negativo");
        }
        this.nombre = nombre;
        this.precio = precio;
    }

    @Override
    public Double getPrecio() {
        return this.precio;
    }

    @Override
    public String getDescripcion() {
        return this.nombre;
    }

    @Override
    public Integer getCantidadServicios() {
        return 1;
    }

    @Override
    public Boolean incluyeServicio(String nombreServicio) {
        return this.nombre.equalsIgnoreCase(nombreServicio);
    }

    public void setPrecioBase(Double nuevoPrecioBase) {
        if (nuevoPrecioBase < 0) {
            throw new PrecioNegativoException("El precio no puede ser negativo");
        }
        this.precio = nuevoPrecioBase;
    }
}
