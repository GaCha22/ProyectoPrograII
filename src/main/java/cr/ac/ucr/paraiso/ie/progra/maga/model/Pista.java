package cr.ac.ucr.paraiso.ie.progra.maga.model;

public class Pista {

    private boolean disponible;
    private int numPista;

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public int getNumPista() {
        return numPista;
    }

    @Override
    public String toString() {
        return "Pista{" +
                "numPista=" + numPista +
                '}';
    }
}