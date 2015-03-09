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
package API;

import java.math.BigInteger;

/**
 * Clase utilitaria que contiene métodos que ayudan en el cálculo y validación con
 * fracciones y matrices.
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 * @since 1.0
 */
public class Calculadora {
    
    private static final int FILA_CERO = 0;

    /**
     * Suma dos fracciones.
     * @param primeraFraccion Primer fracción.
     * @param segundaFraccion Segunda fracción.
     * @return Suma de la primera con la segunda fracción
     */
    public static Fraccion sumar( Fraccion primeraFraccion, Fraccion segundaFraccion ){
        /**
         * La suma de dos fracciones es igual:
         * (numPF)/(denPF) + (numeSF)/(denSF) = ( numPF*denSF + numSF*denPF ) / ( denPF*DenSF )
         */
        
        // numPF*denSF + numSF*denPF
        BigInteger numeradorResultado = ( primeraFraccion.getNumerador().multiply( segundaFraccion.getDenominador() ) ).add( segundaFraccion.getNumerador().multiply( primeraFraccion.getDenominador() ) );
        
        // denPF*DenSF
        BigInteger denominadorResultado = primeraFraccion.getDenominador().multiply( segundaFraccion.getDenominador() );
        
        //Devolver el resultado
        return new Fraccion( numeradorResultado, denominadorResultado );
    }
    
    /**
     * Resta dos fracciones.
     * @param primeraFraccion Primer fracción.
     * @param segundaFraccion Segunda fracción.
     * @return Resta de la segunda fracción a la primera (PF - SF)
     */
    public static Fraccion restar( Fraccion primeraFraccion, Fraccion segundaFraccion ){
        /**
         * La resta de dos fracciones es igual:
         * (numPF)/(denPF) - (numeSF)/(denSF) = ( numPF*denSF - numSF*denPF ) / ( denPF*DenSF )
         */
        
        // numPF*denSF - numSF*denPF
        BigInteger numeradorResultado = ( primeraFraccion.getNumerador().multiply( segundaFraccion.getDenominador() ) ).subtract( segundaFraccion.getNumerador().multiply( primeraFraccion.getDenominador() ) );
        
        // denPF*DenSF
        BigInteger denominadorResultado = primeraFraccion.getDenominador().multiply( segundaFraccion.getDenominador() );
        
        //Devolver el resultado
        return new Fraccion( numeradorResultado, denominadorResultado );
    }
    
    /**
     * Multiplica dos fracciones.
     * @param primeraFraccion Primer fracción.
     * @param segundaFraccion Segunda fracción.
     * @return Multiplicación de las dos fracciones.
     */
    public static Fraccion multiplicar( Fraccion primeraFraccion, Fraccion segundaFraccion ){
        
        //Calcular el numerador del resultado.
        BigInteger numeradorResultado = primeraFraccion.getNumerador().multiply( segundaFraccion.getNumerador());
        
        //Calcular el denominador del resultado.
        BigInteger denominadorResultado = primeraFraccion.getDenominador().multiply( segundaFraccion.getDenominador() );
        
        //Devolver la fracción resuelta.
        return new Fraccion( numeradorResultado, denominadorResultado );
        
    }
    
    
    
    /**
     * Multiplica dos matrices.
     * Realiza el calcula matrizA * matrizB
     * @param matrizA Primer matriz.
     * @param matrizB Segunda matriz.
     * @throws IllegalArgumentException Si alguna matriz tiene algún elemento nulo, no es una
     * matriz rectangular o el tamaño de las matrices no son compatibles.
     * @return Multiplicación de las dos matrices.
     */
    public static Fraccion[][] multiplicar( Fraccion[][] matrizA, Fraccion[][] matrizB ){
        
        //Comprobar que se puedan multiplicar
        if( 
                tieneAlgunElementoNulo(matrizA) || tieneAlgunElementoNulo(matrizB) ||
                !esUnaMatrizRectangular(matrizA) || !esUnaMatrizRectangular( matrizB ) ||
                !sePuedenMultiplicar(matrizA, matrizB)
                ){
            throw new IllegalArgumentException();
        }
        
        
        
        /*
        El resultado es otra matriz con la cantidad de filas de la matrizA
        y la cantidad de columnas de la matrizB
        */
        Fraccion[][] resultadoMultiplicacion = new Fraccion[ matrizA.length ][ matrizB[ FILA_CERO ].length ];
        
        
        
        
        //Multiplicar las matrices
        for( int filaMatrizA = 0; filaMatrizA < matrizA.length; filaMatrizA++ ){
            
            for( int columnaMatrizB = 0; columnaMatrizB < matrizB[ FILA_CERO ].length; columnaMatrizB++ ){
                
                Fraccion sumatoria = Fraccion.CERO;
                
                for( int columnaMatrizA = 0; columnaMatrizA < matrizA[ FILA_CERO ].length; columnaMatrizA++ ){
                    
                    int filaMatrizB = columnaMatrizA;
                    
                    sumatoria = Calculadora.sumar( sumatoria, Calculadora.multiplicar( matrizA[ filaMatrizA ][ columnaMatrizA ] , matrizB[ filaMatrizB ][ columnaMatrizB ] ) );
                    
                }
                
                resultadoMultiplicacion[ filaMatrizA ][ columnaMatrizB ] = sumatoria;
                
            }
            
            
        }
        
        return resultadoMultiplicacion;
        
    }
    
    /**
     * Divide dos fracciones.
     * @param primeraFraccion Primer fracción (Es dividida por la segunda fracción).
     * @param segundaFraccion Segunda fracción (Divide a la primera fracción).
     * @return División la segunda fraccion a la primera (PF / SF)
     */
    public static Fraccion dividir( Fraccion primeraFraccion, Fraccion segundaFraccion ){
        
        /*
         * Cuando se dividen 2 fracciones, el resultado es lo mismo que multiplicarlas
         * pero invirtiendo la segunda fraccion (Elevándola a la -1). Es decir:
         * primeraFraccion/segundaFraccion = primeraFraccion * ((segundaFraccion)^-1)
         */
        
        //Calcular el numerador del resultado. Observar que se lo multiplica por el denominador de la segunda fracción.
        BigInteger numeradorResultado = primeraFraccion.getNumerador().multiply( segundaFraccion.getDenominador());
        
        //Calcular el denominador del resultado. Observar que se lo multiplica por el numerador de la segunda fracción.
        BigInteger denominadorResultado = primeraFraccion.getDenominador().multiply( segundaFraccion.getNumerador());
        
        
        //Devolver la fracción resuelta.
        return new Fraccion( numeradorResultado, denominadorResultado );
    }

    
    
    /**
     * Indica si el objeto enviado como parámetro es una matriz rectangular.
     * Cuando nos referimos a que sea una "matriz rectangular" nos referimos
     * a que en cada fila tenga la misma cantidad de columnas (O dicho de
     * otra forma, que sea rectangular). Ésto se realiza debido a que Java
     * permite la creación de matrices con filas que contienen diferentes
     * cantidades de columnas.
     * @param matriz Matriz que se va a analizar
     * @return 
     */
    public static boolean esUnaMatrizRectangular( Fraccion[][] matriz ){
        
        int cantidadColumnasPrimerFila = matriz[ FILA_CERO ].length;
        
        for( Fraccion[] fila : matriz ){
            if( fila.length != cantidadColumnasPrimerFila ){
                return false;
            }
        }
        
        return true;
    }
    
    
    /**
     * Indica si algún elemento de la matriz es nulo.
     * @param matriz
     * @return true si un o más elemento es nulo, false caso contrario.
     */
    public static boolean tieneAlgunElementoNulo( Fraccion[][] matriz ){
        
        for( int fila = 0; fila < matriz.length; fila++ ){
            for( int columna = 0; columna < matriz[ fila ].length; columna++ ){
                if( matriz[ fila ][ columna ] == null ){
                    return true;
                }
            }
        }
        
        return false;
    }
    
    
    /**
     * Indica si se puede realizar la multiplicación de la matrizA por la matrizB.
     * Ésto se hace comprobando que la cantidad de columnas de la matrizA sea igual que
     * la cantidad de filas de la matrizB.
     * @param matrizA
     * @param matrizB
     * @return 
     */
    public static boolean sePuedenMultiplicar( Fraccion[][] matrizA, Fraccion[][] matrizB ){
        
        return (
                matrizA[ FILA_CERO ].length == matrizB.length
                );
    }
    
    
    /**
     * Constructor.
     * Es un constructor privado debido a que contiene todos métodos estáticos.
     * Por lo tanto, nunca se va a crear un objeto Calculadora.
     */
    private Calculadora() {
    }
}
