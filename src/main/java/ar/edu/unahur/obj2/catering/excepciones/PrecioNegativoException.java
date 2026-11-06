package ar.edu.unahur.obj2.catering.excepciones;
// Justificacion: Hereda de RuntimeException porque representa una violacion de las 
// precondiciones de instanciacion que el cliente del codigo debe evitar mediante validacion
public class PrecioNegativoException extends RuntimeException {
    public PrecioNegativoException(String mensaje) {
        super(mensaje);
    }
}