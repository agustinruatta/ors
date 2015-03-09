/*
 * Copyright (C) 2014 Agustín Ruatta <agustinruatta@gmail.com>
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

package Optimizacion.Enumeraciones;

/**
 * Indica que tipo de variable es.
 * Una variable puede ser de decisión, de holgura, artificial, de superávit,
 * ninguna, M o Z.
 * 
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 */
public enum TipoVariable {
    M( "M" ),
    Z( "Z" ),
    DECISION( "X" ),
    HOLGURA( "H" ),
    ARTIFICIAL( "A" ),
    SUPERAVIT( "S" ),
    TERMINO_INDEPENDIENTE( "TI" );
    
    private final String abreviatura;
    
    private TipoVariable( String abreviatura ){
        this.abreviatura = abreviatura;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    @Override
    public String toString() {
        return getAbreviatura();
    }
    
    
    
}
