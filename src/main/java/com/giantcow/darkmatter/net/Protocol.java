package com.giantcow.darkmatter.net;

/**
 *
 * @author Jeremiah M. Via <jxv911@cs.bham.ac.uk>
 */
public class Protocol {

    enum State {

        Waiting, Sent, Closing
    };
    State state;

    public GameMessage processMesage(GameMessage message) {

        switch (state) {
            case Waiting:
            case Sent:
            case Closing:
            default:

        }
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
