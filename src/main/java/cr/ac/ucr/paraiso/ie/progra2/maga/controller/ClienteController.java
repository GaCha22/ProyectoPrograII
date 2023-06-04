package cr.ac.ucr.paraiso.ie.progra2.maga.controller;

import cr.ac.ucr.paraiso.ie.progra2.maga.cliente.Piloto;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Aeronave;
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
    private TextArea txtaDatos;
    @FXML
    private Button btnDespegar;
    @FXML
    private Button btnAterrizar;
    @FXML
    private Button btnIrAPuerta;
    private Piloto piloto;
    private Aeronave aeronave;

    @FXML
    void initialize(){
        piloto = new Piloto(9999);
        piloto.start(txtaDatos);
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