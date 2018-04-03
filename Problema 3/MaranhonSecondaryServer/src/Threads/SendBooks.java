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
import maranhomsecondaryserver.Controller.Controller;
import static maranhonSecondaryServer.MaranhonSecondaryServer.books;

/**
 *
 * @author Gledson
 */
public class SendBooks implements Runnable {

    Socket client;

    public SendBooks(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        ObjectOutputStream send;
        try {
            send = new ObjectOutputStream(client.getOutputStream());
            send.writeObject(books.getDataBooks().size());

            String[][] dataBooks = Controller.getBooks();

            send.writeObject(dataBooks);
            
            client.close();
            
        } catch (IOException ex) {
            Logger.getLogger(SendBooks.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
