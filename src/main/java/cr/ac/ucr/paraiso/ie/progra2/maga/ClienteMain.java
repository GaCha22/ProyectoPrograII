package cr.ac.ucr.paraiso.ie.progra2.maga;

import cr.ac.ucr.paraiso.ie.progra2.maga.cliente.Piloto;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClienteMain extends Application {
    Piloto piloto = new Piloto();
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClienteMain.class.getResource("interfaz/cliente.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("cliente");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        piloto.start();
    }

    public static void main(String[] args) {
        launch();
    }
}