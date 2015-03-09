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
package Optimizacion.MetodoDeLasDosFases;

import Optimizacion.Tableau;
import Optimizacion.Variable;


public class PasoMetodoDeLasDosFases {

    private int filaPivot;
    private int columnaPivot;
    private Tableau tableauAntesDeSimplificar;
    private Tableau tableauAntesDeAplicarGaussJordan;
    private Tableau tableauDespuesDeAplicarGaussJordan;
    private Variable nuevaVariableBasica;

    public void setPivot(int filaPivot, int columnaPivot) {
        this.filaPivot = filaPivot;
        this.columnaPivot = columnaPivot;
    }

    @SuppressWarnings("AssignmentToCollectionOrArrayFieldFromParameter")
    public void setTableauAntesDeSimplificar(Tableau _tableauAntesDeSimplificar) {
        this.tableauAntesDeSimplificar = _tableauAntesDeSimplificar.clone();
    }

    @SuppressWarnings("AssignmentToCollectionOrArrayFieldFromParameter")
    public void setTableauAntesDeAplicarGaussJordan(Tableau _tableauAntesDeAplicarGaussJordan) {
        this.tableauAntesDeAplicarGaussJordan = _tableauAntesDeAplicarGaussJordan.clone();
    }

    @SuppressWarnings("AssignmentToCollectionOrArrayFieldFromParameter")
    public void setTableauDespuesDeAplicarGaussJordan(Tableau _tableauDespuesDeAplicarGaussJordan) {
        this.tableauDespuesDeAplicarGaussJordan = _tableauDespuesDeAplicarGaussJordan.clone();
    }

    public void setNuevaVariableBasica(Variable _nuevaVariableBasica) {
        this.nuevaVariableBasica = _nuevaVariableBasica;
    }
    
    
    

    public int getFilaPivot() {
        return filaPivot;
    }

    public int getColumnaPivot() {
        return columnaPivot;
    }

    public Tableau getTableauAntesDeSimplificar() {
        return tableauAntesDeSimplificar;
    }

    public Tableau getTableauDespDeSimplificarYAntesGaussJordan() {
        return tableauAntesDeAplicarGaussJordan;
    }

    public Tableau getTableauDespuesDeAplicarGaussJordan() {
        return tableauDespuesDeAplicarGaussJordan;
    }

    public Variable getNuevaVariableBasica() {
        return nuevaVariableBasica;
    }
    
    
}
