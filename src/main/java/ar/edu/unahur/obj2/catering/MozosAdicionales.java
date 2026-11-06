package ar.edu.unahur.obj2.catering;

public class MozosAdicionales extends Adicional {

    public MozosAdicionales(Propuesta propuestaBase) {
        super(propuestaBase);
    }

    @Override
    public Double getPrecio() {
        return propuestaBase.getPrecio() + 18000.0;
    }

    @Override
    public String getDescripcion() {
        return propuestaBase.getDescripcion() + " + Mozos adicionales";
    }
}