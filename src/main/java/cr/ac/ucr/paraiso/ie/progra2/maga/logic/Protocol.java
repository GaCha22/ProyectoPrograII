package cr.ac.ucr.paraiso.ie.progra2.maga.logic;

import cr.ac.ucr.paraiso.ie.progra2.maga.model.Aeronave;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Aeropuerto;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Vuelo;

import java.util.Scanner;

public class Protocol {
 //BORRADOR PROTOCOL
    Aeropuerto aeropuerto = new Aeropuerto();
    Scanner sn = new Scanner(System.in);
    int tipo = sn.nextInt(); //es con interfaz no asi
    Aeronave aeronave = new Aeronave(tipo);

    VueloLogica vueloLogica = new VueloLogica();
    //  hilos, wait(), notify(), si no hay pistas o puertas segun el estado entonces ESPERA segun el avion.
    // se ocupa utilizarlos metodos de aeropuerto, pero tambien vuelo logica, como el ejemplo de la panaderia

    int estadoAeronave;
    public void gestionarEstado(int estado) throws InterruptedException {
         estadoAeronave = estado;
            switch (estado){
                case 0: despegar(); break;
                case 1: puerta(); break;
                case 2: vueloLogica.estadoAeronave(estadoAeronave);
                case 3: aterrizar(); break;
            }

    }
    public void despegar() throws InterruptedException {
    if(aeropuerto.pistasDisponibles())
       vueloLogica.estadoAeronave(estadoAeronave);
    else{
        wait(aeronave.calcularTiempoEspera());
    }
    }


    public void puerta() throws InterruptedException {
        if(aeropuerto.puertasDisponibles())
            vueloLogica.estadoAeronave(estadoAeronave);
        else{
            wait(aeronave.calcularTiempoEspera());
        }

    }

    public void aterrizar() throws InterruptedException {
        if(aeropuerto.pistasDisponibles())
            vueloLogica.estadoAeronave(estadoAeronave);
        else{
            wait(aeronave.calcularTiempoEspera());
        }
    }
}
