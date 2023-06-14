package cr.ac.ucr.paraiso.ie.progra.maga.service;

import com.google.gson.GsonBuilder;
import cr.ac.ucr.paraiso.ie.progra.maga.model.Aeropuerto;
import cr.ac.ucr.paraiso.ie.progra.maga.model.Solicitud;
import cr.ac.ucr.paraiso.ie.progra.maga.model.Vuelo;
import com.google.gson.Gson;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GestionaArchivo {

    public static Aeropuerto leerArchivoConfiguracion(String path) { //Se lee el archivo de configuración del aeropuerto destino.
        // Se crea una instancia de Gson con un adaptador de tipo LocalTimeTypeAdapter existente.
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter())
                .create();
        Aeropuerto aeropuerto = null;
        try (FileReader reader = new FileReader(path)) {
            aeropuerto = gson.fromJson(reader, Aeropuerto.class); //El contenido del archivo json se convierte en un objeto aeropuerto.
        } catch (IOException e) {
            e.printStackTrace();
        }

        return aeropuerto; //Se retorna el objeto aeropuerto
    }

    public static String generarReporteVuelos(String path) {
        String jsonString; //Variable para almacenar el contenido del archivo JSON en formato de String

                String salida = ""; // String que contendrá el reporte de vuelos
        try {
            jsonString = new String(Files.readAllBytes(Paths.get(path))); //Lee el contenido del archivo en forma de bytes y lo convierte a un String.
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo: " + e.getMessage(), e); //Se lanza una excepción si hay un error al leer el archivo.
        }
        Gson gson = new GsonBuilder() //Intancia de Gson con LocalTimeTypeAdapter
                .registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter())
                .create();

        Vuelo[] vuelos = gson.fromJson(jsonString, Vuelo[].class); //Se convierte el contenido del archivo JSON en un array de objetos Vuelo


        if (vuelos == null) return "No hay reportes";
        for (Vuelo vuelo : vuelos) {
            salida +=  vuelo.toString()  + "\n\n" ; //Si existen vuelos entonces se concatena su información en la variable que se va a retornar, así se imprime el reporte.
        }
        return salida; //Se retorna el reporte de vuelos como un String.
    }

    public static String classAJson(Object solicitud){
        String json = null;
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter())
                .create();
        json = gson.toJson(solicitud);
        return json;
    }

    public static Solicitud jsonASolicitud(String json){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter())
                .create();

        Solicitud solicitud = null;
        solicitud =  gson.fromJson(json, Solicitud.class);

        return solicitud;
    }

    public static Vuelo leerVuelo(String path) { //Método que se encarga de leer un solo vuelo.
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter())
                .create();

        Vuelo vuelo = null;
        try (FileReader reader = new FileReader(path)) {
            vuelo =  gson.fromJson(reader, Vuelo.class); //Se convierte el contenido del archivo en un objeto Vuelo.
        } catch (IOException e) {
            e.printStackTrace();
        }

        return vuelo; //Se retorna el vuelo leído.
    }

    public static Vuelo jsonAVuelo(String json){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter())
                .create();

        Vuelo vuelo = null;
        vuelo =  gson.fromJson(json, Vuelo.class);

        return vuelo;
    }

    public static void escribirVueloenVuelos(Vuelo vuelo, String path) { //ya no debe ser una lista
        File file = new File(path);
        if (!file.exists()) {
            try {
                boolean b = file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter())
                .create();

        try (FileReader fileReader = new FileReader(path)) {
            Vuelo vueloExistente = gson.fromJson(fileReader, Vuelo.class); // Lee el vuelo existente del archivo

            try (FileWriter fileWriter = new FileWriter(path)) {
                String json = gson.toJson(vueloExistente != null ? vueloExistente : vuelo); // Convierte el vuelo existente o el nuevo vuelo en formato JSON
                fileWriter.write(json);
            } catch (IOException e) {
                throw new RuntimeException("Error al escribir el vuelo en el archivo: " + e.getMessage(), e);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el vuelo existente del archivo: " + e.getMessage(), e);
        }
    }

    public static void escribirVuelo(Vuelo vuelo, String path) {
        File file = new File(path);
        if (!file.exists()){
            try {
                boolean b = file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
// Se crea una instancia de Gson con un adaptador de tipo LocalTimeTypeAdapter
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter())
                .create();

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
            // Crea un BufferedWriter que permite escribir en el archivo especificado en modo (true), es decir que no borra el contenido existente del archivo
            BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo, true));

            // Escribe la placa en una nueva línea del archivo
            writer.write(placa);
            writer.newLine();

            // Cierra el BufferedWriter para asegurar que los cambios se guarden en el archivo
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean buscarPlacaEnArchivo(String placa, String nombreArchivo) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo)); //BufferedReader que permite leer el contenido del archivo
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.equals(placa)) { //Se compara cada línea del archivo con la placa buscada
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