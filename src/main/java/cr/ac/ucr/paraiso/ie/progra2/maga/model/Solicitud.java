package cr.ac.ucr.paraiso.ie.progra2.maga.model;

public class Solicitud {
    private Vuelo vuelo;
    private String solicitud;

    public Solicitud(Vuelo vuelo, String solicitud) {
        this.vuelo = vuelo;
        this.solicitud = solicitud;
    }

    public Vuelo getVuelo() {
        return vuelo;
    }

    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }

    public String getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(String solicitud) {
        this.solicitud = solicitud;
    }
}
