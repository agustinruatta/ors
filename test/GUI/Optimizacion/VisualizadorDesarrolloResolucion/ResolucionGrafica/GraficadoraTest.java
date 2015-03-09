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

import API.Fraccion;
import Optimizacion.Enumeraciones.Igualdades;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javax.swing.SwingUtilities;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 *
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 */
public class GraficadoraTest {
    
    @Rule
    public final JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
    
    @Test
    public void getInterseccionesEntreRestricciones1() {
        
        
        Fraccion[][] restricciones = {
            { new Fraccion(1), new Fraccion(1), new Fraccion(1) },
            { new Fraccion(1), new Fraccion(0), new Fraccion(0.5) }
        };
        
        Igualdades[] igualdades = {
            Igualdades.MENOR_O_IGUAL,
            Igualdades.MENOR_O_IGUAL
        };
        
        List<Coordenada> interseccionesEsperadas = new ArrayList<>();
        interseccionesEsperadas.add( new Coordenada(0, 1) );
        interseccionesEsperadas.add( new Coordenada(1, 0) );
        interseccionesEsperadas.add( new Coordenada(0.5, 0.5) );
        interseccionesEsperadas.add( new Coordenada(0.5, 0) );
        interseccionesEsperadas.add( new Coordenada(0.5, 2) );
        interseccionesEsperadas.add( new Coordenada(-1, 2) );
        interseccionesEsperadas.add( new Coordenada(2, -1) );
        interseccionesEsperadas.add( new Coordenada(0, 0) );
        interseccionesEsperadas.add( new Coordenada(0, 2) );
        interseccionesEsperadas.add( new Coordenada(2, 0) );
        interseccionesEsperadas.add( new Coordenada(2, 2) );
        
        Graficadora graficadora = new Graficadora( 500, 500, restricciones, igualdades, null);
        
        List<Coordenada> interseccionesEncontradas = graficadora.getInterseccionesEntreRestricciones();
        
        boolean sonIguales = interseccionesEsperadas.containsAll( interseccionesEncontradas ) && interseccionesEncontradas.containsAll( interseccionesEsperadas );
        
        Assert.assertEquals( sonIguales, true );
        
    }
    
    
    @Test
    public void getInterseccionesQueDelimitanRegionFactible() {
        
        
        Fraccion[][] restricciones = {
            { new Fraccion(1), new Fraccion(1), new Fraccion(1) },
            { new Fraccion(1), new Fraccion(0), new Fraccion(0.5) }
        };
        
        Igualdades[] igualdades = {
            Igualdades.MENOR_O_IGUAL,
            Igualdades.MENOR_O_IGUAL
        };
        
        List<Coordenada> interseccionesEsperadas = new ArrayList<>();
        interseccionesEsperadas.add( new Coordenada(0, 0) );
        interseccionesEsperadas.add( new Coordenada(0.5, 0) );
        interseccionesEsperadas.add( new Coordenada(0.5, 0.5) );
        interseccionesEsperadas.add( new Coordenada(0, 1) );
        
        Graficadora graficadora = new Graficadora( 500, 500, restricciones, igualdades, null);
        
        List<Coordenada> interseccionesEncontradas = graficadora.getInterseccionesQueDelimitanRegionFactible();
        
        boolean sonIguales = interseccionesEsperadas.containsAll( interseccionesEncontradas ) && interseccionesEncontradas.containsAll( interseccionesEsperadas );
        
        Assert.assertEquals( sonIguales, true );
        
    }
    
    
    
    
    
    
    
    
    /*
    Usado para permitir hacer tests unitarios a componentes de JavaFX.
    Sacado de http://stackoverflow.com/questions/18429422/basic-junit-test-for-javafx-8 .
    */
    
    /**
    * A JUnit {@link Rule} for running tests on the JavaFX thread and performing
    * JavaFX initialisation. To include in your test case, add the following code:
    *
    * <pre>
    * {@literal @}Rule
    * public JavaFXThreadingRule jfxRule = new JavaFXThreadingRule();
    * </pre>
    *
    * @author Andy Till
    *
    */
    private static class JavaFXThreadingRule implements TestRule {
        /**
        * Flag for setting up the JavaFX, we only need to do this once for all tests.
        */
        private static boolean jfxIsSetup;

        @Override
        public Statement apply(Statement statement, Description description) {
            return new OnJFXThreadStatement(statement);
        }

        private static class OnJFXThreadStatement extends Statement {
            private final Statement statement;


            private Throwable rethrownException = null;

            private OnJFXThreadStatement(Statement aStatement) {
                this.statement = aStatement;
            }

            @Override
            public void evaluate() throws Throwable {
                if(!jfxIsSetup) {
                    setupJavaFX();
                    jfxIsSetup = true;
                }

                final CountDownLatch countDownLatch = new CountDownLatch(1);

                Platform.runLater( new Runnable() {
                    @Override
                    public void run() {
                        try {
                            statement.evaluate();
                        } 
                        catch (Throwable e) {
                            rethrownException = e;
                        }
                        countDownLatch.countDown();
                    }
                });

                countDownLatch.await();
                // if an exception was thrown by the statement during evaluation,
                // then re-throw it to fail the test
                if(rethrownException != null) {
                    throw rethrownException;
                }
            }

            protected void setupJavaFX() throws InterruptedException {
                long timeMillis = System.currentTimeMillis();

                final CountDownLatch latch = new CountDownLatch(1);
                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        // initializes JavaFX environment
                        JFXPanel jfxPanel = new JFXPanel();
                        latch.countDown();
                    }
                });

                System.out.println("javafx initialising...");
                latch.await();
                System.out.println("javafx is initialised in " + (System.currentTimeMillis() - timeMillis) + "ms");
            }
        }
    } 
    
}
