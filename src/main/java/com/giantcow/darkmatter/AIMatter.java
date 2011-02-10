/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.giantcow.darkmatter;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 *
 * @author Charlie
 */
public class AIMatter extends Matter {

    public AIMatter(double x, double y, double radius, double dy, double dx) {
        super(new Ellipse2D.Double(x, y, radius, radius), dy, dx);
    }

    public Matter changeMove(double x, double y, ArrayList<Matter> others) {
        // Calculate expelled matter's new speed
        Matter best = null;
        Matter expel = new Matter(0., 0.0, 0.0, 0.1, 0.1);
        for (Matter other : others) {
            if (Math.hypot(other.getBlob().getCenterX() - getBlob().getCenterX(),
                    other.getBlob().getCenterY() - getBlob().getCenterY()) <= Math.hypot(best.getBlob().getCenterX() - getBlob().getCenterX(),
                    best.getBlob().getCenterY() - getBlob().getCenterY())) {
                if (other.getArea() < best.getArea()) {
                    best = other;
                }

            }
        }

        double xmod = Math.abs(getBlob().getCenterX() - best.getBlob().getCenterX());
        double ymod = Math.abs(getBlob().getCenterY() - best.getBlob().getCenterY());

        if (getBlob().getCenterX() < best.getBlob().getCenterX()) {
            x = getBlob().getCenterX() - xmod;
        }

        if (getBlob().getCenterX() > best.getBlob().getCenterX()) {
            x = getBlob().getCenterX() + xmod;
        }

        if (getBlob().getCenterY() < best.getBlob().getCenterY()) {
            y = getBlob().getCenterY() - ymod;
        }


        if (getBlob().getCenterY() > best.getBlob().getCenterY()) {
            y = getBlob().getCenterY() + ymod;
        }

        // Set matter's speed
        expel.setArea(0.05 * getArea());
        setArea((1 - 0.05) * getArea());
        // Calculate matter's position
        Point2D centre = expulsionCentres(x, y, expel.getRadius());
        double posX = centre.getX();
        double posY = centre.getY();

        expel.getBlob().setFrameFromCenter(posX, posY, posX + expel.getRadius(), posY + expel.getRadius());

        double nextDY = getDy() - expel.getDy();
        nextDY = (nextDY > MAX_SPEED) ? MAX_SPEED : nextDY;
        double nextDX = getDx() - expel.getDx();
        nextDX = (nextDX > MAX_SPEED) ? MAX_SPEED : nextDX;
        setDy(nextDY);
        setDx(nextDX);

        return expel;
    }
}
