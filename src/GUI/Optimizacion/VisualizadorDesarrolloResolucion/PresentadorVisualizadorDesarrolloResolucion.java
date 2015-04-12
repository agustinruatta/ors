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
package GUI.Optimizacion.VisualizadorDesarrolloResolucion;

import API.Fraccion;
import Excepciones.NoSePudoCargarVistaException;
import GUI.ControladorVistas;
import GUI.ListadoVistas;
import GUI.MostrarMensaje;
import GUI.Optimizacion.VisualizadorDesarrolloResolucion.PasoResolucionTableau.PresentadorPasoResolucionTableau;
import GUI.Optimizacion.VisualizadorDesarrolloResolucion.ResolucionGrafica.Graficadora;
import GUI.Optimizacion.VisualizadorDesarrolloResolucion.ResolucionGrafica.RestriccionDeDosVariables;
import GUI.Optimizacion.VisualizadorDesarrolloResolucion.SolucionesOptimizacion.PresentadorSolucionesOptimizacion;
import GUI.PresentadorAbstracto;
import GUI.Vista;
import GestionErrores.GestionErrores;
import Optimizacion.MetodoDeLasDosFases.MetodoDeLasDosFases;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * Presentador encargado de la VistaVisualizadorResolución.
 * Cuando nos referimos a  la resolución, nos referimos a
 * mostrar:
 * <ul>
 * <li>Si se seleccionó, los pasos de la resolución del Tableau.</li>
 * <li>Solución de la optimización si es que tiene.</li>
 * <li>Si se seleccionó, el análisis de sensibilidad.</li>
 * </ul>
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 */
public class PresentadorVisualizadorDesarrolloResolucion extends PresentadorAbstracto{
    
    @FXML
    private BorderPane vista;
    
    @FXML
    private Pagination paginacion;
    
    private boolean seMuestraResolucionPorMetodoGrafico;
    
    private boolean seMuestraPasosResolucionTableau;
    
    private boolean seMuestranSolucionesOptimizacion;
    
    private boolean seMuestraAnalisisDeSensibilidad;
    
    private Fraccion[][] restricciones;
    
    private RestriccionDeDosVariables solucionOptima;
    
    private MetodoDeLasDosFases metodoDeLasDosFases;
    
    public PresentadorVisualizadorDesarrolloResolucion() {
        this.reestablecerParametrosresolucion();
    }
    
    
    
    /**
     * Establecer todos los parámetros necesario para mostrar la resolución.
     * Es un sustituto al constructor ya que no se puede llamarlo (Debido
     * a que el presentador se crea automáticamente cuando se carga la vista).
     * @param _seMuestraResolucionPorMetodoGrafico Indica si se va a mostrar
     * o no la resolución por método gráfico.
     * @param _seMuestraPasosResolucionTableau Indica si se va a mostrar
     * o no los pasos de la resolución del Tableau.
     * @param _seMuestranSolucionesOptimizacion Indica si se van a mostrar o no
     * las soluciones óptimas de la optimización. Por defecto se muestra. El
     * único caso en donde no se muestran es cuando Z no está acotada
     * y solamente se muestra la región factible en el método gráfico.
     * @param _seMuestraAnalisisDeSensibilidad Indica si se va a mostrar
     * o no el análisis de sensibilidad.
     * @param _restricciones Restricciones que se ingresaron en forma de una matriz de Fraccion.
     * @param _solucionOptima
     * @param _metodoDeLasDosFases Método de las 2 fases resuelto, de donde
     * se extraerá la información del cálculo de optimización llevada a cabo.
     */
    @SuppressWarnings("AssignmentToCollectionOrArrayFieldFromParameter")
    public void establecerParametrosResolucion( boolean _seMuestraResolucionPorMetodoGrafico, boolean _seMuestraPasosResolucionTableau, boolean _seMuestranSolucionesOptimizacion, boolean _seMuestraAnalisisDeSensibilidad, Fraccion[][] _restricciones, RestriccionDeDosVariables _solucionOptima, MetodoDeLasDosFases _metodoDeLasDosFases ){
        this.seMuestraResolucionPorMetodoGrafico = _seMuestraResolucionPorMetodoGrafico;
        this.seMuestraPasosResolucionTableau = _seMuestraPasosResolucionTableau;
        this.seMuestranSolucionesOptimizacion = _seMuestranSolucionesOptimizacion;
        this.seMuestraAnalisisDeSensibilidad = _seMuestraAnalisisDeSensibilidad;
        
        
        this.restricciones = _restricciones;
        
        this.solucionOptima = _solucionOptima;
        
        this.metodoDeLasDosFases = _metodoDeLasDosFases;
    }
    
    public final void reestablecerParametrosresolucion(){
        this.seMuestraResolucionPorMetodoGrafico = false;
        this.seMuestraPasosResolucionTableau = false;
        this.seMuestranSolucionesOptimizacion = false;
        this.seMuestraAnalisisDeSensibilidad = false;
        
        this.restricciones = null;
        
        this.solucionOptima = null;
        
        this.metodoDeLasDosFases = null;
    }
    
    
    /**
     * Reorganiza la vista para mostrar el desarrollo de la resolución
     * del problema de optimización.
     * @throws NoSePudoCargarVistaException
     */
    public void mostrarDesarrolloResolucion() throws NoSePudoCargarVistaException{
        assert this.metodoDeLasDosFases != null;
        
        this.paginacion.setPageCount( this.getCantidadPaginas() );
        
        this.paginacion.setPageFactory( (Integer pageIndex) -> getPagina(pageIndex) );
        
    }
    
    
    private Node getPagina( int numeroPagina ){
        
        try {
            
            if( this.seMuestraResolucionPorMetodoGrafico && numeroPagina == 0 ){
                /*
                Si se muestra las soluciones óptimas, entonces es un problema
                acotado y se puede mostrar la recta de la función objetivo.
                */
                return this.getResolucionPorMetodoGrafico( this.seMuestranSolucionesOptimizacion );
            }
            /*
            No hace falta estar preguntando si se muestra los pasos
            de la resolución del tableau o el análisis de sensibilidad.
            Con sólo ver el número de página de las soluciones es
            suficiente.
            */
            //Se tiene q devolver la resolución del tableau
            else if( numeroPagina < this.getNumeroPaginaSoluciones() ) {
                return this.getPasoResolucionTableau( numeroPagina );
            }
            //Se tiene que devolver las soluciones
            else if( numeroPagina == this.getNumeroPaginaSoluciones()  ){
                return this.getSolucionesOptimizacion();
            }
            //Se tiene que devolver el análisis de sensibilidad
            else{
                return this.getAnalisisDeSensibilidad( numeroPagina );
            }
        }
        catch ( NoSePudoCargarVistaException e ) {
            GestionErrores.registrarError(e);
            MostrarMensaje.errorAlRealizarUnaOperacion(e);
            
            //Se devuelve algo para evitar un NullPointerException
            return new Group();
        }
                
    }
    
    
    private Node getPasoResolucionTableau( int numeroPagina ){
        
        int numeroPasoResolucion;
        
        if( this.seMuestraResolucionPorMetodoGrafico ){
            numeroPasoResolucion = numeroPagina - 1;
        }
        else{
            numeroPasoResolucion = numeroPagina;
        }
        
        try {
            
            Vista paso = new Vista(ListadoVistas.PASO_RESOLUCION_TABLEAU);
            
            paso.<PresentadorPasoResolucionTableau>getPresentador().mostrarPaso( numeroPasoResolucion, this.metodoDeLasDosFases.getPasosDeUnaFaseMetodoDeLasDosFaseses().get( numeroPasoResolucion ) );
            
            return paso.getVista();
        }
        catch (NoSePudoCargarVistaException ex) {
            GestionErrores.registrarError(ex);
            
            throw new RuntimeException();
        }
        
    }
    
    private Node getSolucionesOptimizacion() throws NoSePudoCargarVistaException{
        
        Vista vistaSolucionesOptimizacion = new Vista(ListadoVistas.SOLUCIONES_OPTIMIZACION);
        
        vistaSolucionesOptimizacion.<PresentadorSolucionesOptimizacion>getPresentador().mostrarSoluciones( this.metodoDeLasDosFases );
        
        return vistaSolucionesOptimizacion.getVista();
    }
    
    private Node getAnalisisDeSensibilidad( int numeroPagina ){
        return new AnchorPane();
    }
    
    /**
     * 
     * @param seMuestraRectaFuncionObjetivo Si es true, se muestra la recta
     * de la función objetivo que pasa por el punto de la solución óptima.
     * Normalmente ésto es así. La única razón para no mostrarlo es que
     * la solución no esté acotada y no se pueda mostarla (Debido a que
     * no hay solución óptima).
     * @return 
     */
    private Node getResolucionPorMetodoGrafico( boolean seMuestraRectaFuncionObjetivo ){
        Graficadora graficadora;
        
        if( seMuestraRectaFuncionObjetivo ){
            graficadora = new Graficadora( this.getAnchoResolucionMetodoGrafico(), this.getAltoResolucionMetodoGrafico(), this.restricciones, this.metodoDeLasDosFases.getIgualdadesRestricciones() , this.solucionOptima);
        }
        else{
            graficadora = new Graficadora( this.getAnchoResolucionMetodoGrafico(), this.getAltoResolucionMetodoGrafico(), this.restricciones, this.metodoDeLasDosFases.getIgualdadesRestricciones() , null);
        }
        
        return graficadora;
    }
    
    @FXML
    private void volverIntroduccionDatos(){
        ControladorVistas.getInstance().mostrarVista(ListadoVistas.INTRODUCCION_DATOS_OPTIMZACION );
        
        this.reestablecerParametrosresolucion();
    }
    
    
    
    
    
    private int getCantidadPaginas(){
        int cantidadPaginas = 0;
        
        if( this.seMuestraResolucionPorMetodoGrafico ){
            cantidadPaginas++;
        }
        if( this.seMuestraPasosResolucionTableau ){
            cantidadPaginas += this.metodoDeLasDosFases.getPasosDeUnaFaseMetodoDeLasDosFaseses().size();
        }
        if( this.seMuestranSolucionesOptimizacion ){
            cantidadPaginas++;
        }
        if( this.seMuestraAnalisisDeSensibilidad ){
            //TODO: agregar la cantidad de páginas que se agregan cuando se implemente
        }
        
        return cantidadPaginas;
        
    }
    
    
    /**
     * Devuelve el número de página donde se encuentran
     * las soluciones.
     * @return 
     */
    private int getNumeroPaginaSoluciones(){
        int numeroPagina = 0;
        
        if( this.seMuestraResolucionPorMetodoGrafico ){
            numeroPagina++;
        }
        if( this.seMuestraPasosResolucionTableau ){
            numeroPagina += this.metodoDeLasDosFases.getPasosDeUnaFaseMetodoDeLasDosFaseses().size();
        }
        
        return numeroPagina;
    }
    
    
    private int getAnchoResolucionMetodoGrafico(){
        int anchoPantallaCompleta = (int) ControladorVistas.getInstance().getWidth();
        
        return anchoPantallaCompleta - 250;
    }
    
    private int getAltoResolucionMetodoGrafico(){
        int altoPantallaCompleta = (int) ControladorVistas.getInstance().getHeight();
        
        return altoPantallaCompleta - 180;
    }
    
}
