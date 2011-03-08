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
    }

    public Set<Matter> getSet() {

        return set;
    }

    public void setSet(Set<Matter> set) {

        this.set = set;
    }

    public String getString() {

        return string;
    }

    public void setString(String string) {

        this.string = string;
    }

    public void setClick(Point2D click) {
        this.click = click;
    }

    public void setClick(double x, double y) {
        click = new Point2D.Double(x, y);
    }

    public Point2D getClick() {
        return click;
    }
}
