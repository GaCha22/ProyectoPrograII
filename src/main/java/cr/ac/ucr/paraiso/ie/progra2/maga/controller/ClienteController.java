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
    private ChoiceBox<String> chbTipo;
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

    @FXML
    void initialize(){
        piloto.start();
        ObservableList<String> items = FXCollections.observableArrayList("Avioneta", "Avión comercial", "Avión de carga");
        chbTipo.setItems(items);
        chbTipo.setValue("Tipo de avión");
        String respuesta = piloto.getRespuesta();
        txtaDatos.setText(respuesta);
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