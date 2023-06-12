package cr.ac.ucr.paraiso.ie.progra2.maga.controller;

import cr.ac.ucr.paraiso.ie.progra2.maga.cliente.Piloto;
import cr.ac.ucr.paraiso.ie.progra2.maga.logic.VueloLogica;
import cr.ac.ucr.paraiso.ie.progra2.maga.service.GestionaArchivo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;


public class ReporteController {
    private Button close;
    GestionaArchivo gestionaArchivo = new GestionaArchivo();
    @FXML
    private TextArea textReporte;

    @FXML
    void initialize(){
        textReporte.setText(gestionaArchivo.generarReporteVuelos("reportes.json"));
    }

    @FXML
    void cerrar(ActionEvent event) {

    }

}
