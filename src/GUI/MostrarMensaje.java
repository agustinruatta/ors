/*
 * Copyright (C) 2014 Agustín Ruatta <agustinruatta@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package GUI;

import GUI.Optimizacion.PresentadorIntroduccionDatosOptimizacion;
import Optimizacion.MetodoDeLasDosFases.MetodoDeLasDosFases;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.swing.JOptionPane;


public final class MostrarMensaje {

    public static enum OPCIONES_CONSULTA {ACEPTAR, CANCELAR};
    
    private static final String TITULO_ERROR = "¡Error!";
    private static final String TITULO_INFORMACION = "Información";
    private static final String TITULO_CONSULTA = "Consulta";
    
    
    public static void algunCoeficienteDeLaFuncionObjetivoNoEstaCompletado(){
        mostrarMensajeError("¡Algún coeficiente de la función objetivo no está completado!");
    }
    
    public static void algunCoeficienteDeLaFuncionObjetivoEsIncorrecto(){
        mostrarMensajeError("¡Algún coeficiente de la función objetivo es incorrecto!");
    }
    
    public static void unaOMasIgualdadesIncorrectas(){
        mostrarMensajeError("¡Una o más igualdades se ingresaron incorrectamente!");
    }
    
    public static void algunCoeficienteDeLasRestriccionesNoEstaCompletado(){
        mostrarMensajeError("¡Algún coeficiente de las restricciones no está completado!");
    }
    
    public static void algunCoeficienteDeLasRestriccionesEsIncorrecto(){
        mostrarMensajeError("¡Algún coeficiente de las restricciones es incorrecto!");
    }
    
    public static void ZNoEstaAcotada(){
        mostrarMensajeError("¡Z no está acotada!\n"
                + "El método de las 2 fases determinó que Z no se encuentra acotada (Puede tomar valores infinito positivo para Maximización"
                + "\no infinito negativo para Minimización)."
                + "\nProbablemente el modelo esté mal formulado, ya sea por haber omitido una"
                + "\nrestricción relevante o por haberla establecido de modo incorrecta."
                + "\nAdemás verifique que no haya puesto Maximizar cuando en realidad quiere Minimizar o viceversa."
        );
    }
    
    public static void sinSolucionesFactibles(){
        mostrarMensajeError("¡No existen soluciones factibles!\n"
                + "El método de las 2 fases determinó que no existe ninguna solución factible."
        );
    }
    
    public static void unaOMasFilasTieneTodosLosCoeficientesCero(){
        mostrarMensajeError("¡Una o más filas de la función objetivo o de las restricciones tiene todos los coeficientes con valor cero!");
    }

    public static void errorAlRealizarUnaOperacion( Throwable excepcionOcurrida ) {
        StringWriter error = new StringWriter();
        excepcionOcurrida.printStackTrace(new PrintWriter(error));
        
        mostrarMensajeError("¡UPS! Hubo un error al realizar una operación :(\n\n"
                + "Vea la ventana de ayuda para más información. Si lo desea, contacte con el\n"
                + "desarrollador explicándole que hizo para que ocurriera este error y enviándole\n"
                + "el siguiente texto, que se usará para detectar donde ocurrió el error:\n\n"
                + error.toString()
        );
    }
    
    
    public static void demasiadasIteraciones() {
        String mensajeError = String.format("¡Imposible resolver!\n"
                + "Se ha detectado que el método ha hecho demasiadas iteraciones para tratar de resolver el problema\n"
                + "Ésto puede ser debido a que:\n"
                + "1) Se ha introducido un problema que produce que el Método de las 2 Fases itere infinitamente.\n"
                + "2) El programa tiene un bug y, por lo tanto, itera infinitamente.\n"
                + "3) Verdaderamente el problema requiere muchas iteraciones (Más de %d, lo cual es bastante improbable).\n"
                + "Por favor, informe al desarrollador sobre éste error, enviándole el problema indicado. ", MetodoDeLasDosFases.CANTIDAD_MAXIMA_ITERACIONES);
        
        mostrarMensajeError(mensajeError);
    }
    
    
    public static void seReacomodoAlgunaRestriccionConTINegativo(){
        mostrarMensajeInformacion(
        "Se reacomodó una o más filas que tenían T.I. negativo, multiplicando la restricción por -1.\n"
                + "Por ejemplo, si introdujo la restricción X1 + X2 <= -3, se transformó automáticamente\n"
                + "en -X1 - X2 >= 3"
        );
    }
    
    public static OPCIONES_CONSULTA estaSeguroDemasiadasVariables(){
        return mostrarMensajeConsulta(
                "Ha ingresado más de " + PresentadorIntroduccionDatosOptimizacion.CANTIDAD_MAXIMA_VARIABLES_RECOMENDADAS + " variables.\n"
                        + "¿Esta seguro de que el problema tiene tantas?");
    }
    
    public static OPCIONES_CONSULTA estaSeguroDemasiadasRestricciones(){
        return mostrarMensajeConsulta(
                "Ha ingresado más de " + PresentadorIntroduccionDatosOptimizacion.CANTIDAD_MAXIMA_RESTRICCIONES_RECOMENDADAS + " restricciones.\n"
                        + "¿Esta seguro de que el problema tiene tantas?");
    }
    
    
    
    private static void mostrarMensajeError( String mensajeError ){
        JOptionPane.showMessageDialog( null, mensajeError , TITULO_ERROR, JOptionPane.ERROR_MESSAGE );
    }
    
    private static void mostrarMensajeInformacion( String mensajeInformacion ){
        JOptionPane.showMessageDialog( null, mensajeInformacion , TITULO_INFORMACION, JOptionPane.INFORMATION_MESSAGE );
    }
    
    private static OPCIONES_CONSULTA mostrarMensajeConsulta( String mensajeConsulta ){
        int respuesta = JOptionPane.showConfirmDialog(null, mensajeConsulta, TITULO_CONSULTA, JOptionPane.YES_NO_OPTION);
        
        if( respuesta == JOptionPane.YES_OPTION ){
            return OPCIONES_CONSULTA.ACEPTAR;
        }
        else{
            return OPCIONES_CONSULTA.CANCELAR;
        }
    }

    private MostrarMensaje() {
    }
}
