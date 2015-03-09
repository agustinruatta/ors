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

import javafx.css.PseudoClass;
import javafx.scene.control.TextField;

/**
 * Ésta clase se encarga del manejo de los errores de la GUI de la aplicación.
 * Ésto incluye que mensaje de error mostrar, que efectos mostrar y cómo
 * mostrarlos (Todo ésto solamente aplicable cuando se manejan errores).</br>
 * Éstos errores se producen cuando el usuario:
 * <ul>
 * <li>Ha ingresado un dato inválido.</li>
 * <li>No ha ingresado ningún dato</li>
 * <li>Algún otro motivo</li>
 * </ul>
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 */
public class ManejoErroresGUI {
    
    /**
     * Produce un efecto indicando que existe un error debido a un
     * ingreso incorrecto de datos en un TextField.
     * @param textFieldQueTieneError 
     */
    public static void indicarError( TextField textFieldQueTieneError ){
        
        final PseudoClass errorClass = PseudoClass.getPseudoClass("error");
        
        textFieldQueTieneError.pseudoClassStateChanged(errorClass, true); // or false to unset it
    }
    
    /**
     * Deja de mostrar el efecto que indica un error.
     * @param textFieldQueTieneError 
     */
    public static void dejarDeMostrarError( TextField textFieldQueTieneError ){
        
        final PseudoClass errorClass = PseudoClass.getPseudoClass("error");
        
        textFieldQueTieneError.pseudoClassStateChanged(errorClass, false); // or false to unset it
    }

    private ManejoErroresGUI() {
    }
}
