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

import javafx.event.EventHandler;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.input.KeyEvent;

/**
 * Contiene una serie de métodos que producen validaciones
 * comúnes para una serie de componentes de la GUI.
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 */
public class ComprobacionesGUI {
    
    public static EventHandler<KeyEvent> comprobarQueSeaUnNumeroEntero() {

        EventHandler<KeyEvent> manejadorEvento = new EventHandler<KeyEvent>() {
            
            @Override
            public void handle(KeyEvent keyEvent) {
                if ( !"0123456789".contains( keyEvent.getCharacter() ) ) {
                    keyEvent.consume();

                }
            }
            
        };
        
        return manejadorEvento;
    }
    
    public static EventHandler<CellEditEvent<Double, String>> comprobarQueSeIngreseUnNumeroDecimalALaCelda() {

        EventHandler<CellEditEvent<Double, String>> manejadorEvento = new EventHandler<CellEditEvent<Double, String>>() {
            
            @Override
            public void handle(CellEditEvent<Double, String> evento) {
                //No es la forma más eficiente, pero si la más fácil sin agregar librerías externas
                try {
                    Double.parseDouble( evento.getNewValue() );
                }
                catch (NumberFormatException e) {
                    evento.consume();
                }
            }
        };
        
        return manejadorEvento;
    }
    
    
    
    
    
    
    
    

    private ComprobacionesGUI() {
    }
    
}
