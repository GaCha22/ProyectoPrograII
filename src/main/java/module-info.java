module cr.ac.ucr.paraiso.ie.progra2.gama {
    requires javafx.controls;
    requires javafx.fxml;


    opens cr.ac.ucr.paraiso.ie.progra2.gama to javafx.fxml;
    exports cr.ac.ucr.paraiso.ie.progra2.gama;
}