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

package Optimizacion.AnalisisDeSensibilidad;

import API.Fraccion;
import Optimizacion.Tableau;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;

/**
 *
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 */
public class CambiosEnLabTest {
    
    private Tableau getTableauFinal(){
        
        Fraccion[][] tableauFinal ={
            { new Fraccion( 0 ), new Fraccion( 0 ), new Fraccion( 0 ), new Fraccion( 0 ), new Fraccion( 0 ), new Fraccion( 0 ) },
            { new Fraccion( 0 ), new Fraccion( 0 ), new Fraccion( 0 ), new Fraccion( 3, 2 ), new Fraccion( 1 ), new Fraccion( 36 ) },
            { new Fraccion( 0 ), new Fraccion( 0 ), new Fraccion( 1 ), new Fraccion( 1, 3 ), new Fraccion( -1, 3 ), new Fraccion( 2 ) },
            { new Fraccion( 0 ), new Fraccion( 1 ), new Fraccion( 0 ), new Fraccion( 1, 2 ), new Fraccion( 0 ), new Fraccion( 6 ) },
            { new Fraccion( 1 ), new Fraccion( 0 ), new Fraccion( 0 ), new Fraccion( -1, 3 ), new Fraccion( 1, 3 ), new Fraccion( 2 ) }
        
        };
        
        return new Tableau( tableauFinal, 2, 2, 2);
    }
    
    private Fraccion[][] getNuevosTI(){
        Fraccion[][] nuevoTI = {
            { new Fraccion( 4 ) },
            { new Fraccion( 24 ) },
            { new Fraccion( 18 ) },
        };
        
        return nuevoTI;
    }
    
    
    
    @Test
    public void testArgumentosValidos1(){
        
        Fraccion[][] nuevosTi ={
            { new Fraccion(4) },
            { new Fraccion(4) },
            { new Fraccion(4) },
        };
        
        try {
            CambiosEnLab cambiosEnLab = new CambiosEnLab( this.getTableauFinal() , nuevosTi );
            assert true;
        }
        catch (IllegalArgumentException e) {
            assert false;
        }
        
    }
    
    
    @Test
    public void testArgumentosInvalidos1(){
        
        Fraccion[][] nuevosTi ={
            { new Fraccion(4) },
            { new Fraccion(4) },
        };
        
        try {
            CambiosEnLab cambiosEnLab = new CambiosEnLab( this.getTableauFinal() , nuevosTi );
            assert false;
        }
        catch (IllegalArgumentException e) {
            assert true;
        }
        
    }
    
    @Test
    public void testArgumentosInvalidos2(){
        
        Fraccion[][] nuevosTi ={
            { new Fraccion(4) },
            { new Fraccion(4) },
            { new Fraccion(4) },
            { new Fraccion(4) },
        };
        
        try {
            CambiosEnLab cambiosEnLab = new CambiosEnLab( this.getTableauFinal() , nuevosTi );
            assert false;
        }
        catch (IllegalArgumentException e) {
            assert true;
        }
        
    }
    
    
    @Test
    public void testGetNuevoZAsterisco1(){
        CambiosEnLab cambiosEnLab = new CambiosEnLab( this.getTableauFinal() , this.getNuevosTI() );
        
        Fraccion[][] nuevoZAsteriscoEsperado = {
            {new Fraccion(54)}
        };
        
        assertArrayEquals( nuevoZAsteriscoEsperado, cambiosEnLab.getNuevoZAsterisco() );
        
    }
    
    
    @Test
    public void testGetNuevobAsterisco1(){
        CambiosEnLab cambiosEnLab = new CambiosEnLab( this.getTableauFinal() , this.getNuevosTI() );
        
        Fraccion[][] nuevobAsteriscoEsperado = {
            { new Fraccion(6) },
            { new Fraccion(12) },
            { new Fraccion(-2) }
        };
        
        assertArrayEquals( nuevobAsteriscoEsperado, cambiosEnLab.getNuevobAsterisco() );
    }
    
}
