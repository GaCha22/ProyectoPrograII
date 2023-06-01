package cr.ac.ucr.paraiso.ie.progra2.maga.logic;

public class Vuelo {

    //Se crean los estados de los aviones.
    private final int EN_ESPERA = 0;
    private final int  ATERRIZAR = 1;
    private final int PUERTA = 2;
    private final int DESPEGAR = 3;
    private int estado = EN_ESPERA;

    //Método en la cual se le indica al avión que despegue.
    public void despegar (){ //Si el avión se encuentra en tierra puede despegar.
        this.estado = DESPEGAR;
    }

    //Método en la cual se le indica al avión que aterrice.
    public void aterrizar (){
        if(this.estado == DESPEGAR){ //Si el avión se encuentra en el aire puede aterrizar.
            this.estado = ATERRIZAR;
        }
    }

    //Método en la cual se le indica al avión que aterrice.
    public void irAPuerta (){ //Si el avión ya aterrizó puede despegar ir a puerta.
        if(this.estado == ATERRIZAR) {
            this.estado = PUERTA;
        }
    }

    //Método en la cual se le indica al avión que vaya a puerta.
    public void enEspera (){
        this.estado = EN_ESPERA;
    }

}
