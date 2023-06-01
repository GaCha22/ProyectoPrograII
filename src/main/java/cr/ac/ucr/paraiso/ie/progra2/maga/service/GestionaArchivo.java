package cr.ac.ucr.paraiso.ie.progra2.maga.service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Pista;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Puerta;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class GestionaArchivo {

    public void leerArchivo() {

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Leer el archivo JSON y convertirlo en un objeto raíz
            File file = new File("config.json");

            JsonNode rootNode = objectMapper.readTree(file);

            // Obtener el arreglo de pistas
            JsonNode pistasNode = rootNode.get("pistas");
            List<Pista> pistas = objectMapper.convertValue(pistasNode, new TypeReference<List<Pista>>() {});

            JsonNode puertasNode = rootNode.get("puertas");
            List<Puerta> puertas = objectMapper.convertValue(puertasNode, new TypeReference<List<Puerta>>() {});


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}