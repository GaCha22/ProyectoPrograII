package cr.ac.ucr.paraiso.ie.progra2.maga.model;

import java.util.List;

public class CompaniaAerea {

    private String nombre;

    private List<Aeronave> aeronaves;

    private List<Puerta> puertasAlquiladas;

    public CompaniaAerea(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}

