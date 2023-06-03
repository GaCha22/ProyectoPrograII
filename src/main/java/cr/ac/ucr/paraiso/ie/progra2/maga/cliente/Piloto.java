package cr.ac.ucr.paraiso.ie.progra2.maga.cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Piloto extends Thread{
    private PrintWriter writer;
    private BufferedReader reader;
    private String respuesta;

    @Override
    public void run() {

        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            Socket echoSocket = new Socket(inetAddress, 9999);
            this.writer = new PrintWriter(echoSocket.getOutputStream(), true);
            this.reader = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            while (respuesta == null) {
                respuesta = reader.readLine();
            }
            System.out.println("Servidor: " + respuesta);
            BufferedReader lectorTeclado = new BufferedReader(new InputStreamReader(System.in));
            lectorTeclado.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getRespuesta() {
        return respuesta;
    }
}
