package Debug;

import javafx.collections.ObservableList;

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

/**
 *
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 */
public class DebugUtil {
    
    public static void mostrarEnConsola( Object [] vector ){
        
        for( Object objeto : vector ){
            System.out.printf( objeto.toString() + " " );
        }
        
    }
    
    public static void mostrarEnConsola( Object [][] matriz ){
        
        for( int fila = 0; fila < matriz.length; fila++ ){
            for( int columna = 0; columna < matriz[fila].length; columna++ ){
                System.out.printf(matriz[fila][columna].toString() + " ");
            }
            System.out.println("");
        }
        
    }
    
    public static void mostrarEnConsola( ObservableList<?> observableList ){
        
        for( Object item: observableList ){
            System.out.println(item);
        }
        
    }
}
