package cr.ac.ucr.paraiso.ie.progra2.maga.model;

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
