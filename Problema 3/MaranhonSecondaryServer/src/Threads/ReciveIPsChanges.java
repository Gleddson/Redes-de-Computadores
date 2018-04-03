/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

import Model.IPModel;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import static maranhonSecondaryServer.MaranhonSecondaryServer.IPs;

/**
 *
 * @author Gledson
 */
public class ReciveIPsChanges implements Runnable {

    private Socket client;
    private ObjectInputStream controller;

    public ReciveIPsChanges(Socket client, ObjectInputStream controller) {

        this.client = client;
        this.controller = controller;

    }

    @Override
    public void run() {
        
        try {
        
            String action = (String) controller.readObject();
            
            if (action.equals("1")) {
                
                String ip = (String) controller.readObject();
                int port = (int) controller.readObject();
                
                IPModel IP = new IPModel();
                IP.setIP(ip);
                IP.setPort(port);

                IPs.add(IP);
                
                System.err.println("IP add");
                
            } 
            client.close();

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ReciveIPsChanges.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
