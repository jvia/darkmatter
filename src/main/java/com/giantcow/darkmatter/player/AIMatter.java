package com.giantcow.darkmatter.player;

import java.awt.geom.Line2D;
import java.io.Serializable;
import java.util.Collection;

/**
 * The intelligent computer player. It simply looks at the nearest object and moves towards it if it
 * is smaller than itself or away from it if it is larger than itself.
 *
 * @author Charlie Horrell <cxh900@cs.bham.ac.uk>
 * @author Joss Greenaway <jtg897@cs.bham.ac.uk>
 */
public class AIMatter extends Matter implements Serializable {

    /**
     * Constrcuts the AIMatter object.
     *
     * @param x      top-left X coordinate of the object
     * @param y      top-left Y coordinate of the object
     * @param radius radius of the object
     * @param dy     change in y-axis
     * @param dx     change in x-axis
     */
    public AIMatter(double x, double y, double radius, double dy, double dx) {
        super(x, y, radius, dy, dx);
    }

    /**
     * Allows the AIMatter object to change its movement. The player will not modify its movement if
     * there is no need for it.
     *
     * @param x      unused
     * @param y      unused
     * @param others the state of the world
     * @return the expelled matter
     */
    @Override
    public Matter changeMove(double x, double y, Collection<Matter> others) {

        Matter nearest = nearestMatter(others);

        if (nearest.getArea() * 0.10 >= getArea())
            return biggerBehavior(nearest);
        else
            return smallerBehavior(nearest);
    }

    /**
     * Determines what to do if this object is smaller than the nearest object.
     *
     * @param nearest nearest object
     * @return expelled matter if changing movement
     */
    private Matter smallerBehavior(Matter nearest) {
        if (willCollide(nearest))
            return moveAway(nearest);
        else
            return null;
    }

    /**
     * Determines what to do if this object is bigger than the nearest object.
     *
     * @param nearest nearest object
     * @return expelled matter if changing movement
     */
    private Matter biggerBehavior(Matter nearest) {
        if (!willCollide(nearest))
            return moveTowards(nearest);
        else
            return null;
    }

    /**
     * Finds the nearest object to this one.
     *
     * @param others the list of other objects
     * @return the nearest object
     */
    private Matter nearestMatter(Collection<Matter> others) {
        others.remove(this);
        Matter nearest = null;

        for (Matter m : others)
            if (nearest == null || distance(m) < distance(nearest))
                nearest = m;

        return nearest;
    }

    /**
     * Creates a virtual click so this object can move away from the nearest object.
     *
     * @param nearest other object
     * @return expelled matter
     */
    private Matter moveAway(Matter nearest) {
        return super.changeMove(0.0, 0.0, null);
    }

    /**
     * Creates a virtual click so this object can move towards from the nearest object.
     *
     * @param nearest other object
     * @return expelled matter
     */
    private Matter moveTowards(Matter nearest) {
        return super.changeMove(0.0, 0.0, null);
    }

    /**
     * Determines if this object will collide with the nearest object.
     *
     * @param o other object
     * @return true if they will collide; otherwise false
     */
    private boolean willCollide(Matter o) {
        Line2D v1 = new Line2D.Double(getCenterX(), getCenterY(),
                                      getCenterX() + (100 * getDx()),
                                      getCenterY() + (100 * getDy()));
        Line2D v2 = new Line2D.Double(o.getCenterX(), o.getCenterY(),
                                      o.getCenterX() + (100 * o.getDx()),
                                      o.getCenterY() + (100 * o.getDy()));
        return v1.intersectsLine(v2);
    }

}
