package cr.ac.ucr.paraiso.ie.progra2.maga.controller;

import cr.ac.ucr.paraiso.ie.progra2.maga.cliente.Piloto;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Aeronave;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.CompaniaAerea;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Vuelo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    private Vuelo vuelo;

    @FXML
    void initialize(){
        piloto = new Piloto();
        piloto.start();
        btnDespegar.setDisable(true);
        btnIrAPuerta.setDisable(true);
    }

    @FXML
    void onActionIrAPuerta(ActionEvent a) throws InterruptedException {
        btnAterrizar.setDisable(true);
        btnDespegar.setDisable(false);
        btnIrAPuerta.setDisable(true);
    }

    @FXML
    void onActionAterrizar(ActionEvent a) throws InterruptedException {
        btnDespegar.setDisable(true);
        btnIrAPuerta.setDisable(false);
        btnAterrizar.setDisable(true);
    }

    @FXML
    void onActionDespegar(ActionEvent a) throws InterruptedException {
        btnIrAPuerta.setDisable(true);
        btnAterrizar.setDisable(false);
        btnDespegar.setDisable(true);
    }

    public void setTextTXT(String txt){
        txtaDatos.setText(txt);
    }

    public void setVuelo(Aeronave aeronave, CompaniaAerea companiaAerea){
        this.vuelo = new Vuelo(aeronave, companiaAerea);
    }
}