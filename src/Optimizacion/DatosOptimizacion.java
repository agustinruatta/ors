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
package Optimizacion;

import API.Fraccion;
import Excepciones.TodosLosCoeficientesDeLaFilaSonCeroException;
import Optimizacion.Enumeraciones.Igualdades;
import Optimizacion.Enumeraciones.Objetivo;
import java.util.Arrays;
import java.util.Objects;



public class DatosOptimizacion {
    
    private final Objetivo objetivoDeLaOptimizacion;
    
    private final Igualdades[] igualdades;
    
    private final Fraccion[] funcionObjetivo;
    
    /**
     * Restricciones. Incluye el T.I.
     */
    private final Fraccion[][] restricciones;
    
    private final RestriccionVariableDeDecision[] restriccionesVariablesDeDecision;
    
    
    private boolean seReacomodoAlgunaRestriccionConTINegativo;

    /**
     * Constructor predeterminado.
     * Guarda los datos pero con todas las restricciones de las variables de decisión se establecen por defecto.
     * Además reacomoda las restricciones con T.I. negativo para que no lo tenga (Multiplica por -1 la restricción).
     * @param objetivoDeLaOptimizacion Objetivo que se busca de la optimización: Maximizar o Minimizar
     * @param igualdades Las igualdades (O desigualdades) que poseen las restricciones.
     * @param funcionObjetivo Función objetivo de la optimización.
     * @param restricciones Restricciones de la optimización.
     * @throws IllegalArgumentException Se lanza cuando algún/os argumento/s no cumple con cierto requisito.
     */
    public DatosOptimizacion( Objetivo objetivoDeLaOptimizacion, Fraccion[] funcionObjetivo, Fraccion[][] restricciones, Igualdades[] igualdades ) throws IllegalArgumentException, TodosLosCoeficientesDeLaFilaSonCeroException {
        
        this( objetivoDeLaOptimizacion, funcionObjetivo, restricciones, igualdades, RestriccionVariableDeDecision.getRestriccionesPorDefecto( funcionObjetivo.length ), true );
        
    }
    
    
    
    

    public DatosOptimizacion( Objetivo objetivoDeLaOptimizacion, Fraccion[] funcionObjetivo, Fraccion[][] restricciones, Igualdades[] igualdades, RestriccionVariableDeDecision[] restriccionesVariablesDeDecision, boolean reacomodarRestriccionesConTINegativo )  throws IllegalArgumentException, TodosLosCoeficientesDeLaFilaSonCeroException {
        
        //Lo que primer se debe hacer es asignar, para evitar NullPointerException en las validaciones posteriores
        this.objetivoDeLaOptimizacion = objetivoDeLaOptimizacion;
        this.igualdades = igualdades;
        this.funcionObjetivo = funcionObjetivo;
        this.restricciones = restricciones;
        this.restriccionesVariablesDeDecision = restriccionesVariablesDeDecision;
        
        
        //Si algún dato no es válido, lanzar una excepción.
        if( !losDatosSonValidos() ){
            throw new IllegalArgumentException();
        }
        
        if( this.tieneTodosCerosEnAlgunaFila(restricciones) ){
            throw new TodosLosCoeficientesDeLaFilaSonCeroException();
        }
        if( this.tieneTodosCerosEnLaFila(funcionObjetivo, false) ){
            throw new TodosLosCoeficientesDeLaFilaSonCeroException();
        }
        
        
        if( reacomodarRestriccionesConTINegativo ){
            //Se reacomodan las restricciones con TI negativo para poder usar el método de las dos fases
            reacomodarRestriccionesConTINegativo();
        }
        
    }
    
    
    private boolean tieneTodosCerosEnLaFila( Fraccion[] fila, boolean ultimaFilaEsTerminoIndependiente){
        int limite = fila.length;
        
        if( ultimaFilaEsTerminoIndependiente ){
            //Para no analizar el término independiente
            limite--;
        }
        
        for (int i = 0; i < limite; i++) {
            if( !fila[i].esCero() ){
                return false;
            }
        }
        
        return true;
    }
    
    private boolean tieneTodosCerosEnAlgunaFila( Fraccion[][] coeficientes ){
        
        for( Fraccion[] fila : coeficientes ){
            
            if( this.tieneTodosCerosEnLaFila(fila, true) ){
                return true;
            }
            
        }
        
        return false;
        
    }
    
    
    
    
    
    
    
    

    
    
    /**
     * Indica si todos los datos enviados como parámetros son válidos.
     * @return true si son todos válidos, false si alguno/s son no válido/s.
     */
    private boolean losDatosSonValidos(){
        
        
        if( algunParametroEsNulo() ){
            return false;
        }
        if( algunCoeficienteDeLaFuncionObjetivoEsNulo() ){
            return false;
        }
        if( algunCoeficienteDeLasRestriccionesEsNulo() ){
            return false;
        }
        if( !todasLasFilasDeLasRestriccionesTienenLaMismaCantidadDeColumnas() ){
            return false;
        }
        if( !elTamanioDeLaFuncionObjetivoYRestriccionesSonConcordantes() ){
            return false;
        }
        if( !laCantidadDeIgualdadesYRestriccionesSonIguales() ){
            return false;
        }
        if( !laCantidadDeRestriccionesDeVariablesDeDecisionSonCorrectas() ){
            return false;
        }
        
        return true;
        
    }
    
    
    /**
     * Indica si algún parámetro enviado es nulo.
     * @return True si algún parametro es nulo, false caso contrario.
     */
    private boolean algunParametroEsNulo(){
        
        return(
                objetivoDeLaOptimizacion == null ||
                funcionObjetivo == null ||
                restricciones == null ||
                igualdades == null
                );
        
    }
    
    
    /**
     * Indica si algún coeficiente de la función objetivo es nulo.
     * @return true si algún coeficiente de la función objetivo es nulo,
     * false caso contrario.
     */
    private boolean algunCoeficienteDeLaFuncionObjetivoEsNulo(){
        
        for( Fraccion coeficiente : funcionObjetivo ){
            
            if ( coeficiente == null ) {
                return true;
            }
            
        }
        
        return false;
        
    }
    
    /**
     * Indica su algún coeficiente de las restricciones es nulo.
     * @return true si algún coeficiente de las restricciones es nulo,
     * false caso contrario.
     */
    private boolean algunCoeficienteDeLasRestriccionesEsNulo(){
        
        for( int fila = 0; fila < getCantidadRestricciones(); fila ++ ){
            for( int columna = 0; columna < restricciones[fila].length ; columna++ ){
                if( restricciones[ fila ][ columna ] == null ){
                    return true;
                }
            }
        }
        
        return false;
    }
    
    
    /**
     * Indica si la cantidad de restricciones de las variables de decisión son correctas.
     * Se comprueba que las restricciones de las variables de decisión (Por ej. X1>=0) sean
     * igual a la cantidad totales de variables de decisión.
     * @return 
     */
    private boolean laCantidadDeRestriccionesDeVariablesDeDecisionSonCorrectas(){
        return (
                restriccionesVariablesDeDecision.length == getCantidadVariablesDeDecision()
                );
    }
    
    /**
     * Indica si todas las filas de las restricciones tienen la misma cantidad de columnas.
     * Al ser como una tabla, las restricciones deben tener la misma cantidad de columnas.
     * @return true si tienen la misma cantidad de columnas, false caso contrario.
     */
    private boolean todasLasFilasDeLasRestriccionesTienenLaMismaCantidadDeColumnas(){
        
        int cantidadColumnas = restricciones[0].length;
        
        for( Fraccion[] filaRestriccion : restricciones ){
            
            if( cantidadColumnas != filaRestriccion.length ){
                return false;
            }
            
        }
        
        return true;        
    }
    
    /**
     * Indica si el tamaño de la función objetivo y las restricciones son concordantes.
     * Verifica que la función objetivo tenga una columna menos que las restricciones
     * ingresadas. Ésto se da debido a que la función objetivo no tiene una columna
     * dedicada para los T.I. (Cosa que si lo tienen las restricciones.
     * @return true si son concordantes, false caso contrario.
     */
    private boolean elTamanioDeLaFuncionObjetivoYRestriccionesSonConcordantes(){
        
        return (
                getCantidadColumnasFuncionObjetivo() + 1 == getCantidadColumnasRestricciones()
                );
        
    }
    
    
    /**
     * Indica si la cantidad de igualdades y la cantidad de restricciones son iguales.
     * Debe existir una igualdad por cada restricción.
     * @return true si son iguales, false caso contrario.
     */
    private boolean laCantidadDeIgualdadesYRestriccionesSonIguales(){
        return (
                igualdades.length == getCantidadFilasRestricciones()
                );
    }
    
    
    
    /**
     * Niega ( Multiplica por -1 ) las filas de las restricciones  que tengan término
     * independiente negativo y luego invierte la igualdad.
     * Ésto se hace debido a que es una condición del método de las 2 fases. Sino se
     * tiene que aplicar el método simplex dual cuando se encuentra un T.I. negativo.
     */
    private void reacomodarRestriccionesConTINegativo(){
        
        this.seReacomodoAlgunaRestriccionConTINegativo = false;
        
        //Recorrer los TI, observar cual es negativo, y negar esa filaConResultadoMasChico.
        for( int fila = 0; fila < restricciones.length; fila++ ){
            
            if( restricciones[ fila ][ getNumeroColumnaTIRestricciones() ].esFraccionNegativa() ){
                invertirSignoCoeficientesIngresados(restricciones, fila);
                invertirIgualdadDeCoeficientesIngresados(fila);
                
                this.seReacomodoAlgunaRestriccionConTINegativo = true;
            }
        }
        
    }
    
    
    /**
     * Invierta la igualdad de los coeficientes de restricción de la fila
     * indicada en el parámetro.
     * @param filaCoeficientesIngresados Fila en donde será cambiada la igualdad.
     */
    private void invertirIgualdadDeCoeficientesIngresados( int fila ){
        
        //Si la igualdad es mayor o igual, se cambia a menor o igual.
        if( igualdades[ fila ] == Igualdades.MAYOR_O_IGUAL ){
            igualdades[ fila ] = Igualdades.MENOR_O_IGUAL;
        }
        //Si la igualdad es menor o igual, se cambia a mayor o igual.
        else if( igualdades[ fila ] == Igualdades.MENOR_O_IGUAL ){
            igualdades[ fila ] = Igualdades.MAYOR_O_IGUAL;
        }
        //Si la igualdad es igual(=), no se hace nada.
    }
    
    
    /**
     * Invierte los signos de los coeficientes de la filaConResultadoMasChico indicada en el parámetro.
     * @param fila Fila en la cual se deben invertir los signos.
     */
    private void invertirSignoCoeficientesIngresados( Fraccion[][] restricciones, int fila ){
        
        for( int columna = 0; columna < restricciones[0].length; columna++ ){
            restricciones[ fila ][ columna ] = restricciones[ fila ][ columna ].getNegativo();
        }
        
    }
    
    
    
    public Objetivo getObjetivoDeLaOptimizacion() {
        return objetivoDeLaOptimizacion;
    }
    
    public Igualdades[] getIgualdades() {
        return igualdades;
    }

    public Fraccion[] getFuncionObjetivo() {
        return funcionObjetivo;
    }

    public Fraccion[][] getRestricciones() {
        return restricciones;
    }

    public RestriccionVariableDeDecision[] getRestriccionesVariablesDeDecision() {
        return restriccionesVariablesDeDecision;
    }
    
    
    
    
    
    
    private int getNumeroColumnaTIRestricciones(){
        return restricciones[0].length - 1;
    }
    
    
    public Fraccion getTIDeRestriccion( int fila ){
        return restricciones[ fila ][ getNumeroColumnaTIRestricciones() ];
    }
    
    public int getCantidadFilasRestricciones(){
        return restricciones.length;
    }
    
    public int getCantidadColumnasRestricciones(){
        return restricciones[0].length;
    }
    
    public int getCantidadColumnasFuncionObjetivo(){
        return funcionObjetivo.length;
    }
    
    
    public int getCantidadVariablesDeDecision(){
        return getCantidadColumnasFuncionObjetivo();
    }
    
    public int getCantidadRestricciones(){
        return getCantidadFilasRestricciones();
    }

    public boolean seReacomodoAlgunaRestriccionConTINegativo() {
        return seReacomodoAlgunaRestriccionConTINegativo;
    }
    
    
    
    
    
    @Override
    public boolean equals( Object objetoAComparar ){
        
//        if( this == objetoAComparar ){
//            return true;
//        }
//        
//        if( objetoAComparar instanceof DatosOptimizacion ){
//            DatosOptimizacion datosAComparar = (DatosOptimizacion) objetoAComparar;
//            
//            return (
//                    this.funcionObjetivo.equals( datosAComparar.getFuncionObjetivo() ) &&
//                    this.igualdades.equals( datosAComparar.getIgualdades() ) &&
//                    this.objetivoDeLaOptimizacion.equals( datosAComparar.getObjetivoDeLaOptimizacion() ) &&
//                    this.restricciones.equals( datosAComparar.getRestricciones() ) &&
//                    this.restriccionesVariablesDeDecision.equals( datosAComparar.getRestriccionesVariablesDeDecision() ) &&
//                    );
//        }
//        
//        return false;
        
        return this.hashCode() == objetoAComparar.hashCode();
    }

    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.objetivoDeLaOptimizacion);
        hash = 79 * hash + Arrays.deepHashCode(this.igualdades);
        hash = 79 * hash + Arrays.deepHashCode(this.funcionObjetivo);
        hash = 79 * hash + Arrays.deepHashCode(this.restricciones);
        hash = 79 * hash + Arrays.deepHashCode(this.restriccionesVariablesDeDecision);
        return hash;
    }

    
    
    
}
