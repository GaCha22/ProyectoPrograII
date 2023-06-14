package cr.ac.ucr.paraiso.ie.progra.maga.servidor;

import cr.ac.ucr.paraiso.ie.progra.maga.logic.GeneraRandoms;
import cr.ac.ucr.paraiso.ie.progra.maga.model.Aeropuerto;
import cr.ac.ucr.paraiso.ie.progra.maga.model.Solicitud;
import cr.ac.ucr.paraiso.ie.progra.maga.model.Vuelo;
import cr.ac.ucr.paraiso.ie.progra.maga.service.GestionaArchivo;
import cr.ac.ucr.paraiso.ie.progra.maga.logic.Protocolo;
import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalTime;

public class MultiServidorHilo extends Thread{
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private String peticion;
    private final Vuelo vuelo;
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
            while ((peticion = reader.readLine()) != null && !peticion.equals("desconectar")) {
                switch (peticion){
                    case "en puerta":
                    case "despegado":
                        vuelo.setHoraSalida(LocalTime.now());
                        GestionaArchivo.escribirVuelo(vuelo, "reportes.json");
                        protocolo.liberarPista();
                        break;
                    case "esperando":
                        protocolo.liberarPuerta();
                        MultiServidor.setMensaje("consume");
                        MultiServidor.setMensaje("actualizar");
                        break;
                    case "aterrizado":
                        vuelo.setHoraLlegada(LocalTime.now());
                        GestionaArchivo.escribirVuelo(vuelo, "reportes.json");
                        cambiarDatosVuelo();
                        break;
                    default:
                        solicitud = GestionaArchivo.jsonASolicitud(peticion);
                        MultiServidor.addSolicitudesInQueue(solicitud);
                        System.out.println(peticion);
                        MultiServidor.setMensaje("consume");
                        MultiServidor.setMensaje("actualizar");
                        MultiServidor.addClientsInQueue(this);
                }
            }
            closeResources(this.socket, this.reader, this.writer);
        }catch (SocketException e){
            closeResources(this.socket, this.reader, this.writer);
        }catch (IOException e) {
            closeResources(this.socket, this.reader, this.writer);
            e.printStackTrace();
        }

    }

    private void cambiarDatosVuelo() {
        vuelo.setHoraSalida(null);
        vuelo.setHoraLlegada(null);
        vuelo.setAeropuertoOrigen(MultiServidor.aeropuertoServer);
        vuelo.setAeropuertoDestino(new Aeropuerto(GeneraRandoms.generaAeropuertoRandom()));
    }

    public void aceptarSolicitud(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText("");
        switch (solicitud.getSolicitud()){
            case "aterrizar":
                if (!protocolo.avionAterrizando()){
                    alert.setContentText("No hay pistas disponibles");
                    alert.showAndWait();
                    MultiServidor.addListaEsperaPistas(MultiServidor.removeClientInQueue());
                }
                break;
            case "despegar":
                if (!protocolo.avionDespegue()){
                    alert.setContentText("No hay pistas disponibles");
                    alert.showAndWait();
                    MultiServidor.addListaEsperaPistas(MultiServidor.removeClientInQueue());
                }
                break;
            case "puerta":
                if (!protocolo.avionAPuerta()){
                    alert.setContentText("No hay puertas disponibles");
                    alert.showAndWait();
                    MultiServidor.addListaEsperaPuertas(MultiServidor.removeClientInQueue());
                }
                break;
        }

        if (alert.getContentText().equals("")){
            this.writer.println("aceptar");
            MultiServidor.getClientsInQueue().poll();
            MultiServidor.removeSolicitudInQueue();
        }else {
            this.writer.println("lista de espera");
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
