package com.giantcow.darkmatter.net;

import com.giantcow.darkmatter.level.LevelLoader;
import com.giantcow.darkmatter.player.HumanMatter;
import com.giantcow.darkmatter.player.Matter;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the point of contact between the client and the server. Each client is given a thread
 * which deals with the communication between the two parties. All messages are  {@link GameMsg}
 * objects. This allows the handler to easily deal with one kind of message in an efficient manner.
 *
 * @author Jeremiah M. Via <jxv911@cs.bham.ac.uk>
 * @version 2011.0311
 */
public class Handler extends Thread {

    /** The socket connected to the client. */
    private Socket client;
    /** Determines if we have finished processing. */
    private boolean finished = false;
    /** The player's number. */
    private int player;
    /** Output stream to client. */
    private ObjectOutputStream output;
    /** Input stream to client. */
    private ObjectInputStream input;
    private static final boolean VERBOSE = false;

    /**
     * Creates a {@code Handler} to work with a client on a given socket,
     *
     * @param clientSocket socket to use
     */
    public Handler(Socket clientSocket) {
        client = clientSocket;
        player = Server.newPlayer();
    }

    /**
     * Runs the handler code. It waits for messages from the client and sends a response depending
     * on the type of message.
     */
    @Override
    public void run() {
        try {

            // Create I/O streams
            output = new ObjectOutputStream(client.getOutputStream());
            output.flush();
            input = new ObjectInputStream(client.getInputStream());

            // Deal with client request while not done
            while (!finished) {
                GameMsg msg = readMessage();

                switch (msg.getMessageType()) {
                    case click:
                        msg = processClick(msg);
                        break;
                    case done:
                        shutdown();
                        return;
                    case error:
                        msg = new GameMsg(Message.nosend);
                        break;
                    case gamestate:
                        msg = processGameStateRequest();
                        break;
                    case level:
                        msg = processLevelRequest(msg);
                        break;
                    case ready:
                        msg = processReadyRequest();
                        break;
                    case whoami:
                        msg = processWhoAmIRequest();
                        break;
                }
                try {
                    writeMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Handler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Sets the Handler to a finished state and removes the player from the server.
     *
     * @throws IOException io problem
     */
    private void shutdown() throws IOException {
        finished = true;
        Server.leavePlayer();
        client.close();
    }

    /**
     * Clients can query the server to see if the game is ready to begin. If enough clients have
     * connected to meet the minimum number of players for the game then the server will answer
     * <code>true</code> and begin the {@link GameLoop}.
     *
     * @return message containing true if ready, false otherwise
     */
    private GameMsg processReadyRequest() {
        GameMsg msg = new GameMsg(Server.hasMinimumPlayers(), Message.ready);
        if (VERBOSE) System.out.println(msg);

        if (Server.hasMinimumPlayers() && !GameLoop.getGL().isRunning()) {
            System.out.println("Starting Game Loop!");
            GameLoop.reset();
            GameLoop.getGL().start();
        }
        return msg;
    }

    /**
     * Deals with a request for the state of the world. This method will return the current state of
     * {@link Server#GSTATE}.
     *
     * @return current state of the game
     */
    private GameMsg processGameStateRequest() {
        List<Matter> m;
        synchronized (Server.GSTATE) {
            m = new ArrayList<Matter>(Server.GSTATE);
        }
        return new GameMsg(m, Message.gamestate);
    }

    /**
     * Allows a player to request a specific level to be loaded.
     *
     * @param msg message send to handler
     * @return a nosend message
     */
    private GameMsg processLevelRequest(GameMsg msg) {
        LevelLoader.readFile((String) msg.getMessage());
        synchronized (Server.GSTATE) {
            Server.GSTATE.addAll(LevelLoader.loadLevel());
        }
        msg.setMessageType(Message.nosend);
        return msg;
    }

    /**
     * Clients query the server to figure out which player they are. The handler will load them as a
     * {@link HumanMatter} in the game and return to them an integer indicating which player they
     * are.
     *
     * @return client's player number
     */
    private GameMsg processWhoAmIRequest() {
        HumanMatter human = null;
        human = LevelLoader.loadPlayer(human, player);
        synchronized (Server.GSTATE) {
            Server.GSTATE.clear();
            Server.GSTATE.addAll(LevelLoader.loadLevel());
        }
        return new GameMsg(human.getId(), Message.whoami);
    }

    /**
     * Deals with a click message sent by the user. When the <code>Handler</code> gets a click from
     * the client, the <code>Handler</code> will place a lock on the game state and update the
     * player's movement and add any matter the player has expelled.
     *
     * @param msg click message
     * @return a nosend message
     */
    private GameMsg processClick(GameMsg msg) {
        Point click = (Point) msg.getMessage();
        if (VERBOSE) System.out.println("Player click @ " + click);

        synchronized (Server.GSTATE) {
            Matter changeMove = Server.GSTATE.get(player).changeMove(click.x, click.y, null);
            Server.GSTATE.add(changeMove);
        }
        msg.setMessageType(Message.nosend);
        return msg;
    }

    /**
     * Helper method to read messages.
     *
     * @return the message read from the stream or an error message
     */
    private GameMsg readMessage() {
        try {
            return (GameMsg) input.readObject();
        } catch (Exception e) {
            System.out.println("Shutting handler down");
            return new GameMsg(Message.done);
        }
    }

    /**
     * Helper method to write messages.
     *
     * @param msg message to send
     * @throws IOException io problems
     */
    private void writeMessage(GameMsg msg) throws IOException {
        if (VERBOSE)
            System.out.println(">> " + msg + " @ " + System.currentTimeMillis());
        if (!finished && msg.getMessageType() != Message.nosend) {
            output.writeObject(msg);
            output.reset();
        }
    }
}
