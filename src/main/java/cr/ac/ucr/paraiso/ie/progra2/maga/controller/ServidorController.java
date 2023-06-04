package cr.ac.ucr.paraiso.ie.progra2.maga.controller;

import cr.ac.ucr.paraiso.ie.progra2.maga.model.Vuelo;
import cr.ac.ucr.paraiso.ie.progra2.maga.servidor.MultiServidor;
import javafx.fxml.FXML;

import java.util.PriorityQueue;
import java.util.Queue;

public class ServidorController {

    private MultiServidor multiServidor;
    Queue<Vuelo> vuelosQueue = new PriorityQueue<>();

    @FXML
    void initialize(){
        multiServidor = new MultiServidor(9999);
        multiServidor.startServer();
    }

}
