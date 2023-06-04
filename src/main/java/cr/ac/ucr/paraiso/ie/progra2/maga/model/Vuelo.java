package cr.ac.ucr.paraiso.ie.progra2.maga.model;

import cr.ac.ucr.paraiso.ie.progra2.maga.logic.VueloLogica;

import java.time.LocalTime;
import java.util.Date;

public class Vuelo {

    private String idVuelo;
    private Aeronave aeronave;
    private Aeropuerto aeropuertoOrigen;
    private Aeropuerto aeropuertoDestino;
    private CompaniaAerea companiaAerea;
    private boolean enEstado; //true si está Autorizado y false si está en espera
    private LocalTime horaSalida;
    private LocalTime horaLlegada;
    private VueloLogica vueloLogica;

    public Vuelo(String idVuelo, Aeronave aeronave, Aeropuerto aeropuertoOrigen, Aeropuerto aeropuertoDestino, CompaniaAerea companiaAerea, boolean enEstado, LocalTime horaSalida, LocalTime horaLlegada, VueloLogica vueloLogica) {
        this.idVuelo = idVuelo;
        this.aeronave = aeronave;
        this.aeropuertoOrigen = aeropuertoOrigen;
        this.aeropuertoDestino = aeropuertoDestino;
        this.companiaAerea = companiaAerea;
        this.enEstado = enEstado;
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;
        this.vueloLogica = vueloLogica;
    }

    public void cambiarEnEstado(){
        if(aeronave.getEstado()==vueloLogica.estadoAeronave(aeronave.getEstado())){
            this.enEstado = false;
        }else{
            this.enEstado = true;
        }
    }

    public String getIdVuelo() {
        return idVuelo;
    }

    public void setIdVuelo(String idVuelo) {
        this.idVuelo = idVuelo;
    }

    public Aeronave getAeronave() {
        return aeronave;
    }

    public void setAeronave(Aeronave aeronave) {
        this.aeronave = aeronave;
    }

    public Aeropuerto getAeropuertoOrigen() {
        return aeropuertoOrigen;
    }

    public void setAeropuertoOrigen(Aeropuerto aeropuertoOrigen) {
        this.aeropuertoOrigen = aeropuertoOrigen;
    }

    public Aeropuerto getAeropuertoDestino() {
        return aeropuertoDestino;
    }

    public void setAeropuertoDestino(Aeropuerto aeropuertoDestino) {
        this.aeropuertoDestino = aeropuertoDestino;
    }

    public CompaniaAerea getCompaniaAerea() {
        return companiaAerea;
    }

    public void setCompaniaAerea(CompaniaAerea companiaAerea) {
        this.companiaAerea = companiaAerea;
    }

    public boolean isEnEstado() {
        return enEstado;
    }

    public void setEnEstado(boolean enEstado) {
        this.enEstado = enEstado;
    }

    public LocalTime getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(LocalTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    public LocalTime getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(LocalTime horaLlegada) {
        this.horaLlegada = horaLlegada;
    }
}
