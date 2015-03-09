/*
 * Copyright (C) 2015 Agustín Ruatta <agustinruatta@gmail.com>
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
package GUI.Optimizacion.VisualizadorDesarrolloResolucion.ResolucionGrafica;

import API.Fraccion;
import Optimizacion.Enumeraciones.Igualdades;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 */
public class RestriccionDeDosVariablesTest {
    
    public RestriccionDeDosVariablesTest() {
    }

    @Test
    public void getX1CuandoX2EsCero() {
        RestriccionDeDosVariables restriccion = new RestriccionDeDosVariables( Fraccion.UNO , Fraccion.DOS, Igualdades.MENOR_O_IGUAL, Fraccion.UNO);
        
        Assert.assertEquals( restriccion.getX1CuandoX2EsCero(), Fraccion.UNO );
    }
    
    @Test
    public void getX2CuandoX1EsCero(){
        RestriccionDeDosVariables restriccion = new RestriccionDeDosVariables( Fraccion.UNO , Fraccion.DOS, Igualdades.MENOR_O_IGUAL, Fraccion.UNO);
        
        Assert.assertEquals( restriccion.getX2CuandoX1EsCero(), new Fraccion(1, 2) );
    }
    
    
    /**
     * Compara dos instancias diferentes pero que tienen mismos coeficientes,
     * igualdad y término independiente. Deben ser iguales.
     */
    @Test
    public void equals1(){
        RestriccionDeDosVariables restriccionA = new RestriccionDeDosVariables(Fraccion.UNO, Fraccion.DOS, Igualdades.IGUAL, Fraccion.TRES);
        RestriccionDeDosVariables restriccionB = new RestriccionDeDosVariables(Fraccion.UNO, Fraccion.DOS, Igualdades.IGUAL, Fraccion.TRES);
        
        Assert.assertEquals( restriccionA.equals( restriccionB ), true );
    }
    
    /**
     * Compara dos instancias diferentes pero que tienen diferente coeficientes,
     * igualdad y término independiente. Deben ser distintos.
     */
    @Test
    public void equals2(){
        RestriccionDeDosVariables restriccionA = new RestriccionDeDosVariables(Fraccion.UNO, Fraccion.DOS, Igualdades.IGUAL, Fraccion.TRES);
        RestriccionDeDosVariables restriccionB = new RestriccionDeDosVariables(Fraccion.DOS, Fraccion.DOS, Igualdades.IGUAL, Fraccion.TRES);
        
        Assert.assertEquals( restriccionA.equals( restriccionB ), false );
    }
    
    
    @Test
    public void getInterseccion1(){
        RestriccionDeDosVariables restriccion1 = new RestriccionDeDosVariables(Fraccion.UNO, Fraccion.UNO, Igualdades.IGUAL, Fraccion.UNO);
        RestriccionDeDosVariables restriccion2 = new RestriccionDeDosVariables(Fraccion.MENOS_UNO, Fraccion.UNO, Igualdades.IGUAL, Fraccion.CERO);

        Assert.assertEquals( RestriccionDeDosVariables.getInterseccion(restriccion1, restriccion2), new Coordenada(0.5, 0.5) );
    }
    
    @Test
    public void getInterseccion2(){
        RestriccionDeDosVariables restriccion1 = new RestriccionDeDosVariables(Fraccion.UNO, Fraccion.CERO, Igualdades.IGUAL, Fraccion.UNO);
        RestriccionDeDosVariables restriccion2 = new RestriccionDeDosVariables(Fraccion.DOS, Fraccion.CERO, Igualdades.IGUAL, Fraccion.UNO);

        Assert.assertEquals( RestriccionDeDosVariables.getInterseccion(restriccion1, restriccion2), null );
    }
    
    @Test
    public void getInterseccion3(){
        RestriccionDeDosVariables restriccion1 = new RestriccionDeDosVariables(Fraccion.UNO, Fraccion.UNO, Igualdades.IGUAL, Fraccion.UNO);
        RestriccionDeDosVariables restriccion2 = new RestriccionDeDosVariables(Fraccion.CERO, Fraccion.UNO, Igualdades.IGUAL, Fraccion.DOS);

        Assert.assertEquals( RestriccionDeDosVariables.getInterseccion(restriccion1, restriccion2), new Coordenada(-1, 2) );
    }
    
    
    @Test
    public void cumpleConLaRestriccion1(){
        //x1 + 2*x2 <= 4
        RestriccionDeDosVariables restriccion = new RestriccionDeDosVariables( Fraccion.UNO , Fraccion.DOS, Igualdades.MENOR_O_IGUAL, Fraccion.CUATRO );
        
        Assert.assertEquals( restriccion.cumpleConLaRestriccion( 1 , 1 ), true );
    }
    
    @Test
    public void cumpleConLaRestriccion2(){
        //x1 + 2*x2 <= 4
        RestriccionDeDosVariables restriccion = new RestriccionDeDosVariables( Fraccion.UNO , Fraccion.DOS, Igualdades.MENOR_O_IGUAL, Fraccion.CUATRO );
        
        Assert.assertEquals( restriccion.cumpleConLaRestriccion( 1 , 2 ), false );
    }
    
    @Test
    public void cumpleConLaRestriccion3(){
        //x1 + 2*x2 = 4
        RestriccionDeDosVariables restriccion = new RestriccionDeDosVariables( Fraccion.UNO , Fraccion.DOS, Igualdades.IGUAL, Fraccion.CUATRO );
        
        Assert.assertEquals( restriccion.cumpleConLaRestriccion( 2 , 1 ), true );
    }
    
    @Test
    public void cumpleConLaRestriccion4(){
        //x1 + 2*x2 = 4
        RestriccionDeDosVariables restriccion = new RestriccionDeDosVariables( Fraccion.UNO , Fraccion.DOS, Igualdades.IGUAL, Fraccion.CUATRO );
        
        Assert.assertEquals( restriccion.cumpleConLaRestriccion( 1 , 1 ), false );
    }
    
    @Test
    public void cumpleConLaRestriccion5(){
        //x1 + 2*x2 >= 4
        RestriccionDeDosVariables restriccion = new RestriccionDeDosVariables( Fraccion.UNO , Fraccion.DOS, Igualdades.MAYOR_O_IGUAL, Fraccion.CUATRO );
        
        Assert.assertEquals( restriccion.cumpleConLaRestriccion( 2 , 2 ), true );
    }
    
    @Test
    public void cumpleConLaRestriccion6(){
        //x1 + 2*x2 >= 4
        RestriccionDeDosVariables restriccion = new RestriccionDeDosVariables( Fraccion.UNO , Fraccion.DOS, Igualdades.MAYOR_O_IGUAL, Fraccion.CUATRO );
        
        Assert.assertEquals( restriccion.cumpleConLaRestriccion( 1 , 1 ), false );
    }
    
    
    @Test
    public void getRestricciones1(){
        
        Fraccion[][] restricciones = {
            { new Fraccion(1), new Fraccion(1), new Fraccion(1) },
            { new Fraccion(1), new Fraccion(0), new Fraccion(0.5) }
        };
        
        Igualdades[] igualdades = {
            Igualdades.MENOR_O_IGUAL,
            Igualdades.MENOR_O_IGUAL
        };
        
        List<RestriccionDeDosVariables> restriccionesEsperada = new ArrayList<>();
        restriccionesEsperada.add( new RestriccionDeDosVariables(Fraccion.UNO, Fraccion.UNO, Igualdades.MENOR_O_IGUAL, Fraccion.UNO) );
        restriccionesEsperada.add( new RestriccionDeDosVariables(Fraccion.UNO, Fraccion.CERO, Igualdades.MENOR_O_IGUAL, new Fraccion(1, 2) ) );
        
        Assert.assertEquals( restriccionesEsperada, RestriccionDeDosVariables.getRestricciones(restricciones, igualdades) );
    }
    
    @Test
    public void getRestricciones2(){
        
        Fraccion[][] restricciones = {
            { new Fraccion(1), new Fraccion(4), new Fraccion(8) },
            { new Fraccion(-1), new Fraccion(0), new Fraccion(2) }
        };
        
        Igualdades[] igualdades = {
            Igualdades.IGUAL,
            Igualdades.MAYOR_O_IGUAL
        };
        
        List<RestriccionDeDosVariables> restriccionesEsperada = new ArrayList<>();
        restriccionesEsperada.add( new RestriccionDeDosVariables( Fraccion.UNO, Fraccion.CUATRO, Igualdades.IGUAL, new Fraccion(8) ) );
        restriccionesEsperada.add( new RestriccionDeDosVariables(Fraccion.MENOS_UNO, Fraccion.CERO, Igualdades.MAYOR_O_IGUAL, Fraccion.DOS ) );
        
        Assert.assertEquals( restriccionesEsperada, RestriccionDeDosVariables.getRestricciones(restricciones, igualdades) );
    }
}
