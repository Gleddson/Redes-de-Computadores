/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maranhon.DataBooks.DataClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import maranhon.Model.IPModel;
import maranhon.Model.ClientModel;

/**
 *
 * @author Gledson
 */
public class DataClient {

    ClientModel client;

    public synchronized List goFile() throws FileNotFoundException, IOException {

        List<ClientModel> list = new ArrayList();
        FileInputStream stream = new FileInputStream("Clients.txt");
        InputStreamReader reader = new InputStreamReader(stream);
        BufferedReader br = new BufferedReader(reader);

        while (true) {

            client = new ClientModel();
            String linha = br.readLine();

            if (linha == null) {

                break;

            }

            String[] vet = linha.split("/");
            client.setName(vet[0]);
            client.setAmmount(vet[1]);

            list.add(client);
            System.err.println(vet[0]);

        }

        return list;
    }

    public synchronized void changeClient(String clientName, String ammount) throws IOException {

        List<ClientModel> clients = goFile();
        boolean contain = false;
        
        System.err.println(clientName);

        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getName().equals(clientName)) {
                System.err.println(clients.get(i).getName());
                int ammountAux = Integer.parseInt(clients.get(i).getAmmount());
                int newAmmountAux = Integer.parseInt(ammount);

                int newAmmount = ammountAux + newAmmountAux;
                String newAmmountS = "" + newAmmount;
                clients.get(i).setAmmount(newAmmountS);
                contain = true;
                System.out.println("CONTAIN");
                changeClient(clients);
            }
        }

        if (contain == false) {

            System.out.println("NOT CONTAIN");
            ClientModel newClient = new ClientModel();
            newClient.setName(clientName);
            newClient.setAmmount(ammount);
            clients.add(newClient);

            changeClient(clients);
            System.out.println("client added");
        }
    }

    public synchronized void changeClient(List<ClientModel> clients) throws IOException {
        File file = new File("Clients.txt");
        FileOutputStream fos = new FileOutputStream(file);

        for (int i = 0; i < clients.size(); i++) {

            String name = clients.get(i).getName();
            String amount = clients.get(i).getAmmount();

            String divisorParametro = "/";
            String divisorUsuario = "\n";

            fos.write(name.getBytes());
            fos.write(divisorParametro.getBytes());
            fos.write(amount.getBytes());
            fos.write(divisorUsuario.getBytes());
        }
        fos.close();
    }
}
