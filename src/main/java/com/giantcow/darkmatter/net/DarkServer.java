package com.giantcow.darkmatter.net;

import com.giantcow.darkmatter.player.Matter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jeremiah Via <jxv911@cs.bham.ac.uk>
 */
public class DarkServer {

    public static final int DEFAULT_PORT = 1234;
    public static final String HOST = "localhost";
    public static int players;
    Set<Matter> gameState;

    /**
     * 
     * @param port
     */
    public DarkServer(final int port) {
        gameState = initializeGame();

        try {
            ServerSocket socket = new ServerSocket(port);

            Socket client;

            while (true) {
                System.out.println("Waiting for client " + (players +1) + "..." );
                client = socket.accept();
                new DarkServerHandler(client, gameState);
            }

        } catch (IOException ex) {
            Logger.getLogger(DarkServer.class.getName()).log(Level.SEVERE, "Could not lock socket", ex);
        }

    }

    /**
     *
     */
    public DarkServer() {
        this(DEFAULT_PORT);
    }

    /**
     *
     * @return
     */
    private Set<Matter> initializeGame() {
        Set<Matter> set = new HashSet<Matter>();
        set.add(new Matter(10, 10, 40, 0, 0));
        return set;
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        new DarkServer();
    }
}
