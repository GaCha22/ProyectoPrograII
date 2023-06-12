package cr.ac.ucr.paraiso.ie.progra2.maga.logic;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Aeronave;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Pista;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Puerta;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Vuelo;

import static java.lang.Thread.sleep;

public class Protocolo {
    Aeronave aeronave;
    VueloLogica logica;
    private Vuelo vuelo;
    Pista pistas[] = vuelo.getAeropuertoDestino().getPistas();
    Puerta puertas[] = vuelo.getAeropuertoDestino().getPuertas();

    public synchronized void avionAterrizando(){
        while(!vuelo.getAeropuertoDestino().pistasDisponibles() && aeronave.getEstado() == 3){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        utilityPistas();
        aeronave.setEstado(logica.estadoAeronave(aeronave.getEstado()));
    }

    //si hay puertas disponibles, y si el avion esta en puerta, entonces lo pone a esperar un tiempo respectivo
    public synchronized void avionAPuerta(){
        while(!vuelo.getAeropuertoDestino().puertasDisponibles() && aeronave.getEstado() == 1){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        utilityPuertas();
        //sale de puertas, mandelo a esperar
        aeronave.setEstado(logica.estadoAeronave(aeronave.getEstado()));
        }


    public synchronized void avionDespegue() {
        while (!vuelo.getAeropuertoDestino().pistasDisponibles() && aeronave.getEstado() == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        utilityPistas();
        //sale de puertas, mandelo a despegar
        aeronave.setEstado(aeronave.getEstado());
    }

    public synchronized void avionAEnEspera(){
        //mientras no haya pistas o puertas disponibles
        while(aeronave.getEstado() == 2){
            try {
                switch(aeronave.getTipo()){
                    case 1: //comercial
                        sleep(60000);
                        break;
                    case 2: //carga
                        sleep(120000);
                        break;
                    case 3: //avioneta
                        sleep(240000);
                        break;
                }
                break;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        aeronave.setEstado(aeronave.getEstado());
        if(vuelo.getAeropuertoDestino().pistasDisponibles()){
            avionDespegue();
        }else{
            avionEsperando();
        }
    }

    //si no hay puertas o pistas pone en espera al hilo hasta que alguien le notifique que ya hay puertas
    public synchronized void avionEsperando(){
        //mientras no haya pistas o puertas disponibles
        while(!vuelo.getAeropuertoDestino().puertasDisponibles() && aeronave.getEstado() == 1 ||
                !vuelo.getAeropuertoDestino().pistasDisponibles() && aeronave.getEstado() == 2 ||
                !vuelo.getAeropuertoDestino().pistasDisponibles() && aeronave.getEstado() == 3){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //gestiona puertas disponibles en el aeropuerto destino
    private synchronized void utilityPuertas() {
        int flag = 0;
        if (vuelo.getAeropuertoDestino().puertasDisponibles()) {
            for (int i = 0; i < puertas.length; i++) {
                if (puertas[i].isDisponible()) {
                    puertas[i].setDisponible(false);
                    try {
                        sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    flag = i;
                    break;
                }
            }
            vuelo.getAeropuertoDestino().setPuertas(puertas);
            puertas[flag].setDisponible(true); //siempre pasa por el if porque si entro al if incial es que al menos hay una puerta disponible
            notifyAll();
        }
    }

    //gestiona pistas en el aeropuerto destino
    private synchronized void utilityPistas() {
        int flag = 0;
        if (vuelo.getAeropuertoDestino().pistasDisponibles()) {
            for (int i = 0; i < pistas.length; i++) {
                if (pistas[i].isDisponible()) {
                    pistas[i].setDisponible(false);
                    try {
                        sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    flag = i;
                    break;
                }
            }
            vuelo.getAeropuertoDestino().setPistas(pistas);
            pistas[flag].setDisponible(true);
            notifyAll();
        }
    }

}

