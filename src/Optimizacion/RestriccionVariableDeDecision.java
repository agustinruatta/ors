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
import java.util.Objects;


public class RestriccionVariableDeDecision {

    public static final RestriccionVariableDeDecision RESTRICCION_POR_DEFECTO = new RestriccionVariableDeDecision();
    
    public static final RestriccionVariableDeDecision VARIABLE_NO_NEGATIVA_POR_DEFECTO = new RestriccionVariableDeDecision(CondicionRestriccionVariableDeDecision.MAYOR_O_IGUAL, Fraccion.CERO);
    public static final RestriccionVariableDeDecision VARIABLE_NO_POSITIVA_POR_DEFECTO = new RestriccionVariableDeDecision(CondicionRestriccionVariableDeDecision.MENOR_O_IGUAL, Fraccion.CERO);
    public static final RestriccionVariableDeDecision VARIABLE_NO_RESTRINGIDA_POR_DEFECTO = new RestriccionVariableDeDecision(CondicionRestriccionVariableDeDecision.NO_RESTRINGIDA, Fraccion.CERO);
    
    /**
     * Devuelve un vector del tamaño indicado en el parámetro con todas restricciones por defecto.
     * @param cantidadDeVariablesDeDecision
     * @return 
     */
    public static RestriccionVariableDeDecision[] getRestriccionesPorDefecto( int cantidadDeVariablesDeDecision ){
        
        RestriccionVariableDeDecision[] restriccionesPorDefecto = new RestriccionVariableDeDecision[cantidadDeVariablesDeDecision];
        
        for( int i = 0; i < restriccionesPorDefecto.length; i++ ){
            restriccionesPorDefecto[ i ] = RestriccionVariableDeDecision.RESTRICCION_POR_DEFECTO;
        }
        
        return restriccionesPorDefecto;
    }
    
    /**
     * Límite numérico que indica la restricción.
     * Si Xn>=10, entonces el valor límite es 10.
     */
    private final Fraccion valorNumericoLimiteCondicion;
    
    /**
     * Condición que afecta a la variable de decisión.
     * Si Xn>=10, entonces la condición es MAYOR_O_IGUAL.
     */
    private final CondicionRestriccionVariableDeDecision condicion;

    
    /**
     * Constructor por defecto.
     * Crea una restricción con Xn >= 0 (Lo que comunmente se da).
     */
    public RestriccionVariableDeDecision() {
        this( CondicionRestriccionVariableDeDecision.MAYOR_O_IGUAL, Fraccion.CERO );
    }

    
    
    public RestriccionVariableDeDecision( CondicionRestriccionVariableDeDecision condicion, Fraccion numeroCondicion ) {
        this.valorNumericoLimiteCondicion = numeroCondicion;
        this.condicion = condicion;
    }

    public Fraccion getValorNumericoLimiteCondicion() {
        return valorNumericoLimiteCondicion;
    }

    public CondicionRestriccionVariableDeDecision getCondicion() {
        return condicion;
    }
    
    
    

    
    
    @Override
    public boolean equals( Object objetoAComparar ){
        
        if( this == objetoAComparar ){
            return true;
        }
        
        if( objetoAComparar instanceof RestriccionVariableDeDecision ){
            RestriccionVariableDeDecision restriccionVariableDeDecision = (RestriccionVariableDeDecision) objetoAComparar;
            
            if(
                    restriccionVariableDeDecision.getCondicion().equals( this.condicion ) &&
                    restriccionVariableDeDecision.getValorNumericoLimiteCondicion().equals( this.valorNumericoLimiteCondicion )
                    ){
                return true;
            }
            
        }
        
        return false;
        
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.valorNumericoLimiteCondicion);
        hash = 97 * hash + Objects.hashCode(this.condicion);
        return hash;
    }
    
    
    
    
    public boolean esNoNegativa(){
        return (
                //Tiene que ser mayor o igual a ( 0 o un número superior, es decir, no es negativa )
                this.condicion.equals( CondicionRestriccionVariableDeDecision.MAYOR_O_IGUAL ) &&
                //Garantizar que el número sea 0 o más (No negativo)
                this.valorNumericoLimiteCondicion.esMayorOIgualACero()
                );
    }
    
    public boolean esNoPositiva(){
        return (
                //Tiene que ser menor o igual a ( 0 o un número menor, es decir, no positivo )
                this.condicion.equals( CondicionRestriccionVariableDeDecision.MENOR_O_IGUAL ) &&
                //Garantizar que el número sea 0 o menos (No positiva)
                this.valorNumericoLimiteCondicion.esMenorOIgualACero()
                );
    }
    
    public boolean esNoRestringida(){
        return this.condicion.equals( CondicionRestriccionVariableDeDecision.NO_RESTRINGIDA );
    }
    
}
