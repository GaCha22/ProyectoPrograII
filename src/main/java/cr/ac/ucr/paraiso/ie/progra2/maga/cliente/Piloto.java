package cr.ac.ucr.paraiso.ie.progra2.maga.cliente;

import cr.ac.ucr.paraiso.ie.progra2.maga.model.Aeronave;
import javafx.scene.control.TextArea;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Piloto{
    private BufferedWriter writer;
    private BufferedReader reader;
    private String respuesta;
    private int puerto;
    private Socket echoSocket;

    public Piloto(int puerto) {
        this.puerto = puerto;
        try {
            echoSocket = new Socket("localhost", puerto);
            this.writer = new BufferedWriter(new OutputStreamWriter(echoSocket.getOutputStream()));
            this.reader = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        }catch (IOException e) {
            e.printStackTrace();
            closeResources(echoSocket, reader, writer);
        }
    }

    public void start(TextArea textArea) {

        try {
            respuesta = reader.readLine();
            textArea.setText("Servidor: " + respuesta);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void enviarPetición(Aeronave aeronave){
        try {
            writer.write(aeronave.toString());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public String getRespuesta() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (echoSocket.isConnected()){
                    try {
                        String respuesta = reader.readLine();
                    }catch (IOException e){
                        e.printStackTrace();
                        System.out.println("Error reciviendo el mensaje del servidor");
                        closeResources(echoSocket, reader, writer);
                        break;
                    }
                }
            }
        });
        return "";
    }

    private void closeResources(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        try {
            if (socket != null) {
                socket.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
