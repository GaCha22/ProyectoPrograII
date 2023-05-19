package cr.ac.ucr.paraiso.ie.progra2.maga.servidor;

import java.io.IOException;
import java.net.ServerSocket;

public class MultiServidor {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        boolean escuchando = true;

        try {
            serverSocket = new ServerSocket(9999);
            System.out.println("Servidor activo: " + serverSocket.getLocalPort());
            while(escuchando){
                new MultiServidorHilo(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
