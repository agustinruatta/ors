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
package Internacionalizacion;

import GUI.ControladorVistas;
import java.util.Locale;


public class Internacionalizacion {

    public static final String ARCHIVO_IDIOMAS = "Internacionalizacion.LANG";
    
    private static IdiomasDisponibles idiomaElegido;
    
    
    static{
        if( Locale.getDefault().getLanguage().equals("es") ){
            idiomaElegido = IdiomasDisponibles.ESPANIOL;
        }
        else{
            idiomaElegido = IdiomasDisponibles.INGLES;
        }
        
    }
    
    public static void cambiarIdioma( IdiomasDisponibles idioma ){
        idiomaElegido = idioma;
        
        Locale.setDefault( idioma.getLocale() );
        
        //Se recargan todas las vistas
        ControladorVistas.getInstance().inicializar();
        
    }

    public static IdiomasDisponibles getIdiomaElegido() {
        return idiomaElegido;
    }
    
    

    private Internacionalizacion() {
    }
}
