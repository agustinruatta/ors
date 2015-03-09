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
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 */
public class DualidadTest {
    
    public DualidadTest() {
    }

    @Test
    public void getProblemaDual1() throws IllegalArgumentException, TodosLosCoeficientesDeLaFilaSonCeroException {
        
        /*
        Primal
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
        Fraccion[] funcionObjetivoPrimal = {
            new Fraccion(170), new Fraccion(160), new Fraccion(175), new Fraccion(180), new Fraccion(195)
        };
        Fraccion restriccionesPrimal[][] = {
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

        Igualdades igualdadesIngresadasPrimal[] = { Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL };

        RestriccionVariableDeDecision[] restriccionesVariablesDeDecisionesPrimal= { RestriccionVariableDeDecision.RESTRICCION_POR_DEFECTO, RestriccionVariableDeDecision.RESTRICCION_POR_DEFECTO, RestriccionVariableDeDecision.RESTRICCION_POR_DEFECTO, RestriccionVariableDeDecision.RESTRICCION_POR_DEFECTO, RestriccionVariableDeDecision.RESTRICCION_POR_DEFECTO };

        DatosOptimizacion datosOptimizacionPrimal = new DatosOptimizacion(Objetivo.MINIMIZAR, funcionObjetivoPrimal, restriccionesPrimal, igualdadesIngresadasPrimal, restriccionesVariablesDeDecisionesPrimal, false);
        
            
        //Dual
        Fraccion[] funcionObjetivoDual = {
            new Fraccion(48), new Fraccion(79), new Fraccion(65), new Fraccion(87), new Fraccion(64), new Fraccion(73), new Fraccion(82), new Fraccion(43), new Fraccion(52), new Fraccion(15)
        };
        Fraccion restriccionesDual[][] = {
            { new Fraccion(1), new Fraccion(1), new Fraccion(1), new Fraccion(1), new Fraccion(0), new Fraccion(0), new Fraccion(0), new Fraccion(0), new Fraccion(0), new Fraccion(0), new Fraccion(170)},
            { new Fraccion(0), new Fraccion(1), new Fraccion(1), new Fraccion(1), new Fraccion(1), new Fraccion(0), new Fraccion(0), new Fraccion(0), new Fraccion(0), new Fraccion(0), new Fraccion(160)},
            { new Fraccion(0), new Fraccion(0), new Fraccion(0), new Fraccion(1), new Fraccion(1), new Fraccion(1), new Fraccion(1), new Fraccion(0), new Fraccion(0), new Fraccion(0), new Fraccion(175)},
            { new Fraccion(0), new Fraccion(0), new Fraccion(0), new Fraccion(0), new Fraccion(0), new Fraccion(1), new Fraccion(1), new Fraccion(1), new Fraccion(1), new Fraccion(0), new Fraccion(180)},
            { new Fraccion(0), new Fraccion(0), new Fraccion(0), new Fraccion(0), new Fraccion(0), new Fraccion(0), new Fraccion(0), new Fraccion(0), new Fraccion(1), new Fraccion(1), new Fraccion(195)}
        };

        Igualdades igualdadesIngresadasDual[] = { Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL };

        RestriccionVariableDeDecision[] restriccionesVariablesDeDecisionesDual= { RestriccionVariableDeDecision.VARIABLE_NO_POSITIVA_POR_DEFECTO, RestriccionVariableDeDecision.VARIABLE_NO_POSITIVA_POR_DEFECTO, RestriccionVariableDeDecision.VARIABLE_NO_POSITIVA_POR_DEFECTO, RestriccionVariableDeDecision.VARIABLE_NO_POSITIVA_POR_DEFECTO, RestriccionVariableDeDecision.VARIABLE_NO_POSITIVA_POR_DEFECTO, RestriccionVariableDeDecision.VARIABLE_NO_POSITIVA_POR_DEFECTO, RestriccionVariableDeDecision.VARIABLE_NO_POSITIVA_POR_DEFECTO, RestriccionVariableDeDecision.VARIABLE_NO_POSITIVA_POR_DEFECTO, RestriccionVariableDeDecision.VARIABLE_NO_POSITIVA_POR_DEFECTO, RestriccionVariableDeDecision.VARIABLE_NO_POSITIVA_POR_DEFECTO };

        DatosOptimizacion datosOptimizacionDual = new DatosOptimizacion(Objetivo.MAXIMIZAR, funcionObjetivoDual, restriccionesDual, igualdadesIngresadasDual, restriccionesVariablesDeDecisionesDual, false);
        
        assertEquals( ( new Dualidad(datosOptimizacionPrimal ).getProblemaDual() ), datosOptimizacionDual );
        
    }
    
    
    
    
    
     @Test
    public void getProblemaDual2() throws IllegalArgumentException, TodosLosCoeficientesDeLaFilaSonCeroException {
        
        //Primal
        Fraccion[] funcionObjetivoPrimal = {
            new Fraccion(5), new Fraccion(8)
        };
        Fraccion restriccionesPrimal[][] = {
            { new Fraccion(20), new Fraccion(40), new Fraccion(18)},
            { new Fraccion(17), new Fraccion(14), new Fraccion(7)
            }
        };

        Igualdades igualdadesIngresadasPrimal[] = { Igualdades.MENOR_O_IGUAL, Igualdades.MENOR_O_IGUAL };

        RestriccionVariableDeDecision[] restriccionesVariablesDeDecisionesPrimal= { RestriccionVariableDeDecision.RESTRICCION_POR_DEFECTO, RestriccionVariableDeDecision.RESTRICCION_POR_DEFECTO };

        DatosOptimizacion datosOptimizacionPrimal = new DatosOptimizacion(Objetivo.MINIMIZAR, funcionObjetivoPrimal, restriccionesPrimal, igualdadesIngresadasPrimal, restriccionesVariablesDeDecisionesPrimal, false);
        
            
        //Dual
        Fraccion[] funcionObjetivoDual = {
            new Fraccion(18), new Fraccion(7)
        };
        Fraccion restriccionesDual[][] = {
            { new Fraccion(20), new Fraccion(17), new Fraccion(5) },
            { new Fraccion(40), new Fraccion(14), new Fraccion(8) },
        };

        Igualdades igualdadesIngresadasDual[] = { Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL };

        RestriccionVariableDeDecision[] restriccionesVariablesDeDecisionesDual= { RestriccionVariableDeDecision.VARIABLE_NO_NEGATIVA_POR_DEFECTO, RestriccionVariableDeDecision.VARIABLE_NO_NEGATIVA_POR_DEFECTO };

        DatosOptimizacion datosOptimizacionDual = new DatosOptimizacion(Objetivo.MAXIMIZAR, funcionObjetivoDual, restriccionesDual, igualdadesIngresadasDual, restriccionesVariablesDeDecisionesDual, false);
        
        assertEquals( ( new Dualidad(datosOptimizacionPrimal ).getProblemaDual() ), datosOptimizacionDual );
        
    }
    
    
    /**
     * Prueba realizar una dualidad con un coeficiente de TI negativo.
     */
    @Test
    public void getProblemaDual3() throws IllegalArgumentException, TodosLosCoeficientesDeLaFilaSonCeroException {
        
        //Primal
        /*
        Min 5X1 + 8X2
        20X1    +   40X2    <=  -18
        -17X1   +   14X2    <=  7
        */
        Fraccion[] funcionObjetivoPrimal = {
            new Fraccion(5), new Fraccion(8)
        };
        Fraccion restriccionesPrimal[][] = {
            { new Fraccion(20), new Fraccion(40), new Fraccion(-18)},
            { new Fraccion(17), new Fraccion(14), new Fraccion(7)
            }
        };

        Igualdades igualdadesIngresadasPrimal[] = { Igualdades.MENOR_O_IGUAL, Igualdades.MENOR_O_IGUAL };

        RestriccionVariableDeDecision[] restriccionesVariablesDeDecisionesPrimal= { RestriccionVariableDeDecision.RESTRICCION_POR_DEFECTO, RestriccionVariableDeDecision.RESTRICCION_POR_DEFECTO };

        DatosOptimizacion datosOptimizacionPrimal = new DatosOptimizacion(Objetivo.MINIMIZAR, funcionObjetivoPrimal, restriccionesPrimal, igualdadesIngresadasPrimal, restriccionesVariablesDeDecisionesPrimal, false);
        
            
        //Dual
        Fraccion[] funcionObjetivoDual = {
            new Fraccion(-18), new Fraccion(7)
        };
        Fraccion restriccionesDual[][] = {
            { new Fraccion(20), new Fraccion(17), new Fraccion(5) },
            { new Fraccion(40), new Fraccion(14), new Fraccion(8) },
        };

        Igualdades igualdadesIngresadasDual[] = { Igualdades.MAYOR_O_IGUAL, Igualdades.MAYOR_O_IGUAL };

        RestriccionVariableDeDecision[] restriccionesVariablesDeDecisionesDual= { RestriccionVariableDeDecision.VARIABLE_NO_NEGATIVA_POR_DEFECTO, RestriccionVariableDeDecision.VARIABLE_NO_NEGATIVA_POR_DEFECTO };

        DatosOptimizacion datosOptimizacionDual = new DatosOptimizacion(Objetivo.MAXIMIZAR, funcionObjetivoDual, restriccionesDual, igualdadesIngresadasDual, restriccionesVariablesDeDecisionesDual, false);
        
        assertEquals( ( new Dualidad(datosOptimizacionPrimal ).getProblemaDual() ), datosOptimizacionDual );
        
    }
    
    
    
}
