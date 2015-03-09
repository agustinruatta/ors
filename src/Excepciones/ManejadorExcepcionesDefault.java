package Excepciones;

import GUI.MostrarMensaje;
import java.lang.Thread.UncaughtExceptionHandler;

/**
 * Clase encargada de manejar las excepciones que no se agarraron
 * en ninguna otra parte del programa.
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 */
public class ManejadorExcepcionesDefault implements UncaughtExceptionHandler{

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
        
        MostrarMensaje.errorAlRealizarUnaOperacion(e);
    }

}
