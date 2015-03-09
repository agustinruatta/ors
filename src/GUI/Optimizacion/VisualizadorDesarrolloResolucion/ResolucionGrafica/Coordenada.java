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
package GUI.Optimizacion.VisualizadorDesarrolloResolucion.ResolucionGrafica;

/**
 * Clase usada para guardar una coordenada compuesta por
 * los valores en x e y.
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 */
public class Coordenada {
    
    private final double x;
    
    private final double y;

    public Coordenada(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if( objeto instanceof Coordenada ){
            Coordenada coordenada = (Coordenada) objeto;
            if(
                    this.x == coordenada.getX() &&
                    this.y == coordenada.getY()
                    ){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    @Override
    public String toString() {
        return "x = " + this.x + "\ty = " + this.y;
    }

//    /**
//     * Compara el objeto actual con el enviado como parámetro.
//     * La política de comparación es la siguiente:<br>
//     * Es menor el objeto que esté antes mirando las coordenadas
//     * en un giro en sentido anti-horario, empezando dicho giro
//     * por la coordenada más cerca del origen.
//     * @param coordenada
//     * @return 
//     */
//    @Override
//    public int compareTo(Coordenada coordenada) {
//        
//        if( this.y < coordenada.getY() ){
//            return -1;
//        }
//        else if( this.y > coordenada.getY() ){
//            return 1;
//        }
//        //this.y == coordenada.y
//        else{
//            if( this.x < coordenada.getX() ){
//                return -1;
//            }
//            else if( this.x > coordenada.getX() ){
//                return 1;
//            }
//            else{
//                return 0;
//            }
//        }
//        
//    }
    
    
    
    
}
