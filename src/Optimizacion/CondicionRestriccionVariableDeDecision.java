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

package Optimizacion;

/**
 * Condición que indica que restricción se le aplica a la variable de decisión.
 * Por ejemplo, si X1>=0 (Es decir, es una variable no negativa) iría la condición
 * MAYOR_O_IGUAL acompañado del 0.
 * <br>
 * Una condición NO_RESTRINGIDA indica que la variable no está restringida (Puede tomar
 * cualquier valor).
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 */
public enum CondicionRestriccionVariableDeDecision {
    MAYOR_O_IGUAL, MENOR_O_IGUAL, NO_RESTRINGIDA
    
}
