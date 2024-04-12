/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udpmulticast;

import static UDPMulticast.UDPMulServer.ANSI_BLUE;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nucciarelli Giorgio
 */
public class UDPClientMulticast {

    public static void main(String[] args) {
    public static final String BLU = "\u001B[34m";
    public static final String ROSSO = "\033[1;31m";
    public static final String VERDE = "\033[4;32m";
    public static final String RESET = "\033[0m";
    int porta = 3455;
    int portaGruppo = 3300;
    InetAddress indirizzoServer;
    DatagramSocket dSocket = null;
    MulticastSocket mSocket = null;
    InetAddress gruppo;
    DatagramPacket outPacket;
    DatagramPacket inPacket;
    byte[] buffer = new byte[1024];
    String outM = "Richiesta comunicazione";
    String messageIn;

    
        try{
        System.out.println(ROSSO + "CLIENT UDP" + RESET);
        indirizzoServer = InetAddress.getLocalHost();
        System.out.println(ROSSO + "Indirizzo del server trovato!" + RESET);
        dSocket = new DatagramSocket();
        outPacket = new DatagramPacket(outM.getBytes(), outM.length(), serverAddress, porta);
        dSocket.send(outPacket);
        System.out.println(ROSSO + "Richiesta al server inviata!" + RESET);
        inPacket = new DatagramPacket(buffer, buffer.length);
        dSocket.receive(inPacket);
        outM = new String(inPacket.getData(), 0, inPacket.getLength());
        System.out.println(BLU + "Lettura dei dati ricevuti dal server" + RESET);

        messageIn = new String(inPacket.getData(), 0, inPacket.getLength());
        System.out.println(BLU + "Messaggio ricevuto dal server " + indirizzoServer
                + ":" + porta + "\n\t" + messageIn + RESET);
        mSocket = new MulticastSocket(portaGruppo);
        group = InetAddress.getByName("239.255.255.250");
        mSocket.joinGroup(gruppo);
        inPacket = new DatagramPacket(buffer, buffer.length);
        mSocket.receive(inPacket);
        messageIn = new String(inPacket.getData(), 0, inPacket.getLength());
        System.out.println(VERDE + "Lettura dei dati ricevuti dai partecipanti al gruppo" + RESET);
        System.out.println(VERDE + "Messaggio ricevuto dal gruppo " + gruppo
                + ":" + portaGruppo + "\n\t" + messageIn + RESET);
        mSocket.leaveGroup(gruppo);
    }
    catch (UnknownHostException ex) {
                    Logger.getLogger(UDPClientMulticast.class.getName()).log(Level.SEVERE, null, ex);
        System.err.println("Errore di risoluzione");
    }
    catch (SocketException ex) {
                    Logger.getLogger(UDPClientMulticast.class.getName()).log(Level.SEVERE, null, ex);
        System.err.println("Errore di creazione socket");
    }
    catch (IOException ex) {
                    Logger.getLogger(UDPClientMulticast.class.getName()).log(Level.SEVERE, null, ex);
        System.err.println("Errore di I/O");
    }

    
        finally{
                    if (dSocket != null) {
            dSocket.close();
        }
        if (mSocket != null) {
            mSocket.close();
        }
    }
}


    

