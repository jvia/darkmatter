/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.giantcow.darkmatter.net;

import com.giantcow.darkmatter.Matter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jxv911
 */
public class DarkClient {

    // Server details
    private int PORT = 1234;
    private String HOST = "localhost";
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public DarkClient() {
        try {
            socket = new Socket(HOST, PORT);

            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

        } catch (UnknownHostException ex) {
            Logger.getLogger(DarkClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DarkClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void end() {
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(DarkClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Set<Matter> update(Set<Matter> gameState) {
        Set<Matter> data = new HashSet<Matter>();
        try {
            output.writeObject(gameState);
            data = (Set<Matter>) input.readObject();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DarkClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DarkClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
}
