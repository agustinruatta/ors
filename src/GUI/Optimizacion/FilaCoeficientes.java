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
package GUI.Optimizacion;

import API.Fraccion;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 */
public class FilaCoeficientes extends ArrayList<SimpleStringProperty>{
    
    
    /**
     * Constructor.
     * Devuelve una fila de coeficientes vacíos (String "") con la
     * cantidad de columnas indicada con parámetros.
     * @param cantidadColumnas 
     */
    public FilaCoeficientes( int cantidadColumnas ){
        
        for (int i = 0; i < cantidadColumnas; i++) {
            super.add( new SimpleStringProperty("") );
        }
        
    }
    
    
    /**
     * Constructor.
     * Devuelve una fila de coeficientes con los valores enviados como parámetro.
     * @param fila 
     */
    public FilaCoeficientes( Fraccion[] fila ){
        
        for( Fraccion coeficiente : fila ){
            super.add( new SimpleStringProperty( coeficiente.toString() ) );
        }
        
    }
    
    
    private FilaCoeficientes(){};
}
