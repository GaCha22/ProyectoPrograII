package cr.ac.ucr.paraiso.ie.progra2.maga.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class ClienteHomeController
{
    @javafx.fxml.FXML
    private ChoiceBox chbAerolinea;
    @javafx.fxml.FXML
    private ChoiceBox chbTipo;
    @javafx.fxml.FXML
    private TextField txtPlaca;

    @javafx.fxml.FXML
    public void initialize() {
        ObservableList<String> items = FXCollections.observableArrayList("Avioneta", "Avión comercial", "Avión de carga");
        chbTipo.setItems(items);
        chbTipo.setValue("Tipo de avión");
    }}