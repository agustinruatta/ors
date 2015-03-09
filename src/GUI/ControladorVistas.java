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
package GUI;

import GestionErrores.GestionErrores;
import java.util.HashMap;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 *
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 */
public class ControladorVistas extends StackPane {
    private static ControladorVistas instanciaUnica;
    
    public static ControladorVistas getInstance(){
        
        if( instanciaUnica == null ){
            instanciaUnica = new ControladorVistas();
        }
        
        return instanciaUnica;
    }
    
    
    
    /**
     * Colección que contiene todas las vistas de la aplicación.
     */
    private final HashMap< ListadoVistas, Vista > vistas;
    
    private final double OPACIDAD_PREDETERMINADA = 0;
    
    @SuppressWarnings("CollectionWithoutInitialCapacity")
    private ControladorVistas() {
        super();
        
        this.vistas = new HashMap<>();
        
        this.inicializar();
        
    }
    
    
    
    public final void inicializar(){
        this.borrarTodasLasVistas();
        
        //Cargar todas las vistas en el HashMap
        this.cargarVista( ListadoVistas.PANTALLA_PRINCIPAL );
        this.cargarVista( ListadoVistas.INTRODUCCION_DATOS_OPTIMZACION );
        this.cargarVista( ListadoVistas.VISUALIZADOR_DESARROLLO_RESOLUCION );
        this.cargarVista( ListadoVistas.STOCK );
        this.cargarVista( ListadoVistas.ACERCA_DE );
        this.cargarVista( ListadoVistas.OPCIONES );
        this.cargarVista( ListadoVistas.AYUDA );
        
        
        //Mostrar la pantalla principal
        this.mostrarVista( ListadoVistas.PANTALLA_PRINCIPAL );
        
    }
    
    public Vista getVista( ListadoVistas vistaACargar ){
        //Las siguientes dos vistas no se cargan desde aca
        assert vistaACargar != ListadoVistas.PASO_RESOLUCION_TABLEAU;
        assert vistaACargar != ListadoVistas.SOLUCIONES_OPTIMIZACION;
        
        return this.vistas.get( vistaACargar );
    }
    
    
    @SuppressWarnings({"BroadCatchBlock", "TooBroadCatch"})
    private void cargarVista( ListadoVistas vistaACargar ) {
        try{
            vistas.put( vistaACargar, new Vista(vistaACargar) );
        }
        catch (Exception e) {
            GestionErrores.registrarError(e);
        }
        
    }

    public void mostrarVista(final ListadoVistas vistaAEstablecer ) {   
        
        assert vistas.get( vistaAEstablecer ) != null;
        
        final DoubleProperty opacidad = this.opacityProperty();

        
        if ( this.seEstaMostrandoAlgunaVista() ) {
            
            Animation animacion = this.getAnimacionCambioEntreVistas(vistaAEstablecer);
            
            animacion.play();

        }
        else {
            this.setOpacity( this.OPACIDAD_PREDETERMINADA );
            
            this.getChildren().add( vistas.get(vistaAEstablecer).getVista() );
            
            Timeline fadeIn = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(opacidad, 0.0)),
                    new KeyFrame(new Duration(2500), new KeyValue(opacidad, 1.0)));
            fadeIn.play();
        }

    }
    
    
    
    private boolean seEstaMostrandoAlgunaVista(){
        return ( !this.getChildren().isEmpty() );
    }
    
    private void borrarTodasLasVistas(){
        this.vistas.clear();
    }
    
    private Animation getAnimacionCambioEntreVistas( final ListadoVistas nuevaVista ){
        final DoubleProperty opacidad = this.opacityProperty();
        
        return new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(opacidad, 1.0)),
                    new KeyFrame(new Duration(1000), new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent t) {
                            getChildren().remove(0);
                            getChildren().add(0, vistas.get(nuevaVista).getVista());
                            Timeline fadeIn = new Timeline(
                                    new KeyFrame(Duration.ZERO, new KeyValue(opacidad, 0.0)),
                                    new KeyFrame(new Duration(800), new KeyValue(opacidad, 1.0)));
                            fadeIn.play();
                        }
                    }, new KeyValue(opacidad, 0.0)));
    }

}
