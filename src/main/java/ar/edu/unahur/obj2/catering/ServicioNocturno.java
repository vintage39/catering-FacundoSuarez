package ar.edu.unahur.obj2.catering;

public class ServicioNocturno extends Adicional {

    public ServicioNocturno(Propuesta propuestaBase) {
        super(propuestaBase);
    }

    @Override
    public Double getPrecio() {
        return propuestaBase.getPrecio() * 1.20;
    }

    @Override
    public String getDescripcion() {
        return propuestaBase.getDescripcion() + " + Servicio nocturno";
    }
}