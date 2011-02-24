package com.giantcow.darkmatter.net;

import com.giantcow.darkmatter.Matter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jeremiah Via <jxv911@cs.bham.ac.uk>
 */
public class DarkServer {

    protected static final Matter MINIMUM_MATTER_SIZE = new Matter(0, 0, 1.0, 0, 0);

    public static void main(String[] args) {
        try {
            new DarkServer();
        } catch (IOException ex) {
            Logger.getLogger(DarkServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static final int PORT = 1234;
    // Blob characteristics
    private static final int INITIAL_NPCS = 20;
    private double MAX_START_X = 1000;
    private double MAX_START_Y = 1000;
    private double MAX_RADIUS = 50.0;
    private double MAX_DY = 1.0;
    private double MAX_DX = 1.0;
    private SortedSet<Matter> matters = Collections.synchronizedSortedSet(new TreeSet<Matter>());
    public static int PLAYERS = 0;

    public DarkServer() throws IOException {

        while (matters.size() < INITIAL_NPCS) {
            Matter m = new Matter(randomX(), randomY(), randomRadius(),
                                  randomDY(), randomDX());

            if (!m.intersects(matters))
                matters.add(m);
        }

        ServerSocket serverSocket = new ServerSocket(PORT);

        while (true) {
            Socket socket = serverSocket.accept();
            PLAYERS++;
            DarkServerHandler handler = new DarkServerHandler(socket, matters);
            handler.start();
        }
    }

    private double randomX() {
        return Math.random() * MAX_START_X;
    }

    private double randomY() {
        return Math.random() * MAX_START_Y;
    }

    private double randomRadius() {
        return Math.random() * MAX_RADIUS;
    }

    private double randomDY() {
        return Math.random() * MAX_DY;
    }

    private double randomDX() {
        return Math.random() * MAX_DX;
    }

}
