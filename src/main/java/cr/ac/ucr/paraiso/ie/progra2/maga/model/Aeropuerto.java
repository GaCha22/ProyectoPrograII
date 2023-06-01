package cr.ac.ucr.paraiso.ie.progra2.maga.model;

import java.util.List;

public class Aeropuerto {

    private List<Pista> pistas;
    private List<Puerta> puertas;

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

    public List<Pista> getPistas() {
        return pistas;
    }

    public void setPistas(List<Pista> pistas) {
        this.pistas = pistas;
    }

    public List<Puerta> getPuertas() {
        return puertas;
    }

    public void setPuertas(List<Puerta> puertas) {
        this.puertas = puertas;
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

}
