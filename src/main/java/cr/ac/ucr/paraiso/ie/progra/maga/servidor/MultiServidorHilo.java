package cr.ac.ucr.paraiso.ie.progra.maga.servidor;

import cr.ac.ucr.paraiso.ie.progra.maga.logic.GeneraRandoms;
import cr.ac.ucr.paraiso.ie.progra.maga.model.Aeropuerto;
import cr.ac.ucr.paraiso.ie.progra.maga.model.Solicitud;
import cr.ac.ucr.paraiso.ie.progra.maga.model.Vuelo;
import cr.ac.ucr.paraiso.ie.progra.maga.service.GestionaArchivo;
import cr.ac.ucr.paraiso.ie.progra.maga.logic.Protocolo;
import javafx.application.Platform;
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
    private String respuesta;
    private final Vuelo vuelo;
    private Solicitud solicitud;
    private boolean listaEspera;
    Protocolo protocolo;

    public MultiServidorHilo(Socket socket, Vuelo vuelo) {
        try {
            this.vuelo = vuelo;
            this.protocolo = new Protocolo(vuelo);
            this.peticion = null;
            this.respuesta = "";
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
                        break;
                    case "despegado":
                        protocolo.liberarPista();
                        MultiServidor.setMensaje("consume");
                        MultiServidor.setMensaje("actualizar");
                        vuelo.setHoraSalida(LocalTime.now());
                        GestionaArchivo.escribirVuelo(vuelo, "reportes.json");
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
        listaEspera = false;
        if (respuesta.equals("lista de espera pista")){
            respuesta = "aceptar";
            MultiServidor.removeListaEsperaPistas();
        }else if (respuesta.equals("lista de espera puerta")){
            respuesta = "aceptar";
            MultiServidor.removeListaEsperaPuertas();

        }

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("");
        switch (solicitud.getSolicitud()){
            case "aterrizar":
                if (!protocolo.avionAterrizando()){
                    alert.setContentText("No hay pistas disponibles");
                    respuesta = "lista de espera pista";
                    Platform.runLater(alert::show);
                    MultiServidor.addListaEsperaPistas(MultiServidor.removeClientInQueue());
                    listaEspera = true;
                }
                break;
            case "despegar":
                if (!protocolo.avionDespegue()){
                    alert.setContentText("No hay pistas disponibles");
                    respuesta = "lista de espera pista";
                    Platform.runLater(alert::show);
                    MultiServidor.addListaEsperaPistas(MultiServidor.removeClientInQueue());
                    listaEspera = true;
                }
                break;
            case "puerta":
                if (!protocolo.avionAPuerta()){
                    alert.setContentText("No hay puertas disponibles");
                    respuesta = "lista de espera puerta";
                    Platform.runLater(alert::show);
                    MultiServidor.addListaEsperaPuertas(MultiServidor.removeClientInQueue());
                    listaEspera = true;
                }
                break;
        }

        if (alert.getContentText().equals("") && !listaEspera){
            respuesta = "aceptar";
            MultiServidor.getClientsInQueue().poll();
        }

        this.writer.println(respuesta);
        MultiServidor.removeSolicitudInQueue();
        MultiServidor.setMensaje("consume");
        MultiServidor.setMensaje("actualizar");
    }

    public void ponerEnEspera(){
        respuesta = "esperar";
        this.writer.println(respuesta);
        MultiServidor.addSolicitudesInQueue(MultiServidor.removeSolicitudInQueue());
        MultiServidor.getClientsInQueue().offer(MultiServidor.getClientsInQueue().poll());
        MultiServidor.setMensaje("consume");
        MultiServidor.setMensaje("actualizar");
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
