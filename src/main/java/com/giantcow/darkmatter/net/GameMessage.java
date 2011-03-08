package com.giantcow.darkmatter.net;

import com.giantcow.darkmatter.player.Matter;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.Set;

/**
 * The message objects that will be sent back and forth between the Client and Server.
 *
 * @author Jeremiah M. Via <jxv911@cs.bham.ac.uk>
 */
public class GameMessage implements Serializable {

    private static final long serialVersionUID = -8584149296031996410L;
    private String string;
    private Set<Matter> set;
    private Point2D click;
    private Type type = Type.String;

    public enum Type {

        Click, String, Set
    }

    /**
     * Returns the current type of the message.
     *
     * @return
     */
    public Type getType() {
        return type;
    }

    /**
     * Sets the type of the message. <em>Warning:</em> Setting the type will clear out any data already in the message.
     *
     * @param type
     */
    public void setType(Type type) {
        this.type = type;
        click = null;
        string = null;
        set = null;
    }

    /**
     * Returns the set within the message.
     *
     * @return set
     */
    public Set<Matter> getSet() {
        return set;
    }

    /**
     * Sets the set within the message.
     *
     * @param set set to send
     */
    public void setSet(Set<Matter> set) {
        this.set = set;
    }

    /**
     * Gets the string in the message.
     *
     * @return the string
     */
    public String getString() {
        return string;
    }

    /**
     * Sets the string within the message.
     *
     * @param string string to send
     */
    public void setString(String string) {
        this.string = string;
    }

    /**
     * Sets the click within the message.
     *
     * @param click click to send
     */
    public void setClick(Point2D click) {
        this.click = click;
    }

    /**
     * Sets the click within the message.
     *
     * @param x x component of click
     * @param y y component of click
     */
    public void setClick(double x, double y) {
        click = new Point2D.Double(x, y);
    }

    /**
     * Gets the click from the message.
     *
     * @return the click
     */
    public Point2D getClick() {
        return click;
    }
}
