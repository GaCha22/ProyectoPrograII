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

    Socket echoSocket;
    TextArea txtA;

    public Piloto(int puerto, TextArea textArea){
        this.txtA = textArea;
        this.puerto = puerto;
        try {
            echoSocket = new Socket("localhost", this.puerto);
            this.writer = new PrintWriter(echoSocket.getOutputStream(), true);
            this.reader = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        try {
            while (respuesta == null) {
                respuesta = reader.readLine();
            }
            System.out.println("Servidor: " + respuesta);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
