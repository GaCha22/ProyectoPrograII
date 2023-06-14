package cr.ac.ucr.paraiso.ie.progra.maga.model;

public class Aeropuerto {
    private Pista[] pistas;
    private Puerta[] puertas;
    private String nombre;

    public Aeropuerto() {
    }

    public Aeropuerto(String nombre) {
        this.nombre = nombre;
    }

    //Método que comprueba las pistas disponibles
    public boolean pistasDisponibles(){
        boolean despegar = false;

        for(Pista pista : pistas){
            if(pista.isDisponible()){
                despegar = true;
            }
        }
        return despegar;
    }

    //Método que comprueba las puertas disponibles
    public boolean puertasDisponibles(){
        boolean puertaDisponible = false;
        for(Puerta puerta : puertas){
            if(puerta.isDisponible()){
                puertaDisponible = true;
            }
        }
        return puertaDisponible;
    }

    public Pista[] getPistas() {
        return pistas;
    }

    public Puerta[] getPuertas() {
        return puertas;
    }

    public String getNombre() {
        return nombre;
    }

    public String pistasToString(){
        StringBuilder sb = new StringBuilder();
        for (Pista pista: pistas) {
            sb.append("Pista #").append(pista.getNumPista()).append(" ")
                    .append(pista.isDisponible()?"Disponible":"No Disponible")
                    .append("\n");
        }

        return sb.toString();
    }
    
    public String puertasToString(){
        StringBuilder sb = new StringBuilder();
        for (Puerta puerta: puertas) {
            sb.append("Puerta #").append(puerta.getNumPuerta()).append(" ")
                    .append(puerta.isDisponible()?"Disponible":"No Disponible")
                    .append("\n");
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Aeropuerto{");
        sb.append("nombre=").append(nombre);
        sb.append(", pistas=[");
        for (Pista pista : pistas) {
            sb.append(pista.toString()).append(", ");
        }
        sb.setLength(sb.length() - 2);
        sb.append("]");
        sb.append(", puertas=[");
        for (Puerta puerta : puertas) {
            sb.append(puerta.toString()).append(", ");
        }
        sb.setLength(sb.length() - 2);
        sb.append("]");
        sb.append("}");
        return sb.toString();
    }


}