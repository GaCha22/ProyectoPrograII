package cr.ac.ucr.paraiso.ie.progra2.maga.logic;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Aeronave;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Pista;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Puerta;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Vuelo;

public class Protocolo {
    Aeronave aeronave;
    VueloLogica logica;
    private Vuelo vuelo;
    Pista pistas[] = vuelo.getAeropuertoDestino().getPistas();
    Puerta puertas[] = vuelo.getAeropuertoDestino().getPuertas();



    // si hay puertas disponibles, y si el avion esta en puerta, entonces lo pone a esperar un tiempo respectivo
    public synchronized void avionAPuerta() throws InterruptedException {
        while(vuelo.getAeropuertoDestino().puertasDisponibles() && aeronave.getEstado() == 1){
            //pone el estado del avión en puerta
            aeronave.setEstado(logica.estadoAeronave(aeronave.getEstado()));
            //deshabilita las puertas que se vayan a usar en ese momento
            utilityPuertas();
            switch(aeronave.getTipo()){
                case 1: // comercial
                    wait(60000);
                    break;
                case 2: // carga
                    wait(120000);
                    break;
                case 3: // avioneta
                    wait(240000);
                    break;
            }
        }
        // sale de puertas, mandelo a despegar
        if(vuelo.getAeropuertoDestino().pistasDisponibles()) {
            aeronave.setEstado(3);
            notifyAll();
        }
    }

    // gestiona puertas disponibles en el aeropuerto destino
    private synchronized void utilityPuertas() {
        int flag = 0;
        if (vuelo.getAeropuertoDestino().puertasDisponibles()) {
            for (int i = 0; i < puertas.length; i++) {
                if (puertas[i].isDisponible()) {
                    puertas[i].setDisponible(false);
                    flag = i;
                }
            }
            vuelo.getAeropuertoDestino().setPuertas(puertas);
            puertas[flag].setDisponible(true); // siempre pasa por el if porque si entro al if incial es que al menos hay una puerta disponible
        }
    }

    // gestiona pistas en el aeropuerto destino
    private synchronized void utilityPistas() {
        int flag = 0;
        if (vuelo.getAeropuertoDestino().pistasDisponibles()) {
            for (int i = 0; i < pistas.length; i++) {
                if (pistas[i].isDisponible()) {
                    pistas[i].setDisponible(false);
                    flag = i;
                }
            }
            vuelo.getAeropuertoDestino().setPistas(pistas);
            pistas[flag].setDisponible(true);
        }
    }

    //si no hay puertas pone en espera al hilo hasta que alguien le notifique que ya hay puertas
    public synchronized void avionEsperando(){
        //mientras no haya pistas o puertas disponibles
        while(!vuelo.getAeropuertoDestino().puertasDisponibles() && aeronave.getEstado() == 1 ||
                !vuelo.getAeropuertoDestino().pistasDisponibles() && aeronave.getEstado() == 2){
            aeronave.setEstado(0);
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        utilityPistas(); //deshabilita las pistas que se usen en este momento
        aeronave.setEstado(logica.estadoAeronave(aeronave.getEstado()));
        notifyAll();
    }

}

