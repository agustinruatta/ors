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
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 */
public class CalculadoraTest {
    
    //*****Test Sumar*****
    /**
     * Suma la misma fracción.
     */
    @Test
    public void testSumar1() {
        Fraccion fraccionA = new Fraccion( 3, 5);
        Fraccion fraccionB = new Fraccion( 3, 5);
        
        Fraccion resultado = Calculadora.sumar(fraccionA, fraccionB);
        
        Fraccion resultadoEsperado = new Fraccion( new BigInteger("6"), new BigInteger("5"));
        
        assertEquals(resultado, resultadoEsperado);
    }
    
    /**
     * Suma dos fracciones no simplificables.
     */
    @Test
    public void testSumar2() {
        Fraccion fraccionA = new Fraccion( new BigInteger("7"), new BigInteger("5"));
        Fraccion fraccionB = new Fraccion( new BigInteger("49"), new BigInteger("87"));
        
        Fraccion resultado = Calculadora.sumar(fraccionA, fraccionB);
        
        Fraccion resultadoEsperado = new Fraccion( new BigInteger("854"), new BigInteger("435"));
        
        assertEquals(resultado, resultadoEsperado);
    }
    
    /**
     * Verifica que la suma del complemento da 0.
     * El signo negativo está en el numerador de la primer fracción.
     */
    @Test
    public void testSumar3() {
        Fraccion fraccionA = new Fraccion( new BigInteger("-3"), new BigInteger("5"));
        Fraccion fraccionB = new Fraccion( new BigInteger("3"), new BigInteger("5"));
        
        Fraccion resultado = Calculadora.sumar(fraccionA, fraccionB);
        
        Fraccion resultadoEsperado = new Fraccion( new BigInteger("0"), new BigInteger("1"));
        
        assertEquals(resultado, resultadoEsperado);
    }
    
    /**
     * Verifica que la suma del complemento da 0.
     * El signo negativo está en el denominador de la primer fracción.
     */
    @Test
    public void testSumar4() {
        Fraccion fraccionA = new Fraccion( new BigInteger("3"), new BigInteger("-5"));
        Fraccion fraccionB = new Fraccion( new BigInteger("3"), new BigInteger("5"));
        
        Fraccion resultado = Calculadora.sumar(fraccionA, fraccionB);
        
        Fraccion resultadoEsperado = new Fraccion( new BigInteger("0"), new BigInteger("1"));
        
        assertEquals(resultado, resultadoEsperado);
    }
    
    /**
     * Verifica que la suma del complemento da 0.
     * El signo negativo está en el numerador de la segunda fracción.
     */
    @Test
    public void testSumar5() {
        Fraccion fraccionA = new Fraccion( new BigInteger("3"), new BigInteger("5"));
        Fraccion fraccionB = new Fraccion( new BigInteger("-3"), new BigInteger("5"));
        
        Fraccion resultado = Calculadora.sumar(fraccionA, fraccionB);
        
        Fraccion resultadoEsperado = new Fraccion( new BigInteger("0"), new BigInteger("1"));
        
        assertEquals(resultado, resultadoEsperado);
    }
    
    /**
     * Verifica que la suma del complemento da 0.
     * El signo negativo está en el denominador de la segunda fracción.
     */
    @Test
    public void testSumar6() {
        Fraccion fraccionA = new Fraccion( new BigInteger("3"), new BigInteger("5"));
        Fraccion fraccionB = new Fraccion( new BigInteger("3"), new BigInteger("-5"));
        
        Fraccion resultado = Calculadora.sumar(fraccionA, fraccionB);
        
        Fraccion resultadoEsperado = new Fraccion( new BigInteger("0"), new BigInteger("1"));
        
        assertEquals(resultado, resultadoEsperado);
    }
    
    /**
     * Verifica la suma de dos fracciones que son simplificables.
     */
    @Test
    public void testSumar7() {
        Fraccion fraccionA = new Fraccion( new BigInteger("897"), new BigInteger("45"));
        Fraccion fraccionB = new Fraccion( new BigInteger("35"), new BigInteger("5"));
        
        Fraccion resultado = Calculadora.sumar(fraccionA, fraccionB);
        
        Fraccion resultadoEsperado = new Fraccion( new BigInteger("404"), new BigInteger("15"));
        
        assertEquals(resultado, resultadoEsperado);
    }
    
    
    
    
    
    //*****Test Restar*****
    
    /**
     * Resta la misma fracción.
     */
    @Test
    public void testRestar1() {
        Fraccion fraccionA = new Fraccion( new BigInteger("3"), new BigInteger("5"));
        Fraccion fraccionB = new Fraccion( new BigInteger("3"), new BigInteger("5"));
        
        Fraccion resultado = Calculadora.restar(fraccionA, fraccionB);
        
        Fraccion resultadoEsperado = new Fraccion( new BigInteger("0"), new BigInteger("1"));
        
        assertEquals(resultado, resultadoEsperado);
    }
    
    /**
     * Resta dos fracciones no simplificables que dan un resultado positivo.
     */
    @Test
    public void testRestar2() {
        Fraccion fraccionA = new Fraccion( new BigInteger("987"), new BigInteger("5"));
        Fraccion fraccionB = new Fraccion( new BigInteger("48"), new BigInteger("7"));
        
        Fraccion resultado = Calculadora.restar(fraccionA, fraccionB);
        
        Fraccion resultadoEsperado = new Fraccion( new BigInteger("6669"), new BigInteger("35"));
        
        assertEquals(resultado, resultadoEsperado);
    }
    
    /**
     * Resta dos fracciones no simplificables que dan un resultado negativo.
     */
    @Test
    public void testRestar3() {
        Fraccion fraccionA = new Fraccion( new BigInteger("48"), new BigInteger("7"));
        Fraccion fraccionB = new Fraccion( new BigInteger("789"), new BigInteger("67"));
        
        Fraccion resultado = Calculadora.restar(fraccionA, fraccionB);
        
        Fraccion resultadoEsperado = new Fraccion( new BigInteger("-2307"), new BigInteger("469"));
        
        assertEquals(resultado, resultadoEsperado);
    }
    
    
    /**
     * Prueba que la resta de una fracción negativa es igual que sumarla.
     */
    @Test
    public void testRestar4() {
        Fraccion fraccionA = new Fraccion( new BigInteger("48"), new BigInteger("7"));
        Fraccion fraccionB = new Fraccion( new BigInteger("-789"), new BigInteger("67"));
        
        Fraccion resultado = Calculadora.restar(fraccionA, fraccionB);
        
        Fraccion resultadoEsperado = new Fraccion( new BigInteger("8739"), new BigInteger("469"));
        
        assertEquals(resultado, resultadoEsperado);
    }
    
    
    
    
    //*****Test Multiplicacion*****
    
    /**
     * Multiplica dos numeros simplificables positivos.
     */
    @Test
    public void testMultiplicacion1() {
        Fraccion fraccionA = new Fraccion( new BigInteger("4"), new BigInteger("2"));
        Fraccion fraccionB = new Fraccion( new BigInteger("58"), new BigInteger("4"));
        
        Fraccion resultado = Calculadora.multiplicar(fraccionA, fraccionB);
        
        Fraccion resultadoEsperado = new Fraccion( new BigInteger("29"), new BigInteger("1"));
        
        assertEquals(resultado, resultadoEsperado);
    }
    
    /**
     * Multiplica dos no numeros simplificables positivos.
     */
    @Test
    public void testMultiplicacion2() {
        Fraccion fraccionA = new Fraccion( new BigInteger("48"), new BigInteger("7"));
        Fraccion fraccionB = new Fraccion( new BigInteger("789"), new BigInteger("67"));
        
        Fraccion resultado = Calculadora.multiplicar(fraccionA, fraccionB);
        
        Fraccion resultadoEsperado = new Fraccion( new BigInteger("37872"), new BigInteger("469"));
        
        assertEquals(resultado, resultadoEsperado);
    }
    
    /**
     * Multiplica dos numeros simplificables (Uno positivo y otro negativo).
     */
    @Test
    public void testMultiplicacion3() {
        Fraccion fraccionA = new Fraccion( new BigInteger("-4"), new BigInteger("2"));
        Fraccion fraccionB = new Fraccion( new BigInteger("58"), new BigInteger("4"));
        
        Fraccion resultado = Calculadora.multiplicar(fraccionA, fraccionB);
        
        Fraccion resultadoEsperado = new Fraccion( new BigInteger("-29"), new BigInteger("1"));
        
        assertEquals(resultado, resultadoEsperado);
    }
    
    /**
     * Multiplica dos no numeros simplificables positivos (Uno positivo y otro negativo).
     */
    @Test
    public void testMultiplicacion4() {
        Fraccion fraccionA = new Fraccion( new BigInteger("48"), new BigInteger("7"));
        Fraccion fraccionB = new Fraccion( new BigInteger("789"), new BigInteger("-67"));
        
        Fraccion resultado = Calculadora.multiplicar(fraccionA, fraccionB);
        
        Fraccion resultadoEsperado = new Fraccion( new BigInteger("-37872"), new BigInteger("469"));
        
        assertEquals(resultado, resultadoEsperado);
    }
    
    /**
     * Muestra que menos por menos es mas
     */
    @Test
    public void testMultiplicacion5() {
        Fraccion fraccionA = new Fraccion( new BigInteger("-48"), new BigInteger("7"));
        Fraccion fraccionB = new Fraccion( new BigInteger("789"), new BigInteger("-67"));
        
        Fraccion resultado = Calculadora.multiplicar(fraccionA, fraccionB);
        
        Fraccion resultadoEsperado = new Fraccion( new BigInteger("37872"), new BigInteger("469"));
        
        assertEquals(resultado, resultadoEsperado);
    }
    
    
    //*****Test Division*****
    
    /**
     * Divide dos numeros simplificables positivos.
     */
    @Test
    public void testDivisión1() {
        Fraccion fraccionA = new Fraccion( new BigInteger("4"), new BigInteger("2"));
        Fraccion fraccionB = new Fraccion( new BigInteger("58"), new BigInteger("4"));
        
        Fraccion resultado = Calculadora.dividir(fraccionA, fraccionB);
        
        Fraccion resultadoEsperado = new Fraccion( new BigInteger("4"), new BigInteger("29"));
        
        assertEquals(resultado, resultadoEsperado);
    }
    
    /**
     * Divide dos no numeros simplificables positivos.
     */
    @Test
    public void testDivisión2() {
        Fraccion fraccionA = new Fraccion( new BigInteger("48"), new BigInteger("7"));
        Fraccion fraccionB = new Fraccion( new BigInteger("789"), new BigInteger("67"));
        
        Fraccion resultado = Calculadora.dividir(fraccionA, fraccionB);
        
        Fraccion resultadoEsperado = new Fraccion( new BigInteger("1072"), new BigInteger("1841"));
        
        assertEquals(resultado, resultadoEsperado);
    }
    
    /**
     * Divide dos numeros simplificables (Uno positivo y otro negativo).
     */
    @Test
    public void testDivisión3() {
        Fraccion fraccionA = new Fraccion( new BigInteger("-4"), new BigInteger("2"));
        Fraccion fraccionB = new Fraccion( new BigInteger("58"), new BigInteger("4"));
        
        Fraccion resultado = Calculadora.dividir(fraccionA, fraccionB);
        
        Fraccion resultadoEsperado = new Fraccion( new BigInteger("-4"), new BigInteger("29"));
        
        assertEquals(resultado, resultadoEsperado);
    }
    
    /**
     * Multiplica dos no numeros simplificables positivos (Uno positivo y otro negativo).
     */
    @Test
    public void testDivisión4() {
        Fraccion fraccionA = new Fraccion( new BigInteger("48"), new BigInteger("7"));
        Fraccion fraccionB = new Fraccion( new BigInteger("789"), new BigInteger("-67"));
        
        Fraccion resultado = Calculadora.dividir(fraccionA, fraccionB);
        
        Fraccion resultadoEsperado = new Fraccion( new BigInteger("-1072"), new BigInteger("1841"));
        
        assertEquals(resultado, resultadoEsperado);
    }
    
    /**
     * Muestra que menos divido menos es mas
     */
    @Test
    public void testDivisión5() {
        Fraccion fraccionA = new Fraccion( new BigInteger("-48"), new BigInteger("7"));
        Fraccion fraccionB = new Fraccion( new BigInteger("789"), new BigInteger("-67"));
        
        Fraccion resultado = Calculadora.dividir(fraccionA, fraccionB);
        
        Fraccion resultadoEsperado = new Fraccion( new BigInteger("1072"), new BigInteger("1841"));
        
        assertEquals(resultado, resultadoEsperado);
    }

    
    
    @Test
    public void testEsUnaMatrizRectangular1(){
        
        Fraccion[][] matriz ={
            { Fraccion.CERO, Fraccion.CERO, Fraccion.CERO },
            { Fraccion.CERO, Fraccion.CERO, Fraccion.CERO },
            { Fraccion.CERO, Fraccion.CERO, Fraccion.CERO },
            { Fraccion.CERO, Fraccion.CERO, Fraccion.CERO }
        };
        
        
        assertEquals( true , Calculadora.esUnaMatrizRectangular(matriz) );
    }
    
    @Test
    public void testEsUnaMatrizRectangular2(){
        
        Fraccion[][] matriz ={
            { Fraccion.CERO, Fraccion.CERO, Fraccion.CERO },
            { Fraccion.CERO, Fraccion.CERO },
            { Fraccion.CERO, Fraccion.CERO, Fraccion.CERO },
            { Fraccion.CERO, Fraccion.CERO, Fraccion.CERO }
        };
        
        
        assertEquals( false , Calculadora.esUnaMatrizRectangular(matriz) );
        
    }
    
    
    @Test
    public void testTieneAlgunElementoNulo1(){
        Fraccion[][] matriz ={
            { Fraccion.CERO, Fraccion.CERO, Fraccion.CERO },
            { Fraccion.CERO, Fraccion.CERO, Fraccion.CERO },
            { Fraccion.CERO, Fraccion.CERO, Fraccion.CERO },
            { Fraccion.CERO, Fraccion.CERO, Fraccion.CERO }
        };
        
        
        assertEquals( false , Calculadora.tieneAlgunElementoNulo( matriz ) );
    }
    
    @Test
    public void testTieneAlgunElementoNulo2(){
        Fraccion[][] matriz ={
            { Fraccion.CERO, Fraccion.CERO, Fraccion.CERO },
            { Fraccion.CERO, Fraccion.CERO, Fraccion.CERO },
            { null, Fraccion.CERO, Fraccion.CERO },
            { Fraccion.CERO, Fraccion.CERO, Fraccion.CERO }
        };
        
        
        assertEquals( true , Calculadora.tieneAlgunElementoNulo( matriz ) );
    }
    
    
    @Test
    public void testSePuedenMultiplicar1(){
        
        Fraccion[][] matrizA = new Fraccion[5][9];
        Fraccion[][] matrizB = new Fraccion[9][5];
        
        assertEquals( true, Calculadora.sePuedenMultiplicar(matrizA, matrizB) );
    }
    
    @Test
    public void testSePuedenMultiplicar2(){
        Fraccion[][] matrizA = new Fraccion[5][9];
        Fraccion[][] matrizB = new Fraccion[9][4];
        
        assertEquals( true, Calculadora.sePuedenMultiplicar(matrizA, matrizB) );
    }
    
    @Test
    public void testSePuedenMultiplicar3(){
        Fraccion[][] matrizA = new Fraccion[3][3];
        Fraccion[][] matrizB = new Fraccion[3][2];
        
        assertEquals( true, Calculadora.sePuedenMultiplicar(matrizA, matrizB) );
    }
    
    @Test
    public void testSePuedenMultiplicar4(){
        Fraccion[][] matrizA = new Fraccion[3][7];
        Fraccion[][] matrizB = new Fraccion[4][3];
        
        assertEquals( false, Calculadora.sePuedenMultiplicar(matrizA, matrizB) );
    }
    
    
    @Test
    public void testMultiplicarMatrices1(){
        
        Fraccion[][] matrizA = {
            { new Fraccion(2), new Fraccion(0), new Fraccion(1) },
            { new Fraccion(3), new Fraccion(0), new Fraccion(0) },
            { new Fraccion(5), new Fraccion(1), new Fraccion(1) }
        };
        
        Fraccion[][] matrizB = {
            { new Fraccion(1), new Fraccion(0), new Fraccion(1) },
            { new Fraccion(1), new Fraccion(2), new Fraccion(1) },
            { new Fraccion(1), new Fraccion(1), new Fraccion(0) }
        };
        
        Fraccion[][] matrizEsperada = {
            { new Fraccion(3), new Fraccion(1), new Fraccion(2) },
            { new Fraccion(3), new Fraccion(0), new Fraccion(3) },
            { new Fraccion(7), new Fraccion(3), new Fraccion(6) }
        };
        
        
        assertArrayEquals( matrizEsperada, Calculadora.multiplicar(matrizA, matrizB) );
    }
    
    @Test
    public void testMultiplicarMatrices2(){
        
        Fraccion[][] matrizA = {
            { new Fraccion(0), new Fraccion(1) },
            { new Fraccion(-1), new Fraccion(2) },
            { new Fraccion(4), new Fraccion(2) }
        };
        
        Fraccion[][] matrizB = {
            { new Fraccion(3), new Fraccion(2), new Fraccion(-1) },
            { new Fraccion(-1), new Fraccion(4), new Fraccion(0) },
        };
        
        Fraccion[][] matrizEsperada = {
            { new Fraccion(-1), new Fraccion(4), new Fraccion(0) },
            { new Fraccion(-5), new Fraccion(6), new Fraccion(1) },
            { new Fraccion(10), new Fraccion(16), new Fraccion(-4) }
        };
        
        
        assertArrayEquals( matrizEsperada, Calculadora.multiplicar(matrizA, matrizB) );
    }
    
    @Test
    public void testMultiplicarMatrices3(){
        
        Fraccion[][] matrizA = {
            { new Fraccion(1), new Fraccion(-2), new Fraccion(3) },
            { new Fraccion(4), new Fraccion(0), new Fraccion(1) },
            { new Fraccion(2), new Fraccion(3), new Fraccion(7) }
        };
        
        Fraccion[][] matrizB = {
            { new Fraccion(0), new Fraccion(1), new Fraccion(3) },
            { new Fraccion(1), new Fraccion(4), new Fraccion(1) },
            { new Fraccion(2), new Fraccion(3), new Fraccion(-1) }
        };
        
        Fraccion[][] matrizEsperada = {
            { new Fraccion(4), new Fraccion(2), new Fraccion(-2) },
            { new Fraccion(2), new Fraccion(7), new Fraccion(11) },
            { new Fraccion(17), new Fraccion(35), new Fraccion(2) }
        };
        
        
        assertArrayEquals( matrizEsperada, Calculadora.multiplicar(matrizA, matrizB) );
    }
    
    @Test
    public void testMultiplicarMatrices4(){
        
        Fraccion[][] matrizA = {
            { new Fraccion(-1), new Fraccion(2), new Fraccion(-2) },
            { new Fraccion(3), new Fraccion(2), new Fraccion(1) },
        };
        
        Fraccion[][] matrizB = {
            { new Fraccion(1), new Fraccion(0), new Fraccion(0) },
            { new Fraccion(0), new Fraccion(1), new Fraccion(0) },
            { new Fraccion(0), new Fraccion(0), new Fraccion(1) }
        };
        
        Fraccion[][] matrizEsperada = {
            { new Fraccion(-1), new Fraccion(2), new Fraccion(-2) },
            { new Fraccion(3), new Fraccion(2), new Fraccion(1) },
        };
        
        
        assertArrayEquals( matrizEsperada, Calculadora.multiplicar(matrizA, matrizB) );
    }
    
    @Test
    public void testMultiplicarMatrices5(){
        
        Fraccion[][] matrizA = {
            { new Fraccion(-1), new Fraccion(-2), new Fraccion(2) },
            { new Fraccion(0), new Fraccion(-1), new Fraccion(1) }
        };
        
        Fraccion[][] matrizB = {
            { new Fraccion(-1), new Fraccion(0), new Fraccion(0), new Fraccion(0) },
            { new Fraccion(0), new Fraccion(-2), new Fraccion(0), new Fraccion(0) },
            { new Fraccion(1), new Fraccion(0), new Fraccion(0), new Fraccion(1) }
        };
        
        Fraccion[][] matrizEsperada = {
            { new Fraccion(3), new Fraccion(4), new Fraccion(0), new Fraccion(2) },
            { new Fraccion(1), new Fraccion(2), new Fraccion(0), new Fraccion(1) }
        };
        
        
        assertArrayEquals( matrizEsperada, Calculadora.multiplicar(matrizA, matrizB) );
    }
    
    @Test
    public void testMultiplicarMatrices6(){
        
        Fraccion[][] matrizA = {
            { new Fraccion(1), new Fraccion(4), new Fraccion(0) },
        };
        
        Fraccion[][] matrizB = {
            { new Fraccion(2) },
            { new Fraccion(-1) },
            { new Fraccion(5) }
        };
        
        Fraccion[][] matrizEsperada = {
            { new Fraccion(-2) }
        };
        
        
        assertArrayEquals( matrizEsperada, Calculadora.multiplicar(matrizA, matrizB) );
    }
    
    
    @Test
    public void testMultiplicarMatrices7(){
        
        Fraccion[][] matrizA = {
            { new Fraccion(3), new Fraccion(2), new Fraccion(-1) },
            { new Fraccion(-1), new Fraccion(4), new Fraccion(0) },
        };
        
        Fraccion[][] matrizB = {
            { new Fraccion(0), new Fraccion(1) },
            { new Fraccion(-1), new Fraccion(2) },
            { new Fraccion(4), new Fraccion(2) }
        };
        
        Fraccion[][] matrizEsperada = {
            { new Fraccion(-6), new Fraccion(5) },
            { new Fraccion(-4), new Fraccion(7) }
        };
        
        
        assertArrayEquals( matrizEsperada, Calculadora.multiplicar(matrizA, matrizB) );
    }
    
}
