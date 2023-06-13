package cr.ac.ucr.paraiso.ie.progra2.maga.cliente;

import cr.ac.ucr.paraiso.ie.progra2.maga.logic.Protocolo;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Solicitud;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Vuelo;
import cr.ac.ucr.paraiso.ie.progra2.maga.service.GestionaArchivo;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalTime;

public class Piloto extends Thread{
    private int puerto;
    private PrintWriter writer;
    private BufferedReader reader;
    private String respuesta;
    private Socket echoSocket;
    private String propertyMessage;
    private String mensaje;
    public final static Vuelo vuelo = GestionaArchivo.leerVuelo("vuelo.json");
    private PropertyChangeSupport propertyChangeSupport;
    private Solicitud solicitud;

    public Piloto(int puerto){
        this.puerto = puerto;
        this.solicitud = new Solicitud(vuelo, "");
        try {
            propertyChangeSupport = new PropertyChangeSupport(this);
            echoSocket = new Socket("localhost", this.puerto);
            this.writer = new PrintWriter(echoSocket.getOutputStream(), true);
            this.reader = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            respuesta = reader.readLine();
            System.out.println("Servidor: " + respuesta);
            respuesta = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while ((respuesta = reader.readLine()) != null) {
                System.out.println(respuesta);
                setMensaje(respuesta + " " + mensaje);
                if(respuesta.equals("aceptar")) {
                    try {
                        sleep(10000);
                        if(mensaje.equals("puerta")){
                            switch(vuelo.getAeronave().getTipo()) {
                                case 1: //comercial
                                    sleep(120000);
                                    break;
                                case 2: //carga
                                    sleep(240000);
                                    break;
                                case 3: //avioneta
                                    sleep(60000);
                                    break;
                            }
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                setMensaje(mensaje);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void despegar(){
        this.mensaje = "despegar";
        solicitud.setSolicitud(mensaje);
        this.writer.println(GestionaArchivo.solicitudAJson(solicitud));
    }

    public void aterrizar(){
        this.mensaje = "aterrizar";
        solicitud.setSolicitud(mensaje);
        this.writer.println(GestionaArchivo.solicitudAJson(solicitud));
    }

    public void puerta(){
        this.mensaje = "puerta";
        solicitud.setSolicitud(mensaje);
        this.writer.println(GestionaArchivo.solicitudAJson(solicitud));
    }

    public void agregarPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removerPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public void setMensaje(String nuevoMensaje) {
        String viejoMensaje = propertyMessage;
        propertyMessage = nuevoMensaje;
        propertyChangeSupport.firePropertyChange("mensaje", viejoMensaje, nuevoMensaje);
    }

}
