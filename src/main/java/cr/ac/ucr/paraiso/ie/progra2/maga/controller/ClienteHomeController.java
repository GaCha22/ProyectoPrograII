package cr.ac.ucr.paraiso.ie.progra2.maga.controller;

import cr.ac.ucr.paraiso.ie.progra2.maga.ClienteMain;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Aeronave;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.CompaniaAerea;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Vuelo;
import cr.ac.ucr.paraiso.ie.progra2.maga.service.GestionaArchivo;
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
        if (archivo.exists()) {
            boolean x = archivo.delete();
        }
        ObservableList<String> items = FXCollections.observableArrayList("Avioneta", "Avión comercial", "Avión de carga");
        chbTipo.setItems(items);
        chbTipo.setValue("Tipo de avión");
        ObservableList<String> itemsAerolinea = FXCollections.observableArrayList("Avianca", "Iberia", "TACA");
        chbAerolinea.setItems(itemsAerolinea);
        chbAerolinea.setValue("Aerolíneas");
    }

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(ClienteMain.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
            clienteController = fxmlLoader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onActionGuardar(ActionEvent a) {
        if (!chbAerolinea.getValue().equals("Aerolíneas") && !chbTipo.getValue().equals("Tipo de avión") && !txtPlaca.getText().equals("")) {
            String placa= txtPlaca.getText();
            if (gestionaArchivo.buscarPlacaEnArchivo(placa,"placasRegistradas.txt")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error. La placa ya existe en la base de datos.");
                alert.setHeaderText(null);
                alert.setContentText("Ingrese una placa diferente");
                alert.showAndWait();
                txtPlaca.setText(""); //limpiar espacio
            } else {
                gestionaArchivo.registrarPlacas(placa,"placasRegistradas.txt");
                companiaAerea = new CompaniaAerea(chbAerolinea.getValue());
                int tipo = chbTipo.getValue().equals("Avioneta") ? 3 : chbTipo.getValue().equals("Avión comercial") ? 1 : 2;
                aeronave = new Aeronave(txtPlaca.getText(), tipo);
                Vuelo vuelo = new Vuelo(aeronave, companiaAerea);
                GestionaArchivo.escribirVueloenVuelos(vuelo, "vuelo.json");
                loadPage("interfaz/cliente.fxml");
                String tipoSt = aeronave.getTipo() == 1 ? "COMERCIAL" : aeronave.getTipo() == 2 ? "CARGA" : "AVIONETA";
                clienteController.setTextTXT("Placa: " + aeronave.getPlaca() +
                        "\nTipo: " + tipoSt +
                        "\nAerolínea: " + companiaAerea.getNombre() +
                        "\nEstado del avión: En el aire");
                File archivo = new File("vuelo.json");
                if (archivo.exists()) {
                    boolean x = archivo.delete();
                }
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error al guardar");
            alert.setHeaderText(null);
            alert.setContentText("Complete todos los datos para poder guardar");
            alert.showAndWait();
        }
    }
}