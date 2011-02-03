/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.giantcow.darkmatter;

/**
 * Calcualte the vector of a matter
 *
 * @author Charlie Horrell <cxh900@cs.bham.ac.uk>
 * @author Yukun Wang <yxw999@cs.bham.ac.uk>
 * @version 2011.0201
 * @since 0.1
 */
public class Vector {

    private double quantity;
    private double direction;

    /**
     * Constructs a Vector object
     * @param matter
     */
    public Vector(Matter matter) {

        quantity = matter.getRadius()*matter.getRadius();

        if (matter.getDx() > 0 && matter.getDy() > 0) {
            direction = Math.atan(-matter.getDx() / matter.getDy()) + Math.PI;
        }
        if (matter.getDx() > 0 && matter.getDy() < 0) {
            direction = Math.atan(-matter.getDx() / matter.getDy());
        }
        if (matter.getDx() < 0 && matter.getDy() > 0) {
            direction = Math.atan(-matter.getDx() / matter.getDy()) - Math.PI;
        }
        if (matter.getDx() < 0 && matter.getDy() < 0) {
            direction = Math.atan(-matter.getDx() / matter.getDy());
        }
    }

    /**
     *
     * @return(
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * 
     * @return
     */
    public double getDirection() {
        return direction;
    }
}
