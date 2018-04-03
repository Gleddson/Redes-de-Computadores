/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maranhonSecondaryServer;

import ConfigureServer.Configuration;
import DataServer.Books;
import Model.IPModel;
import Threads.ChangeBooks;
import Threads.IsAlive;
import Threads.ReciveIPsChanges;
import Threads.SendBooks;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gledson
 */
public class MaranhonSecondaryServer {

    /**
     * 
     */
    public static Books books;
    public static int port;
    public static List<IPModel> IPs;

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        books = new Books();
        IPs = new ArrayList();
        
        Configuration configure  = new Configuration(books);
        port = configure.start();
        
        ServerSocket servidor = new ServerSocket(port);
        System.out.println("Porta " + port + " aberta!");

        ObjectInputStream controller;
        
        while (true) {
            
            Socket client = servidor.accept();
            System.out.println("Conected");
            
            for(int i = 0; i < IPs.size(); i++) {
                System.out.println("start");
                
                int controllerIsAlive = 0;
                
                try {
                    
                    Socket isAlive = new Socket(((IPModel) IPs.get(i)).getIP(), ((IPModel) IPs.get(i)).getPort());
                    System.out.println(((IPModel) IPs.get(i)).getIP() + " " + ((IPModel) IPs.get(i)).getPort());
                    
                    ObjectOutputStream controllerOutput = new ObjectOutputStream(isAlive.getOutputStream());
                    
                    controllerOutput.writeObject("3");
                    System.out.println("request sent");
                    
                    ObjectInputStream controllerImputIsAlive = new ObjectInputStream(isAlive.getInputStream());
                    
                    int selector = (int) controllerImputIsAlive.readObject();
                    System.out.println(IPs.size());

                } catch ( SocketException ex) {
                    IPs.remove(i);
                    System.err.println("removed");
                }
            }

            controller = new ObjectInputStream(client.getInputStream());
            
            String selector = (String) controller.readObject();
            System.out.println("Selector");

            if (selector.equals("1")) {
                
                ChangeBooks changeBooks = new ChangeBooks(client, books, controller, IPs);
                new Thread(changeBooks).start();
                System.out.println("Change books");
                
            } else if (selector.equals("2")) {
                
                //send list size;
                SendBooks sendBooks = new SendBooks(client);
                new Thread(sendBooks).start();
                System.out.println("Server is conected to get books");
                
            } else if (selector.equals("3")) { //is alive
                
                System.out.println("I am alive");
                IsAlive isAlive = new IsAlive(client);
                new Thread(isAlive).start();
                
            } else if(selector.equals("4")) {
                
                ReciveIPsChanges reciveIpChange = new ReciveIPsChanges(client, controller);
                new Thread(reciveIpChange).start();
                System.out.println("Command 4 activated");
            } 
        }
    }
}
