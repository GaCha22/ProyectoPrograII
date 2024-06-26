package cr.ac.ucr.paraiso.ie.progra.maga;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ClienteMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClienteMain.class.getResource("interfaz/clienteHome.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Piloto");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show(); //Se muestra la interfaz de clienteHome
    }

    @Override
    public void stop() throws Exception { //Método que permite cerrar la aplicación del cliente
        System.exit(0);
    }

    public static void main(String[] args) {
        launch();
    }
}