package cr.ac.ucr.paraiso.ie.progra.maga.controller;

import cr.ac.ucr.paraiso.ie.progra.maga.service.GestionaArchivo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

public class ReporteController {
    @FXML
    private TextArea textReporte;

    //Se muestran los registros del archivo reportes.json
    @FXML
    void initialize(){
        textReporte.setText(GestionaArchivo.generarReporteVuelos("reportes.json"));
    }

    @FXML
    void cerrar(ActionEvent event) {
        ServidorController.stage.close();
    }

}
