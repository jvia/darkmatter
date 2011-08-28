package com.giantcow.darkmatter.net;

/**
 * The Message type to send over the network. This is for convenience as it allows the client and server to negotiate
 * what a message means more simply.
 *
 * @author Jeremiah M. Via <jxv911@cs.bham.ac.uk>
 * @version 2011.0311
 */
public enum Message {
    /**
     * From the client this represents a query for the current state of the game. From the server this represents the
     * current state of the game.
     */
    gamestate,

    /**
     * From the client this represents a query to find out which player id the client has. From the server this
     * represents the response to the client's request.
     */
    whoami,

    /** A request from the client to find out if the game is ready to begin. Server responds with true of false. */
    ready,

    /**
     * Message from the client to let the server know that it is done playing and that the server can let the client's
     * handler thread die.
     */
    done,

    /**
     * A simple way to let the client or server know an error has occurred when they are reading or writing to their
     * streams. This is usually handled by the by the entity that generates it.
     */
    error,

    /** A message from the client consisting of the coordinates of a user's click. */
    click,

    /** A request to load a specific level with the LevelLoader. */
    level,

    /** A type of message that will not be written to stream. Essentially a dummy message. */
    nosend
}
