package cr.ac.ucr.paraiso.ie.progra2.maga.model;

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

    public void setNumPista(int numPista) {
        this.numPista = numPista;
    }

    @Override
    public String toString() {
        return "Pista{" +
                "numPista=" + numPista +
                '}';
    }
}