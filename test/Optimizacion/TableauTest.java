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
import Excepciones.DemasiadasIteracionesMetodoDosFasesException;
import Excepciones.SinSolucionesFactiblesException;
import Excepciones.TodosLosCoeficientesDeLaFilaSonCeroException;
import Excepciones.ZNoAcotadaException;
import Optimizacion.Enumeraciones.Igualdades;
import Optimizacion.Enumeraciones.Objetivo;
import Optimizacion.MetodoDeLasDosFases.MetodoDeLasDosFases;
import java.math.BigInteger;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;

/**
 *
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 */
public class TableauTest {
    
    
    private Tableau getTableauFinalParaProbar1(){
        
        /*
        Max Z = 5X1 + 3X2 - 4X3
        2X1 + 8X2 + 0X3 <= 150
        5X1 + 5X2 + 5X3 <= 425
        9X1 + 0X2 + 8X3 >= 10
        11X1 + 2X2 + 10X3 >= 25
        */
        Fraccion[] funcionObjetivo = {
            new Fraccion(5), new Fraccion(3), new Fraccion(-4)
        };
        Fraccion restricciones[][] = {
            { new Fraccion(2), new Fraccion(8), new Fraccion(0), new Fraccion(150) },
            { new Fraccion(5), new Fraccion(5), new Fraccion(5), new Fraccion(425) },
            { new Fraccion(9), new Fraccion(0), new Fraccion(8), new Fraccion(10) },
            { new Fraccion(11), new Fraccion(2), new Fraccion(10), new Fraccion(25) },
        };
        
        Igualdades igualdadesIngresadas[] = { Igualdades.MENOR_O_IGUAL, Igualdades.MENOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL };
        
        /**
         * Salida
         * X1        X2       X3       H1       H2       S1      S2        A1       A2       T.I.
         * ( 0 / 1 )( 0 / 1 )( 0 / 1 )( 0 / 1 )( 0 / 1 )( 0 / 1 )( 0 / 1 )( 1 / 1 )( 1 / 1 )( 0 / 1 )
         * ( 9 / 1 )( 7 / 1 )( 0 / 1 )( 0 / 1 )( 4 / 5 )( 0 / 1 )( 0 / 1 )( 0 / 1 )( 0 / 1 )( 340 / 1 )
         * ( 2 / 1 )( 8 / 1 )( 0 / 1 )( 1 / 1 )( 0 / 1 )( 0 / 1 )( 0 / 1 )( 0 / 1 )( 0 / 1 )( 150 / 1 )
         * ( -1 / 1 )( 8 / 1 )( 0 / 1 )( 0 / 1 )( 2 / 1 )( 0 / 1 )( 1 / 1 )( 0 / 1 )( -1 / 1 )( 825 / 1 )
         * ( 1 / 1 )( 1 / 1 )( 1 / 1 )( 0 / 1 )( 1 / 5 )( 0 / 1 )( 0 / 1 )( 0 / 1 )( 0 / 1 )( 85 / 1 )
         * ( -1 / 1 )( 8 / 1 )( 0 / 1 )( 0 / 1 )( 8 / 5 )( 1 / 1 )( 0 / 1 )( -1 / 1 )( 0 / 1 )( 670 / 1 )
         */

        try {
            DatosOptimizacion datosIngresados = new DatosOptimizacion(Objetivo.MINIMIZAR, funcionObjetivo, restricciones, igualdadesIngresadas);
            MetodoDeLasDosFases metodo = new MetodoDeLasDosFases( datosIngresados, true );
            
            
            return metodo.getTableau();
        } 
        catch (ZNoAcotadaException | DemasiadasIteracionesMetodoDosFasesException | SinSolucionesFactiblesException | TodosLosCoeficientesDeLaFilaSonCeroException ex) {
            ex.printStackTrace();
            
            return null;
        }
        
    }
    
    
    @Test
    public void testgetzAsteriscoMenosc1(){
        
        /**
         * Esperado
         * X1   X2      X3
         * 9	7	0	
         */
        Fraccion tableauEsperado[][] = {
            { new Fraccion( 9 ), new Fraccion( 7 ), new Fraccion( 0 ) }
        };
        
        assertArrayEquals( this.getTableauFinalParaProbar1().getzAsteriscoMenosc() , tableauEsperado );
        
    }
    
    
    @Test
    public void testgetyAsterisco1(){
        
        /**
         * Esperado
         *  H1       H2       S1       S2       A1       A2
         * ( 0 / 1 )( 4 / 5 )( 0 / 1 )( 0 / 1 )( 0 / 1 )( 0 / 1 )	
         */
        Fraccion tableauEsperado[][] = {
            { new Fraccion( 0 ), new Fraccion( new BigInteger("4"), new BigInteger("5") ), new Fraccion( 0 ), new Fraccion( 0 ), new Fraccion( 0 ), new Fraccion( 0 ) }
            
            
        };
        
        assertArrayEquals( tableauEsperado, this.getTableauFinalParaProbar1().getyAsterisco() );
        
    }
    
    
    @Test
    public void testgetZAsterisco1(){
        
        /**
         * Esperado
         * T.I.
         * 340	
         */
        Fraccion tableauEsperado[][] = {
            { new Fraccion( 340 ) }
            
            
        };
        
        assertArrayEquals( this.getTableauFinalParaProbar1().getZAsterisco() , tableauEsperado );
        
    }
    
    
    @Test
    public void testgetAAsterisco1(){
        
        /**
         * Esperado
         * X1        X2       X3
         * ( 2 / 1 )( 8 / 1 )( 0 / 1 )
         * ( -1 / 1 )( 8 / 1 )( 0 / 1 )
         * ( 1 / 1 )( 1 / 1 )( 1 / 1 )
         * ( -1 / 1 )( 8 / 1 )( 0 / 1 )
         */
        Fraccion tableauEsperado[][] = {
            { new Fraccion( 2 ), new Fraccion( 8 ), new Fraccion( 0 ) },
            { new Fraccion( -1 ), new Fraccion( 8 ), new Fraccion( 0 ) },
            { new Fraccion( 1 ), new Fraccion( 1 ), new Fraccion( 1 ) },
            { new Fraccion( -1 ), new Fraccion( 8 ), new Fraccion( 0 ) }
        };
        
        assertArrayEquals( this.getTableauFinalParaProbar1().getAAsterisco(), tableauEsperado );
        
    }
    
    
    @Test
    public void testgetSAsterisco1(){
        
        /**
         * Esperado
         *  H1       H2       S1      S2        A1       A2
         * ( 1 / 1 )( 0 / 1 )( 0 / 1 )( 0 / 1 )( 0 / 1 )( 0 / 1 )
         * ( 0 / 1 )( 2 / 1 )( 0 / 1 )( 1 / 1 )( 0 / 1 )( -1 / 1 )
         * ( 0 / 1 )( 1 / 5 )( 0 / 1 )( 0 / 1 )( 0 / 1 )( 0 / 1 )
         * ( 0 / 1 )( 8 / 5 )( 1 / 1 )( 0 / 1 )( -1 / 1 )( 0 / 1 )
         */
        Fraccion tableauEsperado[][] = {
            { new Fraccion( 1 ), new Fraccion( 0 ), new Fraccion( 0 ), new Fraccion( 0 ), new Fraccion( 0 ), new Fraccion( 0 ) },
            { new Fraccion( 0 ), new Fraccion( 2 ), new Fraccion( 0 ), new Fraccion( 1 ), new Fraccion( 0 ), new Fraccion( -1 ) },
            { new Fraccion( 0 ), new Fraccion( new BigInteger("1"), new BigInteger("5") ), new Fraccion( 0 ), new Fraccion( 0 ), new Fraccion( 0 ), new Fraccion( 0 ) },
            { new Fraccion( 0 ), new Fraccion( new BigInteger("8"), new BigInteger("5") ), new Fraccion( 1 ), new Fraccion( 0 ), new Fraccion( -1 ), new Fraccion( 0 ) }
            
            
        };
        
        assertArrayEquals( this.getTableauFinalParaProbar1().getSAsterisco() , tableauEsperado );
        
    }
    
    
    @Test
    public void testgetbAsterisco1(){
        
        /**
         * Esperado
         *  T.I.
         * ( 150 / 1 )
         * ( 825 / 1 )
         * ( 85 / 1 )
         * ( 670 / 1 )
         */
        Fraccion tableauEsperado[][] = {
            { new Fraccion( 150 ) },
            { new Fraccion( 825 ) },
            { new Fraccion( 85 ) },
            { new Fraccion( 670 ) }
        };
        
        assertArrayEquals( this.getTableauFinalParaProbar1().getbAsterisco() , tableauEsperado );
        
    }
    
}
