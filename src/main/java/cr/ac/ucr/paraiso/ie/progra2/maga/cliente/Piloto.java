package cr.ac.ucr.paraiso.ie.progra2.maga.cliente;

import cr.ac.ucr.paraiso.ie.progra2.maga.model.Vuelo;
import cr.ac.ucr.paraiso.ie.progra2.maga.service.GestionaArchivo;

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
    private String mensaje;
    public final static Vuelo vuelo = GestionaArchivo.leerVuelo("vuelo.json");
    private PropertyChangeSupport propertyChangeSupport;

    public Piloto(int puerto){
        this.puerto = puerto;
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
                respuesta = respuesta.replaceAll("aceptar ", "");
                setMensaje(respuesta);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void despegar(){
        this.writer.println("despegar");
    }

    public void aterrizar(){
        this.writer.println("aterrizar");
    }

    public void puerta(){
        this.writer.println("puerta");
    }

    public void agregarPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removerPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public void setMensaje(String nuevoMensaje) {
        String viejoMensaje = mensaje;
        mensaje = nuevoMensaje;
        propertyChangeSupport.firePropertyChange("mensaje", viejoMensaje, nuevoMensaje);
    }

}
