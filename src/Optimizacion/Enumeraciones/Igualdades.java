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
 *
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 */
public enum Igualdades {
    MENOR_O_IGUAL( "<=" ),
    IGUAL( "=" ),
    MAYOR_O_IGUAL( ">=" );
    
    public static final Igualdades[] conjuntoIgualdades = { MENOR_O_IGUAL, IGUAL, MAYOR_O_IGUAL };
    /**
     * Representaciones textuales de los diferentes tipos de igualdades ("&lt=", "=" y ">=")
     */
    public static final String[] RepresentacionesTextualesTiposIgualdades = { MENOR_O_IGUAL.getTextoRepresentativo(), IGUAL.getTextoRepresentativo(), MAYOR_O_IGUAL.getTextoRepresentativo() };
    
    
    
    private final String textoRepresentativo;

    private Igualdades(String textoRepresentativo) {
        this.textoRepresentativo = textoRepresentativo;
    }

    public String getTextoRepresentativo() {
        return textoRepresentativo;
    }
    
    
    /**
     * Devuelve el tipo de igualdad que posee el texto representativo enviado como
     * parámetro.
     * @param textoRepresentativo Texto representativo del tipo de igualdad que se quiere buscar.
     * @return El tipo de igualdad con el texto representativo enviado como parámetro si existe alguna.
     * Si no se encuentra ninguna, devuelve null.
     * @throw IllegalArgumentException si el parámetro enviado no coincide con ninguna igualdad.
     */
    public static Igualdades getTipoIgualdad( String textoRepresentativo ){
        
        for( Igualdades igualdad : conjuntoIgualdades ){
            if( textoRepresentativo.equals( igualdad.getTextoRepresentativo() ) ){
                return igualdad;
            }
        }
        
        return null;
        
    }
    
    

    
    @Override
    /**
     * Igual que llamar a getTextoRepresentativo().
     */
    public String toString(){
        return getTextoRepresentativo();
    }
    
}
