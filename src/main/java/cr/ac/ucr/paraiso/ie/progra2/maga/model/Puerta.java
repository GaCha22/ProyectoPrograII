package cr.ac.ucr.paraiso.ie.progra2.maga.model;

public class Puerta {

    public int numPuerta;

    public boolean disponible;

    public Puerta() {
    }

    public int getNumPuerta() {
        return numPuerta;
    }

    public void setNumPuerta(int numPuerta) {
        this.numPuerta = numPuerta;
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
                '}';
    }
}
