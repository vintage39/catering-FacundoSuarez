package ar.edu.unahur.obj2.catering;

public class ModificarDescuentoMenu implements Comando {
    private final Menu menu;
    private final Double nuevoDescuento;
    private Double descuentoAnterior;

    public ModificarDescuentoMenu(Menu menu, Double nuevoDescuento) {
        this.menu = menu;
        this.nuevoDescuento = nuevoDescuento;
    }

    @Override
    public void ejecutar() {
        this.descuentoAnterior = menu.getDescuento();
        menu.setDescuento(nuevoDescuento);
    }

    @Override
    public void deshacer() {
        if (descuentoAnterior != null) {
            menu.setDescuento(descuentoAnterior);
        }
    }
}