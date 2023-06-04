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

    public void onActionPonerEnEspera(ActionEvent a) {
    }

    public void onActionAceptarSolicitud(ActionEvent a) {
    }

    public void onActionGenerarReporte(ActionEvent a) {
    }
}
