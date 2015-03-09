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
package GUI.Stock;

import GUI.ControladorVistas;
import GUI.ListadoVistas;
import GUI.PresentadorAbstracto;
import javafx.fxml.FXML;

/**
 *
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 */
public class PresentadorStock extends PresentadorAbstracto{
    
    @FXML
    private void volverAlMenuPrincipal(){
        ControladorVistas.getInstance().mostrarVista(ListadoVistas.PANTALLA_PRINCIPAL );
    }
}
