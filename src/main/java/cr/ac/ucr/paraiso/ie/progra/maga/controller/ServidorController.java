package cr.ac.ucr.paraiso.ie.progra.maga.controller;

import cr.ac.ucr.paraiso.ie.progra.maga.ClienteMain;
import cr.ac.ucr.paraiso.ie.progra.maga.model.Solicitud;
import cr.ac.ucr.paraiso.ie.progra.maga.service.GestionaArchivo;
import cr.ac.ucr.paraiso.ie.progra.maga.servidor.MultiServidor;
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
    private MultiServidor multiServidor;
    public static Stage stage;

    @FXML
    void initialize() throws IOException {
        //Se le da los valores al multiservidor que posee el config.json
        multiServidor = new MultiServidor(GestionaArchivo.leerArchivoConfiguracion("config.json"));
        multiServidor.agregarPropertyChangeListener(this);
        if (!multiServidor.isAlive()) multiServidor.start(); //Si el servidor no está en ejecución inicializa
        File archivo = new File("placasRegistradas.txt");
        //Se elimina el archivo placasRegistradas.txt si existe
        if (archivo.exists()) {
            archivo.delete();
        }
        archivo.createNewFile(); //De esta manera el archivo se limpia cuando el servidor se carga
        txtaPistasDisponibles.setText(MultiServidor.aeropuertoServer.pistasToString());
        txtaPuertasDisponibles.setText(MultiServidor.aeropuertoServer.puertasToString());
        setTable();
        setSolicitudSeleccionada();
    }

    //Se le da la funcionalidad al botón Aceptar Solicitud
    @FXML
    void onActionAceptarSolicitud(ActionEvent actionEvent) {
        //Si hay una solicitud en cola se acepta
        if(MultiServidor.getClientsInQueue().peek() != null) {
            MultiServidor.getClientsInQueue().peek().aceptarSolicitud();
            setSolicitudSeleccionada();
        }else { //Si no hay solicitudes se alerta al usuario
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("No haz seleccionado una petición");
            alert.showAndWait();
        }
    }

    //Se le da la funcionalidad al botón En Espera
    @FXML
    void onActionEnEsperar(ActionEvent actionEvent) {
        //Si hay una solicitud se pone en espera
        if(MultiServidor.getClientsInQueue().peek() != null)
            MultiServidor.getClientsInQueue().peek().ponerEnEspera();
        else { //Si no hay solicitudes se alerta al usuario
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("No haz seleccionado una petición");
            alert.showAndWait();
        }
    }

    //Método que carga la "escena" desde el documento FXML
    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(ClienteMain.class.getResource(page));
        try {
            stage = new Stage();
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Se le da la funcionalidad al botón Generar Reporte
    //Se muestran los registros que posee reporte.json
    @FXML
    void onActionGenerarReporte(ActionEvent actionEvent) {
        loadPage("interfaz/reporte.fxml");
    }

    //Método para dar los valores al TableView
    private void setTable(){
        TableColumn<List<String>, String> column = new TableColumn<>("Placa");
        column.setCellValueFactory(data->new SimpleStringProperty(data.getValue().get(0)));

        TableColumn<List<String>, String> column2 = new TableColumn<>("Aerolinea");
        column2.setCellValueFactory(data->new SimpleStringProperty(data.getValue().get(1)));

        TableColumn<List<String>, String> column3 = new TableColumn<>("Tipo");
        column3.setCellValueFactory(data->new SimpleStringProperty(data.getValue().get(2)));

        TableColumn<List<String>, String> column4 = new TableColumn<>("Solicitud");
        column4.setCellValueFactory(data->new SimpleStringProperty(data.getValue().get(3)));

        //Se agregan todas las columnas al TableView
        tblSolcitudes.getColumns().add(column);
        tblSolcitudes.getColumns().add(column2);
        tblSolcitudes.getColumns().add(column3);
        tblSolcitudes.getColumns().add(column4);
    }

    //Método en el cual se obtiene la información de la aeronave para enviarla al multiservidor
    public ObservableList<List<String>> getData() {
        ObservableList<List<String>> data = FXCollections.observableArrayList();
        int count = 0;
        List<String> info = new ArrayList<>();
        int ni = MultiServidor.getSolicitudesParaMostrar().size();
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

    //Método en el cual se le da valor al TextArea de la Solicitud Seleccionada
    private void setSolicitudSeleccionada(){
        tblSolcitudes.setItems(getData());
        //Si existe una solicitud la escribe en el TextArea
        if (MultiServidor.peekSolicitudInQueue()!=null) txtaSolicitudSeleccionada.setText(MultiServidor.peekSolicitudInQueue().getVuelo().getAeronave().getPlaca());
        else txtaSolicitudSeleccionada.setText("No hay solicitudes"); //Si no hay solicitudes lo indica
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String solicitud = (String) evt.getNewValue();
        //Si la solicitud es igual a "actualizar"
        if (solicitud.equals("actualizar")){
            //Se coloca la nueva solicitud
            setSolicitudSeleccionada();
            //Se muestran las pistas y puertas disponibles
            txtaPistasDisponibles.setText(MultiServidor.aeropuertoServer.pistasToString());
            txtaPuertasDisponibles.setText(MultiServidor.aeropuertoServer.puertasToString());
        }else if (solicitud.equals("consume")){}
    }
}
