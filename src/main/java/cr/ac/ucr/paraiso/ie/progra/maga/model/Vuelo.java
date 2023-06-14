package cr.ac.ucr.paraiso.ie.progra.maga.model;

import cr.ac.ucr.paraiso.ie.progra.maga.logic.GeneraRandoms;
import cr.ac.ucr.paraiso.ie.progra.maga.service.GestionaArchivo;

import java.time.LocalTime;

public class Vuelo {

    private String idVuelo;
    private Aeronave aeronave;
    private Aeropuerto aeropuertoOrigen;
    private Aeropuerto aeropuertoDestino;
    private CompaniaAerea companiaAerea;
    private LocalTime horaSalida;
    private LocalTime horaLlegada;
    private Puerta puertaAsignada;
    private Pista pistaAsignada;

    public Vuelo(Aeronave aeronave, CompaniaAerea companiaAerea) {
        this.idVuelo = GeneraRandoms.getIdVuelo();
        this.aeronave = aeronave;
        this.aeropuertoOrigen = new Aeropuerto(GeneraRandoms.generaAeropuertoRandom());
        this.aeropuertoDestino = GestionaArchivo.leerArchivoConfiguracion("config.json");
        this.companiaAerea = companiaAerea;
        this.horaSalida = LocalTime.now().minusHours(GeneraRandoms.random(1, 24));
    }

    public Vuelo(String idVuelo, Aeronave aeronave, Aeropuerto aeropuertoOrigen, Aeropuerto aeropuertoDestino, CompaniaAerea companiaAerea, boolean enEstado, LocalTime horaSalida, LocalTime horaLlegada) {
        this.idVuelo = idVuelo;
        this.aeronave = aeronave;
        this.aeropuertoOrigen = aeropuertoOrigen;
        this.aeropuertoDestino = aeropuertoDestino;
        this.companiaAerea = companiaAerea;
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;
    }

    public Aeronave getAeronave() {
        return aeronave;
    }

    public void setAeropuertoOrigen(Aeropuerto aeropuertoOrigen) {
        this.aeropuertoOrigen = aeropuertoOrigen;
    }

    public void setAeropuertoDestino(Aeropuerto aeropuertoDestino) {
        this.aeropuertoDestino = aeropuertoDestino;
    }

    public CompaniaAerea getCompaniaAerea() {
        return companiaAerea;
    }

    public void setHoraSalida(LocalTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    public void setHoraLlegada(LocalTime horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public Puerta getPuertaAsignada() {
        return puertaAsignada;
    }

    public void setPuertaAsignada(Puerta puertaAsignada) {
        this.puertaAsignada = puertaAsignada;
    }

    public Pista getPistaAsignada() {
        return pistaAsignada;
    }

    public void setPistaAsignada(Pista pistaAsignada) {
        this.pistaAsignada = pistaAsignada;
    }

    @Override
    public String toString() {
        return "VUELO: " + idVuelo +
                "\nAeronave: " + aeronave +
                "\nAeropuerto de origen: " + aeropuertoOrigen.getNombre()+
                "\nAeropuerto destino: " + aeropuertoDestino.getNombre() +
                "\nAerolinea: " + companiaAerea.getNombre() +
                "\nHora de salida: " + horaSalida +
                "\nHora de llegada: " + horaLlegada;
    }
}
