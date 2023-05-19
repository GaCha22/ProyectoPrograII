package cr.ac.ucr.paraiso.ie.progra2.maga.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class ClienteController {


    @FXML
    private ChoiceBox<String> chbTipo;
    @FXML
    private TextArea txtaDatos;

    @FXML
    void initialize(){
        ObservableList<String> items = FXCollections.observableArrayList("Avioneta", "Avión comercial", "Avión de carga");
        chbTipo.setItems(items);
        chbTipo.setValue("Tipo de avión");
    }

    @FXML
    void onActionGenerarReporte(ActionEvent event) {
    }

}