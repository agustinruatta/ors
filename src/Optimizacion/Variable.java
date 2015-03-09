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

import Optimizacion.Enumeraciones.TipoVariable;
import java.util.Objects;


public class Variable {
    public static final Variable ZETA = new Variable(TipoVariable.Z, 1);
    public static final Variable M = new Variable(TipoVariable.M, 1);
    
    
    /**
     * Representa el número de variable que es.
     * Empieza en 1 y termina en (Cantidad total de éste tipo de variables).
     */
    private final int numeroVariable;
    
    /**
     * Tipo de variable que es.
     */
    private final TipoVariable tipoVariable;
    
    private final RestriccionVariableDeDecision restriccionVariableDeDecision;

    /**
     * Constructor predeterminado.
     * Establece el tipo y número de variable como los enviado como parámetro.
     * Además establece la restriccion de la variable de decisión como Xn>=0 (Lo más común).
     * @param tipoVariable Tipo de variable que es.
     * @param numeroVariable Número de variable que es.
     */
    public Variable(TipoVariable tipoVariable, int numeroVariable) {
        this(tipoVariable, numeroVariable, new RestriccionVariableDeDecision());
    }

    /**
     * Constructor.
     * @param numeroVariable
     * @param tipoVariable
     * @param restriccionVariableDeDecision 
     */
    public Variable(TipoVariable tipoVariable, int numeroVariable, RestriccionVariableDeDecision restriccionVariableDeDecision) {
        this.numeroVariable = numeroVariable;
        this.tipoVariable = tipoVariable;
        this.restriccionVariableDeDecision = restriccionVariableDeDecision;
    }
    
    
    

    public int getNumeroVariable() {
        return numeroVariable;
    }

    public TipoVariable getTipoVariable() {
        return tipoVariable;
    }

    public RestriccionVariableDeDecision getRestriccionVariableDeDecision() {
        return restriccionVariableDeDecision;
    }
    
    
    
    
    /**
     * Indica si la variable actual es igual al objeto enviado como
     * parametro.
     * Para ser iguales deben tener el mismo tipo y número de Variable.
     * @param objetoCualquiera Objeto con que se va a comprar el objeto actual.
     * @return true si son iguales, false caso contrario.
     */
    @Override
    public boolean equals( Object objetoCualquiera ){
        //Si es el mismo objeto
        if (this == objetoCualquiera) {
            return true;
        }
        if (objetoCualquiera instanceof Variable) {
            Variable variable= (Variable) objetoCualquiera;
            
            //Si los objetos tienen el mismo numerador y denominador
            if( 
                    variable.getTipoVariable().equals( this.tipoVariable ) &&
                    variable.getNumeroVariable() == this.numeroVariable &&
                    variable.getRestriccionVariableDeDecision().equals( this.restriccionVariableDeDecision )
                    ){
                return true;
            }
        }
        
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + this.numeroVariable;
        hash = 11 * hash + Objects.hashCode(this.tipoVariable);
        hash = 11 * hash + Objects.hashCode(this.restriccionVariableDeDecision);
        return hash;
    }
    
    @Override
    public String toString(){
        return tipoVariable + Integer.toString( numeroVariable );
    }
}
