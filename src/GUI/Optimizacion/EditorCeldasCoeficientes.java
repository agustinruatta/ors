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
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

/**
 *
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 */
public class EditorCeldasCoeficientes implements Callback< TableColumn< FilaCoeficientes, String >, TableCell< FilaCoeficientes , String > >{
    
    
    @Override
    public TableCell< FilaCoeficientes , String > call(TableColumn< FilaCoeficientes, String > columna) {
        return new CeldaEditable();
    }
    
    private class CeldaEditable extends TableCell< FilaCoeficientes , String >{
        
        private final String SIGNO_FRACCION = "/";
        private final String SIGNO_MENOS = "-";
        
        private TextField textField;

        private CeldaEditable() {
            super();
        }
        
        
        
        
        @Override
        public void startEdit() {
            super.startEdit();
            
            if ( textField == null ) {
                this.crearTextField();
            }
            
            setGraphic(textField);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    textField.requestFocus();
                    textField.selectAll();
                }
            });
        }
        
        @Override
        public void cancelEdit() {
            super.cancelEdit();
            
            this.setText( this.getItem() );
            
            this.setContentDisplay( ContentDisplay.TEXT_ONLY );
        }
        
        @Override
        public void updateItem(String item, boolean empty) {
            
            super.updateItem(item, empty);
            
            if ( empty ) {
                this.setText(null);
                this.setGraphic(null);
            }
            else {
                if ( isEditing() ) {
                    if ( textField != null) {
                        textField.setText(this.getString());
                    }
                    setGraphic(textField);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                } else {
                    setText( this.getString() );
                    setContentDisplay(ContentDisplay.TEXT_ONLY);
                }
            }
            
        }
        
        
        private String getString() {
            return getItem() == null ? "" : getItem();
        }
        
        
        private void crearTextField() {
            
            textField = new TextField("");
            
            textField.setOnKeyPressed( new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent evento) {
                    
                    if ( evento.getCode() == KeyCode.ENTER ) {
                        commitEdit( textField.getText() );
                    }
                    else if (evento.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    }
                    else if (evento.getCode() == KeyCode.TAB) {
                        commitEdit( textField.getText() );
                        
                        TableColumn< ObservableList<StringProperty> , ?> siguienteColumna = null;
                        
                        
                        if ( !seEstaEditandoLaUltimaColumna() ) {
                            getTableView().edit( getTableRow().getIndex(), getSiguienteColumna() );
                        }
                        else{
                            if( !seEstaEditandoLaUltimaFila() ){
                                getTableView().edit( getTableRow().getIndex() + 1, getPrimerColumna() );
                            }
                            else{
                                getTableView().edit( 0, getPrimerColumna() );
                            }
                            
                        }
                    }
                }
            });
            
            this.textField.setOnKeyTyped(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent evento) {
                    
                    if( !esUnCaracterPermitido( evento.getCharacter() ) ){
                        evento.consume();
                    }
                }
            });
            
            textField.focusedProperty().addListener( new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (!newValue && textField != null) {
                        commitEdit(textField.getText());
                    }
                }
            });
        }
        
        /**
         * Devuelve la siguiente columna a la cual tiene que saltar.
         * La siguiente columna que se devuelve es la que está
         * a la derecha de la actual que se está editando.
         * @return Columna a la que tiene que saltar.
         * Si es null, se llego a la última columna.
         */
        private TableColumn< FilaCoeficientes , ?> getSiguienteColumna() {
            
            TableColumn< FilaCoeficientes , ?> siguienteColumna = null;
            boolean devolverProximaColumna = false;
            
            
            for( TableColumn< FilaCoeficientes , ?> columna: this.getTableView().getColumns() ){
                
                if( devolverProximaColumna ){
                    siguienteColumna = columna;
                    break;
                }
                
                //Si es justo la misma columna, devolver la próxima
                if( columna.equals( this.getTableColumn() ) ){
                    devolverProximaColumna = true;
                }
                
            }
            
            assert siguienteColumna != null;
            
            return siguienteColumna;
        }
        
        
        private boolean seEstaEditandoLaUltimaColumna(){
            return ( this.getTableView().getColumns().indexOf( this.getTableColumn() ) + 1 == this.getTableView().getColumns().size() );
        }
        
        private boolean seEstaEditandoLaUltimaFila(){
            return ( this.getTableRow().getIndex() + 1 == this.getTableView().getItems().size() );
        }
        
        private TableColumn< FilaCoeficientes , ?> getPrimerColumna(){
            return this.getTableView().getColumns().get( 0 );
        }
        
        private boolean esUnCaracterPermitido( String caracterIngresado ){
            assert caracterIngresado != null;
            
            String textoQueSeVaAMostrar = this.textoQueSeVaAMostrar(caracterIngresado, this.textField.getSelectedText() );
            
            try{
                Fraccion fraccion = new Fraccion(textoQueSeVaAMostrar);
                
                return true;
            }
            catch( IllegalArgumentException e ){
                /*
                Si no se pudo crear la fracción, comprobar que no se
                vaya a escribir un número correcto posteriormente.
                */
                
                //Es el signo de la fracción ubicado correctamente (Es decir, no al principio)
                if( 
                        caracterIngresado.equals( this.SIGNO_FRACCION ) && 
                        !this.seIngresoAnteriormeteUnSignoFraccion() && 
                        !this.esElPrimerCaracterIngresado() &&
                        !this.elCursorEstaAlComienzo() &&
                        !this.hayIngresadoUnNumeroDecimal()
                        ){
                    return true;
                }
                //Es el signo menos
                else if(
                        caracterIngresado.equals(this.SIGNO_MENOS) &&
                        ( this.esElPrimerCaracterIngresado() || (this.elCursorEstaAlComienzo() && !this.yaSeIngreso(this.SIGNO_MENOS) ) )
                        ){
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        
        private boolean seIngresoAnteriormeteUnSignoFraccion(){
            //Si está todo el texto seleccionado, es como si no se hubiese ingresado un signo fracción
            if( this.seSeleccionTodoElTexto() ){
                return false;
            }
            
            return this.yaSeIngreso( SIGNO_FRACCION );
        }
        
        private boolean esElPrimerCaracterIngresado(){
            //Si está todo el texto seleccionado, es como si fuese el primer caracter ingresado
            if( this.seSeleccionTodoElTexto() ){
                return true;
            }
            
            return this.textField.getText().isEmpty();
        }
        
        private boolean elCursorEstaAlComienzo(){
            //Si está todo el texto seleccionado, es como si el cursor estuviera al comienzo
            if( this.seSeleccionTodoElTexto() ){
                return true;
            }
            
            return this.textField.getCaretPosition() == 0;
        }
        
        private boolean yaSeIngreso( String caracter ){
            assert caracter.length() <= 1;
            
            //Si está todo el texto seleccionado, es como si no se hubiese ingresado
            if( this.seSeleccionTodoElTexto() ){
                return false;
            }
            
            return this.textField.getText().contains(caracter);
        }
        
        /**
         * Texto que se va a mostrar cuando se ingrese el nuevo caracter.
         * @param caracterIngresado
         * @param textoSeleccionado
         * @return 
         */
        private String textoQueSeVaAMostrar( String caracterIngresado, String textoSeleccionado ){
            String textoQueSeVaAMostrar = this.textField.getText();
            
            //Si esta seleccionado una parte, ver como quedaría
            //Borrar la parte seleccionada
            textoQueSeVaAMostrar = textoQueSeVaAMostrar.substring(0, this.textField.getSelection().getStart() ) + caracterIngresado + textoQueSeVaAMostrar.substring( this.textField.getSelection().getEnd() );
            
            return textoQueSeVaAMostrar;
        }
        
        
        private boolean hayIngresadoUnNumeroDecimal(){
            if( this.textField.getText().contains(".") ){
                return true;
            }
            
            try {
                double numero = Double.valueOf( this.textField.getText() );
                
                //Si el número redondeado es diferente al original, entonces se ha ingresado un número decimal
                return ( (int) numero ) != numero;
            }
            catch (NumberFormatException e) {
                return false;
            }
        }
        
        private boolean seSeleccionTodoElTexto(){
            return this.textField.getText().length() == this.textField.getSelectedText().length();
        }
        
    }
    
}
