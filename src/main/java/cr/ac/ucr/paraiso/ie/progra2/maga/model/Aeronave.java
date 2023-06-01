package cr.ac.ucr.paraiso.ie.progra2.maga.model;

public class Aeronave {

    private String placa;

    private String tiempoEspera;

    private int tipo;

    private final static int COMERCIAL = 1;
    private final static int CARGA= 2;
    private final static int AVIONETA = 3;

    public Aeronave(int tipo) {
        this.tipo = tipo;
    }

    public String calcularTiempoEspera(int tipo){
         String tiempoEspera = "Tiempo Espera: ";

         switch(this.tipo){
             case 1: tiempoEspera += " 2min"; break;
             case 2: tiempoEspera += " 4min"; break;
             case 3: tiempoEspera += " 1min"; break;
         }

         return tiempoEspera;

    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }


}
