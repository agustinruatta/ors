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

import API.Calculadora;
import API.Fraccion;
import Optimizacion.Enumeraciones.Igualdades;
import java.util.ArrayList;
import java.util.List;

/**
 * Sirve para guardar los datos de una restricción con dos variables.
 * Usado para la representación en el método gráfico.
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 */
public class RestriccionDeDosVariables {

    /**
     * Devuelve la coordenada en donde se interceptan las dos restricciones.
     * Si no se interceptan (Debido a que, por ejemplo, restriccionA -> x1 >= 5
     * y restriccionB -> x1 >= 6) devuelve null.<br>
     * Si las dos restricciones son iguales, también devuelve null (Debido
     * a que hay infinitos puntos donde se interceptan).
     * @param restriccion1
     * @param restriccion2
     * @return
     */
    public static Coordenada getInterseccion(RestriccionDeDosVariables restriccion1, RestriccionDeDosVariables restriccion2) {
        if( restriccion1.equals( restriccion2 ) ){
            return null;
        }
        /*
        Deducción de la fórmula para obtener las intersecciones.
        --------------------------------------------------------
        
        Tenemos 2 restricciones
        
        1)Restricción 1 ->  A*x1 + B*x2 = Ta
        2)Restricción 2 ->  C*x1 + D*x2 = Tb
        
        Aunque se podría resolver por método de Gauss-Jordan, es un
        desperdicio de recursos. Lo mejor es obtener las ecuaciones
        que permiten obtener en un solo paso x1 y x2.
        
        Despejando de 1, obtenemos que:
        
        ----------------------
        |x2 = (Ta - A*x1) / B |
        ----------------------
        
        Reemplazamos eso en 2 y tenemos que:
        
        3.1)  C*x1 + D*( (Ta - A*x1) / B ) = Tb
        3.2)  B*C*x1 + D*Ta - D*A*x1 = Tb*B
        
              ----------------------------------
        3.3)  |x1 = (Tb*B - D*Ta) / (B*C - D*A)|
              ----------------------------------
        
        Observación: cuando dos restricciones no tienen
        intersección (Por ejemplo una es x1<5 y la otra x1<3),
        cuando se quiere obtener x1 queda una división de 0/0.
        */
        final Fraccion A = restriccion1.getCoeficienteX1();
        final Fraccion B = restriccion1.getCoeficienteX2();
        final Fraccion Ta = restriccion1.getTerminoIndependiente();
        
        final Fraccion C = restriccion2.getCoeficienteX1();
        final Fraccion D = restriccion2.getCoeficienteX2();
        final Fraccion Tb = restriccion2.getTerminoIndependiente();
        
        final Fraccion numeradorX1 = Calculadora.restar( Calculadora.multiplicar( Tb , B ) , Calculadora.multiplicar( D , Ta ) );
        final Fraccion denominadorX1 = Calculadora.restar( Calculadora.multiplicar( B , C ) , Calculadora.multiplicar( D , A ) );
        
        //No hay intersección
        if( numeradorX1.esCero() && denominadorX1.esCero() ){
            return null;
        }
        /*
        Tampoco hay intersección. Ésto puede darse cuando se tiene
        x2 = 12
        x2 = 0
        Son 2 restricciones que no se tocan. Por lo tanto el denominador
        de x1 va a ser cero. Es decir:
        (B*C - D*A) = (1*0 - 1*0)
        
        También se da cuando hay 2 restricciones iguales. Por ejemplo
        x1 + 2x2 = 2
        x1 + 2x2 = 2
        (B*C - D*A) = (2*1 - 2*1)
        */
        if( denominadorX1.esCero() ){
            return null;
        }
        
//        Usado en caso de necesitar un debug
//        System.out.println("\n\n\n");
//        System.out.println(A);
//        System.out.println(B);
//        System.out.println(Ta);
//        System.out.println(C);
//        System.out.println(D);
//        System.out.println(Tb);
//        System.out.println(numeradorX1 + "  " + denominadorX1);
//        System.out.println("\n\n\n");
        
        final Fraccion x1 = Calculadora.dividir(numeradorX1, denominadorX1);
        
        final Fraccion x2;
        
        /*
        Ésto se realiza para evitar la división por 0 cuando B=0.
        En el caso de que B=0, x1 es igual a Ta/A (Se supone que
        no puede haber restricciones con A y B = 0).
        */
        if( !B.esCero() ){
            x2 = Calculadora.dividir( Calculadora.restar( Ta , Calculadora.multiplicar(A, x1) ), B );
        }
        else{
            x2 = Calculadora.dividir( Calculadora.restar( Tb,  Calculadora.multiplicar(C, x1)) , D );
        }
        
        
        return new Coordenada( x1.getValor(), x2.getValor() );
    }
    
    
    public static List<RestriccionDeDosVariables> getRestricciones( Fraccion[][] _restricciones, Igualdades[] _igualdades ){
        if( _restricciones.length != _igualdades.length ){
            throw new IllegalArgumentException("No hay igual cantidad de restricciones que de igualdades.");
        }
        
        ArrayList<RestriccionDeDosVariables> restricciones = new ArrayList<>();
        
        for( int fila = 0; fila < _restricciones.length; fila++ ){
            
            //Son 3 debido a los 2 coeficientes más el término independiente
            if( _restricciones[fila].length != 3 ){
                throw new IllegalArgumentException("No tiene 2 variables");
            }
            
            restricciones.add( new RestriccionDeDosVariables( _restricciones[fila][0], _restricciones[fila][1], _igualdades[fila], _restricciones[fila][2] ) );
        }
        
        return restricciones;
    }
    
    private final Fraccion coeficienteX1;
    
    private final Fraccion coeficienteX2;
    
    private final Igualdades igualdad;
    
    private final Fraccion terminoIndependiente;

    public RestriccionDeDosVariables(Fraccion _coeficienteX1, Fraccion _coeficienteX2, Igualdades _igualdad, Fraccion _terminoIndependiente) {
        if( _coeficienteX1.esCero() && _coeficienteX2.esCero() ){
            throw new IllegalArgumentException("Tanto X1 como X2 son cero");
        }
        
        this.coeficienteX1 = _coeficienteX1;
        this.coeficienteX2 = _coeficienteX2;
        this.igualdad = _igualdad;
        this.terminoIndependiente = _terminoIndependiente;
    }

    public Fraccion getCoeficienteX1() {
        return coeficienteX1;
    }

    public Fraccion getCoeficienteX2() {
        return coeficienteX2;
    }
    

    public Igualdades getIgualdad() {
        return igualdad;
    }

    public Fraccion getTerminoIndependiente() {
        return terminoIndependiente;
    }
    
    
    /**
     * Devuelve el valor del Coeficiente X1 cuando el Coeficiente X2
     * es cero.
     * Por ejemplo, si se tiene la restricción:
     * 11X1 + 3X2 &#60= 5, devolvería 5/11.<br>
     * Si X1 es igual a 0 (Por lo tanto, nunca "toca" a X1), devuelve null.
     * @return 
     */
    public Fraccion getX1CuandoX2EsCero(){
        if( !this.coeficienteX1.esCero() ){
            return Calculadora.dividir( this.terminoIndependiente , this.coeficienteX1 );
        }
        else{
            return null;
        }
        
    }
    
    /**
     * Devuelve el valor del Coeficiente X2 cuando el Coeficiente X1
     * es cero.
     * Por ejemplo, si se tiene la restricción:
     * 11X1 + 3X2 &#60= 5, devolvería 5/3
     * Si X2 es igual a 0 (Por lo tanto, nunca "toca" a X2), devuelve null.
     * @return 
     */
    public Fraccion getX2CuandoX1EsCero(){
        if( !this.coeficienteX2.esCero() ){
            return Calculadora.dividir( this.terminoIndependiente , this.coeficienteX2 );
        }
        else{
            return null;
        }
        
    }
    
    
    /**
     * Indica si los coeficientes enviados como parámetros cumplen
     * con la restricción.
     * @param x1
     * @param x2
     * @return 
     */
    public boolean cumpleConLaRestriccion( double x1, double x2 ){
        
        /*
        De manera predeterminada tomamos que todos los coeficientes
        deben ser mayor o igual que cero.
        Si en algún lado se modifica para que la restricción acepte otro
        tipos de valores, se debe modificar ésta parte del programa.
        */
        
        if( x1 < 0 || x2 < 0 ){
            return false;
        }
        
        double total = this.coeficienteX1.getValor()*x1 + this.coeficienteX2.getValor()*x2;
        
        if( this.igualdad.equals( Igualdades.MENOR_O_IGUAL ) ){
            return total <= this.terminoIndependiente.getValor();
        }
        else if( this.igualdad.equals( Igualdades.IGUAL ) ){
            return total == this.terminoIndependiente.getValor();
        }
        //Mayor o igual
        else{
            return total >= this.terminoIndependiente.getValor();
        }
        
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if( objeto instanceof RestriccionDeDosVariables){
            RestriccionDeDosVariables restriccion = (RestriccionDeDosVariables) objeto;
            
            if( this == restriccion ){
                return true;
            }
            if(
                    this.coeficienteX1.equals( restriccion.getCoeficienteX1() ) &&
                    this.coeficienteX2.equals( restriccion.getCoeficienteX2() ) &&
                    this.igualdad.equals( restriccion.getIgualdad() ) &&
                    this.terminoIndependiente.equals( restriccion.getTerminoIndependiente() )
                    ){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    @Override
    public String toString() {
        return this.coeficienteX1+"X1 " + this.coeficienteX2+"X2 " + this.igualdad + " " + this.terminoIndependiente;
    }
    
    
    
    
    
    
    
    
    
}
