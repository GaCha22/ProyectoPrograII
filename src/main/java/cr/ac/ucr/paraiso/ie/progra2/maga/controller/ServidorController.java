package cr.ac.ucr.paraiso.ie.progra2.maga.controller;

import cr.ac.ucr.paraiso.ie.progra2.maga.ClienteMain;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Solicitud;
import cr.ac.ucr.paraiso.ie.progra2.maga.service.GestionaArchivo;
import cr.ac.ucr.paraiso.ie.progra2.maga.servidor.MultiServidor;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class ServidorController implements PropertyChangeListener{

    @FXML
    private BorderPane bp;
    @FXML
    private TableView tblSolcitudes;
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
    public static Stage stage;
    @FXML
    void initialize() throws IOException {
        multiServidor = new MultiServidor(GestionaArchivo.leerArchivoConfiguracion("config.json"));
        multiServidor.agregarPropertyChangeListener(this);
        if (!multiServidor.isAlive()) multiServidor.start();
        File archivo = new File("placasRegistradas.txt");
        if (archivo.exists()) {
            archivo.delete();
        }
        archivo.createNewFile(); //de esta manera el archivo se limpia cuando el servidor se carga
        txtaPistasDisponibles.setText(MultiServidor.aeropuertoServer.pistasToString());
        txtaPuertasDisponibles.setText(MultiServidor.aeropuertoServer.puertasToString());
        setTable();
        setSolicitudSeleccionada();
    }

    @FXML
    void onActionAceptarSolicitud(ActionEvent actionEvent) {
        if(MultiServidor.getClientsInQueue().peek() != null) {
            MultiServidor.getClientsInQueue().peek().aceptarSolicitud();
            MultiServidor.removeSolicitudInQueue();
            setSolicitudSeleccionada();
        }else {
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
            stage = new Stage();
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
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

    private void setTable(){
        TableColumn<List<String>, String> column = new TableColumn<>("Placa");
        column.setCellValueFactory(data->new SimpleStringProperty(data.getValue().get(0)));

        TableColumn<List<String>, String> column2 = new TableColumn<>("Aerolinea");
        column2.setCellValueFactory(data->new SimpleStringProperty(data.getValue().get(1)));

        TableColumn<List<String>, String> column3 = new TableColumn<>("Tipo");
        column3.setCellValueFactory(data->new SimpleStringProperty(data.getValue().get(2)));

        TableColumn<List<String>, String> column4 = new TableColumn<>("Solicitud");
        column4.setCellValueFactory(data->new SimpleStringProperty(data.getValue().get(3)));

        tblSolcitudes.getColumns().add(column);
        tblSolcitudes.getColumns().add(column2);
        tblSolcitudes.getColumns().add(column3);
        tblSolcitudes.getColumns().add(column4);
    }

    public ObservableList<List<String>> getData() {
        ObservableList<List<String>> data = FXCollections.observableArrayList();
        int count = 0;
        List<String> info = new ArrayList<>();
        int ni = MultiServidor.getSolicitudes().size();
        int i = 0;
        while (i < ni){
            Solicitud solicitud = MultiServidor.removeSolicitudInQueue();
            info.add(solicitud.getVuelo().getAeronave().getPlaca());
            info.add(solicitud.getVuelo().getCompaniaAerea().getNombre().toUpperCase());
            info.add(solicitud.getVuelo().getAeronave().getTipo() == 1? "COMERCIAL": solicitud.getVuelo().getAeronave().getTipo() == 2? "CARGA":"AVIONETA");
            info.add(solicitud.getSolicitud().toUpperCase());
            data.add(info);
            MultiServidor.addSolicitudesInQueue(solicitud);
            info = new ArrayList<>();
            i++;
        }
        return data;
    }

    private void setSolicitudSeleccionada(){
        tblSolcitudes.setItems(getData());
        if (MultiServidor.peekSolicitudInQueue()!=null) txtaSolicitudSeleccionada.setText(MultiServidor.peekSolicitudInQueue().getVuelo().getAeronave().getPlaca());
        else txtaSolicitudSeleccionada.setText("No hay solicitudes");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String solicitud = (String) evt.getNewValue();
        if (solicitud.equals("actualizar")){
            setSolicitudSeleccionada();
            txtaPistasDisponibles.setText(MultiServidor.aeropuertoServer.pistasToString());
            txtaPuertasDisponibles.setText(MultiServidor.aeropuertoServer.puertasToString());
        }else if (solicitud.equals("consume")){}
    }
}
