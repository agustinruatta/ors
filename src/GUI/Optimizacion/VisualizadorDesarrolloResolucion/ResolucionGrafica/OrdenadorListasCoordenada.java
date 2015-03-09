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
import java.util.Arrays;

/**
 * Ordena la lista de coordenadas en forma de sentido antihorario.
 * Antes de definir el algoritmo usado, definiremos una lista de ejemplo.
 * Ésta lista está compuesta por las siguientes coordenadas:
 * <p>
 * <table border="1">
 * <tr>
 * <th>Coordenada</th><th>X</th><th>Y</th>
 * </tr>
 * <tr>
 * <td>A</td><td>2</td><td>0</td>
 * </tr>
 * <tr>
 * <td>B</td><td>5</td><td>0</td>
 * </tr>
 * <tr>
 * <td>C</td><td>7</td><td>2</td>
 * </tr>
 * <tr>
 * <td>D</td><td>7</td><td>5</td>
 * </tr>
 * <tr>
 * <td>E</td><td>6</td><td>7</td>
 * </tr>
 * <tr>
 * <td>F</td><td>4</td><td>8</td>
 * </tr>
 * <tr>
 * <td>G</td><td>2</td><td>8</td>
 * </tr>
 * <tr>
 * <td>H</td><td>0</td><td>5</td>
 * </tr>
 * <tr>
 * <td>I</td><td>0</td><td>2</td>
 * </tr>
 * <tr>
 * <td>J</td><td>0.5</td><td>0.5</td>
 * </tr>
 * </table>
 * </p>
 * <p>En la siguiente explicación del algoritmo, punto y coordenada
 * tienen el mismo significado.</p>
 * <p>Los pasos del algoritmo son los siguientes:<p>
 * <ol>
 * <li>Se busca el punto que tenga el menor valor de y, y esté mas cerca
 * del origen (Es decir que tenga menor x). En la lista del ejemplo la coordenada
 * sería la A (2;0)
 * </li>
 * <li>
 * Se empieza a buscar los puntos que están a la derecha de A, en orden secuencial.<br>
 * Éstos puntos deben tener un valor de y menor o igual al punto más a la izquierda
 * y que tenga menor valor de y (Éste punto sería el C (7;2))<br>
 * Los puntos en la lista de ejemplo que está a la derecha de A son B y C.
 * </li>
 * <li>
 * Se empiezan a buscar los puntos que estén arriba de C, en orden secuencial.<br>
 * Éstos puntos tienen que tener el mismo valor de x que el punto que está
 * más a la derecha y que tenga menor valor de y (Es decir, el punto C está
 * más a la derecha teniendo el menor valor de y).<br>
 * Éstos puntos buscados tienen que tener el mismo valor de x que C.<br>
 * En este caso el punto que le sigue es el D.
 * </li>
 * <li>
 * Se empiezan a buscar los puntos que estén a la izquierda de D, en orden secuencial.<br>
 * Se empiezan a buscar los puntos con mayor x. No importa el valor de y,
 * debido a que los otros puntos que puedan tener mayor valor de x pero que están
 * debajo del punto más a la izquierda inferior (Es decir, los puntos A y B)
 * no se toman en cuenta ya que se ordenaron anteriormente. Es decir, esos puntos no
 * se comparan nuevamente<br>
 * En este caso, los puntos que se encuentran secuencialmente son E, F, G y H.
 * </li>
 * <li>
 * Una vez que se llego al punto limite izquierdo superior (El punto H)
 * se empieza a buscar los puntos que están abajo de éste. Es decir, los puntos
 * que tienen el mismo x, pero un y menor.<br>
 * En el ejemplo, éste punto es el I.<br>
 * </li>
 * <li>
 * Se recorre nuevamente para la derecha, buscando el/los puntos
 * que tengan menor x (Ya no importa el valor de y, debido a que son los
 * últimos puntos). En éste caso el punto que quedaría es el J (1;0).
 * </li>
 * <p>Ya se llegó al último punto, habiéndolos ordenado así en forma antihoraria.</p>
 * <p>Unas consideraciones a tener en cuenta</p>
 * <ul>
 * <li>
 * Las coordenadas del método gráfico siempre se ordenan de tal forma
 * que forman una figura parecida a un circulo o un cuadrado.
 * Es decir, van a la derecha, suben, van a la izquierda y bajan.
 * No tienen otra forma diferente, ya que las restricciones permiten
 * esta forma de dibujado.
 * </li>
 * <li>
 * Por lo dicho en un punto anterior, una linea que dibuja la región factible
 * no puede subir y después bajar. Es decir, no puede haber los siguientes 3 ptos:
 * (2;0), (3;1), (4;0).
 * </li>
 * </ul>
 * </ol>
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 */
public class OrdenadorListasCoordenada{
    
    private final Coordenada[] coordenadasOrdenadas;
    
    private final Coordenada coordenadaMasALaDerechaInferior;
    
    private final Coordenada coordenadaMasALaDerechaSuperior;
    
    private final Coordenada coordenadaMasALaIzquierdaSuperior;
    
    private final Coordenada coordenadaMasALaIzquierdaInferior;
    
    private boolean yaSeOrdeno;
    
    private boolean yaSeLlegoALaCoordenadaMasALaIzquierdaInferior;
    
    private DIRECCION_BUSQUEDA direccionActual;

    public OrdenadorListasCoordenada( ArrayList<Coordenada> listaCoordenadas ) {
        if( listaCoordenadas == null ){
            throw new IllegalArgumentException("Parámetro nulo");
        }
        if( listaCoordenadas.isEmpty() ){
            throw new IllegalArgumentException("La lista no tiene elementos");
        }
        
        this.yaSeOrdeno = false;
        
        this.coordenadasOrdenadas = new Coordenada[ listaCoordenadas.size() ];
        
        this.getListaConCoordendasIgualesQuitadas( listaCoordenadas ).toArray( this.coordenadasOrdenadas );
        
        this.coordenadaMasALaDerechaInferior = this.getCoordenadaMasALaDerechaInferior();
        
        this.coordenadaMasALaDerechaSuperior = this.getCoordenadaMasALaDerechaSuperior();
        
        this.coordenadaMasALaIzquierdaSuperior = this.getCoordenadaMasALaIzquierdaSuperior();
        
        this.coordenadaMasALaIzquierdaInferior = this.getCoordenadaMasALaIzquierdaInferior();
        
        this.yaSeLlegoALaCoordenadaMasALaIzquierdaInferior = false;
        
    }
    
    
    public ArrayList<Coordenada> getListaOrdenadaEnSentidoAntiHorario(){
        
        if( !this.yaSeOrdeno ){
            this.ordenarListaEnSentidoAntiHorario();
        }
        
        this.yaSeOrdeno = true;
        
        return new ArrayList<>( Arrays.asList( this.coordenadasOrdenadas ) );
    }
    
    
    
    private void ordenarListaEnSentidoAntiHorario(){
        
        this.establecerPrimeraACoordenadaMasCercaDelEjeXYElOrigen();
        
        int i = 1;
        
        this.direccionActual = DIRECCION_BUSQUEDA.DERECHA;
        
        while( i < this.coordenadasOrdenadas.length ){
            
            if( this.hayMasCoordenadasEnDirección(i) ){
                Coordenada siguienteCoordenada = this.getSiguienteCoordenada(i);
                
                this.intercambiarCoordenadas( siguienteCoordenada, this.coordenadasOrdenadas[i] );
                
                i++;
            }
            else{
                this.cambiarDireccion();
            }
            
        }
        
    }
    
    
    
    private void intercambiarCoordenadas( Coordenada coordenadaA, Coordenada coordenadaB ){
        //Si son iguales no se cambia
        if( coordenadaA == coordenadaB ){
            return;
        }
        
        int indiceCoordenadaA = -1;
        int indiceCoordenadaB = -1;
        
        for (int i = 0; i < this.coordenadasOrdenadas.length; i++) {
            //Estoy buscando que sea la misma referencia. Está bien que se compare con ==
            if( this.coordenadasOrdenadas[i] == coordenadaA ){
                indiceCoordenadaA = i;
            }
            else if( this.coordenadasOrdenadas[i] == coordenadaB ){
                indiceCoordenadaB = i;
            }
        }
        
        assert indiceCoordenadaA != -1;
        assert indiceCoordenadaB != -1;
        
        Coordenada temp = this.coordenadasOrdenadas[ indiceCoordenadaA ];
        this.coordenadasOrdenadas[ indiceCoordenadaA ] = this.coordenadasOrdenadas[ indiceCoordenadaB ];
        this.coordenadasOrdenadas[ indiceCoordenadaB ] = temp;
        
    }
    
    
    /**
     * Devuelve la coordenada que esté mas cerca del eje X.
     * Si hay más de una que están igual de cerca sobre el eje x,
     * devuelve la que mas cerca del origen esté.
     * @param coordenadas
     * @return 
     */
    private void establecerPrimeraACoordenadaMasCercaDelEjeXYElOrigen(){
        //Buscar el primer elemento
        Coordenada coordenadaMasCerca = null;
        
        for( Coordenada coordenada : this.coordenadasOrdenadas ){
            
            if( coordenadaMasCerca != null ){
                
                if( coordenada.getY() < coordenadaMasCerca.getY() ){
                    coordenadaMasCerca = coordenada;
                }
                else if(
                        ( coordenada.getY() == coordenadaMasCerca.getY() ) &&
                        ( coordenada.getX() < coordenadaMasCerca.getX() )
                        ){
                    coordenadaMasCerca = coordenada;
                }
                //Si tiene un y que es mayor, se pasa
            }
            else{
                coordenadaMasCerca = coordenada;
            }
        }
        
        this.intercambiarCoordenadas( this.coordenadasOrdenadas[0] , coordenadaMasCerca );
    }
    
    
    /**
     * Indica si hay más coordenadas para ordenar en la dirección actual.
     * @param indiceActual
     * @return 
     */
    private boolean hayMasCoordenadasEnDirección( int indiceActual ){
        
        /*
        Buscar en todas las coordenadas restantes, para ver si hay una que
        siga en esa dirección
        */
        for (int i = indiceActual; i < this.coordenadasOrdenadas.length; i++) {
            
            Coordenada coordenadaActual = this.coordenadasOrdenadas[i];

            /*
            Si se busca a la derecha, la coordenada actual debe:
            -Estar en la parte baja
            -Si no se paso el punto más a la izquierda inferior (Es decir,
            recien se está empezando a acomodar los puntos, se está llendo
            a la derecha y ascendiendo).:
                -Tener el x mayor queque el primer elemento (El punto A en el ejemplo)
            -Si ya lo paso
                -Nada
            */
            if( direccionActual == DIRECCION_BUSQUEDA.DERECHA ){
                if( this.estaEnLaParteBaja( coordenadaActual ) ){
                    if(
                            !this.yaSeLlegoALaCoordenadaMasALaIzquierdaInferior &&
                            coordenadaActual.getX() >= this.coordenadasOrdenadas[0].getX()
                            ){
                        return true;

                    }
                    else if(
                            this.yaSeLlegoALaCoordenadaMasALaIzquierdaInferior
                            ){
                        return true;

                    }
                }
            }
            /*
            Si se busca arriba, la coordenada actual debe:
            -Tener el x igual a la coordenada más a la
            derecha (inferior o superior, no importa).
            */
            else if( direccionActual == DIRECCION_BUSQUEDA.ARRIBA ){
                if( coordenadaActual.getX() == this.coordenadaMasALaDerechaInferior.getX() ){
                    return true;
                }
            }
            /*
            Si se busca a la izquierda, la coordenada debe:
            -Estar en la parte superior. (Ésto último se hace para
            que por ejemplo no se tome el punto J del ejemplo como válido).
            */
            else if( direccionActual == DIRECCION_BUSQUEDA.IZQUIERDA ){
                if( this.estaEnLaParteSuperior( coordenadaActual ) ){
                    return true;
                }
            }
            /*
            Si se busca abajo, la coordenada debe:
            -Tener que tener el x igual al punto más izquierdo (superior o
            inferior, no importa).
            -No hace falta ver que tenga el y mayor que el punto más izquierdo
            inferior
            */
            else if( direccionActual == DIRECCION_BUSQUEDA.ABAJO ){
                if( coordenadaActual.getX() == this.coordenadaMasALaIzquierdaSuperior.getX() ){
                    return true;
                }
            }
            
        }
        
        
        return false;
        
    }
    
    
    /**
     * Busca la siguiente coordenada que corresponde.
     * @param indiceActual Indice del vector que se está recorriendo
     * actualmente (Y que se va a intercambiar por la siguiente
     * coordenada que corresponda).
     * @return 
     */
    @SuppressWarnings("null")
    private Coordenada getSiguienteCoordenada( int indiceActual ){
        
        Coordenada siguienteCoordenada = null;
        
        for (int i = indiceActual; i < this.coordenadasOrdenadas.length; i++) {
            
            Coordenada coordenadaActual = this.coordenadasOrdenadas[i];

            /*
            Si se busca a la derecha, la siguiente coordenada va a ser:
            -Tiene que estar en la parte baja
            -Si no se paso el punto más a la izquierda inferior (Es decir,
            recien se está empezando a acomodar los puntos, se está llendo
            a la derecha y ascendiendo).:
                -La que tenga el x mas chico, siendo mayor o igual que el primer
                elemento (El punto A en el ejemplo)
            -Si ya lo paso
                -La que tenga el x más chico (No hace falta comparar que sea
                menor que el primer elemento).
            */
            if( 
                    ( direccionActual == DIRECCION_BUSQUEDA.DERECHA ) &&
                    ( this.estaEnLaParteBaja( coordenadaActual ) )
                    ){
                if( 
                        ( !this.yaSeLlegoALaCoordenadaMasALaIzquierdaInferior ) &&
                        ( coordenadaActual.getX() >= this.coordenadasOrdenadas[0].getX() )
                        ){
                    if( siguienteCoordenada == null ){
                        siguienteCoordenada = coordenadaActual;
                    }
                    else if( coordenadaActual.getX() < siguienteCoordenada.getX() ){
                        siguienteCoordenada = coordenadaActual;
                    }
                }
                else if( this.yaSeLlegoALaCoordenadaMasALaIzquierdaInferior ){
                    if( siguienteCoordenada == null ){
                        siguienteCoordenada = coordenadaActual;
                    }
                    else if( coordenadaActual.getX() < siguienteCoordenada.getX() ){
                        siguienteCoordenada = coordenadaActual;
                    }
                }
            }
            /*
            Si se busca arriba, va a ser:
            -La que tenga menor y.
            -Tiene que tener el x igual a la coordenada más a la
            derecha (inferior o superior, no importa).
            */
            else if(
                    (direccionActual == DIRECCION_BUSQUEDA.ARRIBA ) &&
                    ( coordenadaActual.getX() == this.coordenadaMasALaDerechaInferior.getX() )
                    ){
                if( siguienteCoordenada == null ){
                    siguienteCoordenada = coordenadaActual;
                }
                else if( coordenadaActual.getY() < siguienteCoordenada.getY() ){
                    siguienteCoordenada = coordenadaActual;
                }
            }
            /*
            Si se busca a la izquierda, la siguiente coordenada va a ser:
            -La que tenga mayor x.
            -Tiene que estar en la parte superior. (Ésto último se hace para
            que por ejemplo no se tome el punto J del ejemplo como válido).
            */
            else if(
                    ( direccionActual == DIRECCION_BUSQUEDA.IZQUIERDA ) &&
                    ( this.estaEnLaParteSuperior(coordenadaActual) )
                    ){
                if( siguienteCoordenada == null ){
                    siguienteCoordenada = coordenadaActual;
                }
                else if( coordenadaActual.getX() > siguienteCoordenada.getX() ){
                    siguienteCoordenada = coordenadaActual;
                }
            }
            /*
            Si se busca abajo la siguiente coordenada va a ser:
            -La que tenga y más alto
            -Tiene que tener el x igual al punto más izquierdo (superior o
            inferior, no importa).
            -No hace falta ver que tenga el y mayor que el punto más izquierdo
            inferior
            */
            else if(
                    ( direccionActual == DIRECCION_BUSQUEDA.ABAJO ) &&
                    ( coordenadaActual.getX() == this.coordenadaMasALaIzquierdaSuperior.getX() )
                    ){
                if( siguienteCoordenada == null ){
                    siguienteCoordenada = coordenadaActual;
                }
                else if( coordenadaActual.getY() > siguienteCoordenada.getY() ){
                    siguienteCoordenada = coordenadaActual;
                }
            }
            
        }
        
        assert siguienteCoordenada != null;
        
        if( siguienteCoordenada.equals( this.coordenadaMasALaIzquierdaInferior ) ){
            this.yaSeLlegoALaCoordenadaMasALaIzquierdaInferior = true;
        }
        
        return siguienteCoordenada;
    }
    
    private void cambiarDireccion(){
        
        if( this.direccionActual == DIRECCION_BUSQUEDA.DERECHA ){
            this.direccionActual = DIRECCION_BUSQUEDA.ARRIBA;
        }
        else if( this.direccionActual == DIRECCION_BUSQUEDA.ARRIBA ){
            this.direccionActual = DIRECCION_BUSQUEDA.IZQUIERDA;
        }
        else if( this.direccionActual == DIRECCION_BUSQUEDA.IZQUIERDA ){
            this.direccionActual = DIRECCION_BUSQUEDA.ABAJO;
        }
        else if( this.direccionActual == DIRECCION_BUSQUEDA.ABAJO ){
            this.direccionActual = DIRECCION_BUSQUEDA.DERECHA;
        }
        
    }
    
    
    /**
     * Devuelve la coordenada que esté mas a la izquierda (Es
     * decir, la coordenada que tenga el menor valor
     * de x) y más arriba (Es decir, con el mayor valor de y).
     * @return 
     */
    private Coordenada getCoordenadaMasALaIzquierdaSuperior(){
        
        assert this.coordenadasOrdenadas != null;
        
        Coordenada _coordenadaMasALaIzquierdaSuperior = null;
        
        for( Coordenada coordenada : this.coordenadasOrdenadas ){
            
            if( _coordenadaMasALaIzquierdaSuperior != null ){
                
                if( coordenada.getX() < _coordenadaMasALaIzquierdaSuperior.getX() ){
                    _coordenadaMasALaIzquierdaSuperior = coordenada;
                }
                else if(
                        ( coordenada.getX() == _coordenadaMasALaIzquierdaSuperior.getX() ) &&
                        ( coordenada.getY() > _coordenadaMasALaIzquierdaSuperior.getY() )
                        ){
                    _coordenadaMasALaIzquierdaSuperior = coordenada;
                }
            }
            //Es la primer coordenada que se evalúa
            else{
                _coordenadaMasALaIzquierdaSuperior = coordenada;
            }
        }
        
        assert _coordenadaMasALaIzquierdaSuperior != null;
        
        return _coordenadaMasALaIzquierdaSuperior;
    }
    
    /**
     * Devuelve la coordenada que esté mas a la izquierda (Es
     * decir, la coordenada que tenga el menor valor
     * de x) y más abajo (Es decir, con el menor valor de y).
     * @return 
     */
    private Coordenada getCoordenadaMasALaIzquierdaInferior(){
        
        assert this.coordenadasOrdenadas != null;
        
        Coordenada _coordenadaMasALaIzquierdaInferior = null;
        
        for( Coordenada coordenada : this.coordenadasOrdenadas ){
            
            if( _coordenadaMasALaIzquierdaInferior != null ){
                
                if( coordenada.getX() < _coordenadaMasALaIzquierdaInferior.getX() ){
                    _coordenadaMasALaIzquierdaInferior = coordenada;
                }
                else if(
                        ( coordenada.getX() == _coordenadaMasALaIzquierdaInferior.getX() ) &&
                        ( coordenada.getY() < _coordenadaMasALaIzquierdaInferior.getY() )
                        ){
                    _coordenadaMasALaIzquierdaInferior = coordenada;
                }
            }
            //Es la primer coordenada que se evalúa
            else{
                _coordenadaMasALaIzquierdaInferior = coordenada;
            }
        }
        
        assert _coordenadaMasALaIzquierdaInferior != null;
        
        return _coordenadaMasALaIzquierdaInferior;
    }
    
    
    /**
     * Devuelve la coordenada que esté mas a la derecha (Es
     * decir, la coordenada que tenga el mayor valor
     * de x) y mas abajo (Es decir, que tenga menor valor de y).
     * @return 
     */
    private Coordenada getCoordenadaMasALaDerechaInferior(){
        
        Coordenada _coordenadaMasALaDerechaInferior = null;
        
        for( Coordenada coordenada : this.coordenadasOrdenadas ){
            
            if( _coordenadaMasALaDerechaInferior != null ){
                
                if( coordenada.getX() > _coordenadaMasALaDerechaInferior.getX() ){
                    _coordenadaMasALaDerechaInferior = coordenada;
                }
                else if(
                        ( coordenada.getX() == _coordenadaMasALaDerechaInferior.getX() ) &&
                        ( coordenada.getY() < _coordenadaMasALaDerechaInferior.getY() )
                        ){
                    _coordenadaMasALaDerechaInferior = coordenada;
                }
            }
            //Es la primer coordenada que se evalúa
            else{
                _coordenadaMasALaDerechaInferior = coordenada;
            }
        }
        
        assert _coordenadaMasALaDerechaInferior != null;
        
        return _coordenadaMasALaDerechaInferior;
    }
    
    /**
     * Devuelve la coordenada que esté mas a la derecha (Es
     * decir, la coordenada que tenga el mayor valor
     * de x) y mas arriba (Es decir, que tenga mayor valor de y).
     * @return 
     */
    private Coordenada getCoordenadaMasALaDerechaSuperior(){
        
        Coordenada _coordenadaMasALaDerechaSuperior = null;
        
        for( Coordenada coordenada : this.coordenadasOrdenadas ){
            
            if( _coordenadaMasALaDerechaSuperior != null ){
                
                if( coordenada.getX() > _coordenadaMasALaDerechaSuperior.getX() ){
                    _coordenadaMasALaDerechaSuperior = coordenada;
                }
                else if(
                        ( coordenada.getX() == _coordenadaMasALaDerechaSuperior.getX() ) &&
                        ( coordenada.getY() > _coordenadaMasALaDerechaSuperior.getY() )
                        ){
                    _coordenadaMasALaDerechaSuperior = coordenada;
                }
            }
            //Es la primer coordenada que se evalúa
            else{
                _coordenadaMasALaDerechaSuperior = coordenada;
            }
        }
        
        assert _coordenadaMasALaDerechaSuperior != null;
        
        return _coordenadaMasALaDerechaSuperior;
    }
    
    
    /**
     * Si hay 2 o + coordenadas iguales (Es decir, que tenga iguales valores
     * de x e y), deja una sola.
     * Ésto se hace debido a que no tiene sentido tener la misma coordenada
     * más de una vez y para evitar problemas de ordenamiento que puedan ocurrir.
     * @param listaCoordenadas 
     */
    private ArrayList<Coordenada> getListaConCoordendasIgualesQuitadas( ArrayList<Coordenada> listaCoordenadas ){
        
        for (int i = 0; i < listaCoordenadas.size(); i++) {
            
            for (int j = i+1; j < listaCoordenadas.size(); j++) {
                
                if( listaCoordenadas.get(i).equals( listaCoordenadas.get(j) ) ){
                    listaCoordenadas.remove(j);
                }
                
            }
            
        }
        
        return listaCoordenadas;
        
    }
    
    
    /**
     * Indica si la coordenada está en la parte inferior
     * de la figura.
     * Ésto se hace trazando una linea entre el punto más izquierdo
     * inferior y el punto más derecho inferior. Si toca o está arriba
     * de esa línea, entonces esta en la parte superior.
     * @param coordenada
     * @return 
     */
    private boolean estaEnLaParteBaja( Coordenada  coordenada ){
        
        return coordenada.getY() <= this.getValorFuncionLineal(coordenada.getX(), this.coordenadaMasALaIzquierdaInferior.getX(), this.coordenadaMasALaIzquierdaInferior.getY(), this.coordenadaMasALaDerechaInferior.getX(), this.coordenadaMasALaDerechaInferior.getY() );
        
    }
    
    /**
     * Indica si la coordenada está en la parte superior
     * de la figura.
     * Ésto se hace trazando una linea entre el punto más izquierdo
     * superior y el punto más derecho superior. Si toca o está arriba
     * de esa línea, entonces esta en la parte superior.
     * @param coordenada
     * @return 
     */
    private boolean estaEnLaParteSuperior( Coordenada coordenada ){
        
        return coordenada.getY() >= this.getValorFuncionLineal(coordenada.getX(), this.coordenadaMasALaIzquierdaSuperior.getX(), this.coordenadaMasALaIzquierdaSuperior.getY(), this.coordenadaMasALaDerechaSuperior.getX(), this.coordenadaMasALaDerechaSuperior.getY() );
        
    }
    
    /**
     * Devuelve el valor en x de la función lineal compuesta por
     * x0, y0, x1 e y1.
     * @param x
     * @param x0
     * @param y0
     * @param x1
     * @param y1
     * @return 
     */
    private double getValorFuncionLineal( double x, double x0, double y0, double x1, double y1){
        
        /*
        Sabemos que:
        y-y0 = ( (y1-y0)/(x1-x0) )*(x-x0)
        
        Por lo tanto:
        y = ( (y1-y0)/(x1-x0) )*(x-x0) + y0
        */
        
        return ( (y1-y0)/(x1-x0) )*(x-x0) + y0;
        
    }

    private enum DIRECCION_BUSQUEDA {
        DERECHA, ARRIBA, IZQUIERDA, ABAJO
    }
}
