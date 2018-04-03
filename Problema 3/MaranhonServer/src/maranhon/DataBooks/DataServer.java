/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maranhon.DataBooks;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import maranhon.Model.IPModel;

/**
 *
 * @author Gledson
 */
public class DataServer {
    
    IPModel IP;
            
    public synchronized List goFile() throws FileNotFoundException, IOException {
        List<IPModel> list = new ArrayList();
        FileInputStream stream = new FileInputStream("IPs.txt");
        InputStreamReader reader = new InputStreamReader(stream);
        BufferedReader br = new BufferedReader(reader);

        IP = new IPModel();
        
        String linha = br.readLine();

        while (linha != null) {
            String[] vet = linha.split("/");
            IP.setIP(vet[0]);
            //IP.setPort(vet[1]);

            list.add(IP);
            linha = br.readLine();
        }
        return list;
    }
    
    public synchronized boolean checkServer(String IP, int Port) throws FileNotFoundException, IOException {
        List<IPModel> list = goFile();
        boolean aux = false;
        for (int i = 0; i < list.size(); i++) {
            if (((IPModel) list.get(i)).getIP().equals(IP)) {
                
            }
        }
        return aux;
    }
    
    public synchronized void AddServer(String IP, int Port) throws IOException {
        Writer arquivo = new BufferedWriter(new FileWriter("IPs.txt", true));
        arquivo.append(IP + "/" + Port + "\n");
        arquivo.close();
    }
}

