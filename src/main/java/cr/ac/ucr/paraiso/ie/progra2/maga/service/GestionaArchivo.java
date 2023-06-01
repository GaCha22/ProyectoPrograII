package cr.ac.ucr.paraiso.ie.progra2.maga.service;

import com.google.gson.GsonBuilder;
import cr.ac.ucr.paraiso.ie.progra2.maga.logic.Vuelo;
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
        Vuelo[] vuelos = gson.fromJson(jsonString, Vuelo[].class);

        for (Vuelo vuelo : vuelos) {
            salida += "\n" + vuelo.toString();
        }
        return salida;
    }

    public void escribirVuelo(Vuelo vuelo) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String path = "reportes.json";

        try (FileWriter fileWriter = new FileWriter(path)) {
            String json = gson.toJson(vuelo); // convierte objeto Java en cadena JSON
            fileWriter.write(json);
        } catch (IOException e) {
            throw new RuntimeException("Error al escribir el vuelo en el archivo: " + e.getMessage(), e);
        }
    }
}
