package cr.ac.ucr.paraiso.ie.progra2.maga.model;

public class Puerta {

    private int numPuerta;

    private boolean disponible;

    private CompaniaAerea companiaAerea;
    public Puerta() {
    }

    public void cantidadPuertas(){
        
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

    public CompaniaAerea getCompaniaAerea() {
        return companiaAerea;
    }

    public void setCompaniaAerea(CompaniaAerea companiaAerea) {
        this.companiaAerea = companiaAerea;
    }

    @Override
    public String toString() {
        return "Puerta{" +
                "numPuerta=" + numPuerta +
                ", companiaAerea=" + companiaAerea +
                '}';
    }
}
