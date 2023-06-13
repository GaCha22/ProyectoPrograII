package cr.ac.ucr.paraiso.ie.progra2.maga.controller;

import cr.ac.ucr.paraiso.ie.progra2.maga.cliente.Piloto;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Aeronave;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.CompaniaAerea;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Vuelo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.application.Platform;

public class ClienteController implements PropertyChangeListener {

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
        piloto = new Piloto(9999);
        piloto.agregarPropertyChangeListener(this);
        piloto.start();
        btnDespegar.setDisable(true);
        btnIrAPuerta.setDisable(true);
    }

    @FXML
    void onActionIrAPuerta(ActionEvent a){
        nuevoEstado("Esperando aprobación");
        piloto.puerta();
        btnIrAPuerta.setDisable(true);
    }

    @FXML
    void onActionAterrizar(ActionEvent a) {
        nuevoEstado("Esperando aprobación");
        piloto.aterrizar();
        btnAterrizar.setDisable(true);
    }

    @FXML
    void onActionDespegar(ActionEvent a){
        nuevoEstado("Esperando aprobación");
        piloto.despegar();
        btnDespegar.setDisable(true);
    }

    public void setTextTXT(String txt){
        txtaDatos.setText(txt);
    }

    public void nuevoEstado(String mensaje){
        Platform.runLater(() -> {
            String textArea = txtaDatos.getText();
            String[] lineas = textArea.split("\n");
            lineas[3] = "Estado del avión: " + mensaje;
            String txtaDatos = String.join("\n", lineas);
            this.txtaDatos.setText(txtaDatos);
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String actualizar = (String) evt.getNewValue();
        switch (actualizar){
            case "despegar":
                btnIrAPuerta.setDisable(true);
                btnAterrizar.setDisable(false);
                btnDespegar.setDisable(true);
                nuevoEstado("En el aire");
                break;
            case "aterrizar":
                btnIrAPuerta.setDisable(false);
                btnAterrizar.setDisable(true);
                btnDespegar.setDisable(true);
                nuevoEstado("En pista");
                break;
            case "puerta":
                btnIrAPuerta.setDisable(true);
                btnAterrizar.setDisable(true);
                btnDespegar.setDisable(false);
                nuevoEstado("En puerta");
                break;
            case "aceptar aterrizar":
                nuevoEstado("Aterrizando");
                break;
            case "aceptar despegar":
                nuevoEstado("Despegando");
                break;
            case "aceptar puerta":
                nuevoEstado("En puerta");
                break;
            case "esperando":
                nuevoEstado("En espera");
                break;
        }
    }
}