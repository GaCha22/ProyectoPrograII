import cr.ac.ucr.paraiso.ie.progra.maga.logic.GeneraRandoms;
import cr.ac.ucr.paraiso.ie.progra.maga.model.Aeronave;
import cr.ac.ucr.paraiso.ie.progra.maga.model.Aeropuerto;
import cr.ac.ucr.paraiso.ie.progra.maga.model.CompaniaAerea;
import cr.ac.ucr.paraiso.ie.progra.maga.model.Vuelo;


import cr.ac.ucr.paraiso.ie.progra.maga.service.GestionaArchivo;
import java.time.LocalTime;


public class Test {

    GestionaArchivo gestionaArchivo = new GestionaArchivo();
    GeneraRandoms gR = new GeneraRandoms();
    LocalTime horaSalida = LocalTime.now();
    LocalTime horaLlegada= LocalTime.now();
    Vuelo vuelo1 = new Vuelo(GeneraRandoms.getIdVuelo(), new Aeronave("CA52432", 2), new Aeropuerto(GeneraRandoms.generaAeropuertoRandom()), new Aeropuerto( "Juan Santamaria"),new CompaniaAerea("compañia 2"),false, horaSalida,horaLlegada);
    Vuelo vuelo2 = new Vuelo(GeneraRandoms.getIdVuelo(), new Aeronave("AV10953", 3), new Aeropuerto(GeneraRandoms.generaAeropuertoRandom()), new Aeropuerto( "El Dorado"),new CompaniaAerea("compañia 1"),false, horaSalida,horaLlegada);


    @org.junit.jupiter.api.Test
    public void escribir(){
        GestionaArchivo.escribirVuelo(vuelo1, "reportes.json");
        GestionaArchivo.escribirVuelo(vuelo2, "reportes.json");
    }


    @org.junit.jupiter.api.Test
    public void leerRegistro(){
        System.out.println(GestionaArchivo.generarReporteVuelos("reportes.json"));
    }

    @org.junit.jupiter.api.Test
    public void leerArchivoConfigFunciona(){
        String path = "config.json";
        //aeropuertoDestino
        System.out.println((GestionaArchivo.leerArchivoConfiguracion(path)).toString());
    }




    @org.junit.jupiter.api.Test
    public void generaRandomsFunciona(){
        System.out.println(GeneraRandoms.getIdVuelo());
        System.out.println(GeneraRandoms.generaAeropuertoRandom());
    }


}
