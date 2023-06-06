package cr.ac.ucr.paraiso.ie.progra2.maga.controller;

import cr.ac.ucr.paraiso.ie.progra2.maga.cliente.Piloto;
import cr.ac.ucr.paraiso.ie.progra2.maga.logic.Protocol;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class ClienteController {
    @FXML
    private TextArea txtaDatos;
    @FXML
    private Button btnDespegar;
    @FXML
    private Button btnAterrizar;
    @FXML
    private Button btnIrAPuerta;
    private Piloto piloto;
    Protocol protocol = new Protocol();

    //private Vuelo vuelo;


    @FXML
    void initialize(){
        piloto = new Piloto();
        piloto.start();
        ObservableList<String> items = FXCollections.observableArrayList("Avioneta", "Avión comercial", "Avión de carga");
        chbTipo.setItems(items);
        chbTipo.setValue("Tipo de avión");
        String respuesta = piloto.getRespuesta();
        txtaDatos.setText(respuesta);
        btnDespegar.setDisable(true);
        btnIrAPuerta.setDisable(true);
    }

    //¡Importante! Nota para mí xd
    //Deshabilitar vuelo.cambiarEstado() porque aún no tiene valores, tira un error.
    //Solucionar cosas con el constructor de Vuelo.
    @FXML
    void onActionDespegar(ActionEvent e) {
        //vuelo.cambiarEstado();
        btnIrAPuerta.setDisable(true);
        btnAterrizar.setDisable(false);
        btnDespegar.setDisable(true);
    }

    @FXML
    void onActionAterrizar(ActionEvent e) {
        btnIrAPuerta.setDisable(false);
        btnAterrizar.setDisable(true);
        //vuelo.cambiarEstado();
    }

    @FXML
    void onActionIrAPuerta(ActionEvent e) {
        btnDespegar.setDisable(false);
        btnIrAPuerta.setDisable(true);
        //vuelo.cambiarEstado();

    private Aeronave aeronave;

    @FXML
    void initialize(){
        piloto = new Piloto(9999);
        piloto.start(txtaDatos);
    }

    @FXML
    void onActionIrAPuerta(ActionEvent a) throws InterruptedException {
        btnAterrizar.setDisable(true);
        btnDespegar.setDisable(false);
        protocol.IrAPuerta();

    }

    @FXML
    void onActionAterrizar(ActionEvent a) throws InterruptedException {
        btnDespegar.setDisable(true);
        btnIrAPuerta.setDisable(false);
        protocol.aterrizar();
    }

    @FXML
    void onActionDespegar(ActionEvent a) throws InterruptedException {
        btnIrAPuerta.setDisable(true);
        btnAterrizar.setDisable(false);
        protocol.despegar();
    }
}