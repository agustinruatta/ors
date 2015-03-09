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

import API.Fraccion;
import Optimizacion.Tableau;


public class IntroduccionNuevaVariableDecision {
    
    private final Tableau tableauFinal;
    private final Fraccion[][] coeficientesNuevaVariableDeDecision;

    public IntroduccionNuevaVariableDecision(Tableau tableauFinal, Fraccion[][] nuevasVariablesDeDecision) {
        this.tableauFinal = tableauFinal;
        this.coeficientesNuevaVariableDeDecision = nuevasVariablesDeDecision;
        
        if( !this.sonCompatiblesElTableauFinalYLaNuevaVariablesDeDecision() ){
            throw new IllegalArgumentException("Tableau final y nuevas variables de decisión incompatibles");
        }
    }
    
    
    
    private boolean sonCompatiblesElTableauFinalYLaNuevaVariablesDeDecision(){
        
        return (
                tableauFinal.getCantidadRestricciones() == coeficientesNuevaVariableDeDecision.length
                );
        
    }
    
}
