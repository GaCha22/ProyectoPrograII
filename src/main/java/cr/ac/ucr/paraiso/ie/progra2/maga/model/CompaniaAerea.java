package cr.ac.ucr.paraiso.ie.progra2.maga.model;

import java.util.List;

public class CompaniaAerea {

    private String nombre;

    private List<Aeronave> aeronaves;

    public CompaniaAerea(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "CompaniaAerea{" +
                "nombre='" + nombre + '\'' +
        '}';
    }
}