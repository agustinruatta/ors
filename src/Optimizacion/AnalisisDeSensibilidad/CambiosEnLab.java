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
package Optimizacion.AnalisisDeSensibilidad;

import API.Calculadora;
import API.Fraccion;
import Optimizacion.Tableau;


public class CambiosEnLab {

    private final Tableau tableauFinal;
    private final Fraccion[][] nuevosTerminosIndependientes;

    public CambiosEnLab(Tableau tableauFinal, Fraccion[][] nuevosTerminosIndependientes) {
        
        this.tableauFinal = tableauFinal;
        this.nuevosTerminosIndependientes = nuevosTerminosIndependientes;
        
        if( !this.sonCompatiblesElTableauFinalYElNuevoTI() ){
            throw new IllegalArgumentException("Tableau final y nuevo T.I. incompatibles");
        }
    }
    
    
    /**
     * Devuelve Z*.
     * @return 
     */
    public Fraccion[][] getNuevoZAsterisco(){
        //Nuevo Z* = Viejo y* x nuevos T.I.
        return Calculadora.multiplicar( tableauFinal.getyAsterisco() , nuevosTerminosIndependientes );
    }
    
    /**
     * Devuelve b*.
     * @return 
     */
    public Fraccion[][] getNuevobAsterisco(){
        //Nuevo b* = Viejo S* x nuevo T.I.
        return Calculadora.multiplicar( tableauFinal.getSAsterisco(), nuevosTerminosIndependientes );
    }
    
    
    private boolean sonCompatiblesElTableauFinalYElNuevoTI(){
        
        return (
                //Comprobar si la cantidad de restricciones del tableau final es igual a la cantidad de T.I. nuevos
                tableauFinal.getCantidadRestricciones() == nuevosTerminosIndependientes.length
                );
    }
}
