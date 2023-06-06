package cr.ac.ucr.paraiso.ie.progra2.maga.model;

import java.util.Arrays;
import java.util.List;

public class Aeropuerto {

    private Pista[] pistas;
    private Puerta[] puertas;
    private int numPistas;
    private int numPuertas;

    private String nombre;

    public Aeropuerto() {
    }

    public Aeropuerto(int numPistas, int numPuertas, String nombre) {
        this.numPistas = numPistas;
        this.numPuertas = numPuertas;
        this.nombre = nombre;
    }

    public boolean pistasDisponibles(){
        boolean despegar = false;

        for(Pista pista : pistas){
            if(pista.isDisponible()){
                despegar = true;
            }
        }
        return despegar;
    }

    public boolean puertasDisponibles(){
        boolean puertaDisponible = false;
        for(Puerta puerta : puertas){
            if(puerta.isDisponible()){
                puertaDisponible = true;
            }
        }
        return puertaDisponible;
    }

    public Pista[] getPistas() {
        return pistas;
    }

    public void setPistas(Pista[] pistas) {
        this.pistas = pistas;
    }

    public Puerta[] getPuertas() {
        return puertas;
    }

    public void setPuertas(Puerta[] puertas) {
        this.puertas = puertas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Aeropuerto{" +
                "pistas=" + Arrays.toString(pistas) +
                ", puertas=" + Arrays.toString(puertas) +
                '}';
    }


}
