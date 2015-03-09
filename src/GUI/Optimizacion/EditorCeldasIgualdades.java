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
public class EditorCeldasIgualdades implements Callback< TableColumn< FilaCoeficientes, String >, TableCell< FilaCoeficientes , String > >{
    
    
    @Override
    public TableCell< FilaCoeficientes , String > call(TableColumn< FilaCoeficientes, String > columna) {
        return new CeldaEditable();
    }
    
    private class CeldaEditable extends TableCell< FilaCoeficientes , String >{
        
        
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
            
            if( this.textField.getText().length() == 2 ){
                return false;
            }
            
            if( esElPrimerCaracterIngresado() ){
                if(
                        caracterIngresado.equals(">") ||
                        caracterIngresado.equals("=") ||
                        caracterIngresado.equals("<")
                        ){
                    return true;
                }
                else{
                    return false;
                }
            }
            //No es el primer caracter ingresado
            else{
                if( caracterIngresado.equals("=") && !getPrimerCaracterIngresado().equals("=") ){
                    return true;
                }
                else{
                    return false;
                }
            }
            
        }
        
        
        private boolean esElPrimerCaracterIngresado(){
            return this.textField.getText().isEmpty();
        }
        
        private String getPrimerCaracterIngresado(){
            return this.textField.getText().substring(0, 1);
        }
        
        
    }
    
}
