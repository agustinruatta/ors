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
package GUI.AcercaDe;

import GUI.ControladorVistas;
import GUI.ListadoVistas;
import GUI.PresentadorAbstracto;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 *
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 */
public class PresentadorAcercaDe extends PresentadorAbstracto implements Initializable{
    
    @FXML
    private WebView navegador;
    
    private final String URL_GITHUB_ORS = "https://github.com/agustinruatta/ors";
    private final int ALTURA_NAVEGADOR = 600;
    
    @FXML
    private void volverAlMenuPrincipal(){
        ControladorVistas.getInstance().mostrarVista(ListadoVistas.PANTALLA_PRINCIPAL );
    }
    
    @FXML
    private void clickHipervinculo(){
        final WebEngine webEngine = navegador.getEngine();
        
        webEngine.load( this.URL_GITHUB_ORS );
        
        this.navegador.setVisible(true);
        this.navegador.setPrefHeight( this.ALTURA_NAVEGADOR );
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.navegador.setVisible(false);
        this.navegador.setPrefHeight(0);
    }
}
