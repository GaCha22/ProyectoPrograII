package cr.ac.ucr.paraiso.ie.progra2.maga.logic;

import cr.ac.ucr.paraiso.ie.progra2.maga.model.*;

import static java.lang.Thread.sleep;

public class VueloLogica {

    //Se crean los estados de los aviones.
    private final int EN_ESPERA = 0;
    private final int  ATERRIZAR = 1;
    private final int PUERTA = 2;
    private final int DESPEGAR = 3;
    Aeronave aeronave;
    private Vuelo vuelo;

    public VueloLogica(Vuelo vuelo) {
        this.vuelo = vuelo;
    }

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

    public synchronized void avionAPuerta(int estado, int tipoAvion) throws InterruptedException {

        while(vuelo.getAeropuertoDestino().puertasDisponibles() && estado == 1){

            switch(tipoAvion){
                case 1: // comercial
                    //utilityPuertas();
                    wait(60000);
                    break;
                case 2: // carga
                    //utilityPuertas();
                    wait(120000);
                    break;
                case 3: // avioneta
                    //utilityPuertas();
                    wait(240000);
                    break;
            }
        }
        notifyAll();
    }

    //metodo que libere o quite puertas
//    private void utilityPuertas() {
//        int flag = 0;
//        if (vuelo.getAeropuertoDestino().puertasDisponibles()) { // gestionar puertas disponibles
//            for (int i = 0; i < puertas.length; i++) {
//                if (puertas[i].isDisponible()) {
//                    puertas[i].setDisponible(false);
//                    flag = i;
//                }
//            }
//            vuelo.getAeropuertoDestino().setPuertas(puertas);
//            puertas[flag].setDisponible(true); // siempre pasa por el if porque si entro al if incial es que al menos hay una puerta disponible
//        }
//    }
//
//    // necesitamos un pistas
//    private void utilityPistas() {
//        int flag = 0;
//        if (vuelo.getAeropuertoDestino().pistasDisponibles()) { //gestionar puertas disponibles
//            for (int i = 0; i < pistas.length; i++) {
//                if (pistas[i].isDisponible()) {
//                    pistas[i].setDisponible(false);
//                    flag = i;
//                }
//            }
//            vuelo.getAeropuertoDestino().setPistas(pistas);
//            pistas[flag].setDisponible(true);
//        }
//    }

    public synchronized  void avionEsperandoPuerta(int estado){
        while(!vuelo.getAeropuertoDestino().pistasDisponibles() && estado == 2){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        aeronave.setEstado(estadoAeronave(estado));
        notify();
    }
}