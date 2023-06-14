package cr.ac.ucr.paraiso.ie.progra2.maga.logic;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.*;
import cr.ac.ucr.paraiso.ie.progra2.maga.servidor.MultiServidor;

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

    //si no hay puertas o pistas pone en espera al hilo hasta que alguien le notifique que ya hay puertas
//    public synchronized void avionEsperando(){
//        //mientras no haya pistas o puertas disponibles
//        while(!aeropuerto.puertasDisponibles() && aeronave.getEstado() == 1 ||
//                !aeropuerto.pistasDisponibles() && aeronave.getEstado() == 2 ||
//                !aeropuerto.pistasDisponibles() && aeronave.getEstado() == 3){
//            try {
//                wait();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

    //gestiona puertas disponibles en el aeropuerto destino
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
//            aeropuerto.getPuertas()[flag].setDisponible(true);//siempre pasa por el if porque si entro al if incial es que al menos hay una puerta disponible
//            notifyAll();
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
//            aeropuerto.getPistas()[flag].setDisponible(true);
//            notifyAll();
        }
    }

    public void liberarPista(){
        for (Pista pista : aeropuerto.getPistas()) {
            if (pista == vuelo.getPistaAsignada()){
                pista.setDisponible(true);
                vuelo.setPistaAsignada(null);
//                notify();
            }
        }
    }

    public void liberarPuerta() {
        for (Puerta puerta :aeropuerto.getPuertas()) {
            if (puerta == vuelo.getPuertaAsignada()){
                puerta.setDisponible(true);
                vuelo.setPuertaAsignada(null);
//                notify();
            }
        }
    }
}

