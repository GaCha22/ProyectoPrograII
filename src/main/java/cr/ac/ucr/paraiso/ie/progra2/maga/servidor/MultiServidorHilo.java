package cr.ac.ucr.paraiso.ie.progra2.maga.servidor;

import cr.ac.ucr.paraiso.ie.progra2.maga.logic.Protocol;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Vuelo;

import java.io.*;
import java.net.Socket;
import java.util.Queue;

public class  MultiServidorHilo implements Runnable {
    private Socket socket;
    BufferedReader reader;
    BufferedWriter writer;
    Protocol protocol = new Protocol();

    public MultiServidorHilo(Socket socket) {
        this.socket = socket;
        try {
            reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
        } catch (IOException e) {
            closeResources(socket, writer, reader);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            writer.write("Cliente conectado con el servidor");
            while (socket.isConnected()){
                String peticion = reader.readLine();

            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void agregarPetición(Queue<Vuelo> vuelos){

    }

    private void closeResources(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedReader){
        try {
            if (socket != null) {
                socket.close();
            }
            if (bufferedReader != null){
                bufferedReader.close();
            }
            if (bufferedWriter != null){
                bufferedWriter.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
