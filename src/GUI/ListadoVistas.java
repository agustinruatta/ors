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
package GUI;

/**
 * Sirve para guardad como enumeración todas las vistas FXML existentes,
 * asbtrayendo así propiedades de éstas tales como la ubicación del archivo
 * FXML y la ubicación del archivo de internacionalización.
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 */
public enum ListadoVistas {
    PANTALLA_PRINCIPAL("/GUI/PantallaPrincipal/VistaPantallaPrincipal.fxml", "Internacionalizacion.LANG" ),
    OPCIONES("/GUI/Opciones/VistaOpciones.fxml", "Internacionalizacion.LANG" ),
    STOCK("/GUI/Stock/VistaStock.fxml", "Internacionalizacion.LANG" ),
    ACERCA_DE("/GUI/AcercaDe/VistaAcercaDe.fxml", "Internacionalizacion.LANG" ),
    AYUDA("/GUI/Ayuda/VistaAyuda.fxml", "Internacionalizacion.LANG" ),
    INTRODUCCION_DATOS_OPTIMZACION("/GUI/Optimizacion/VistaIntroduccionDatosOptimizacion.fxml", "Internacionalizacion.LANG" ),
    VISUALIZADOR_DESARROLLO_RESOLUCION("/GUI/Optimizacion/VisualizadorDesarrolloResolucion/VistaVisualizadorDesarrolloResolucion.fxml", "Internacionalizacion.LANG" ),
    PASO_RESOLUCION_TABLEAU("/GUI/Optimizacion/VisualizadorDesarrolloResolucion/PasoResolucionTableau/VistaPasoResolucionTableau.fxml", "Internacionalizacion.LANG" ),
    SOLUCIONES_OPTIMIZACION("/GUI/Optimizacion/VisualizadorDesarrolloResolucion/SolucionesOptimizacion/VistaSolucionesOptimizacion.fxml", "Internacionalizacion.LANG" );
    
    /**
     * Ubicación del archivo FXML
     */
    private final String archivoFXML;
    
    /**
     * Ubicación del archivo de internacionalización.
     */
    private final String archivoInternacionalizacion;
    

    private ListadoVistas( String _archivoFXML, String _archivoInternacionalizacion ) {
        this.archivoFXML = _archivoFXML;
        this.archivoInternacionalizacion = _archivoInternacionalizacion;
    }

    
    public String getArchivoFXML() {
        return archivoFXML;
    }

    public String getArchivoInternacionalizacion() {
        return archivoInternacionalizacion;
    }
    
    
    
}
