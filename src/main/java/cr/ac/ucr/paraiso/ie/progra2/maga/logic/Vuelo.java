package cr.ac.ucr.paraiso.ie.progra2.maga.logic;

import cr.ac.ucr.paraiso.ie.progra2.maga.model.Aeropuerto;

public class Vuelo {

    //Se crean los estados de los aviones.
    private final int EN_ESPERA = 1;
    private final int  ATERRIZAR = 2;
    private final int PUERTA = 3;
    private final int DESPEGAR = 4;
    private int estado = 0;

    Aeropuerto aeropuerto = new Aeropuerto();

    //Método en la cual se le indica al avión que despegue.
    public int despegar (){
        if(this.estado == EN_ESPERA && aeropuerto.pistasDisponibles()) { //Si el avión se encuentra en tierra y hay pistas disponibles puede despegar.
            this.estado = DESPEGAR;
        }
        return estado;
    }

    //Método en la cual se le indica al avión que aterrice.
    public int aterrizar (){
        if(this.estado == DESPEGAR && aeropuerto.pistasDisponibles()){ //Si el avión se encuentra en el aire y hay pistas disponibles puede aterrizar.
            this.estado = ATERRIZAR;
        }
        return estado;
    }

    //Método en la cual se le indica al avión que aterrice.
    public int irAPuerta (){ //Si el avión ya aterrizó y hay puertas disponibles puede despegar ir a puerta.
        if(this.estado == ATERRIZAR && aeropuerto.puertasDisponibles()) {
            this.estado = PUERTA;
        }
        return estado;
    }

    //Método en la cual se le indica al avión que vaya a puerta.
    public int enEspera (){
        this.estado = EN_ESPERA;
        return estado;
    }

}
