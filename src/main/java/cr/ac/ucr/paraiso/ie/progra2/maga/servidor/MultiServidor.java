package cr.ac.ucr.paraiso.ie.progra2.maga.servidor;

import java.io.IOException;
import java.net.ServerSocket;

public class MultiServidor{
    ServerSocket serverSocket;
    public MultiServidor(int socket){
        try {
            serverSocket = new ServerSocket(socket);
            System.out.println("Servidor activo: " + serverSocket.getLocalPort());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void startServer() {
            while(!serverSocket.isClosed()){
                try {
                    new Thread(new MultiServidorHilo(serverSocket.accept())).start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
    }
}
