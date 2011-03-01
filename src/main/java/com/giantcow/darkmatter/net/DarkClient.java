/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.giantcow.darkmatter.net;

import com.giantcow.darkmatter.player.Matter;
import java.io.InputStream;
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
            InputStream inputStream = socket.getInputStream();
            input = new ObjectInputStream(inputStream);

            System.out.println("Connected!");
        } catch (UnknownHostException ex) {
            Logger.getLogger(DarkClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DarkClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int playerCountRequest() {
        try {
            Message msg = new Message();
            msg.type = msg.type.String;
            msg.message = "playercount";
            output.writeObject(msg);
            msg = (Message) input.readObject();
            if (msg.type == msg.type.String)
                return Integer.parseInt(msg.message);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DarkClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DarkClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Integer.MIN_VALUE;
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
            output.flush();
            data = (Set<Matter>) input.readObject();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DarkClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DarkClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }

    public static void main(String[] args) {
        DarkClient dc = new DarkClient();

        while (true) {
            System.out.println("PLAYERS: " + dc.playerCountRequest());
        }
    }
}
