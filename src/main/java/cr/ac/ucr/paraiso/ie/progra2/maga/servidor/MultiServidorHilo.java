package cr.ac.ucr.paraiso.ie.progra2.maga.servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MultiServidorHilo extends Thread{
    private Socket socket;
    public MultiServidorHilo(Socket socket, String name) {
        super(name);
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            PrintWriter writer = new PrintWriter(this.socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            writer.println("Cliente conectado con el servidor");
            writer.close();
            reader.close();
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
