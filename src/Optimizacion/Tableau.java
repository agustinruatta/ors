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

import API.Calculadora;
import API.Fraccion;
import Optimizacion.Enumeraciones.TipoVariable;

/**
 * Representa a un Tableau, que es usado por el Método de las dos Fases.
 * <p>
 * El Tableau inicial está compuesto de la siguiente forma:<br>
 * --------------<br>
 * | -c | 0 | 0 |<br>
 * | A &nbsp| I | b |<br>
 * --------------<br>
 * <br>
 * 
 * El tableau final está compuesto de la siguiente forma:<br>
 * ------------------<br>
 * | z*-c | y* | Z* |<br>
 * | A* &nbsp&nbsp| S* | b* |<br>
 * ------------------<br>
 * </p>
 * <p>
 * Vale aclarar que a S* también se lo conoce como B^(-1).
 * </p>
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 */
public class Tableau implements Cloneable{
    
    public static final int NUMERO_FILA_COEFICIENTES_M = 0;
    
    public static final int NUMERO_FILA_FUNCION_OBJETIVO = 1;
    
    public static final int NUMERO_INICIO_FILA_RESTRICCIONES = 2;
    
    public static final int CANTIDAD_COLUMNAS_QUE_OCUPA_TERMINO_INDEPENDIENTE = 1;
    
    public static final int CANTIDAD_FILAS_QUE_OCUPA_FUNCION_OBJETIVO = 1;
    
    public static final int DESPLAZAMIENTO_DEL_TABLEAU_CON_RESPECTO_IGUALDADES = 2;
    
    public static final int DESPLAZAMIENTO_DEL_TABLEAU_CON_RESPECTO_A_RESTRICCIONES = 2;
    
    public static final int FILA_CERO = 0;
    
    
    public final int CANTIDAD_FILAS;
    public final int CANTIDAD_COLUMNAS;
    
    public final int NUMERO_COLUMNA_TI;
    
    /**
     * Estructura donde se guardan los coeficientes.
     * Se guardan de la forma Fraccion[filaTableau][columnaTableau].
     */
    private final Fraccion[][] tableau;
    
    private final int numeroColumnaDondeEmpiezanVariablesDeHolgura;
    private final int numeroColumnaDondeEmpiezanVariablesDeSuperavit;
    private final int numeroColumnaDondeEmpiezanVariablesArticiales;
    
    
    public Tableau( Fraccion[][] tableau, int _numeroColumnaDondeEmpiezanVariablesDeHolgura, int _numeroColumnaDondeEmpiezanVariablesDeSuperavit, int _numeroColumnaDondeEmpiezanVariablesArticiales ){
        
        this.tableau = tableau;
        
        this.numeroColumnaDondeEmpiezanVariablesDeHolgura = _numeroColumnaDondeEmpiezanVariablesDeHolgura;
        this.numeroColumnaDondeEmpiezanVariablesDeSuperavit = _numeroColumnaDondeEmpiezanVariablesDeSuperavit;
        this.numeroColumnaDondeEmpiezanVariablesArticiales = _numeroColumnaDondeEmpiezanVariablesArticiales;
        
        this.CANTIDAD_FILAS = tableau.length;
        this.CANTIDAD_COLUMNAS = tableau[ Tableau.FILA_CERO ].length;
        
        this.NUMERO_COLUMNA_TI = this.CANTIDAD_COLUMNAS - 1;
        
    }

    
    /**
     * Constructor.
     * Tableau inicializado con todos los coeficientes en cero.
     * @param cantidadFilas
     * @param cantidadColumnas
     * @param _numeroColumnaDondeEmpiezanVariablesDeHolgura
     * @param _numeroColumnaDondeEmpiezanVariablesDeSuperavit
     * @param _numeroColumnaDondeEmpiezanVariablesArticiales 
     */
    public Tableau( int cantidadFilas, int cantidadColumnas, int _numeroColumnaDondeEmpiezanVariablesDeHolgura, int _numeroColumnaDondeEmpiezanVariablesDeSuperavit, int _numeroColumnaDondeEmpiezanVariablesArticiales) {
        this.tableau = this.getMatrizConCoeficientesEnCero( cantidadFilas, cantidadColumnas );
        
        this.numeroColumnaDondeEmpiezanVariablesDeHolgura = _numeroColumnaDondeEmpiezanVariablesDeHolgura;
        this.numeroColumnaDondeEmpiezanVariablesDeSuperavit = _numeroColumnaDondeEmpiezanVariablesDeSuperavit;
        this.numeroColumnaDondeEmpiezanVariablesArticiales = _numeroColumnaDondeEmpiezanVariablesArticiales;
        
        this.CANTIDAD_FILAS = cantidadFilas;
        this.CANTIDAD_COLUMNAS = cantidadColumnas;
        
        this.NUMERO_COLUMNA_TI = this.CANTIDAD_COLUMNAS - 1;
    }
    
    
    
    
    
    public void setValor( int fila, int columna, Fraccion valor ){
        assert fila >= 0 && fila < this.CANTIDAD_FILAS;
        assert columna >= 0 && columna < this.CANTIDAD_COLUMNAS;
        assert valor != null;
        
        tableau[ fila ][ columna ] = valor;
    }
    
    public Fraccion getValor( int fila, int columna ){
        assert fila >= 0 && fila < this.CANTIDAD_FILAS;
        assert columna >= 0 && columna < this.CANTIDAD_COLUMNAS;
        
        return tableau[ fila ][ columna ];
    }
    
    
    public Fraccion[] getFila( int numeroFila ){
        assert numeroFila < this.tableau.length;
        
        return this.tableau[ numeroFila ];
    }
    
    
    
    
    
    /**
     * Simplifica la fila dividiendo a todos los coeficientes por
     * el coeficiente de la columna indicada (Sirve para obtener el pivot).
     * @param filaASimplificar Fila que se desea simplificar.
     * @param columnaPivot Columna que contiene el pivot.
     */
    public void simplificarFila( int filaASimplificar, int columnaPivot ){
        assert filaASimplificar >= 0 && filaASimplificar < this.CANTIDAD_FILAS;
        assert columnaPivot >= 0 && columnaPivot < this.CANTIDAD_COLUMNAS;
        
        Fraccion pivot = this.getValor( filaASimplificar, columnaPivot );
        
        for( int columna = 0; columna < this.CANTIDAD_COLUMNAS; columna++){
            this.setValor( filaASimplificar, columna, Calculadora.dividir( this.getValor( filaASimplificar, columna ) , pivot) );
        }
        
    }
    
    
    
    /**
     * Realiza la eliminación de Gauss Jordan, para tener ceros arriba y abajo
     * del pivot.
     * @param filaPivot Fila que contiene el pivot.
     * @param columnaPivot Columna que contiene el pivot.
     */
    public void realizarEliminacionGaussJordan( int filaPivot, int columnaPivot ){
        assert filaPivot >= 0 && filaPivot < this.CANTIDAD_FILAS;
        assert columnaPivot >= 0 && columnaPivot < this.CANTIDAD_COLUMNAS;
        
        //Recorrer todas las filas, excepto la del pivot.
        for( int filaActual = 0; filaActual < this.CANTIDAD_FILAS; filaActual++ ){
            
            
            //Si la fila actual es la misma que la pivot, no se debe operar.
            if( filaActual == filaPivot ){
                continue;
            }
                
            /*
            Coeficiente que está en la misma columna que el pivot, y que siempre se lo
            resta.
            */
            Fraccion coeficienteQueSeHaraCero = tableau[ filaActual ][ columnaPivot ];

            //Recorrer todas las columnas y cambiar sus coeficientes (Excepto la columna
            for( int columnaActual=0; columnaActual < this.CANTIDAD_COLUMNAS; columnaActual++ ){

                //Realizar el cálculo.
                tableau[ filaActual ][ columnaActual ] = Calculadora.restar( tableau[ filaActual ][ columnaActual ], Calculadora.multiplicar(coeficienteQueSeHaraCero, tableau[ filaPivot ][ columnaActual ]) );

            }
            
            
        }
    }
    
    
    
    
    /**
     * Devuelve la cantidad de variables de holgura, superavit y artificial
     * que tiene el Tableau.
     * @return 
     */
    private int getCantidadVariablesHolguraSuperavitArtificial(){
        return this.NUMERO_COLUMNA_TI - this.numeroColumnaDondeEmpiezanVariablesDeHolgura;
    }
    
    
    
    /**
     * Devuelve una matriz inicializada con todos los coeficientes en cero
     */
    private Fraccion[][] getMatrizConCoeficientesEnCero( int cantidadTotalFilas, int cantidadTotalColumnas ){
        
        //Crear Tableau
        Fraccion[][] matriz = new Fraccion[ cantidadTotalFilas ][ cantidadTotalColumnas ];
        
        //Inicializar en 0 todo el tableau
        for( int fila = 0; fila < cantidadTotalFilas; fila++ ){
            for( int columna = 0; columna< cantidadTotalColumnas; columna++ ){
                matriz[ fila ][ columna ] = Fraccion.CERO;
            }
        }
        
        return matriz;
    }
    
    
    
    /**
     * Devuelve z*-c.
     * @return 
     */
    @SuppressWarnings("ManualArrayToCollectionCopy")
    public Fraccion[][] getzAsteriscoMenosc(){
        Fraccion[][] zAsteriscoMenosc = new Fraccion[ Tableau.CANTIDAD_FILAS_QUE_OCUPA_FUNCION_OBJETIVO ][ this.numeroColumnaDondeEmpiezanVariablesDeHolgura ];
        
        for( int filaTableau = Tableau.NUMERO_FILA_FUNCION_OBJETIVO; filaTableau < Tableau.NUMERO_INICIO_FILA_RESTRICCIONES; filaTableau++ ){
            for( int columnaTableau = 0; columnaTableau < this.numeroColumnaDondeEmpiezanVariablesDeHolgura; columnaTableau++ ){
                zAsteriscoMenosc[ filaTableau - Tableau.NUMERO_FILA_FUNCION_OBJETIVO ][ columnaTableau ] = this.tableau[ filaTableau ][ columnaTableau ];
            }
        }
        
        return zAsteriscoMenosc;
    }
    
    
    /**
     * Devuelve y*.
     * @return 
     */
    public Fraccion[][] getyAsterisco(){
        Fraccion[][] yAsterisco = new Fraccion[ Tableau.CANTIDAD_FILAS_QUE_OCUPA_FUNCION_OBJETIVO ][ this.getCantidadVariablesHolguraSuperavitArtificial() ];
        
        for( int filaTableau = Tableau.NUMERO_FILA_FUNCION_OBJETIVO; filaTableau < Tableau.NUMERO_INICIO_FILA_RESTRICCIONES; filaTableau++ ){
            for( int columnaTableau = this.numeroColumnaDondeEmpiezanVariablesDeHolgura; columnaTableau < this.NUMERO_COLUMNA_TI; columnaTableau++ ){
                yAsterisco[ filaTableau - Tableau.NUMERO_FILA_FUNCION_OBJETIVO ][ columnaTableau - this.numeroColumnaDondeEmpiezanVariablesDeHolgura ] = this.tableau[ Tableau.NUMERO_FILA_FUNCION_OBJETIVO ][ columnaTableau ];
            }
        }
        
        return yAsterisco;
    }
    
    /**
     * Devuelve Z*.
     * @return 
     */
    public Fraccion[][] getZAsterisco(){
        Fraccion[][] ZAsterisco = { {this.tableau[ Tableau.NUMERO_FILA_FUNCION_OBJETIVO ][ this.NUMERO_COLUMNA_TI ]} };
        
        return ZAsterisco;
    }
    
    
    /**
     * Devuelve A*.
     * @return 
     */
    @SuppressWarnings("ManualArrayToCollectionCopy")
    public Fraccion[][] getAAsterisco(){
        
        Fraccion[][] AAsterisco = new Fraccion[ this.getCantidadRestricciones() ][ this.numeroColumnaDondeEmpiezanVariablesDeHolgura ];
        
        for( int filaTableau = Tableau.NUMERO_INICIO_FILA_RESTRICCIONES; filaTableau < this.CANTIDAD_FILAS; filaTableau++ ){
            for( int columnaTableau = 0; columnaTableau < this.numeroColumnaDondeEmpiezanVariablesDeHolgura; columnaTableau++ ){
                
                AAsterisco[ filaTableau - Tableau.NUMERO_INICIO_FILA_RESTRICCIONES ][ columnaTableau ] = this.tableau[ filaTableau ][ columnaTableau ];
                
            }
        }
        
        return AAsterisco;
    }
    
    
    /**
     * Devuelve S*.
     * S* también es conocido como B^(-1)
     * @return 
     */
    @SuppressWarnings("ManualArrayToCollectionCopy")
    public Fraccion[][] getSAsterisco(){
        Fraccion[][] SAsterisco = new Fraccion[ this.getCantidadRestricciones() ][ this.getCantidadVariablesHolguraSuperavitArtificial() ];
        
        for( int filaTableau = Tableau.NUMERO_INICIO_FILA_RESTRICCIONES; filaTableau < this.CANTIDAD_FILAS; filaTableau++ ){
            for( int columnaTableau = this.numeroColumnaDondeEmpiezanVariablesDeHolgura; columnaTableau < this.NUMERO_COLUMNA_TI; columnaTableau++ ){
                
                SAsterisco[ filaTableau - Tableau.NUMERO_INICIO_FILA_RESTRICCIONES ][ columnaTableau - this.numeroColumnaDondeEmpiezanVariablesDeHolgura ] = this.tableau[ filaTableau ][ columnaTableau ];
                
            }
        }
        
        return SAsterisco;
    }
    
    
    /**
     * Devuelve b*.
     * @return 
     */
    public Fraccion[][] getbAsterisco(){
        Fraccion[][] bAsterisco = new Fraccion[ this.getCantidadRestricciones() ][ Tableau.CANTIDAD_COLUMNAS_QUE_OCUPA_TERMINO_INDEPENDIENTE ];
        
        for( int filaTableau = Tableau.NUMERO_INICIO_FILA_RESTRICCIONES; filaTableau < this.CANTIDAD_FILAS; filaTableau++ ){
            
            bAsterisco[ filaTableau - Tableau.NUMERO_INICIO_FILA_RESTRICCIONES ][ 0 ] = this.tableau[ filaTableau ][ this.NUMERO_COLUMNA_TI ];
            
        }
        
        return bAsterisco;
    }
    
    
    
    public int getCantidadRestricciones(){
        return this.CANTIDAD_FILAS - 2;
    }
    
    
    
    /**
     * Dibuja en la consola el Tableau expresando los coeficientes
     * del mismo como fracciones.
     * Usado para hacer test o debug.
     */
    @SuppressWarnings("UseOfSystemOutOrSystemErr")
    public void dibujarTableauEnConsolaComoFraccion(){
        for( int i=0; i < this.CANTIDAD_FILAS; i++ ){
            for( int j=0; j < this.CANTIDAD_COLUMNAS; j++ ){
                System.out.printf(tableau[i][j].toString());
            }
            System.out.println();
        }
        
        System.out.println("\n\n");
    }
    
    
    /**
     * Dibuja en la consola el Tableau expresando los coeficientes
     * del mismo en números decimales.
     * Usado para hacer test o debug.
     */
    @SuppressWarnings("UseOfSystemOutOrSystemErr")
    public void dibujarTableauEnConsolaComoDecimales(){
        for( int i=0; i < this.CANTIDAD_FILAS; i++ ){
            for( int j=0; j < this.CANTIDAD_COLUMNAS; j++ ){
                System.out.printf("%s\t", tableau[i][j].toPlainString());
            }
            System.out.println();
        }
        
        System.out.println("\n\n");
    }

    public int getNumeroColumnaDondeEmpiezanVariablesDeHolgura() {
        return numeroColumnaDondeEmpiezanVariablesDeHolgura;
    }

    public int getNumeroColumnaDondeEmpiezanVariablesDeSuperavit() {
        return numeroColumnaDondeEmpiezanVariablesDeSuperavit;
    }

    public int getNumeroColumnaDondeEmpiezanVariablesArticiales() {
        return numeroColumnaDondeEmpiezanVariablesArticiales;
    }
    

    
    /**
     * Devuelve una representación matricial del Tableau.
     * @return 
     */
    @SuppressWarnings("ReturnOfCollectionOrArrayField")
    public Fraccion[][] getTableauEnMatriz() {
        return tableau;
    }
    
    
    /**
     * Devuelve la variable para el número de columna indicado en el parámetro.
     * @param numeroColumna
     * @return 
     */
    public Variable getVariable( int numeroColumna ){
        
        if( numeroColumna < this.numeroColumnaDondeEmpiezanVariablesDeHolgura ){
            return new Variable( TipoVariable.DECISION , numeroColumna + 1 );
        }
        else if( numeroColumna < this.numeroColumnaDondeEmpiezanVariablesDeSuperavit ){
            return new Variable( TipoVariable.HOLGURA , numeroColumna - this.numeroColumnaDondeEmpiezanVariablesDeHolgura + 1 );
        }
        else if( numeroColumna < this.numeroColumnaDondeEmpiezanVariablesArticiales ){
            return new Variable( TipoVariable.SUPERAVIT , numeroColumna - this.numeroColumnaDondeEmpiezanVariablesDeSuperavit + 1 );
        }
        else if( numeroColumna < this.NUMERO_COLUMNA_TI ){
            return new Variable( TipoVariable.ARTIFICIAL , numeroColumna - this.numeroColumnaDondeEmpiezanVariablesArticiales + 1 );
        }
        else{
            return new Variable( TipoVariable.TERMINO_INDEPENDIENTE , 1);
        }
    }
    
    
    /**
     * Clona el Tableau actual.
     * Es un tableau exactamente igual, aunque son 2 instancias diferentes.
     * @return 
     */
    @Override
    @SuppressWarnings({"CloneDeclaresCloneNotSupported", "CloneDoesntCallSuperClone"})
    public Tableau clone(){
        /*
            No se puede usar "return (Tableau) super.clone()" debido
            a que no clona los coeficientes de Fracción que tiene.
            Por lo tanto, hay q hacer el trabajo manualmente.
            */
            Fraccion[][] tableauClonado = new Fraccion[ this.CANTIDAD_FILAS ][ this.CANTIDAD_COLUMNAS ];

            for( int fila = 0; fila < this.CANTIDAD_FILAS; fila++ ){
                for( int columna = 0; columna < this.CANTIDAD_COLUMNAS; columna++ ){
                    tableauClonado[fila][columna] = this.getValor(fila, columna).clone();
                }
            }

            return new Tableau( tableauClonado, this.numeroColumnaDondeEmpiezanVariablesDeHolgura, this.numeroColumnaDondeEmpiezanVariablesDeSuperavit, this.numeroColumnaDondeEmpiezanVariablesArticiales );
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}
