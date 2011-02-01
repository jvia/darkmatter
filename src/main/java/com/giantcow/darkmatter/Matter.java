package com.giantcow.darkmatter;

import java.awt.geom.Ellipse2D;

/**
 * Perfect circle representing a Matter object in space
 * @author Joss Greenaway <jtg897@cs.bham.ac.uk>
 * @author Jeremiah Via <jxv911@cs.bham.ac.uk>
 * @version 2011.0201
 * @since 0.1
 */
public class Matter {

    private Ellipse2D.Double blob;
    private double dy;
    private double dx;

    /**
     * Constructs a Matter object
     * @param blob the Ellipse2D object
     * @param dy change in y
     * @param dx change in x
     */
    public Matter(Ellipse2D.Double blob, double dy, double dx) {
        this.blob = blob;
        this.dy = dy;
        this.dx = dx;
    }

    /**
     * Constructs a Matter object
     * @param blob the Ellipse2D object
     */
    public Matter(Ellipse2D.Double blob) {
        this(blob, 0.0, 0.0);
    }

    /**
     * Constructs a Matter object
     * @param x Top left corner of the bounding rectangle
     * @param y Top left corner of the bounding rectangle
     * @param radius the radius
     * @param dy change in y
     * @param dx change in x
     */
    public Matter(double x, double y, double radius, double dy, double dx) {
        this(new Ellipse2D.Double(x, y, radius, radius), dy, dx);
    }

    /**
     * Returns Ellipse2D object
     * @return
     */
    public Ellipse2D getBlob() {
        return blob;
    }

    /**
     * sets Ellipse2D object
     * @param blob the Ellipse2D object
     */
    public void setBlob(Ellipse2D.Double blob) {
        this.blob = blob;
    }

    /**
     * Returns change in x
     * @return change in x
     */
    public double getDx() {
        return dx;
    }

    /**
     * Sets change in x
     * @param dx change in x
     */
    public void setDx(double dx) {
        this.dx = dx;
    }

    /**
     * returns change in y
     * @return change in y
     */
    public double getDy() {
        return dy;
    }

    /**
     * sets change in y
     * @param dy change in y
     */
    public void setDy(double dy) {
        this.dy = dy;
    }

    /**
     * Checks whether Matter object intersects 'other'
     * @param other other Matter object
     * @return 'True' if objects intersects, 'False' if otherwise
     */
    public boolean intersects(Matter other) {
        return blob.intersects(other.getBlob().getX(), other.getBlob().getY(),
                other.getBlob().getWidth(), other.getBlob().getHeight());
    }

    /**
     * Modifies Matter object based on the other Matter object it has intersected with
     * @param other other Matter object
     */
    public void collision(Matter other) {
        throw new UnsupportedOperationException("Not done");
    }

    /**
     * Updates the position of the Ellipse2D object 'blob'
     */
    public void update(){
        blob.x = (dx + blob.getX());
        blob.y = (dy + blob.getY());
    }
}
