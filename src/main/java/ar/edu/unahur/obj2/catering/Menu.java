package ar.edu.unahur.obj2.catering;

import ar.edu.unahur.obj2.catering.excepciones.DescuentoInvalidoException;
import java.util.ArrayList;
import java.util.List;

public class Menu implements Propuesta {
    private final String nombre;
    private Double porcentajeDescuento;
    private final List<Propuesta> componentes;

    public Menu(String nombre, Double porcentajeDescuento) {
        if (porcentajeDescuento < 0 || porcentajeDescuento > 100) {
            throw new DescuentoInvalidoException("El descuento debe estar entre 0 y 100");
        }
        this.nombre = nombre;
        this.porcentajeDescuento = porcentajeDescuento;
        this.componentes = new ArrayList<>();
    }

    public void agregarPropuesta(Propuesta propuesta) {
        this.componentes.add(propuesta);
    }

    @Override
    public Double getPrecio() {
        Double subtotal = componentes.stream()
                .mapToDouble(Propuesta::getPrecio)
                .sum();
        return subtotal * (1.0 - (porcentajeDescuento / 100.0));
    }

    @Override
    public String getDescripcion() {
        return this.nombre;
    }

    @Override
    public Integer getCantidadServicios() {
        return componentes.stream()
                .mapToInt(Propuesta::getCantidadServicios)
                .sum();
    }

    @Override
    public Boolean incluyeServicio(String nombreServicio) {
        return componentes.stream()
                .anyMatch(p -> p.incluyeServicio(nombreServicio));
    }



    public void setDescuento(Double nuevoDescuento) {
        if (nuevoDescuento < 0 || nuevoDescuento > 100) {
            throw new DescuentoInvalidoException("El descuento debe estar entre 0 y 100");
        }
        this.porcentajeDescuento = nuevoDescuento; 
    }

    public Double getDescuento() {
        return this.porcentajeDescuento; 
    }
}