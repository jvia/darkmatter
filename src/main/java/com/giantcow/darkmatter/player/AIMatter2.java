/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.giantcow.darkmatter.player;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author yxw999
 */
public class AIMatter2 extends HumanMatter {

    public Matter matter;
    public Set<Matter> matterList;
    public int DEFAULT_WIDTH;
    public int DEFAULT_HEIGHT;
    public double x;
    public double y;

    public AIMatter2(double x, double y, double radius, double dy, double dx) {
        super(x, y, radius, dy, dx);
    }

    public void setScreen(int w, int h) {
        DEFAULT_WIDTH = w;
        DEFAULT_HEIGHT = h;
    }

    private void update2() {
        // Detect collisions
        for (Matter m : matterList) {
            for (Matter n : matterList) {
                if (m.equals(n)) {
                    continue;
                }

                if (m.intersects(n) && m.isBigger(n)) {
                    double d = n.getRadius() + m.getRadius() - m.distance(n);
                    double area = 0.03 * d * n.getArea();
                    m.setArea(m.getArea() + area);
                    n.setArea(n.getArea() - area);
                }
            }

        }
        updateDirection();
        updateMovement();
    }

    private void updateDirection() {
        // Change dy/dx if necessary
        for (Matter m : matterList) {
            if (m.getX() <= 0 || m.getMaxX() >= DEFAULT_WIDTH) {
                m.setDx(-m.getDx());
            }
            if (m.getY() <= 0 || m.getMaxY() >= DEFAULT_HEIGHT) {
                m.setDy(-m.getDy());
            }
            m.update();
        }

    }

    private void updateMovement() {
        ArrayList<Matter> list = new ArrayList<Matter>();
        for (Matter m : matterList) {
            if (m == matter) {
                continue;
            }
            Matter move = m.changeMove(0, 0, matterList);
            if (move != null) {
                list.add(move);
            }
        }
        matterList.addAll(list);
    }

    private boolean escape() {
        boolean r = false;
        for (Matter m : matterList) {
            if (!m.equals(matter) && m.intersects(matter) && m.isBigger(matter)) {
                x = m.getCenterX();
                y = m.getCenterY();
                r = true;
            }
        }
        return r;
    }

    private boolean capture() {
        boolean r = false;
        for (Matter m : matterList) {
            if (!m.equals(matter) && m.intersects(matter) && matter.isBigger(m)) {
                r = true;
            }
        }
        return r;
    }

    private boolean click() {
        boolean r = false;
        if (escape()) {
            r = true;
        } else if (capture()) {
        }
        return r;
    }

    public void thinking() {
        x=0;
        y=0;
        int i = 0;
        double d=500;
        while (i < 10 && !click()) {
            update2();
        }
        for(Matter m : matterList) {
            if (matter.isBigger(m)&&matter.distance(m)<d){
                d=matter.distance(m);
                if(m.getCenterY()>matter.getCenterY()){
                    y=matter.getCenterY()+1;
                } else if(m.getCenterY()<matter.getCenterY()){
                    y=matter.getCenterY()-1;
                }
                if(m.getCenterX()>matter.getCenterX()){
                    x=matter.getCenterX()+1;
                } else if(m.getCenterX()<matter.getCenterX()){
                    x=matter.getCenterX()-1;
                }
            }
        }
        
    }

    public Matter changeMove(Collection<Matter> list) {
        Matter[] a =(Matter[]) list.toArray();
        matterList = new HashSet<Matter>();
        matterList.addAll(Arrays.asList(a));

        thinking();
        
        System.out.println(x+" "+y);
        
        double deltaX = 0.25;
        double deltaY = 0.25;

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
