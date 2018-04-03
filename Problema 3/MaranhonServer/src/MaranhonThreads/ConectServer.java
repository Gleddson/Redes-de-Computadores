/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MaranhonThreads;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import maranhon.Model.IPModel;

/**
 *
 * @author Gledson
 */
public class ConectServer implements Runnable {
    
    private List<IPModel> IPs;
    
    public ConectServer(List<IPModel> IPs) {
        this.IPs = IPs;
    }

    @Override
    public void run() {
        for(int i = 0; i < IPs.size(); i++) {
            try {
            
                Socket sendIps = new Socket(((IPModel)IPs.get(i)).getIP(), ((IPModel)IPs.get(i)).getPort());
                
                ObjectOutputStream send = new ObjectOutputStream(sendIps.getOutputStream());
                send.flush();
                
                send.writeObject("4");
                send.writeObject("1");
                
                send.writeObject(sendIps.getInetAddress().getHostAddress());
                
                int port = sendIps.getPort() + 1;
                
                send.writeObject(port);
                sendIps.close();
                        
            } catch ( ConnectException ex) {
                Logger.getLogger(ConectServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ConectServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }    
}
