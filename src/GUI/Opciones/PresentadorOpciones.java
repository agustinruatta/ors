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
package GUI.Opciones;

import GUI.ControladorVistas;
import GUI.ListadoVistas;
import GUI.PresentadorAbstracto;
import Internacionalizacion.IdiomasDisponibles;
import Internacionalizacion.Internacionalizacion;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;

/**
 *
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 */
public class PresentadorOpciones extends PresentadorAbstracto implements Initializable{
    
    @FXML
    private RadioButton idiomaEspaniol;
    
    @FXML
    private RadioButton idiomaIngles;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.establecerOpcionIdiomaPorDefecto();
    }
    
    private void establecerOpcionIdiomaPorDefecto(){
        if( Internacionalizacion.getIdiomaElegido().equals( IdiomasDisponibles.ESPANIOL ) ){
            this.idiomaEspaniol.setSelected(true);
        }
        else if( Internacionalizacion.getIdiomaElegido().equals( IdiomasDisponibles.INGLES ) ){
            this.idiomaIngles.setSelected(true);
        }
        else{
            throw new RuntimeException("Lenguaje seleccionado desconocido");
        }
    }
    
    @FXML
    private void cambioIdioma(){
        Internacionalizacion.cambiarIdioma( this.getIdiomaSeleccionado() );
    }
    
    @FXML
    private void volverMenuPrincipal(){
        ControladorVistas.getInstance().mostrarVista(ListadoVistas.PANTALLA_PRINCIPAL );
    }
    
    private IdiomasDisponibles getIdiomaSeleccionado(){
        
        if( idiomaEspaniol.isSelected() ){
            return IdiomasDisponibles.ESPANIOL;
        }
        else if( idiomaIngles.isSelected() ){
            return IdiomasDisponibles.INGLES;
        }
        else{
            //No debería nunca llegar hasta acá
            throw new RuntimeException("Lenguaje seleccionado desconocido");
        }
    }
    
}
