package cr.ac.ucr.paraiso.ie.progra2.maga.logic;
import cr.ac.ucr.paraiso.ie.progra2.maga.cliente.Piloto;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.*;
import cr.ac.ucr.paraiso.ie.progra2.maga.servidor.MultiServidor;

import static java.lang.Thread.sleep;

public class Protocolo {
    private final Vuelo vuelo = Piloto.vuelo;
    private final Aeropuerto aeropuerto = MultiServidor.aeropuertoServer;

    public synchronized void avionAterrizando(){
        while(!aeropuerto.pistasDisponibles() && vuelo.getAeronave().getEstado() == 3){
            try {
                System.out.println("Aterrizar");
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        utilityPistas();
        vuelo.getAeronave().setEstado(1);
    }

    //si hay puertas disponibles, y si el avion esta en puerta, entonces lo pone a esperar un tiempo respectivo
    public synchronized void avionAPuerta(){
        while(!aeropuerto.puertasDisponibles() && vuelo.getAeronave().getEstado() == 1){
            try {
                System.out.println("Puerta");
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        utilityPuertas();
        //sale de puertas, mandelo a esperar
        vuelo.getAeronave().setEstado(2);
        }


    public synchronized void avionDespegue() {
        while (!aeropuerto.pistasDisponibles() && vuelo.getAeronave().getEstado() == 0) {
            try {
                System.out.println("Despegar");
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        utilityPistas();
        //sale de puertas, mandelo a despegar
        vuelo.getAeronave().setEstado(3);
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
            notifyAll();
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
            notifyAll();
        }
    }

}

