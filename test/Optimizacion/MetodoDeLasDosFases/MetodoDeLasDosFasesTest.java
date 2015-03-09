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

package Optimizacion.MetodoDeLasDosFases;

import API.Fraccion;
import Excepciones.DemasiadasIteracionesMetodoDosFasesException;
import Excepciones.SinSolucionesFactiblesException;
import Excepciones.TodosLosCoeficientesDeLaFilaSonCeroException;
import Excepciones.ZNoAcotadaException;
import Optimizacion.DatosOptimizacion;
import Optimizacion.Enumeraciones.Igualdades;
import Optimizacion.Enumeraciones.Objetivo;
import org.junit.Assert;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;

/**
 *
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 */
public class MetodoDeLasDosFasesTest {
    
    
    @Test
    public void testGeneracionDeTableau1() throws TodosLosCoeficientesDeLaFilaSonCeroException{
        
        
        /*
        Entrada:
        1 1
        1 1 <= 1
        1 1 = 1
        1 1 >= 1
        */
        Fraccion[] funcionObjetivo = {
            Fraccion.UNO, Fraccion.UNO
        };
        Fraccion restricciones[][] = {
            {Fraccion.UNO, Fraccion.UNO, Fraccion.UNO},
            {Fraccion.UNO, Fraccion.UNO, Fraccion.UNO},
            {Fraccion.UNO, Fraccion.UNO, Fraccion.UNO}
        };
        
        Igualdades igualdadesIngresadas[] = { 
            Igualdades.MENOR_O_IGUAL,
            Igualdades.IGUAL,
            Igualdades.MAYOR_O_IGUAL
        };
        
        
        /*
        Salida:
            X1  X2  H   S   A1  A2  =   TI
        M   -2  -2  0   1   0   0  =   -2
        Z   -1  -1  0   0   0   0   =   0
        H1  1   1   1   0   0   0   <=  1
        A1  1   1   0   0   1   0   =   1
        A2  1   1   0   -1  0   1   >=  1
        */
        Fraccion tableauEsperado[][] = {
            {Fraccion.MENOS_DOS, Fraccion.MENOS_DOS, Fraccion.CERO, Fraccion.UNO, Fraccion.CERO, Fraccion.CERO, Fraccion.MENOS_DOS},
            {Fraccion.MENOS_UNO, Fraccion.MENOS_UNO, Fraccion.CERO, Fraccion.CERO, Fraccion.CERO, Fraccion.CERO, Fraccion.CERO},
            {Fraccion.UNO, Fraccion.UNO, Fraccion.UNO, Fraccion.CERO, Fraccion.CERO, Fraccion.CERO, Fraccion.UNO},
            {Fraccion.UNO, Fraccion.UNO, Fraccion.CERO, Fraccion.CERO, Fraccion.UNO, Fraccion.CERO, Fraccion.UNO},
            {Fraccion.UNO, Fraccion.UNO, Fraccion.CERO, Fraccion.MENOS_UNO, Fraccion.CERO, Fraccion.UNO, Fraccion.UNO},
        };
        
        try {
            DatosOptimizacion datosIngresados = new DatosOptimizacion(Objetivo.MAXIMIZAR, funcionObjetivo, restricciones, igualdadesIngresadas);
            MetodoDeLasDosFases metodo = new MetodoDeLasDosFases( datosIngresados );

            Fraccion[][] tableauDevuelto = metodo.getTableau().getTableauEnMatriz();

            assertArrayEquals(tableauEsperado, tableauDevuelto);
        }
        catch ( DemasiadasIteracionesMetodoDosFasesException | SinSolucionesFactiblesException | ZNoAcotadaException | IllegalArgumentException e ) {
            e.printStackTrace();
        }
        
        
    }
    
    @Test
    public void testGeneracionDeTableau2() throws TodosLosCoeficientesDeLaFilaSonCeroException{
        
        /*
        Entrada:
        -8  3   6
        1   -3  5   =   1
        5   3   -4  >=  1
        */
        Fraccion[] funcionObjetivo = {
            new Fraccion(-8), new Fraccion(3), new Fraccion(6)
        };
        Fraccion restricciones[][] = {
            { new Fraccion(1), new Fraccion(-3), new Fraccion(5), new Fraccion(1)},
            { new Fraccion(5), new Fraccion(3), new Fraccion(-4), new Fraccion(1)},
        };
        
        Igualdades igualdadesIngresadas[] = {
            Igualdades.IGUAL,
            Igualdades.MAYOR_O_IGUAL
        };
        
        
        /*
        Salida:
            X1  X2  X3  S1  A1  A2  =   TI
        M   -6  0   -1  1   0   0  =   -2   
        Z   8   -3  -6  0   0  0   =   0 
        A1  1   -3  5   0   1   0   =   1
        A2  5   3   -4  -1  0   1   >=  1
        */
        Fraccion tableauEsperado[][] = {
            { new Fraccion( -6 ), new Fraccion( 0 ), new Fraccion( -1 ), new Fraccion( 1 ), new Fraccion( 0 ), new Fraccion( 0 ), new Fraccion( -2 )},
            { new Fraccion( 8 ), new Fraccion( -3 ), new Fraccion( -6 ), new Fraccion( 0 ), new Fraccion( 0 ), new Fraccion( 0 ), new Fraccion( 0 )},
            { new Fraccion( 1 ), new Fraccion( -3 ), new Fraccion( 5 ), new Fraccion( 0 ), new Fraccion( 1 ), new Fraccion( 0 ), new Fraccion( 1 )},
            { new Fraccion( 5 ), new Fraccion( 3 ), new Fraccion( -4 ), new Fraccion( -1 ), new Fraccion( 0 ), new Fraccion( 1 ), new Fraccion( 1 )},
            
        };
        
        try {
            DatosOptimizacion datosIngresados = new DatosOptimizacion(Objetivo.MAXIMIZAR, funcionObjetivo, restricciones, igualdadesIngresadas);
            MetodoDeLasDosFases metodo = new MetodoDeLasDosFases( datosIngresados );

            Fraccion[][] tableauDevuelto = metodo.getTableau().getTableauEnMatriz();

            assertArrayEquals(tableauEsperado, tableauDevuelto);
        }
        catch ( DemasiadasIteracionesMetodoDosFasesException | SinSolucionesFactiblesException | ZNoAcotadaException | IllegalArgumentException e ) {
            e.printStackTrace();
        }
        
        
    }
    
    @Test
    public void testGeneracionDeTableau3() throws TodosLosCoeficientesDeLaFilaSonCeroException{
        
        /*
        Min Z  = 2X1 +  X2 + 3X3
        Sujeto a:
            3X1 +   X2 + 2X3   <=  -10  ->  -3X1 -   X2 - 2X3   >=  10
            X1 -  2X2 + 3X3   >=  6
            2X1 +  3X2 -  X3   <=  9                   
            X1 +  X2  +  2X3  =  7
        */
        Fraccion[] funcionObjetivo = {
            new Fraccion(2), new Fraccion(1), new Fraccion(3)
        };
        Fraccion restricciones[][] = {
            { new Fraccion(3), new Fraccion(1), new Fraccion(2), new Fraccion(-10)},
            { new Fraccion(1), new Fraccion(-2), new Fraccion(3), new Fraccion(6)},
            { new Fraccion(2), new Fraccion(3), new Fraccion(-1), new Fraccion(9)},
            { new Fraccion(1), new Fraccion(1), new Fraccion(2), new Fraccion(7)}
        };
        
        Igualdades igualdadesIngresadas[] = {
            Igualdades.MENOR_O_IGUAL,
            Igualdades.MAYOR_O_IGUAL,
            Igualdades.MENOR_O_IGUAL,
            Igualdades.IGUAL
        };
        
        
        /*
        Salida:
        V   X1  X2  X3  H1  S1  S2  A1  A2  A3  TI
        M   1   2   -3  0   1   1   0   0   0  -23  
        Z   2   1   3   0   0   0   0   0   0   0
        A1  -3  -1  -2  0   -1  0   1   0   0   10
        A2  1   -2  3   0   0   -1  0   1   0    6
        H1  2 +  3  -1  1   0   0   0   0   0    9                   
        A3  1 +  1  2   0   0   0   0   0   1    7
        */
        Fraccion tableauEsperado[][] = {
            { new Fraccion( 1 ), new Fraccion( 2 ), new Fraccion( -3 ), new Fraccion( 0 ), new Fraccion( 1 ), new Fraccion( 1 ), new Fraccion( 0 ), new Fraccion( 0 ), new Fraccion( 0 ), new Fraccion( -23 )},
            { new Fraccion( 2 ), new Fraccion( 1 ), new Fraccion( 3 ), new Fraccion( 0 ), new Fraccion( 0 ), new Fraccion( 0 ), new Fraccion( 0 ), new Fraccion( 0 ), new Fraccion( 0 ), new Fraccion( 0 )},
            { new Fraccion( -3 ), new Fraccion( -1 ), new Fraccion( -2 ), new Fraccion( 0 ), new Fraccion( -1 ), new Fraccion( 0 ), new Fraccion( 1 ), new Fraccion( 0 ), new Fraccion( 0 ), new Fraccion( 10 )},
            { new Fraccion( 1 ), new Fraccion( -2 ), new Fraccion( 3 ), new Fraccion( 0 ), new Fraccion( 0 ), new Fraccion( -1 ), new Fraccion( 0 ), new Fraccion( 1 ), new Fraccion( 0 ), new Fraccion( 6 )},
            { new Fraccion( 2 ), new Fraccion( 3 ), new Fraccion( -1 ), new Fraccion( 1 ), new Fraccion( 0 ), new Fraccion( 0 ), new Fraccion( 0 ), new Fraccion( 0 ), new Fraccion( 0 ), new Fraccion( 9 )},
            { new Fraccion( 1 ), new Fraccion( 1 ), new Fraccion( 2 ), new Fraccion( 0 ), new Fraccion( 0 ), new Fraccion( 0 ), new Fraccion( 0 ), new Fraccion( 0 ), new Fraccion( 1 ), new Fraccion( 7 )},
            
            
        };
        
        try {
            DatosOptimizacion datosIngresados = new DatosOptimizacion(Objetivo.MINIMIZAR, funcionObjetivo, restricciones, igualdadesIngresadas);
            MetodoDeLasDosFases metodo = new MetodoDeLasDosFases( datosIngresados );

            Fraccion[][] tableauDevuelto = metodo.getTableau().getTableauEnMatriz();

            assertArrayEquals(tableauEsperado, tableauDevuelto);
        }
        catch ( DemasiadasIteracionesMetodoDosFasesException | SinSolucionesFactiblesException | ZNoAcotadaException | IllegalArgumentException e ) {
            e.printStackTrace();
        }
        
        
    }
    
    
    
    
    @Test
    public void testSeResolvioBienElTableu1() throws TodosLosCoeficientesDeLaFilaSonCeroException{
        
        
        /*
        Max Z = X1 + X2
        X1 + X2  <= 2
        X1 - 2X2 <= 2
        */
        Fraccion[] funcionObjetivo = {
            new Fraccion(1), new Fraccion(1)
        };
        Fraccion restricciones[][] = {
            { new Fraccion(1), new Fraccion(1), new Fraccion(2)},
            { new Fraccion(1), new Fraccion(-2), new Fraccion(2)}
        };
        
        Igualdades igualdadesIngresadas[] = { Igualdades.MENOR_O_IGUAL, Igualdades.MENOR_O_IGUAL };
        
        /*
        Solucion esperada
        X1 = 2
        X2 = 0
        */
        Fraccion solucionEsperada[] = { new Fraccion(2), new Fraccion(0)};
        
        
        
        
        try {
            DatosOptimizacion datosIngresados = new DatosOptimizacion(Objetivo.MAXIMIZAR, funcionObjetivo, restricciones, igualdadesIngresadas);
            MetodoDeLasDosFases metodo = new MetodoDeLasDosFases( datosIngresados );
            
            metodo.resolver();
            
            Fraccion solucionesRecibidas[] = metodo.getSoluciones();
        
            assertArrayEquals(solucionEsperada, solucionesRecibidas);
        } catch (ZNoAcotadaException | DemasiadasIteracionesMetodoDosFasesException | SinSolucionesFactiblesException ex) {
            ex.printStackTrace();
        }
        
        
    }
    
    @Test
    public void testSeResolvioBienElTableu2() throws IllegalArgumentException, TodosLosCoeficientesDeLaFilaSonCeroException{
        
        
        /*
        Max Z = 30X1 + 50X2
        2X1 + X2  <= 16
        X1  + 2X2   <= 11
        X1  + 3X2  <= 15
        */
        Fraccion[] funcionObjetivo = {
            new Fraccion(30), new Fraccion(50)
        };
        Fraccion restricciones[][] = {
            { new Fraccion(2), new Fraccion(1), new Fraccion(16)},
            { new Fraccion(1), new Fraccion(2), new Fraccion(11)},
            { new Fraccion(1), new Fraccion(3), new Fraccion(15)}
        };
        
        Igualdades igualdadesIngresadas[] = { Igualdades.MENOR_O_IGUAL, Igualdades.MENOR_O_IGUAL, Igualdades.MENOR_O_IGUAL };
        
        /*
        Solucion esperada
        X1 = 7
        X2 = 2
        */
        Fraccion solucionEsperada[] = { new Fraccion(7), new Fraccion(2)};
        
        
        

        try {
            DatosOptimizacion datosIngresados = new DatosOptimizacion(Objetivo.MAXIMIZAR, funcionObjetivo, restricciones, igualdadesIngresadas);
            MetodoDeLasDosFases metodo = new MetodoDeLasDosFases( datosIngresados );
            
            metodo.resolver();
            
            Fraccion solucionesRecibidas[] = metodo.getSoluciones();
        
            assertArrayEquals(solucionEsperada, solucionesRecibidas);
        }
        catch (ZNoAcotadaException | DemasiadasIteracionesMetodoDosFasesException | SinSolucionesFactiblesException ex) {
            ex.printStackTrace();
        }
        
    }
    
    @Test
    public void testSeResolvioBienElTableu3() throws TodosLosCoeficientesDeLaFilaSonCeroException{
        
        
        /*
        Min Z = 0.4X1 + 0.5X2
        0.3X1 + 0.1X2 <= 2.7
        0.5X1 + 0.5X2 = 6
        0.6X1 + 0.4X2 >= 6
        */
        Fraccion[] funcionObjetivo = {
            new Fraccion(0.4), new Fraccion(0.5)
        };
        Fraccion restricciones[][] = {
            { new Fraccion(0.3), new Fraccion(0.1), new Fraccion(2.7)},
            { new Fraccion(0.5), new Fraccion(0.5), new Fraccion(6)},
            { new Fraccion(0.6), new Fraccion(0.4), new Fraccion(6)}
        };
        
        Igualdades igualdadesIngresadas[] = { Igualdades.MENOR_O_IGUAL, Igualdades.IGUAL, Igualdades.MAYOR_O_IGUAL };
        
        /*
        Solucion esperada
        X1 = 7.5
        X2 = 4.5
        */
        Fraccion solucionEsperada[] = { new Fraccion(7.5), new Fraccion(4.5)};
        
        

        try {
            DatosOptimizacion datosIngresados = new DatosOptimizacion(Objetivo.MINIMIZAR, funcionObjetivo, restricciones, igualdadesIngresadas);
            MetodoDeLasDosFases metodo = new MetodoDeLasDosFases( datosIngresados );
            
            metodo.resolver();
            
            Fraccion solucionesRecibidas[] = metodo.getSoluciones();
        
            assertArrayEquals(solucionEsperada, solucionesRecibidas);
        } 
        catch (ZNoAcotadaException | DemasiadasIteracionesMetodoDosFasesException | SinSolucionesFactiblesException ex) {
            ex.printStackTrace();
        }
        
        
    }
    
    
    @Test
    public void testSeResolvioBienElTableu4() throws TodosLosCoeficientesDeLaFilaSonCeroException{
        
        
        /*
        Max Z = 3X1 + 5X2
        X1        <= 4
              2X2 <= 12
        3X1 + 2X2 <= 18
        */
        Fraccion[] funcionObjetivo = {
            new Fraccion(3), new Fraccion(5)
        };
        Fraccion restricciones[][] = {
            { new Fraccion(1), new Fraccion(0), new Fraccion(4)},
            { new Fraccion(0), new Fraccion(2), new Fraccion(12)},
            { new Fraccion(3), new Fraccion(2), new Fraccion(18)}
        };
        
        Igualdades igualdadesIngresadas[] = { Igualdades.MENOR_O_IGUAL, Igualdades.MENOR_O_IGUAL, Igualdades.MENOR_O_IGUAL };
        
        /*
        Solucion esperada
        X1 = 2
        X2 = 6
        */
        Fraccion solucionEsperada[] = { new Fraccion(2), new Fraccion(6)};
        
        

        try {
            DatosOptimizacion datosIngresados = new DatosOptimizacion(Objetivo.MAXIMIZAR, funcionObjetivo, restricciones, igualdadesIngresadas);
            MetodoDeLasDosFases metodo = new MetodoDeLasDosFases( datosIngresados );
            
            metodo.resolver();
            
            Fraccion solucionesRecibidas[] = metodo.getSoluciones();
        
            assertArrayEquals(solucionEsperada, solucionesRecibidas);
        } catch (ZNoAcotadaException | DemasiadasIteracionesMetodoDosFasesException | SinSolucionesFactiblesException ex) {
            ex.printStackTrace();
        }
        
        
    }
    
    @Test
    public void testSeResolvioBienElTableu5() throws TodosLosCoeficientesDeLaFilaSonCeroException{
        
        
        /*
        Min Z = 2000X1 + 500X2
        2X1 + 3X2 >= 36
        3X1 + 6X2 >= 60
        */
        Fraccion[] funcionObjetivo = {
            new Fraccion(2000), new Fraccion(500)
        };
        Fraccion restricciones[][] = {
            { new Fraccion(2), new Fraccion(3), new Fraccion(36)},
            { new Fraccion(3), new Fraccion(6), new Fraccion(60)}
        };
        
        Igualdades igualdadesIngresadas[] = { Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL };
        
        /*
        Solucion esperada
        X1 = 0
        X2 = 12
        */
        Fraccion solucionEsperada[] = { new Fraccion(0), new Fraccion(12)};
        
        

        try {
            DatosOptimizacion datosIngresados = new DatosOptimizacion(Objetivo.MINIMIZAR, funcionObjetivo, restricciones, igualdadesIngresadas);
            MetodoDeLasDosFases metodo = new MetodoDeLasDosFases( datosIngresados );
            
            metodo.resolver();
            
            Fraccion solucionesRecibidas[] = metodo.getSoluciones();
            
            assertArrayEquals(solucionEsperada, solucionesRecibidas);
        } catch (ZNoAcotadaException | DemasiadasIteracionesMetodoDosFasesException | SinSolucionesFactiblesException ex) {
            ex.printStackTrace();
        }
        
        
    }
    
    
    @Test
    public void testSeResolvioBienElTableu6() throws TodosLosCoeficientesDeLaFilaSonCeroException{
        
        
        /*
        Max Z = 170X1 + 160X2 + 175X3 + 180X4 + 195X5
        X1 >= 48
        X1 + X2 >= 79
        X1 + X2 >= 65
        X1 + X2 + X3 >= 87
             X2 + X3 >= 64
                  X3 + X4 >= 73
                  X3 + X4 >=82
                       X4 >= 43
                       X4 + X5 >= 52
                       X5 >= 15
        */
        Fraccion[] funcionObjetivo = {
            new Fraccion(170), new Fraccion(160), new Fraccion(175), new Fraccion(180), new Fraccion(195)
        };
        Fraccion restricciones[][] = {
            { new Fraccion(1), new Fraccion(0), new Fraccion(0), new Fraccion(0), new Fraccion(0), new Fraccion(48)},
            { new Fraccion(1), new Fraccion(1), new Fraccion(0), new Fraccion(0), new Fraccion(0), new Fraccion(79)},
            { new Fraccion(1), new Fraccion(1), new Fraccion(0), new Fraccion(0), new Fraccion(0), new Fraccion(65)},
            { new Fraccion(1), new Fraccion(1), new Fraccion(1), new Fraccion(0), new Fraccion(0), new Fraccion(87)},
            { new Fraccion(0), new Fraccion(1), new Fraccion(1), new Fraccion(0), new Fraccion(0), new Fraccion(64)},
            { new Fraccion(0), new Fraccion(0), new Fraccion(1), new Fraccion(1), new Fraccion(0), new Fraccion(73)},
            { new Fraccion(0), new Fraccion(0), new Fraccion(1), new Fraccion(1), new Fraccion(0), new Fraccion(82)},
            { new Fraccion(0), new Fraccion(0), new Fraccion(0), new Fraccion(1), new Fraccion(0), new Fraccion(43)},
            { new Fraccion(0), new Fraccion(0), new Fraccion(0), new Fraccion(1), new Fraccion(1), new Fraccion(52)},
            { new Fraccion(0), new Fraccion(0), new Fraccion(0), new Fraccion(0), new Fraccion(1), new Fraccion(15)},
        };
        
        Igualdades igualdadesIngresadas[] = { Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL };
        
        /*
        Solucion esperada
        X1 = 48
        X2 = 31
        X3 = 39
        X4 = 43
        X5 = 15
        */
        Fraccion solucionEsperada[] = { new Fraccion(48), new Fraccion(31), new Fraccion(39), new Fraccion(43), new Fraccion(15)};
        
        

        try {
            DatosOptimizacion datosIngresados = new DatosOptimizacion(Objetivo.MINIMIZAR, funcionObjetivo, restricciones, igualdadesIngresadas);
            MetodoDeLasDosFases metodo = new MetodoDeLasDosFases( datosIngresados );
            
            metodo.resolver();
            
            Fraccion solucionesRecibidas[] = metodo.getSoluciones();
            
            assertArrayEquals(solucionEsperada, solucionesRecibidas);
        } catch (ZNoAcotadaException | DemasiadasIteracionesMetodoDosFasesException | SinSolucionesFactiblesException ex) {
            ex.printStackTrace();
        }
        
        
    }
    
    
    @Test
    public void testSeResolvioBienElTableu7() throws TodosLosCoeficientesDeLaFilaSonCeroException{
        
        
        /*
        Min Z = 0.4X1 + 0.5X2
        -0.3X1 - 0.1X2 >= 2.7
        0.5X1 + 0.5X2 = 6
        0.6X1 + 0.4X2 >= 6
        */
        Fraccion[] funcionObjetivo = {
            new Fraccion(0.4), new Fraccion(0.5)
        };
        Fraccion restricciones[][] = {
            { new Fraccion(-0.3), new Fraccion(-0.1), new Fraccion(-2.7)},
            { new Fraccion(0.5), new Fraccion(0.5), new Fraccion(6)},
            { new Fraccion(0.6), new Fraccion(0.4), new Fraccion(6)}
        };
        
        Igualdades igualdadesIngresadas[] = { Igualdades.MAYOR_O_IGUAL, Igualdades.IGUAL, Igualdades.MAYOR_O_IGUAL };
        
        /*
        Solucion esperada
        X1 = 7.5
        X2 = 4.5
        */
        Fraccion solucionEsperada[] = { new Fraccion(7.5), new Fraccion(4.5)};
        
        

        try {
            DatosOptimizacion datosIngresados = new DatosOptimizacion(Objetivo.MINIMIZAR, funcionObjetivo, restricciones, igualdadesIngresadas);
            MetodoDeLasDosFases metodo = new MetodoDeLasDosFases( datosIngresados );
            
            metodo.resolver();
            
            Fraccion solucionesRecibidas[] = metodo.getSoluciones();
            
            assertArrayEquals(solucionEsperada, solucionesRecibidas);
        } catch (ZNoAcotadaException | DemasiadasIteracionesMetodoDosFasesException | SinSolucionesFactiblesException ex) {
            ex.printStackTrace();
        }
        
        
    }
    
    
    /**
     * Solución no acotada.
     */
    @Test
    public void testSeResolvioBienElTableu8() throws TodosLosCoeficientesDeLaFilaSonCeroException{
        
        /*
        Max Z = 2X1 + X2
        X1 <= 20
        X1  - X2   <= 10
        */
        Fraccion[] funcionObjetivo = {
            new Fraccion(2), new Fraccion(1)
        };
        Fraccion restricciones[][] = {
            { new Fraccion(1), new Fraccion(0), new Fraccion(20)},
            { new Fraccion(1), new Fraccion(-1), new Fraccion(10)}
        };
        
        Igualdades igualdadesIngresadas[] = { Igualdades.MENOR_O_IGUAL, Igualdades.MENOR_O_IGUAL };
        
        

        try {
            DatosOptimizacion datosIngresados = new DatosOptimizacion(Objetivo.MAXIMIZAR, funcionObjetivo, restricciones, igualdadesIngresadas);
            MetodoDeLasDosFases metodo = new MetodoDeLasDosFases( datosIngresados );
            
            metodo.resolver();
            
            assert false;
        } 
        catch ( ZNoAcotadaException ex ) {
            assert true;
        } 
        catch (DemasiadasIteracionesMetodoDosFasesException | SinSolucionesFactiblesException ex) {
            assert false;
        }
    }
    
    
    /**
     * Sin solución factible 1.
     */
    @Test
    public void testSeResolvioBienElTableu9() throws TodosLosCoeficientesDeLaFilaSonCeroException{
        
        /*
        Max Z = 100X1 + 140X2 + 50X3
        X1 + 2X2 + X3  <= 10
        X1       + X3  = 20
        X1 - X2        <= 15
        X1 + X2  - 2X3 <= 12
        */
        Fraccion[] funcionObjetivo = {
            new Fraccion(100), new Fraccion(140), new Fraccion(50)
        };
        Fraccion restricciones[][] = {
            { new Fraccion(1), new Fraccion(2), new Fraccion(1), new Fraccion(10)},
            { new Fraccion(1), new Fraccion(0), new Fraccion(1), new Fraccion(20)},
            { new Fraccion(1), new Fraccion(-1), new Fraccion(0), new Fraccion(15)},
            { new Fraccion(1), new Fraccion(1), new Fraccion(-2), new Fraccion(12)}
        };
        
        Igualdades igualdadesIngresadas[] = { Igualdades.MENOR_O_IGUAL, Igualdades.IGUAL, Igualdades.MENOR_O_IGUAL, Igualdades.MENOR_O_IGUAL };
        
        
        
        try {
            DatosOptimizacion datosIngresados = new DatosOptimizacion(Objetivo.MAXIMIZAR, funcionObjetivo, restricciones, igualdadesIngresadas);
            MetodoDeLasDosFases metodo = new MetodoDeLasDosFases( datosIngresados );
            
            metodo.resolver();
            
            assert false;
        }
        catch (ZNoAcotadaException | DemasiadasIteracionesMetodoDosFasesException ex) {
            assert false;
        }
        catch (SinSolucionesFactiblesException ex) {
            assert true;
        }
    }
    
    
    /**
     * Sin solución factible 2.
     */
    @Test
    public void testSeResolvioBienElTableu10() throws TodosLosCoeficientesDeLaFilaSonCeroException{
        
        /*
        Min Z = 0.4X1 + 0.5X2
        0.3X1 + 0.1X2  <= 1.8
        0.5X1 + 0.5X2   = 6
        0.6X1 + 0.4X2  >= 6
        */
        Fraccion[] funcionObjetivo = {
            new Fraccion(0.4), new Fraccion(0.5)
        };
        Fraccion restricciones[][] = {
            { new Fraccion(0.3), new Fraccion(0.1), new Fraccion(1.8) },
            { new Fraccion(0.5), new Fraccion(0.5), new Fraccion(6) },
            { new Fraccion(0.6), new Fraccion(0.4), new Fraccion(6) }
        };
        
        Igualdades igualdadesIngresadas[] = { Igualdades.MENOR_O_IGUAL, Igualdades.IGUAL, Igualdades.MAYOR_O_IGUAL };
        
        
        
        try {
            DatosOptimizacion datosIngresados = new DatosOptimizacion(Objetivo.MAXIMIZAR, funcionObjetivo, restricciones, igualdadesIngresadas);
            MetodoDeLasDosFases metodo = new MetodoDeLasDosFases( datosIngresados );
            
            metodo.resolver();
            
            assert false;
        }
        catch (ZNoAcotadaException | DemasiadasIteracionesMetodoDosFasesException ex) {
            assert false;
        }
        catch (SinSolucionesFactiblesException ex) {
            assert true;
        }
    }
    
    
    
    @Test
    public void getSolucionOptima1() throws TodosLosCoeficientesDeLaFilaSonCeroException{
        
        
        /*
        Max Z = 30X1 + 50X2
        2X1 + X2  <= 16
        X1  + 2X2   <= 11
        X1  + 3X2  <= 15
        */
        Fraccion[] funcionObjetivo = {
            new Fraccion(30), new Fraccion(50)
        };
        Fraccion restricciones[][] = {
            { new Fraccion(2), new Fraccion(1), new Fraccion(16)},
            { new Fraccion(1), new Fraccion(2), new Fraccion(11)},
            { new Fraccion(1), new Fraccion(3), new Fraccion(15)}
        };
        
        Igualdades igualdadesIngresadas[] = { Igualdades.MENOR_O_IGUAL, Igualdades.MENOR_O_IGUAL, Igualdades.MENOR_O_IGUAL };
        
        try {
            DatosOptimizacion datosIngresados = new DatosOptimizacion(Objetivo.MAXIMIZAR, funcionObjetivo, restricciones, igualdadesIngresadas);
            MetodoDeLasDosFases metodo = new MetodoDeLasDosFases( datosIngresados );
            
            metodo.resolver();
            
            Fraccion solucionOptima = metodo.getSolucionOptima();
        
            Assert.assertEquals( new Fraccion(310) , solucionOptima);
        }
        catch (ZNoAcotadaException | DemasiadasIteracionesMetodoDosFasesException | SinSolucionesFactiblesException ex) {
            ex.printStackTrace();
        }
        
        
    }
    
    
    
}
