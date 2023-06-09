module cr.ac.ucr.paraiso.ie.progra2.gama {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    exports cr.ac.ucr.paraiso.ie.progra2.maga.logic;
    exports cr.ac.ucr.paraiso.ie.progra2.maga.service;
    exports cr.ac.ucr.paraiso.ie.progra2.maga.controller;
    exports cr.ac.ucr.paraiso.ie.progra2.maga.model;
    opens cr.ac.ucr.paraiso.ie.progra2.maga.controller to javafx.fxml;
    opens cr.ac.ucr.paraiso.ie.progra2.maga.model to com.google.gson;
    exports cr.ac.ucr.paraiso.ie.progra2.maga;
    opens cr.ac.ucr.paraiso.ie.progra2.maga to javafx.fxml;
}