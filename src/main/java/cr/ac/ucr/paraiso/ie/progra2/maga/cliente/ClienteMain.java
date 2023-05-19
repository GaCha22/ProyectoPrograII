package cr.ac.ucr.paraiso.ie.progra2.maga.cliente;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClienteMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClienteMain.class.getResource("cr/ac/ucr/paraiso/ie/progra2/gama/interfaz/cliente.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Cliente");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}