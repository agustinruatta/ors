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
import Optimizacion.Enumeraciones.TipoVariable;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 */
public class VariableTest {
    
    public VariableTest() {
    }

    /**
     * Prueba que el constructor predeterminado sea igual a la variable
     * con la restriccion X1>=0.
     */
    @Test
    public void testConstructorPredeterminado1() {
        Variable variable1 = new Variable( TipoVariable.DECISION, 1);
        Variable variable2 = new Variable( TipoVariable.DECISION, 1, new RestriccionVariableDeDecision() );
        
        assertEquals( variable1, variable2 );
    }
    
    /**
     * Prueba que el constructor predeterminado no es igual a otra variable
     * que se le indica otra restricción (Particularmente X1>=2)
     */
    @Test
    public void testConstructorPredeterminado2() {
        Variable variable1 = new Variable( TipoVariable.DECISION, 1);
        Variable variable2 = new Variable( TipoVariable.DECISION, 1, new RestriccionVariableDeDecision( CondicionRestriccionVariableDeDecision.MAYOR_O_IGUAL, Fraccion.DOS ) );
        
        assertNotSame(variable1, variable2 );
    }
    
    
    
    
    /**
     * Comprueba que 2 iguales variables sean iguales.
     */
    @Test
    public void testEquals1(){
        
        Variable variableA = new Variable( TipoVariable.DECISION, 1, new RestriccionVariableDeDecision( CondicionRestriccionVariableDeDecision.MAYOR_O_IGUAL, Fraccion.CUATRO)  );
        Variable variableB = new Variable( TipoVariable.DECISION, 1, new RestriccionVariableDeDecision( CondicionRestriccionVariableDeDecision.MAYOR_O_IGUAL, Fraccion.CUATRO)  );
        
        assertEquals( variableA, variableB );
    }
    
    
    /**
     * Comprueba que 2 iguales distintas no sean iguales.
     * Cambia el tipo de variable.
     */
    @Test
    public void testEquals2(){
        
        Variable variableA = new Variable( TipoVariable.DECISION, 1, new RestriccionVariableDeDecision( CondicionRestriccionVariableDeDecision.MAYOR_O_IGUAL, Fraccion.CUATRO)  );
        Variable variableB = new Variable( TipoVariable.HOLGURA, 1, new RestriccionVariableDeDecision( CondicionRestriccionVariableDeDecision.MAYOR_O_IGUAL, Fraccion.CUATRO)  );
        
        assertNotSame(variableA, variableB );
    }
    
    /**
     * Comprueba que 2 iguales distintas no sean iguales.
     * Cambia el número de la variable.
     */
    @Test
    public void testEquals3(){
        
        Variable variableA = new Variable( TipoVariable.DECISION, 1, new RestriccionVariableDeDecision( CondicionRestriccionVariableDeDecision.MAYOR_O_IGUAL, Fraccion.CUATRO)  );
        Variable variableB = new Variable( TipoVariable.DECISION, 2, new RestriccionVariableDeDecision( CondicionRestriccionVariableDeDecision.MAYOR_O_IGUAL, Fraccion.CUATRO)  );
        
        assertNotSame( variableA, variableB );
    }
    
    /**
     * Comprueba que 2 iguales distintas no sean iguales.
     * Cambia la restricción de la variable de decisión.
     */
    @Test
    public void testEquals4(){
        
        Variable variableA = new Variable( TipoVariable.DECISION, 1, new RestriccionVariableDeDecision( CondicionRestriccionVariableDeDecision.MAYOR_O_IGUAL, Fraccion.CUATRO)  );
        Variable variableB = new Variable( TipoVariable.DECISION, 2, RestriccionVariableDeDecision.RESTRICCION_POR_DEFECTO  );
        
        assertNotSame( variableA, variableB );
    }
    
}
