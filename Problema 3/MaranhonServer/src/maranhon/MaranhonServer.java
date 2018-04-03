/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maranhon;

import MaranhonThreads.ConectClient;
import MaranhonThreads.ConectSecondaryServer;
import MaranhonThreads.ConectServer;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import maranhon.Model.IPModel;
import maranhon.DataBooks.DataServer;

/**
 *
 * @author Gledson
 */
public class MaranhonServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        
        ServerSocket servidor = new ServerSocket(12345);
        System.out.println("Porta 12345 aberta!");
        
        DataServer IPList = new DataServer();
        boolean TestConection;

        List<IPModel> IPs = new ArrayList<IPModel>();

        int controllerIps = 0;
        int numConectedServers = 0;
        int controllerServers = 0;
        ObjectOutputStream controllerOutput;
        ObjectInputStream controllerImput;

        while (true) {
            
            //Iterator<IPModel> IPsIterator = IPs.iterator();
            

            System.out.println("Esperando Cliente/Servidor!");
            Socket client = servidor.accept();
            
            for(int i = 0; i < IPs.size(); i++) {
                System.out.println("start");
                
                int controllerIsAlive = 0;
                
                try {
                    
                    Socket isAlive = new Socket(((IPModel) IPs.get(i)).getIP(), ((IPModel) IPs.get(i)).getPort());
                    System.out.println(((IPModel) IPs.get(i)).getIP() + " " + ((IPModel) IPs.get(i)).getPort());
                    
                    controllerOutput = new ObjectOutputStream(isAlive.getOutputStream());
                    
                    controllerOutput.writeObject("3");
                    System.out.println("request sent");
                    
                    ObjectInputStream controllerImputIsAlive = new ObjectInputStream(isAlive.getInputStream());
                    
                    int selector = (int) controllerImputIsAlive.readObject();
                    System.out.println(IPs.size());

                } catch ( SocketException | EOFException ex) {
                    IPs.remove(i);
                    System.err.println("Server not alive. It was removed");
                    System.out.println(ex.getMessage());
                }
            }

            controllerOutput = new ObjectOutputStream(client.getOutputStream());
            controllerImput = new ObjectInputStream(client.getInputStream());

            String selector = (String) controllerImput.readObject();

            if (selector.equals("1")) {
                
                if (controllerIps < IPs.size() - 1) {
                    controllerIps++;
                } else {
                    controllerIps = 0;
                }
                
                ConectClient conectClient = new ConectClient(client ,IPs,  controllerIps, controllerOutput, controllerImput);
                new Thread(conectClient).start();

            } else if (selector.equals("2")) {
                ConectSecondaryServer conectSecondaryServer = new ConectSecondaryServer(client, IPs, controllerIps, controllerOutput, controllerImput);
                new Thread(conectSecondaryServer).start();
            }
        }
    }
}