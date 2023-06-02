package cr.ac.ucr.paraiso.ie.progra2.maga.service;

import com.google.gson.GsonBuilder;
import cr.ac.ucr.paraiso.ie.progra2.maga.logic.VueloLogica;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Pista;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Puerta;
import com.google.gson.Gson;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GestionaArchivo {
    private Pista[] pistas;
    private Puerta[] puertas;
    public void leerArchivoConfig() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader("config.json")) {
            GestionaArchivo archivo = gson.fromJson(reader, GestionaArchivo.class);
            this.pistas = archivo.getPistas();
            this.puertas = archivo.getPuertas();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Pista[] getPistas() {
        return pistas;
    }

    public Puerta[] getPuertas() {
        return puertas;
    }

    public String generarReporteVuelos() {
        String jsonString;
        String salida = "";
        String path = "reportes.json";
        try {
            jsonString = new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo: " + e.getMessage(), e);
        }

        Gson gson = new GsonBuilder().create();
        VueloLogica[] vueloLogicas = gson.fromJson(jsonString, VueloLogica[].class);

        for (VueloLogica vueloLogica : vueloLogicas) {
            salida += "\n" + vueloLogica.toString();
        }
        return salida;
    }

    public void escribirVuelo(VueloLogica vueloLogica) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String path = "reportes.json";

        try (FileWriter fileWriter = new FileWriter(path)) {
            String json = gson.toJson(vueloLogica); // convierte objeto Java en cadena JSON
            fileWriter.write(json);
        } catch (IOException e) {
            throw new RuntimeException("Error al escribir el vuelo en el archivo: " + e.getMessage(), e);
        }
    }
}
