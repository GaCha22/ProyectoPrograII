package cr.ac.ucr.paraiso.ie.progra2.maga.servidor;

import cr.ac.ucr.paraiso.ie.progra2.maga.logic.Protocolo;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Solicitud;
import cr.ac.ucr.paraiso.ie.progra2.maga.model.Vuelo;
import cr.ac.ucr.paraiso.ie.progra2.maga.service.GestionaArchivo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalTime;

public class MultiServidorHilo extends Thread{
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private String peticion;
    private Vuelo vuelo;
    private Solicitud solicitud;
    Protocolo protocolo;

    public MultiServidorHilo(Socket socket, Vuelo vuelo) {
        try {
            this.vuelo = vuelo;
            this.protocolo = new Protocolo(vuelo);
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
        try {
            writer.println("Cliente conectado con el servidor");
            while ((peticion = reader.readLine()) != null) {

                switch (peticion){
                    case "en puerta":
                    case "despegando":
                        protocolo.liberarPista();
                        break;
                    case "esperando":
                        protocolo.liberarPuerta();
                        MultiServidor.setMensaje("consume");
                        MultiServidor.setMensaje("actualizar");
                        break;
                    case "aterrizado":
                        break;
                    default:
                        solicitud = GestionaArchivo.jsonASolicitud(peticion);
                        MultiServidor.setMensaje(peticion);
                        System.out.println(peticion);
                        MultiServidor.addClientsInQueue(this);
                }
            }
        } catch (IOException e) {
            closeResources(this.socket, this.reader, this.writer);
            e.printStackTrace();
        }

    }

    public void aceptarSolicitud(){
        switch (solicitud.getSolicitud()){
            case "aterrizar":
                vuelo.setHoraLlegada(LocalTime.now());
                GestionaArchivo.escribirVuelo(vuelo, "reportes.json");
                protocolo.avionAterrizando();
                break;
            case "despegar":
                vuelo.setHoraSalida(LocalTime.now());
                GestionaArchivo.escribirVuelo(vuelo, "reportes.json");
                protocolo.avionDespegue();
                break;
            case "puerta":
                protocolo.avionAPuerta();
                break;
        }
        this.writer.println("aceptar");
        MultiServidor.getClientsInQueue().poll();
        try {
            sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        MultiServidor.setMensaje("consume");
        MultiServidor.setMensaje("actualizar");
    }

    public void ponerEnEspera(){
        this.writer.println("esperar");
        MultiServidor.getClientsInQueue().offer(MultiServidor.getClientsInQueue().poll());
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
