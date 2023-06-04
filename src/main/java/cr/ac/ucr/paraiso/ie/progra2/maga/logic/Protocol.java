package cr.ac.ucr.paraiso.ie.progra2.maga.logic;

import cr.ac.ucr.paraiso.ie.progra2.maga.model.Aeronave;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Aeropuerto;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Pista;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Puerta;

import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Protocol {
    //BORRADOR PROTOCOL
    Aeropuerto aeropuerto = new Aeropuerto();
    Scanner sn = new Scanner(System.in);
    int tipo = sn.nextInt(); //es con interfaz no así
    Aeronave aeronave = new Aeronave(tipo);
    VueloLogica vueloLogica = new VueloLogica();
    Pista pistas [] = aeropuerto.getPistas();
    Puerta puertas [] = aeropuerto.getPuertas();
    int estadoAeronave;
    public void gestionarEstado(int estado) throws InterruptedException {
        estadoAeronave = estado;
        switch (estado){
            case 0:
                despegar();
                break;
            case 1:
                IrAPuerta();
                break;
            case 2:
                vueloLogica.estadoAeronave(estadoAeronave);
            case 3:
                aterrizar();
                break; //este es el estado inicial de todas las aeronaves
        }
    }

    public void despegar() throws InterruptedException {
        int flag=0;
        System.out.println("sirve despegar");
        if(aeropuerto.pistasDisponibles()){ //gestionar puertas disponibles
            for(int i=0; i<pistas.length; i++){
                if(pistas[i].isDisponible()){
                    pistas[i].setDisponible(false);
                    flag = i;
                }
            }
            aeropuerto.setPistas(pistas);
            pistas[flag].setDisponible(true);
            notify();
            gestionarEstado(vueloLogica.estadoAeronave(estadoAeronave));
        }else{
            wait();
        }
    }

    public void IrAPuerta() throws InterruptedException {
        int flag=0;
        System.out.println("sirve puerta");
        if(aeropuerto.puertasDisponibles()){ //gestionar puertas disponibles
            for(int i=0; i<puertas.length; i++){
                if(puertas[i].isDisponible()){
                    puertas[i].setDisponible(false);
                    flag = i;
                }
            }
            aeropuerto.setPuertas(puertas);
            sleep(aeronave.calcularTiempoEspera());
            puertas[flag].setDisponible(true); //siempre pasa por el if porque si entro al if incial es que al menos hay una puerta disponible
            notify();
            gestionarEstado(vueloLogica.estadoAeronave(estadoAeronave));
        }else{
            wait();
        }
    }

    public void aterrizar() throws InterruptedException {
        int flag=0;
        System.out.println("sirve aterrizar");
        if(aeropuerto.pistasDisponibles()){ //gestionar puertas disponibles
            for(int i=0; i<pistas.length; i++){
                if(pistas[i].isDisponible()){
                    pistas[i].setDisponible(false);
                    flag = i;
                }
            }
            aeropuerto.setPistas(pistas);
            pistas[flag].setDisponible(true);
            notify();
            gestionarEstado(vueloLogica.estadoAeronave(estadoAeronave));
        }else{
            wait();
        }
    }
}
