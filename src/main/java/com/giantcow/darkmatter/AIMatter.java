    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.giantcow.darkmatter;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author Charlie
 */
public class AIMatter extends Matter {

    public AIMatter(double x, double y, double radius, double dy, double dx) {
        super(new Ellipse2D.Double(x, y, radius, radius), dy, dx);
    }

    @Override
    public Matter changeMove(double x, double y, Set<Matter> others) {
        // Calculate expelled matter's new speed
        Matter best = (Matter)others.toArray()[0];
        Matter expel = new Matter(5.0, 5.0, 5.0, 0.1, 0.1);
        for (Matter other : others) {
            if (Math.hypot(other.getCenterX() - getCenterX(),
                    other.getCenterY() - getCenterY()) <= Math.hypot(best.getCenterX() - getCenterX(),
                    best.getCenterY() - getCenterY())) {
                if (other.getArea() < best.getArea()) {
                    best = other;
                }

            }
        }

        double xmod = Math.abs(getCenterX() - best.getCenterX());
        double ymod = Math.abs(getCenterY() - best.getCenterY());

        if (getCenterX() < best.getCenterX()) {
            x = getCenterX() - xmod;
        }

        if (getCenterX() > best.getCenterX()) {
            x = getCenterX() + xmod;
        }

        if (getCenterY() < best.getCenterY()) {
            y = getCenterY() - ymod;
        }


        if (getCenterY() > best.getCenterY()) {
            y = getCenterY() + ymod;
        }

        // Set matter's speed
        expel.setArea(0.05 * getArea());
        setArea((1 - 0.05) * getArea());
        // Calculate matter's position
        Point2D centre = expulsionCentres(x, y, expel.getRadius());
        double posX = centre.getX();
        double posY = centre.getY();

        expel.setFrameFromCenter(posX, posY, posX + expel.getRadius(), posY + expel.getRadius());

        double nextDY = getDy() - expel.getDy();
        nextDY = (nextDY > MAX_SPEED) ? MAX_SPEED : nextDY;
        double nextDX = getDx() - expel.getDx();
        nextDX = (nextDX > MAX_SPEED) ? MAX_SPEED : nextDX;
        setDy(nextDY);
        setDx(nextDX);

        return expel;
    }

}
