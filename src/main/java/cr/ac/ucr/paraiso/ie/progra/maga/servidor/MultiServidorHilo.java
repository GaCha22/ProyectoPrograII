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

    //Se crean las instancias que se van a necesitar
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private String peticion;
    private String respuesta;
    private final Vuelo vuelo;
    private Solicitud solicitud;
    private boolean listaEspera;
    Protocolo protocolo;

    public MultiServidorHilo(Socket socket, Vuelo vuelo) { //Constructor que inicializa las variables
        try {
            this.vuelo = vuelo;
            this.protocolo = new Protocolo(vuelo);
            this.peticion = null;
            this.respuesta = "";
            this.socket = socket;
            this.writer = new PrintWriter(this.socket.getOutputStream(), true); //Se crea un PrintWriter para enviar datos al cliente por medio del socket.
            this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));// Se crea un BufferedReader para leer datos recibidos del cliente a través del socket.
        } catch (IOException e) {
            closeResources(this.socket, this.reader, this.writer); //Si se produce una excepción se cierrran los recursos.
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            writer.println("Cliente conectado con el servidor"); //Se confirma la conexión con el servidor
            while ((peticion = reader.readLine()) != null && !peticion.equals("desconectar")) { //Ciclo que permite leer las peteciones del cliente y procesarla mientras no sean null o "desconectar"
                switch (peticion){
                    case "en puerta":
                        break; //De esta manera no entra al default
                    case "despegado":
                        protocolo.liberarPista(); //Se libera una pista cuando el avión ha despegado
                        MultiServidor.setMensaje("consume");
                        MultiServidor.setMensaje("actualizar");
                        vuelo.setHoraSalida(LocalTime.now()); //Se toma la hora de la máquina para setearla como hora de salida del vuelo.
                        GestionaArchivo.escribirVuelo(vuelo, "reportes.json"); //Se escribe en el archivo reportes.json el nuevo vuelo que ha despegado.
                        break;
                    case "esperando":
                        protocolo.liberarPuerta(); //Se libera una puerta cuando el estado es esperando.
                        MultiServidor.setMensaje("consume");
                        MultiServidor.setMensaje("actualizar");
                        break;
                    case "aterrizado":
                        vuelo.setHoraLlegada(LocalTime.now()); //Se fija la hora de llegada, tomandola de la máquina.
                        GestionaArchivo.escribirVuelo(vuelo, "reportes.json"); //Se escribe en el archivo reportes.json el vuelo que ha llegado a su destino.
                        cambiarDatosVuelo(); //se inicializan nuevamente los datos de vuelo
                        break;
                    default:
                        solicitud = GestionaArchivo.jsonASolicitud(peticion); //Se convierte la petición en un objeto Solicitud y se agrega a la cola de solicitudes. De esa forma se muestra en el Table View.
                        MultiServidor.addSolicitudesInQueue(solicitud);
                        MultiServidor.setMensaje("consume");
                        MultiServidor.setMensaje("actualizar");
                        MultiServidor.addClientsInQueue(this);
                }
            }
            closeResources(this.socket, this.reader, this.writer); //Se cierran los recursos cuando se termina de leer peticiones.
        }catch (SocketException e){
            closeResources(this.socket, this.reader, this.writer);
        }catch (IOException e) {
            closeResources(this.socket, this.reader, this.writer);
            e.printStackTrace();
        }

    }

    private void cambiarDatosVuelo() { //Se inicializan los datos de vuelo por nuevos valores.
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

            Alert alert = new Alert(Alert.AlertType.WARNING); //Se crea un objeto Alert para mostrar mensajes de advertencia.
            alert.setHeaderText(null);
            alert.setContentText("");


        switch (solicitud.getSolicitud()){ //Se evaluan las solicitudes para poder seguir con las instrucciones según corresponda
            case "aterrizar":
                if (!protocolo.avionAterrizando()){  //Protocolo evalua la disponibilidad de las pistas
                    alert.setContentText("No hay pistas disponibles"); //se lanza la alerta indicando que no hay pistas disponibles.
                    respuesta = "lista de espera pista";
                    Platform.runLater(alert::show);
                    MultiServidor.addListaEsperaPistas(MultiServidor.removeClientInQueue());
                    listaEspera = true;
                }
                break;
            case "despegar":
                if (!protocolo.avionDespegue()){ //Protocolo evalua si hay pistas disponibles, en caso de que no se lanza una alerta
                    alert.setContentText("No hay pistas disponibles");
                    respuesta = "lista de espera pista";
                    Platform.runLater(alert::show);
                    MultiServidor.addListaEsperaPistas(MultiServidor.removeClientInQueue());
                    listaEspera = true;
                }
                break;
            case "puerta":
                if (!protocolo.avionAPuerta()){  // Verifica si hay puertas disponibles en el protocolo
                    alert.setContentText("No hay puertas disponibles");
                    respuesta = "lista de espera puerta";
                    Platform.runLater(alert::show);
                    MultiServidor.addListaEsperaPuertas(MultiServidor.removeClientInQueue());  // Agrega al cliente actual a la lista de espera de puertas
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
        respuesta = "esperar";     //Se ctualiza el estado de la respuesta
        this.writer.println(respuesta);
        MultiServidor.addSolicitudesInQueue(MultiServidor.removeSolicitudInQueue());   // Agregar una solicitud a la cola de solicitudes
        MultiServidor.getClientsInQueue().offer(MultiServidor.getClientsInQueue().poll()); // Mover el cliente actual a la parte posterior de la cola de clientes para ponerlo en espera.

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
