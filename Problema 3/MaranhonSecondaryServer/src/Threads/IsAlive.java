/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import static maranhonSecondaryServer.MaranhonSecondaryServer.books;

/**
 *
 * @author Gledson
 */
public class IsAlive implements Runnable {

    public Socket client;

    public IsAlive(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        ObjectOutputStream send;
        try {
            
            send = new ObjectOutputStream(client.getOutputStream());
            send.flush();
            int tamanho = books.getDataBooks().size();
            send.writeObject(tamanho);
            
            client.close();
            
        } catch (IOException ex) {
            Logger.getLogger(IsAlive.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
