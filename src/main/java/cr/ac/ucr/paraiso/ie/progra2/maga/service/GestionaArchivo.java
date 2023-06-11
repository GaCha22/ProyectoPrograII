package cr.ac.ucr.paraiso.ie.progra2.maga.service;

import com.google.gson.GsonBuilder;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Aeropuerto;
import com.google.gson.Gson;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Vuelo;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GestionaArchivo {


    public static Aeropuerto leerArchivoConfiguracion(String path) {

        Gson gson = new Gson();
        Aeropuerto aeropuerto = null;
        try (FileReader reader = new FileReader(path)) {

            aeropuerto = gson.fromJson(reader, Aeropuerto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return aeropuerto;
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
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter())
                .create();

        Vuelo[] vuelos = gson.fromJson(jsonString, Vuelo[].class);


        for (Vuelo vuelo : vuelos) {
            salida += "\n" + vuelo.toString();
        }
        return salida;
    }

    public void escribirVuelo(Vuelo vuelo) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter())
                .create();
        String path = "reportes.json";

        try (FileReader fileReader = new FileReader(path)) {
            Vuelo[] vuelosExistentes = gson.fromJson(fileReader, Vuelo[].class); // Lee los vuelos existentes del archivo
            List<Vuelo> listaVuelos;

            if (vuelosExistentes != null) {
                listaVuelos = new ArrayList<>(Arrays.asList(vuelosExistentes)); // Convierte el arreglo en una lista
            } else {
                listaVuelos = new ArrayList<>(); // Crea una nueva lista si no hay vuelos existentes
            }

            listaVuelos.add(vuelo); // Agrega el nuevo vuelo a la lista

            try (FileWriter fileWriter = new FileWriter(path)) {
                String json = gson.toJson(listaVuelos.toArray()); // Convierte la lista de vuelos en un arreglo JSON
                fileWriter.write(json);
            } catch (IOException e) {
                throw new RuntimeException("Error al escribir los vuelos en el archivo: " + e.getMessage(), e);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al leer los vuelos existentes del archivo: " + e.getMessage(), e);
        }
    }


    // archivos txt
    public void registrarPlacas(String placa, String nombreArchivo) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo, true));
            writer.write(placa);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean buscarPlacaEnArchivo(String placa, String nombreArchivo) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo));
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.equals(placa)) {
                    reader.close();
                    return true; // La placa se encontró en el archivo
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // La placa no se encontró en el archivo
    }
}
