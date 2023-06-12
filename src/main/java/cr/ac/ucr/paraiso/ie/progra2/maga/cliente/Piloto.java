package cr.ac.ucr.paraiso.ie.progra2.maga.cliente;

import javafx.scene.control.TextArea;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Piloto extends Thread{
    private int puerto;
    private PrintWriter writer;
    private BufferedReader reader;
    private String respuesta;
    private Socket echoSocket;
    private String mensaje;
    private PropertyChangeSupport propertyChangeSupport;

    public Piloto(int puerto, String id){
        this.puerto = puerto;
        try {
            propertyChangeSupport = new PropertyChangeSupport(this);
            respuesta = null;
            echoSocket = new Socket("localhost", this.puerto);
            this.writer = new PrintWriter(echoSocket.getOutputStream(), true);
            this.reader = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            writer.println(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            respuesta = reader.readLine();
            System.out.println("Servidor: " + respuesta);
            respuesta = null;
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
