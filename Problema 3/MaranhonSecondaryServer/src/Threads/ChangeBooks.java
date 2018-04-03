/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

import DataServer.Books;
import Model.IPModel;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gledson
 */
public class ChangeBooks implements Runnable {
    
    private Socket client;
    private Books books;
    private ObjectInputStream controllerImput;
    private List<IPModel> IPs;
    
    public ChangeBooks(Socket client, Books books, ObjectInputStream controller, List<IPModel> IPs) {
        this.client = client;
        this.books = books;
        this.controllerImput = controller;
        this.IPs = IPs;
    }
    
    @Override
    public void run() {
        
        try {
            
            String clientOrServer = (String) controllerImput.readObject();
            String nameBooks = (String) controllerImput.readObject();
            String ammountBooks = (String) controllerImput.readObject();
            
            books.chageBooks(nameBooks, ammountBooks);
            System.err.println("Changed for the client");
            
            ///ObjectOutputStream outputConfirmChanges = new ObjectOutputStream(client.getOutputStream());
            //outputConfirmChanges.flush();
            //outputConfirmChanges.writeObject("Changed for the client");
            
            if (clientOrServer.equals("1")) {
                System.err.println("Server Conected");
                if (!IPs.isEmpty()) {
                    for (int i = 0; i < IPs.size(); i++) {
                        Socket changeBooks = new Socket(((IPModel) IPs.get(i)).getIP(), ((IPModel) IPs.get(i)).getPort());
                        
                        ObjectOutputStream changes = new ObjectOutputStream(changeBooks.getOutputStream());
                        changes.writeObject("1");
                        changes.writeObject("2");
                        changes.writeObject(nameBooks);
                        changes.writeObject(ammountBooks);
                        
                        System.err.println("Send to all servers");
                        
                    }
                }
            }
            
            client.close();
            
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ChangeBooks.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
