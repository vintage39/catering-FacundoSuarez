package ar.edu.unahur.obj2.catering;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import ar.edu.unahur.obj2.catering.excepciones.NadaParaRevertirException;
public class GestorCarta {
    private final Deque<Comando> historial = new ArrayDeque<>();

    public void ejecutar(Comando comando) {
        comando.ejecutar();
        historial.push(comando);
    }

   public void deshacerUltimo() {
    if (historial.isEmpty()) {
        throw new NadaParaRevertirException("No hay ningún comando en el historial para deshacer.");
    }
    Comando ultimo = historial.pop();
    ultimo.deshacer();
}

    public void ejecutarLote(List<Comando> comandos) {
        Deque<Comando> ejecutadosEnLote = new ArrayDeque<>();
        try {
            for (Comando comando : comandos) {
                comando.ejecutar();
                ejecutadosEnLote.push(comando);
            }
        
            while (!ejecutadosEnLote.isEmpty()) {
                historial.push(ejecutadosEnLote.removeLast());
            }
        } catch (RuntimeException excepcion) {
            // Justificación: Captura cualquier falla producida durante el lote para revertir (rollback)
            // los comandos ejecutados hasta el momento del error y mantener la atomicidad de la carta.
            while (!ejecutadosEnLote.isEmpty()) {
                ejecutadosEnLote.pop().deshacer();
            }
            throw excepcion;
        }
    }

    public int getCantidadComandosEnHistorial() {
        return historial.size();
    }
}