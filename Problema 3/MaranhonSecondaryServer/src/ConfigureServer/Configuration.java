/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConfigureServer;

import DataServer.Books;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import maranhomsecondaryserver.Controller.Controller;

/**
 *
 * @author Gledson
 */
public class Configuration {
    
    public static Books books;
    
    public Configuration(Books books) {
        this.books = books;
    }
    
    public int start() throws IOException, ClassNotFoundException {
        
        Socket conectToMaranhon = new Socket("10.42.0.43", 12345);
        int port;
        
        ObjectOutputStream output0 = new ObjectOutputStream(conectToMaranhon.getOutputStream());
        output0.writeObject("2");
        System.out.println("eniada solicitação");

        ObjectInputStream controllerImput = new ObjectInputStream(conectToMaranhon.getInputStream());
        port = controllerImput.readInt();
        System.out.println("Porta recebida");

        //port = port + 1;
        
        int controllerConectionToMaranhon = (int) controllerImput.readObject();

        if (controllerConectionToMaranhon == 2) {
            
            ObjectInputStream reciveAllBooks = new ObjectInputStream(conectToMaranhon.getInputStream());
            String[][] dataBooks = (String[][]) reciveAllBooks.readObject();
            
            System.out.println("tamanho da lista" + Controller.AddBooks(dataBooks).size());
            
            //ListaEncadeada aux = Controller.AddBooks(dataBooks); 
            books.writeNewFile(dataBooks);
            
            System.out.println("SERVIDOR IGUAL AOS DEMAIS");
            
        } else {
            System.out.println("primeiro a se conectar");
        }
        
        conectToMaranhon.close();
        
        return port;
    }
    
}
