

import cr.ac.ucr.paraiso.ie.progra2.maga.model.Aeronave;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Aeropuerto;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.CompaniaAerea;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Vuelo;


import cr.ac.ucr.paraiso.ie.progra2.maga.service.GestionaArchivo;

import java.time.LocalTime;

public class Test {

    GestionaArchivo gestionaArchivo = new GestionaArchivo();
     LocalTime horaSalida = LocalTime.now();
    LocalTime horaLlegada= LocalTime.now();
    Vuelo vuelo1 = new Vuelo("123",new Aeronave(2), new Aeropuerto(), new Aeropuerto(1,1, "Juan Santamaria"),new CompaniaAerea("compañia 2"),false, horaSalida,horaLlegada);
    Vuelo vuelo2 = new Vuelo("456",new Aeronave(3), new Aeropuerto(), new Aeropuerto(1,1, "El Dorado"),new CompaniaAerea("compañia 1"),false, horaSalida,horaLlegada);

    @org.junit.jupiter.api.Test
    public void escribir(){
        gestionaArchivo.escribirVuelo(vuelo1);
        gestionaArchivo.escribirVuelo(vuelo2);
    }

    @org.junit.jupiter.api.Test
    public void leerRegistro(){
        System.out.println(gestionaArchivo.generarReporteVuelos());
    }


}