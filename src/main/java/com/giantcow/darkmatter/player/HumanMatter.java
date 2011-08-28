package com.giantcow.darkmatter.player;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.Collection;

/**
 * The Matter object for human players. This class simply overrides the {@link
 * Matter#changeMove(double, double, java.util.Collection)} method to take clicks as user input.
 * This class takes care of the creation of the new Matter object that results from moving.
 *
 * @author Jeremiah M. Via <jxv911@cs.bham.ac.uk>
 * @version 2011.0320
 * @since 0.1
 */
public class HumanMatter extends Matter implements Serializable {

    /**
     * Constructs the HumanMatter object.
     *
     * @param x      top-left X coordinate of the object
     * @param y      top-left Y coordinate of the object
     * @param radius radius of the object
     * @param dy     change in y-axis
     * @param dx     change in x-axis
     */
    public HumanMatter(double x, double y, double radius, double dy, double dx) {
        super(x, y, radius, dy, dx);
    }

    /**
     * Constructs the HumanMatter object.
     *
     * @param x      top-left X coordinate of the object
     * @param y      top-left Y coordinate of the object
     * @param radius radius of the object
     * @param dy     change in y-axis
     * @param dx     change in x-axis
     * @param id     the object's unique ID
     */
    public HumanMatter(double x, double y, double radius, double dy, double dx, int id) {
        super(x, y, radius, dy, dx, id);
    }

    /**
     * Takes a user's click and calculates the object's new velocity and the Matter object to be
     * expelled from it.
     *
     * @param x          x-coordinate of click
     * @param y          y-coordinate of click
     * @param matterList the list of all other objects
     * @return the expelled Matter object
     */
    @Override
    public Matter changeMove(double x, double y, Collection<Matter> matterList) {
        // Calculate expelled matter's new speed
        double deltaX = Math.abs(x - getCenterX());
        double deltaY = Math.abs(y - getCenterY());
        deltaX = 0.5 * deltaX / (deltaX + deltaY);
        deltaY = 0.5 - deltaX;

        if (x < getCenterX()) {
            deltaX *= -1;
        }
        if (y < getCenterY()) {
            deltaY *= -1;
        }

        // Create empty matter with the speed
        Matter m = new Matter(0.0, 0.0, 0.0, deltaY, deltaX);

        // Set matter's speed
        m.setArea(0.01 * getArea());
        setArea((1 - 0.01) * getArea());
        // Calculate matter's position
        Point2D centre = expulsionCentres(x, y, m.getRadius());
        double posX = centre.getX();
        double posY = centre.getY();

        m.setFrameFromCenter(posX, posY, posX + m.getRadius(), posY + m.getRadius());

        double nextDY = getDy() - m.getDy();
        nextDY = (nextDY > MAX_SPEED) ? MAX_SPEED : nextDY;
        double nextDX = getDx() - m.getDx();
        nextDX = (nextDX > MAX_SPEED) ? MAX_SPEED : nextDX;
        setDy(nextDY);
        setDx(nextDX);

        return m;
    }
}
