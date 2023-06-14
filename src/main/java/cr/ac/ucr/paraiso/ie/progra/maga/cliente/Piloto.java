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
    public final Vuelo vuelo = GestionaArchivo.leerVuelo("vuelo.json"); //Se lee el vuelo en el archivo.
    private PropertyChangeSupport propertyChangeSupport;
    private Solicitud solicitud;

    //Constructor de piloto, el método permite la conexión del servidor, además de que
    //de inicializar el propertyChage para los futuros cambios que sucedan en el cliente.
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
                setMensaje(respuesta + " " + mensaje);
                if(respuesta.equals("aceptar")) {
                    try {
                        sleep(10_000);
                        switch (mensaje){
                            case "despegar":
                                this.writer.println("despegado");
                                break;
                            case "aterrizar":
                                this.writer.println("aterrizado");
                                break;
                            case "puerta":
                                this.writer.println("esperando");
                                setMensaje("esperando");
                                switch(vuelo.getAeronave().getTipo()) {
                                    case 1: //Comercial
                                        sleep(120_000); //2 minutos
                                        break;
                                    case 2: //Carga
                                        sleep(240_000); //4 minutos
                                        break;
                                    case 3: //Avioneta
                                        sleep(60_000); //1 minuto
                                        break;
                                }
                                break;

                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (respuesta.equals("lista de espera pista") || respuesta.equals("lista de espera puerta") || respuesta.equals("esperar")){
                    setMensaje(respuesta);
                }else {
                    setMensaje(mensaje);
                }
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

    public void agregarPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
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
