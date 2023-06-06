package cr.ac.ucr.paraiso.ie.progra2.maga.controller;

import cr.ac.ucr.paraiso.ie.progra2.maga.model.Vuelo;
import cr.ac.ucr.paraiso.ie.progra2.maga.servidor.MultiServidor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.PriorityQueue;
import java.util.Queue;

public class ServidorController {

    @FXML
    private Button btnGenerarReporte;
    @FXML
    private Button btnAceptarSolicitud;
    @FXML
    private Button btnPonerEnEspera;
    private MultiServidor multiServidor;
    Queue<Vuelo> vuelosQueue = new PriorityQueue<>();

    @FXML
    void initialize(){
        multiServidor = new MultiServidor(9999);
        multiServidor.startServer();
    }


}

