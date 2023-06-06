package cr.ac.ucr.paraiso.ie.progra2.maga.controller;

import cr.ac.ucr.paraiso.ie.progra2.maga.servidor.MultiServidor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ServidorController {
    @FXML
    private Button btnGenerarReporte;
    @FXML
    private Button btnAceptarSolicitud;
    @FXML
    private Button btnPonerEnEspera;
    private MultiServidor multiServidor;

    @FXML
    void initialize(){
        multiServidor = new MultiServidor();
        multiServidor.start();
    }

    @FXML
    void onActionEnEsperar(ActionEvent actionEvent) {
    }

    @FXML
    void onActionAceptarSolicitud(ActionEvent actionEvent) {
    }

    @FXML
    void onActionGenerarReporte(ActionEvent actionEvent) {
    }

}
