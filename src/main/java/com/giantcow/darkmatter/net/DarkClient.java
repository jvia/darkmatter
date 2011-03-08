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
        HumanMatter me = null;
        boolean finished = false;

        while (!client.isReady())
            System.out.println("Waiting for connection...");
        System.out.println("Connected!");

        game = client.update(game);
        me = client.getPlayer();
        Matter remove = null;
        for (Matter m : game) {
            if (m.getX() == me.getX() && m.getY() == me.getY() && m.getRadius() == me.getRadius()
                && m.getDx() == me.getDx() && m.getDy() == m.getDy())
                remove = m;
        }
        game.remove(remove);
        game.add(me);
        game = client.update(game);

        for (Matter m : game) {
            System.out.println((me == m) + " " + m.getClass().getSimpleName());
        }
        System.out.println("I'm " + me + "\n");

        int i = 0;
        while (!finished) {
            System.out.println("SIZE: " + game.size());
            for (Matter m : game)
                if (m == me)
                    System.out.printf(">  %s\n", m);
                else
                    System.out.printf("   %s \n", m);


            // Human clicks on the screen
            // and client sends a message to server
            game = client.sendClick(new Point2D.Double(Math.random(), Math.random()));
            System.out.println();

            finished = i++ == 5;
        }
        client.shutdown();
    }
}
