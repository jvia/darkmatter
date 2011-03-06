/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.giantcow.darkmatter.net;

import com.giantcow.darkmatter.player.Matter;
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

    private static final String DEFAULT_HOST = "localhost";
    private String host;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Socket socket;

    public DarkClient(String host) {
        try {
            socket = new Socket(host, DarkServer.PORT);
            output = new ObjectOutputStream(socket.getOutputStream());
            output.flush();
            input = new ObjectInputStream(socket.getInputStream());
        }
        catch (Exception ex) {
            Logger.getLogger(DarkClient.class.getName()).log(Level.SEVERE, "Could not connect", ex);
            System.exit(1);
        }

    }

    public DarkClient() {
        this(DEFAULT_HOST);
    }

    public boolean isReady() {
        boolean ready = false;
        try {
            GameMessage message = new GameMessage();
            message.setType(GameMessage.Type.String);
            message.setString("ready?");
            output.writeObject(message);
            message = (GameMessage) input.readObject();
            ready = Boolean.valueOf(message.getString());
        }
        catch (Exception ex) {
            Logger.getLogger(DarkClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ready;
    }

    public Set<Matter> update(Set<Matter> set) {
        try {
            GameMessage message = new GameMessage();
            message.setType(GameMessage.Type.Set);
            message.setSet(set);
            output.writeObject(message);
            message = (GameMessage) input.readObject();
            return message.getSet();
        }
        catch (Exception ex) {
            Logger.getLogger(DarkClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return set;
    }

    public void shutdown() {
        try {
            GameMessage message = new GameMessage();
            message.setType(GameMessage.Type.String);
            message.setString("bye");
            output.writeObject(message);

            socket.close();
        }
        catch (Exception ex) {
            Logger.getLogger(DarkClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) throws UnknownHostException, IOException,
            IllegalMessageTypeException, ClassNotFoundException {

        DarkClient client = new DarkClient();
        Set<Matter> game = new HashSet<Matter>();
        boolean finished = false;
        boolean ready = false;

        while (!client.isReady()) {
            System.out.println("Not ready");
        }
        System.out.println("Ready");
        
        while (!finished) {


            game.add(new Matter(Math.random(),
                    Math.random(),
                    Math.random(),
                    Math.random(),
                    Math.random()));
            game = client.update(game);
            System.out.println(game.size());

            if (game.size() == 1000) {
                finished = true;
            }
        }
        client.shutdown();
    }
}
