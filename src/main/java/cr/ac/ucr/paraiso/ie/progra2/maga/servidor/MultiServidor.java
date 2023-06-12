package cr.ac.ucr.paraiso.ie.progra2.maga.servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class MultiServidor extends Thread{
    private boolean escuchando = true;
    private Map<String, MultiServidorHilo> clientes = new HashMap<>();
    private static Queue<MultiServidorHilo> clientsInQueue = new ArrayDeque<>();

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(9999);
            System.out.println("Servidor activo: " + serverSocket.getLocalPort());
            while(escuchando){
                Socket socketCliente = serverSocket.accept();
                String clienteId = getClientId(socketCliente);
                MultiServidorHilo cliente = new MultiServidorHilo(socketCliente, clienteId);
                cliente.start();
                clientes.put(clienteId, cliente);
                System.out.println(clientes.values());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getClientId(Socket socketCliente){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
            return reader.readLine();
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
