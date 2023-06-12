package cr.ac.ucr.paraiso.ie.progra2.maga.servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MultiServidorHilo extends Thread{
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private String peticion;
    public MultiServidorHilo(Socket socket, String name) {
        super(name);
        try {

            this.peticion = null;
            this.socket = socket;
            this.writer = new PrintWriter(this.socket.getOutputStream(), true);
            this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        } catch (IOException e) {
            closeResources(this.socket, this.reader, this.writer);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        synchronized (this) {
            try {
                writer.println("Cliente " + this.getName() + " conectado con el servidor");
                while ((peticion = reader.readLine()) != null) {
                    System.out.println(peticion);
                    MultiServidor.addClientsInQueue(this);
                    if (MultiServidor.getClientsInQueue().peek() != this) {
                        wait();
                    }
                }
            } catch (IOException | InterruptedException e) {
                closeResources(this.socket, this.reader, this.writer);
                e.printStackTrace();
            }
        }
    }

    public void aceptarSolicitud(){
        synchronized (MultiServidor.getClientsInQueue()) {
            this.writer.println("aceptar " + peticion);
            MultiServidor.getClientsInQueue().poll();
        }
    }

    public void ponerEnEspera(){
        synchronized (MultiServidor.getClientsInQueue()) {
            this.writer.println("espera");
            MultiServidor.getClientsInQueue().offer(MultiServidor.getClientsInQueue().poll());
        }
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
