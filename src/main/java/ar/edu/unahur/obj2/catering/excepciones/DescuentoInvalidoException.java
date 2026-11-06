package ar.edu.unahur.obj2.catering.excepciones;
// Justificacion: Hereda de RuntimeException porque pasar un porcentaje fuera del rango [0, 100] 
// es un error de programacion/uso de la API que se debe corregir en tiempo de desarrollo
public class DescuentoInvalidoException extends RuntimeException {
    public DescuentoInvalidoException(String mensaje) {
        super(mensaje);
    }
}