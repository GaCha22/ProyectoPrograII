package cr.ac.ucr.paraiso.ie.progra2.maga.cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Vuelo extends Thread{

    private static final int EN_ESPERA = 0;
    private static final int  ATERRIZAR = 1;
    private static final int PUERTA = 2;
    private static final int DESPEGAR = 3;
    private int estado = EN_ESPERA;

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

    public String estadoAvion(String entrada){
        String salida = null;
        if(estado == EN_ESPERA)
            salida = "Avión en espera";
        return salida;
    }

}
