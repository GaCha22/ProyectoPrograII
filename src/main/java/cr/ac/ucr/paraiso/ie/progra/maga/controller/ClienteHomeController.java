package cr.ac.ucr.paraiso.ie.progra.maga.controller;

import cr.ac.ucr.paraiso.ie.progra.maga.model.Aeronave;
import cr.ac.ucr.paraiso.ie.progra.maga.model.CompaniaAerea;
import cr.ac.ucr.paraiso.ie.progra.maga.model.Vuelo;
import cr.ac.ucr.paraiso.ie.progra.maga.service.GestionaArchivo;
import cr.ac.ucr.paraiso.ie.progra.maga.ClienteMain;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import java.io.File;
import java.io.IOException;

public class ClienteHomeController {

    @FXML
    private BorderPane bp;
    @FXML
    private ChoiceBox<String> chbAerolinea;
    @FXML
    private ChoiceBox<String> chbTipo;
    @FXML
    private TextField txtPlaca;
    private ClienteController clienteController;
    CompaniaAerea companiaAerea;
    Aeronave aeronave;
    GestionaArchivo gestionaArchivo = new GestionaArchivo();


    @FXML
    public void initialize() {
        File archivo = new File("vuelo.json");
        //Si el archivo vuelo.json existe se elimina al entrar
        if (archivo.exists()) {
            boolean x = archivo.delete();
        }
        //Se le da valor a los respectivos ChoiceBox
        ObservableList<String> items = FXCollections.observableArrayList("Avioneta", "Avión comercial", "Avión de carga");
        chbTipo.setItems(items);
        chbTipo.setValue("Tipo de avión");
        ObservableList<String> itemsAerolinea = FXCollections.observableArrayList("Avianca", "Iberia", "TACA", "American Airlines", "Qatar Airways");
        chbAerolinea.setItems(itemsAerolinea);
        chbAerolinea.setValue("Aerolíneas");
    }

    //Método que carga la "escena" desde el documento FXML
    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(ClienteMain.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
            clienteController = fxmlLoader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Se le da la funcionalidad al botón Guardar
    @FXML
    void onActionGuardar(ActionEvent a) {
        //Si algún dato no se ingresa, se alerta al usuario
        if (!chbAerolinea.getValue().equals("Aerolíneas") && !chbTipo.getValue().equals("Tipo de avión") && !txtPlaca.getText().equals("")) {
            String placa= txtPlaca.getText();
            //Si la placa es igual a una ya digitada se alerta al usuario
            if (gestionaArchivo.buscarPlacaEnArchivo(placa,"placasRegistradas.txt")) { //Se alerta al usuario que ingresó una placa registrada
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error. La placa ya existe en la base de datos.");
                alert.setHeaderText(null);
                alert.setContentText("Ingrese una placa diferente");
                alert.showAndWait();
                txtPlaca.setText(""); //Limpiar espacio
            } else { //Si todos los datos se ingresan correctamente
                //Se registra la placa del avión
                gestionaArchivo.registrarPlacas(placa,"placasRegistradas.txt");
                companiaAerea = new CompaniaAerea(chbAerolinea.getValue());
                //Se define el tipo de la aeronave
                int tipo = chbTipo.getValue().equals("Avioneta") ? 3 : chbTipo.getValue().equals("Avión comercial") ? 1 : 2;
                aeronave = new Aeronave(txtPlaca.getText(), tipo);
                Vuelo vuelo = new Vuelo(aeronave, companiaAerea);
                //Se escribe el vuelo en el archivo vuelo.json
                GestionaArchivo.escribirVueloenVuelos(vuelo, "vuelo.json");
                //Se carga la escena del cliente, donde se pueden realizar las acciones principales
                loadPage("interfaz/cliente.fxml");
                String tipoSt = aeronave.getTipo() == 1 ? "COMERCIAL" : aeronave.getTipo() == 2 ? "CARGA" : "AVIONETA";
                clienteController.setTextTXT("Placa: " + aeronave.getPlaca() +
                        "\nTipo: " + tipoSt +
                        "\nAerolínea: " + companiaAerea.getNombre() +
                        "\nEstado del avión:\nEN EL AIRE");
                File archivo = new File("vuelo.json");
                //Se elimina el archivo
                if (archivo.exists()) {
                    boolean x = archivo.delete();
                }
            }
        }else{ //Se alerta al usuario que no ingresó todos los datos
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error al guardar");
            alert.setHeaderText(null);
            alert.setContentText("Complete todos los datos para poder guardar");
            alert.showAndWait();
        }
    }
}