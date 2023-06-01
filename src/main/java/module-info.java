module cr.ac.ucr.paraiso.ie.progra2.gama {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;


    exports cr.ac.ucr.paraiso.ie.progra2.maga.controller;
    opens cr.ac.ucr.paraiso.ie.progra2.maga.controller to javafx.fxml;
    exports cr.ac.ucr.paraiso.ie.progra2.maga;
    opens cr.ac.ucr.paraiso.ie.progra2.maga to javafx.fxml;
}