package com.giantcow.darkmatter.net;

import com.giantcow.darkmatter.player.Matter;
import java.io.Serializable;
import java.util.Set;

/**
 * The message objects that will be sent back and forth
 * between the Client and Server.
 * 
 * @author Jeremiah M. Via <jxv911@cs.bham.ac.uk>
 */
public class Message implements Serializable {

    private static final long serialVersionUID = -8584149296031996410L;
    private static final int MESSAGE = 0;
    private static final int DATA = 1;

    String message;
    Set<Matter> data;
    int type = MESSAGE;
}
