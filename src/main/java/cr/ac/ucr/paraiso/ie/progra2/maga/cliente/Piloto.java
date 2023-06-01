package cr.ac.ucr.paraiso.ie.progra2.maga.cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Piloto extends Thread{

    @Override
    public void run() {
        Socket echoSocket = null;
        PrintWriter writer = null;
        BufferedReader reader = null;

        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            echoSocket = new Socket(inetAddress,9999);
            writer = new PrintWriter(echoSocket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            String entrada = reader.readLine();
            System.out.println("Servidor: " + entrada);
            BufferedReader lectorTeclado = new BufferedReader(new InputStreamReader(System.in));
            reader.close();
            writer.close();
            lectorTeclado.close();
            echoSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
