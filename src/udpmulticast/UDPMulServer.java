/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udpmulticast;

import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nucciarelli Giorgio
 */
public class UDPMulServer {

    public static void main(String[] args) {
    public static final String BLU = "\u001B[34m";
    //colore del prompt del Client
    public static final String ROSSO = "\033[1;31m";
    //colore reset
    public static final String RESET = "\033[0m";
    //porta del Server
    int porta = 2000;
    //oggetto Socket UDP 
    DatagramSocket dSocket = null;

    //datagramma UDP ricevuto dal client
    DatagramPacket in;
    //datagramma UDP di risposta da inviare
    DatagramPacket out;
    //Buffer per il contenuto del segmento da ricevere
    byte[] inBuffer;

    //Indirizzo del gruppo Multicast UDP
    InetAddress groupAddress;
    //messaggio ricevuto
    String msgIn;
    //messaggio da inviare
    String msgOut;

    
        try {
            
            System.out.println(BLU + "SERVER UDP" + RESET);
        //1) SERVER IN ASCOLTO 
        //si crea il socket e si associa alla porta specifica
        dSocket = new DatagramSocket(porta);
        System.out.println(BLU + "Apertura porta in corso!" + RESET);

        while (true) {
                try {
                    //si prepara il buffer per il messaggio da ricevere
                    inBuffer = new byte[256];
                    
                    //2) RICEZIONE MESSAGGIO DEL CLIENT
                    //si crea un datagramma UDP in cui trasportare il buffer per tutta la sua lunghezza
                    in = new DatagramPacket(inBuffer, inBuffer.length);
                    //si attende il pacchetto dal client
                    dSocket.receive(in);
                    
                    //si recupera l'indirizzo IP e la porta UDP del client
                    InetAddress indirizzoClient = in.getAddress();
                    int portaClient = in.getPort();
                    
                    //si stampa a video il messaggio ricevuto dal client
                    msgIn = new String(in.getData(), 0, in.getLength());
                    System.out.println(ROSSO + "Messaggio ricevuto dal client " + indirizzoClient
                            + ":" + portaClient + "\n\t" + msgIn + RESET);
                    
                    //3)RISPOSTA AL CLIENT
                    //si prepara il datagramma con i dati da inviare
                    msgOut = "Ricevuta richiesta!";
                    out = new DatagramPacket(msgOut.getBytes(), msgOut.length(), indirizzoClient, portaClient);
                    
                    //si inviano i dati
                    dSocket.send(out);
                    System.out.println(BLU + "Spedito messaggio al client: " + msgOut + RESET);
                    
                    //4)INVIO MESSAGGIO AL GRUPPO DOPO UNA SOSPENSIONE
                    //IP gruppo
                    groupAddress = InetAddress.getByName("239.255.255.250");
                    //si inizializza la porta del gruppo
                    int groupPort = 1900;
                    
                    //si prepara il datagramma con i dati da inviare al gruppo
                    msgOut = "Benvenuti a tutti!";
                    
                    out = new DatagramPacket(msgOut.getBytes(), msgOut.length(), groupAddress, groupPort);
                    
                    //invio dati
                    dSocket.send(out);
                    System.out.println(BLU + "Spedito messaggio al gruppo: " + msgOut + RESET);
                } catch (IOException ex) {
                    Logger.getLogger(UDPMulServer.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }catch (BindException ex) {
            Logger.getLogger(UDPMulServer.class.getName()).log(Level.SEVERE, null, ex);
        System.err.println("Porta già in uso");
    }
    catch (SocketException ex) {
            Logger.getLogger(UDPMulServer.class.getName()).log(Level.SEVERE, null, ex);
        System.err.println("Errore di creazione del socket e apertura del server");
    }
    catch (UnknownHostException ex) {
            Logger.getLogger(UDPMulServer.class.getName()).log(Level.SEVERE, null, ex);
        System.err.println("Errore di risoluzione");
    }
    catch (IOException ex) {
            Logger.getLogger(UDPMulServer.class.getName()).log(Level.SEVERE, null, ex);
        System.err.println("Errore di I/O");
    }

    
        finally{
           if (dSocket != null) {
            dSocket.close();
        }
    }
}

}

