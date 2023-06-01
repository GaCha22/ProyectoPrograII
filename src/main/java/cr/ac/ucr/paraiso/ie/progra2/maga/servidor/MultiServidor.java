package cr.ac.ucr.paraiso.ie.progra2.maga.servidor;

import java.io.IOException;
import java.net.ServerSocket;

public class MultiServidor extends Thread{
    boolean escuchando = true;
    @Override
    public void run() {
        ServerSocket serverSocket = null;

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

    @Override
    public void interrupt() {
        escuchando = false;
    }
}
