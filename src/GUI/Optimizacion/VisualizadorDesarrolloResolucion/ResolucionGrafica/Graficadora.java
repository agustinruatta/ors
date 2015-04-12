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
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

/**
 * Graficadora de restricciones.
 * La graficadora va a representar en el eje x al coeficiente X1
 * y en el eje y al coeficiente X2. Es decir:<br>
 * &#32&#32&#32&#32&#32|<br>
 * X2|<br>
 * &#32&#32&#32&#32&#32|<br>
 * &#32&#32&#32&#32&#32|<br>
 * ------------------><br>
 * &#32 0|&#32&#32&#32&#32&#32&#32&#32&#32&#32&#32&#32&#32&#32&#32X1<br>
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 */
public class Graficadora extends BorderPane{
    private final int ANCHO_LINEA_PREDETERMINADO = 2;
    private final int ANCHO_LINEA_SOLUCION_OPTIMA = 4;
    
    private final Color COLOR_FONDO_ZONA_FACTIBLE = Color.GAINSBORO;
    
    private final int ANCHO_GRAFICADORA_MINIMO = 0;
    private final int ALTO_GRAFICADORA_MINIMO = 0;
    
    private final double xMaximo;
    private final double yMaximo;
    
    private final EjeCartesiano ejeCartesiano;
    
    private final int ancho;
    private final int alto;
    
    private final List<RestriccionDeDosVariables> restricciones;
    
    private final RestriccionDeDosVariables solucionOptima;
    
    
    /**
     * Constructor.
     * La diferencia con el otro constructor es que en éste a las restricciones
     * y función objetivo se la envía como una matriz de fracciones (E internamente
     * las pasa a Restricciones).
     * @param _ancho
     * @param _alto
     * @param _restricciones
     * @param _igualdades
     * @param _solucionOptima Restricción que contiene la solución
     * óptima. Si es null, no se dibujará.
     * @throws IllegalArgumentException Cuando alguna/s fila/s de las restricciones
     * no tiene 2 variables o cuando no hay igual cantidad de restricciones que de igualdades.
     */
    public Graficadora( int _ancho, int _alto, Fraccion[][] _restricciones, Igualdades[] _igualdades, RestriccionDeDosVariables _solucionOptima ) {
        
        this( _ancho, _alto, RestriccionDeDosVariables.getRestricciones(_restricciones, _igualdades), _solucionOptima );
    }
    
    /**
     * Constructor.
     * @param _ancho Ancho de la graficadora.
     * @param _alto Alto de la graficadora.
     * @param _restricciones Lista con las restricciones que se van a dibujar
     * @param _solucionOptima Coeficientes de la función objetivo y solución optima,
     * expresadas como una restricción. Si es null, no se dibuja la solución óptima.
     */
    public Graficadora( int _ancho, int _alto, List<RestriccionDeDosVariables> _restricciones, RestriccionDeDosVariables _solucionOptima ) {
        
        super();
        
        this.setPrefWidth( _ancho );
        this.setPrefHeight( _alto );
        
        
        if( _ancho < this.ANCHO_GRAFICADORA_MINIMO || _alto < this.ALTO_GRAFICADORA_MINIMO ){
            throw new IllegalArgumentException( String.format("Anchura o altura incorrecta:  %d  y %d", _ancho, _alto) );
        }
        
        this.ancho = _ancho;
        this.alto = _alto;
        
        this.restricciones = _restricciones;
        
        this.solucionOptima = _solucionOptima;
        
        this.xMaximo = this.getXMaximo();
        this.yMaximo = this.getYMaximo();
        
        this.ejeCartesiano = this.getEjecartesiano();
        
        this.getChildren().add( this.ejeCartesiano );
        
        //Dibujar las restricciones
        _restricciones.stream().forEach((restriccion) -> {
            this.dibujarRestriccion( restriccion );
        });
        
        this.pintarFondoZonaFactible();
        
        //Dibujar la solución optima
        if( _solucionOptima != null ){
            this.dibujarRestriccion( _solucionOptima, ANCHO_LINEA_SOLUCION_OPTIMA, Color.RED );
        }
        
        this.setEstilo();
        
        
        this.ejeCartesiano.prefWidthProperty().bind( this.widthProperty() );
        this.ejeCartesiano.prefHeightProperty().bind( this.heightProperty() );
        
        
    }
    
    
    private void dibujarRestriccion( RestriccionDeDosVariables restriccion ) {
        
        this.dibujarRestriccion(restriccion, ANCHO_LINEA_PREDETERMINADO, this.getColor() );
        
    }
    
    private void dibujarRestriccion( RestriccionDeDosVariables restriccion, int grosorLinea, Color colorLinea ) {
        /*
        Si ambos coeficientes son cero, entonces directamente no se dibuja nada,
        ya que no tiene sentido.
        */
        if( restriccion.getCoeficienteX1().esCero() && restriccion.getCoeficienteX2().esCero() ){
            return;
        }
        
        Path funcionGraficada = new Path();
        
        funcionGraficada.setStroke( colorLinea );

        funcionGraficada.setStrokeWidth( grosorLinea );
        
        /**
         * Dependiendo si X2 o X1 son cero, se modifica la forma en que se grafican.
         * Si X2 = 0 -> Origen modificado, final normal.
         * Si X1 = 0 -> Origen normal, final modificado.
         * Si X1 != 0 y X2 != 0, origen y final normal.
         */
        
        //Se dibuja desde el corte de la y hasta el corte de las x
        if( !restriccion.getCoeficienteX1().esCero() && !restriccion.getCoeficienteX2().esCero() ){
            funcionGraficada.getElements().add( this.getOrigen( 0 , restriccion.getX2CuandoX1EsCero().getValor() ) );
            funcionGraficada.getElements().add( this.getLinea( restriccion.getX1CuandoX2EsCero().getValor() , 0) );
        }
        else if( restriccion.getCoeficienteX2().esCero() ){
            funcionGraficada.getElements().add( this.getOrigen( restriccion.getX1CuandoX2EsCero().getValor() , this.yMaximo ) );
            funcionGraficada.getElements().add( this.getLinea( restriccion.getX1CuandoX2EsCero().getValor() , 0) );
        }
        else if( restriccion.getCoeficienteX1().esCero() ){
            funcionGraficada.getElements().add( this.getOrigen( 0 , restriccion.getX2CuandoX1EsCero().getValor() ) );
            funcionGraficada.getElements().add( this.getLinea( this.xMaximo , restriccion.getX2CuandoX1EsCero().getValor() ) );
        }
        
        this.getChildren().add( funcionGraficada );
    }
    
     
    private void pintarFondoZonaFactible(){
        Path caminoRegionFactible = new Path();
        caminoRegionFactible.setFill( COLOR_FONDO_ZONA_FACTIBLE);
        caminoRegionFactible.setStroke( this.getColor() );
        caminoRegionFactible.setFillRule( FillRule.EVEN_ODD );
        
        List<Coordenada> coordenadasZonaFactible = this.getInterseccionesQueDelimitanRegionFactible();
        
        caminoRegionFactible.getElements().add( this.getOrigen(coordenadasZonaFactible.get(0).getX() , coordenadasZonaFactible.get(0).getY() ) );
        
        for (int numeroCoordenada = 1; numeroCoordenada < coordenadasZonaFactible.size(); numeroCoordenada++) {
            caminoRegionFactible.getElements().add( this.getLinea( coordenadasZonaFactible.get( numeroCoordenada ).getX() , coordenadasZonaFactible.get( numeroCoordenada ).getY() ) );
        }
        
        //Cerrar la zona
        caminoRegionFactible.getElements().add( this.getLinea( coordenadasZonaFactible.get( 0 ).getX() , coordenadasZonaFactible.get( 0 ).getY() ) );
        
        this.getChildren().add( caminoRegionFactible );
    }


    private double mapearX(double x) {
        return ( ( this.ejeCartesiano.getPrefWidth() / this.ejeCartesiano.getEjeX().getUpperBound() ) * x ) + EjeCartesiano.COORDENADA_COMIENZO_EJE_X;
    }

    private double mapearY(double y) {
        return this.getEjecartesiano().getPrefHeight() - (this.ejeCartesiano.getPrefHeight()/ this.ejeCartesiano.getEjeY().getUpperBound() ) * y;
    }


    /**
     * Devuelve un color aleatorio.
     * Ésto se hace así todas las restricciones tienen diferente color.
     * @return 
     */
    private Color getColor(){
        return new Color( Math.random() , Math.random(), Math.random(), 1);
    }

    private EjeCartesiano getEjecartesiano(){
        assert this.restricciones != null;

        return new EjeCartesiano( this.ancho, this.alto, this.xMaximo, this.getPasoEjeX(), this.yMaximo, this.getPasoEjeY() );
    }

    /**
     * Devuelve el X máximo entre todas las restricciones y de la solución óptima
     * @return 
     */
    private double getXMaximo(){
        assert this.restricciones != null;
        
        ArrayList<RestriccionDeDosVariables> restriccionesParaVerXMaximo = new ArrayList<>(this.restricciones);
        
        if( this.seMuestraRectaSolucionOptima() ){
            //Agregamos la solución óptima así no queda fuera de enfoque si llegase a tener un x más grande.
            restriccionesParaVerXMaximo.add( this.solucionOptima );
        }

        double xMaximoTemp = 0;

        for( RestriccionDeDosVariables restriccion : restriccionesParaVerXMaximo ){
            Fraccion X1 = restriccion.getX1CuandoX2EsCero();

            //Nunca toca al eje x (Porque X1 es igual a cero).
            if( X1 == null ){
                continue;
            }

            if( X1.getValor()  > xMaximoTemp ){
                xMaximoTemp = restriccion.getX1CuandoX2EsCero().getValor();
            }

        }

        //Se le suma 1 para que no quede tan "apretado" el grafico.
        return xMaximoTemp + 1;
    }

    /**
     * Devuelve el Y máximo entre todas las restricciones y de la solución óptima
     * @return 
     */
    private double getYMaximo(){
        assert this.restricciones != null;
        
        ArrayList<RestriccionDeDosVariables> restriccionesParaVerYMaximo = new ArrayList<>(this.restricciones);
        
        if( this.seMuestraRectaSolucionOptima() ){
            //Agregamos la solución óptima así no queda fuera de enfoque si llegase a tener una y más grande.
            restriccionesParaVerYMaximo.add( this.solucionOptima );
        }

        double yMaximoTemp = 0;

        for( RestriccionDeDosVariables restriccion : restriccionesParaVerYMaximo ){
            Fraccion X2 = restriccion.getX2CuandoX1EsCero();

            //Nunca toca al eje y (Porque X2 es igual a cero).
            if( X2 == null ){
                continue;
            }

            if( X2.getValor()  > yMaximoTemp ){
                yMaximoTemp = restriccion.getX2CuandoX1EsCero().getValor();
            }

        }

        //Se le suma 1 para que no quede tan "apretado" el grafico.
        return yMaximoTemp + 1;
    }
    
    /**
     * Indica si se muestra la recta que pasa
     * por la solución óptima. Ésta recta es de
     * la función objetivo
     * @return 
     */
    private boolean seMuestraRectaSolucionOptima(){
        return this.solucionOptima != null;
    }

    private int getPasoEjeY(){
        return (int) ( this.yMaximo / 10 ) + 1;
    }

    private int getPasoEjeX(){
        return (int) ( this.xMaximo / 10 ) + 1;
    }


    private MoveTo getOrigen( double x, double y ){
        return new MoveTo( this.mapearX( x ), this.mapearY( y ) );
    }

    private LineTo getLinea( double x, double y ){
        return new LineTo( this.mapearX( x ), this.mapearY( y ) );
    } 

    private void setEstilo(){
        //this.setStyle("-fx-background-color: rgb(35, 39, 50);");
    }



    /**
     * Devuelve las intersecciones entre las restricciones que cumplan
     * con todas las restricciones presentes.
     * @return 
     */
    public List<Coordenada> getInterseccionesQueDelimitanRegionFactible(){
        assert this.restricciones != null;

        ArrayList<Coordenada> coordenadasQueCumpleTodasLasRestricciones = new ArrayList<>();

        for( Coordenada coordenada : this.getInterseccionesEntreRestricciones() ){
            boolean cumpleLasRestricciones = true;

            for( RestriccionDeDosVariables restriccion: this.restricciones ){

                if( !restriccion.cumpleConLaRestriccion( coordenada.getX() , coordenada.getY() ) ){
                    cumpleLasRestricciones = false;
                    break;
                }
            }

            if( cumpleLasRestricciones ){
                coordenadasQueCumpleTodasLasRestricciones.add( coordenada );
            }

        }
        
        OrdenadorListasCoordenada ordenador = new OrdenadorListasCoordenada(coordenadasQueCumpleTodasLasRestricciones);
        
        return ordenador.getListaOrdenadaEnSentidoAntiHorario();

    }

    /**
     * Devuelve las intersecciones entre las restricciones (Incluídas
     * las intersecciones producidas por las restricciones adicionales
     * artificiales que delimitan el área de dibujado).
     * @return 
     */
    public List<Coordenada> getInterseccionesEntreRestricciones(){
        ArrayList<Coordenada> intersecciones = new ArrayList<>();

        /*
        Ir restricción por restricción para saber las intersecciones
        que posee con las otras restricciones.
        Además de las otras restricciones que ya hay, se deben agregar
        4 restricciones adicionales que son los bordes del cuadrado
        de la graficadora. Éstos se agregan para que no se dibuje hasta el
        infinito si las restricciones son mayores o iguales (>=) sino
        el contorno de la graficadora. Son:
        x = 0;
        x = 0;
        x = xMaximo;
        y = yMaximo;
         */

        final ArrayList<RestriccionDeDosVariables> restriccionesParaComparar = new ArrayList<>( this.restricciones );

        restriccionesParaComparar.add( new RestriccionDeDosVariables(Fraccion.UNO, Fraccion.CERO, Igualdades.IGUAL, Fraccion.CERO) );
        restriccionesParaComparar.add( new RestriccionDeDosVariables(Fraccion.CERO, Fraccion.UNO, Igualdades.IGUAL, Fraccion.CERO) );
        restriccionesParaComparar.add( new RestriccionDeDosVariables(Fraccion.UNO, Fraccion.CERO, Igualdades.IGUAL, new Fraccion( this.xMaximo ) ) );
        restriccionesParaComparar.add( new RestriccionDeDosVariables(Fraccion.CERO, Fraccion.UNO, Igualdades.IGUAL, new Fraccion( this.yMaximo ) ) );

        for( int numeroRestriccionOriginal = 0; numeroRestriccionOriginal < this.restricciones.size(); numeroRestriccionOriginal++ ){
            RestriccionDeDosVariables restriccionOriginal = this.restricciones.get( numeroRestriccionOriginal );

            /*
            Se arranca del numero de restriccion original mas 1 así no se encuentra la misma
            restricción y no devuelve la misma intersección dos veces.
            */
            for( int numeroRestriccionParaComparar = numeroRestriccionOriginal + 1; numeroRestriccionParaComparar < restriccionesParaComparar.size(); numeroRestriccionParaComparar++ ){
                RestriccionDeDosVariables restriccionParaComparar = restriccionesParaComparar.get( numeroRestriccionParaComparar );

                /*
                Se saltea si la restricción original y para comparar son las mismas
                (No necesariamente la misma instancia), ya que el cálculo daría 0/0
                debido a que existen infinitas intersecciones (Además que no tiene sentido comparar).
                 */
                if( restriccionOriginal.equals( restriccionParaComparar ) ){
                    continue;
                }
                
                Coordenada interseccion = RestriccionDeDosVariables.getInterseccion(restriccionOriginal, restriccionParaComparar);
                
                
                if( interseccion != null ){
                    intersecciones.add( interseccion );
                }

            }
        }

        //Agregar las intersecciones que conformar el cuadrado de graficado
        intersecciones.add( new Coordenada( 0, 0) );
        intersecciones.add( new Coordenada( 0, this.yMaximo ) );
        intersecciones.add( new Coordenada( this.xMaximo, 0 ) );
        intersecciones.add( new Coordenada( this.xMaximo, this.yMaximo ) );

        return intersecciones;
    }
    
    
        
        
}
