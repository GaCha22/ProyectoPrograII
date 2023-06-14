package cr.ac.ucr.paraiso.ie.progra.maga.model;

public class CompaniaAerea {

    private String nombre;

    public CompaniaAerea(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "CompaniaAerea{" +
                "nombre='" + nombre + '\'' +
        '}';
    }
}