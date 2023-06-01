package cr.ac.ucr.paraiso.ie.progra2.maga;

import cr.ac.ucr.paraiso.ie.progra2.maga.servidor.MultiServidor;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ServerMain extends Application {
    MultiServidor multiServidor = new MultiServidor();
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ServerMain.class.getResource("interfaz/servidor.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Controllador Aereo");
        stage.setScene(scene);
        stage.show();
        multiServidor.start();
    }

    @Override
    public void stop() throws Exception {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch();
    }
}