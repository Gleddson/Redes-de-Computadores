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
import maranhon.Model.IPModel;

/**
 *
 * @author Gledson
 */
public class ConectSecondaryServer implements Runnable {

    private Socket client = null;
    private List<IPModel> IPs;
    private int controllerIps;
    private ObjectOutputStream controllerOutput;
    private ObjectInputStream controllerImput;

    public ConectSecondaryServer(Socket client, List<IPModel> IPs, int controllerIps, ObjectOutputStream controllerOutput, ObjectInputStream controllerImput) {
        this.client = client;
        this.IPs = IPs;
        this.controllerIps = controllerIps;
        this.controllerImput = controllerImput;
        this.controllerOutput = controllerOutput;
    }

    @Override
    public void run() {
        System.out.println("Servidor connectado!");

        IPModel endereco = new IPModel();

        int port = client.getPort();

        endereco.setIP(client.getInetAddress().getHostAddress());
        endereco.setPort(port);
        
        try {
            //enviar porta ao servidor
            controllerOutput.writeInt(client.getPort());
        } catch (IOException ex) {
            Logger.getLogger(ConectSecondaryServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (IPs.isEmpty()) {

            try {
                
                controllerOutput.writeObject(1);
                client.close();
                System.out.println("completed operation: First conection");
                
                IPs.add(endereco);

            } catch (IOException ex) {
                Logger.getLogger(ConectSecondaryServer.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            try {

                controllerOutput.writeObject(2);

                Socket catchBooks;

                catchBooks = new Socket(((IPModel) IPs.get(0)).getIP(), ((IPModel) IPs.get(0)).getPort());
                ObjectOutputStream controllerOutput2 = new ObjectOutputStream(catchBooks.getOutputStream());
                controllerOutput2.writeObject("2");

                ObjectInputStream controllerImput2 = new ObjectInputStream(catchBooks.getInputStream());
                int ammountBooks = (int) controllerImput2.readObject();
                System.out.println("peguei a quantidade livros");

                String[][] dataBooks = (String[][]) controllerImput2.readObject();
                System.out.println("PEGUEI TODOS OS LIVROS");

                ObjectOutputStream sendAllBooks = new ObjectOutputStream(client.getOutputStream());
                sendAllBooks.writeObject(dataBooks);
                client.close();
                //Test afther
                System.out.println("completed operation: Other conection");
                
                IPs.add(endereco);
                
                ConectServer cenectAll = new ConectServer(IPs);
                new Thread(cenectAll).start();

            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(ConectSecondaryServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}