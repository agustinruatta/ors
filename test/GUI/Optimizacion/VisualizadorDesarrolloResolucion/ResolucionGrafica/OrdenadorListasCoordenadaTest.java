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

import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 */
public class OrdenadorListasCoordenadaTest {
    
    

    @Test
    public void getListaOrdenadaEnSentidoAntiHorario1() {
        ArrayList<Coordenada> listaEnviada = new ArrayList<>();
        listaEnviada.add( new Coordenada(0, 2) );
        listaEnviada.add( new Coordenada(2, 8) );
        listaEnviada.add( new Coordenada(0, 5) );
        listaEnviada.add( new Coordenada(4, 8) );
        listaEnviada.add( new Coordenada(6, 7) );
        listaEnviada.add( new Coordenada(5, 0) );
        listaEnviada.add( new Coordenada(7, 5) );
        listaEnviada.add( new Coordenada(0.5, 0.5) );
        listaEnviada.add( new Coordenada(7, 2) );
        listaEnviada.add( new Coordenada(2, 0) );
        
        ArrayList<Coordenada> listaEsperada = new ArrayList<>();
        listaEsperada.add( new Coordenada(2, 0) );
        listaEsperada.add( new Coordenada(5, 0) );
        listaEsperada.add( new Coordenada(7, 2) );
        listaEsperada.add( new Coordenada(7, 5) );
        listaEsperada.add( new Coordenada(6, 7) );
        listaEsperada.add( new Coordenada(4, 8) );
        listaEsperada.add( new Coordenada(2, 8) );
        listaEsperada.add( new Coordenada(0, 5) );
        listaEsperada.add( new Coordenada(0, 2) );
        listaEsperada.add( new Coordenada(0.5, 0.5) );
        
        
        OrdenadorListasCoordenada ordenador = new OrdenadorListasCoordenada( listaEnviada );
        
        ArrayList<Coordenada> listaOrdenada = ordenador.getListaOrdenadaEnSentidoAntiHorario();
        
        Assert.assertEquals( listaEsperada, listaOrdenada );
    }
    
    
    
}
