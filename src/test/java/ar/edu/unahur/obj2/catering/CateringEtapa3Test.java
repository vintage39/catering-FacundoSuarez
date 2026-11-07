package ar.edu.unahur.obj2.catering;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unahur.obj2.catering.excepciones.DescuentoInvalidoException;
import ar.edu.unahur.obj2.catering.excepciones.NadaParaRevertirException;
import ar.edu.unahur.obj2.catering.excepciones.PrecioNegativoException;

public class CateringEtapa3Test {

    private Servicio mesaEntradas;
    private Servicio parrilladaCompleta;
    private Menu menuParrillero;
    private GestorCarta gestor;

    @BeforeEach
    void setUp() {
        mesaEntradas = new Servicio("Mesa de entradas", 80000.0);
        parrilladaCompleta = new Servicio("Parrillada completa", 240000.0);

        menuParrillero = new Menu("Menú Parrillero", 10.0); // Subtotal: $320.000 -> Con 10%: $288.000
        menuParrillero.agregarPropuesta(mesaEntradas);
        menuParrillero.agregarPropuesta(parrilladaCompleta);

        gestor = new GestorCarta();
    }

    @Test
    public void dadoUnServicioYMenu_alEjecutarComandosYDeshacer_actualizaYRestauraElEstado() {
        Comando cmdPrecio = new ModificarPrecioServicio(mesaEntradas, 100000.0);
        Comando cmdDescuento = new ModificarDescuentoMenu(menuParrillero, 20.0);

        gestor.ejecutar(cmdPrecio);
        assertEquals(100000.0, mesaEntradas.getPrecio(), 0.01);

        gestor.ejecutar(cmdDescuento);
        assertEquals(20.0, menuParrillero.getDescuento(), 0.01);

        gestor.deshacerUltimo(); // Deshace la modificación del descuento
        assertEquals(10.0, menuParrillero.getDescuento(), 0.01);
    }

    @Test
    public void dadoUnHistorialVacio_alDeshacerUltimo_lanzaNadaParaRevertirException() {
        assertThrows(NadaParaRevertirException.class, () -> gestor.deshacerUltimo());
    }

    @Test
    public void dadoUnLoteValido_alEjecutarLote_aplicaTodosLosComandosYLosAgregaAlHistorial() {
        Comando cmd1 = new ModificarPrecioServicio(mesaEntradas, 100000.0);
        Comando cmd2 = new ModificarDescuentoMenu(menuParrillero, 15.0);

        gestor.ejecutarLote(List.of(cmd1, cmd2));

        assertEquals(100000.0, mesaEntradas.getPrecio(), 0.01);
        assertEquals(15.0, menuParrillero.getDescuento(), 0.01);
        assertEquals(2, gestor.getCantidadComandosEnHistorial());
    }

    @Test
    public void dadoUnLoteConError_alEjecutarLote_haceRollbackYDejaElEstadoEHistorialIntactos() {
        Comando cmdValido = new ModificarPrecioServicio(mesaEntradas, 100000.0);
        Comando cmdInvalido = new ModificarPrecioServicio(parrilladaCompleta, -50000.0);

        assertThrows(PrecioNegativoException.class, () -> gestor.ejecutarLote(List.of(cmdValido, cmdInvalido)));

       
        assertEquals(80000.0, mesaEntradas.getPrecio(), 0.01);
        assertEquals(0, gestor.getCantidadComandosEnHistorial());
    }

    @Test
    public void dadoUnContratoPrevio_alModificarLaCarta_elContratoMantieneSuImporteOriginal() {
        Propuesta propuesta = new MozosAdicionales(menuParrillero); // $288.000 + $18.000 = $306.000
        Contrato contrato = new Contrato("Marcos", propuesta);

        gestor.ejecutar(new ModificarPrecioServicio(mesaEntradas, 200000.0));

        assertEquals(306000.0, contrato.getImporte(), 0.01);
    }

    

    @Test
    public void dadoUnComandoConPrecioNegativo_alEjecutar_lanzaPrecioNegativoException() {
        Comando cmdInvalido = new ModificarPrecioServicio(mesaEntradas, -1000.0);
        assertThrows(PrecioNegativoException.class, () -> gestor.ejecutar(cmdInvalido));
    }

    @Test
    public void dadoUnComandoConDescuentoInvalido_alEjecutar_lanzaDescuentoInvalidoException() {
        Comando cmdInvalido = new ModificarDescuentoMenu(menuParrillero, 150.0);
        assertThrows(DescuentoInvalidoException.class, () -> gestor.ejecutar(cmdInvalido));
    }

    @Test
    public void dadosVariosComandosEjecutados_alDeshacerMultiplesVeces_revierteEnOrdenInverso() {
        gestor.ejecutar(new ModificarPrecioServicio(mesaEntradas, 100000.0));
        gestor.ejecutar(new ModificarPrecioServicio(parrilladaCompleta, 300000.0));

        assertEquals(2, gestor.getCantidadComandosEnHistorial());

        gestor.deshacerUltimo(); 
        assertEquals(240000.0, parrilladaCompleta.getPrecio(), 0.01);

        gestor.deshacerUltimo(); 
        assertEquals(80000.0, mesaEntradas.getPrecio(), 0.01);

        assertEquals(0, gestor.getCantidadComandosEnHistorial());
    }
}