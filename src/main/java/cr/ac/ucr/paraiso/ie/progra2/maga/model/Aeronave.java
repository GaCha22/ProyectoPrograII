package cr.ac.ucr.paraiso.ie.progra2.maga.model;

import cr.ac.ucr.paraiso.ie.progra2.maga.logic.VueloLogica;

public class Aeronave {

    private String placa;
    private int tiempoEspera;
    private int tipo;
    private final int COMERCIAL = 1;
    private final int CARGA= 2;
    private final int AVIONETA = 3;
    private int estado = 3;

    private Vuelo vuelo;

    public Aeronave(int tipo) {
        this.tipo = tipo;
        this.tiempoEspera = calcularTiempoEspera();
    }

    public Aeronave(String placa, int tipo) {
        this.placa = placa;
        this.tipo = tipo;
    }

    public int calcularTiempoEspera(){
         switch(this.tipo){
             case COMERCIAL:
                 return  120000;
             case CARGA:
                 return 240000;
             case AVIONETA:
                 return  60000;
             default: //si no tiene un tipo definido
                 return 0;
         }
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

    public int getEstado() {
        return this.estado;
    }

    @Override
    public String toString() {
        switch(this.tipo){
            case COMERCIAL:
                return  "COMERCIAL";
            case CARGA:
                return "CARGA";
            case AVIONETA:
                return  "AVIONETA";
            default: //si no tiene un tipo definido
                return "";
        }
    }
}
