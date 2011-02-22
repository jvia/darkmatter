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

    public Matter AIchangeMove(double x, double y, ArrayList<Matter> others) {
        // Calculate expelled matter's new speed
        Matter best = null;
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
     /**
     * Creates Point2D object holding x and y coordinates which represent the centre
     * position of a new expelled Matter object.
     *
     * @param x x-coordinate of mouse click
     * @param y y-coordinate of mouse click
     * @param radius radius of expelled matter object
     * @return Point2D object holding x and y coordinates which represent the centre position
     *         of the expelled Matter object
     */
    protected Point2D expulsionCentres( double xmod, double ymod, double radius) {
        Point2D.Double expulsionCentre = new Point2D.Double(0.0, 0.0);
        double theta = Math.atan(Math.abs(xmod - getCenterY())/Math.abs(xmod - getCenterX()));
        double theta2 = Math.toDegrees(Math.atan2(ymod,xmod));

        double hyp = radius + getRadius() ;
        double y1 = Math.sin(theta) * hyp;
        double x1 = Math.cos(theta) * hyp;

        if (xmod <= getCenterX() & ymod <= getCenterY()) {
            expulsionCentre.setLocation(getCenterX() - x1, getCenterY() - y1);
        } else if (xmod > getCenterX() & ymod <= getCenterY()) {
            expulsionCentre.setLocation(getCenterX() + x1, getCenterY() - y1);
        } else if (xmod <= getCenterX() & ymod > getCenterY()) {
            expulsionCentre.setLocation(getCenterX() - x1, getCenterY() + y1);
        } else if (xmod > getCenterX() & ymod > getCenterY()) {
            expulsionCentre.setLocation(getCenterX() + x1, getCenterY() + y1);
        }
        return expulsionCentre;
    }

}
