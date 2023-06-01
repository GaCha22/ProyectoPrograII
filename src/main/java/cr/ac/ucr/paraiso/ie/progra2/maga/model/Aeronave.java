package cr.ac.ucr.paraiso.ie.progra2.maga.model;

import cr.ac.ucr.paraiso.ie.progra2.maga.logic.Vuelo;

public class Aeronave {

    private String placa;

    private String tiempoEspera;

    private int tipo;
    private final static int COMERCIAL = 1;
    private final static int CARGA= 2;
    private final static int AVIONETA = 3;
    private int estado = 3;

    Vuelo vuelo = new Vuelo();

    public Aeronave(int tipo) {
        this.tipo = tipo;
    }

    public String calcularTiempoEspera(int tipo){
         tiempoEspera = "Tiempo Espera: ";
         switch(this.tipo){
             case 1:
                 tiempoEspera += " 2min";
                 break;
             case 2:
                 tiempoEspera += " 4min";
                 break;
             case 3:
                 tiempoEspera += " 1min";
                 break;
         }
         return tiempoEspera;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public void setEstado(){
        this.estado = vuelo.estadoAeronave(estado);
    }

}
