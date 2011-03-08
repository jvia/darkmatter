/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.giantcow.darkmatter.net;

import com.giantcow.darkmatter.player.HumanMatter;
import com.giantcow.darkmatter.player.Matter;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/** @author jxv911 */
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
        } catch (Exception ex) {
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
        } catch (Exception ex) {
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
        } catch (Exception ex) {
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
        } catch (Exception ex) {
            Logger.getLogger(DarkClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public HumanMatter getPlayer() {
        try {
            GameMessage message = new GameMessage();
            message.setType(GameMessage.Type.String);
            message.setString("whoami?");
            output.writeObject(message);

            message = (GameMessage) input.readObject();
            HumanMatter matter = (HumanMatter) message.getSet().toArray()[0];
            return matter;
        } catch (Exception ex) {
            Logger.getLogger(DarkClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Set<Matter> sendClick(Point2D click) {
        GameMessage message = new GameMessage();
        message.setType(GameMessage.Type.Click);

        if (click == null)
            message.setClick(-1, -1);
        else
            message.setClick(click);

        try {
            output.writeObject(message);
            message = (GameMessage) input.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return message.getSet();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        DarkClient client = new DarkClient();
        Set<Matter> game = new HashSet<Matter>();
        boolean finished = false;
        boolean ready = false;

        Matter o = new Matter(0.0, 0.0, 3.0, 5.0, 6.0);
        Matter n = o;

        while (!client.isReady()) {
            System.out.println(o + " <> " + n);
            System.out.println("Not ready <> " + o.equals(n));
            n.update();
        }
        System.out.println("Ready");

        while (!finished) {
            System.out.println();
            System.out.print(game.size() + ": ");
            if (game.size() > 0) {
                System.out.print(game.toArray()[0]);
            }
            game = client.update(game);
            if (game.size() > 0) {
                System.out.println("BEFORE: " + game.toArray()[0]);
            }
            for (Matter m : game) {
                m.update();
            }
            if (game.size() > 0) {
                System.out.println("AFTER: " + game.toArray()[0]);
            }

            if (game.size() == 1000) {
                finished = true;
            }
        }
        client.shutdown();
    }
}
