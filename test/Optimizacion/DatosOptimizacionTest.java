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
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 */
public class DatosOptimizacionTest {
    
    
    
    @Test
    /**
     * Se prueba que uno correcto no genere ninguna excepción.
     */
    public void testCompruebaSiSonDatosValidos1() throws TodosLosCoeficientesDeLaFilaSonCeroException{
        
        
        try{
            /*
            Min Z = 170X1 + 160X2 + 175X3 + 180X4 + 195X5
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

            RestriccionVariableDeDecision[] restriccionesVariablesDeDecisiones= { RestriccionVariableDeDecision.RESTRICCION_POR_DEFECTO, RestriccionVariableDeDecision.RESTRICCION_POR_DEFECTO, RestriccionVariableDeDecision.RESTRICCION_POR_DEFECTO, RestriccionVariableDeDecision.RESTRICCION_POR_DEFECTO, RestriccionVariableDeDecision.RESTRICCION_POR_DEFECTO };
            
            DatosOptimizacion datosIngresados = new DatosOptimizacion(Objetivo.MINIMIZAR, funcionObjetivo, restricciones, igualdadesIngresadas, restriccionesVariablesDeDecisiones, true);
            
            assert true;
        }
        catch( IllegalArgumentException e ){
            assert false;
        }
        
    }
    
    
    @Test
    /**
     * Comprueba si detecta algún parámetro nulo.
     */
    public void testCompruebaSiSonDatosValidos2() throws TodosLosCoeficientesDeLaFilaSonCeroException{
        
        
        try{
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

            
            DatosOptimizacion datosIngresados = new DatosOptimizacion(Objetivo.MINIMIZAR, funcionObjetivo, restricciones, null );
            
            //Fallar. No se debería llegar acá (Tendría que saltar una excepción antes).
            assertEquals(1, 2);
        }
        catch( IllegalArgumentException e ){
        }
        
    }
    
    
    @Test
    /**
     * Comprueba si algún coeficiente de la función objetivo es nulo.
     */
    public void testCompruebaSiSonDatosValidos3() throws TodosLosCoeficientesDeLaFilaSonCeroException{
        
        
        try{
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
                new Fraccion(170), null, new Fraccion(175), new Fraccion(180), new Fraccion(195)
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

            DatosOptimizacion datosIngresados = new DatosOptimizacion(Objetivo.MINIMIZAR, funcionObjetivo, restricciones, igualdadesIngresadas);
            
            //Fallar. No se debería llegar acá (Tendría que saltar una excepción antes).
            assertEquals(1, 2);
        }
        catch( IllegalArgumentException e ){
        }
        
    }
    
    
    @Test
    /**
     * Comprueba si algún coeficiente de las restricciones es nulo.
     */
    public void testCompruebaSiSonDatosValidos4() throws TodosLosCoeficientesDeLaFilaSonCeroException{
        
        
        try{
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
                { new Fraccion(0), new Fraccion(0), null, new Fraccion(1), new Fraccion(0), new Fraccion(73)},
                { new Fraccion(0), new Fraccion(0), new Fraccion(1), new Fraccion(1), new Fraccion(0), new Fraccion(82)},
                { new Fraccion(0), new Fraccion(0), new Fraccion(0), new Fraccion(1), new Fraccion(0), new Fraccion(43)},
                { new Fraccion(0), new Fraccion(0), new Fraccion(0), new Fraccion(1), new Fraccion(1), new Fraccion(52)},
                { new Fraccion(0), new Fraccion(0), new Fraccion(0), new Fraccion(0), new Fraccion(1), new Fraccion(15)},
            };

            Igualdades igualdadesIngresadas[] = { Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL };

            DatosOptimizacion datosIngresados = new DatosOptimizacion(Objetivo.MINIMIZAR, funcionObjetivo, restricciones, igualdadesIngresadas);
            
            //Fallar. No se debería llegar acá (Tendría que saltar una excepción antes).
            assertEquals(1, 2);
        }
        catch( IllegalArgumentException e ){
        }
        
    }
    
    
    @Test
    /**
     * Comprueba que todas las filas de las restricciones tengan la misma cantidad de columnas.
     */
    public void testCompruebaSiSonDatosValidos5() throws TodosLosCoeficientesDeLaFilaSonCeroException{
        
        
        try{
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
                { new Fraccion(0), new Fraccion(0), new Fraccion(0), new Fraccion(1), new Fraccion(15)},
            };

            Igualdades igualdadesIngresadas[] = { Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL };

            DatosOptimizacion datosIngresados = new DatosOptimizacion(Objetivo.MINIMIZAR, funcionObjetivo, restricciones, igualdadesIngresadas);
            
            //Fallar. No se debería llegar acá (Tendría que saltar una excepción antes).
            assertEquals(1, 2);
        }
        catch( IllegalArgumentException e ){
        }
        
    }
    
    
    @Test
    /**
     * Comprueba que el tamaño de la función objetivo y las restricciones sean correctas.
     */
    public void testCompruebaSiSonDatosValidos6() throws TodosLosCoeficientesDeLaFilaSonCeroException{
        
        
        try{
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
                new Fraccion(170), new Fraccion(160), new Fraccion(175), new Fraccion(195)
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

            DatosOptimizacion datosIngresados = new DatosOptimizacion(Objetivo.MINIMIZAR, funcionObjetivo, restricciones, igualdadesIngresadas);
            
            //Fallar. No se debería llegar acá (Tendría que saltar una excepción antes).
            assertEquals(1, 2);
        }
        catch( IllegalArgumentException e ){
        }
        
    }
    
    
    
    @Test
    /**
     * Comprueba que la cantidad de variables y restricciones sean correctas.
     */
    public void testCompruebaSiSonDatosValidos7() throws TodosLosCoeficientesDeLaFilaSonCeroException{
        
        
        try{
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

            Igualdades igualdadesIngresadas[] = { Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL };

            DatosOptimizacion datosIngresados = new DatosOptimizacion(Objetivo.MINIMIZAR, funcionObjetivo, restricciones, igualdadesIngresadas);
            
            //Fallar. No se debería llegar acá (Tendría que saltar una excepción antes).
            assertEquals(1, 2);
        }
        catch( IllegalArgumentException e ){
        }
        
    }
    
    
    @Test
    /**
     * Comprueba que la cantidad de restricciones de las variables de decisión sea correcta.
     */
    public void testCompruebaSiSonDatosValidos8() throws TodosLosCoeficientesDeLaFilaSonCeroException{
        
        
        try{
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

            RestriccionVariableDeDecision[] restriccionesVariablesDeDecisiones= { RestriccionVariableDeDecision.RESTRICCION_POR_DEFECTO, RestriccionVariableDeDecision.RESTRICCION_POR_DEFECTO };
            DatosOptimizacion datosIngresados = new DatosOptimizacion(Objetivo.MINIMIZAR, funcionObjetivo, restricciones, igualdadesIngresadas, restriccionesVariablesDeDecisiones, true);
            
            //Fallar
            assert false;
        }
        catch( IllegalArgumentException e ){
            assert true;
        }
        
    }
    
    
    
}
