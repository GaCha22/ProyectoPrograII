package cr.ac.ucr.paraiso.ie.progra.maga.service;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

//Clase adaptadora para la serialización y deserialización de objetos LocalTime en formato JSON.
public class LocalTimeTypeAdapter implements JsonSerializer<LocalTime>, JsonDeserializer<LocalTime> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public JsonElement serialize(final LocalTime time, final Type typeOfSrc,
                                 final JsonSerializationContext context) {
        return new JsonPrimitive(time.format(formatter));
    }

    @Override
    public LocalTime deserialize(final JsonElement json, final Type typeOfT,
                                 final JsonDeserializationContext context) throws JsonParseException {
        return LocalTime.parse(json.getAsString(), formatter);
    }

}
