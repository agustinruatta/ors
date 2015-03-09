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
package GUI.Optimizacion.VisualizadorDesarrolloResolucion.PasoResolucionTableau;

import API.Fraccion;
import GUI.Optimizacion.FilaCoeficientes;
import GUI.PresentadorAbstracto;
import Optimizacion.MetodoDeLasDosFases.PasoMetodoDeLasDosFases;
import Optimizacion.Tableau;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 *
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 */
public class PresentadorPasoResolucionTableau extends PresentadorAbstracto{
    
    @FXML
    private Label pasoNumeroLabel;
    
    @FXML
    private Label filaElegidaComoPivot;
    
    @FXML
    private Label columnaElegidaComoPivot;
    
    @FXML
    private Label nuevaVariableBasica;
    
    @FXML
    private TableView< FilaCoeficientes > tableauAntesDeSimplificar;
    
    @FXML
    private TableView<FilaCoeficientes> tableuDespDeSimplificarYAntesGaussJordan;
    
    @FXML
    private TableView<FilaCoeficientes> tableauDespGaussJordan;
    
    
    
    /**
     * Muestra el paso indicado como parámetro.
     * @param numeroPaso Número del paso que se envía.
     * @param pasoDeLaFase Paso del método de las 2 fases que se va a mostrar.
     */
    public void mostrarPaso( int numeroPaso, PasoMetodoDeLasDosFases pasoDeLaFase){
        
        this.pasoNumeroLabel.setText( String.valueOf( numeroPaso + 1 ) );
        
        this.filaElegidaComoPivot.setText( String.valueOf( pasoDeLaFase.getFilaPivot() + 1 ) );
        this.columnaElegidaComoPivot.setText( String.valueOf( pasoDeLaFase.getColumnaPivot() + 1 ) );
        
        this.nuevaVariableBasica.setText( pasoDeLaFase.getNuevaVariableBasica().toString() );
        
        this.completarTabla( tableauAntesDeSimplificar , pasoDeLaFase.getTableauAntesDeSimplificar() );
        this.completarTabla( tableuDespDeSimplificarYAntesGaussJordan , pasoDeLaFase.getTableauDespDeSimplificarYAntesGaussJordan() );
        this.completarTabla( tableauDespGaussJordan , pasoDeLaFase.getTableauDespuesDeAplicarGaussJordan());
        
    }
    
    
    
    /**
     * Rellena la tabla enviada como parámetro con el Tableau enviado como
     * parámetro.
     * @param tablaACompletar
     * @param tableau 
     */
    private void completarTabla( TableView<FilaCoeficientes> tablaACompletar, Tableau tableau ){
        
        //Agregar las columnas a la tabla
        for( int numeroColumna = 0; numeroColumna < tableau.CANTIDAD_COLUMNAS; numeroColumna++ ){
            TableColumn<FilaCoeficientes, String> columna = new TableColumn<>( tableau.getVariable( numeroColumna ).toString() );
            
            columna.setSortable( false );
            
            final int j = numeroColumna;
            
            columna.setCellValueFactory( new Callback<TableColumn.CellDataFeatures< FilaCoeficientes, String>, ObservableValue<String>>() {

                @Override
                public ObservableValue<String> call( TableColumn.CellDataFeatures< FilaCoeficientes, String> parametro ) {
                    return new SimpleStringProperty( parametro.getValue().get(j).get() );
                }
                
            });
            
            tablaACompletar.getColumns().add(columna);
        }
        
        
        //Agregar los coeficientes
        for( Fraccion[] fila : tableau.getTableauEnMatriz() ){
            tablaACompletar.getItems().add( new FilaCoeficientes( fila ) );
        }
        
    }
}
