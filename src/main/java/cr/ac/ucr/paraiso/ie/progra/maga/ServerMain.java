package cr.ac.ucr.paraiso.ie.progra.maga;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ServerMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //Se carga el archivo FXML que define la interfaz de usuario del servidor
        FXMLLoader fxmlLoader = new FXMLLoader(ServerMain.class.getResource("interfaz/servidor.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Controlador Aéreo"); //Se define el título de la ventana
        stage.setScene(scene); //Se establece la escena en el Stage proporcionado
        stage.setResizable(false);
        stage.show(); //Se muestra la interfaz del servidor
    }

    @Override
    public void stop() throws Exception {
        System.exit(0); //Con este método se cierra toda la aplicación del servidor
    }

    public static void main(String[] args) {
        launch();
    }
}