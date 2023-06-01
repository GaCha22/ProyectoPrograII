package cr.ac.ucr.paraiso.ie.progra2.maga.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

//https://github.com/fangyidong/json-simple , usar esta libreria?

public class GestionaArchivo {

        public void leerArchivo()
        {
            JSONParser parser = new JSONParser();
            FileReader fileReader;
            try {
                File file = new File("config.json");
                fileReader = new FileReader(file);

                JSONArray array = (JSONArray) parser.parse(fileReader);


                for(int i=0;i<array.size();i++)
                {
                    JSONObject obj =  (JSONObject)array.get(i);
                    parseObject(obj);
                }

            }

            catch(FileNotFoundException e)
            {
                System.out.println(e.getStackTrace()+ " :File Not Found");
            }
            catch(ParseException e)
            {
                System.out.println(e.getStackTrace()+ " :Could not parse");
            }
            catch(IOException e)
            {
                System.out.println(e.getStackTrace()+ " :IOException");
            }
        }

    private void parseObject(JSONObject obj) {
        JSONArray pistas= (JSONArray) obj.get("pistas");
        JSONArray puertas = (JSONArray) obj.get("puertas");
    }

}