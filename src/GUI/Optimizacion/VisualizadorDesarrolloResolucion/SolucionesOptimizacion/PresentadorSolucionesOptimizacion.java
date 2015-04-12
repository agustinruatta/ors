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
package GUI.Optimizacion.VisualizadorDesarrolloResolucion.SolucionesOptimizacion;

import API.Fraccion;
import GUI.Optimizacion.FilaCoeficientes;
import GUI.PresentadorAbstracto;
import Optimizacion.Enumeraciones.TipoVariable;
import Optimizacion.MetodoDeLasDosFases.MetodoDeLasDosFases;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

/**
 *
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 */
public class PresentadorSolucionesOptimizacion extends PresentadorAbstracto{
    
    @FXML
    private TableView<FilaCoeficientes> tablaSoluciones;
    
    @FXML
    private TextField solucionOptima;
    
    @FXML
    private RadioButton mostrarFraccion;
    
    private MetodoDeLasDosFases metodoDeLasDosFases;
    
    /**
     * Muestra las tablaSoluciones.
     * @param _metodoDeLasDosFases
     */
    public void mostrarSoluciones( MetodoDeLasDosFases _metodoDeLasDosFases ){
        this.metodoDeLasDosFases = _metodoDeLasDosFases;
        
        this.completarTablaSoluciones();
        
        this.completarSolucionOptima();
    }
    
    
    private void completarTablaSoluciones(){
        Fraccion[] soluciones = this.metodoDeLasDosFases.getSoluciones();
        
        //Agregar las columnas a la tabla
        for( int numeroColumna = 0; numeroColumna < soluciones.length; numeroColumna++ ){
            TableColumn<FilaCoeficientes, String> columna = new TableColumn<>( TipoVariable.DECISION + String.valueOf( numeroColumna + 1 ) );
            
            columna.setSortable( false );
            
            final int j = numeroColumna;
            
            columna.setCellValueFactory( new Callback<TableColumn.CellDataFeatures< FilaCoeficientes, String>, ObservableValue<String>>() {

                @Override
                public ObservableValue<String> call( TableColumn.CellDataFeatures< FilaCoeficientes, String> parametro ) {
                    return new SimpleStringProperty( parametro.getValue().get(j).get() );
                }
                
            });
            
            this.tablaSoluciones.getColumns().add(columna);
        }
        
        //Agregar las tablaSoluciones
        this.tablaSoluciones.getItems().add( new FilaCoeficientes( soluciones ) );
        
    }
    
    
    private void completarSolucionOptima(){
        if( this.mostrarFraccion.isSelected() ){
            this.completarSolucionOptimaComoFraccion();
        }
        else{
            this.completarSolucionOptimaComoDecimal();
        }
    }
    
    private void completarSolucionOptimaComoFraccion(){
        this.solucionOptima.setText( this.metodoDeLasDosFases.getSolucionOptima().toString() );
    }
    
    private void completarSolucionOptimaComoDecimal(){
        this.solucionOptima.setText( this.metodoDeLasDosFases.getSolucionOptima().toPlainString() );
    }
    
    
    /**
     * Se apretó un RadioButton para elegir si se muestra
     * la solución óptima como Fracción o número decimal.
     */
    @FXML
    private void cambioPreferenciasVisualizaciónSolucionOptima(){
        this.completarSolucionOptima();
    }
}
