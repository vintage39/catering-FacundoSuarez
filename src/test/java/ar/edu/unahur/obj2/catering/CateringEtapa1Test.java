package ar.edu.unahur.obj2.catering;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;


import ar.edu.unahur.obj2.catering.excepciones.DescuentoInvalidoException;
import ar.edu.unahur.obj2.catering.excepciones.PrecioNegativoException;

public class CateringEtapa1Test {

    private Servicio mesaEntradas;
    private Servicio parrilladaCompleta;
    private Servicio mesaDulce;
    private Servicio barraTragos;
    private Servicio djSonido;
    private Servicio brindisBienvenida;

    private Menu menuParrillero;
    private Menu packFiesta;
    private Menu eventoCompleto;

    @BeforeEach
    void setUp() {
        // Catálogo de servicios iniciales
        mesaEntradas = new Servicio("Mesa de entradas", 80000.0);
        parrilladaCompleta = new Servicio("Parrillada completa", 240000.0);
        mesaDulce = new Servicio("Mesa dulce", 60000.0);
        barraTragos = new Servicio("Barra de tragos", 150000.0);
        djSonido = new Servicio("DJ y sonido", 120000.0);
        brindisBienvenida = new Servicio("Brindis de bienvenida", 50000.0);

        // Menú Parrillero (10% desc) -> Subtotal 380.000 -> Con desc = $342.000
        menuParrillero = new Menu("Menú Parrillero", 10.0);
        menuParrillero.agregarPropuesta(mesaEntradas);
        menuParrillero.agregarPropuesta(parrilladaCompleta);
        menuParrillero.agregarPropuesta(mesaDulce);

        // Pack Fiesta (5% desc) -> Subtotal 270.000 -> Con desc = $256.500
        packFiesta = new Menu("Pack Fiesta", 5.0);
        packFiesta.agregarPropuesta(barraTragos);
        packFiesta.agregarPropuesta(djSonido);

        // Evento Completo (20% desc sobre componentes) -> Subtotal (342.000 + 256.500 + 50.000) = 648.500 -> Con desc = $518.800
        eventoCompleto = new Menu("Evento Completo", 20.0);
        eventoCompleto.agregarPropuesta(menuParrillero);
        eventoCompleto.agregarPropuesta(packFiesta);
        eventoCompleto.agregarPropuesta(brindisBienvenida);
    }

    @Test
    public void dadoUnServicioConPrecioNegativo_alCrearlo_lanzaPrecioNegativoException() {
        assertThrows(PrecioNegativoException.class, () -> new Servicio("Invalido", -1.0));
    }

    @Test
    public void dadoUnMenuConDescuentoFueraDeRango_alCrearlo_lanzaDescuentoInvalidoException() {
        assertThrows(DescuentoInvalidoException.class, () -> new Menu("Invalido", -10.0));
        assertThrows(DescuentoInvalidoException.class, () -> new Menu("Invalido", 101.0));
    }

    @Test
    public void dadoUnMenuSimple_alCalcularPrecioYCantidad_retornaValoresEsperados() {
        assertEquals(342000.0, menuParrillero.getPrecio(), 0.01);
        assertEquals(256500.0, packFiesta.getPrecio(), 0.01);
        assertEquals(3, menuParrillero.getCantidadServicios());
    }

    @Test
    public void dadoUnMenuSimple_alConsultarInclusionDeServicio_respondeCorrectamente() {
        assertTrue(menuParrillero.incluyeServicio("Parrillada completa"));
        assertFalse(menuParrillero.incluyeServicio("DJ y sonido"));
    }

    @Test
    public void dadoUnMenuConMenusAnidados_alCalcularPrecio_aplicaDescuentoSobrePreciosYaDescontados() {
        assertEquals(518800.0, eventoCompleto.getPrecio(), 0.01);
    }
    @Test
    public void dadoUnMenuConMenusAnidados_alContarServicios_sumaLosDeTodosLosNiveles() {
        assertEquals(6, eventoCompleto.getCantidadServicios());
    }
    @Test
    public void dadoUnMenuConMenusAnidados_alConsultarInclusion_buscaEnTodosLosNiveles() {
        assertTrue(eventoCompleto.incluyeServicio("DJ y sonido"));
        assertFalse(eventoCompleto.incluyeServicio("Fotógrafo"));
    }
}