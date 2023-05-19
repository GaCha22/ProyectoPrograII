package cr.ac.ucr.paraiso.ie.progra2.maga.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ClienteController {
    @FXML
    private Label welcomeText;

    @FXML
    void initialize(){

    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

}