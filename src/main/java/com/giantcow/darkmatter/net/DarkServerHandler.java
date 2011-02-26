package com.giantcow.darkmatter.net;

import com.giantcow.darkmatter.player.Matter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
public class DarkServerHandler extends Thread {

    private Socket clientSock;
    private String address;
    private SortedSet<Matter> matters = Collections.synchronizedSortedSet(new TreeSet<Matter>());

    DarkServerHandler(Socket socket, SortedSet<Matter> matters) {
        clientSock = socket;
        this.matters = matters;
    }

    @Override
    public void run() {
        // open object stream from client
        ObjectInputStream input = null;
        try {
            input = new ObjectInputStream(clientSock.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(DarkServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (clientSock.isConnected()) {
            try {

                // Read the player's current matter list
                SortedSet<Matter> playerset = Collections.synchronizedSortedSet(new TreeSet<Matter>());
                playerset = (SortedSet<Matter>) input.readObject();
                playerset = playerset.tailSet(DarkServer.MINIMUM_MATTER_SIZE);

                synchronized (matters) {
                    matters.addAll(playerset);
                }

                // Open object sttream to client
                ObjectOutputStream output = new ObjectOutputStream(clientSock.getOutputStream());

                // Send the new set back down
                output.writeObject(matters);

            } catch (IOException ex) {
                Logger.getLogger(DarkServerHandler.class.getName()).log(Level.SEVERE, "IO Problem", ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DarkServerHandler.class.getName()).log(Level.SEVERE, "Object not of expected type", ex);
            }
        }
    }
}
