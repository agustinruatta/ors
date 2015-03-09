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
package GUI.Ayuda;

import GUI.ControladorVistas;
import GUI.ListadoVistas;
import GUI.PresentadorAbstracto;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 *
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 */
public class PresentadorAyuda extends PresentadorAbstracto implements Initializable{
    
    @FXML
    private TreeView<String> menuLateralAyuda;
    
    @FXML
    private TextArea descripcionAyuda;
    
    
    
    @FXML
    private void volverAlMenuPrincipal(){
        ControladorVistas.getInstance().mostrarVista(ListadoVistas.PANTALLA_PRINCIPAL );
    }

    @Override
    @SuppressWarnings("unchecked")
    public void initialize(URL location, ResourceBundle resources) {
        //Este método está implementada a las apuradas. Podría mejorarse
        
        
        final TreeItem<String> optimizacion = new TreeItem<>( this.getStringInternacionalizado("pantalla_principal.optimizacion") );
        
        //Hijos de optimizacion
        final TreeItem<String> cantidadVariablesYRestricciones = new TreeItem<>( this.getStringInternacionalizado("vista_ayuda.pestania_cantidad_variables_restricciones") );
        final TreeItem<String> ingresarDatosOptimizacion = new TreeItem<>( this.getStringInternacionalizado("vista_ayuda.pestania_ingresar_datos_optimizacion") );
        final TreeItem<String> ejemploUso = new TreeItem<>( this.getStringInternacionalizado("vista_ayuda.pestania_ejemplo") );
        final TreeItem<String> errores = new TreeItem<>( this.getStringInternacionalizado("vista_ayuda.pestania_errores") );
        
        //Hijos de ingresar datos de la optimización
        final TreeItem<String> maximizarOMinimizar = new TreeItem<>( this.getStringInternacionalizado("vista_ayuda.pestania_maximizar_minimizar") );
        final TreeItem<String> funcionObjetivo =new TreeItem<>( this.getStringInternacionalizado("vista_ayuda.pestania_funcion_objetivo") );
        final TreeItem<String> restricciones = new TreeItem<>( this.getStringInternacionalizado("vista_ayuda.pestania_restricciones") );
        final TreeItem<String> resoluciones = new TreeItem<>( this.getStringInternacionalizado("vista_ayuda.pestania_resoluciones") );
        
        final TreeItem<String> algunCoeficienteFuncionObjetivoNoCompletado = new TreeItem<>( this.getStringInternacionalizado("vista_ayuda.pestania_algun_coeficiente_funcion_objetivo_no_completado") );
        final TreeItem<String> algunCoeficienteFuncionObjetivoIncorrecto = new TreeItem<>( this.getStringInternacionalizado("vista_ayuda.pestania_algun_coeficiente_funcion_objetivo_incorrecto") );
        final TreeItem<String> igualdadesIncorrectas = new TreeItem<>( this.getStringInternacionalizado("vista_ayuda.pestania_igualdades_incorrectas") );
        final TreeItem<String> algunCoeficienteRestriccionesNoCompletado = new TreeItem<>( this.getStringInternacionalizado("vista_ayuda.pestania_algun_coeficiente_restricciones_no_completado") );
        final TreeItem<String> algunCoeficienteRestriccionesIncorrecto = new TreeItem<>( this.getStringInternacionalizado("vista_ayuda.pestania_algun_coeficiente_restricciones_incorrecto") );
        final TreeItem<String> errorAlRealizarUnaOperacion = new TreeItem<>( this.getStringInternacionalizado("vista_ayuda.pestania_error_al_realizar_operacion") );
        final TreeItem<String> imposibleResolver = new TreeItem<>( this.getStringInternacionalizado("vista_ayuda.pestania_imposible_resolver") );
        final TreeItem<String> sinSolucionesFactibles = new TreeItem<>( this.getStringInternacionalizado("vista_ayuda.pestania_sin_soluciones_factibles") );
        final TreeItem<String> todosLosCoeficientesDeLaFilaSonCero = new TreeItem<>( this.getStringInternacionalizado("vista_ayuda.pestania_todos_los_coeficientes_de_la_fila_son_cero") );
        final TreeItem<String> zNoAcotada = new TreeItem<>( this.getStringInternacionalizado("vista_ayuda.pestania_z_no_acotada") );
        
        this.menuLateralAyuda.getSelectionModel().selectedItemProperty().addListener( new ChangeListener< TreeItem<String> >() {
            

            @Override
            public void changed( ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue, TreeItem<String> itemSeleccionado ) {
                
                if( itemSeleccionado == optimizacion ){
                    descripcionAyuda.setText( "" );
                }
                else if( itemSeleccionado == cantidadVariablesYRestricciones ){
                    descripcionAyuda.setText( getStringInternacionalizado("vista_ayuda.cantidad_variables_restricciones") );
                }
                else if( itemSeleccionado == ingresarDatosOptimizacion ){
                    descripcionAyuda.setText( getStringInternacionalizado("vista_ayuda.ingresar_datos_optimizacion") );
                }
                else if( itemSeleccionado == ejemploUso ){
                    descripcionAyuda.setText( getStringInternacionalizado("vista_ayuda.ejemplo") );
                }
                else if( itemSeleccionado == maximizarOMinimizar ){
                    descripcionAyuda.setText( getStringInternacionalizado("vista_ayuda.maximizar_minimizar") );
                }
                else if( itemSeleccionado == funcionObjetivo ){
                    descripcionAyuda.setText( getStringInternacionalizado("vista_ayuda.funcion_objetivo") );
                }
                else if( itemSeleccionado == restricciones ){
                    descripcionAyuda.setText( getStringInternacionalizado("vista_ayuda.restricciones") );
                }
                else if( itemSeleccionado == resoluciones ){
                    descripcionAyuda.setText( getStringInternacionalizado("vista_ayuda.resoluciones") );
                }
                else if( itemSeleccionado == errores ){
                    descripcionAyuda.setText( getStringInternacionalizado("vista_ayuda.errores") );
                }
                else if( itemSeleccionado == algunCoeficienteFuncionObjetivoNoCompletado ){
                    descripcionAyuda.setText( getStringInternacionalizado("vista_ayuda.algun_coeficiente_funcion_objetivo_no_completado") );
                }
                else if( itemSeleccionado == algunCoeficienteFuncionObjetivoIncorrecto ){
                    descripcionAyuda.setText( getStringInternacionalizado("vista_ayuda.algun_coeficiente_funcion_objetivo_incorrecto") );
                }
                else if( itemSeleccionado == igualdadesIncorrectas ){
                    descripcionAyuda.setText( getStringInternacionalizado("vista_ayuda.igualdades_incorrectas") );
                }
                else if( itemSeleccionado == algunCoeficienteRestriccionesNoCompletado ){
                    descripcionAyuda.setText( getStringInternacionalizado("vista_ayuda.algun_coeficiente_restricciones_no_completado") );
                }
                else if( itemSeleccionado == algunCoeficienteRestriccionesIncorrecto ){
                    descripcionAyuda.setText( getStringInternacionalizado("vista_ayuda.algun_coeficiente_restricciones_incorrecto") );
                }
                else if( itemSeleccionado == errorAlRealizarUnaOperacion ){
                    descripcionAyuda.setText( getStringInternacionalizado("vista_ayuda.error_al_realizar_operacion") );
                }
                else if( itemSeleccionado == imposibleResolver ){
                    descripcionAyuda.setText( getStringInternacionalizado("vista_ayuda.imposible_resolver") );
                }
                else if( itemSeleccionado == sinSolucionesFactibles ){
                    descripcionAyuda.setText( getStringInternacionalizado("vista_ayuda.sin_soluciones_factibles") );
                }
                else if( itemSeleccionado == todosLosCoeficientesDeLaFilaSonCero ){
                    descripcionAyuda.setText( getStringInternacionalizado("vista_ayuda.todos_los_coeficientes_de_la_fila_son_cero") );
                }
                else if( itemSeleccionado == zNoAcotada ){
                    descripcionAyuda.setText( getStringInternacionalizado("vista_ayuda.z_no_acotada") );
                }
                
            }
            
      });
        
        this.menuLateralAyuda.setRoot(optimizacion);
        
        optimizacion.getChildren().addAll( cantidadVariablesYRestricciones, ingresarDatosOptimizacion, ejemploUso, errores );
        
        ingresarDatosOptimizacion.getChildren().addAll( maximizarOMinimizar, funcionObjetivo, restricciones, resoluciones );
        
        errores.getChildren().addAll( 
                algunCoeficienteFuncionObjetivoNoCompletado, algunCoeficienteFuncionObjetivoIncorrecto,
                igualdadesIncorrectas, algunCoeficienteRestriccionesNoCompletado,
                algunCoeficienteRestriccionesIncorrecto, errorAlRealizarUnaOperacion,
                imposibleResolver, sinSolucionesFactibles,
                todosLosCoeficientesDeLaFilaSonCero, zNoAcotada
        );
        
    }
    
    
    
    private String getStringInternacionalizado( String key ){
        return ResourceBundle.getBundle("Internacionalizacion.LANG").getString(key);
    }
    
}
