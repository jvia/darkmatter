package com.giantcow.darkmatter.net;

import com.giantcow.darkmatter.Matter;
import java.net.Socket;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author Jeremiah Via <jxv911@cs.bham.ac.uk>
 */
public class DarkServerHandler extends Thread {

    private Socket clientSock;
    private String address;
    private SortedSet<Matter> matters = Collections.synchronizedSortedSet(
            new TreeSet<Matter>());

    DarkServerHandler(Socket socket, SortedSet<Matter> matters) {
        clientSock = socket;
        this.matters = matters;
    }

    public void run() {
        throw new UnsupportedOperationException();
    }

    




}
