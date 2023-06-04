package cr.ac.ucr.paraiso.ie.progra2.maga.controller;

import cr.ac.ucr.paraiso.ie.progra2.maga.cliente.Piloto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
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

    @FXML
    void initialize(){
        piloto = new Piloto();
        piloto.start();
        ObservableList<String> items = FXCollections.observableArrayList("Avioneta", "Avión comercial", "Avión de carga");
        chbTipo.setItems(items);
        chbTipo.setValue("Tipo de avión");
        String respuesta = piloto.getRespuesta();
        txtaDatos.setText(respuesta);
    }

    @FXML
    void onActionIrAPuerta(ActionEvent a) {
        btnAterrizar.setDisable(true);
        btnDespegar.setDisable(false);
    }

    @FXML
    void onActionAterrizar(ActionEvent a) {
        btnDespegar.setDisable(true);
        btnIrAPuerta.setDisable(false);
    }

    @FXML
    void onActionDespegar(ActionEvent a) {
        btnIrAPuerta.setDisable(true);
        btnAterrizar.setDisable(false);
    }
}