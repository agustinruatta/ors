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
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import org.junit.Test;

/**
 *
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 */
public class RestriccionVariableDeDecisionTest {
    
    @Test
    public void testGetRestriccionesPorDefecto1(){
        
        RestriccionVariableDeDecision[] restriccionEsperada = { RestriccionVariableDeDecision.RESTRICCION_POR_DEFECTO, RestriccionVariableDeDecision.RESTRICCION_POR_DEFECTO, RestriccionVariableDeDecision.RESTRICCION_POR_DEFECTO };
        
        assertArrayEquals(restriccionEsperada, RestriccionVariableDeDecision.getRestriccionesPorDefecto( restriccionEsperada.length ));
        
    }
    
    @Test
    public void testConstructorPredeterminado(){
        RestriccionVariableDeDecision restriccionConstructorPredeterminado = new RestriccionVariableDeDecision();
        RestriccionVariableDeDecision restriccionConstructorNoPredeterminado = new RestriccionVariableDeDecision( CondicionRestriccionVariableDeDecision.MAYOR_O_IGUAL, Fraccion.CERO );
        
        assertEquals(  restriccionConstructorPredeterminado, restriccionConstructorNoPredeterminado);
    }

    @Test
    public void testEquals1() {
        RestriccionVariableDeDecision restriccion1 = new RestriccionVariableDeDecision( CondicionRestriccionVariableDeDecision.MAYOR_O_IGUAL, Fraccion.MENOS_DOS );
        RestriccionVariableDeDecision restriccion2 = new RestriccionVariableDeDecision( CondicionRestriccionVariableDeDecision.MAYOR_O_IGUAL, Fraccion.MENOS_DOS );
        
        assertEquals(restriccion1, restriccion2);
    }
    
    @Test
    public void testEquals2() {
        
        RestriccionVariableDeDecision restriccion1 = new RestriccionVariableDeDecision( CondicionRestriccionVariableDeDecision.MAYOR_O_IGUAL, Fraccion.MENOS_DOS );
        RestriccionVariableDeDecision restriccion2 = new RestriccionVariableDeDecision();
        
        assertNotSame(restriccion1, restriccion2);
        
    }
    
}
