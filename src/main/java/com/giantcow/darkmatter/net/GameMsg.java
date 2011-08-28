package com.giantcow.darkmatter.net;

import java.io.Serializable;

/**
 * The class represents a message to be sent over the network between the client and server.
 *
 * @author Jeremiah M. Via <jxv911@cs.bham.ac.uk>
 * @version 2011.0311
 */
public class GameMsg implements Serializable {

    /** Unique identifier */
    private static final long serialVersionUID = -8584149296031996410L;
    /** The contents of the message. */
    private Object message;
    /** The type of message. */
    private Message messageType;

    /**
     * Creates a GameMsg which contains data and a <code>MessageType</code>
     *
     * @param message     data to send
     * @param messageType the message type
     */
    public GameMsg(Object message, Message messageType) {
        this.message = message;
        this.messageType = messageType;
    }

    /**
     * Creates a <code>GameMsg</code> with an empty data compartment. This is useful for sending queries rather than
     * data.
     *
     * @param messageType message type
     */
    public GameMsg(Message messageType) {
        this(null, messageType);
    }

    /**
     * Gets the data out of the GameMsg.
     *
     * @return data
     */
    public Object getMessage() {
        return message;
    }

    /**
     * Sets the data inside the GameMsg.
     *
     * @param message the data
     */
    public void setMessage(Object message) {
        this.message = message;
    }

    /**
     * Gets the Message type out of the GameMsg.
     *
     * @return message type
     */
    public Message getMessageType() {
        return messageType;
    }

    /**
     * Sets the Message type.
     *
     * @param messageType the message type
     */
    public void setMessageType(Message messageType) {
        this.messageType = messageType;
    }

    /**
     * Returns a String representation of the <code>GameMsg</code> in the format <code>Messagetype [data]</code>
     *
     * @return string
     */
    @Override
    public String toString() {
        return String.format("%s [%s]",
                             messageType,
                             (message == null) ? "null" : message.toString());
    }
}
