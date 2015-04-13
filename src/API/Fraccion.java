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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Objects;

/**
 * <p>Representa una fracción (El numerador y denominador).</p>
 * <p>Ésta clase representa una fracción. Contiene 2 atributos:</p>
 * <p>*numerador: representa el numerador de la fracción.</p>
 * <p>*denominador: representa el denominador de la fracción.</p>
 * <p>Cuando se crea la Fracción, si se puede, se simplifica automáticamente 
 * (Ver constructor para más detalles).</p>
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 * @since 1.0
 */
public class Fraccion implements Cloneable{
    
    /**
     * Constante que representa una fracción igual a cero.
     */
    public static final Fraccion CERO = new Fraccion( 0, 1 );
    
    /**
     * Constante que representa una fracción igual a uno.
     */
    public static final Fraccion UNO = new Fraccion( 1, 1 );
    
    public static final Fraccion DOS = new Fraccion( 2,1 );
    
    public static final Fraccion TRES = new Fraccion( 3, 1);
    
    public static final Fraccion CUATRO = new Fraccion( 4, 1 );
    
    public static final Fraccion MENOS_UNO = new Fraccion( -1, 1 );
    
    public static final Fraccion MENOS_DOS = new Fraccion( -2, 1 );
    
    public static final Fraccion MENOS_TRES = new Fraccion( -3, 1 );
    
    public static final Fraccion MENOS_CUATRO = new Fraccion( -4, 1 );
    
    
    private final static MathContext PRECISION_BIG_DECIMAL = MathContext.DECIMAL64;
    
    /**
     * Cantidad de números decimales que se van a tomar del número enviado como
     * parámetro en el constructor con parámetro double.
     * @since 1.0
     */
    public static final int CANTIDAD_NUMEROS_DECIMALES_TOMADOS = 20;
    
    /**
     * Numero por el cual se va a multiplicar el número enviado como parámetro
     * en el método getFracción, y que luega será dividido por el mismo número.
     */
    private static final BigDecimal NUMERO_MULTIPLICADOR = new BigDecimal( new BigInteger("10").pow(CANTIDAD_NUMEROS_DECIMALES_TOMADOS ) );

    
    /**
     * Signo que se usa para indicar una fracción
     * cuando se usa el constructor que admite un
     * String como parámetro.
     */
    private final String SIGNO_FRACCION = "/";
    

    /**
     * Guarda en un objeto BigInteger el número del numerador.
     * @since 1.0
     */
    private final BigInteger numerador;
    
    /**
     * Guarda en un objeto BigInteger el número del denominador.
     * @since 1.0
     */
    private final BigInteger denominador;
    
    /**
     * Contructor.
     * @param fraccionONumeroDecimal Es un string del tipo AAAA/BBBB en donde
     * AAAA es un número entero y BBBB es otro número entero, o es un string
     * que sea un número entero o un número decimal.
     * Por ejemplo los siguientes argumentos serían válidos
     * <ul>
     * <li>"55/12  (Fracción)".</li>
     * <li>"33/1  (Fracción)".</li>
     * <li>"1234/1234  (Fracción)".</li>
     * <li>"3425  (Número entero)"</li>
     * <li>"3455.43 (Numero decimal)</li>
     * </ul>. 
     * Sin embargo los siguientes serían inválidos:
     * <ul>
     * <li>"/57" (No posee numerador).</li>
     * <li>"123/" (No posee denominador)</li>
     * <li>"12a/12" (Contiene letras)<li>
     * <li>"123//32" (Tiene 2 veces el signo de fracción)</li>
     * <li>"123.34/12" (El denominador no es un argumento entero)</li>
     * </ul>
     * @throws IllegalArgumentException Cuando contiene más de un signo que
     * representa la fracción, no tiene numerador, no contiene denominador
     * o alguno de éstos no es un número válido.
     */
    public Fraccion( String fraccionONumeroDecimal ){
        
        //*****Inicio control para saber si el parámetro es correcto
        
        //Tiene más de un signo de division
        if( fraccionONumeroDecimal.indexOf( this.SIGNO_FRACCION ) != fraccionONumeroDecimal.lastIndexOf( this.SIGNO_FRACCION ) ){
            throw new IllegalArgumentException("Contiene más de un signo que representa la Fracción");
        }
        //No tiene numerador
        if( String.valueOf( fraccionONumeroDecimal.charAt(0) ).equals( this.SIGNO_FRACCION )){
            throw new IllegalArgumentException("No tiene numerador");
        }
        //No tiene denominador
        if( String.valueOf( fraccionONumeroDecimal.charAt( fraccionONumeroDecimal.length() - 1 ) ).equals( this.SIGNO_FRACCION )){
            throw new IllegalArgumentException("No tiene denominador");
        }
        
        BigInteger num = null;
        BigInteger den = null;
        
        //Dividir la fracción para obtener el numerador y el denominador
        String[] fraccionDividida = fraccionONumeroDecimal.split( this.SIGNO_FRACCION );
        
        //Es una Fracción
        if( fraccionONumeroDecimal.contains( this.SIGNO_FRACCION ) ){
            
            try {
                num = new BigInteger( fraccionDividida[0] );
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Numerador incorrecto");
            }

            try {
                den = new BigInteger( fraccionDividida[1] );
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Denominador incorrecto");
            }
        }
        //Es un número entero o decimal, no tiene el signo de Fracción
        else{
            try {
                /*
                Multiplicamos el número enviado como parámetro por un número grande
                (NUMERO_MULTIPLICADOR) para quitarle los decimales, y luego lo dividimos
                por el mismo número (Generamos la fracción).
                No importa si el número es un entero.
                */

                //Al crear la fracción, se simplifica automáticamente.
                num = new BigDecimal(fraccionDividida[0], Fraccion.PRECISION_BIG_DECIMAL).multiply( NUMERO_MULTIPLICADOR ).toBigInteger();
                den = NUMERO_MULTIPLICADOR.toBigInteger();
            }
            catch (NumberFormatException e) {
                throw new IllegalArgumentException("El argumento enviado no es un entero o decimal");
            }
            
            
        }
        
        
        assert num != null;
        assert den != null;
        
        BigInteger[] coeficientesSimplificados = this.getSimplificado( num, den );
        
        this.numerador = coeficientesSimplificados[0];
        this.denominador = coeficientesSimplificados[1];
        
    }
    
    /**
     * Constructor
     * @param numerador
     * @param denominador
     * @throws IllegalArgumentException Si el denominador es igual a 0.
     */
    public Fraccion( long numerador, long denominador ) throws IllegalArgumentException{
        
        this( BigInteger.valueOf( numerador ) , BigInteger.valueOf( denominador ) );
        
    }
    
    
    public Fraccion( double valor ){
        /**
         * Multiplicamos el número enviado como parámetro por un número grande
         * (NUMERO_MULTIPLICADOR) para quitarle los decimales, y luego lo dividimos
         * por el mismo número (Generamos la fracción).*/
        
        //Al crear la fracción, se simplifica automáticamente.
        this( ( new BigDecimal(valor, Fraccion.PRECISION_BIG_DECIMAL) ).multiply( NUMERO_MULTIPLICADOR ).toBigInteger(), NUMERO_MULTIPLICADOR.toBigInteger() );
    }

    /**
     * Constructor.
     * @param num Numerador de la fracción.
     * @param den Denominador de la fracción
     * @throws IllegalArgumentException Si el denominador es 0
     * @since 1.0
     */
    @SuppressWarnings("AssignmentToMethodParameter")
    public Fraccion(BigInteger num, BigInteger den) throws IllegalArgumentException{
        
        BigInteger[] coeficientesSimplificados = this.getSimplificado( num, den );
        
        this.numerador = coeficientesSimplificados[0];
        this.denominador = coeficientesSimplificados[1];
    }

    
    
    /**
     * Devuelve el numerador.
     * @return Objeto BigInteger con el valor del numerador.
     * @since 1.0
     */
    public BigInteger getNumerador() {
        return numerador;
    }

    
    /**
     * Devuelve el denominador.
     * @return Objeto BigInteger con el valor del denominador.
     * @since 1.0
     */
    public BigInteger getDenominador() {
        return denominador;
    }
    
    
    /**
     * Indica si el objeto actual es una función negativa (&lt0).
     * Tener en cuenta que se indica si es MENOR a 0, no MENOR O IGUAL.
     * @return true si es negativa, false caso contrario.
     */
    public boolean esFraccionNegativa(){
        return this.esNumeroNegativo( numerador );
    }
    
    /**
     * Indica si el objeto actual es una función positiva (>0).
     * Tener en cuenta que se indica si es MAYOR a 0, no MAYOR O IGUAL.
     * @return true si es negativa, false caso contrario.
     */
    public boolean esFraccionPositiva(){
        return this.esNumeroPositivo(numerador );
    }
    
    /**
     * Devuelve el negativo de la fracción.
     * Se multiplica a la fracción por -1.
     * @return Negativo de la fracción (-this).
     */
    public Fraccion getNegativo(){
        BigInteger nuevoNumerador = this.numerador.negate();
        
        return new Fraccion( nuevoNumerador, denominador );
    }
    
    public boolean esCero(){
        return this.equals( Fraccion.CERO );
    }
    
    public boolean esMayorOIgualACero(){
        return !this.esFraccionNegativa();
    }
    
    public boolean esMenorOIgualACero(){
        return !this.esFraccionPositiva();
    }
    
    
    /**
     * Devuelve un vector de longitud 2 con el numerador y denominador
     * enviados como parámetros simplificados.
     * @param num Numerador de la fracción.
     * @param den Denominador de la fracción.
     * @return BigInteger[0] = numerador simplificado , BigInteger[1] = denominador simplificado
     */
    private BigInteger[] getSimplificado( final BigInteger numerador, final BigInteger denominador ){
        BigInteger num = numerador;
        BigInteger den = denominador;
        
        //1 - Comprobar que el denominador no sea cero
        if( den.equals( BigInteger.ZERO ) ){
            throw new IllegalArgumentException("Denominador igual a cero.");
        }
        
        //2 - Simplificar
        
        //Obtener el máximo común denominador
        BigInteger mcd = getMaximoComunDivisor(num, den);

        //Mientras se pueda simplificar
        while ( !mcd.equals( new BigInteger("1") ) ) {
            //Simplificar el numerador.
            num = num.divide( mcd );

            //Simplificar el denominador
            den = den.divide( mcd );

            mcd = getMaximoComunDivisor(num, den);
        }
        //Fin simplificar


        //3 - Acomodar los signos del numerador y denominador
        //Si tanto numerador como denominador son negativos, quitarles el signo.
        if( esNumeroNegativo(num) && esNumeroNegativo(den) ){
            num = num.abs();
            den = den.abs();
        }
        //Si el denominador es negativo, pasarle el signo al numerador.
        else{
            if( esNumeroNegativo(den) ){
                num = num.negate();
                den = den.abs();
            }
        }
        
        BigInteger[] coeficientesSimplificados = {num,den};
        
        return coeficientesSimplificados;
    }
    
    /**
     * Devuelve el máximo común divisor de los 2 parámetros enviados.
     * A través del método de Euclides, se obtiene el MCD.
     * Se genera un método propio debido a que los parámetros pasados son BigInteger's.
     * @param numeroA Primer número.
     * @param numeroB Segundo número.
     * @return El máximo comun divisor de los dos números enviados.
     * @since 1
     */
    private BigInteger getMaximoComunDivisor( BigInteger numeroA, BigInteger numeroB) {
        //Asegurar que los 2 numeros son NO negativos
        BigInteger primerNumero = numeroA.abs();
        BigInteger segundoNumero = numeroB.abs();
        
        //Obtener el mínimo y el máximo.
        BigInteger minimo = primerNumero.min( segundoNumero );
        BigInteger maximo = primerNumero.max( segundoNumero );
        
        
        //Mientras mínimo sea diferente de 0
        while ( !minimo.equals( new BigInteger("0") ) ) {
            BigInteger resto = maximo.mod( minimo );
            maximo = minimo;
            minimo = resto;
        }
        
        
        //Devolver el maximo común denominador (Es igual al último máximo)
        return maximo;
    }
    
    
    /**
     * Indica si el número es negativo.
     * @param numero Número a verificar.
     * @return true si es negativo (<0), false caso contrario.
     * @since 1.0
     */
    private boolean esNumeroNegativo( BigInteger numero){
        return ( numero.compareTo( BigInteger.ZERO ) == -1);
    }
    
    /**
     * Indica si el número es positivo.
     * @param numero Número a verificar.
     * @return true si es positivo (>0), false caso contrario.
     * @since 1.0
     */
    private boolean esNumeroPositivo( BigInteger numero){
        return ( numero.compareTo( BigInteger.ZERO ) == 1);
    }
    
    
    /**
     * Indica si el objeto actual es menor que la fracción enviada por parámetro.
     * @param fraccionAComparar Fracción con la cual se va a comparar el objeto actual.
     * @return true si es menor, false caso contario.
     */
    public boolean esMenorQue( Fraccion fraccionAComparar ){
        
        /*
        Si al objeto actual negado se le suma la fracción a comparar, y ésto da positivo,
        entonces el objeto actual es menor que la fracción a comprar.
        */
        Fraccion resultado = Calculadora.sumar( this.getNegativo(), fraccionAComparar );
        
        return resultado.esFraccionPositiva();
    }
    
    
    /**
     * Devuelve el valor double que representa la Fracción.
     * Si el número es muy grande (Tanto positivamente como negativamente)
     * se devuelve los valores <code>Double.POSITIVE_INFINITY</code> o <code>Double.NEGATIVE_INFINITY</code>.
     * Si el numerador tanto como el denominador son muy grandes, produce
     * una división de infinito sobre infinito. En ese caso se devuelve 1.
     * @return 
     */
    public double getValor() {
        double valor = this.numerador.doubleValue() / this.denominador.doubleValue();
        
        if( valor != Double.NaN ){
            return valor;
        }
        else{
            return 1;
        }
    }
    
    
    /**
     * Compara si ésta fracción es igual al objeto pasado.
     * Son iguales si tanto el numerador como el denominador poseen los mismos valores.
     * @param objetoCualquiera Fracción que se debe comparar
     * @return true si son iguales, false caso contrario.
     * @since 1.0
     */
    @Override
    public boolean equals( Object objetoCualquiera){
        
        //Si es el mismo objeto
        if (this == objetoCualquiera) {
            return true;
        }
        if (objetoCualquiera instanceof Fraccion) {
            Fraccion fraccion= (Fraccion) objetoCualquiera;
            
            //Si los objetos tienen el mismo numerador y denominador
            if( fraccion.getNumerador().equals( numerador ) && fraccion.getDenominador().equals( denominador )){
                return true;
            }
        }
        
        return false;
    }

    /**
     * Devuelve un código hash utilizando el numerador y el denominador
     * @return Número hash
     * @since 1.0
     */
    @Override
    public int hashCode(){
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.numerador);
        hash = 17 * hash + Objects.hashCode(this.denominador);
        return hash;
    }
    
    
    /**
     * Devuelve un String representando la Fracción.
     * @return Si el denominador es diferente de 1,
     * devuelve "( numerador / denominador )", en donde numerador
     * y denominador son los números correspondientes de ellos.<br>
     * Si el denominador es igual a 1, devuelve el número que representa
     * el numerador.
     */
    @Override
    public String toString(){
        
        //Si no tiene denominador igual a 1, devolver en formato de fracción
        if( !this.denominador.equals( BigInteger.ONE ) ){
            return this.toFraccionString();
        }
        //Tiene denominador igual a 1, devolver solamente en formato de número entero
        else{
            return this.numerador.toString();
        }
        
    }
    
    /**
     * Devuelve un String representando una fracción siempre,
     * sin importar que tenga o no como denominador un 1.
     * @return 
     */
    public String toFraccionString(){
        return ( "( " + numerador.toString() + " / " + denominador.toString() + " )" );
    }
    
    
    /**
     * Devuelve un String con un numero decimal que representa la Fracción.
     * @return 
     */
    public String toPlainString(){
        return ( new BigDecimal(numerador) ).divide( new BigDecimal(denominador) ).toString();
    }
    
    
    @Override
    @SuppressWarnings("CloneDeclaresCloneNotSupported")
    public Fraccion clone(){
        try {
            return (Fraccion) super.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
