package cr.ac.ucr.paraiso.ie.progra2.maga.cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Vuelo {

    public static void main(String[] args) throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        Socket echoSocket = null;
        PrintWriter writer = null;
        BufferedReader reader = null;

        try {
            echoSocket = new Socket(inetAddress,9999);
            writer = new PrintWriter(echoSocket.getOutputStream(), true);
            reader = new BufferedReader(
                    new InputStreamReader(
                            echoSocket.getInputStream()));
            String entrada = reader.readLine();
            System.out.println("Servidor: " + entrada);
            String salida;
            BufferedReader lectorTeclado = new BufferedReader(
                    new InputStreamReader(System.in));
            while((salida = lectorTeclado.readLine()) != null){
                writer.println(salida);
                entrada = reader.readLine();
                System.out.println("Servidor: " + entrada);
            }//while
            reader.close();
            writer.close();
            lectorTeclado.close();
            echoSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
