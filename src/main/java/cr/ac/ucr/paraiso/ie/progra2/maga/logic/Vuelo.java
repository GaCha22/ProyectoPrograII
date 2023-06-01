package cr.ac.ucr.paraiso.ie.progra2.maga.logic;

import cr.ac.ucr.paraiso.ie.progra2.maga.model.Aeronave;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Aeropuerto;

public class Vuelo {

    //Se crean los estados de los aviones.
    private final int EN_ESPERA = 0;
    private final int  ATERRIZAR = 1;
    private final int PUERTA = 2;
    private final int DESPEGAR = 3;

    private Aeronave aeronave;

    Aeropuerto aeropuertoOrigen = new Aeropuerto();
    Aeropuerto aeropuertoDestino = new Aeropuerto();

    public Vuelo(int estado, Aeronave aeronave, Aeropuerto aeropuertoOrigen, Aeropuerto aeropuertoDestino) {
        this.estado = estado;
        this.aeronave = aeronave;
        this.aeropuertoOrigen = aeropuertoOrigen;
        this.aeropuertoDestino = aeropuertoDestino;
    }


    public int estadoAeronave(int estado){
        switch (estado){
            case 0: //En Espera a Despegar
                if(aeropuerto.pistasDisponibles()) { //Si el avión se encuentra en tierra y hay pistas disponibles puede despegar.
                    estado = DESPEGAR;
                }
                break;
            case 1: //Aterrizar a Ir a puerta
                if(aeropuerto.pistasDisponibles()) { //Si el avión se encuentra en el aire y hay pistas disponibles puede aterrizar.
                    estado = PUERTA;
                }
                break;
            case 2: //Puerta a En espera
                if(aeropuerto.puertasDisponibles()) { //Si el avión ya aterrizó y hay puertas disponibles puede despegar ir a puerta.
                    estado = EN_ESPERA;
                }
                break;
            case 3: //Despegar a Aterrizar
                if(aeropuerto.pistasDisponibles()) { //Si el avión se encuentra en el aire y hay pistas disponibles puede aterrizar.
                    estado = ATERRIZAR;
                }
                break;
        }
        return estado;
    }

    @Override
    public String toString() {
        return "Vuelo{" +
                "estado=" + estado +
                ", aeronave=" + aeronave +
                ", aeropuertoOrigen=" + aeropuertoOrigen +
                ", aeropuertoDestino=" + aeropuertoDestino +
                '}';
    }
}
