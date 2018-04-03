/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MaranhonThreads;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import maranhon.DataBooks.DataClient.DataClient;
import maranhon.Model.IPModel;

/**
 *
 * @author Gledson
 */
public class ConectClient implements Runnable {

    private Socket client = null;
    private List<IPModel> IPs;
    private int controllerIps;
    private ObjectOutputStream controllerOutput;
    private ObjectInputStream controllerImput;

    public ConectClient(Socket client, List<IPModel> IPs, int controllerIps, ObjectOutputStream controllerOutput, ObjectInputStream controllerImput) {

        this.client = client;
        this.IPs = IPs;
        this.controllerIps = controllerIps;
        this.controllerImput = controllerImput;
        this.controllerOutput= controllerOutput;

    }

    @Override
    public void run() {

        try {

            System.out.println("Cliente cenectado!");
            System.out.println("ControllerIP: " + controllerIps);

            if (IPs.isEmpty()) {

                System.err.println("Not server conected");
                controllerOutput.writeObject(false);

            } else {

                controllerOutput.writeObject(true);
                
                String name = (String) controllerImput.readObject();
                String ammount = (String) controllerImput.readObject();
                
                System.out.println("alterar");
                DataClient change = new DataClient();
                change.changeClient(name, ammount);
                
                System.out.println("IPs size: "+IPs.size());
                
                controllerOutput.writeObject(((IPModel) IPs.get(controllerIps)).getIP());
                controllerOutput.writeObject(((IPModel) IPs.get(controllerIps)).getPort());
            }
            
            client.close();
            
        } catch (IOException ex) {
            Logger.getLogger(ConectClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConectClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
