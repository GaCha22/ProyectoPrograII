package cr.ac.ucr.paraiso.ie.progra2.maga.controller;

import cr.ac.ucr.paraiso.ie.progra2.maga.service.GestionaArchivo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

public class ReporteController {
    @FXML
    private BorderPane bp;
    GestionaArchivo gestionaArchivo = new GestionaArchivo();
    @FXML
    private TextArea textReporte;

    @FXML
    void initialize(){
        textReporte.setText(GestionaArchivo.generarReporteVuelos("reportes.json"));
    }

    @FXML
    void cerrar(ActionEvent event) {
        ServidorController.stage.close();
    }

}
