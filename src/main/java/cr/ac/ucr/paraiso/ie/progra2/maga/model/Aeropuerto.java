package cr.ac.ucr.paraiso.ie.progra2.maga.model;

import java.util.Arrays;
import java.util.List;

public class Aeropuerto {
    private Pista[] pistas;
    private Puerta[] puertas;
    private String nombre;

    public Aeropuerto() {
    }

    public Aeropuerto(String nombre) {
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
    
    public String pistasToString(){
        StringBuilder sb = new StringBuilder();
        for (Pista pista: pistas) {
            sb.append("Pista #").append(pista.getNumPista()).append(" ")
                    .append(pista.isDisponible()?"Disponible":"No Disponible")
                    .append("\n");
        }

        return sb.toString();
    }
    
    public String puertasToString(){
        StringBuilder sb = new StringBuilder();
        for (Puerta puerta: puertas) {
            sb.append("Puerta #").append(puerta.getNumPuerta()).append(" ")
                    .append(puerta.isDisponible()?"Disponible":"No Disponible")
                    .append("\n");
        }

        return sb.toString();
    }

    public void setDisponiblePista(int numPista, boolean disponible){
        for (Pista pista:pistas) {
            if(pista.getNumPista() == numPista){
                pista.setDisponible(disponible);
            }
        }
    }

    public void setDisponiblePuerta(int numPuerta, boolean disponible){
        for (Puerta puerta:puertas) {
            if(puerta.getNumPuerta() == numPuerta){
                puerta.setDisponible(disponible);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Aeropuerto{");
        sb.append("nombre=").append(nombre);
        sb.append(", pistas=[");
        for (Pista pista : pistas) {
            sb.append(pista.toString()).append(", ");
        }
        sb.setLength(sb.length() - 2);
        sb.append("]");
        sb.append(", puertas=[");
        for (Puerta puerta : puertas) {
            sb.append(puerta.toString()).append(", ");
        }
        sb.setLength(sb.length() - 2);
        sb.append("]");
        sb.append("}");
        return sb.toString();
    }


}