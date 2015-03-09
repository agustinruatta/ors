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
package GUI.PantallaPrincipal;

import GUI.ControladorVistas;
import GUI.ListadoVistas;
import GUI.PresentadorAbstracto;
import javafx.fxml.FXML;
import javafx.scene.control.ToolBar;

/**
 *
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 */
public class PresentadorPantallaPrincipal extends PresentadorAbstracto{
    @FXML
    private ToolBar barraDeHerramientas;
    
    @FXML
    private void clickBotonOptimizacion(){
        ControladorVistas.getInstance().mostrarVista(ListadoVistas.INTRODUCCION_DATOS_OPTIMZACION );
    }
    
    @FXML
    private void clickBotonStock(){
        ControladorVistas.getInstance().mostrarVista(ListadoVistas.STOCK );
    }
    
    @FXML
    private void clickBotonOpciones(){
        ControladorVistas.getInstance().mostrarVista(ListadoVistas.OPCIONES );
    }
    
    @FXML
    private void clickBotonAcercaDe(){
        ControladorVistas.getInstance().mostrarVista(ListadoVistas.ACERCA_DE );
    }
    
    @FXML
    private void clickBotonAyuda(){
        ControladorVistas.getInstance().mostrarVista(ListadoVistas.AYUDA );
    }
    
    @FXML
    private void clickBotonSalir(){
        System.exit( 0 );
    }
    
    
    @FXML
    private void mouseEntraEnBarraDeHerramientas(){
        
        barraDeHerramientas.setOpacity(1.0);
    }
    
    @FXML
    private void mouseSaleDeBarraDeHerramientas(){
        
        barraDeHerramientas.setOpacity(0.5);
    }
}
