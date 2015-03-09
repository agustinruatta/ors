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
package Optimizacion.Dualidad;

import API.Fraccion;
import Excepciones.TodosLosCoeficientesDeLaFilaSonCeroException;
import Optimizacion.DatosOptimizacion;
import Optimizacion.Enumeraciones.Igualdades;
import Optimizacion.Enumeraciones.Objetivo;
import Optimizacion.RestriccionVariableDeDecision;


public class Dualidad {

    private final DatosOptimizacion problemaPrimal;
    private final DatosOptimizacion problemaDual;

    public Dualidad( DatosOptimizacion problemaPrimal ) throws IllegalArgumentException, TodosLosCoeficientesDeLaFilaSonCeroException {
        this.problemaPrimal = problemaPrimal;
        
        //Se indica que no se quiere reacomodar las filas con T.I negativos ya que podría alterar la escencia de la dualidad.
        this.problemaDual = new DatosOptimizacion( this.getObjetivoDual() , this.getFuncionObjetivoDual(), this.getRestriccionesDual(), this.getIgualdadesDual(), this.getRestriccionesVariablesDeDecision(), false );
        
    }

    
    /**
     * Devuelve el problema dual de problema primal enviado como
     * parámetro en el constructor.
     * @return 
     */
    public DatosOptimizacion getProblemaDual() {
        return problemaDual;
    }
    
    
    private Fraccion[] getFuncionObjetivoDual(){
        
        Fraccion[] funcionObjetivoDual = new Fraccion[ problemaPrimal.getCantidadRestricciones() ];
        
        for( int i = 0; i < funcionObjetivoDual.length; i++ ){
            
            funcionObjetivoDual[ i ] = problemaPrimal.getTIDeRestriccion( i );
            
        }
        
        return funcionObjetivoDual;
    }
    
    private Fraccion[][] getRestriccionesDual(){
        
        //Se le suma 1 debido a que hay que tener en cuenta el T.I.
        Fraccion[][] restriccionesDual = new Fraccion[ problemaPrimal.getCantidadVariablesDeDecision() ][ problemaPrimal.getCantidadRestricciones() + 1 ];
        Fraccion[][] restriccionesPrimal = problemaPrimal.getRestricciones();
        Fraccion[] funcionObjetivoPrimal = problemaPrimal.getFuncionObjetivo();
        
        for( int fila = 0; fila < problemaPrimal.getCantidadRestricciones(); fila++ ){
            //Se recorre hasta la cantidad de variable de decisión. Por lo tanto no se toma en cuenta el T.I.
            int columna;
            for( columna = 0; columna < problemaPrimal.getCantidadVariablesDeDecision(); columna++ ){
                restriccionesDual[ columna ][ fila ] = restriccionesPrimal[ fila ][ columna ];
            }
            
        }
        
        //Completar los T.I.
        
        for( int i = 0; i < problemaPrimal.getCantidadVariablesDeDecision(); i++ ){
            restriccionesDual[ i ][ problemaPrimal.getCantidadRestricciones() ] = funcionObjetivoPrimal[ i ];
        }
        
        return restriccionesDual;
    }
    
    private Objetivo getObjetivoDual(){
        
        if( problemaPrimal.getObjetivoDeLaOptimizacion().equals( Objetivo.MAXIMIZAR ) ){
            return Objetivo.MINIMIZAR;
        }
        else{
            return Objetivo.MAXIMIZAR;
        }
        
    }
    
    
    private Igualdades[] getIgualdadesDual(){
        
        Igualdades[] igualdadesDual = new Igualdades[ problemaPrimal.getCantidadVariablesDeDecision() ];
        RestriccionVariableDeDecision[] restriccionVariableDeDecisionPrimal = problemaPrimal.getRestriccionesVariablesDeDecision();
        
        for( int i = 0; i < igualdadesDual.length; i++ ){
            
            if( restriccionVariableDeDecisionPrimal[ i ].esNoRestringida() ){
                igualdadesDual[ i ] = Igualdades.IGUAL;
            }
            else if( restriccionVariableDeDecisionPrimal[ i ].esNoNegativa() ){
                igualdadesDual[ i ] = Igualdades.MAYOR_O_IGUAL;
            }
            else if( restriccionVariableDeDecisionPrimal[ i ].esNoPositiva() ){
                igualdadesDual[ i ] = Igualdades.MENOR_O_IGUAL;
            }
            else{
                //No se debería llegar nunca acá
                throw new RuntimeException();
            }
            
        }
        
        return igualdadesDual;
        
    }
    
    
    private RestriccionVariableDeDecision[] getRestriccionesVariablesDeDecision(){
        Igualdades[] igualdadesProblemaPrimal = problemaPrimal.getIgualdades();
        RestriccionVariableDeDecision[] restriccionesVariablesDeDecisionDual = new RestriccionVariableDeDecision[ igualdadesProblemaPrimal.length ];
        
        
        for( int i = 0; i < igualdadesProblemaPrimal.length; i++ ){
            
            if( igualdadesProblemaPrimal[ i ].equals( Igualdades.MENOR_O_IGUAL ) ){
                restriccionesVariablesDeDecisionDual[ i ] = RestriccionVariableDeDecision.VARIABLE_NO_NEGATIVA_POR_DEFECTO;
            }
            else if( igualdadesProblemaPrimal[ i ].equals( Igualdades.MAYOR_O_IGUAL ) ){
                restriccionesVariablesDeDecisionDual[ i ] = RestriccionVariableDeDecision.VARIABLE_NO_POSITIVA_POR_DEFECTO;
            }
            else if( igualdadesProblemaPrimal[ i ].equals( Igualdades.IGUAL ) ){
                restriccionesVariablesDeDecisionDual[ i ] = RestriccionVariableDeDecision.VARIABLE_NO_RESTRINGIDA_POR_DEFECTO;
            }
            else{
                //No debería llegar nunca acá
                throw new RuntimeException();
            }
        }
        
        return restriccionesVariablesDeDecisionDual;
    }
    
}
