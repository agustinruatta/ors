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
package GUI.Optimizacion;

import API.Fraccion;
import Excepciones.CantidadRestriccionesIncorrecto;
import Excepciones.CantidadVariablesIncorrecto;
import Excepciones.DemasiadasIteracionesMetodoDosFasesException;
import Excepciones.NoSePudoCargarVistaException;
import Excepciones.SinSolucionesFactiblesException;
import Excepciones.TodosLosCoeficientesDeLaFilaSonCeroException;
import Excepciones.ValorIngresadoInvalidoException;
import Excepciones.ZNoAcotadaException;
import GUI.ComprobacionesGUI;
import GUI.ControladorVistas;
import GUI.IReestablecerComponentes;
import GUI.ListadoVistas;
import GUI.ManejoErroresGUI;
import GUI.MostrarMensaje;
import GUI.Optimizacion.VisualizadorDesarrolloResolucion.PresentadorVisualizadorDesarrolloResolucion;
import GUI.Optimizacion.VisualizadorDesarrolloResolucion.ResolucionGrafica.RestriccionDeDosVariables;
import GUI.PresentadorAbstracto;
import GestionErrores.GestionErrores;
import Optimizacion.DatosOptimizacion;
import Optimizacion.Enumeraciones.Igualdades;
import Optimizacion.Enumeraciones.Objetivo;
import Optimizacion.Enumeraciones.TipoVariable;
import Optimizacion.MetodoDeLasDosFases.MetodoDeLasDosFases;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 *
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 */
public final class PresentadorIntroduccionDatosOptimizacion extends PresentadorAbstracto implements Initializable, IReestablecerComponentes {
    
    public static final int CANTIDAD_MAXIMA_VARIABLES_RECOMENDADAS = 25;
    
    public static final int CANTIDAD_MAXIMA_RESTRICCIONES_RECOMENDADAS = 100;
    
    /**
     * Parte superior de la ventana, que incluye:
     * <ul>
     * <li>Campo de texto para indicar la cantidad de variables.</li>
     * <li>Campo de texto para indicar la cantidad de restricciones.</li>
     * <li>Boton para habilitar el ingreso de datos.</li>
     * </ul>
     */
    @FXML
    private GridPane parteIngresoCantidadVariablesYRestricciones;
    
    /**
     * Parte de la ventana, que incluye:
     * <ul>
     * <li>Objetivo de la optimización.</li>
     * <li>Función objetivo.</li>
     * <li>Restricciones.</li>
     * <li>Si se muestran los pasos de la resolución del Tableau</li>
     * <li>Si se muestra el análisis de sensibilidad</li>
     * <li>Botones</li>
     * </ul>
     */
    @FXML
    private VBox parteIngresoDatosOptimizacion;
    
    @FXML
    private TextField cantidadVariables;
    
    @FXML
    private TextField cantidadRestricciones;
    
    @FXML
    private RadioButton maximizar;
    
    @FXML
    private RadioButton minimizar;
    
    @FXML
    private CheckBox mostrarPasosResolucionTableau;
    
    @FXML
    private CheckBox mostrarAnalisisSensibilidad;
    
    @FXML
    private CheckBox mostrarResolucionPorMetodoGrafico;
    
    @FXML
    private TableView< FilaCoeficientes > tablaFuncionObjetivo;
    
    @FXML
    private TableView< FilaCoeficientes > tablaRestricciones;
    
    

    public PresentadorIntroduccionDatosOptimizacion() {
        
    }

    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*
        Permitir solamente el ingreso de números en los campos de texto cant.
        de variables y restricciones
        */
        this.cantidadVariables.addEventFilter( KeyEvent.KEY_TYPED, ComprobacionesGUI.comprobarQueSeaUnNumeroEntero() );
        this.cantidadRestricciones.addEventFilter( KeyEvent.KEY_TYPED, ComprobacionesGUI.comprobarQueSeaUnNumeroEntero() );
        
        this.reestablecerComponentes();
        
        //Se deshabilita lo de analisis de sensibilidad porque todavía no está implementado
        this.mostrarAnalisisSensibilidad.setDisable(true);
    }
    
    
    @FXML
    private void clickBotonIngresarDatos(){
        
        try {
            //Limpiar de todos los errores que se hayan mostrado anteriormente.
            this.dejarDeMostrarErrores();
            
            boolean hayAlgunError = false;
            
            if( this.cantidadVariables.getText().isEmpty() ){
                
                ManejoErroresGUI.indicarError( this.cantidadVariables );
                
                hayAlgunError = true;
            }
            if( this.cantidadRestricciones.getText().isEmpty() ){
                
                ManejoErroresGUI.indicarError( this.cantidadRestricciones );
                
                hayAlgunError = true;
            }
            
            //Si hay algún error, no seguir mostrando nada más
            if( hayAlgunError ){
                return;
            }
            
            
            
            if( this.getCantidadDeVariables() > CANTIDAD_MAXIMA_VARIABLES_RECOMENDADAS ){
                
                MostrarMensaje.OPCIONES_CONSULTA opcionElegida = MostrarMensaje.estaSeguroDemasiadasVariables();
                
                if( opcionElegida == MostrarMensaje.OPCIONES_CONSULTA.CANCELAR ){
                    return;
                }
                
            }
            if( this.getCantidadDeRestricciones()> CANTIDAD_MAXIMA_RESTRICCIONES_RECOMENDADAS ){
                
                MostrarMensaje.OPCIONES_CONSULTA opcionElegida = MostrarMensaje.estaSeguroDemasiadasRestricciones();
                
                if( opcionElegida == MostrarMensaje.OPCIONES_CONSULTA.CANCELAR ){
                    return;
                }
                
            }
            
            
            //Ocultar la parte superior y mostrar la parte inferior
            this.parteIngresoCantidadVariablesYRestricciones.setVisible( false );
            this.parteIngresoDatosOptimizacion.setVisible( true );
            
            //Establecer la tabla función objetivo
            this.tablaFuncionObjetivo.getColumns().addAll( this.getColumnas( false ) );
            this.tablaFuncionObjetivo.setItems( this.getFilaFuncionObjetivo() );
            
            //Establecer la tabla restricciones
            this.tablaRestricciones.getColumns().addAll( 0, this.getColumnas( true ) );
            this.tablaRestricciones.setItems( FXCollections.observableArrayList( this.getFilasRestricciones() ) );
            
            //Si se puede, habilitar el CheckBox para ver resolución por método gráfico
            if( this.sePuedeAplicarMetodoGrafico() ){
                this.mostrarResolucionPorMetodoGrafico.setDisable( false );
            }
        }
        catch ( CantidadVariablesIncorrecto ex ) {
            ManejoErroresGUI.indicarError( this.cantidadVariables );
        }
        catch( CantidadRestriccionesIncorrecto ex ){
            ManejoErroresGUI.indicarError( this.cantidadRestricciones );
        }
        
        
    }
    
    
    @FXML
    private void clickVolverAlMenuPrincipal(){
        ControladorVistas.getInstance().mostrarVista(ListadoVistas.PANTALLA_PRINCIPAL );
    }
    
    @FXML
    private void clickCancelar(){
        this.reestablecerComponentes();
    }
    
    @FXML
    private void clickResolver(){
        
        
        if( !estanTodosLosCoeficientesDeTablaFuncionObjetivoCompletados() ){
            MostrarMensaje.algunCoeficienteDeLaFuncionObjetivoNoEstaCompletado();
            return;
        }
        if( !estanTodosLosCoeficientesDeTablaRestriccionesCompletados()){
            MostrarMensaje.algunCoeficienteDeLasRestriccionesNoEstaCompletado();
            return;
        }
        
        
        Objetivo objetivoSeleccionado = this.getObjetivoOptimizacionSeleccionado();
        Fraccion[] funcionObjetivo;
        Fraccion[][] restricciones;
        Igualdades[] igualdades;
        
        try {
            funcionObjetivo = this.getFuncionObjetivo();
        }
        catch (ValorIngresadoInvalidoException | CantidadVariablesIncorrecto e) {
            MostrarMensaje.algunCoeficienteDeLaFuncionObjetivoEsIncorrecto();
            return;
        }
        
        try {
            restricciones = this.getRestricciones();
        }
        catch (ValorIngresadoInvalidoException | CantidadRestriccionesIncorrecto | CantidadVariablesIncorrecto e) {
            MostrarMensaje.algunCoeficienteDeLasRestriccionesEsIncorrecto();
            return;
        }
        
        try {
            igualdades = this.getIgualdades();
        }
        catch (ValorIngresadoInvalidoException | CantidadRestriccionesIncorrecto | CantidadVariablesIncorrecto e) {
            MostrarMensaje.unaOMasIgualdadesIncorrectas();
            return;
        }
        
        
        assert funcionObjetivo != null;
        assert restricciones != null;
        assert igualdades != null;
        
        
        
        try {
            DatosOptimizacion datosIngresados = new DatosOptimizacion(objetivoSeleccionado, funcionObjetivo, restricciones, igualdades);
            
            MetodoDeLasDosFases metodoDeLasDosFases = new MetodoDeLasDosFases(datosIngresados, true);
            
            PresentadorVisualizadorDesarrolloResolucion presentador = ControladorVistas.getInstance().getVista(ListadoVistas.VISUALIZADOR_DESARROLLO_RESOLUCION).<PresentadorVisualizadorDesarrolloResolucion>getPresentador();
            
            RestriccionDeDosVariables restriccionSolucionOptima = null;
            
            if( this.seMuestraResolucionPorMetodoGrafico() ){
                restriccionSolucionOptima = new RestriccionDeDosVariables( this.getFuncionObjetivo()[0] , this.getFuncionObjetivo()[1], Igualdades.IGUAL, metodoDeLasDosFases.getSolucionOptima() );
            }
            
            //Si solo se muestra la solución, no hace falta mostrar este mensaje
            if( 
                    metodoDeLasDosFases.getDatosMetodoDeLasDosFases().seReacomodoAlgunaRestriccionConTINegativo() &&
                    ( this.seMuestraResolucionPorMetodoGrafico() || this.seMuestranLosPasosDeResolucionDeTableau() || this.seMuestranElAnalisisDeSensibilidad() )
                    ){
                MostrarMensaje.seReacomodoAlgunaRestriccionConTINegativo();
            }
            
            presentador.establecerParametrosResolucion( this.seMuestranLosPasosDeResolucionDeTableau() , this.seMuestranElAnalisisDeSensibilidad(), this.seMuestraResolucionPorMetodoGrafico(), this.getRestricciones(), restriccionSolucionOptima, metodoDeLasDosFases);
            presentador.mostrarDesarrolloResolucion();
            
            ControladorVistas.getInstance().mostrarVista(ListadoVistas.VISUALIZADOR_DESARROLLO_RESOLUCION );
        }
        catch (ZNoAcotadaException ex) {
            MostrarMensaje.ZNoEstaAcotada();
        }
        catch (DemasiadasIteracionesMetodoDosFasesException ex) {
            MostrarMensaje.demasiadasIteraciones();
        }
        catch (SinSolucionesFactiblesException ex) {
            MostrarMensaje.sinSolucionesFactibles();
        }
        //No llega nunca a este catch ya que antes controla si son valores válidos
        catch (ValorIngresadoInvalidoException ex) {
            GestionErrores.registrarError(ex);
        }
        catch( CantidadVariablesIncorrecto | CantidadRestriccionesIncorrecto ex ){
            GestionErrores.registrarError(ex);
        }
        catch (TodosLosCoeficientesDeLaFilaSonCeroException ex) {
            MostrarMensaje.unaOMasFilasTieneTodosLosCoeficientesCero();
        } 
        catch( NoSePudoCargarVistaException | IllegalArgumentException ex ){
            GestionErrores.registrarError(ex);
            MostrarMensaje.errorAlRealizarUnaOperacion(ex);
        }
        
    }
    
    
    @Override
    public void reestablecerComponentes(){
        /*
        Esconder la parte para ingresar los datos ya que no se determinó
        la cantidad de variables y restricciones.
        */
        this.parteIngresoCantidadVariablesYRestricciones.setVisible( true );
        this.parteIngresoDatosOptimizacion.setVisible( false );
        
        this.dejarDeMostrarErrores();
        
        this.cantidadVariables.setText("");
        this.cantidadRestricciones.setText("");
        
        //Limpiar la tabla función objetivo
        this.tablaFuncionObjetivo.getColumns().clear();
        this.tablaFuncionObjetivo.getItems().clear();
        
        //Limpiar la tabla restricciones
        this.tablaRestricciones.getColumns().clear();
        this.tablaRestricciones.getItems().clear();
        
        //Dejar apretado el boton maximizar
        this.maximizar.setSelected(true);
        this.minimizar.setSelected(false);
        
        //Deseleccionar los CheckBoxs
        this.mostrarPasosResolucionTableau.setSelected(false);
        this.mostrarAnalisisSensibilidad.setSelected(false);
        this.mostrarResolucionPorMetodoGrafico.setSelected(false);
        
        this.mostrarResolucionPorMetodoGrafico.setDisable( true );
        
    }
    
    private int getCantidadDeVariables() throws CantidadVariablesIncorrecto{
        
        try{
            int cantVariables = Integer.parseInt( this.cantidadVariables.getText() );
            
            if( cantVariables >= 0 ){
                return cantVariables;
            }
            else{
                throw new CantidadVariablesIncorrecto();
            }
        }
        catch( NumberFormatException ex ){
            throw new CantidadVariablesIncorrecto();
        }
    }
    
    private int getCantidadDeRestricciones() throws CantidadRestriccionesIncorrecto{
        
        try{
            int cantRestricciones = Integer.parseInt( this.cantidadRestricciones.getText() );
            
            if( cantRestricciones >= 0 ){
                return cantRestricciones;
            }
            else{
                throw new CantidadRestriccionesIncorrecto();
            }
        }
        catch( NumberFormatException ex ){
            throw new CantidadRestriccionesIncorrecto();
        }
        
    }
    
    /**
     * Devuelve una colección de columnas.
     * Además si al parámetro incluirColumnaIgualdadYTI se lo establece como verdadero,
     * se agregan las columnas de las igualdades y del término independiente.</br>
     * <p>
     * Ejemplo 1:</br>
     * <ul>
     * <li>Cantidad variables: 2</li>
     * <li>Cantidad restricciones: 2</li>
     * <li>incluirColumnaIgualdadYTI: false</li>
     * </ul>
     * Eso va a devolver una colección de la forma ["X1", "X2"].</p>
     * <p>
     * Ejemplo 2:</br>
     * <ul>
     * <li>Cantidad variables: 3</li>
     * <li>Cantidad restricciones: 2</li>
     * <li>incluirColumnaIgualdadYTI: true</li>
     * </ul></br>
     * Eso va a devolver una colección de la forma ["X1", "X2", "X3", "Igualdades", "TI"]</br>
     * </p>
     * @param incluirColumnaIgualdadYTI Si es true, se agrega ademas las columnas de igualdad
     * y término independiente (T.I.).
     * @return Listado con las columnas.
     */
    private ArrayList< TableColumn< FilaCoeficientes, String > > getColumnas( boolean incluirColumnaIgualdadYTI) throws CantidadVariablesIncorrecto{
        ArrayList< TableColumn< FilaCoeficientes, String > > columnasVariablesDecision = new ArrayList<>();
        
        int cantidadDeColumnas;
        
        if( incluirColumnaIgualdadYTI ){
            cantidadDeColumnas = this.getCantidadDeVariables() + 2;
        }
        else{
            cantidadDeColumnas = this.getCantidadDeVariables();
        }
        
        
        for( int numeroColumna = 0; numeroColumna < cantidadDeColumnas; numeroColumna++ ){
            
            final int j = numeroColumna;
            
            TableColumn< FilaCoeficientes, String > columna;
            
            if( numeroColumna < this.getCantidadDeVariables() ){
                columna = new TableColumn<>( TipoVariable.DECISION + String.valueOf(numeroColumna + 1) );
                columna.setCellFactory( new EditorCeldasCoeficientes() );
            }
            else if( numeroColumna == this.getIndiceColumnaIgualdades() ){
                columna = new TableColumn<>( this.getEncabezadoColumnaIgualdad() );
                columna.setCellFactory( new EditorCeldasIgualdades() );
            }
            else{
                columna = new TableColumn<>( this.getEncabezadoColumnaTI() );
                columna.setCellFactory( new EditorCeldasCoeficientes() );
            }
            
            
            columna.setSortable( false );
            
            
            columna.setCellValueFactory( new Callback<CellDataFeatures< FilaCoeficientes, String>, ObservableValue<String>>() {

                @Override
                public ObservableValue<String> call( CellDataFeatures< FilaCoeficientes, String> parametro ) {
                    return new SimpleStringProperty( parametro.getValue().get(j).get() );
                }
                
            });
            
            columna.setOnEditCommit(new EventHandler<CellEditEvent<FilaCoeficientes, String>>() {

                @Override
                public void handle(CellEditEvent<FilaCoeficientes, String> evento) {
                    evento.getTableView().getItems().get( evento.getTablePosition().getRow() ).get(j).set( evento.getNewValue() );
                }
                
            
            });
            
            
            columnasVariablesDecision.add( columna );
        }
        
        
        
        return columnasVariablesDecision;
    }
    
    
    private ObservableList< FilaCoeficientes >  getFilaFuncionObjetivo() throws CantidadVariablesIncorrecto{
        
        ObservableList<FilaCoeficientes> filaFuncionObjetivo = FXCollections.observableArrayList();
        filaFuncionObjetivo.add( new FilaCoeficientes( this.getCantidadDeVariables() ) );
        
        return filaFuncionObjetivo;
    }
    
    private ArrayList< FilaCoeficientes > getFilasRestricciones() throws CantidadVariablesIncorrecto, CantidadRestriccionesIncorrecto{
        ArrayList< FilaCoeficientes > filasRestricciones = new ArrayList<>();
        
        for( int i = 0; i < this.getCantidadDeRestricciones(); i++ ){
            filasRestricciones.add( new FilaCoeficientes( this.getCantidadDeVariables() + 2 ) );
        }
        
        return filasRestricciones;
    }
    
    
    private String getValorCeldaFuncionObjetivo( int columna) throws CantidadVariablesIncorrecto {
        try {
            assert columna < this.getCantidadDeVariables();
        } catch (CantidadVariablesIncorrecto ex) {
            GestionErrores.registrarError(ex);
        }
        
        return this.tablaFuncionObjetivo.getItems().get(0).get(columna).get();
    }
    
    private String getValorCeldaRestricciones( int fila, int columna ){
        try {
            assert fila < this.getCantidadFilasRestricciones();
            assert columna < this.getCantidadDeVariables() + 2;
        } catch (CantidadVariablesIncorrecto | CantidadRestriccionesIncorrecto ex) {
            GestionErrores.registrarError(ex);
        }
        
        return this.tablaRestricciones.getItems().get(fila).get(columna).get();
    }
    
    
    private Objetivo getObjetivoOptimizacionSeleccionado(){
        //Alguno de los dos objetivos debe estar seleccionado
        assert this.maximizar.isSelected() || this.minimizar.isSelected();
        
        if( this.maximizar.isSelected() ){
            return Objetivo.MAXIMIZAR;
        }
        else{
            return Objetivo.MINIMIZAR;
        }
        
    }
    
    private boolean seMuestranLosPasosDeResolucionDeTableau(){
        return this.mostrarPasosResolucionTableau.isSelected();
    }
    
    private boolean seMuestranElAnalisisDeSensibilidad(){
        return this.mostrarAnalisisSensibilidad.isSelected();
    }
    
    private boolean seMuestraResolucionPorMetodoGrafico() throws CantidadVariablesIncorrecto{
        if( this.sePuedeAplicarMetodoGrafico() ){
            return this.mostrarResolucionPorMetodoGrafico.isSelected();
        }
        else{
            return false;
        }
    }
    
    /**
     * Deja de mostrar todos los errores de la GUI.
     */
    private void dejarDeMostrarErrores(){
        ManejoErroresGUI.dejarDeMostrarError( this.cantidadVariables );
        ManejoErroresGUI.dejarDeMostrarError( this.cantidadRestricciones );
    }
    
    
    private boolean estanTodosLosCoeficientesDeTablaFuncionObjetivoCompletados(){
        for( SimpleStringProperty coeficiente : this.tablaFuncionObjetivo.getItems().get(0) ){
            if( coeficiente == null || coeficiente.get().isEmpty() ){
                return false;
            }
        }
        
        return true;
    }
    
    private boolean estanTodosLosCoeficientesDeTablaRestriccionesCompletados(){
        for( FilaCoeficientes filaCoeficientes : this.tablaRestricciones.getItems() ){
            for( SimpleStringProperty valorCelda : filaCoeficientes ){
                if( valorCelda == null || valorCelda.get().isEmpty() ){
                    return false;
                }
            }
        }
        
        return true;
    }
    
    
    /**
     * Devuelve los coeficientes de la función objetivo ingresada.
     * @return
     * @throws ValorIngresadoInvalidoException Si algún coeficiente
     * ingresado no es válido.
     */
    private Fraccion[] getFuncionObjetivo() throws CantidadVariablesIncorrecto, ValorIngresadoInvalidoException{
        
        Fraccion[] coeficientesFuncionObjetivo = new Fraccion[ this.getCantidadDeVariables() ];
        
        for( int columna = 0; columna < coeficientesFuncionObjetivo.length; columna++ ){
            
            try {
                coeficientesFuncionObjetivo[ columna ] = new Fraccion( this.getValorCeldaFuncionObjetivo(columna) );
            }
            catch (IllegalArgumentException e) {
                throw new ValorIngresadoInvalidoException();
            }
            
        }
        
        return coeficientesFuncionObjetivo;
    }
    
    /**
     * Devuelve los coeficientes de las restricciones ingresadas.
     * @return
     * @throws ValorIngresadoInvalidoException Si algún coeficiente
     * ingresado no es válido.
     */
    private Fraccion[][] getRestricciones() throws CantidadVariablesIncorrecto, ValorIngresadoInvalidoException, CantidadRestriccionesIncorrecto{
        //Se le suma 1 a la cantidad de columnas debido a que hay que sumar la columna del Término Independiente.
        Fraccion[][] coeficientesRestricciones = new Fraccion[ this.getCantidadFilasRestricciones() ][ this.getCantidadDeVariables() + 1 ];
        
        /**
         * Guardamos los coeficientes sin incluir el Término Independiente.
         */
        for( int fila = 0; fila < this.getCantidadFilasRestricciones(); fila++ ){
            for( int columna = 0; columna < this.getCantidadDeVariables(); columna++ ){
                
                try {
                    coeficientesRestricciones[ fila ][ columna ] = new Fraccion( this.getValorCeldaRestricciones( fila, columna ) );
                }
                catch (IllegalArgumentException e) {
                    throw new ValorIngresadoInvalidoException();
                }
            }
        }
        
        /**
         * Guardamos los coeficientes del Término Independiente.
         */
        for( int fila = 0; fila < this.getCantidadFilasRestricciones(); fila++ ){
            
            try {
                    coeficientesRestricciones[ fila ][ this.getCantidadDeVariables() ] = new Fraccion( this.getValorCeldaRestricciones( fila, this.getIndiceColumnaTerminoIndependiente() ) );
                }
                catch (IllegalArgumentException e) {
                    throw new ValorIngresadoInvalidoException();
                }
        }
        
        return coeficientesRestricciones;
        
    }
    
    private Igualdades[] getIgualdades() throws ValorIngresadoInvalidoException, CantidadRestriccionesIncorrecto, CantidadVariablesIncorrecto{
        Igualdades[] igualdades = new Igualdades[ this.getCantidadDeRestricciones() ];
        
        for( int fila = 0; fila < this.getCantidadFilasRestricciones(); fila++ ){
            Igualdades igualdadDeEstaFila = Igualdades.getTipoIgualdad( this.getValorCeldaRestricciones( fila, this.getIndiceColumnaIgualdades() ) );
            
            if( igualdadDeEstaFila == null ){
                throw new ValorIngresadoInvalidoException();
            }
            
            igualdades[ fila ] = igualdadDeEstaFila;
        }
        
        return igualdades;
    }
    
    private int getIndiceColumnaTerminoIndependiente() throws CantidadVariablesIncorrecto{
        return this.getCantidadDeVariables() + 1;
    }
    
    private int getIndiceColumnaIgualdades() throws CantidadVariablesIncorrecto{
        return this.getCantidadDeVariables();
    }
    
    private int getCantidadFilasRestricciones() throws CantidadRestriccionesIncorrecto{
        return this.getCantidadDeRestricciones();
    }
    
    
    private String getEncabezadoColumnaIgualdad(){
        return "Igualdad( \">=\", \"=\" o \"<=\" )";
    }
    
    private String getEncabezadoColumnaTI(){
        return "T.I.";
    }
    
    private boolean sePuedeAplicarMetodoGrafico() throws CantidadVariablesIncorrecto{
        return this.getCantidadDeVariables() == 2;
    }
    
    
    
}