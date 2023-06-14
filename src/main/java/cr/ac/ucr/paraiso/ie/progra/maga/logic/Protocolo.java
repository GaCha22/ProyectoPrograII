package cr.ac.ucr.paraiso.ie.progra.maga.logic;
import cr.ac.ucr.paraiso.ie.progra.maga.model.Aeropuerto;
import cr.ac.ucr.paraiso.ie.progra.maga.model.Pista;
import cr.ac.ucr.paraiso.ie.progra.maga.model.Puerta;
import cr.ac.ucr.paraiso.ie.progra.maga.model.Vuelo;
import cr.ac.ucr.paraiso.ie.progra.maga.servidor.MultiServidor;

import static java.lang.Thread.sleep;

public class Protocolo {
    private final Vuelo vuelo;
    private final Aeropuerto aeropuerto = MultiServidor.aeropuertoServer;

    public Protocolo(Vuelo vuelo){
        this.vuelo = vuelo;
    }

    //Método para hacer que el avión aterrice
    public boolean avionAterrizando(){
        if(!aeropuerto.pistasDisponibles() && vuelo.getAeronave().getEstado() == 3){
            return false;
        }
        utilityPistas();
        //Se le da un nuevo valor al estado del avión
        vuelo.getAeronave().setEstado(1);
        return true;
    }

    //Método para hacer que el avión vaya a Puerta
    public boolean avionAPuerta(){
        if (!aeropuerto.puertasDisponibles() && vuelo.getAeronave().getEstado() == 1){
                return false;
        }
        liberarPista();
        utilityPuertas();
        //Se le da un nuevo valor al estado del avión
        vuelo.getAeronave().setEstado(2);
        return true;
        }

    //Método para hacer que el avión despegue
    public boolean avionDespegue() {
        if (!aeropuerto.pistasDisponibles() && vuelo.getAeronave().getEstado() == 2) {
                return false;
        }
        utilityPistas();
        //Se le da un nuevo valor al estado del avión
        vuelo.getAeronave().setEstado(3);
        return true;
    }

    //Método para gestionar puertas disponibles en el aeropuerto destino
    private void utilityPuertas() {
        if (aeropuerto.puertasDisponibles()) {
            for (int i = 0; i < aeropuerto.getPuertas().length; i++) {
                Puerta puerta = aeropuerto.getPuertas()[i];
                if (puerta.isDisponible()) {
                    puerta.setDisponible(false);
                    //Se guarda en el vuelo la puerta asignada
                    vuelo.setPuertaAsignada(puerta);
                    break;
                }
            }
        }
    }

    //Método para gestionar pistas disponibles en el aeropuerto destino
    private void utilityPistas() {
        if (aeropuerto.pistasDisponibles()) {
            for (int i = 0; i < aeropuerto.getPistas().length; i++) {
                Pista pista = aeropuerto.getPistas()[i];
                if (pista.isDisponible()) {
                    pista.setDisponible(false);
                    //Se guarda en el vuelo la pista asignada
                    vuelo.setPistaAsignada(pista);
                    break;
                }
            }
        }
    }

    //Método que permite la liberación de pistas
    public void liberarPista(){
        for (Pista pista : aeropuerto.getPistas()) {
            if (pista == vuelo.getPistaAsignada()){
                pista.setDisponible(true);
                //Se le quita el valor de la pista al vuelo
                vuelo.setPistaAsignada(null);
                if (MultiServidor.getListaDeEsperaPistas().peek() != null) MultiServidor.removeListaEsperaPistas().aceptarSolicitud();
            }
        }
    }

    //Método que permite la liberación de puertas
    public void liberarPuerta() {
        for (Puerta puerta :aeropuerto.getPuertas()) {
            if (puerta == vuelo.getPuertaAsignada()){
                puerta.setDisponible(true);
                //Se le quita el valor de la puerta al vuelo
                vuelo.setPuertaAsignada(null);
                if (MultiServidor.getListaDeEsperaPuertas().peek() != null) MultiServidor.removeListaEsperaPuertas().aceptarSolicitud();
            }
        }
    }
}

