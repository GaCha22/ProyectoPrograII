package cr.ac.ucr.paraiso.ie.progra2.maga.controller;

import cr.ac.ucr.paraiso.ie.progra2.maga.servidor.MultiServidor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ServidorController {

    private MultiServidor multiServidor;

    @FXML
    void initialize(){
        multiServidor = new MultiServidor();
        multiServidor.start();
    }
    @FXML
    void onActionPonerEnEspera(ActionEvent a) {
    }

    @FXML
    void onActionAceptarSolicitud(ActionEvent a) {
    }

    @FXML
    void onActionGenerarReporte(ActionEvent a) {
    }
}
