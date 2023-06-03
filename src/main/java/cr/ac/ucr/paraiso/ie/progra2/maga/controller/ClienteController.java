package cr.ac.ucr.paraiso.ie.progra2.maga.controller;

import cr.ac.ucr.paraiso.ie.progra2.maga.cliente.Piloto;
import cr.ac.ucr.paraiso.ie.progra2.maga.logic.VueloLogica;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Vuelo;
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
    private Vuelo vuelo;


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
    }

    //¡Importante!
    //Aarón: Deshabilité vuelo.cambiarEstado() porque aún no tiene valores, por lo que tira un error.
    @FXML
    void onActionDespegar(ActionEvent e) {
        //vuelo.cambiarEstado();
        btnIrAPuerta.setDisable(true);
    }

    @FXML
    void onActionAterrizar(ActionEvent e) {
        btnIrAPuerta.setDisable(false);
        //vuelo.cambiarEstado();
    }

    @FXML
    void onActionIrAPuerta(ActionEvent e) {
        btnDespegar.setDisable(false);
        //vuelo.cambiarEstado();
    }

}