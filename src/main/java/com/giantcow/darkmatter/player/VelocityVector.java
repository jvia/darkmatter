package com.giantcow.darkmatter.player;

import java.io.Serializable;

/**
 * Defines velocity for objects in the game. It also contains some nice methods for dealing with the
 * velocity of objects traveling on a plane.
 *
 * @author Charlie Horrell <cxh900@cs.bham.ac.uk>
 * @author Jeremiah Via <jxv911@cs.bham.ac.uk>
 * @version 2011.0203
 * @since 0.1
 */
public class VelocityVector implements Serializable {

    /** Unique identifier. */
    private static final long serialVersionUID = 7526472295622776148L;

    /** Change in y-axis. */
    private double dy;
    /** Change in x-axis. */
    private double dx;

    /**
     * Creates a Velocity Vector.
     *
     * @param dy the change in the y-axs
     * @param dx the change in the x-axis
     */
    public VelocityVector(double dy, double dx) {
        this.dy = dy;
        this.dx = dx;
    }

    /**
     * Returns the change in the x-axis.
     *
     * @return change in x-axis
     */
    public double getDx() {
        return dx;
    }

    /**
     * Sets the change in the x-axis.
     *
     * @param dx change in x-axis
     */
    public void setDx(double dx) {
        this.dx = dx;
    }

    /**
     * Returns the change in the y-axis.
     *
     * @return change in y-axis
     */
    public double getDy() {
        return dy;
    }

    /**
     * Sets the change in the y-axis.
     *
     * @param dy change in y-axis
     */
    public void setDy(double dy) {
        this.dy = dy;
    }

    /**
     * Calculates the resultant of the vector. This is nothing more than the hypotenuse of the
     * triangle formed by the values {@code dy} and {@code dx}.
     *
     * @return hypotenus of dy-dx triangle
     */
    public double resultant() {
        return Math.hypot(dx, dy);
    }

    /**
     * Calculates the angle of the direction of the velocity vector. This can be used to figure out
     * the direction and object will be moving on the plane.
     *
     * @return the object's direction
     */
    public double angle() {
        return Math.toDegrees(Math.atan2(dx, dy));
    }

    /**
     * Calculates the momentum value of the matter. Momentum is simply an object's mass multiplied
     * by how fast it is moving through the plane.
     *
     * @param mass the object's mass
     * @return the momentum of the object.
     */
    public double momentum(double mass) {
        return resultant() * mass;
    }
}
