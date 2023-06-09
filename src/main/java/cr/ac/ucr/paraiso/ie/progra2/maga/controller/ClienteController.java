package cr.ac.ucr.paraiso.ie.progra2.maga.controller;

import cr.ac.ucr.paraiso.ie.progra2.maga.cliente.Piloto;
import cr.ac.ucr.paraiso.ie.progra2.maga.logic.VueloLogica;
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
    private VueloLogica vueloLogica;

    @FXML
    void initialize(){
        piloto = new Piloto(9999, txtaDatos);
        piloto.start();
        btnDespegar.setDisable(true);
        btnIrAPuerta.setDisable(true);
    }

    @FXML
    void onActionIrAPuerta(ActionEvent a){
        btnAterrizar.setDisable(true);
        btnDespegar.setDisable(false);
        btnIrAPuerta.setDisable(true);
        nuevoEstado();
    }

    @FXML
    void onActionAterrizar(ActionEvent a) {
        btnDespegar.setDisable(true);
        btnIrAPuerta.setDisable(false);
        btnAterrizar.setDisable(true);
        nuevoEstado();
    }

    @FXML
    void onActionDespegar(ActionEvent a){
        btnIrAPuerta.setDisable(true);
        btnAterrizar.setDisable(false);
        btnDespegar.setDisable(true);
        nuevoEstado();
    }

    public void setTextTXT(String txt){
        txtaDatos.setText(txt);
    }

    public void setVuelo(Aeronave aeronave, CompaniaAerea companiaAerea){
        this.vuelo = new Vuelo(aeronave, companiaAerea);
    }

    public void nuevoEstado(){
        String contenido = txtaDatos.getText();
        String[] lineas = contenido.split("\n");
        String estado = this.vuelo.getAeronave().getEstado() == 3 ? "En el aire" : this.vuelo.getAeronave().getEstado() == 2 ? "En puerta" : this.vuelo.getAeronave().getEstado() == 1 ? "Aterrizando" : "En espera";
        lineas[3] = "Estado del avión: SIRVE" + estado;
        String nuevoContenido = String.join("\n", lineas);
        txtaDatos.setText(nuevoContenido);
    }

}