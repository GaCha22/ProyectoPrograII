package cr.ac.ucr.paraiso.ie.progra2.maga.servidor;

import cr.ac.ucr.paraiso.ie.progra2.maga.model.Aeropuerto;

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

    public MultiServidor(Aeropuerto aeropuerto) {
        aeropuertoServer = aeropuerto;
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(9999);
            System.out.println("Servidor activo: " + serverSocket.getLocalPort());
            while(escuchando){
                Socket socketCliente = serverSocket.accept();
                MultiServidorHilo cliente = new MultiServidorHilo(socketCliente);
                cliente.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addClientsInQueue(MultiServidorHilo client) {
        MultiServidor.clientsInQueue.offer(client);
    }

    public static void removeClientInQueue() {
        MultiServidor.clientsInQueue.poll();
    }

    public static Queue<MultiServidorHilo> getClientsInQueue() {
        return clientsInQueue;
    }
}
