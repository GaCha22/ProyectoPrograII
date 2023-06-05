package cr.ac.ucr.paraiso.ie.progra2.maga.model;

import java.util.List;

public class Aeropuerto {

    private Pista[] pistas;
    private Puerta[] puertas;

    public Aeropuerto() {
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

    public boolean  puertasDisponibles(){
        boolean puertaDisponible = false;
        for(Puerta puerta : puertas){
            if(puerta.isDisponible()){
                puertaDisponible = true;
            }
        }
        return puertaDisponible;
    }

}
