package cr.ac.ucr.paraiso.ie.progra.maga.controller;

import cr.ac.ucr.paraiso.ie.progra.maga.cliente.Piloto;
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

    //Se le da la funcionalidad al botón Ir A Puerta
    @FXML
    void onActionIrAPuerta(ActionEvent a){
        nuevoEstado("Esperando aprobación"); //Se cambia el estado del avión
        piloto.puerta();
        btnIrAPuerta.setDisable(true); //Se dehabilita el botón al usarse
    }

    //Se le da la funcionalidad al botón IAterrizar
    @FXML
    void onActionAterrizar(ActionEvent a) {
        nuevoEstado("Esperando aprobación"); //Se cambia el estado del avión
        piloto.aterrizar();
        btnAterrizar.setDisable(true); //Se dehabilita el botón al usarse
    }

    //Se le da la funcionalidad al botón Despegar
    @FXML
    void onActionDespegar(ActionEvent a){
        nuevoEstado("Esperando aprobación"); //Se cambia el estado del avión
        piloto.despegar();
        btnDespegar.setDisable(true); //Se dehabilita el botón al usarse
    }

    public void setTextTXT(String txt){
        txtaDatos.setText(txt);
    }

    //Método que permite que el cambio de estado del avión sea visible en el TextArea
    public void nuevoEstado(String mensaje){
        Platform.runLater(() -> {
            String textArea = txtaDatos.getText();
            String[] lineas = textArea.split("\n"); //Se hace un split a las líneas
            lineas[4] = mensaje.toUpperCase(); //Se le da el nuevo valor a la línea 4, que es la última
            String txtaDatos = String.join("\n", lineas); //Se incorpora las líneas
            this.txtaDatos.setText(txtaDatos); //Se le da el nuevo valor al TextArea
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //Se obtiene el nombre del evento
        String actualizar = (String) evt.getNewValue();
        //Conforme al nombre del evento se realizarán las acciones
        switch (actualizar){
            case "despegar":
                //Se deshabilitan y habilitan los botones según es necesario
                btnIrAPuerta.setDisable(true);
                btnAterrizar.setDisable(false);
                btnDespegar.setDisable(true);
                //Se cambia el estado del avión
                nuevoEstado("En el aire");
                break;
            case "aterrizar":
                //Se deshabilitan y habilitan los botones según es necesario
                btnIrAPuerta.setDisable(false);
                btnAterrizar.setDisable(true);
                btnDespegar.setDisable(true);
                //Se cambia el estado del avión
                nuevoEstado("En pista");
                break;
            case "puerta":
                //Se deshabilitan y habilitan los botones según es necesario
                btnIrAPuerta.setDisable(true);
                btnAterrizar.setDisable(true);
                btnDespegar.setDisable(false);
                //Se cambia el estado del avión
                nuevoEstado("Listo para despegar");
                break;
            case "aceptar aterrizar":
                nuevoEstado("Aterrizando"); //Se cambia el estado del avión
                break;
            case "aceptar despegar":
                nuevoEstado("Despegando"); //Se cambia el estado del avión
                break;
            case "aceptar puerta":
                nuevoEstado("En puerta"); //Se cambia el estado del avión
                break;
            case "esperando":
                nuevoEstado("Preparando avión"); //Se cambia el estado del avión
                break;
            case "lista de espera pista":
            case "lista de espera puerta":
                nuevoEstado("En lista de espera"); //Se cambia el estado del avión
                break;
            case "esperar":
                nuevoEstado("En espera"); //Se cambia el estado del avión
                break;
        }
    }
}