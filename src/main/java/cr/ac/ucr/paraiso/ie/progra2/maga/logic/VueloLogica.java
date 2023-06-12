package cr.ac.ucr.paraiso.ie.progra2.maga.logic;

import cr.ac.ucr.paraiso.ie.progra2.maga.model.*;

import static java.lang.Thread.sleep;

public class VueloLogica {

    //Se crean los estados de los aviones.
    private final int EN_ESPERA = 0;
    private final int  ATERRIZAR = 1;
    private final int PUERTA = 2;
    private final int DESPEGAR = 3;
    private Aeronave aeronave;
    public static Vuelo vuelo;

    public VueloLogica(Vuelo vuelo) {
        this.vuelo = vuelo;
    }

    public VueloLogica(){}

    public int estadoAeronave(int estado){
        switch (estado){
            case 0: //En Espera a Despegar
                if(vuelo.getAeropuertoDestino().pistasDisponibles()) { //Si el avión se encuentra en tierra y hay pistas disponibles puede despegar.
                    estado = DESPEGAR;
                }
                break;
            case 1: //Aterrizar a Ir a puerta
                if(vuelo.getAeropuertoDestino().puertasDisponibles()) { //Si el avión se encuentra en el aire y hay pistas disponibles puede aterrizar.
                    estado = PUERTA;
                }
                break;
            case 2: //Puerta a En espera
                if(vuelo.getAeropuertoDestino().puertasDisponibles()) { //Si el avión ya aterrizó y hay puertas disponibles puede despegar ir a puerta.
                    estado = EN_ESPERA;
                }
                break;
            case 3: //Despegar a Aterrizar
                if(vuelo.getAeropuertoDestino().pistasDisponibles()) { //Si el avión se encuentra en el aire y hay pistas disponibles puede aterrizar.
                    estado = ATERRIZAR;
                }
                break;
        }
        return estado;
    }

}