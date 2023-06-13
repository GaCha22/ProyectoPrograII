package cr.ac.ucr.paraiso.ie.progra2.maga.logic;
import cr.ac.ucr.paraiso.ie.progra2.maga.cliente.Piloto;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.*;
import cr.ac.ucr.paraiso.ie.progra2.maga.servidor.MultiServidor;

import static java.lang.Thread.sleep;

public class Protocolo {
    private final Aeronave aeronave = Piloto.vuelo.getAeronave();
    private final Aeropuerto aeropuerto = MultiServidor.aeropuertoServer;

    public synchronized void avionAterrizando(){
        while(!aeropuerto.pistasDisponibles() && aeronave.getEstado() == 3){
            try {
                System.out.println("Aterrizar");
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        utilityPistas();
        aeronave.setEstado(1);
    }

    //si hay puertas disponibles, y si el avion esta en puerta, entonces lo pone a esperar un tiempo respectivo
    public synchronized void avionAPuerta(){
        while(!aeropuerto.puertasDisponibles() && aeronave.getEstado() == 1){
            try {
                System.out.println("Puerta");
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        utilityPuertas();
        //sale de puertas, mandelo a esperar
        aeronave.setEstado(2);
        }


    public synchronized void avionDespegue() {
        while (!aeropuerto.pistasDisponibles() && aeronave.getEstado() == 0) {
            try {
                System.out.println("Despegar");
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        utilityPistas();
        //sale de puertas, mandelo a despegar
        aeronave.setEstado(3);
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
        int flag = 0;
        if (aeropuerto.puertasDisponibles()) {
            for (int i = 0; i < aeropuerto.getPuertas().length; i++) {
                if (aeropuerto.getPuertas()[i].isDisponible()) {
                    aeropuerto.getPuertas()[i].setDisponible(false);
                    flag = i;
                    break;
                }
            }
            aeropuerto.getPuertas()[flag].setDisponible(true);//siempre pasa por el if porque si entro al if incial es que al menos hay una puerta disponible
            notifyAll();
        }
    }

    //gestiona pistas en el aeropuerto destino
    private synchronized void utilityPistas() {
        int flag = 0;
        if (aeropuerto.pistasDisponibles()) {
            for (int i = 0; i < aeropuerto.getPistas().length; i++) {
                if (aeropuerto.getPistas()[i].isDisponible()) {
                    aeropuerto.getPistas()[i].setDisponible(false);
                    flag = i;
                    break;
                }
            }
            aeropuerto.getPistas()[flag].setDisponible(true);
            notifyAll();
        }
    }

}

