package ar.edu.unahur.obj2.catering;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CateringEtapa2Test {

    private Servicio mesaEntradas;
    private Servicio parrilladaCompleta;
    private Servicio mesaDulce;
    private Menu menuParrillero;

    @BeforeEach
    void setUp() {
        mesaEntradas = new Servicio("Mesa de entradas", 80000.0);
        parrilladaCompleta = new Servicio("Parrillada completa", 240000.0);
        mesaDulce = new Servicio("Mesa dulce", 60000.0);

        menuParrillero = new Menu("Menú Parrillero", 10.0); // Subtotal 380k -> $342.000
        menuParrillero.agregarPropuesta(mesaEntradas);
        menuParrillero.agregarPropuesta(parrilladaCompleta);
        menuParrillero.agregarPropuesta(mesaDulce);
    }

    @Test
    public void dadoUnMenuParrillero_alCombinarAdicionalesEnDistintoOrden_elOrdenAlteraElPrecioFinal() {
        // (342.000 + 18.000) * 1.20 = 432.000
        Propuesta mozosYNocturno = new ServicioNocturno(new MozosAdicionales(menuParrillero));
        assertEquals(432000.0, mozosYNocturno.getPrecio(), 0.01);
        assertEquals("Menú Parrillero + Mozos adicionales + Servicio nocturno", mozosYNocturno.getDescripcion());

        // (342.000 * 1.20) + 18.000 = 428.400
        Propuesta nocturnoYMozos = new MozosAdicionales(new ServicioNocturno(menuParrillero));
        assertEquals(428400.0, nocturnoYMozos.getPrecio(), 0.01);
        assertEquals("Menú Parrillero + Servicio nocturno + Mozos adicionales", nocturnoYMozos.getDescripcion());
    }

    @Test
    public void dadaUnaPropuesta_alRepetirAdicionales_acumulaPreciosCorrectamente() {
        // Servicio suelto ($60.000) * 1.20 * 1.20 = 86.400
        Propuesta mesaDulceDobleNocturno = new ServicioNocturno(new ServicioNocturno(mesaDulce));
        assertEquals(86400.0, mesaDulceDobleNocturno.getPrecio(), 0.01);

        // Servicio suelto ($80.000) + 12.000 + 12.000 = 104.000
        Propuesta entradasDobleVajilla = new VajillaPremium(new VajillaPremium(mesaEntradas));
        assertEquals(104000.0, entradasDobleVajilla.getPrecio(), 0.01);
    }

    @Test
    public void dadoUnAdicional_alConsultarServiciosEInclusion_mantieneLosValoresOriginalesSinModificarLaPropuestaBase() {
        Propuesta menuConMozos = new MozosAdicionales(menuParrillero);

        // Mantiene la cantidad de servicios e inclusión
        assertEquals(3, menuConMozos.getCantidadServicios());
        assertTrue(menuConMozos.incluyeServicio("Parrillada completa"));

        // Inmutabilidad: La propuesta original sigue intacta
        assertEquals(342000.0, menuParrillero.getPrecio(), 0.01);
        assertEquals("Menú Parrillero", menuParrillero.getDescripcion());
    }

    @Test
    public void dadoUnContratoCreado_alConsultar_fijaElImporteYLaDescripcionSinCambiar() {
        Propuesta propuesta = new MozosAdicionales(menuParrillero);
        Contrato contrato = new Contrato("Lucía", propuesta);

        assertEquals("Lucía", contrato.getCliente());
        assertEquals(360000.0, contrato.getImporte(), 0.01);
        assertEquals("Menú Parrillero + Mozos adicionales", contrato.getDescripcion());
    }
}