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
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import org.junit.Test;

/**
 *
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 */
public class FraccionTest {
    
    
    /**
     * Se prueba el constructor que recibe como parámetros 2 enteros.
     */
    @Test
    public void testConstructor1(){
        Fraccion fraccion1 = new Fraccion(8, 3);
        Fraccion fraccion2 = new Fraccion( new BigInteger("8"), new BigInteger("3") );
        
        //Deberían ser iguales
        assertEquals( fraccion1, fraccion2 );
    }
    
    /**
     * Se prueba el constructor que recibe como parámetros 2 enteros.
     */
    @Test
    public void testConstructor2(){
        Fraccion fraccion1 = new Fraccion(3, 5);
        Fraccion fraccion2 = new Fraccion( new BigInteger("3"), new BigInteger("5") );
        
        //Deberían ser iguales
        assertEquals( fraccion1, fraccion2 );
    }
    
    /**
     * Se prueba el constructor que recibe como parámetros 2 enteros.
     */
    @Test
    public void testConstructor3(){
        Fraccion fraccion1 = new Fraccion(8, 3);
        Fraccion fraccion2 = new Fraccion( new BigInteger("10"), new BigInteger("3") );
        
        //No deberían ser iguales debido a que tiene numerador diferente
        assertNotSame(fraccion1, fraccion2 );
    }
    
    /**
     * Se prueba el constructor que recibe como parámetros un double.
     */
    @Test
    public void testConstructor4(){
        Fraccion fraccion1 = new Fraccion(0);
        
        //Deberían ser iguales
        assertEquals(fraccion1, new Fraccion( BigInteger.ZERO , BigInteger.ONE) );
    }
    
    /**
     * Se prueba el constructor que recibe como parámetros un String.
     * Se envía un parámetro válido que no es necesario simplificarlo.
     */
    @Test
    public void testConstructor5(){
        Fraccion fraccion1 = new Fraccion("55/2");
        Fraccion fraccion2 = new Fraccion(55, 2);
        
        //Deberían ser iguales
        assertEquals(fraccion1, fraccion2 );
    }
    
    
    /**
     * Se prueba el constructor que recibe como parámetros un String.
     * Se envía un parámetro válido que es necesario simplificarlo.
     */
    @Test
    public void testConstructor6(){
        Fraccion fraccion1 = new Fraccion("110/2");
        Fraccion fraccion2 = new Fraccion(55, 1);
        
        //Deberían ser iguales
        assertEquals(fraccion1, fraccion2 );
    }
    
    /**
     * Se prueba el constructor que recibe como parámetros un String.
     * Se envía un parámetro válido que no contiene el signo de Fracción.
     */
    @Test
    public void testConstructor7(){
        Fraccion fraccion1 = new Fraccion("110");
        Fraccion fraccion2 = new Fraccion(110, 1);
        
        assertEquals(fraccion1, fraccion2);
    }
    
    /**
     * Se prueba el constructor que recibe como parámetros un String.
     * Se envía un parámetro válido que contiene el numerador negativo,
     * sin signo de fracción.
     */
    @Test
    public void testConstructor8(){
        Fraccion fraccion1 = new Fraccion("-110");
        Fraccion fraccion2 = new Fraccion(-110, 1);
        
        assertEquals(fraccion1, fraccion2);
    }
    
    /**
     * Se prueba el constructor que recibe como parámetros un String.
     * Se envía un parámetro válido que contiene el numerador negativo,
     * con signo de fracción.
     */
    @Test
    public void testConstructor9(){
        Fraccion fraccion1 = new Fraccion("-110/50");
        Fraccion fraccion2 = new Fraccion(-110, 50);
        
        assertEquals(fraccion1, fraccion2);
    }
    
    /**
     * Se prueba el constructor que recibe como parámetros un String.
     * Se envía un parámetro inválido que contiene más de un signo
     * de Fracción (Los dos signos están juntos).
     */
    @Test
    public void testConstructor10(){
        try {
            Fraccion fraccion1 = new Fraccion("110//55");
        }
        catch (IllegalArgumentException e) {
            assert true;
            return;
        }
        
        Assert.fail();
    }
    
    /**
     * Se prueba el constructor que recibe como parámetros un String.
     * Se envía un parámetro inválido que contiene más de un signo
     * de Fracción (Los dos signos están separados).
     */
    @Test
    public void testConstructor11(){
        try {
            Fraccion fraccion1 = new Fraccion("110/5/5");
        }
        catch (IllegalArgumentException e) {
            assert true;
            return;
        }
        
        Assert.fail();
    }
    
    /**
     * Se prueba el constructor que recibe como parámetros un String.
     * Se envía un parámetro inválido porque no contiene numerador.
     */
    @Test
    public void testConstructor12(){
        try {
            Fraccion fraccion1 = new Fraccion("/55");
        }
        catch (IllegalArgumentException e) {
            assert true;
            return;
        }
        
        Assert.fail();
    }
    
    /**
     * Se prueba el constructor que recibe como parámetros un String.
     * Se envía un parámetro inválido porque no contiene numerador.
     */
    @Test
    public void testConstructor13(){
        try {
            Fraccion fraccion1 = new Fraccion("/55");
        }
        catch (IllegalArgumentException e) {
            assert true;
            return;
        }
        
        Assert.fail();
    }
    
    /**
     * Se prueba el constructor que recibe como parámetros un String.
     * Se envía un parámetro inválido porque no contiene un entero.
     */
    @Test
    public void testConstructor14(){
        try {
            Fraccion fraccion1 = new Fraccion("55a55");
        }
        catch (IllegalArgumentException e) {
            assert true;
            return;
        }
        
        Assert.fail();
    }
    
    
    /**
     * Se prueba el constructor que recibe como parámetros un String.
     * Se envía un parámetro válido que contiene un número decimal.
     */
    @Test
    public void testConstructor15(){
        Fraccion fraccion = new Fraccion("0.5");
        
        assertEquals( new Fraccion(1, 2) , fraccion );
    }
    
    /**
     * Se prueba el constructor que recibe como parámetros un String.
     * Se envía un parámetro válido que contiene un número decimal.
     */
    @Test
    public void testConstructor16(){
        Fraccion fraccion = new Fraccion("3.1");
        
        assertEquals( new Fraccion(31, 10) , fraccion );
    }
    
    /**
     * Se prueba el constructor que recibe como parámetros un String.
     * Se envía un parámetro válido que contiene un número decimal.
     */
    @Test
    public void testConstructor17(){
        Fraccion fraccion = new Fraccion("-0.55555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555");
        
        assertEquals( new Fraccion(-0.55555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555) , fraccion );
    }
    
    /**
     * Se prueba el constructor que recibe como parámetros un String.
     * Se envía un parámetro válido que contiene un número decimal.
     */
    @Test
    public void testConstructor18(){
        Fraccion fraccion = new Fraccion("5.");
        
        assertEquals( new Fraccion(5) , fraccion );
    }
    
    
    
    /**
     * TEST sonIguales()
     */
    
    /**
     * Prueba si son iguales el mismo objeto.
     * Deben ser iguales
     */
    @Test 
    public void testSonIguales1(){
        Fraccion fraccionA = new Fraccion( new BigInteger("2"), new BigInteger("1"));
        
        boolean sonIguales = fraccionA.equals(fraccionA);
        
        assertEquals(sonIguales, true);
    }
    
    /**
     * Prueba si dos objetos distintos son iguales.
     * No deben ser iguales.
     */
    @Test 
    public void testSonIguales2(){
        Fraccion fraccionA = new Fraccion( new BigInteger("2"), new BigInteger("1"));
        Object fraccionB = new Object();
        
        boolean sonIguales = fraccionA.equals(fraccionB);
        
        assertEquals(sonIguales, false);
    }
    
    
    @Test 
    public void testSonIguales3(){
        Fraccion fraccionA = new Fraccion( new BigInteger("2"), new BigInteger("1"));
        Fraccion fraccionB = new Fraccion( new BigInteger("2"), new BigInteger("1"));
        
        boolean sonIguales = fraccionA.equals(fraccionB);
        
        assertEquals(sonIguales, true);
    }
    
    @Test 
    public void testSonIguales4(){
        Fraccion fraccionA = new Fraccion( new BigInteger("2"), new BigInteger("1"));
        Fraccion fraccionB = new Fraccion( new BigInteger("3"), new BigInteger("1"));
        
        boolean sonIguales = fraccionA.equals(fraccionB);
        
        assertEquals(sonIguales, false);
    }

    
    @Test
    public void testSiSeSimplifica1() {
        Fraccion fraccion = new Fraccion( new BigInteger("4"), new BigInteger("2"));
        Fraccion resultadoEsperado = new Fraccion( new BigInteger("2"), new BigInteger("1"));
        
        assertEquals(fraccion, resultadoEsperado);
    }
    
    @Test
    public void testSiSeSimplifica2() {
        Fraccion fraccion = new Fraccion( new BigInteger("2"), new BigInteger("2"));
        Fraccion resultadoEsperado = new Fraccion( new BigInteger("1"), new BigInteger("1"));
        
        assertEquals(fraccion, resultadoEsperado);
    }
    
    @Test
    public void testSiSeSimplifica3() {
        Fraccion fraccion = new Fraccion( new BigInteger("457"), new BigInteger("89"));
        Fraccion resultadoEsperado = new Fraccion( new BigInteger("457"), new BigInteger("89"));
        
        assertEquals(fraccion, resultadoEsperado);
    }
    
    @Test
    public void testSiSeSimplifica4() {
        Fraccion fraccion = new Fraccion( new BigInteger("-4"), new BigInteger("2"));
        Fraccion resultadoEsperado = new Fraccion( new BigInteger("-2"), new BigInteger("1"));
        
        assertEquals(fraccion, resultadoEsperado);
    }
    
    @Test
    public void testSiSeSimplifica5() {
        Fraccion fraccion = new Fraccion( new BigInteger("-4"), new BigInteger("-2"));
        Fraccion resultadoEsperado = new Fraccion( new BigInteger("2"), new BigInteger("1"));
        
        assertEquals(fraccion, resultadoEsperado);
    }
    
    @Test
    public void testSiSeSimplifica6() {
        Fraccion fraccion = new Fraccion( new BigInteger("10251"), new BigInteger("3"));
        Fraccion resultadoEsperado = new Fraccion( new BigInteger("3417"), new BigInteger("1"));
        
        assertEquals(fraccion, resultadoEsperado);
    }
    
    @Test
    public void testSiSeSimplifica7() {
        Fraccion fraccion = new Fraccion( new BigInteger("-10251"), new BigInteger("3"));
        Fraccion resultadoEsperado = new Fraccion( new BigInteger("-3417"), new BigInteger("1"));
        
        assertEquals(fraccion, resultadoEsperado);
    }
    
    @Test
    public void testSiSeSimplifica8() {
        Fraccion fraccion = new Fraccion( new BigInteger("10251"), new BigInteger("-3"));
        Fraccion resultadoEsperado = new Fraccion( new BigInteger("-3417"), new BigInteger("1"));
        
        assertEquals(fraccion, resultadoEsperado);
    }
    
    @Test
    public void testSiSeSimplifica9() {
        Fraccion fraccion = new Fraccion( new BigInteger("-10251"), new BigInteger("-3"));
        Fraccion resultadoEsperado = new Fraccion( new BigInteger("3417"), new BigInteger("1"));
        
        assertEquals(fraccion, resultadoEsperado);
    }
    
    
    
    
    //*****Test getFracción*****
    
    @Test
    public void testGetFraccion1(){
        Fraccion fraccionRecibida = new Fraccion( 2.45 );
        Fraccion fraccionEsperada = new Fraccion( new BigInteger("49"), new BigInteger("20"));
        
        assertEquals(fraccionRecibida, fraccionEsperada);
    }
    
    @Test
    public void testGetFraccion2(){
        Fraccion fraccionRecibida = new Fraccion( -2.45 );
        Fraccion fraccionEsperada = new Fraccion( new BigInteger("-49"), new BigInteger("20"));
        
        assertEquals(fraccionRecibida, fraccionEsperada);
    }
    
    @Test
    public void testGetFraccion3(){
        Fraccion fraccionRecibida = new Fraccion( 98.658 );
        Fraccion fraccionEsperada = new Fraccion( new BigInteger("49329"), new BigInteger("500"));
        
        assertEquals(fraccionRecibida, fraccionEsperada);
    }
    
    @Test
    public void testGetFraccion4(){
        Fraccion fraccionRecibida = new Fraccion( -98.658 );
        Fraccion fraccionEsperada = new Fraccion( new BigInteger("-49329"), new BigInteger("500"));
        
        assertEquals(fraccionRecibida, fraccionEsperada);
    }
    
    @Test
    public void testGetFraccion5(){
        Fraccion fraccionRecibida = new Fraccion( 5 );
        Fraccion fraccionEsperada = new Fraccion( new BigInteger("5"), new BigInteger("1"));
        
        assertEquals(fraccionRecibida, fraccionEsperada);
    }
    
    
    
    //*****Test toString()*****
    public void testToString1(){
        Fraccion fraccion = new Fraccion( new BigInteger("32"), new BigInteger("1"));
        String representacion = fraccion.toString();
        
        assertEquals(representacion, "( 32 / 1 )");
    }
    
    
    /**
     * Test of getFraccion method, of class Fraccion.
     */
    @Test
    public void testGetFraccion() {
    }

    /**
     * Test of getNumerador method, of class Fraccion.
     */
    @Test
    public void testGetNumerador() {
    }

    /**
     * Test of getDenominador method, of class Fraccion.
     */
    @Test
    public void testGetDenominador() {
    }

    /**
     * Test of equals method, of class Fraccion.
     */
    @Test
    public void testEquals() {
    }

    /**
     * Test of hashCode method, of class Fraccion.
     */
    @Test
    public void testHashCode() {
    }

    /**
     * Test of toString method, of class Fraccion.
     */
    @Test
    public void testToString() {
    }

    
    /**
     * *****Test getNegativo()
     */
    @Test
    public void testGetNegativo1() {
        Fraccion fraccion = new Fraccion( new BigInteger("32"), new BigInteger("1"));
        Fraccion negativo = fraccion.getNegativo();
        Fraccion resultadoEsperado = new Fraccion( new BigInteger("-32"), new BigInteger("1"));
        
        assertEquals( negativo, resultadoEsperado );
        
    }
    
    @Test
    public void testGetNegativo2() {
        Fraccion fraccion = new Fraccion( new BigInteger("-32"), new BigInteger("1"));
        Fraccion negativo = fraccion.getNegativo();
        Fraccion resultadoEsperado = new Fraccion( new BigInteger("32"), new BigInteger("1"));
        
        assertEquals( negativo, resultadoEsperado );
        
    }
    
    
    /**
     * *****Test esMenorQue()
     */
    
    
    public void testEsMenorQue1(){
        Fraccion fraccionA = new Fraccion( 1 );
        Fraccion fraccionB = new Fraccion( -1 );
        
        assertEquals( fraccionB.esMenorQue(fraccionA) , true);
    }
    
    public void testEsMenorQue2(){
        Fraccion fraccionA = new Fraccion( 1 );
        Fraccion fraccionB = new Fraccion( -1 );
        
        assertEquals( fraccionA.esMenorQue(fraccionB) , false);
    }
    
    public void testEsMenorQue3(){
        Fraccion fraccionA = new Fraccion( 1 );
        Fraccion fraccionB = new Fraccion( 1 );
        
        assertEquals( fraccionA.esMenorQue(fraccionB) , false);
    }
    
    @Test
    public void testEsMenorQue4(){
        Fraccion fraccionA = new Fraccion( 1 );
        Fraccion fraccionB = new Fraccion( 2 );
        
        assertEquals( fraccionA.esMenorQue(fraccionB), true);
    }
    
    @Test
    public void testEsMenorQue5(){
        Fraccion fraccionA = new Fraccion( new BigInteger("8"), new BigInteger("5") );
        Fraccion fraccionB = new Fraccion( new BigInteger("9"), new BigInteger("7") );
        
        assertEquals( fraccionA.esMenorQue(fraccionB), false);
    }
    
    
    @Test
    public void esMayorOIgualACero1(){
        assertEquals( true , new Fraccion(0).esMayorOIgualACero() );
    }
    
    @Test
    public void esMayorOIgualACero2(){
        assertEquals( true , new Fraccion(2).esMayorOIgualACero() );
    }
    
    @Test
    public void esMayorOIgualACero3(){
        assertEquals( false , new Fraccion(-1).esMayorOIgualACero() );
    }
    
    @Test
    public void esMenorOIgualACero1(){
        assertEquals( true , new Fraccion(0).esMenorOIgualACero() );
    }
    
    @Test
    public void esMenorOIgualACero2(){
        assertEquals( true , new Fraccion(-1).esMenorOIgualACero() );
    }
    
    @Test
    public void esMenorOIgualACero3(){
        assertEquals( false , new Fraccion(1).esMenorOIgualACero() );
    }
    
    
    @Test
    public void getValor1(){
        Fraccion fraccion = new Fraccion(1, 2);
        
        assertEquals( fraccion.getValor() , 0.5, 0 );
        
    }
    
    @Test
    public void getValor2(){
        Fraccion fraccion = new Fraccion(3, 5);
        
        assertEquals( fraccion.getValor() , 0.6, 0 );
        
    }
    
    
    /**
     * Crea una fracción que sea muy grande positiva, imposible de representar como double.
     * Verifica que devuelva <code>Double.POSITIVE_INFINITY</code>.
     */
    @Test
    public void getValor3(){
        
        Fraccion fraccion = Calculadora.multiplicar( new Fraccion("999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999") , new Fraccion("999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999") );
        double a = fraccion.getValor();
        assertEquals( Double.POSITIVE_INFINITY, fraccion.getValor(), 0 );
        
    }
    
    
    /**
     * Crea una fracción que sea muy grande negativa, imposible de representar como double.
     * Verifica que devuelva <code>Double.NEGATIVE_INFINITY</code>.
     */
    @Test
    public void getValor4(){
        
        Fraccion fraccion = Calculadora.multiplicar( new Fraccion("-999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999") , new Fraccion("999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999") );
        double a = fraccion.getValor();
        assertEquals( Double.NEGATIVE_INFINITY, fraccion.getValor(), 0 );
        
    }
    
    
    
}
