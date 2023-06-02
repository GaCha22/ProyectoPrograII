import cr.ac.ucr.paraiso.ie.progra2.maga.service.GestionaArchivo;

public class Test {
    @org.junit.jupiter.api.Test
    void test(){
        GestionaArchivo gestionaArchivo = new GestionaArchivo();
        String txt = gestionaArchivo.leerArchivo("config.json");
        System.out.println(txt);
    }
}
