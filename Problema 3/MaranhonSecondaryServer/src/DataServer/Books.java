/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataServer;

import Model.BookModel;
import Model.IPModel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gledson
 */
public class Books {

    private boolean canChage;
    //ListaEncadeada DataBooks;

    public Books() throws IOException {
        canChage = true;
    }

    public synchronized List goFile() throws FileNotFoundException, IOException {
        
        List<BookModel> list = new ArrayList();
        FileInputStream stream = new FileInputStream("Books.txt");
        InputStreamReader reader = new InputStreamReader(stream);
        BufferedReader br = new BufferedReader(reader);

        while (true) {
            
            BookModel book = new BookModel();
            String linha = br.readLine();
            
            if(linha==null) {
                
                break;
                
            }
            
            String[] vet = linha.split("/");
            
            book.setName(vet[0]);
            book.setValue(vet[1]);
            book.setAmount(vet[2]);
            
            list.add(book);
           
        }
          
        return list;
    }

    public synchronized void chageBooks(String nameBook, String newAmount) throws FileNotFoundException, IOException {
        
        //certo
        List books = goFile();
        
        int aux1 = Integer.parseInt(newAmount);
        
        for (int i = 0; i < books.size(); i++) {
            
            if (((BookModel) books.get(i)).getName().equals(nameBook)) {
                
                int aux2 = Integer.parseInt(((BookModel) books.get(i)).getAmount());
                int aux3 = aux2 - aux1;
                String newAmountAux = "" + aux3;
                ((BookModel) books.get(i)).setAmount(newAmountAux);
                
            }
            
        }
        
        String[][] aux = new String[books.size()][3];
        
        for (int i = 0; i < books.size(); i++) {
            BookModel a = (BookModel)books.get(i);
            
            aux[i][0] = ((BookModel) books.get(i)).getName();
            aux[i][1] = ((BookModel) books.get(i)).getValue();
            aux[i][2] = ((BookModel) books.get(i)).getAmount();
            
        }
        
        writeNewFile(aux);
    }

    public synchronized void writeNewFile(String[][] books) throws IOException {
        
        File file = new File("Books.txt"); 
        FileOutputStream fos = new FileOutputStream(file);

        for (int i = 0; i < books.length; i++) {
            
            String name = books[i][0];
            String value = books[i][1];
            String amount = books[i][2];
            
            String divisorParametro = "/";
            String divisorUsuario = "\n";

            fos.write(name.getBytes());
            fos.write(divisorParametro.getBytes());
            fos.write(value.getBytes());
            fos.write(divisorParametro.getBytes());
            fos.write(amount.getBytes());
            fos.write(divisorUsuario.getBytes());
        }
        fos.close();
    }

    public List getDataBooks() throws IOException {
        return goFile();
    }

    public boolean isCanChage() {
        return canChage;
    }

    public void setCanChage(boolean canChage) {
        this.canChage = canChage;
    }
}
