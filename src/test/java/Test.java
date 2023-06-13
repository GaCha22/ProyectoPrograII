import cr.ac.ucr.paraiso.ie.progra2.maga.logic.GeneraRandoms;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Aeronave;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Aeropuerto;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.CompaniaAerea;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Vuelo;


import cr.ac.ucr.paraiso.ie.progra2.maga.service.GestionaArchivo;
import java.time.LocalTime;


public class Test {

    GestionaArchivo gestionaArchivo = new GestionaArchivo();
    GeneraRandoms gR = new GeneraRandoms();
    LocalTime horaSalida = LocalTime.now();
    LocalTime horaLlegada= LocalTime.now();
    Vuelo vuelo1 = new Vuelo("123",new Aeronave(2), new Aeropuerto(), new Aeropuerto( "Juan Santamaria"),new CompaniaAerea("compañia 2"),false, horaSalida,horaLlegada);
    Vuelo vuelo2 = new Vuelo("456",new Aeronave(3), new Aeropuerto(), new Aeropuerto( "El Dorado"),new CompaniaAerea("compañia 1"),false, horaSalida,horaLlegada);


    @org.junit.jupiter.api.Test
    public void escribir(){
        gestionaArchivo.escribirVuelo(vuelo1, "reportes.json");
        gestionaArchivo.escribirVuelo(vuelo2, "reportes.json");
    }


    @org.junit.jupiter.api.Test
    public void leerRegistro(){
        System.out.println(gestionaArchivo.generarReporteVuelos("reportes.json"));
    }

    @org.junit.jupiter.api.Test
    public void leerArchivoConfigFunciona(){
        String path = "config.json";
        //aeropuertoDestino
        System.out.println((gestionaArchivo.leerArchivoConfiguracion(path)).toString());
    }




    @org.junit.jupiter.api.Test
    public void generaRandomsFunciona(){
        System.out.println(gR.getIdVuelo());
        System.out.println(gR.getAeropuertoOrigen());
    }



}
