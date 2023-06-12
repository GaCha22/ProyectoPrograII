package cr.ac.ucr.paraiso.ie.progra2.maga.controller;

import cr.ac.ucr.paraiso.ie.progra2.maga.ClienteMain;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Aeronave;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Aeropuerto;
import cr.ac.ucr.paraiso.ie.progra2.maga.service.GestionaArchivo;
import cr.ac.ucr.paraiso.ie.progra2.maga.servidor.MultiServidor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

public class ServidorController {

    @FXML
    private BorderPane bp;
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

    private ReporteController reporteController;
    @FXML
    void initialize() throws IOException {
        multiServidor = new MultiServidor(GestionaArchivo.leerArchivoConfiguracion("config.json"));
        multiServidor.start();
        File archivo = new File("placasRegistradas.txt");
        if (archivo.exists()) {
            archivo.delete();
        }
        archivo.createNewFile(); //de esta manera el archivo se limpia cuando el servidor se carga
        txtaPistasDisponibles.setText(MultiServidor.aeropuertoServer.pistasToString());
        txtaPuertasDisponibles.setText(MultiServidor.aeropuertoServer.puertasToString());
    }

    @FXML
    void onActionAceptarSolicitud(ActionEvent actionEvent) {
        if(MultiServidor.getClientsInQueue().peek() != null)
            MultiServidor.getClientsInQueue().peek().aceptarSolicitud();
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("No haz seleccionado una petición");
            alert.showAndWait();
        }
    }

    @FXML
    void onActionEnEsperar(ActionEvent actionEvent) {
        if(MultiServidor.getClientsInQueue().peek() != null)
            MultiServidor.getClientsInQueue().peek().ponerEnEspera();
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("No haz seleccionado una petición");
            alert.showAndWait();
        }
    }

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(ClienteMain.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
            reporteController = fxmlLoader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void onActionGenerarReporte(ActionEvent actionEvent) {
        loadPage("interfaz/reporte.fxml");
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
