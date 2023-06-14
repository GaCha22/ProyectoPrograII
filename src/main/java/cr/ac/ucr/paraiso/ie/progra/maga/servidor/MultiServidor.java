package cr.ac.ucr.paraiso.ie.progra.maga.servidor;

import cr.ac.ucr.paraiso.ie.progra.maga.model.Aeropuerto;
import cr.ac.ucr.paraiso.ie.progra.maga.model.Solicitud;
import cr.ac.ucr.paraiso.ie.progra.maga.model.Vuelo;
import cr.ac.ucr.paraiso.ie.progra.maga.service.GestionaArchivo;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class MultiServidor extends Thread{
    private boolean escuchando = true;
    public static Aeropuerto aeropuertoServer;
    private static Queue<MultiServidorHilo> clientsInQueue = new ArrayDeque<>();
    private static Queue<Solicitud> solicitudes = new ArrayDeque<>();
    private static Queue<MultiServidorHilo> listaDeEsperaPistas = new ArrayDeque<>();
    private static Queue<MultiServidorHilo> listaDeEsperaPuertas = new ArrayDeque<>();
    private static PropertyChangeSupport propertyChangeSupport;
    private static String mensaje;

    public MultiServidor(Aeropuerto aeropuerto) {
        aeropuertoServer = aeropuerto;
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(9999);
            System.out.println("Servidor activo: " + serverSocket.getLocalPort());
            while(escuchando){
                Socket socketCliente = serverSocket.accept();
                MultiServidorHilo cliente = new MultiServidorHilo(socketCliente, getIdVuelo(socketCliente));
                cliente.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Vuelo getIdVuelo(Socket clientSocket){
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String id = reader.readLine();
            return GestionaArchivo.jsonAVuelo(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addClientsInQueue(MultiServidorHilo client) {
        MultiServidor.clientsInQueue.offer(client);
    }

    public static MultiServidorHilo removeClientInQueue() {
        return MultiServidor.clientsInQueue.poll();
    }

    public static Queue<MultiServidorHilo> getClientsInQueue() {
        return clientsInQueue;
    }

    public static void addSolicitudesInQueue(Solicitud solicitud){
        MultiServidor.solicitudes.offer(solicitud);
    }

    public static Solicitud removeSolicitudInQueue() {
        return MultiServidor.solicitudes.poll();
    }

    public static Solicitud peekSolicitudInQueue(){
        return solicitudes.peek();
    }

    public static Queue<Solicitud> getSolicitudes() {
        return solicitudes;
    }

    public static void addListaEsperaPistas(MultiServidorHilo client) {
        MultiServidor.listaDeEsperaPistas.offer(client);
    }
    public static MultiServidorHilo removeListaEsperaPistas() {
        return MultiServidor.listaDeEsperaPistas.poll();
    }
    public static Queue<MultiServidorHilo> getListaDeEsperaPistas() {
        return listaDeEsperaPistas;
    }

    public static void addListaEsperaPuertas(MultiServidorHilo client) {
        MultiServidor.listaDeEsperaPuertas.offer(client);
    }

    public static MultiServidorHilo removeListaEsperaPuertas() {
        return MultiServidor.listaDeEsperaPuertas.poll();
    }
    public static Queue<MultiServidorHilo> getListaDeEsperaPuertas() {
        return listaDeEsperaPuertas;
    }

    public void agregarPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removerPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public static void setMensaje(String nuevoMensaje) {
        String viejoMensaje = mensaje;
        mensaje = nuevoMensaje;
        propertyChangeSupport.firePropertyChange("mensaje", viejoMensaje, nuevoMensaje);
    }
}
