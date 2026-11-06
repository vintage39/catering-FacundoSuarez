package ar.edu.unahur.obj2.catering;

public abstract class Adicional implements Propuesta {
    protected final Propuesta propuestaBase;

    public Adicional(Propuesta propuestaBase) {
        this.propuestaBase = propuestaBase;
    }

    @Override
    public Integer getCantidadServicios() {
        return propuestaBase.getCantidadServicios();
    }

    @Override
    public Boolean incluyeServicio(String nombreServicio) {
        return propuestaBase.incluyeServicio(nombreServicio);
    }
}