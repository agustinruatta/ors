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

import API.Calculadora;
import API.Fraccion;
import Excepciones.DemasiadasIteracionesMetodoDosFasesException;
import Excepciones.NoSeEncontroNingunaFilaException;
import Excepciones.SinSolucionesFactiblesException;
import Excepciones.ZNoAcotadaException;
import Optimizacion.DatosOptimizacion;
import Optimizacion.Enumeraciones.Igualdades;
import Optimizacion.Enumeraciones.Objetivo;
import Optimizacion.Enumeraciones.TipoVariable;
import Optimizacion.Tableau;
import Optimizacion.Variable;
import java.util.ArrayList;



public class MetodoDeLasDosFases {
    /**
     * Cantidad máxima de iteraciones que puede realizar el Método de las 2 Fases.
     */
    public static final int CANTIDAD_MAXIMA_ITERACIONES = 100000;
    
    private final ArrayList<PasoMetodoDeLasDosFases> pasosDeUnaFaseMetodoDeLasDosFaseses;
    
    
    /**
     * Contiene los valores de las variables de decisión una vez
     * optimizadas.
     */
    private final Fraccion[] soluciones;
    
    
    /**
     * Objetivo que se desea obtener de la optimización (Minimizar o maximizar).
     * No se puede cambiar debido a que el objetivo va a ser siempre el mismos
     */
    private final Objetivo objetivoDeLaOptimizacion;
    
    
    /**
     * Los tipos de igualdades que tienen las ecuaciones.
     */
    private final Igualdades[] igualdadesRestricciones;
    
    private final DatosOptimizacion datosMetodoDeLasDosFases;
    
    
    /**
     * Guarda la variables básica de cada fila.
     * Se incluyen la Variable de las Fila M, la de la Función Objetivo y
     * la de todas las restricciones. Es decir, que tiene una longitud igual
     * a la cantidad de filas del tableau.
     */
    private final Variable[] variablesBasicas;
    
    
    /**
     * Guarda el tableau que se genera para solucionar el problema.
     * En ésta matriz es en donde se van a realizar los cálculos.
     * INCLUYE EL TI.
     */
    private final Tableau tableau;
    
    /**
     * Indica si ya está resuelto el tableau.
     */
    private boolean yaSeResolvio = false;
    
    private final int numeroColumnaDondeEmpiezanVariablesDeHolgura;
    
    private final int numeroColumnaDondeEmpiezanVariablesDeSuperavit;
    
    private final int numeroColumnaDondeEmpiezanVariablesArticiales;
    
    
    
    /**
     * Usado para controlar la cantidad de iteraciones que realiza el método 
     * de las 2 fases.
     * Si se supera más iteraciones que la definida como máximo, se lanza una
     * excepción indicando que no se pudo resolver.
     */
    private int numeroIteracion = 0;

    
    
    /**
     * Constructor.
     * @param datosIngresados
     * @param seDebeResolver Indica si se debe resolver el problema
     * de optimización.
     * @throws ZNoAcotadaException
     * @throws DemasiadasIteracionesMetodoDosFasesException
     * @throws SinSolucionesFactiblesException 
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public MetodoDeLasDosFases( DatosOptimizacion datosIngresados, boolean seDebeResolver ) throws ZNoAcotadaException, DemasiadasIteracionesMetodoDosFasesException, SinSolucionesFactiblesException{
        this(datosIngresados);
        
        if( seDebeResolver ){
            this.resolver();
        }
    }
    /**
     * Constructor.
     * No resuelve el problema. Para ello se debe llamar a resolver()
     * @param datosIngresados
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public MetodoDeLasDosFases( DatosOptimizacion datosIngresados ){
        
        //Guardar los parámetros en los atributos de la clase.
        this.objetivoDeLaOptimizacion = datosIngresados.getObjetivoDeLaOptimizacion();
        this.igualdadesRestricciones = datosIngresados.getIgualdades();
        this.datosMetodoDeLasDosFases = datosIngresados;
        
        //Crear una lista para guardar los pasos del método que se usarán posteriormente para visualizarlos
        this.pasosDeUnaFaseMetodoDeLasDosFaseses = new ArrayList<>();
        
        
        
        //La cantidad de variables de holgura es igual a la cantidad de restricciones con menor o igual
        int cantidadVariablesDeHolgura = getCantidadRestricciones( Igualdades.MENOR_O_IGUAL );
        
        //La cantidad de variables de superavit es igual a la cantidad de restricciones con mayor o igual
        int cantidadVariablesSuperavit = getCantidadRestricciones( Igualdades.MAYOR_O_IGUAL );
        
        //La cantidad de variables de holgura es igual a la cantidad de restricciones con igual o mayor o igual 
        int cantidadVariablesArtificiales = getCantidadRestricciones( Igualdades.IGUAL ) + getCantidadRestricciones( Igualdades.MAYOR_O_IGUAL );
        
        
        int cantidadVariablesDeDecision = datosMetodoDeLasDosFases.getCantidadVariablesDeDecision();
        
        /*
        La cantidad total de filas es igual a la cantidad de filas de las restricciones más 2
        (Hay que sumar la fila de las M y la función objetivo).
        */
        int cantidadTotalFilasTableau = datosIngresados.getCantidadFilasRestricciones() + 2;
        
        /*
         * La cantidad de columnas es igual a la cantidad de variables de
         * decision más la de holgura, superavit y artificiales.
         */
        int cantidadTotalColumnasTableau = cantidadVariablesDeDecision + cantidadVariablesDeHolgura + cantidadVariablesSuperavit + cantidadVariablesArtificiales + Tableau.CANTIDAD_COLUMNAS_QUE_OCUPA_TERMINO_INDEPENDIENTE;
        
        //Calcular el número donde comienzan las variables de holgura, superavit y artificial
        this.numeroColumnaDondeEmpiezanVariablesDeHolgura = cantidadVariablesDeDecision;
        this.numeroColumnaDondeEmpiezanVariablesDeSuperavit = cantidadVariablesDeDecision + cantidadVariablesDeHolgura;
        this.numeroColumnaDondeEmpiezanVariablesArticiales = cantidadVariablesDeDecision + cantidadVariablesDeHolgura + cantidadVariablesSuperavit;
        
        
        //Inicializar las variables básicas.
        variablesBasicas = new Variable[ cantidadTotalFilasTableau ];
        /*
        Se inicializa con tipo de variable M y Zeta (Aunque no se use nunca éstas variables básicas.
        Se hace para que quede correcto (Y para evitar el desplazamiento).
        */
        variablesBasicas[ Tableau.NUMERO_FILA_COEFICIENTES_M ] = Variable.M;
        variablesBasicas[ Tableau.NUMERO_FILA_FUNCION_OBJETIVO ] = Variable.ZETA;
        
        
        
        /*
        La cantidad de soluciones obtenidas va a ser igual a la cantidad de
        variables de decisión.
        */
        soluciones = new Fraccion[ cantidadVariablesDeDecision ];
        
        //Crear el tableau
        this.tableau = new Tableau(cantidadTotalFilasTableau, cantidadTotalColumnasTableau, numeroColumnaDondeEmpiezanVariablesDeHolgura, numeroColumnaDondeEmpiezanVariablesDeSuperavit, numeroColumnaDondeEmpiezanVariablesArticiales);
        
        //Generar el tableau aumentado
        this.generarTableauAumentado();
        
    }

    public void resolver() throws ZNoAcotadaException, DemasiadasIteracionesMetodoDosFasesException, SinSolucionesFactiblesException{
        
        if( !yaSeResolvio ){
            /*
            Realizar primera fase (Trabajar en la fila de las M).
            Se itera hasta que ninguna variable artificial sea básica
            */
            while ( hayAlgunaVariableBasicaDelTipo( TipoVariable.ARTIFICIAL ) ) {
                
                if( !sigueTeniendoSolucionesFactibles() ){
                    throw new SinSolucionesFactiblesException();
                }
                
                realizarFase( Tableau.NUMERO_FILA_COEFICIENTES_M );

            }

            /*
            Realizar la segunda fase (Trabajar en la fila de la función objetivo).
            */
            while( hayAlgunCoeficienteDeVariablesNoArtificialesNegativoEnFila( Tableau.NUMERO_FILA_FUNCION_OBJETIVO ) ){
                
                realizarFase( Tableau.NUMERO_FILA_FUNCION_OBJETIVO );

            }
            
            guardarSolucionesOptimasEncontradas();
            
            yaSeResolvio = true;
        }
        
    }
    
    
    /**
     * Guarda las soluciones óptimas encontradas.
     */
    private void guardarSolucionesOptimasEncontradas(){
        
        //Dejar todos los valores de las variables de decisión en 0.
        for( int numeroSolucion = 0; numeroSolucion < datosMetodoDeLasDosFases.getCantidadVariablesDeDecision(); numeroSolucion++ ){
            soluciones[ numeroSolucion ] = Fraccion.CERO;
        }
        
        
        //Guardar los valores de las variables de decisión y que son básicas.
        for( int fila = Tableau.NUMERO_INICIO_FILA_RESTRICCIONES; fila < tableau.CANTIDAD_FILAS; fila++ ){
            
            Variable variableBasicaDeEsaFila = variablesBasicas[ fila ];
            
            if( variableBasicaDeEsaFila.getTipoVariable() == TipoVariable.DECISION ){
                
                soluciones[ variableBasicaDeEsaFila.getNumeroVariable() - 1 ] = tableau.getValor( fila, tableau.NUMERO_COLUMNA_TI );
                
            }
        }
        
    }
    
    
    /**
     * Realiza la fase.
     * El parámetro indica con que fila se está trabajando (Es decir,
     * en que fase esta: fase 1 (Fila 0) o fase 2 (Fila 1)
     * @param filaATrabajar Fila que se está trabajando.
     */
    private void realizarFase( int filaATrabajar ) throws ZNoAcotadaException, DemasiadasIteracionesMetodoDosFasesException{
        
        if( numeroIteracion > CANTIDAD_MAXIMA_ITERACIONES ){
            throw new DemasiadasIteracionesMetodoDosFasesException();
        }
        numeroIteracion++;
        
        
        /**
         * Comentarios de línea que comienzen con "**" son comentarios acerca de
         * los pasos del método que se guardan. Si no comienza con nada, son comentarios
         * con respecto al algoritmo del método.
         */
        
        //**Crear objeto para guardar el paso de ésta fase
        PasoMetodoDeLasDosFases pasoDeMetodoDeLasDosFases = new PasoMetodoDeLasDosFases();
        
        //**Guardar el tableau antes de simplificar
        pasoDeMetodoDeLasDosFases.setTableauAntesDeSimplificar( new Tableau( tableau.getTableauEnMatriz() , this.numeroColumnaDondeEmpiezanVariablesDeHolgura, this.numeroColumnaDondeEmpiezanVariablesDeSuperavit, this.numeroColumnaDondeEmpiezanVariablesArticiales ) );
        
        //Buscar la columna pivot (Columna con el coeficiente M más negativo).
        int columnaPivot = getColumnaDeVariablesNoArtificialesConCoeficienteMasNegativo( filaATrabajar );
        
        
        //Comprobar que Z esté acotada
        if( !ZEstaAcotada(columnaPivot) ){
            throw new ZNoAcotadaException();
        }
        
        
        //Buscar la fila Pivot (Fila con el cociente mínimo).
        int filaPivot = getFilaConCocienteMinimo( columnaPivot );
        
        
        
        //**Guardar el pivot seleccionado
        pasoDeMetodoDeLasDosFases.setPivot(filaPivot, columnaPivot);
        
        //Dividir toda la filaConResultadoMasChico para dejar al pivot en 1
        tableau.simplificarFila( filaPivot, columnaPivot);
        
        //**Guardar el tableau simplificado, sin haberle aplicado Gauss-Jordan
        pasoDeMetodoDeLasDosFases.setTableauAntesDeAplicarGaussJordan( new Tableau( tableau.getTableauEnMatriz() , this.numeroColumnaDondeEmpiezanVariablesDeHolgura, this.numeroColumnaDondeEmpiezanVariablesDeSuperavit, this.numeroColumnaDondeEmpiezanVariablesArticiales ) );
        
        //Realizar los pasos de Gauss-Jordan
        tableau.realizarEliminacionGaussJordan( filaPivot, columnaPivot );
        
        //**Guardar el tablea después de haberle aplicadoi Gauss-Jordan
        pasoDeMetodoDeLasDosFases.setTableauDespuesDeAplicarGaussJordan( new Tableau( tableau.getTableauEnMatriz() , this.numeroColumnaDondeEmpiezanVariablesDeHolgura, this.numeroColumnaDondeEmpiezanVariablesDeSuperavit, this.numeroColumnaDondeEmpiezanVariablesArticiales ) );
        
        //Cambiar la variable básica de la fila.
        Variable nuevaVariableBasica = getVariableRepresentada( columnaPivot );
        variablesBasicas[ filaPivot ] = nuevaVariableBasica;
        
        //**Guardar la nueva variable básica
        pasoDeMetodoDeLasDosFases.setNuevaVariableBasica(nuevaVariableBasica);
        
        
        guardarPasoResolucionTableau(pasoDeMetodoDeLasDosFases);
        
    }
    
    
    
    
    
    
    
    /**
     * Indica si existe algún coeficiente de las variables no artificiales
     * que sea negativo en la fila indicada en el parámetro (No se incluye
     * el Término Independiente ni las columnas que representan variables
     * artificiales).
     * @return true si hay alguno, false caso contrario.
     */
    private boolean hayAlgunCoeficienteDeVariablesNoArtificialesNegativoEnFila( int fila ){
        
        //Recorrer la fila y verificar si hay algún negativo.
        for( int columna = 0; columna < tableau.NUMERO_COLUMNA_TI; columna++ ){
            
            if( getVariableRepresentada( columna ).getTipoVariable().equals( TipoVariable.ARTIFICIAL )  ){
                continue;
            }
            
            if( tableau.getValor( fila, columna ).esFraccionNegativa()){
                return true;
            }
        }
        
        return false;
    }
    
    
    /**
     * Devuelve la columna de las variables no artificiales con el
     * coeficiente más negativo de toda la fila indicada en el parámetro
     * (No se controla el Término Independiente ni las variables artificiales).
     * @param fila Fila donde se desea saber la columna más negativa.
     * @return Columna con el coeficiente más negativo de la fila
     * indicada en el parámetro.
     */
    private int getColumnaDeVariablesNoArtificialesConCoeficienteMasNegativo( int fila ){
        int columnaMasNegativa = 0;
        
        Fraccion coeficienteMasNegativo = Fraccion.UNO;
        
        //Se supone que hay algún coeficiente negativo.
        //Recorrer la fila y ver cual es la columna con el coeficiente más negativo.
        for( int columna = 0; columna < tableau.NUMERO_COLUMNA_TI; columna++ ){
            
            //Si es una variable artificial, no se controla.
            if( getVariableRepresentada( columna ).getTipoVariable() == TipoVariable.ARTIFICIAL ){
                continue;
            }
            
            Fraccion coeficiente = tableau.getValor( fila, columna );
            
            if( coeficiente.esMenorQue( coeficienteMasNegativo ) ){
                coeficienteMasNegativo = coeficiente;
                columnaMasNegativa = columna;
            }
        }
        
        return columnaMasNegativa;
    }

    
    /**
     * Devuelve la fila con el cociente mínimo.
     * @param columna Columna donde se encuentran los coeficientes
     * de la variable básica entrante.
     * @return Fila con el cociente mínimo.
     */
    private int getFilaConCocienteMinimo( int columna ) {
        
        int filaConResultadoMasChico = -1;
        Fraccion cocienteMasPequenio = null;
        boolean esLaPrimeraDivision = true;
        
        for( int fila = Tableau.NUMERO_INICIO_FILA_RESTRICCIONES; fila < tableau.CANTIDAD_FILAS; fila++ ){
            
            Fraccion terminoIndependiente = tableau.getValor( fila, tableau.NUMERO_COLUMNA_TI );
            Fraccion coeficienteVariableBasicaEntrante = tableau.getValor( fila, columna );
            
            //Si el divisor es 0, saltar la iteración (No se puede dividir por 0).
            if( coeficienteVariableBasicaEntrante.equals( Fraccion.CERO ) ){
                continue;
            }
            
            //Realizar la división.
            Fraccion resultadoDivision = Calculadora.dividir( terminoIndependiente, coeficienteVariableBasicaEntrante);
            
            //Si la división es negativa, se salta la iteración
            if( resultadoDivision.esFraccionNegativa() ){
                continue;
            }
                
            //No es la primera División que se hace
            if( !esLaPrimeraDivision ){

                /*
                Si la división es menor que la división mas chica anterior, 
                entonces ésta fila contiene hasta ahora el cociente mínimo.
                */
                if( resultadoDivision.esMenorQue( cocienteMasPequenio ) ){
                    filaConResultadoMasChico = fila;
                    cocienteMasPequenio = resultadoDivision;
                }
            }
            //Si es la primera fila que se encuentra, va a ser el coeficiente mas chico.
            else{
                filaConResultadoMasChico = fila;
                cocienteMasPequenio = resultadoDivision;
                esLaPrimeraDivision = false;
            }
            
            
            
        }
        
        
        if( filaConResultadoMasChico == -1 || cocienteMasPequenio == null ){
            throw new NoSeEncontroNingunaFilaException();
        }
        
        return filaConResultadoMasChico;
    }
    
    
    /**
     * Coloca los coeficientes correspondientes en el tableau.
     */
    private void generarTableauAumentado() {
        /*
        Debe mantener éste orden (Primero fila función objetivo, después restricciones y por
        último M), debido a que, para calcular los valores de la fila de las M, es necesario
        realizar calculos con los otros 2 tipos de fila (Objetivo y restricciones).
        */
        
        //Fila función objetivo.
        colocarCoeficientesEnFilaFuncionObjetivo();
        
        
        //Filas restricciones.
        colocarCoeficientesEnFilasRestricciones();
        
        
        //Fila de los coeficientes M.
        colocarCoeficientesEnFilaM();
    }
    
    
    
    
    /**
     * Rellena la filaConResultadoMasChico de la función objetivo con los coeficientes correspondientes.
     */
    private void colocarCoeficientesEnFilaFuncionObjetivo(){
        
        //Indicar la variable básica de la columna.
        variablesBasicas[ Tableau.NUMERO_FILA_FUNCION_OBJETIVO ] = Variable.ZETA;
        
        /**
         * Rellenar los datos con los coeficientes de la función objetivo.
         * Recorrer hasta una posición menos debido a que la última columna
         * debe ir el término independiente.
         */
        for( int columna = 0; columna < datosMetodoDeLasDosFases.getCantidadVariablesDeDecision(); columna++ ){
            Fraccion coeficienteDeFuncionObjetivo = datosMetodoDeLasDosFases.getFuncionObjetivo()[ columna ];
            
            tableau.setValor( Tableau.NUMERO_FILA_FUNCION_OBJETIVO , columna, coeficienteDeFuncionObjetivo);
        }
        
        
        //Indicar que el término independiente de la función objetivo es 0.
        tableau.setValor( Tableau.NUMERO_FILA_FUNCION_OBJETIVO, tableau.NUMERO_COLUMNA_TI, Fraccion.CERO );
        
        /*
         * Si el objetivo es maximizar, invertir los coeficientes ingresados.
         * Si el objetivo es minimizar, los coeficientes ingresados se mantienen iguales.
         */
        if( objetivoDeLaOptimizacion == Objetivo.MAXIMIZAR ){
            invertirSignoFilaObjetivoTableau();
        }

        
    }
    
    /**
     * Rellena la filaConResultadoMasChico de las restricciones con los coeficientes correspondientes.
     */
    private void colocarCoeficientesEnFilasRestricciones(){
        
        int cantidadDeVariablesDeHolguraCreadasAnteriormente = 0;
        int cantidadDeVariablesDeSuperavitCreadasAnteriormente = 0;
        int cantidadDeVariablesArtificialesCreadasAnteriormente = 0;
        
        /**
         * *****Rellenar las diferentes filas y columnas con los datos proporcionados y las variables generadas.
         */
        for( int filaTableau = Tableau.NUMERO_INICIO_FILA_RESTRICCIONES; filaTableau < tableau.CANTIDAD_FILAS; filaTableau++){
            
            //Rellenar las columnas con los coeficientes de las variablesBasicas de decisión
            //Recorrer una menos debido a que no hay que completar todavía el T.I.
            for( int columna = 0; columna < datosMetodoDeLasDosFases.getCantidadVariablesDeDecision(); columna++ ){
                //Se desplaza debido a que el tableau tiene 2 filas al principio (M y Z) que no lo tiene las restricciones
                Fraccion coeficienteDeRestriccion = datosMetodoDeLasDosFases.getRestricciones()[ filaTableau - Tableau.DESPLAZAMIENTO_DEL_TABLEAU_CON_RESPECTO_A_RESTRICCIONES ][ columna ];

                tableau.setValor( filaTableau , columna, coeficienteDeRestriccion);
            }
            
            /*
             * Rellenar las columnas con las variables de holgura, superavit o artificiales.
             * Se van a colocar primera las de holgura, luego las de superavit y por último las artificales.
             */
            
            //Ver que variable de igualdad está presente en esta restricción.
            Igualdades igualdadDeEstaFila = igualdadesRestricciones[ filaTableau - Tableau.DESPLAZAMIENTO_DEL_TABLEAU_CON_RESPECTO_IGUALDADES ];
            
            //Si es menor o igual, se le agrega una variable de holgura.
            if( igualdadDeEstaFila == Igualdades.MENOR_O_IGUAL){
                /*
                 * Colocar la variable después de las de decisión, en la posición que le 
                 * corresponde (Dependiendo de cuantas variables de holgura hay antes).
                 */
                tableau.setValor( filaTableau, tableau.getNumeroColumnaDondeEmpiezanVariablesDeHolgura() + cantidadDeVariablesDeHolguraCreadasAnteriormente, Fraccion.UNO );
                
                //Indicar que se creo una variable de holgura.
                cantidadDeVariablesDeHolguraCreadasAnteriormente++;
                
                //Indicar la variable básica.
                variablesBasicas[filaTableau] = new Variable(TipoVariable.HOLGURA, cantidadDeVariablesDeHolguraCreadasAnteriormente);
            }
            
            //Si es igual, se le agrega una variable artificial.
            else if( igualdadDeEstaFila == Igualdades.IGUAL){
                /*
                 * Colocar la variable artificial después de las de superavit, en la posición que le 
                 * corresponde (Dependiendo de cuantas variables de holgura y superavit hay
                 * antes).
                 */
                tableau.setValor( filaTableau, tableau.getNumeroColumnaDondeEmpiezanVariablesArticiales() + cantidadDeVariablesArtificialesCreadasAnteriormente, Fraccion.UNO );
                
                //Indicar que se creo una variable artificial.
                cantidadDeVariablesArtificialesCreadasAnteriormente++;
                
                //Indicar la variable básica.
                variablesBasicas[filaTableau] = new Variable(TipoVariable.ARTIFICIAL, cantidadDeVariablesArtificialesCreadasAnteriormente);
            }
            
            //Si es mayor o igual, se le agrega una variable de superavit y una de holgura.
            else if( igualdadDeEstaFila == Igualdades.MAYOR_O_IGUAL){
                /*
                 * Colocar la variable de superavit después de las de holgura, en la posición que le 
                 * corresponde (Dependiendo de cuantas variables de holgura hay antes).
                 */
                tableau.setValor( filaTableau, tableau.getNumeroColumnaDondeEmpiezanVariablesDeSuperavit() + cantidadDeVariablesDeSuperavitCreadasAnteriormente, Fraccion.MENOS_UNO );
                //Indicar que se creo una variable de superavit.
                cantidadDeVariablesDeSuperavitCreadasAnteriormente++;
                
                /**
                 * Colocar la variable artificial después de las de superavit, en la posición que le 
                 * corresponde (Dependiendo de cuantas variables de holgura y superavit hay
                 * antes).
                 */
                tableau.setValor( filaTableau, tableau.getNumeroColumnaDondeEmpiezanVariablesArticiales() + cantidadDeVariablesArtificialesCreadasAnteriormente, Fraccion.UNO );
                
                //Indicar que se creo una variable artificial.
                cantidadDeVariablesArtificialesCreadasAnteriormente++;
                
                //Indicar la variable básica.
                variablesBasicas[filaTableau] = new Variable(TipoVariable.ARTIFICIAL, cantidadDeVariablesArtificialesCreadasAnteriormente);
                
                
            }
            
            
            //Agregar el término independiente
            tableau.setValor( filaTableau, tableau.NUMERO_COLUMNA_TI, datosMetodoDeLasDosFases.getTIDeRestriccion( filaTableau - Tableau.DESPLAZAMIENTO_DEL_TABLEAU_CON_RESPECTO_A_RESTRICCIONES ) );
            
        }
    }
    
    /**
     * Rellena la filaConResultadoMasChico de las M con los coeficientes correspondientes.
     */
    private void colocarCoeficientesEnFilaM(){
        
        //Indicar que la variable básica es M
        variablesBasicas[ Tableau.NUMERO_FILA_COEFICIENTES_M ] = Variable.M;
        
        //Rellenar los coeficientes (Sin incluir los artificiales ni el TI).
        for( int columna = 0; columna < tableau.CANTIDAD_COLUMNAS; columna++ ){
            
            /*
            Si la columna representa una variable artificial, no se suma nada
            debido a que ya se le suma una M (Sea Maximización o Minimización)
            para aplicar la penalización y desp se le resta la M que está en
            la columna. Dicha resta siempre va a dar 0.
            Entonces, si la columna representa una variable artificial, se
            salta la iteración.
            */
            if( getVariableRepresentada( columna ).getTipoVariable() == TipoVariable.ARTIFICIAL ){
                continue;
            }
            
            //Sumar todos los coeficientes de las variablesBasicas artificales;
            Fraccion totalCoeficientesVariablesArtificiales = Fraccion.CERO;
            
            
            for( int fila = Tableau.NUMERO_INICIO_FILA_RESTRICCIONES; fila< tableau.CANTIDAD_FILAS; fila++ ){
                //Si la variable básica es una artificial, sumar al total
                if( variablesBasicas[fila].getTipoVariable() == TipoVariable.ARTIFICIAL ){
                    totalCoeficientesVariablesArtificiales = Calculadora.sumar( totalCoeficientesVariablesArtificiales, tableau.getValor( fila, columna ) );
                }
                
            }
            
            //Agregar el coeficiente (Se debe agregar el negativo de la suma).
            tableau.setValor( Tableau.NUMERO_FILA_COEFICIENTES_M, columna, totalCoeficientesVariablesArtificiales.getNegativo() );
        }
    }
    
    
    private boolean ZEstaAcotada( int columnaPivot ){
        
        for( int fila = Tableau.NUMERO_INICIO_FILA_RESTRICCIONES; fila < tableau.CANTIDAD_FILAS; fila++ ){
            if( tableau.getValor( fila, columnaPivot ).esFraccionPositiva() ){
                return true;
            }
        }
        
        return false;
    }
    
    
    /**
     * Indica si se tiene soluciones factibles.
     * Ésto se realiza en la primer fase del Método de las 2 Fases, en donde si se
     * observa que alguna variable artificial:
     * <br>
     * a) Todas las variables no artificiales (Ni T.I.) son positivos en la final de las M
     * (Es decir, se podría pasar a la otra fase).
     * <br>
     * b) Tiene un 1 en la columna que lo representa, en las restricciones (Es decir, es una variable básica).
     * <br>
     * c) El T.I. indica un valor mayor a 0.
     * @return true si sigue teniendo soluciones factibles, false si se detecta que no hay soluciones factibles.
     */
    private boolean sigueTeniendoSolucionesFactibles(){
        
        if( hayAlgunCoeficienteDeVariablesNoArtificialesNegativoEnFila( Tableau.NUMERO_FILA_COEFICIENTES_M ) ){
            return true;
        }
        
        for( Variable variable: variablesBasicas ){
            
            if( variable.getTipoVariable().equals( TipoVariable.ARTIFICIAL ) ){
                
                if( this.getValorTIVariableBasica( variable ).esFraccionPositiva() ){
                    return false;
                }
                
            }
            
        }
        
        return true;
        
    }
    
    
//    /**
//     * Devuelve el número de columna que representa la variable enviada como parámetro.
//     * @param variable
//     * @return Número de columna que representa la variable enviada como parámetro.
//     */
//    private int getNumeroColumna( Variable variable ){
//        
//        if( !this.existeVariableEnTableau( variable ) ){
//            throw new VariableInexistenteException();
//        }
//        
//        TipoVariable tipoVariable = variable.getTipoVariable();
//        
//        
//        //Se le resta 1 debido a que las variables empiezan a numerarse desde 1, y las columnas (Por definición) desde 0.
//        if( tipoVariable.equals( TipoVariable.DECISION ) ){
//            return variable.getNumeroVariable() - 1;
//        }
//        else if( tipoVariable.equals( TipoVariable.HOLGURA ) ){
//            return tableau.getNumeroColumnaDondeEmpiezanVariablesDeHolgura() + variable.getNumeroVariable() - 1;
//        }
//        else if( tipoVariable.equals( TipoVariable.SUPERAVIT ) ){
//            return tableau.getNumeroColumnaDondeEmpiezanVariablesDeSuperavit() + variable.getNumeroVariable() - 1;
//        }
//        else {
//            return tableau.getNumeroColumnaDondeEmpiezanVariablesArticiales() + variable.getNumeroVariable() - 1;
//        }
//    }
    
    
//    /**
//     * Indica si existe la variable enviada como parámetro en el Tableau.
//     * @param variableAVerificar
//     * @return 
//     */
//    private boolean existeVariableEnTableau( Variable variableAVerificar ){
//        return (
//                
//                //Como las variables empiezan desde 0, se le suma 1 y tiene que ser igual o menos que la cantidad total de ese tipo de variable.
//                variableAVerificar.getNumeroVariable() <= this.getCantidadVariablesEnTableau( variableAVerificar.getTipoVariable() )
//                );
//        
//    }
    
    
    /**
     * Devuelve el valor del TI de la variable básica enviada como parámetro.
     * @param variableBasicaAVerificar
     * @return 
     */
    private Fraccion getValorTIVariableBasica( Variable variableBasicaAVerificar ){
        
        for( int fila = Tableau.NUMERO_INICIO_FILA_RESTRICCIONES; fila < tableau.CANTIDAD_FILAS; fila++ ){
            
            if( variablesBasicas[ fila ].equals( variableBasicaAVerificar ) ){
                return tableau.getValor( fila, tableau.NUMERO_COLUMNA_TI );
            }
            
        }
        
        throw new RuntimeException();
        
    }
    
    
    /**
     * Devuelve la cantidad de restricciones con el variable de igualdad enviada en el parámetro.
     * @param igualdadBuscada Tipo de igualdad de la cual se quiere saber las veces que aparece.
     * @return Cantidad de veces que aparece la igualdad enviada en el parámetro.
     */
    private int getCantidadRestricciones( Igualdades igualdadBuscada ){
        
        int total = 0;
        
        //Recorrer las igualdadesRestricciones enviadas y ver la cantidad de menor o igual que hay.
        for( Igualdades igualdad: igualdadesRestricciones){
            
            if( igualdad == igualdadBuscada ){
                total++;
            }
        }
        
        return total;
    }
    
    
    
//    /**
//     * Devuelve la cantidad de variables que existen en el tableau del tipo indicado
//     * en el parámetro.
//     * @param tipoVariableAVerificar
//     * @return 
//     */
//    private int getCantidadVariablesEnTableau( TipoVariable tipoVariableAVerificar ){
//        
//        if( tipoVariableAVerificar.equals( TipoVariable.M ) ){
//            return 1;
//        }
//        else if( tipoVariableAVerificar.equals( TipoVariable.Z ) ){
//            return 1;
//        }
//        else if( tipoVariableAVerificar.equals( TipoVariable.DECISION ) ){
//            return datosMetodoDeLasDosFases.getCantidadVariablesDeDecision();
//        }
//        if( tipoVariableAVerificar.equals( TipoVariable.HOLGURA ) ){
//            return tableau.getNumeroColumnaDondeEmpiezanVariablesDeSuperavit() - tableau.getNumeroColumnaDondeEmpiezanVariablesDeHolgura();
//        }
//        else if( tipoVariableAVerificar.equals( TipoVariable.SUPERAVIT ) ){
//            return tableau.getNumeroColumnaDondeEmpiezanVariablesArticiales() - tableau.getNumeroColumnaDondeEmpiezanVariablesDeSuperavit();
//        }
//        else if( tipoVariableAVerificar.equals( TipoVariable.ARTIFICIAL ) ){
//            return tableau.NUMERO_COLUMNA_TI - tableau.getNumeroColumnaDondeEmpiezanVariablesArticiales();
//        }
//        else if( tipoVariableAVerificar.equals( TipoVariable.TERMINO_INDEPENDIENTE ) ){
//            return 1;
//        }
//        //Ésto no debería pasar nunca
//        else{
//            throw new RuntimeException();
//        }
//        
//    }
    
    
    
    
    
    /**
     * Indica si alguna de las variables básicas es del variable indicado en el
     * parámetro.
     * @param tipoDeVariable Tipo de variable que se busca.
     * @return true si hay una o más, false si no hay ninguna variable básica
     * de ese variable.
     */
    private boolean hayAlgunaVariableBasicaDelTipo( TipoVariable tipoDeVariable ){
        
        for( Variable variable: variablesBasicas ){
            
            if( variable.getTipoVariable() == tipoDeVariable ){
                return true;
            }
        }
        
        return false;
        
    }
    
    
    
    
    
    private void guardarPasoResolucionTableau( PasoMetodoDeLasDosFases paso ){
        this.pasosDeUnaFaseMetodoDeLasDosFaseses.add( paso );
    }
    
    
    
    /**
     * Invierte los signos de los coeficientes de la fila
     * de la función objetivo.
     */
    private void invertirSignoFilaObjetivoTableau(){
        
        //Se recorre hasta la cantidad de variables de decisión, porque después son todos ceros.
        for( int columna = 0; columna < datosMetodoDeLasDosFases.getCantidadVariablesDeDecision(); columna++ ){
            tableau.setValor( Tableau.NUMERO_FILA_FUNCION_OBJETIVO, columna, tableau.getValor( Tableau.NUMERO_FILA_FUNCION_OBJETIVO, columna ).getNegativo() );
        }
        
    }
    
    
    /**
     * Devuelve la variable que representa la columna enviada por parámetro.
     * @param columna Columna que se desea saber que variable representa.
     * @return Variable que representa.
     */
    private Variable getVariableRepresentada( int columna ){
        /*
        El numero de todas las variables es igual a la columna mas 1 (Porque
        las columnas empiezan en 0 y las columnas en 1.
        */
        //Variable de decisión
        if( columna >= 0 && columna < tableau.getNumeroColumnaDondeEmpiezanVariablesDeHolgura() ){
            return new Variable( TipoVariable.DECISION, columna + 1 );
        }
        //Variable de holgura
        else if( columna >= tableau.getNumeroColumnaDondeEmpiezanVariablesDeHolgura() && columna < tableau.getNumeroColumnaDondeEmpiezanVariablesDeSuperavit() ){
            return new Variable( TipoVariable.HOLGURA, columna - tableau.getNumeroColumnaDondeEmpiezanVariablesDeHolgura() + 1);
        }
        //Variable de superavit
        else if( columna >= tableau.getNumeroColumnaDondeEmpiezanVariablesDeSuperavit() && columna < tableau.getNumeroColumnaDondeEmpiezanVariablesArticiales() ){
            return new Variable( TipoVariable.SUPERAVIT, columna - tableau.getNumeroColumnaDondeEmpiezanVariablesDeSuperavit() + 1);
        }
        //Variables artificiales
        else if( columna >= tableau.getNumeroColumnaDondeEmpiezanVariablesArticiales() && columna < tableau.NUMERO_COLUMNA_TI ) {
            return new Variable( TipoVariable.ARTIFICIAL, columna - tableau.getNumeroColumnaDondeEmpiezanVariablesArticiales() + 1 );
        }
        else{
            return new Variable( TipoVariable.TERMINO_INDEPENDIENTE, 0);
        }
    }
    
    
    
    
    
    
    /**
     * Devuelve el tableu.
     * @return Tableu con los coeficientes.
     */
    public Tableau getTableau() {
        return tableau;
    }
    
    
    /**
     * Devuelve la solución de la optimización.
     * @return
     */
    public Fraccion getSolucionOptima(){
        assert  this.yaSeResolvio == true;
        
        return this.tableau.getValor( Tableau.NUMERO_FILA_FUNCION_OBJETIVO , this.tableau.NUMERO_COLUMNA_TI);
        
    }

    /**
     * Devuelve las soluciones ordenadas por variable de forma creciente
     * (Es decir X1, X2, X3...).
     * @return 
     */
    @SuppressWarnings("ReturnOfCollectionOrArrayField")
    public Fraccion[] getSoluciones() {
        assert this.yaSeResolvio == true;
        
        return soluciones;
    }
    
     @SuppressWarnings("ReturnOfCollectionOrArrayField")
    public Igualdades[] getIgualdadesRestricciones() {
        return igualdadesRestricciones;
    }

    @SuppressWarnings("ReturnOfCollectionOrArrayField")
    public ArrayList<PasoMetodoDeLasDosFases> getPasosDeUnaFaseMetodoDeLasDosFaseses() {
        return pasosDeUnaFaseMetodoDeLasDosFaseses;
    }

    public DatosOptimizacion getDatosMetodoDeLasDosFases() {
        return datosMetodoDeLasDosFases;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    /*
     * *****Métodos get que sirven para realizar tests unitarios.
     */
   

    @SuppressWarnings("ReturnOfCollectionOrArrayField")
    public Variable[] getVariablesBasicasPARAPRUEBA() {
        return variablesBasicas;
    }
    
    
    
}
