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

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 */
public class CoordenadaTest {
    
    public CoordenadaTest() {
    }

    @Test
    public void equals1() {
        Coordenada coordenada1 = new Coordenada(1, 2);
        Coordenada coordenada2 = new Coordenada(1, 2);
        
        Assert.assertEquals( coordenada1.equals( coordenada2 ), true );
    }
    
    @Test
    public void equals2() {
        Coordenada coordenada1 = new Coordenada(1, 2);
        Coordenada coordenada2 = new Coordenada(1, 3);
        
        Assert.assertEquals( coordenada1.equals( coordenada2 ), false );
    }
    
}
