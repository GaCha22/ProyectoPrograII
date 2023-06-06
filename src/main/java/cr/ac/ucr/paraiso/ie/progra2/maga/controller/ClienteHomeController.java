package cr.ac.ucr.paraiso.ie.progra2.maga.controller;

import cr.ac.ucr.paraiso.ie.progra2.maga.ClienteMain;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Aeronave;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.CompaniaAerea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class ClienteHomeController {
    @javafx.fxml.FXML
    private BorderPane bp;
    @javafx.fxml.FXML
    private Label lbAerolinea;
    @javafx.fxml.FXML
    private Label lbPlacaAvion;
    @javafx.fxml.FXML
    private Label lbTipoAvion;
    @javafx.fxml.FXML
    private ChoiceBox<String> chbAerolinea;
    @javafx.fxml.FXML
    private ChoiceBox<String> chbTipo;
    @javafx.fxml.FXML
    private TextField txtPlaca;
    @javafx.fxml.FXML
    private Button btnGuardar;
    private ClienteController clienteController;
    CompaniaAerea companiaAerea;
    Aeronave aeronave;

    @javafx.fxml.FXML
    public void initialize() {
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

    @javafx.fxml.FXML
    void onActionGuardar(ActionEvent a) {
        if (!chbAerolinea.getValue().equals("Aerolíneas") && !chbTipo.getValue().equals("Tipo de avión") && !txtPlaca.equals("")) {
            companiaAerea = new CompaniaAerea(chbAerolinea.getValue());
            int tipo = chbTipo.getValue().equals("Avioneta") ? 3 : chbTipo.getValue().equals("Avión comercial") ? 1 : 2;
            aeronave = new Aeronave(txtPlaca.getText(), tipo);
            loadPage("interfaz/cliente.fxml");
            clienteController.setVuelo(aeronave, companiaAerea);
            clienteController.setTextTXT("Tipo: " + aeronave.getTipo() +
                    "\nPlaca: " + aeronave.getPlaca() +
                    "\nAerolínea: " + companiaAerea.getNombre());
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error al guardar");
            alert.setHeaderText(null);
            alert.setContentText("Ocurrió un error al intentar guardar los datos.");
            alert.showAndWait();
        }
    }
}