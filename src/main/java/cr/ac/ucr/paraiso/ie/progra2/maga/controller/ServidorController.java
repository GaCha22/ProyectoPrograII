package cr.ac.ucr.paraiso.ie.progra2.maga.controller;

import cr.ac.ucr.paraiso.ie.progra2.maga.servidor.MultiServidor;
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
}
