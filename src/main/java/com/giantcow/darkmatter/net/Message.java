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

    enum Type {

        String, Data
    };
    
    String message;
    Set<Matter> data;
    Type type = Type.String;
}
