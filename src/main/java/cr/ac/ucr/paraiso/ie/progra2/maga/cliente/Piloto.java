package cr.ac.ucr.paraiso.ie.progra2.maga.cliente;

import javafx.scene.control.TextArea;

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

    public Piloto(int puerto, String id){
        this.puerto = puerto;
        try {
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
            while ((respuesta = reader.readLine()) != null) {
                System.out.println(respuesta);
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

}
