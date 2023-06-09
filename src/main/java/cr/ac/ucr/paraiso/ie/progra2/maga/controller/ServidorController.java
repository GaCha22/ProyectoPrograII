package cr.ac.ucr.paraiso.ie.progra2.maga.controller;

import cr.ac.ucr.paraiso.ie.progra2.maga.model.Aeronave;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Aeropuerto;
import cr.ac.ucr.paraiso.ie.progra2.maga.service.GestionaArchivo;
import cr.ac.ucr.paraiso.ie.progra2.maga.servidor.MultiServidor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class ServidorController {


    @FXML
    public TextArea txtaSolicitudSeleccionada;
    @FXML
    public TextArea txtaPistasDisponibles;
    @FXML
    public TextArea txtaPuertasDisponibles;
    @FXML
    private Button btnGenerarReporte;
    @FXML
    private Button btnAceptarSolicitud;
    @FXML
    private Button btnPonerEnEspera;

    @FXML
    private Button btnMostrarAterrizando;

    @FXML
    private Button btnMostrarEnEspera;

    @FXML
    private Button btnMostrarEnPuerta;

    @FXML
    private Button btnMostrarTodos;
    private MultiServidor multiServidor;

    private Aeropuerto aeropuerto;

    @FXML
    void initialize(){
        multiServidor = new MultiServidor();
        multiServidor.start();
        aeropuerto = GestionaArchivo.leerArchivoConfiguracion("config.json");
        txtaPistasDisponibles.setText(aeropuerto.pistasToString());
        txtaPuertasDisponibles.setText(aeropuerto.puertasToString());
    }

    @FXML
    void onActionEnEsperar(ActionEvent actionEvent) {
    }

    @FXML
    void onActionAceptarSolicitud(ActionEvent actionEvent) {
    }

    @FXML
    void onActionGenerarReporte(ActionEvent actionEvent) {
    }

    @FXML
    void onActionMostrarAterrizando(ActionEvent event) {
        setDisableAll(false);
        btnMostrarAterrizando.setDisable(true);
    }

    @FXML
    void onActionMostrarEnEspera(ActionEvent event) {
        setDisableAll(false);
        btnMostrarEnEspera.setDisable(true);
    }

    @FXML
    void onActionMostrarEnPuerta(ActionEvent event) {
        setDisableAll(false);
        btnMostrarEnPuerta.setDisable(true);
    }

    @FXML
    void onActionMostrarTodos(ActionEvent event) {
        setDisableAll(false);
        btnMostrarTodos.setDisable(true);
    }

    private void setDisableAll(boolean option){
        btnMostrarAterrizando.setDisable(option);
        btnMostrarTodos.setDisable(option);
        btnMostrarEnEspera.setDisable(option);
        btnMostrarEnPuerta.setDisable(option);
    }

}
