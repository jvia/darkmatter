package com.giantcow.darkmatter.net;

import com.giantcow.darkmatter.DarkMatter;
import com.giantcow.darkmatter.LevelLoader;
import com.giantcow.darkmatter.player.HumanMatter;
import com.giantcow.darkmatter.player.Matter;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the server for the game. All clients connect to it (even in single player).
 * The server runs the main game loop. Clients simply subscribe to the data coming 
 * out of the server and update their views accordingly.
 *
 * TODO: GameLoop should run on the server and clients should simply send their clicks
 * and peek at the resulting sets.
 *
 * @author Jeremiah Via <jxv911@cs.bham.ac.uk>
 */
public class DarkServer extends Thread {

    public static final int PORT = 1234;
    public static final String HOST = "localhost";
    public static int PLAYERS = 0;
    public static Set<Matter> GAME_STATE;


    /** @param args  */
    public static void main(String[] args) {
        DarkServer.GAME_STATE = initializeGame();

        try {
            ServerSocket socket = new ServerSocket(DarkServer.PORT);

            Socket client;

            while (true) {
                System.out.println("Waiting for client " + (PLAYERS + 1) + "...");
                client = socket.accept();
                Handler clientHandler = new Handler(client);
                clientHandler.start();
            }

        } catch (IOException ex) {
            Logger.getLogger(DarkServer.class.getName()).log(Level.SEVERE, "Could not lock socket", ex);
        }
    }

    /** @return  */
    private static Set<Matter> initializeGame() {
        LevelLoader.readFile("01.lvl");
        Set<Matter> set = Collections.synchronizedSet(new HashSet<Matter>());
        set.addAll(LevelLoader.loadLevel());
        return set;
    }
}

/** @author via */
class Handler extends Thread {

    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Socket client;
    private boolean finished = false;
    private int player;
    private HumanMatter human = null;

    /** @param socket  */
    public Handler(Socket socket) {
        client = socket;
        player = DarkServer.PLAYERS++;
    }

    @Override
    public void run() {
        try {
            output = new ObjectOutputStream(client.getOutputStream());
            output.flush();
            input = new ObjectInputStream(client.getInputStream());

            try {

                while (!finished) {
                    GameMessage m = (GameMessage) input.readObject();

                    switch (m.getType()) {
                        case Click:
                            m = processClick(m);
                            break;
                        case String:
                            m = processString(m);
                            break;
                        case Set:
                            m = processSet(m);
                            break;
                    }

                    if (!finished) {
                        output.writeObject(m);
                    }
                }
            } finally {
                client.close();
                DarkServer.PLAYERS--;
            }

        } catch (IOException ex) {
            Logger.getLogger(Handler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Handler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private GameMessage processClick(GameMessage m) {
        Point2D pt = m.getClick();
        human.changeMove(pt.getX(), pt.getY(), null);
        m.setType(GameMessage.Type.String);
        m.setString("gamestate");
        return processString(m);
    }

    private GameMessage processString(GameMessage m) {
        GameMessage message = new GameMessage();
        String msg = m.getString();

        if (msg.equals("ready?")) {
            message.setType(GameMessage.Type.String);
            boolean ready = DarkServer.PLAYERS == 1;
            message.setString(String.valueOf(ready));
            if (ready) {
                new GameLoop().start();
            }
        } else if (msg.equals("bye")) {
            finished = true;
        } else if (msg.equals("gamestate")) {
            message.setType(GameMessage.Type.Set);
            message.setSet(DarkServer.GAME_STATE);
        } else if (msg.equals("whoami?")) {
            message.setType(GameMessage.Type.Set);
            Set<Matter> s = new HashSet<Matter>();
            human = LevelLoader.loadPlayer(human, player);
            s.add(human);
            message.setSet(s);
        }

        return message;
    }

    private GameMessage processSet(GameMessage m) {
        GameMessage message = new GameMessage();
        message.setType(GameMessage.Type.Set);

        Set<Matter> clientSet = m.getSet();

        synchronized (DarkServer.GAME_STATE) {
            DarkServer.GAME_STATE.addAll(clientSet);
        }

        message.setSet(DarkServer.GAME_STATE);

        return message;

    }
}

/**
 *
 * @author Jeremiah M. Via <jxv911@cs.bham.ac.uk>
 */
class GameLoop extends Thread {

    public static final int DELAY = 20;

    @Override
    public void run() {
        long beforeTime, timeDiff, sleep;
        beforeTime = System.currentTimeMillis();

        while (true) {

            update();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0)
                sleep = 2;

            try {
                sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }

            beforeTime = System.currentTimeMillis();
        }
    }

    private void update() {
        // remove too small matter objects
        deleteSmall();

        // Detect collisions
        synchronized (DarkServer.GAME_STATE) {
            for (Matter m : DarkServer.GAME_STATE) {
                for (Matter n : DarkServer.GAME_STATE) {
                    if (m.equals(n)) {
                        continue;
                    }

                    if (m.intersects(n) && m.isBigger(n)) {
                        double d = n.getRadius() + m.getRadius() - m.distance(n);
                        double area = 0.03 * d * n.getArea();
                        m.setArea(m.getArea() + area);
                        n.setArea(n.getArea() - area);
                    }
                }
            }
        }

        updateDirection();
        updateMovement();
    }

    private void updateDirection() {
        synchronized (DarkServer.GAME_STATE) {
            // Change dy/dx if necessary
            for (Matter m : DarkServer.GAME_STATE) {
                if (m.getX() <= 0 || m.getMaxX() >= DarkMatter.DEFAULT_WIDTH) {
                    m.setDx(-m.getDx());
                }
                if (m.getY() <= 0 || m.getMaxY() >= DarkMatter.DEFAULT_HEIGHT) {
                    m.setDy(-m.getDy());
                }
                m.update();
            }
        }
    }

    private void updateMovement() {
        ArrayList<Matter> list = new ArrayList<Matter>();
        synchronized (DarkServer.GAME_STATE) {
            for (Matter m : DarkServer.GAME_STATE) {
                Matter move = m.changeMove(0, 0, DarkServer.GAME_STATE);
                if (move != null) {
                    list.add(move);
                }
            }
        }

        DarkServer.GAME_STATE.addAll(list);
    }

    private void deleteSmall() {
        ArrayList<Matter> remove = new ArrayList<Matter>();
        for (Matter m : DarkServer.GAME_STATE)
            if (m.getArea() <= 0.1)
                remove.add(m);

        synchronized (DarkServer.GAME_STATE) {
            DarkServer.GAME_STATE.removeAll(remove);
        }
    }
}
