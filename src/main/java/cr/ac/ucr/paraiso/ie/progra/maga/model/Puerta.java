package cr.ac.ucr.paraiso.ie.progra.maga.model;

public class Puerta {

    private int numPuerta;
    private boolean disponible;
    private CompaniaAerea companiaAerea;

    public Puerta() {}

    public int getNumPuerta() {
        return numPuerta;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return "Puerta{" +
                "numPuerta=" + numPuerta +
                ", companiaAerea=" + companiaAerea +
                '}';
    }
}