package cr.ac.ucr.paraiso.ie.progra2.maga.logic;

public class GeneraRandoms {

    public static int random(int lowBound, int highBound){
        int value = 0;
        do{
            value = lowBound+(int) Math.floor(Math.random()*highBound);
        }while(!isBetween(value, lowBound, highBound));
        return value;
    }

    public static boolean isBetween(int value, int low, int high) {
        return low <= value && value <= high;
    }


    public static String getIdVuelo(){
        String[] list = {"VL16553", "VL65526", "VL80340", "VL96734", "VL97620", "VL52452", "VL43230", "VL12371", "VL04923", "VL85312"};
        return list[random(0,10)];
    }

    public static String generaAeropuertoRandom(){
        String[] list = {"Aeropuerto Internacional Tobías Bolaños", "Aeropuerto Internacional Fresno Yosemite", "Aeropuerto Internacional de San Francisco", "Aeropuerto de Asheville", "Aeropuerto de Hamburgo", "Aeropuerto Internacional de Viena", "Aeropuerto de Bruselas-National"};
        return list[random(0,7)];
    }

}
