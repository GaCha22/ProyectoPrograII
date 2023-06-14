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

    public synchronized boolean avionAterrizando(){
        if(!aeropuerto.pistasDisponibles() && vuelo.getAeronave().getEstado() == 3){
            return false;
        }
        utilityPistas();
        vuelo.getAeronave().setEstado(1);
        return true;
    }

    //si hay puertas disponibles, y si el avion esta en puerta, entonces lo pone a esperar un tiempo respectivo
    public synchronized boolean avionAPuerta(){
        if (!aeropuerto.puertasDisponibles() && vuelo.getAeronave().getEstado() == 1){
                return false;
        }
        liberarPista();
        utilityPuertas();
        //sale de puertas, mandelo a esperar
        vuelo.getAeronave().setEstado(2);
        return true;
        }


    public synchronized boolean avionDespegue() {
        if (!aeropuerto.pistasDisponibles() && vuelo.getAeronave().getEstado() == 0) {
                return false;
        }
        utilityPistas();
        //sale de puertas, mandelo a despegar
        vuelo.getAeronave().setEstado(3);
        return true;
    }

    private synchronized void utilityPuertas() {
        if (aeropuerto.puertasDisponibles()) {
            for (int i = 0; i < aeropuerto.getPuertas().length; i++) {
                Puerta puerta = aeropuerto.getPuertas()[i];
                if (puerta.isDisponible()) {
                    puerta.setDisponible(false);
                    vuelo.setPuertaAsignada(puerta);
                    break;
                }
            }
        }
    }

    //gestiona pistas en el aeropuerto destino
    private synchronized void utilityPistas() {
        if (aeropuerto.pistasDisponibles()) {
            for (int i = 0; i < aeropuerto.getPistas().length; i++) {
                Pista pista = aeropuerto.getPistas()[i];
                if (pista.isDisponible()) {
                    pista.setDisponible(false);
                    vuelo.setPistaAsignada(pista);
                    break;
                }
            }
        }
    }

    public void liberarPista(){
        for (Pista pista : aeropuerto.getPistas()) {
            if (pista == vuelo.getPistaAsignada()){
                pista.setDisponible(true);
                vuelo.setPistaAsignada(null);
                if (MultiServidor.getListaDeEsperaPistas().peek() != null) MultiServidor.removeListaEsperaPistas().aceptarSolicitud();
            }
        }
    }

    public void liberarPuerta() {
        for (Puerta puerta :aeropuerto.getPuertas()) {
            if (puerta == vuelo.getPuertaAsignada()){
                puerta.setDisponible(true);
                vuelo.setPuertaAsignada(null);
                if (MultiServidor.getListaDeEsperaPuertas().peek() != null) MultiServidor.removeListaEsperaPuertas().aceptarSolicitud();
            }
        }
    }
}

