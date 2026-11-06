package ar.edu.unahur.obj2.catering;

public class VajillaPremium extends Adicional {

    public VajillaPremium(Propuesta propuestaBase) {
        super(propuestaBase);
    }

    @Override
    public Double getPrecio() {
        return propuestaBase.getPrecio() + 12000.0;
    }

    @Override
    public String getDescripcion() {
        return propuestaBase.getDescripcion() + " + Vajilla premium";
    }
}