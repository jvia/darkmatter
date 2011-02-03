package com.giantcow.darkmatter;

/**
 * Class to define Velocity and Direction of matters vector.
 *
 * @author Charlie Horrell <cxh900@cs.bham.ac.uk>
 * @author Jeremiah Via <jxv911@cs.bham.ac.uk>
 * @version 2011.0203
 * @since 0.1
 */
public class VelocityVector {

    private double dy;
    private double dx;

    /**
     * Creates a Velocity Vector.
     * @param dy
     * @param dx
     */
    public VelocityVector(double dy, double dx) {
        this.dy = dy;
        this.dx = dx;
    }

    /**
     * Returns dx.
     * @return
     */
    public double getDx() {
        return dx;
    }

    /**
     * Sets dx.
     * @param dx
     */
    public void setDx(double dx) {
        this.dx = dx;
    }

    /**
     * returns dy.
     * @return
     */
    public double getDy() {
        return dy;
    }

    /**
     * sets dy.
     * @param dy
     */
    public void setDy(double dy) {
        this.dy = dy;
    }

    /**
     * Calculates the resultant based in the change in the x direction and change in the y direction.
     * @return
     */
    public double resultant() {
        return Math.hypot(dx, dy);
    }

    /**
     * Calculates the angle of the direction of the velocity vector.
     * @return
     */
    public double angle() {
        return Math.toDegrees(Math.atan2(dx, dy));
    }

    /**
     * Calculates the momentum value of the matter.
     * @param mass
     * @return
     */
    public double momentum(double mass) {
        return resultant() * mass;
    }
}
