package com.giantcow.darkmatter.net;

import com.giantcow.darkmatter.Utils;
import com.giantcow.darkmatter.level.LevelLoader;
import com.giantcow.darkmatter.player.Matter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The server listens for connections to be made and assigns a {@link Handler} to each connection.
 * <p/>
 * The server does no processing other than assigning Handlers to Clients. It also maintains the
 * global state of the game so that all the Handler threads and modify it.
 * <p/>
 * <b>*Important*</b>: All access to the global game state must be thread-safe. If not a {@link
 * java.util.ConcurrentModificationException} can be thrown which may stop the execution of the
 * {@link GameLoop}.
 *
 * @author Jeremiah M. Via <jxv911@cs.bham.ac.uk>
 * @version 2011.0311
 */
public final class Server extends Thread {

    /**
     * Runs the server on its own so that a multiplayer game can start.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        Server s = new Server();
        s.setMinPlayers(2);
        new Server().start();
    }

    /**
     * The global agme state. All access to this variable must be thread-safe or bad things will
     * happen.
     */
    public static final ArrayList<Matter> GSTATE = new ArrayList<Matter>();


    /** The port to be listening on. */
    private int port;
    /** The socket used by the Server. */
    private ServerSocket server;


    /** Boolean flag to say if we're done running the server or not. */
    private boolean finished = false;
    /**
     * The minimum number of players for a game to begin. 1 by default and must be set higher for a
     * multiplayer game.
     */
    private static int minPlayers = 1;
    /**
     * The current number of players. When {@code current >= minPlayers} is true then the game is
     * ready to begin.
     */
    private static int players = 0;

    /**
     * Constructs the Server to listen on a specific port.
     *
     * @param port port to listen on.
     */
    public Server(int port) {
        this.port = port;
        LevelLoader.readFile(Utils.randomLevel());
        GSTATE.addAll(LevelLoader.loadLevel());
    }

    /** Constructs a Server to listen on the default port. */
    public Server() {
        this(1234);
    }

    /**
     * Sets the minimum number of players required for a game to begin.
     *
     * @param min minimum number of players
     */
    public void setMinPlayers(int min) {
        minPlayers = min;
    }

    /**
     * Runs the server in a thread. The server simply accepts connections and passes them on to a
     * unique Handler thread foreach client.
     */
    @Override
    public void run() {

        try {
            server = new ServerSocket(port);

            while (!finished) {
                System.out.println("Waiting for connection...");
                Socket clientSocket = server.accept();
                System.out.println("Accepting client @ " + clientSocket.getInetAddress());
                new Handler(clientSocket).start();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Server going down");
        }
    }

    /**
     * Shuts the server down.  This will always cause an exception to be thrown because the server
     * will be waiting for a connection.
     */
    public void shutdown() {
        try {
            finished = true;
            if (server != null) {
                server.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Server shutdown");
    }

    /**
     * Increases the player count.
     *
     * @return the number of the player.
     */
    public static int newPlayer() {
        if (players == 0) {
            LevelLoader.readFile(Utils.randomLevel());
            synchronized (GSTATE) {
                GSTATE.addAll(LevelLoader.loadLevel());
            }
        }
        return players++;
    }

    /** Removes the player from the game. */
    public static void leavePlayer() {
        players--;
        System.out.println("Player left");
        // Safety measure to ensure thread dies
        if (players == 0)
            GameLoop.reset();

    }

    /**
     * Determines if the minimum number of players are connected.
     *
     * @return true iff players >= minPlayers
     */
    public static boolean hasMinimumPlayers() {
        return players >= minPlayers;
    }

    /**
     * Loads a level.
     *
     * @param level level to load
     */
    public void loadLevel(String level) {
        LevelLoader.readFile(level);
        synchronized (GSTATE) {
            GSTATE.clear();
            GSTATE.addAll(LevelLoader.loadLevel());
        }
    }
}
