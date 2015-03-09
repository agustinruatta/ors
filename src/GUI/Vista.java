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

import Excepciones.NoSePudoCargarVistaException;
import GestionErrores.GestionErrores;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

/**
 *
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 */
public class Vista {
    
    private final Node vista;
    
    private final PresentadorAbstracto presentador;

    /**
     * Constructor.
     * @param vistaACargar Vista que se va a cargar
     * @throws NoSePudoCargarVistaException 
     */
    public Vista( ListadoVistas vistaACargar ) throws NoSePudoCargarVistaException {
        
        /*
        Aunque ésto se podría encapsular en un método, no se hace
        debido a que no se podrían establecer en final los atributos
        vista y presentador.
        */
        try {
            FXMLLoader cargadorVista = new FXMLLoader( Vista.class.getResource( vistaACargar.getArchivoFXML() ), ResourceBundle.getBundle( vistaACargar.getArchivoInternacionalizacion() ) );
            
            //Cargar y guardar la vista
            this.vista = cargadorVista.load();
            
            //Guardar el presentador
            this.presentador = cargadorVista.getController();
            
        } catch (IOException ex) {
            GestionErrores.registrarError(ex);
            throw new NoSePudoCargarVistaException();
        }
        
    }
    
    
    

    public Node getVista() {
        return vista;
    }

    @SuppressWarnings("unchecked")
    public < P extends PresentadorAbstracto > P getPresentador() {
        return (P) presentador;
    }
    
    
    
    
}
