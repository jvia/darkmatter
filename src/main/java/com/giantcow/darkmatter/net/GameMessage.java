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
public class GameMessage implements Serializable {

    private static final long serialVersionUID = -8584149296031996410L;
    private String string;
    private Set<Matter> set;
    private Type type = Type.String;

    public enum Type {

        String, Set
    };

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Set<Matter> getSet() throws IllegalMessageTypeException {
        if (type == Type.String) {
            throw new IllegalMessageTypeException();
        }
        return set;
    }

    public void setSet(Set<Matter> set) throws IllegalMessageTypeException {
        if (type == Type.String) {
            throw new IllegalMessageTypeException();
        }
        this.set = set;
    }

    public String getString() throws IllegalMessageTypeException {
        if (type == Type.Set) {
            throw new IllegalMessageTypeException();
        }
        return string;
    }

    public void setString(String string) throws IllegalMessageTypeException {
        if (type == Type.Set) {
            throw new IllegalMessageTypeException();
        }
        this.string = string;
    }
}
