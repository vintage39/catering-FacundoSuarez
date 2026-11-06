package ar.edu.unahur.obj2.catering;
public interface Propuesta {
    Double getPrecio();
    String getDescripcion();
    Integer getCantidadServicios();
    Boolean incluyeServicio(String nombreServicio);
}