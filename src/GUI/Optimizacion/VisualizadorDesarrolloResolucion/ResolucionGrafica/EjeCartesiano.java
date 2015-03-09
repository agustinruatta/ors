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

import Optimizacion.Enumeraciones.TipoVariable;
import javafx.geometry.Side;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.Pane;

/**
 *
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 */
public class EjeCartesiano extends Pane{
    
    public static final int COORDENADA_COMIENZO_EJE_X = 37;
    
    private final NumberAxis ejeX;
    private final NumberAxis ejeY;
    
    private final int X_MINIMO = 0;
    private final int Y_MINIMO = 0;
    

    /**
     * Constructor
     * @param ancho Ancho del eje cartesiano.
     * @param alto Alto del eje cartesiano.
     * @param xMaximo Hasta que valor de x se va a dibujar.
     * @param pasoX Cual va a ser el paso en el eje de las x.
     * @param yMaximo Hasta que valor de y se va a dibujar.
     * @param pasoY Cual va a ser el paso en el eje de las y.
     */
    public EjeCartesiano(
            int ancho, int alto,
            double xMaximo, double pasoX,
            double yMaximo, double pasoY
    ) {
        this.hacerTamanioEjeCartesianoFijo(ancho, alto);
        
        this.ejeX = new NumberAxis( this.X_MINIMO , xMaximo, pasoX );
        this.ejeX.setSide(Side.BOTTOM);
        this.ejeX.setMinorTickVisible(true);
        this.ejeX.setPrefWidth(ancho);
        this.ejeX.setLayoutY(alto);
        this.ejeX.setLayoutX( EjeCartesiano.COORDENADA_COMIENZO_EJE_X );
        this.ejeX.setLabel( TipoVariable.DECISION + "1" );

        this.ejeY = new NumberAxis( this.Y_MINIMO, yMaximo, pasoY );
        this.ejeY.setSide(Side.LEFT);
        this.ejeY.setMinorTickVisible(true);
        this.ejeY.setPrefHeight(alto);
//        this.ejeY.layoutXProperty().bind(
//            Bindings.subtract(
//                1,
//                ejeY.widthProperty()
//            )
//        );
        this.ejeY.setLabel( TipoVariable.DECISION + "2" );

        this.getChildren().setAll(ejeX, ejeY);
        
        this.setEstilo();
    }

    public NumberAxis getEjeX() {
        return this.ejeX;
    }

    public NumberAxis getEjeY() {
        return this.ejeY;
    }
    
    /**
     * Hace que el tamaño del eje cartesiano sea fijo
     * (No se pueda modificar posteriormente).
     */
    private void hacerTamanioEjeCartesianoFijo( int ancho, int alto ){
        
        this.setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        this.setPrefSize(ancho, alto);
        this.setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

    }
    
    private void setEstilo(){
            //this.setStyle("-fx-background-color: rgb(35, 39, 50);");
    }
}
