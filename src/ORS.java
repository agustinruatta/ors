import Excepciones.ManejadorExcepcionesDefault;
import GUI.ControladorVistas;
import GUI.MostrarMensaje;
import GestionErrores.GestionErrores;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;



/**
 * Clase inicializadora de la aplicación
 * @author Agustín Ruatta <agustinruatta@gmail.com>
 */
public final class ORS extends Application{

    
    private final static String TITULO_APLICACION = "ORS";

    /**
     * Inicializa la aplicación.
     * @param args Argumentos de línea de comando.
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    private final String HOJA_ESTILO_CASCADA = "/GUI/CSS/style.css";

    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        try {
            this.establecerManejadorExcepcionesNoDetectadas();
            ControladorVistas controladorVistas = ControladorVistas.getInstance();
            
            ScrollPane scrollPane = new ScrollPane( controladorVistas );
            scrollPane.setFitToHeight(true);
            scrollPane.setFitToWidth(true);
            
            Scene scene = new Scene( scrollPane );
            
            scene.getStylesheets().add( getClass().getResource( this.HOJA_ESTILO_CASCADA ).toExternalForm() );
            
            primaryStage.setScene(scene);
            
            primaryStage.show();
            
            primaryStage.setTitle(TITULO_APLICACION);
            
            primaryStage.setMaximized(true);
            
        } 
        catch (Exception e) {
            GestionErrores.registrarError(e);
            MostrarMensaje.errorAlRealizarUnaOperacion(e);
        }
        
    }

    private void establecerManejadorExcepcionesNoDetectadas() {
        Thread.setDefaultUncaughtExceptionHandler( new ManejadorExcepcionesDefault() );
    }
    
    
    
    
    
}
