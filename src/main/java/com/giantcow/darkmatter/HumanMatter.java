
package com.giantcow.darkmatter;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.Set;

/**
 *
 * @author jxv911
 */
public class HumanMatter extends Matter {

    public HumanMatter(double x, double y, double radius, double dy, double dx) {
        super(new Ellipse2D.Double(x, y, 2 * radius, 2 * radius), dy, dx);
    }

    public Matter changeMove(double x, double y, Set<Matter> matterList) {
        // Calculate expelled matter's new speed
        double deltaX = 0.1;
        double deltaY = 0.1;

        if (x < getCenterX()) {
            deltaX *= -1;
        }
        if (y < getCenterY()) {
            deltaY *= -1;
        }

        // Create empty matter with the speed
        Matter m = new Matter(0.0, 0.0, 0.0, deltaY, deltaX);

        // Set matter's speed
        m.setArea(0.05 * getArea());
        setArea((1 - 0.05) * getArea());
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
