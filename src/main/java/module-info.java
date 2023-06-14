module cr.ac.ucr.paraiso.ie.progra.gama {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.desktop;
    exports cr.ac.ucr.paraiso.ie.progra.maga.logic;
    exports cr.ac.ucr.paraiso.ie.progra.maga.service;
    exports cr.ac.ucr.paraiso.ie.progra.maga.controller;
    exports cr.ac.ucr.paraiso.ie.progra.maga.model;
    opens cr.ac.ucr.paraiso.ie.progra.maga.controller to javafx.fxml;
    opens cr.ac.ucr.paraiso.ie.progra.maga.model to com.google.gson;
    exports cr.ac.ucr.paraiso.ie.progra.maga;
    opens cr.ac.ucr.paraiso.ie.progra.maga to javafx.fxml;
}