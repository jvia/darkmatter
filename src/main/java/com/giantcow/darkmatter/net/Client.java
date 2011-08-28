package com.giantcow.darkmatter.net;

import com.giantcow.darkmatter.player.Matter;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The client side of the networking stack. Defines a simple protocol for how a client may interact with the server.
 * Once the client is started in a thread it will continually query the server for the state of the world and send any
 * clicks that have been registered by the user.
 * <p/>
 * It works by keeping a queue of clicks and alternately sending every click in the queue and then requesting for the
 * state of the world. Users clicks are added to the queues and not sent immediately because it can corrupt the stream.
 * <p/>
 * There is a shutdown mechanism that allows the client to notify the server that it is going to quit so that the server
 * can free the resources associated with this client.
 *
 * @author Jeremiah M. Via <jxv911@cs.bham.ac.uk>
 * @version 2011.0311
 */
public final class Client extends Thread {
    /** Output all message data. */
    private static final boolean VERBOSE = false;

    /** Connection to the server. */
    Socket server;
    /** Output stream to server. */
    ObjectOutputStream output;

    /** Input stream from server. */
    ObjectInputStream input;
    /**
     * The current game state. The client is continually updating this and the user simply requests a copy of it when
     * they want to repaint.
     */
    private final List<Matter> list = Collections.synchronizedList(new ArrayList<Matter>());

    /**
     * The queue of user clicks. A click is added to the queue and when appropriate every click in the queue is sent to
     * the server.
     */
    private final Queue<Point> clicks = new LinkedBlockingQueue<Point>();

    /** True if we are still sending data, false if we are quitting. */
    private boolean finished;
    /** True if output stream is being used. */
    private boolean streamActive = false;

    /**
     * Creates a Client which tries to connect to the server specified in the paramaters.
     *
     * @param host server hostname
     * @param port port on server
     */
    public Client(String host, int port) {
        finished = false;
        try {
            server = new Socket(host, port);
            output = new ObjectOutputStream(server.getOutputStream());
            output.flush();
            input = new ObjectInputStream(server.getInputStream());

            System.out.printf("Connected to %s:%d\n", host, port);
        } catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** Creates the Client with default arguments to connect to localhost on port 1234. */
    public Client() {
        this("localhost", 1234);
    }

    /**
     * Interacts with the server while the user wants to remain connected. The run method simply sends all clicks in the
     * click queue and then updates the current game state. It does this continually so that users can repaint
     * immediately and not have to wait for a response from the server. Doing otherwise would take too long and cause
     * the user's game to lag.
     */
    @Override
    public void run() {
        List<Matter> m;
        while (!finished) {
            sendClicksToServer();
            m = getGameStateFromServer();
            if (m == null) continue;
            synchronized (list) {
                list.clear();
                list.addAll(m);
            }
        }
    }

    /** Sends all the clicks registered by the user and sends them when there is time. */
    private void sendClicksToServer() {
        GameMsg msg;
        synchronized (clicks) {
            while (!clicks.isEmpty() && !finished) {
                msg = new GameMsg(clicks.remove(), Message.click);
                writeMessage(msg);
            }
        }
    }


    /** Halts execution until the server is ready. */
    public void waitUntilReady() {
        GameMsg msg = new GameMsg(Message.ready);
        boolean ready;
        try {
            do {
                writeMessage(msg);
                ready = (Boolean) readMessage().getMessage();
            } while (!ready && !finished);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the current game state.
     *
     * @return list of matter objects
     */
    public List<Matter> getGameState() {
        return list;
    }

    /**
     * Sends a click to the server. It adds the user's click to a {@link java.util.concurrent.BlockingQueue} which is
     * sent as soon as there is an opening in the output stream.
     *
     * @param click user's click on screen
     */
    public void sendClick(Point click) {
        synchronized (clicks) {
            clicks.add(click);
        }
    }

    /**
     * Queries the server to find out a player's number. On the server side this also has the effect of replacing a
     * Matter object with a HumanMatter object in the game.
     *
     * @return player's number
     */
    public int whoAmI() {
        writeMessage(new GameMsg(Message.whoami));
        GameMsg msg = readMessage();
        if (msg.getMessageType() == Message.error)
            return whoAmI();
        return (Integer) msg.getMessage();
    }

    /**
     * Sends a shutdown message to the server to let the server know it can shutdown the handler and free up those
     * resources and it shuts down the connection on the client side.
     */
    public void shutdown() {
        try {
            finished = true;
            while (streamActive) {
                // wait to send message
            }
            writeMessage(new GameMsg(Message.done));
            server.close();
            server = null;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Returns the status of the client.
     *
     * @return true iff connected
     */
    public boolean isConnected() {
        return server != null && server.isConnected();
    }

    /**
     * Gets the current game state from the server and sets the to the value returned.
     *
     * @return the game state
     */
    private List<Matter> getGameStateFromServer() {
        GameMsg msg = new GameMsg(Message.gamestate);
        writeMessage(msg);
        msg = readMessage();

        if (msg.getMessageType() == Message.nosend)
            return list;

        return (ArrayList<Matter>) msg.getMessage();
    }

    /**
     * Helper method to read a message from the input stream.
     *
     * @return the GameMsg
     */
    private GameMsg readMessage() {
        if (finished) return new GameMsg(Message.nosend);

        GameMsg msg;
        try {
            msg = (GameMsg) input.readObject();
        } catch (Exception e) {
            msg = new GameMsg(Message.done);
        }

        if (VERBOSE)
            System.out.println("<< " + msg + " @ " + System.currentTimeMillis());

        return msg;
    }

    /**
     * Helper method to write a GameMsg to the output stream.
     *
     * @param msg message to send
     */
    private void writeMessage(GameMsg msg) {
        streamActive = true;
        if (msg.getMessageType() != Message.nosend) {
            try {
                output.writeObject(msg);
                output.reset();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        streamActive = false;
    }
}
