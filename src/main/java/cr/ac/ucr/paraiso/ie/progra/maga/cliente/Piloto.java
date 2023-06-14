package cr.ac.ucr.paraiso.ie.progra.maga.cliente;

import cr.ac.ucr.paraiso.ie.progra.maga.model.Vuelo;
import cr.ac.ucr.paraiso.ie.progra.maga.model.Solicitud;
import cr.ac.ucr.paraiso.ie.progra.maga.service.GestionaArchivo;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Piloto extends Thread{
    private int puerto;
    private PrintWriter writer;
    private BufferedReader reader;
    private String respuesta;
    private Socket echoSocket;
    private String propertyMessage;
    private String mensaje;
    public final Vuelo vuelo = GestionaArchivo.leerVuelo("vuelo.json");
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
            writer.println(GestionaArchivo.classAJson(vuelo));
            respuesta = reader.readLine();
            System.out.println("Servidor: " + respuesta);
            respuesta = null;
        } catch (IOException e) {
            closeResources(this.echoSocket, this.reader, this.writer);
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
                        switch (mensaje){
                            case "despegar":
                                this.writer.println("despegado");
                                break;
                            case "aterrizar":
//                                sleep(10000);
                                this.writer.println("aterrizado");
                                break;
                            case "puerta":
                                this.writer.println("esperando");
                                setMensaje("esperando");
//                                sleep(10000);
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
                                break;

                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                setMensaje(mensaje);
            }
        } catch (IOException e) {
            closeResources(this.echoSocket, this.reader, this.writer);
            e.printStackTrace();
        }
    }

    public void despegar(){
        this.mensaje = "despegar";
        solicitud.setSolicitud(mensaje);
        this.writer.println(GestionaArchivo.classAJson(solicitud));
    }

    public void aterrizar(){
        this.mensaje = "aterrizar";
        solicitud.setSolicitud(mensaje);
        this.writer.println(GestionaArchivo.classAJson(solicitud));
    }

    public void puerta(){
        this.mensaje = "puerta";
        solicitud.setSolicitud(mensaje);
        this.writer.println(GestionaArchivo.classAJson(solicitud));
    }

    public void desconectar(){
        this.writer.println("desconectar");
        closeResources(this.echoSocket, this.reader, this.writer);

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

    private void closeResources(Socket socket, BufferedReader reader, PrintWriter writer){
        try {
            if (socket != null) socket.close();
            if (reader != null) reader.close();
            if (writer != null) writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
