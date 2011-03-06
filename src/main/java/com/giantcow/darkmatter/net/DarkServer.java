package com.giantcow.darkmatter.net;

import com.giantcow.darkmatter.player.Matter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * TODO: Client should be able to ask `whoami' in order to get
 * find out which player they are for the map.
 * 
 * @author Jeremiah Via <jxv911@cs.bham.ac.uk>
 */
public class DarkServer {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        DarkServer.GAME_STATE = initializeGame();

        try {
            ServerSocket socket = new ServerSocket(DarkServer.PORT);

            Socket client;

            while (true) {
                System.out.println("Waiting for client " + ( PLAYERS + 1 ) + "...");
                client = socket.accept();
                Handler clientHandler = new Handler(client);
                clientHandler.start();
            }

        }
        catch (IOException ex) {
            Logger.getLogger(DarkServer.class.getName()).log(Level.SEVERE, "Could not lock socket", ex);
        }
    }
    public static final int PORT = 1234;
    public static final String HOST = "localhost";
    public static int PLAYERS = 0;
    public static Set<Matter> GAME_STATE;

    /**
     *
     * @return
     */
    private static Set<Matter> initializeGame() {
        Set<Matter> set = Collections.synchronizedSet(new HashSet<Matter>());
        set.add(new Matter(10, 10, 40, 0, 0));
        return set;
    }
}

/**
 *
 * @author via
 */
class Handler extends Thread {

    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Socket client;
    private boolean finished = false;

    /**
     *
     * @param socket
     * @param set
     */
    public Handler(Socket socket) {
        client = socket;
        DarkServer.PLAYERS++;
    }

    /**
     *
     */
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
            }
            finally {
                client.close();
                DarkServer.PLAYERS--;
            }

        }
        catch (IOException ex) {
            Logger.getLogger(Handler.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(Handler.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IllegalMessageTypeException ex) {
            Logger.getLogger(Handler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private GameMessage processString(GameMessage m) throws IllegalMessageTypeException {
        GameMessage message = new GameMessage();
        String msg = m.getString();

        if (msg.equals("ready?")) {
            message.setType(GameMessage.Type.String);
            message.setString(String.valueOf(DarkServer.PLAYERS == 2));
        } else if (msg.equals("bye")) {
            finished = true;
        } else if (msg.equals("gamestate")) {
            message.setType(GameMessage.Type.Set);
            message.setSet(DarkServer.GAME_STATE);
        }

        return message;
    }

    private GameMessage processSet(GameMessage m) throws IllegalMessageTypeException {
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
